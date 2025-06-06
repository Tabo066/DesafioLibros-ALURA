package com.aluracursos.challenge.principal;
import com.aluracursos.challenge.model.*;
import com.aluracursos.challenge.repository.LibroRepository;
import com.aluracursos.challenge.service.ConsumoAPI;
import com.aluracursos.challenge.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepo;

    public Principal(LibroRepository libroRepo) {
        this.libroRepo = libroRepo;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libros por nombre 
                    2 - Seleccionar libro por id
                    3 - Buscar libros por autor
                    4 - Buscar libros por idioma
                    5 - Mostrar libros guardados (local)
                    6 - Mostrar libros por idioma (local)
                    7 - Mostrar autores guardados (local) 
                    8 - Top 10 mas descargados (local)
                    9 - Mostrar autores vivos por año (local)
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    buscarLibroPorId();
                    break;
                case 3:
                    buscarLibrosPorAutor();
                    break;
                case 4:
                    buscarLibrosPorIdioma();
                    break;
                case 5:
                    mostrarLibrosGuardados();
                    break;
                case 6:
                    mostrarLibrosPorIdiomaLocal();
                    break;
                case 7:
                    mostrarAutoresGuardados();
                    break;
                case 8:
                    mostrarTop10MasDescargados();
                    break;
                case 9:
                    mostrarAutoresVivos();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibrosWeb() {
        System.out.println("Escribe el título del libro a buscar:");
        String tituloLibro = teclado.nextLine();

        String url = URL_BASE + tituloLibro.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);

        DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

        if (respuesta.results().isEmpty()) {
            System.out.println("No se encontraron resultados para: " + tituloLibro);
            return;
        }

        respuesta.results().forEach(libro -> {
            System.out.println("🔢 Id: " + libro.id());
            System.out.println("📘 Título: " + libro.title());
            System.out.println("✍️ Autor(es): " + libro.authors().stream().map(Autor::name).toList());
            System.out.println("🌐 Idiomas: " + libro.languages());
            System.out.println("⬇️ Descargas: " + libro.download_count());
            System.out.println("—".repeat(50));
        });
    }

    private void buscarLibroPorId() {
        System.out.println("🔢 Ingresa el ID del libro a consultar:");
        int idLibro = teclado.nextInt();
        teclado.nextLine();
        String url = "https://gutendex.com/books/" + idLibro + "/";
        String json = consumoApi.obtenerDatos(url);

        try {
            DatosLibro libro = conversor.obtenerDatos(json, DatosLibro.class);

            System.out.println("📘 Título: " + libro.title());
            System.out.println("✍️ Autor(es): " + libro.authors().stream().map(Autor::name).toList());
            System.out.println("🌐 Idiomas: " + libro.languages());
            System.out.println("⬇️ Descargas: " + libro.download_count());

            Libro guardarLibro = new Libro(libro);
            libroRepo.save(guardarLibro);
            System.out.println("✅ Libro guardado en la base de datos:");
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo obtener el libro con ID " + idLibro + ". Verifica que exista.");
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.println("✍️ Ingresa el nombre del autor a buscar:");
        String autor = teclado.nextLine();
        String url = "https://gutendex.com/books/?search=" + autor.replace(" ", "+") + "&apikey=a93e1904";
        String json = consumoApi.obtenerDatos(url);

        try {
            DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);
            if (respuesta.results().isEmpty()) {
                System.out.println("❌ No se encontraron libros de autor: " + autor);
            } else {
                System.out.println("📚 Resultados para el autor: " + autor);
                respuesta.results().forEach(libro -> {
                    System.out.println("📘 Título: " + libro.title());
                    System.out.println("✍️ Autor(es): " + libro.authors().stream().map(Autor::name).toList());
                    System.out.println("⬇️ Descargas: " + libro.download_count());
                    System.out.println("—".repeat(50));
                });
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al procesar la búsqueda: " + e.getMessage());
        }
    }

    private void mostrarLibrosGuardados() {
        List<Libro> libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros almacenados aún.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void mostrarAutoresGuardados() {
        List<Libro> libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros almacenados aún.");
            return;
        }
        Set<AutorLibro> autores = libros.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .collect(Collectors.toSet());
        System.out.println("✍️ Autores guardados en la base de datos:");
        autores.forEach(autor -> System.out.println("• " + autor));
    }

    private void mostrarAutoresVivos() {
        System.out.println("🗓️ Ingresa el año para verificar autores vivos:");
        int año = teclado.nextInt();
        teclado.nextLine();
        List<Libro> libros = libroRepo.findAll();

        Set<String> autoresVivos = libros.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .filter(autor -> autor.getNacimiento() <= año &&
                        (Objects.isNull(autor.getFallecimiento()) || autor.getFallecimiento() >= año))
                .map(AutorLibro::getNombre)
                .collect(Collectors.toSet());

        if (autoresVivos.isEmpty()) {
            System.out.println("❌ No se encontraron autores vivos en el año " + año);
        } else {
            System.out.println("✍️ Autores vivos en el año " + año + ":");
            autoresVivos.forEach(System.out::println);
        }
    }

    private void mostrarLibrosPorIdiomaLocal() {
        System.out.println("🌐 Ingresa el código de idioma a filtrar (ej: en, es, fr):");
        String idioma = teclado.nextLine().toLowerCase();

        List<Libro> filtrados = libroRepo.findAll().stream()
                .filter(libro -> libro.getIdioma().contains(idioma))
                .toList();

        if (filtrados.isEmpty()) {
            System.out.println("❌ No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("📚 Libros en idioma '" + idioma + "':");
            filtrados.forEach(libro -> {
                System.out.println("📘 Título: " + libro.getTitulo());
                System.out.println("✍️ Autor(es): " + libro.getAutores());
                System.out.println("⬇️ Descargas: " + libro.getDescargas());
                System.out.println("—".repeat(50));
            });
        }
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("🌐 Ingresa el código de idioma (ej: en, es, fr):");
        String codigoIdioma = teclado.nextLine().toLowerCase();
        String url = "https://gutendex.com/books/?languages=" + codigoIdioma;
        String json = consumoApi.obtenerDatos(url);

        try {
            DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);
            if (respuesta.results().isEmpty()) {
                System.out.println("❌ No se encontraron libros en idioma: " + codigoIdioma);
                return;
            }
            System.out.println("📚 Libros en idioma: " + codigoIdioma.toUpperCase());
            respuesta.results().forEach(libro -> {
                System.out.println("📘 Título: " + libro.title());
                System.out.println("✍️ Autor(es): " + libro.authors().stream().map(Autor::name).toList());
                System.out.println("⬇️ Descargas: " + libro.download_count());
                System.out.println("—".repeat(50));
            });
        } catch (Exception e) {
            System.out.println("⚠️ Error al buscar libros por idioma: " + e.getMessage());
        }
    }

    private void mostrarTop10MasDescargados() {
        List<Libro> libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros guardados en la base de datos local.");
            return;
        }
        System.out.println("📈 Top 10 libros más descargados:");
        libros.stream()
                .sorted(Comparator.comparingInt(Libro::getDescargas).reversed())
                .limit(10)
                .forEach(libro -> {
                    System.out.println("📘 Título: " + libro.getTitulo());
                    System.out.println("✍️ Autor(es): " + libro.getAutores());
                    System.out.println("⬇️ Descargas: " + libro.getDescargas());
                    System.out.println("—".repeat(50));
                });
    }
}



