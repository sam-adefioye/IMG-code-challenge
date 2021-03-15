# IMG-code-challenge

To run code:

```
java -jar target/rest-service-0.0.1.jar
```

If you use Maven, you can run the application by using `./mvnw spring-boot:run` or `mvn spring-boot:run`. Alternatively, you can build the JAR file with `./mvnw clean package` or `mvn clean package` and then run the JAR file, using the above command.

To run tests: `./mvnw test` or `mvn test`.

All accessible endpoints (from localhost:8080):
```
/list-by-customer-id/{id}
```

```
/list-by-licence/{licence}
```

```
/list-all-by-licences?licences=X,Y,Z
```
