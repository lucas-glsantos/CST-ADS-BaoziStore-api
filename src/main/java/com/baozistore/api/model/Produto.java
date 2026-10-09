package com.baozistore.api.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")

public class Produto {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "produto", nullable = false, length = 100) private String produto;

    @Column(name = "preco", nullable = false, unique = false) private BigDecimal preco;

    @Column(name = "estoque", nullable = false) private Boolean estoque;

    public Produto() {
        // Construtor Vazio
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getEstoque() {
        return estoque;
    }

    public void setEstoque(Boolean estoque) {
        this.estoque = estoque;
    }
}
