# Spring-Boot-Docker
This spring boot application contains following
1. H2 database to store values
2. flyway data migration
3. com.spotify/docker-maven-plugin used to build docker images locally.
 
### Environment Setup
Install docker toolbox

[`https://www.docker.com/products/docker-toolbox`](https://www.docker.com/products/docker-toolbox)

### Maven Install
https://fabianlee.org/2018/05/24/docker-running-a-spring-boot-based-app-using-docker-compose/
Maven command to build the docker image.
 docker-compose build
Validate the build with:

$docker images

docker-compose up

``` 
git clone https://github.com/Arun4D/spring-boot-docker.git
mvn clean install
docker build -t spring-boot-docker:latest -f Dockerfile .
docker run --name spring-boot-docker-v1  -e "SPRING_PROFILES_ACTIVE=default" -e "SERVER.PORT=10000" -p 10000:10000 -t spring-boot-docker:v1
```

 > Note: Run `docker-machine env default` in the cmd / bash to identify the docker.
 
### H2 database Configuration

* The default h2 console url to run this spring boot application in local environment

    ````
    http://localhost:10000/console/
    ````
    > Note: Port is based on SERVER.PORT configuration. Default spring boot server port 8080


* If this application running in docker. 

    ````
    $ docker-machine  ip default
    192.168.99.100
    
    http://192.168.99.100:10000/console/
    ````
    > Note: Assumed that `default` docker machine  is used. If different docker machine used use `docker-machine  ip <docker-machine name>`

* Use the following details to connect to the database.

    ````
    JDBC URL    : jdbc:h2:mem:PersonDB
    User Name   : sa
    password    :
    ````
### Run Application

Open the browser and hit the following url to invoke the service.
````
http://192.168.99.100:10000/person?firstName=arun&lastName=duraisamy
````

