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
   API or even a Queue

# How to compile the code



1. Clone the project in your local machine
```bash
   git clone https://github.com/nuwe-reports/655c6de1c8d6b92ebc7b9431.git
```

2. Build the project
```bash
  mvn clean package
```

3. Run locally, be careful tu use the .env file
```bash
  mvn springboot:run

```
# Testing the application deployed in LocalHost

* [Search first price](http://localhost:8080/prices?appDate='2020-06-14 10:00:00'&brandId=1&productId=35455)
* [Insert A price]()
