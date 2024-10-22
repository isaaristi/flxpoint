In order to configure the embedded database, the following must be done:
Run the Spring Boot application.
Open a browser and go to http://localhost:8080/h2-console.
Configure the access:
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: (leave empty)
Click on ‘Connect’.

In order to see the documentation of the application endpoints you can run the application and go to the URL: http://localhost:8080/swagger-ui/index.html

My approach to completing this challenge was to create a detailed layered structure where we have: 
1. The DATA layer: This layer contains the entity and classes that we are going to manage in the project.
2. The REPOSITORY layer: This layer is going to help us to create the CRUD with JPA to be able to do all the functionalities that are needed.
3. The SERVICE layer: This layer contains the business logic and contains the interface and the implementation of the necessary methods.
4. The WEB layer: This is where we have our controller and where we have our services exposed and ready to be consumed.

To save the clients I used a H2 database embedded in the application that whenever it is uploaded will start from zero and for the CRM I used a map that will save the data I need.
We also did unit tests of the controller and the services that we have in the application.
