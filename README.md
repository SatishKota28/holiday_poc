This is a poc application with the CRUD operation for Holidays.

It is build using java openJDK-21 and spring boot version 3.4.1 using maven build with h2 database and openAPI.
So to run the application locally the system should have jdk-21 installed.



To run the application locally just import the project from the git hub and it can be started directly as java application.

The application is currently running on port 8081. So make sure the port is free in your system.

port can be changed from application.properties file.

Additional Information: 

This is using h2 database
- username: user 
- password: password

h2 database console login url - http://localhost:8081/h2-console
use same credentials as above to login to database

Swagger is implemented for API documentation

Swagger url: http://localhost:8081/swagger-ui/index.html

For excel upload sample excel file in present @ src/main/resource/static/holidays.xlsx






