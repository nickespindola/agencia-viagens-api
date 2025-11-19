# API de Agência de Viagens

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de destinos de viagem com autenticação e persistência em banco de dados.

## Descrição

Esta API foi desenvolvida para permitir que agências de viagens e parceiros integrem-se ao sistema de gerenciamento de destinos turísticos.  
A aplicação oferece funcionalidades completas para **cadastro, consulta, pesquisa, atualização, avaliação e exclusão** de destinos de viagem, com **autenticação e autorização** por perfil de usuário e **persistência em banco de dados PostgreSQL**.

## Tecnologias Utilizadas

- **Java 11**
- **Spring Boot 2.7.17**
- **Maven 3.6.3**
- **Spring Web** (para criação de APIs REST)
- **Spring Data JPA** (integração com banco de dados)
- **PostgreSQL** (banco de dados relacional)
- **Spring Security** (autenticação e autorização)
- **BCrypt** (criptografia de senhas)
- **Docker** (containerização do banco de dados)

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: Responsável pelos endpoints REST e manipulação de requisições HTTP  
- **Service**: Contém a lógica de negócios da aplicação  
- **Repository**: Camada de acesso a dados com Spring Data JPA
- **Model**: Define as entidades de dados  
- **Security**: Configurações de segurança e autenticação

### Estrutura de Diretórios

```
agencia-viagens-api/
├── src/
│   ├── main/
│   │   ├── java/com/agencia/viagens/
│   │   │   ├── controller/
│   │   │   │   ├── DestinoController.java
│   │   │   │   └── UsuarioController.java
│   │   │   ├── service/
│   │   │   │   ├── DestinoService.java
│   │   │   │   └── UsuarioService.java
│   │   │   ├── repository/
│   │   │   │   ├── DestinoRepository.java
│   │   │   │   └── UsuarioRepository.java
│   │   │   ├── model/
│   │   │   │   ├── Destino.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── AvaliacaoRequest.java
│   │   │   ├── security/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   └── ViagensApiApplication.java
│   │   └── resources/
│   │       └── application.properties
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## Segurança e Autenticação

### Perfis de Usuário

- **ADMIN**: Acesso completo a todos os recursos (criar, atualizar, excluir destinos e gerenciar usuários)
- **USER**: Pode criar destinos e avaliar, mas não pode atualizar ou excluir

### Regras de Autorização

| Endpoint | Método | Acesso Público | USER | ADMIN |
|----------|--------|----------------|------|-------|
| GET /destinos | GET | ✓ | ✓ | ✓ |
| GET /destinos/{id} | GET | ✓ | ✓ | ✓ |
| GET /destinos/pesquisar | GET | ✓ | ✓ | ✓ |
| POST /destinos | POST | ✗ | ✓ | ✓ |
| PATCH /destinos/{id}/avaliar | PATCH | ✗ | ✓ | ✓ |
| PUT /destinos/{id} | PUT | ✗ | ✗ | ✓ |
| DELETE /destinos/{id} | DELETE | ✗ | ✗ | ✓ |
| /usuarios/** | ALL | ✗ | ✗ | ✓ |

### Usuários Padrão

A aplicação cria automaticamente dois usuários para teste:

- **Admin**: username: `admin`, password: `admin123` (perfil: ADMIN)
- **User**: username: `user`, password: `user123` (perfil: USER)

---

## Funcionalidades

### 1. Cadastro de Destinos
Permite inserir novos destinos de viagem no sistema.

**Endpoint**: `POST /destinos`  
**Autenticação**: Requerida (USER ou ADMIN)

**Exemplo de Requisição**:
```json
{
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade luz, conhecida pela Torre Eiffel e museus famosos"
}
```

---

### 2. Listagem de Destinos
Retorna todos os destinos cadastrados.

**Endpoint**: `GET /destinos`  
**Autenticação**: Não requerida

---

### 3. Pesquisa de Destinos
Permite pesquisar destinos por nome ou localização.

**Endpoint**: `GET /destinos/pesquisar?termo={texto}`  
**Autenticação**: Não requerida

**Parâmetros**:
- `termo` (opcional): Texto para busca por nome ou localização

---

### 4. Visualização de Destino Específico
Retorna informações detalhadas sobre um destino específico.

**Endpoint**: `GET /destinos/{id}`  
**Autenticação**: Não requerida

---

### 5. Atualização de Destino
Permite atualizar as informações de um destino existente (nome, localização e descrição).  
Não altera os dados de avaliação já existentes.

**Endpoint**: `PUT /destinos/{id}`  
**Autenticação**: Requerida (apenas ADMIN)

**Exemplo de Requisição**:
```json
{
  "nome": "Paris (Atualizado)",
  "localizacao": "França",
  "descricao": "Cidade luz, famosa por sua arquitetura, gastronomia e museus."
}
```

**Exemplo de Resposta (200 OK)**:
```json
{
  "id": 1,
  "nome": "Paris (Atualizado)",
  "localizacao": "França",
  "descricao": "Cidade luz, famosa por sua arquitetura, gastronomia e museus.",
  "avaliacaoMedia": 8.6,
  "numeroAvaliacoes": 3
}
```

---

### 6. Avaliação de Destino
Permite avaliar um destino com nota de 1 a 10.  
O sistema calcula automaticamente a média das avaliações.

**Endpoint**: `PATCH /destinos/{id}/avaliar`  
**Autenticação**: Requerida (USER ou ADMIN)

**Exemplo de Requisição**:
```json
{
  "nota": 9
}
```

---

### 7. Exclusão de Destino
Remove um destino do sistema.

**Endpoint**: `DELETE /destinos/{id}`  
**Autenticação**: Requerida (apenas ADMIN)

---

### 8. Gerenciamento de Usuários

#### Cadastrar Usuário
**Endpoint**: `POST /usuarios`  
**Autenticação**: Requerida (apenas ADMIN)

**Exemplo de Requisição**:
```json
{
  "username": "novousuario",
  "password": "senha123",
  "roles": ["USER"]
}
```

#### Listar Usuários
**Endpoint**: `GET /usuarios`  
**Autenticação**: Requerida (apenas ADMIN)

#### Buscar Usuário por ID
**Endpoint**: `GET /usuarios/{id}`  
**Autenticação**: Requerida (apenas ADMIN)

#### Excluir Usuário
**Endpoint**: `DELETE /usuarios/{id}`  
**Autenticação**: Requerida (apenas ADMIN)

---

## Modelo de Dados

### Destino

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Identificador único do destino (gerado automaticamente) |
| nome | String | Nome do destino (obrigatório) |
| localizacao | String | Localização do destino (obrigatório) |
| descricao | String | Descrição detalhada |
| avaliacaoMedia | Double | Média das avaliações recebidas |
| numeroAvaliacoes | Integer | Quantidade de avaliações |

### Usuario

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Identificador único do usuário (gerado automaticamente) |
| username | String | Nome de usuário (único, obrigatório) |
| password | String | Senha criptografada (obrigatório) |
| enabled | Boolean | Status do usuário (ativo/inativo) |
| roles | Set<String> | Perfis de acesso (USER, ADMIN) |

---

## Como Executar

### Pré-requisitos

- Java 11 ou superior  
- Maven 3.6 ou superior
- Docker e Docker Compose

### Passo 1: Configurar o Banco de Dados com Docker

1. **Subir o PostgreSQL via Docker Compose:**
```bash
docker compose up -d
```

2. **Verificar se está rodando:**
```bash
docker ps
```

3. **Ver logs do PostgreSQL (opcional):**
```bash
docker compose logs -f postgres
```

### Passo 2: Conectar no DBeaver (Opcional)

Para visualizar os dados no DBeaver:

- **Host**: `localhost`
- **Port**: `5432`
- **Database**: `agencia_viagens`
- **Username**: `postgres`
- **Password**: `postgres123`

### Passo 3: Compilar e Executar a Aplicação

```bash
# Limpar e compilar
mvn clean compile

# Empacotar (gera o JAR)
mvn package

# Executar a aplicação
mvn spring-boot:run
```

A aplicação será iniciada na porta **8080**.

### Comandos Docker Úteis

```bash
# Parar o banco de dados
docker-compose down

# Parar e remover todos os dados
docker-compose down -v

# Reiniciar o banco
docker-compose restart
```

---

## Exemplos de Uso

### Cadastrar um Destino (com autenticação)
```bash
curl -X POST http://localhost:8080/destinos \
  -u user:user123 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rio de Janeiro",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa com praias e Cristo Redentor"
  }'
```

### Listar Todos os Destinos (sem autenticação)
```bash
curl -X GET http://localhost:8080/destinos
```

### Pesquisar Destinos
```bash
curl -X GET "http://localhost:8080/destinos/pesquisar?termo=Brasil"
```

### Buscar Destino por ID
```bash
curl -X GET http://localhost:8080/destinos/1
```

### Atualizar um Destino (apenas ADMIN)
```bash
curl -X PUT http://localhost:8080/destinos/1 \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rio de Janeiro (Atualizado)",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa, agora com novos pontos turísticos."
  }'
```

### Avaliar um Destino (com autenticação)
```bash
curl -X PATCH http://localhost:8080/destinos/1/avaliar \
  -u user:user123 \
  -H "Content-Type: application/json" \
  -d '{"nota": 9}'
```

### Excluir um Destino (apenas ADMIN)
```bash
curl -X DELETE http://localhost:8080/destinos/1 \
  -u admin:admin123
```

### Cadastrar Novo Usuário (apenas ADMIN)
```bash
curl -X POST http://localhost:8080/usuarios \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "novousuario",
    "password": "senha123",
    "roles": ["USER"]
  }'
```

### Listar Usuários (apenas ADMIN)
```bash
curl -X GET http://localhost:8080/usuarios \
  -u admin:admin123
```

---

## Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida  
- **201 Created**: Recurso criado com sucesso  
- **204 No Content**: Recurso excluído com sucesso  
- **400 Bad Request**: Requisição inválida (ex: nota fora do intervalo 1–10)
- **401 Unauthorized**: Autenticação necessária ou credenciais inválidas
- **403 Forbidden**: Acesso negado (permissão insuficiente para o recurso)
- **404 Not Found**: Recurso não encontrado  

---

## Validações

- **Avaliação**: Notas devem estar entre 1 e 10  
- **Cálculo de Média**: A média é recalculada automaticamente a cada nova avaliação  
- **Atualização**: Somente `nome`, `localizacao` e `descricao` podem ser alterados  
- **Pesquisa**: Case-insensitive, busca tanto no nome quanto na localização
- **Senhas**: Armazenadas com criptografia BCrypt (nunca em texto plano)
- **Username**: Deve ser único no sistema
- **Campos Obrigatórios**: Nome e localização são obrigatórios para destinos

---

## Critérios do Desafio Atendidos

✅ **Integrou com Spring Data JPA**  
- Dependências configuradas no `pom.xml`
- Repositories implementados

✅ **Configurou corretamente a integração**  
- `application.properties` com configurações do PostgreSQL
- Hibernate configurado para criação automática de tabelas

✅ **Implementou corretamente as entidades**  
- `Destino` com anotações JPA (`@Entity`, `@Table`, `@Id`, etc)
- `Usuario` com anotações JPA e relacionamentos

✅ **Implementou corretamente os Repositories**  
- `DestinoRepository` com query customizada para pesquisa
- `UsuarioRepository` com métodos de busca por username

✅ **Integrou corretamente o Spring Security**  
- Configuração completa de segurança
- Autenticação HTTP Basic

✅ **Configurou corretamente as permissões**  
- Perfis USER e ADMIN definidos
- Autorização por endpoint e método HTTP

✅ **Implementou autenticação via Banco de Dados**  
- `CustomUserDetailsService` busca usuários no PostgreSQL
- Senhas criptografadas com BCrypt
- Validação de credenciais contra o banco

---

## Observações

- A aplicação utiliza **PostgreSQL** rodando em container Docker para persistência de dados
- Senhas são criptografadas usando **BCrypt** (não são armazenadas em texto plano)
- Autenticação via **HTTP Basic Authentication**
- Usuários padrão são criados automaticamente na primeira execução
- As tabelas são criadas automaticamente pelo Hibernate (`spring.jpa.hibernate.ddl-auto=update`)
- O banco de dados persiste mesmo após parar a aplicação (dados ficam salvos no volume Docker)
- Logs SQL são exibidos no console para facilitar debug (`spring.jpa.show-sql=true`)

---

## Autor

Desenvolvido por mim, como parte de um Desafio na matéria de "Desenvolvimento de Sistemas Móveis e Distribuídos" da UniSENAI.

---

## Licença

Este projeto é de **uso educacional**.
