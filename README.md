# API de Agência de Viagens

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de destinos de viagem.

## Descrição

Esta API foi desenvolvida para permitir que agências de viagens e parceiros integrem-se ao sistema de gerenciamento de destinos turísticos.  
A aplicação oferece funcionalidades completas para **cadastro, consulta, pesquisa, atualização, avaliação e exclusão** de destinos de viagem.

## Tecnologias Utilizadas

- **Java 11**
- **Spring Boot 2.7.17**
- **Maven 3.6.3**
- **Spring Web** (para criação de APIs REST)

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: Responsável pelos endpoints REST e manipulação de requisições HTTP  
- **Service**: Contém a lógica de negócios da aplicação  
- **Model**: Define as entidades de dados  

### Estrutura de Diretórios

```
agencia-viagens-api/
├── src/
│   ├── main/
│   │   ├── java/com/agencia/viagens/
│   │   │   ├── controller/
│   │   │   │   └── DestinoController.java
│   │   │   ├── service/
│   │   │   │   └── DestinoService.java
│   │   │   ├── model/
│   │   │   │   ├── Destino.java
│   │   │   │   └── AvaliacaoRequest.java
│   │   │   └── ViagensApiApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

---

## Funcionalidades

### 1. Cadastro de Destinos
Permite inserir novos destinos de viagem no sistema.

**Endpoint**: `POST /destinos`

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

---

### 3. Pesquisa de Destinos
Permite pesquisar destinos por nome ou localização.

**Endpoint**: `GET /destinos/pesquisar?termo={texto}`

**Parâmetros**:
- `termo` (opcional): Texto para busca por nome ou localização

---

### 4. Visualização de Destino Específico
Retorna informações detalhadas sobre um destino específico.

**Endpoint**: `GET /destinos/{id}`

---

### 5. Atualização de Destino
Permite atualizar as informações de um destino existente (nome, localização e descrição).  
Não altera os dados de avaliação já existentes.

**Endpoint**: `PUT /destinos/{id}`

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

---

## Modelo de Dados

### Destino

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Identificador único do destino |
| nome | String | Nome do destino |
| localizacao | String | Localização do destino |
| descricao | String | Descrição detalhada |
| avaliacaoMedia | Double | Média das avaliações recebidas |
| numeroAvaliacoes | Integer | Quantidade de avaliações |

---

## Como Executar

### Pré-requisitos

- Java 11 ou superior  
- Maven 3.6 ou superior  

### Compilação
```bash
mvn clean compile
```

### Empacotamento
```bash
mvn package
```

### Execução
```bash
java -jar target/viagens-api-1.0.0.jar
```

A aplicação será iniciada na porta **8080**.

---

## Exemplos de Uso

### Cadastrar um Destino
```bash
curl -X POST http://localhost:8080/destinos   -H "Content-Type: application/json"   -d '{
    "nome": "Rio de Janeiro",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa com praias e Cristo Redentor"
  }'
```

### Listar Todos os Destinos
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

### Atualizar um Destino
```bash
curl -X PUT http://localhost:8080/destinos/1   -H "Content-Type: application/json"   -d '{
    "nome": "Rio de Janeiro (Atualizado)",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa, agora com novos pontos turísticos."
  }'
```

### Avaliar um Destino
```bash
curl -X PATCH http://localhost:8080/destinos/1/avaliar   -H "Content-Type: application/json"   -d '{"nota": 9}'
```

### Excluir um Destino
```bash
curl -X DELETE http://localhost:8080/destinos/1
```

---

## Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida  
- **201 Created**: Recurso criado com sucesso  
- **204 No Content**: Recurso excluído com sucesso  
- **400 Bad Request**: Requisição inválida (ex: nota fora do intervalo 1–10)  
- **404 Not Found**: Recurso não encontrado  

---

## Validações

- **Avaliação**: Notas devem estar entre 1 e 10  
- **Cálculo de Média**: A média é recalculada automaticamente a cada nova avaliação  
- **Atualização**: Somente `nome`, `localizacao` e `descricao` podem ser alterados  
- **Pesquisa**: Case-insensitive, busca tanto no nome quanto na localização  

---

## Observações

- A aplicação utiliza **armazenamento em memória** (sem persistência em banco de dados)  
- Os dados são perdidos ao reiniciar a aplicação  
- Não há autenticação ou segurança implementada (conforme especificação do desafio)  

---

## Autor

Desenvolvido como parte de um Desafio na matéria de "Desenvolvimento de Sistemas Móveis e Distribuídos" da UniSENAI.

---

## Licença

Este projeto é de **uso educacional**.
