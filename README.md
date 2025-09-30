# API AgendaCultural

API RESTful para a plataforma **AgendaCultural**, que conecta produtores de eventos culturais ao seu público.  
O sistema permite que produtores gerenciem seus eventos e que consumidores descubram e reservem ingressos de forma segura e prática.

---

## Funcionalidades Principais

### Segurança e Autenticação
- Autenticação baseada em **JWT (JSON Web Token)**.
- Autorização por **roles**: `CONSUMIDOR` e `PRODUTOR`, utilizando Spring Security 6.
- Endpoints públicos para cadastro e login.

### Fluxo do Produtor (`ROLE_PRODUTOR`)
- Criar novos eventos (**POST** `/api/eventos`)
- Editar eventos existentes (**PUT** `/api/eventos/{id}`)
- Adicionar sessões a um evento (**POST** `/api/eventos/{id}/sessoes`)
- Definir tipos de ingressos por sessão (**POST** `/api/sessoes/{id}/tipos-ingresso`)
- Cancelar sessões (**DELETE** `/api/sessoes/{id}`)
- Consultar os eventos do próprio produtor (**GET** `/api/me/eventos`)

### Fluxo do Consumidor e Público
- Consultar catálogo público de eventos (**GET** `/api/eventos`)
- Visualizar detalhes de um evento (**GET** `/api/eventos/{id}`)
- Realizar checkout de ingressos com validação de estoque (**POST** `/api/sessoes/{id}/checkout`)

---

## Documentação
- API totalmente documentada com **Swagger/OpenAPI**.  
- Explore e teste todos os endpoints interativamente:
  - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Tecnologias Utilizadas

**Linguagem e Frameworks**
- Java 21
- Spring Boot 3.5.x

**Persistência**
- Spring Data JPA (Hibernate)
- PostgreSQL (Docker)

**Segurança**
- Spring Security 6
- JWT (biblioteca `java-jwt`)
- Hash de senhas com **BCrypt**

**Build & Dependências**
- Maven

**Infraestrutura**
- Docker
- Docker Compose

**Produtividade**
- Springdoc OpenAPI (Swagger UI)
- Lombok

---

## Como Executar a API

### Pré-requisitos
- Java **JDK 21+**
- Maven
- Docker & Docker Compose

---

### Passo 1: Iniciar o Banco de Dados
Na raiz do projeto, execute:

```bash
docker-compose up -d
```

---

### Passo 2: Executar a Aplicação
1. Abra o projeto no IntelliJ/VSCode.
2. Rode a classe principal: `ApiApplication.java`.

#### Pelo Terminal
Na raiz do projeto:

```bash
mvnw.cmd spring-boot:run
```
---

### Passo 3: Acessar e Testar a API
Após a aplicação iniciar, os seguintes recursos estarão disponíveis:

Base URL:
http://localhost:8080

Swagger UI (documentação interativa):
http://localhost:8080/swagger-ui.html

---

### Autenticação

Use o endpoint de login para obter o token JWT:
```bash
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@teste.com",
  "password": "senha123"
}
```
```bash
Resposta esperada:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

### Acesso a endpoints protegidos

Inclua o token no header Authorization:

GET /api/me/eventos
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR...

### Observações

Endpoints públicos: /auth/**, /login, /eventos (consulta).

Endpoints protegidos: exigem JWT válido.

Recomenda-se usar o Swagger UI ou ferramentas como Postman/Insomnia para testar a API.

---
