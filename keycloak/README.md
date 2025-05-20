## Run Keycloak

```shell
docker run -p 8081:8080 \
    -e KEYCLOAK_ADMIN=admin \
    -e KEYCLOAK_ADMIN_PASSWORD=admin \
    -v ./realm-export.json:/opt/keycloak/data/import/realm-export.json \
    quay.io/keycloak/keycloak:24.0.4 \
    start-dev --import-realm
```

## Obtain token

```shell
curl -X POST "http://localhost:8081/realms/demo-realm/protocol/openid-connect/token" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "username=user1" \
     -d "password=password" \
     -d "grant_type=password" \
     -d "client_id=demo-client"
```

## Use token

```shell
curl localhost:8080/cars -H "Authorization: Bearer <access_token>"
```
