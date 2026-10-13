package com.commercemanager.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")

public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "clienteId") private Cliente clienteId;

    @ManyToOne(optional = false) @JoinColumn(name = "produtoId") private Produto produtoId;

    @JoinColumn(name = "quantidade") private Integer quantidade;

    public Pedido() {
        // Construtor Vazio
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clientId) {
        this.clienteId = clientId;
    }

    public Produto getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Produto produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
