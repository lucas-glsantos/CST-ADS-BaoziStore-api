package com.commercemanager.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.commercemanager.api.model.Produto;
import com.commercemanager.api.repository.ProdutoRepository;

import java.util.*;

@RestController
@RequestMapping("/api/produtos")

public class ProdutoController {

    @Autowired

    private ProdutoRepository repository;

    // CREATE: Salvar um Novo Produto
    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    // READ: Listar Todos os Produtos
    @GetMapping
    public ResponseEntity<Object> listarTodos() {
        List<Produto> produtos = repository.findAll();

        // Validação com Early Return se Lista de Produtos Vazia
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Nenhum produto encontrado"));
        }

        // Se existirem produtos, retorna Lista de Produtos
        return ResponseEntity.ok(Map.of(
            "produtos", produtos,
            "message", produtos.size() + " Produtos Listados"
        ));
    }

    // READ: Buscar um Produto específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produtoOptional = repository.findById(id);

        // Valida se o ID do Produto existe
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Produto não encontrado"));
        }

        // Se ID existe, extrai o objeto e retorna o ID do Produto
        Produto produto = produtoOptional.get();
        return ResponseEntity.ok(produto);
    }

    // UPDATE: Atualizar dados de um Produto Existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody(required = false) Produto produtoAtualizado) {
        Optional<Produto> produtoOptional = repository.findById(id);

        // Valida se o ID existe
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Produto não encontrado"));
        }

        // Valida se o body veio vazio
        if (produtoAtualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Produto não atualizado, Dados não enviados no corpo da requisição"));
        }

        // Se ID existe e o body Enviado com Sucesso, prossegue com a Atualização
        Produto produtoExistente = produtoOptional.get();
        produtoExistente.setProduto(produtoAtualizado.getProduto());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setEstoque(produtoAtualizado.getEstoque());

        Produto atualizado = repository.save(produtoExistente);
        return ResponseEntity.ok(Map.of(
            "success", "Produto atualizado",
            "produto", atualizado
        ));
    }

    // DELETE: Remover um Produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        // Valida se o ID existe
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Produto não encontrado"));
        }

        // Se o ID existe, executa exclusão diretamente
        repository.deleteById(id);

        return ResponseEntity.ok(Map.of("success", "Produto deletado com sucesso"));

    }
}