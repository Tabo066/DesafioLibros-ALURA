# DesafioLibros-ALURA
App en Springboot java implementando una API de consulta de libros agregando una base de datos local en PostgreSQL <br>
Detalles: configuré algunas cosas diferentes a lo que sugeria el trello y a lo aprendido en clase por una cuestion de comodidad personal. <br> 
Por ejemplo el uso de @JsonAlias me estaba complicando el mapeo de la respuesta de la api en mi aplicacion por lo que hice el mapeo como viene en el Json. <br>
La manera en la cual se guardan los libros buscados en la base de datos local es mediante el Id, lo configure asi para que sea mas puntual. <br>
Se puede buscar por nombre primero para estar seguros de que el libro se encuentra en la api y luego a partir de los multiples resultados seleccionar el libro especifico a partir del id.<br>
Dentro de application.properties no utilice las variables de entorno porque me daban dificultades a la hora de conectar con postgre (nose porque).<br>
Dentro de la base de datos libros_alura se crearan 2 tablas: "autores_libro" y "libro" para acceder mas comodamente a las consultas.<br>
"Consumoapi", "IConvierteDatos", "Conviertedatos", entre otras partes de la app, fueron tomadas de proyectos echos en alura screenmatch.<br>
Me costo mucho hacer esta aplicacion por lo que esta demas decir que tuve ayuda de la IA en el proceso.
