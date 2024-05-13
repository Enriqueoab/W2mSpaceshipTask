# World 2 Meet Spaceship Task

**Repositorio para la prueba técnica World 2 Meet de Enrique Barca**

[![en](https://img.shields.io/badge/lang-English-green.svg)](./README.md)

## 1. Cómo ejecutar la aplicación y las pruebas.

### Ejecútalo sin usar la imagen de la ventana acoplable:

- Una vez que tenemos el código en nuestro IDE, tenemos que configurar en nuestro ***IDE el
  ejecutar la configuración, qué tipo de aplicación es, qué archivo es la clase principal, etc.***. Entonces deberíamos poder ejecutar la aplicación,
  ***la base de datos se ejecutará en el puerto 3000*** debemos tenerlo en cuenta.

#### Ejecutando las pruebas

- Podemos ejecutar el siguiente comando desde la carpeta raíz de la aplicación para ejecutar todas las pruebas en la aplicación **Tenemos que asegurarnos
  que nuestra variable JAVA_HOME esté configurada en la versión 21 o superior**.

```sh

    mvn clean install

```
### Ejecútelo con la imagen de la ventana acoplable

- No necesitamos crear la imagen de nuestra aplicación, el comando ya está en nuestra ventana acoplable. Entonces desde la raíz de la carpeta de la aplicación.
  Con el siguiente comando deberíamos poder crear la imagen de nuestra aplicación, extraer las imágenes h2 DB y RabbitMQ.

```sh

     docker-compose up --build 

```

## 2. H2 database:

- La aplicación se ejecuta en una base de datos en memoria, como se sugirió en el archivo de requisitos, por lo que la URL para visualizar la consola
  sería:

```

    http://localhost:3000/h2-console

```

- Y, por si acaso, los valores en la propia consola deberían ser:


JDBC URL:
```
  jdbc:h2:file:./src/main/resources/db/W2mTaskDb
```
```
  User Name: sa
  Password: (Empty field)
```

## 3. Postman collection:

- En este repositorio de git, deberíamos poder ver, dentro de la carpeta doc_utils en la raíz del proyecto, un archivo JSON llamado
  ```W2M-Enrique-Barca.postman_collection.json```. Contiene todos los puntos finales, formularios de envío, parámetros, valores paginables y
  configuración de seguridad. Hay más información sobre los puntos finales en la siguiente sección.

Sólo nos queda abrir Postman e importar el archivo ```"Collections" tab > "Import" button```


## 4. Endpoints - Swagger Documentation:

- La aplicación tiene una dependencia de Swagger, por lo que, cuando el programa se está ejecutando, podemos visitar la siguiente URL como referencia si es necesario.

```
    http://localhost:3000/swagger-ui/index.html
```

- O podemos copiar la información en el archivo, dentro de la carpeta doc_utils en la raíz del proyecto, ```W2M-Swagger-doc.yaml```
  e ir a https://editor.swagger.io/ para ver la documentación de los puntos finales.


 #### Ahora una rápida introducción a los puntos finales y cómo se comportan.

  | Nombre                     | URL                    | Que función tiene                                                                                                              |
  |----------------------------|------------------------|--------------------------------------------------------------------------------------------------------------------------------|
  | Save Spaceship             | /spaceship/save        | Añade un nuevo Spaceship.                                                                                                      |
  | Update Spaceship           | /spaceship/update/{id} | Actualiza, si es encontrado por id, solo los valores de Spaceship (El obj source no se actializa aquí).                        |
  | Delete spaceship           | /spaceship/remove/{id} | Elimina, si es encontrado por id, el Spaceship.                                                                                |
  | Get a Spaceships by ID     | /spaceship/{id}        | Devuelve, si es encontrado por id, a Spaceship.                                                                                |
  | Get a Spaceships by name   | /spaceship/byName      | Devuelve uno o más objetos Spaceships si hay más de uno con nombres similares.                                                 |
  | Update Source              | /source/update/{id}     | Actualiza, si es encontrado por id, el objeto source , SERIES o FILM.                                                          |
  | Show all Spaceships images | /spaceship/showSpaceships      | Envía un mensaje de solicitud al productor RabbitMq con un DTO destinado a ser consumido por un servicio front-end por ejemplo |


## 5. Testear algunas funciones requeridas:

### Seguridad:

> Todos los puntos finales ya están configurados para pasar la seguridad (clave de API), podemos probar si está funcionando simplemente yendo a Postman >
> pestaña "Autorización" > lista "Tipo de autenticación" y elegir cualquier otro, luego solo tenemos que ejecutar el punto final y deberíamos obtener un error de código 400.

### @Aspecto:

> Para ver si el Aspecto creado se activa cuando obtenemos una identificación de entrada negativa, podemos, en cualquier punto final que tenga una identificación como requisito,
> enviar un negativo o cero. Deberíamos poder ver el mensaje "ADVERTENCIA: Intento de encontrar una nave espacial con una identificación no válida: {id}" en los registros de la aplicación.

### Scripts DDL:

> Podemos ver como Flyway, controlador de versión de BD, mantiene los registros de estos cambios en la BD, así que si vamos a http://localhost:3000/h2-console,
> con la aplicación ejecutándose, deberíamos poder ver la tabla "flyway_schema_history" con estos registros.

### Agente de mensajería:

> Cuando ejecutamos el endpoint Mostrar todas las imágenes de naves espaciales (/spaceship/showSpaceships) recibimos como respuesta, en Postman, un objeto DTO
> con el nombre y la url de la imagen de la nave espacial, de esa manera, así podemos ver lo que estamos enviando a la cola, pero para ver ese conejo
> está recibiendo el mensaje del productor, podemos ir, con la aplicación en ejecución, a http://localhost:15672/#/queues y luego ejecutar el punto final
> marque 1) Si se están creando las colas y 2) si estamos recibiendo los mensajes.

> P.D: Dead.letter.queue es solo una buena práctica para evitar perder mensajes cuando la cola no funciona.