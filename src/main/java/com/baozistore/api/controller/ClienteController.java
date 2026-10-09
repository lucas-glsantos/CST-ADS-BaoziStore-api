package com.baozistore.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;

import java.util.*;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    @Autowired

    private ClienteRepository repository;

    // CREATE: Salvar um Novo Cliente
    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    // READ: Listar Todos os Clientes
    @GetMapping
    public ResponseEntity<Object> listarTodos() {

        List<Cliente> clientes = repository.findAll();

        // Validação com Early Return Se a lista estiver vazia
        if (clientes.isEmpty()) {
            // Nota: Em APIs REST estritas, uma lista vazia geralmente retorna 200 OK com [].
            // Porém, para exibir a mensagem solicitada, usamos 404 (ou 200, dependendo da sua regra de negócio).
            return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Nenhum cliente encontrado"));
        }

        // Se existirem clientes, cria a mensagem dinâmica com a quantidade
        return ResponseEntity.ok(Map.of(
            "clientes", clientes,
            "message", clientes.size() + " Clientes Listados"
        ));
    }

    // READ: Buscar um Cliente específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = repository.findById(id);

        // Valida se o ID existe
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Cliente não encontrado"));
        }

        // Se ID existe, extrai o objeto e retorna o ID do Cliente
        Cliente cliente = clienteOptional.get();
        return ResponseEntity.ok(cliente);
    }

    // UPDATE: Atualizar dados de um Cliente Existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody(required = false) Cliente clienteAtualizado) {
        Optional<Cliente> clienteOptional = repository.findById(id);

        // Valida se o ID existe
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Cliente não encontrado"));
        }

        // Valida se o body veio vazio
        if (clienteAtualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Cliente não atualizado. Dados não enviados no corpo da requisição"));
        }

        // Se ID existe e o body Enviado com Sucesso, prossegue com a Atualização
        Cliente clienteExistente = clienteOptional.get();
        clienteExistente.setNome(clienteAtualizado.getNome());

        Cliente atualizado = repository.save(clienteExistente);
        return ResponseEntity.ok(Map.of(
            "success", "Cliente atualizado",
            "cliente", atualizado
        ));
    }

    // DELETE: Remover um CLiente
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        // Valida se o ID existe
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Cliente não encontrado"));
        }

        // Se o ID existe, executa exclusão diretamente
        repository.deleteById(id);

        return ResponseEntity.ok(Map.of("success", "Cliente deletado com sucesso"));
    }
}
