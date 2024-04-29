# Api in Hexagonal Architecture

 A project to handle Api operation using Hexagonal architecture
and good code practices

# Architecture

The code is based using a hexagonal architecture, were the business logic is developed
using a Domain Design in the inner core. The business logic is developed in the inner
core.

There are tree main packages in the structure


1. Domain: Is present the entity to be developed
2. Application: Apply services and uses input and output port that communicates the
   domain layer with infrastructure layer
3. Infrastructure: Layer that apply the specific technology, this have inputs and outputs
   in the input appears the controller that catch Http request and goes through the core and
   finally ends in the output to connect to any DB or even  send the information to another
   API or even a Queue.

![Image Alt text](/images/hex.png "Hexagola Architecure"))

# How to compile the code



1. Clone the project in your local machine
```bash
   git clone https://github.com/georve/CrudHexagonal.git
```

2. Build the project
```bash
  mvn clean package
```

3. Run locally, be careful tu use the .env file
```bash
  mvn springboot:run

#  How to accesses to Database local

* [local database] (http://localhost:8080/h2-console/login.jsp?jsessionid=623120439a315467e236f485bebd2247)
  user: sa
  pass: sa
  
  inside the console you can perform any sql query to load register and see if they change.
```

# Operations in the API
1. Insert A price to the list: 
   This procedure is performed using a post to the following url
   http://localhost:8080/prices
   using the following JSON
   ```javascript
   {
        "brandId": 1,
        "startDate": "2024-06-16 00:00:00",
        "endDate": "2024-06-26 23:59:59",
        "productId": 35455,
        "price": 80.0,
        "countryCode":"EURO"
    }
   ```

2. Update a price available in the list:
   Perform a patch over the api url  http://localhost:8080/prices/{id}
   where id is the row number that want to be changed
      
```javascript
   {
        "brandId": 1,
        "startDate": "2024-06-16 00:00:00",
        "endDate": "2024-06-26 23:59:59",
        "productId": 35455,
        "price": 80.0,
        "countryCode":"EURO"
    }
```

# Iterate over the database local
 As the project is built using H2. The console is available to login
 in the localhost url:
 http://localhost:8080/h2-console/

User: sa
Pass: sa

![Image Alt text](/images/H2console.png "H2 console"))
 
# automatic integration test

To test 5 uses cases related to be used in the automatic deployment
a docker file was added to ejecute testing

execute 
```bash
  docker-compose up
```

# Postman Testing 

 there is a folder called /integrationtest in which there is a collection and an environment variable
 that can be imported in postman and when the api is deployed locally can be executed the collection
 and see how many test are success. These script could be uses in ci/cd action to identify if the 
 project is ok.
 
# Pasos y decisiones tomadas
1 . Pruebas con postman y docker: Se desarrollo un proceso de pruebs automatizadac con docker
    usando docker compose, por lo que se agregaron las pruebas en un docker corriendo con newman
    pero al momento de ejecutar las  pruebas, estas corrian antes que el contenedor de spring boot
    este arriba y todas caen por conexion, sin embargo se coloco un health check pero de igual manera
    no arrancaron.

2. Opcion de correr pruebas en el pipeline de github. Esta opcion es una alternativa para los docker
   y no se usa los contenedores y con las opciones pagas de postman se puede generar un api key para
   conectar postman con github y que permite al hacer un pull correr todas las pruebas de integracion
   con el H2 para ver si las funcionalidades no se hayan roto.

3. Se ha incorporado con action en el repo que hace el build automatico para ver si el codigo
   compila y se puede agregar tareas para generar docker y hacer despliegues

# Cambios para mejorar la funcionalidades

1. Cambiar api rest por reactiva para tener actualiza las tecnologias.
2. Incluir en el pileline sonar para que los pull request esten asociados a 
   la calidad.
3. Incluir pruebas integrales en el pileline para ver que las pruenas funcionales
   pasesn  y no se rompan las funcionalidades,