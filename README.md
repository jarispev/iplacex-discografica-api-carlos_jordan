# Discografía - API REST

## 1. Descripción del proyecto

Proyecto desarrollado para la **Evaluación de la Unidad II** de la asignatura **Programación de Servicios Web** de IPLACEX.

El objetivo es implementar una API REST que permita administrar artistas musicales y sus discos, utilizando Java, Spring Boot y MongoDB Atlas.

La aplicación permite registrar, consultar, actualizar y eliminar artistas, además de registrar y consultar discos asociados a cada artista.

## 2. Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **Spring Boot 3.3.2:** framework para el desarrollo de la aplicación.
- **Spring Web:** implementación de los servicios REST.
- **Spring Data MongoDB:** integración con la base de datos.
- **MongoDB Atlas:** almacenamiento de información en la nube.
- **Gradle:** administración de dependencias, ejecución y empaquetado del proyecto.
- **Visual Studio Code:** entorno de desarrollo.
- **Postman:** pruebas de los endpoints de la API.

## 3. Estructura del proyecto

El proyecto está organizado en dos módulos principales: `artistas` y `discos`.

```text
discografia/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/iplacex/discografia/api/
│   │   │       ├── DiscografiaApplication.java
│   │   │       ├── artistas/
│   │   │       │   ├── Artista.java
│   │   │       │   ├── IArtistaRepository.java
│   │   │       │   └── ArtistaController.java
│   │   │       └── discos/
│   │   │           ├── Disco.java
│   │   │           ├── IDiscoRepository.java
│   │   │           └── DiscoController.java
│   │   └── resources/
│   │       ├── application.properties
│   └── test/
│       └── java/
│           └── org/iplacex/discografia/api/
│               └── DiscografiaApplicationTests.java
├── build/
│   └── libs/
│       └── discografia-1.war
├── build.gradle
├── settings.gradle
└── README.md
```

La carpeta `build/` y su archivo WAR se generan al compilar el proyecto.

## 4. Gestión de artistas

El módulo de artistas permite registrar, consultar, actualizar y eliminar artistas musicales.

La clase `Artista` contiene los siguientes atributos:

| Atributo | Tipo | Descripción |
|---|---|---|
| `_id` | `String` | Identificador único del artista |
| `nombre` | `String` | Nombre del artista |
| `estilos` | `List<String>` | Estilos musicales |
| `anioFundacion` | `int` | Año de fundación |
| `estaActivo` | `boolean` | Indica si el artista está activo |

### Endpoints de artistas

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| POST | `/api/artista` | Registrar un artista |
| GET | `/api/artistas` | Consultar todos los artistas |
| GET | `/api/artista/{id}` | Consultar un artista por ID |
| PUT | `/api/artista/{id}` | Actualizar un artista |
| DELETE | `/api/artista/{id}` | Eliminar un artista |

## 5. Gestión de discos

El módulo de discos permite registrar y consultar discos musicales.

Cada disco se encuentra asociado a un artista mediante el atributo `idArtista`.

La clase `Disco` contiene los siguientes atributos:

| Atributo | Tipo | Descripción |
|---|---|---|
| `_id` | `String` | Identificador único del disco |
| `idArtista` | `String` | Identificador del artista asociado |
| `nombre` | `String` | Nombre del disco |
| `anioLanzamiento` | `int` | Año de lanzamiento |
| `canciones` | `List<String>` | Lista de canciones |

### Endpoints de discos

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| POST | `/api/disco` | Registrar un disco |
| GET | `/api/discos` | Consultar todos los discos |
| GET | `/api/disco/{id}` | Consultar un disco por ID |
| GET | `/api/artista/{id}/discos` | Consultar los discos de un artista |

### Validación de artistas

Antes de registrar un disco, la aplicación verifica que el artista indicado en `idArtista` exista en la base de datos.

Si el artista no existe, la API responde con el código HTTP `404 Not Found`. Esto evita registrar discos asociados a artistas inexistentes.

## 6. Base de datos MongoDB Atlas

La aplicación utiliza MongoDB Atlas como servicio de base de datos NoSQL en la nube.

**Configuración utilizada:**

- Base de datos: `discografica-db`.
- Colección de artistas: `artistas`.
- Colección de discos: `discos`.

La conexión se configura mediante el archivo:

`src/main/resources/application.properties`

La aplicación utiliza Spring Data MongoDB para realizar las operaciones de persistencia. Los repositorios implementan `MongoRepository`, permitiendo crear, consultar, actualizar y eliminar documentos.

Adicionalmente, el repositorio de discos implementa el método `findDiscosByIdArtista`, utilizando la anotación `@Query` para consultar los discos asociados a un artista.

## 7. Ejecución del proyecto

La aplicación puede ejecutarse desde Visual Studio Code utilizando la opción **Run Java**, tal como se realiza durante las actividades prácticas de la asignatura.

Como alternativa, en este proyecto se utilizó el siguiente comando desde la terminal, ubicada en la carpeta principal:

```bash
gradle bootRun
```

### ¿Por qué se utiliza `gradle bootRun`?

El comando `gradle bootRun` permite ejecutar la aplicación Spring Boot utilizando la configuración y las dependencias definidas en `build.gradle`.

Se optó por esta modalidad porque permite iniciar el proyecto directamente desde la terminal mediante una tarea estándar de Gradle, sin depender de la opción de ejecución del editor.

**Ambos métodos permiten iniciar la misma aplicación**, mediante la clase principal `DiscografiaApplication`, que contiene el método `main()` y la anotación `@SpringBootApplication`.

Por lo tanto, utilizar `gradle bootRun` no modifica la lógica de la aplicación ni el funcionamiento de los servicios REST.

### Dirección de acceso

Una vez iniciada la aplicación, el servidor queda disponible en:

`http://localhost:8080`

Los endpoints REST utilizan el prefijo:

`http://localhost:8080/api`

Por ejemplo, para consultar los artistas registrados:

```http
GET http://localhost:8080/api/artistas
```

**Importante:** para ejecutar correctamente el proyecto es necesario disponer de una conexión válida a MongoDB Atlas configurada en `application.properties`.

## 8. Pruebas realizadas con Postman

Para verificar el funcionamiento de la API REST se realizaron pruebas utilizando Postman.

### Pruebas del módulo de artistas

- Registro de artistas mediante POST.
- Consulta de todos los artistas mediante GET.
- Consulta de un artista por su identificador.
- Actualización de los datos de un artista mediante PUT.
- Eliminación de un artista mediante DELETE.
- Consulta de identificadores inexistentes.

### Pruebas del módulo de discos

- Registro de discos asociados a artistas existentes.
- Consulta de todos los discos registrados.
- Consulta de discos por identificador.
- Consulta de discos asociados a un artista específico.
- Intento de registro de un disco asociado a un artista inexistente.

### Códigos HTTP verificados

| Código | Descripción | Aplicación |
|---|---|---|
| `200 OK` | Solicitud procesada correctamente | Consultas y actualizaciones exitosas |
| `201 Created` | Recurso creado correctamente | Registro de artistas y discos |
| `404 Not Found` | Recurso no encontrado | Identificadores inexistentes y validación de artistas |

Las pruebas permitieron comprobar el funcionamiento de los endpoints y la comunicación con MongoDB Atlas.

## 9. Consideraciones técnicas

- La aplicación utiliza arquitectura REST.
- Los controladores utilizan `@RestController` y anotaciones de mapeo HTTP.
- Las respuestas HTTP se gestionan mediante `ResponseEntity`.
- Los identificadores se reciben mediante `@PathVariable`.
- Los datos enviados en las solicitudes se reciben mediante `@RequestBody`.
- Las clases de modelo utilizan `@Document` y `@Id` para el mapeo de documentos MongoDB.
- La persistencia de información se realiza mediante repositorios de Spring Data MongoDB.
- La consulta de discos por artista utiliza `@Query`.
- La aplicación se ejecuta localmente en el puerto `8080`.

## 10. Generación y ejecución del archivo WAR

Como parte de los requisitos de la evaluación, se generó un archivo WAR (*Web Application Archive*) que contiene la aplicación Spring Boot compilada y las dependencias necesarias para su ejecución.

### Configuración de Gradle

Para habilitar la generación del archivo WAR, se agregó el plugin `war` en el archivo `build.gradle`:

```gradle
plugins {
    id 'java'
    id 'war'
    id 'org.springframework.boot' version '3.3.2'
    id 'io.spring.dependency-management' version '1.1.7'
}
```

Esta configuración permite generar un archivo WAR ejecutable, manteniendo el funcionamiento de los controladores REST, los repositorios y la conexión con MongoDB Atlas.

### Comando utilizado para generar el WAR

Desde la terminal de Visual Studio Code, ubicada en la carpeta principal del proyecto, se ejecutó:

```bash
gradle clean bootWar
```

Este comando realiza dos operaciones:

- **`clean`:** elimina los archivos generados por compilaciones anteriores.
- **`bootWar`:** compila y empaqueta la aplicación Spring Boot en un archivo WAR ejecutable.

### Resultado de la compilación

La generación del archivo finalizó correctamente:

```text
BUILD SUCCESSFUL in 12s
5 actionable tasks: 5 executed
```

El archivo generado se encuentra en:

```text
build/libs/discografia-1.war
```

### Ejecución y verificación del WAR

Para comprobar el funcionamiento de la aplicación empaquetada, se ejecutó el siguiente comando:

```bash
java -jar build/libs/discografia-1.war
```

La aplicación inició correctamente utilizando Java 21 y Spring Boot 3.3.2. Los registros de ejecución confirmaron el inicio del servidor Tomcat en el puerto `8080` y la conexión con MongoDB Atlas.

Además, se realizó una solicitud desde Postman al endpoint:

```http
GET http://localhost:8080/api/artistas
```

La solicitud respondió con el código **`200 OK`**, confirmando que la API REST también funciona cuando se ejecuta desde el archivo WAR.

Para ejecutar el WAR se requiere Java 21 y una configuración válida de conexión a MongoDB Atlas.

### Diferencia entre `bootRun` y `bootWar`

- **`gradle bootRun`:** ejecuta la aplicación directamente desde el proyecto, principalmente durante el desarrollo y las pruebas.
- **`gradle bootWar`:** genera el archivo WAR compilado para su distribución y ejecución.
- **`java -jar build/libs/discografia-1.war`:** ejecuta la aplicación previamente empaquetada.

Los tres comandos corresponden a la misma aplicación Spring Boot y no requieren modificar la lógica de los servicios REST.

## 11. Alcance del proyecto

El proyecto contempla el desarrollo y las pruebas de una API REST para la gestión de artistas y discos musicales, utilizando Spring Boot y MongoDB Atlas.

La aplicación fue ejecutada localmente tanto mediante Gradle como desde el archivo WAR generado. Sus servicios fueron verificados mediante Postman.

El despliegue de la aplicación en un servidor cloud no forma parte del alcance de esta evaluación. MongoDB Atlas se utiliza únicamente como servicio de base de datos en la nube.
