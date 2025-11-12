# Relatório de Testes - API de Agência de Viagens

## Resumo
Todos os endpoints foram testados com sucesso. A API está funcionando conforme especificado.

## Testes Realizados

### 1. Cadastro de Destinos (POST /destinos)
**Status**: ✅ Aprovado

**Teste**: Cadastrar novo destino "Paris"
```bash
POST http://localhost:8080/destinos
Content-Type: application/json

{
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade luz, conhecida pela Torre Eiffel e museus famosos"
}
```

**Resultado**: HTTP 201 Created
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade luz, conhecida pela Torre Eiffel e museus famosos",
  "avaliacaoMedia": 0.0,
  "numeroAvaliacoes": 0
}
```

### 2. Listagem de Destinos (GET /destinos)
**Status**: ✅ Aprovado

**Teste**: Listar todos os destinos cadastrados
```bash
GET http://localhost:8080/destinos
```

**Resultado**: HTTP 200 OK - Retornou lista com 4 destinos cadastrados

### 3. Pesquisa de Destinos (GET /destinos/pesquisar)
**Status**: ✅ Aprovado

**Teste 1**: Pesquisar por nome "Rio"
```bash
GET http://localhost:8080/destinos/pesquisar?termo=Rio
```
**Resultado**: HTTP 200 OK - Encontrou "Rio de Janeiro"

**Teste 2**: Pesquisar por localização "Brasil"
```bash
GET http://localhost:8080/destinos/pesquisar?termo=Brasil
```
**Resultado**: HTTP 200 OK - Encontrou "Rio de Janeiro"

### 4. Visualização de Destino Específico (GET /destinos/{id})
**Status**: ✅ Aprovado

**Teste 1**: Buscar destino existente (ID 1)
```bash
GET http://localhost:8080/destinos/1
```
**Resultado**: HTTP 200 OK - Retornou detalhes de Paris

**Teste 2**: Buscar destino inexistente (ID 999)
```bash
GET http://localhost:8080/destinos/999
```
**Resultado**: HTTP 404 Not Found

### 5. Avaliação de Destino (PATCH /destinos/{id}/avaliar)
**Status**: ✅ Aprovado

**Teste 1**: Adicionar primeira avaliação (nota 9)
```bash
PATCH http://localhost:8080/destinos/1/avaliar
Content-Type: application/json

{"nota": 9}
```
**Resultado**: HTTP 200 OK
- avaliacaoMedia: 9.0
- numeroAvaliacoes: 1

**Teste 2**: Adicionar segunda avaliação (nota 7)
**Resultado**: HTTP 200 OK
- avaliacaoMedia: 8.0 (média de 9 e 7)
- numeroAvaliacoes: 2

**Teste 3**: Adicionar terceira avaliação (nota 10)
**Resultado**: HTTP 200 OK
- avaliacaoMedia: 8.666666666666666 (média de 9, 7 e 10)
- numeroAvaliacoes: 3

**Teste 4**: Validação - nota inválida (0)
```bash
PATCH http://localhost:8080/destinos/1/avaliar
Content-Type: application/json

{"nota": 0}
```
**Resultado**: HTTP 400 Bad Request

**Teste 5**: Validação - nota inválida (11)
```bash
PATCH http://localhost:8080/destinos/1/avaliar
Content-Type: application/json

{"nota": 11}
```
**Resultado**: HTTP 400 Bad Request

### 6. Exclusão de Destino (DELETE /destinos/{id})
**Status**: ✅ Aprovado

**Teste 1**: Excluir destino existente (ID 4 - Nova York)
```bash
DELETE http://localhost:8080/destinos/4
```
**Resultado**: HTTP 204 No Content - Destino removido com sucesso

**Teste 2**: Excluir destino inexistente (ID 999)
```bash
DELETE http://localhost:8080/destinos/999
```
**Resultado**: HTTP 404 Not Found

## Validações Implementadas

1. **Validação de Avaliação**: Notas devem estar entre 1 e 10
2. **Cálculo de Média**: A média é calculada corretamente com base em todas as avaliações
3. **Tratamento de Erros**: Retorna códigos HTTP apropriados (404, 400, etc.)
4. **Pesquisa Case-Insensitive**: Busca funciona independente de maiúsculas/minúsculas

## Conclusão

Todos os 6 endpoints especificados foram implementados e testados com sucesso:
- ✅ POST /destinos - Cadastro de destinos
- ✅ GET /destinos - Listagem de destinos
- ✅ GET /destinos/pesquisar - Pesquisa de destinos
- ✅ GET /destinos/{id} - Visualização de destino específico
- ✅ PATCH /destinos/{id}/avaliar - Avaliação de destino
- ✅ DELETE /destinos/{id} - Exclusão de destino

A API está pronta para uso e atende a todos os requisitos especificados.
