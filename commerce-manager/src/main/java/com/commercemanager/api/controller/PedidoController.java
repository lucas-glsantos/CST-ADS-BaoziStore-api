package com.commercemanager.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.commercemanager.api.model.Pedido;
import com.commercemanager.api.repository.PedidoRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")

public class PedidoController {

    @Autowired

    private PedidoRepository repository;

    // CREATE: Salvar um Novo Pedido
    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return repository.save(pedido);
    }

    // READ: Listar Todos os Pedidos
    @GetMapping
    public ResponseEntity<Object> listarTodos() {
        List<Pedido> pedidos = repository.findAll();

        // Validação com Early Return Se lista de pedidos Vazia
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Nenhum pedido encontrado"));
        }

        // Se existirem pedidos. retorna Lista de pedidos
        return ResponseEntity.ok(Map.of(
            "pedidos", pedidos,
            "message", pedidos.size() + " Pedidos Listados"
        ));
    }

    // READ: Buscar um Pedido específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = repository.findById(id);

        // Valida se o ID existe
        if (pedidoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Pedido não encontrado"));
        }

        // Se ID existe, extrai o objeto e retorna o ID do Pedido
        Pedido pedido = pedidoOptional.get();
        return ResponseEntity.ok(pedido);
    }

    // UPDATE: Atualizar dados de um Pedido Existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody(required = false) Pedido pedidoAtualizado) {
        Optional<Pedido> pedidoOptional = repository.findById(id);

        // Valida se o ID existe
        if (pedidoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Pedido não encontrado"));
        }

        // Valida se o body veio vazio
        if (pedidoAtualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Pedido não atualizado. Dados não enviados no corpo da requisição"));
        }

        // Se ID existe e o body Enviado com Sucesso, prossegue com a Atualização
        Pedido pedidoExistente = pedidoOptional.get();
        pedidoExistente.setClienteId(pedidoAtualizado.getClienteId());
        pedidoExistente.setProdutoId(pedidoAtualizado.getProdutoId());
        pedidoExistente.setQuantidade(pedidoAtualizado.getQuantidade());

        Pedido atualizado = repository.save(pedidoExistente);
        return ResponseEntity.ok(Map.of(
            "success", "Pedido atualizado",
            "pedido", atualizado
        ));
    }

    // DELETE: Remover um Pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        // Valida se o ID existe
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Pedido não encontrado"));
        }

        // Se o ID existe, executa exclusão diretamente
        repository.deleteById(id);

        return ResponseEntity.ok(Map.of("success", "Pedido deletado com sucesso"));
    }
}
