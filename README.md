## Running the application

```shell
mvn clean spring-boot:run
```

## Swagger-ui

`http://localhost:8080/swagger-ui/index.html`

## cURL

```shell
curl localhost:8081/cars -X POST -H "Content-type: application/json" --data "{\"name\":\"Škoda\"}"
```