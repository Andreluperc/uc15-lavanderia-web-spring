# UC15 Lavanderia - Web (Spring Boot REST + Front-end)

Projeto final do PI: integração das camadas refatoradas (regras de negócio) com uma aplicação web.
- Back-end: Spring Boot (REST) + JPA (H2 em memória por padrão)
- Front-end: HTML/CSS/JS (páginas servidas pelo próprio Spring em `src/main/resources/static`)

## Como executar
No terminal, dentro da pasta do projeto:

```bash
mvn spring-boot:run
```

Depois acesse:
- Login: http://localhost:8080/login.html
- H2 Console: http://localhost:8080/h2 (JDBC URL: jdbc:h2:mem:lavanderia)

## Usuário de teste (seed)
- CPF: 123.456.789-00
- Matrícula: OP123

## API (principais endpoints)
- GET/POST: `/api/clientes`
- PUT/DELETE: `/api/clientes/{id}`
- GET/POST: `/api/operadores`
- PUT/DELETE: `/api/operadores/{id}`
- POST: `/api/operadores/login` (cpf + matricula)

## Testes
```bash
mvn test
```

Data: 23/12/2025
