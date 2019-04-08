FROM java:8
WORKDIR /jar
COPY ["target/server-1.0-SNAPSHOT-jwd.jar", "/jar/server.jar"]
ENTRYPOINT java -jar server.jar $port