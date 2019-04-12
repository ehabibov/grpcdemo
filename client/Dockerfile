FROM java:8
WORKDIR /jar
COPY ["target/client-1.0-SNAPSHOT-jwd.jar", "/jar/client.jar"]
ENTRYPOINT java -jar client.jar