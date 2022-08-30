# User WS Challenge
This folder contains my solution to the technest challenge
## Application description
A simple REST API for to administer user accounts. The application connects to a MySQL database with a table, Users, which contains the user account information

## Execution instructions
### Option A: manual execution
In order to manually execute the application, MySQL server 8 must be running on localhost, port 3306
1. Run the sql script in the file dbScript.sql to create a sample database (with no data)
2. Open the project in the folder challenge_jmp 
3. Execute the application in the project
4. The web server will be listening on port 8085
### Option B: dockerized execution
In order to run the dockerized application, Docker must be installed in your local environment
1. In a terminal or console, navigate to the project folder
2. Execute the command docker-compose up -d
3. The server will be listening on port 8085

## Operation
* The file technest-swagger.yaml provides the API documentation
* The basic authentication credentials are:
    - username: "admin"
    - password: "admin123"