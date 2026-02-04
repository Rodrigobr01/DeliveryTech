# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## Tecnologias
- Java 21 LTS (versao mais recente)
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## Como executar
1. Pre-requisitos: JDK 21 instalado
2. Clone o repositorio
3. Execute: ./mvnw spring-boot:run
4. Acesse: http://localhost:8080/health

## Endpoints
- GET /health - Status da aplicacao (inclui versao Java)
- GET /info - Informacoes da aplicacao
- GET /h2-console - Console do banco H2
- API Base: /api
- Clientes: /api/clientes
- Restaurantes: /api/restaurantes
- Produtos: /api/produtos
- Pedidos: /api/pedidos

## Configuracao
- Porta: 8080
- Banco: H2 em memoria
- Profile: development

## Desenvolvedor
RodrigoBrizola - Arquitetura de Sistemas  
Desenvolvido com JDK 21 e Spring Boot 3.2.x
