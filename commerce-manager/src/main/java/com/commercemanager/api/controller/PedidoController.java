package com.commercemanager.api.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.commercemanager.api.model.Cliente;
import com.commercemanager.api.model.Pedido;
import com.commercemanager.api.model.Produto;
import com.commercemanager.api.repository.ClienteRepository;
import com.commercemanager.api.repository.PedidoRepository;
import com.commercemanager.api.repository.ProdutoRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")

public class PedidoController {
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
        ClienteRepository clienteRepository,
        PedidoRepository pedidoRepository,
        ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    // CREATE: Salvar um Novo Pedido
    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Cliente cliente = pedido.getClienteId();
        Produto produto = pedido.getProdutoId();

        if (
            cliente == null || cliente.getId() == null || produto == null || produto.getId() == null
        ) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Cliente> clienteDb = clienteRepository.findById(cliente.getId());
        Optional<Produto> produtoDb = produtoRepository.findById(produto.getId());

        if (clienteDb.isEmpty() || produtoDb.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        pedido.setClienteId(clienteDb.get());
        pedido.setProdutoId(produtoDb.get());

        var salvo = pedidoRepository.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ: Listar Todos os Pedidos
    @GetMapping
    public ResponseEntity<Object> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

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
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

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
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

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

        Pedido atualizado = pedidoRepository.save(pedidoExistente);
        return ResponseEntity.ok(Map.of(
            "success", "Pedido atualizado",
            "pedido", atualizado
        ));
    }

    // DELETE: Remover um Pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        // Valida se o ID existe
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Pedido não encontrado"));
        }

        // Se o ID existe, executa exclusão diretamente
        pedidoRepository.deleteById(id);

        return ResponseEntity.ok(Map.of("success", "Pedido deletado com sucesso"));
    }
}
