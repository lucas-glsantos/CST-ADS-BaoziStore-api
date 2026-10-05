package com.baozistore.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;

import java.util.List;

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
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    // READ: Buscar um Cliente específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
            .map(cliente -> ResponseEntity.ok().body(cliente))
            .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE: Atualizar dados de um Cliente Existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return repository.findById(id)
            .map(clienteExistente -> {
                clienteExistente.setNome(clienteAtualizado.getNome());
                clienteExistente.setEmail(clienteAtualizado.getEmail());
                clienteExistente.setTelefone(clienteAtualizado.getTelefone());

                Cliente atualizado = repository.save(clienteExistente);

                return ResponseEntity.ok().body(atualizado);
            }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remover um CLiente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return repository.findById(id)
            .map(clienteExistente -> {
                repository.delete(clienteExistente);

                return ResponseEntity.ok().<Void>build();
            }).orElse(ResponseEntity.notFound().build());
    }
}
