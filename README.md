# prime-generator

A simple restful webservice based prime generator.

Presently support three implemenations

1) Trial by division

2) Eratosthenes Sieve 

3) Parallel version of Eratosthenes Sieve 



# Building the project

Requires Java 1.8 and maven to build this project


1) Clone the project to your local directory

2) Go inside the local directory and execute below command

        mvn clean package


# Running the application

After building the project execute the below command

        java -jar  target/prime-generator-1.0-SNAPSHOT.jar

This should start the spring boot application.

# Testing the application

Project presently expose below rest endpoints

1) Get all the supported prime generation algorithms

        http://localhost:8080/generator/algorithms 

2) Get all prime number upto 'n' using TRIAL_DIVISION algorithm

        http://localhost:8080/generator/prime?algorithm=TRIAL_DIVISION&ceiling=n

3) Get all prime number upto 'n' using ERATOSTHENES_SIEVE_SEQUENTIAL algorithm

        http://localhost:8080/generator/prime?algorithm=ERATOSTHENES_SIEVE_SEQUENTIAL&ceiling=100 

4) Get all prime number upto 'n' using ERATOSTHENES_SIEVE_PARALLEL algorithm

        http://localhost:8080/generator/prime?algorithm=ERATOSTHENES_SIEVE_PARALLEL&ceiling=100 





