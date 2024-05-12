# W2mSpaceshipTask

**Repository for World 2 Meet tech task by Enrique Barca** 


## 1. How to run the application and tests.

### Run it without using the docker image:

- Once we have the code in our IDE, we have to configure in our ***IDE the
  run configuration, what kind of app it is, which file is the main class, etc***. Then we should be able to run the application,
  ***the DB will run in the port 3000*** we should keep that in mind.

#### Running the tests

- We can execute the command below from the root application folder to run all the tests in the app **We have to make sure
  that our JAVA_HOME variable is set to the 21 or greater version**.

```sh

    mvn clean install

```
### Run it with docker image

- We don't need to build our app image, the command is already in our docker-compose. So from the root of the application folder
  with the command below we should be able to create our app image, pull the h2 DB and RabbitMQ images.

```sh

     docker-compose up --build 

```

## 2. H2 database:

- The application is running in an in-memory database, as it was suggested in the requirements file, so the URL to visualize the console
  would be:

```

    http://localhost:3000/h2-console

```

- And, just in case, the values in the console itself should be:


JDBC URL:
```
  jdbc:h2:file:./src/main/resources/db/W2mTaskDb
```
```
  User Name: sa
  Password: (Empty field)
```

## 3. Postman collection:

- In this git repository, we should be able to see, inside the doc_utils folder in the project root, a JSON file called
  ```W2M-Enrique-Barca.postman_collection.json```. It contains all the endpoints, sending forms, params, pageable values, and
  security configuration. There is more info about the endpoints in the next section.

We only have to open Postman and import the file ```"Collections" tab > "Import" button```


## 4. Endpoints - Swagger Documentation:

- The application has a Swagger dependency so, when the program is running, we can visit the url below for reference if needed

```
    http://localhost:3000/swagger-ui/index.html
```

- Or we can copy the info in the file, inside the doc_utils folder in the project root, ```W2M-Swagger-doc.yaml```
  and go to https://editor.swagger.io/ to see the endpoints' documentation.


 #### Now a quick introduction to the endpoints and how they behave.

  | Name                      | URL                    | What it does                                                                                                                  |
  |---------------------------|------------------------|-------------------------------------------------------------------------------------------------------------------------------|
  | Save Spaceship            | /spaceship/save        | Add a new Spaceship record.                                                                                                   |
  | Update Spaceship          | /spaceship/update/{id} | Update, if found by id, only the Spaceship values (Not the source).                                                           |
  | Delete spaceship          | /spaceship/remove/{id} | Remove, if found by id, the Spaceship.                                                                                        |
  | Get a Spaceships by ID   | /spaceship/{id}        | Return, if found by id, a Spaceship.                                                                                           |
  | Get a Spaceships by name | /spaceship/byName      | Return one or more Spaceships that has a name like it.                                                                         |
  | Update Source             | /source/update/{id}     | Update, if found by id, the source object, SERIES or FILM.                                                                   |
  | Show all Spaceships images | /spaceship/showSpaceships      | Send a request message to the RabbitMq producer with a DTO meant to be consumed by a front end service for intance   |


## 5. Test some required features:

### Security:

> All the endpoints are already set to pass the security (api-key), we can test if it is working just going to Postman >
> "Authorization" tab > "Auth Type" list and choose any other, then we just have to execute the endpoint, and we should get a 400 code error.

### @Aspect:

> To see if the Aspect created is trigger when we get a negative input id we can, in any endpoint that has an id as requirement,
> send a negative, or zero. We should be able to see the message "WARNING: Attempt to find spaceship with an invalid id: {id}" in the app logs

### DDL Scrips:

> We can see how Flyway, DB version controller, keep the records of these changes in the DB, so if we go to http://localhost:3000/h2-console,
> with the app running, we should be able to see the "flyway_schema_history" table with these records.

### Messaging broker:

> When we execute the endpoint Show all Spaceships images (/spaceship/showSpaceships) we receive as response, in Postman, a DTO object
> with the name and the image url of the spaceship, that way, so we can see what we are sending to the queue, but to see that rabbit 
> is receiving the message from the producer we can go, with the app running, to http://localhost:15672/#/queues and after execute the endpoint
> check 1) If the queues are being created and 2) if we are getting the messages.

> P.S: The dead.letter.queue is just a good practice to avoid lose messages when the queue is down. 