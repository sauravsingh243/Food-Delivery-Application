### No additional package used in the services, following version of pakages used: 
#Ubuntu : 20.04 , openjdk 11.0.13 used, Apache Maven 3.6.3, Docker version 20.10.7

###Replace the ~/Downloads/initialData.txt with location of initialData.txt from local file system
###Test folder contains 6 test cases. (just run python3 file_name.py)
#---------------------------------------PHASE 2--------------------------------------------------------------------------------------
### There are 2 shell scripts. One for run the project and one for teardown.












#-----------------------------------------PHASE 1------------------------------------------------------------------------------------
# Restaurant Service

### Compile the project and generate the JAR file
./mvnw package

### Build the docker image
docker build -t restaurant-service .

### Run the docker image
docker run -p 8080:8080 --rm --name restaurant --add-host=host.docker.internal:host-gateway -v ~/Downloads/initialData.txt:/initialData.txt restaurant-service
# Notes about the -add-host option above:
# 1. This option is not explicitly required with Windows and Mac. It is
#    explicitly required with Linux.
# 2. Each Spring service can contact http://host.docker.internal:8080,
#    http://host.docker.internal:8081,  and http://host.docker.internal:8082,
#    respectively, to reach the three services, respectively. The way it
#    works is that at runtime Docker replaces "host.docker.internal" with the
#    IP address of the localhost that started all the containers.
#    Separately, the -p commands forward localhost:8080, localhost:8081,
#    and localhost:8082 to the respective services.

### Stop the container
docker stop restaurant

### Remove the docker image
docker image rm restaurant-service


# Delivery Service

### Compile the project and generate the JAR file
./mvnw package

### Build the docker image
docker build -t delivery-service .

### Build the docker image
docker run -p 8081:8080 --rm --name delivery --add-host=host.docker.internal:host-gateway -v ~/Downloads/initialData.txt:/initialData.txt delivery-service

### Stop the container
docker stop delivery

### Remove the docker image
docker image rm delivery-service


# Wallet Service

### Compile the project and generate the JAR file
./mvnw package

### Build the docker image
docker build -t wallet-service .

### Build the docker image
docker run -p 8082:8080 --rm --name wallet --add-host=host.docker.internal:host-gateway -v ~/Downloads/initialData.txt:/initialData.txt wallet-service

### Stop the container
docker stop wallet

### Remove the docker image
docker image rm wallet-service
