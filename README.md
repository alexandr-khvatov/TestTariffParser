Build
=======
To build and run this application locally, you'll need Git and Docker installed on your computer.


Docker instructions:

```
# Clone this repository
$ git clone https://github.com/alexandr-khvatov/TestTariffParser.git

# Go into the repository
$ cd TestTariffParser

# Build and run container
$ docker-compose --env-file .env up

```

Use: 

Get tariffs:

http://localhost:8080/tariffs

Parse:

http://localhost:8080/tariffs/parse