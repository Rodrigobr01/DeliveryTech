# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias
- **Java 21 LTS** (versão mais recente)
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## ⚡ Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## 🏃‍♂️ Como executar
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8080/health

## 📋 Endpoints
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações da aplicação
- GET /h2-console - Console do banco H2
- GET /api/restaurants
- POST /api/restaurants
- PATCH /api/restaurants/{id}/status?active=true
- GET /api/restaurants/{id}/menu-items
- POST /api/restaurants/{id}/menu-items
- GET /api/customers
- POST /api/customers
- GET /api/orders
- GET /api/orders/{id}
- POST /api/orders

## 🔧 Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development

## 👨‍💻 Desenvolvedor
DeliveryTech Team

Desenvolvido com JDK 21 e Spring Boot 3.2.x
