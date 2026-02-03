# DeliveryTech

API de delivery construída com Spring Boot.

## Como executar

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080` com banco H2 em memória.

## Endpoints principais

### Restaurantes
- `GET /api/restaurants`
- `POST /api/restaurants`
- `PATCH /api/restaurants/{id}/status?active=true`
- `GET /api/restaurants/{id}/menu-items`
- `POST /api/restaurants/{id}/menu-items`

### Clientes
- `GET /api/customers`
- `POST /api/customers`

### Pedidos
- `GET /api/orders`
- `GET /api/orders/{id}`
- `POST /api/orders`

## Console H2

`/h2-console` com URL `jdbc:h2:mem:deliverydb`.
