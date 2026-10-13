# Commerce Manager

A Commerce Manager é uma api para comércios especializados em vendas de dispositivos e serviços tecnológicos. O sistema de gestão de comércios é responsável por registrar e gerenciar clientes, produtos e pedidos.

## Tecnologias

- Java 17
- Spring Boot & Data JPA
- H2 Database

## `/api/produtos`

### `POST /api/produtos`

Requisição:

```json
{
	"produto": "Notebook Acer Aspire 5",
	"preco": 3.5,
	"estoque": true
}
```

Resposta:

```json
{
	"estoque": true,
	"id": 1,
	"preco": 3.5,
	"produto": "Notebook Acer Aspire 5"
}
```

### `GET /api/produtos/{id}`

```json
{
	"estoque": true,
	"id": 1,
	"preco": 3.5,
	"produto": "Notebook Acer Aspire 5"
}
```

### `GET /api/produtos

```json
{
	"message": "1 Produtos Listados",
	"produtos": [
		{
			"estoque": true,
			"id": 1,
			"preco": 3.5,
			"produto": "Notebook Acer Aspire 5"
		}
	]
}
```

### DELETE /api/produtos/{id}

```json
// delete/api/produtos/1    200 OK
{
	"success": "Produto deletado com sucesso"
}
```

## `/api/clientes`

### `POST /api/clientes`

Requisição:

```json
{
	"nome": "Lucas5285287"
}
```

Resposta:

```json
{
	"clienteDesde": "2026-10-12",
	"id": 1,
	"nome": "Lucas5285287"
}
```

### `GET /api/clientes/{id}`

```json
// /api/clientes/1
{
	"clienteDesde": "2026-10-12",
	"id": 1,
	"nome": "Lucas5285287"
}
```

### `GET /api/clientes`

```json
{
	"message": "1 Clientes Listados",
	"clientes": [
		{
			"clienteDesde": "2026-10-12",
			"id": 1,
			"nome": "Lucas5285287"
		}
	]
}
```

### `DELETE /api/clientes/{id}`

```json
// delete/api/clientes/1  200 OK
{
	"success": "Cliente deletado com sucesso"
}
```

## `/api/pedidos`

### `POST /api/pedidos`

Requisição:

```json
{
	"clienteId": { "id": 1 },
	"produtoId": { "id": 1 },
	"quantidade": 1
}
```

Resposta:

```json
{
	"clienteId": {
		"clienteDesde": "2026-10-12",
		"id": 1,
		"nome": "Lucas5285287"
	},
	"id": 1,
	"produtoId": {
		"estoque": true,
		"id": 1,
		"preco": 3.5,
		"produto": "Notebook Acer Aspire 5"
	},
	"quantidade": 1
}
```

### `GET /api/pedidos/{id}`

```json
{
	"clienteId": {
		"clienteDesde": "2026-10-12",
		"id": 1,
		"nome": "Lucas5285287"
	},
	"id": 1,
	"produtoId": {
		"estoque": true,
		"id": 1,
		"preco": 3.5,
		"produto": "Notebook Acer Aspire 5"
	},
	"quantidade": 1
}
```

### `GET /api/pedidos`

```json
{
	"message": "1 Pedidos Listados",
	"pedidos": [
		{
			"clienteId": {
				"clienteDesde": "2026-10-12",
				"id": 1,
				"nome": "Lucas5285287"
			},
			"id": 1,
			"produtoId": {
				"estoque": true,
				"id": 1,
				"preco": 3.5,
				"produto": "Notebook Acer Aspire 5"
			},
			"quantidade": 1
		}
	]
}
```

### DELETE /api/pedidos/{id}

```json
// delete/api/pedidos/2     200 OK
{
	"success": "Pedido deletado com sucesso"
}
```
