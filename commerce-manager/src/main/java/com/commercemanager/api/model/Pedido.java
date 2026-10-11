package com.commercemanager.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")

public class Pedido {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "clienteId") private Long clienteId;

    @Column(name = "produtoId") private Long produtoId;

    @Column(name = "quantidade") private Integer quantidade;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clientId) {
        this.clienteId = clientId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
