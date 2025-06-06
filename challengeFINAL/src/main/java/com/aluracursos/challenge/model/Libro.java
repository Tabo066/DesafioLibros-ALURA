package com.aluracursos.challenge.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Libro {

    @Id
    private Integer id;

    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "autores_libro", joinColumns = @JoinColumn(name = "libro_id"))
    private List<AutorLibro> autores;

    private String idioma;

    private Integer descargas;

    private String enlace; // un formato representativo

    public Libro() {}

    public Libro(DatosLibro datos) {
        this.id = datos.id();
        this.titulo = datos.title();
        this.idioma = datos.languages().isEmpty() ? "N/A" : datos.languages().get(0);
        this.descargas = datos.download_count();
        this.enlace = datos.formats().getOrDefault("text/html", "N/A");

        this.autores = datos.authors().stream()
                .map(a -> new AutorLibro(a.name(), a.birth_year(), a.death_year()))
                .toList();
    }

    @Override
    public String toString() {
        return "📘 Libro: " + titulo +
                "\n✍️ Autores: " + autores +
                "\n🌐 Idioma: " + idioma +
                "\n⬇️ Descargas: " + descargas +
                "\n🔗 Enlace: " + enlace + "\n";
    }

    // Getters y Setters omitidos por brevedad

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<AutorLibro> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorLibro> autores) {
        this.autores = autores;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}