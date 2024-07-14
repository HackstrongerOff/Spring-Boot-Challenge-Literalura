<div align="center">
<h1 align="center">
    Literalura Challenge
<br/>
<br/>
  </h1>
</div>

# Spring Boot:


# Proyecto de Consulta de Libros

Este proyecto es una aplicación en Java que interactúa con la API "Gutendex" para buscar información sobre libros basándose en su título. Los datos obtenidos se almacenan en una base de datos PostgreSQL para su posterior consulta y gestión. La aplicación proporciona varias funcionalidades tanto para interactuar con la API como para manipular y consultar los datos almacenados en la base de datos.

## Funcionalidades

### Funciones Basadas en la API

1. **Añadir libro por Título**:
    - Esta función permite al usuario buscar un libro específico por su título utilizando la API "Nombre API".
    - Una vez que el libro es encontrado, se guarda la información relevante del libro en la base de datos PostgreSQL.
    - Esto incluye detalles como el título, autor, fecha de publicación, idioma, y cualquier otra información proporcionada por la API.

### Funciones Basadas en la Base de Datos

Una vez que los datos se han almacenado en la base de datos, la aplicación permite realizar varias operaciones de consulta y gestión sobre los mismos:

1. **Búsqueda de libro por título**:
    - Permite al usuario buscar y recuperar un libro específico almacenado en la base de datos utilizando el título del libro como criterio de búsqueda.

2. **Búsqueda de autor por nombre**:
    - Permite al usuario buscar y recuperar información sobre un autor específico almacenado en la base de datos utilizando el nombre del autor como criterio de búsqueda.

3. **Listar libros guardados**:
    - Devuelve una lista completa de todos los libros que han sido almacenados en la base de datos.
    - Esta lista incluye detalles como el título del libro, el autor, la fecha de publicación, el idioma, entre otros.

4. **Lista de autores**:
    - Devuelve una lista de todos los autores cuyos libros han sido almacenados en la base de datos.
    - Esta lista incluye el nombre de los autores y puede incluir otros detalles como su nacionalidad y fecha de nacimiento si están disponibles.

5. **Listar autores vivos por año**:
    - Permite al usuario obtener una lista de autores que estaban vivos en un año específico.
    - Esto es útil para análisis históricos o estudios literarios.

6. **Listar libros por idioma**:
    - Permite al usuario obtener una lista de todos los libros almacenados en la base de datos que están escritos en un idioma específico.

## Requisitos

- **Java 11** o superior
- **PostgreSQL 13** o superior
- **Maven 3.6.3** o superior

## Configuración

1. **Clonar el repositorio**:
    ```bash
    git clone https://github.com/tu_usuario/tu_proyecto.git
    cd tu_proyecto
    ```

2. **Configurar la base de datos PostgreSQL**:
    - Crear una nueva base de datos en PostgreSQL.
    - Actualizar las credenciales y la URL de la base de datos en el archivo `application.properties` para que coincidan con tu configuración de PostgreSQL.

3. **Compilar y ejecutar el proyecto**:
    ```bash
    mvn clean install
    java -jar target/tu_proyecto-1.0.jar
    ```

## Uso

### Añadir libro por Título
Para añadir un libro por su título utilizando la API, se puede hacer una solicitud POST como se muestra a continuación:
```bash
curl -X POST http://localhost:8080/api/libros -d '{"titulo": "Nombre del Libro"}' -H "Content-Type: application/json"
```

## IDES/ INSTALACIÓN O REQUERIMIENTOS
- MAVEN
- LIBRERIAS SPRING - JPA - POSTGRESDB
- INTELIJ IDEA
- P0


##  Tecnologías usadas
| Tecnología | Versión | Descripción                                                                     |
|------------|---------|---------------------------------------------------------------------------------|
|SPRINGBOOT       | 3.1.5       | Lenguaje para la logica y consumo api. |


# Spring-Boot-Challenge-Literalura
