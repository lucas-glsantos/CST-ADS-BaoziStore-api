package com.commercemanager.api.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")

public class Cliente {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "nome", nullable = false, length = 100) private String nome;

    @CreationTimestamp

    @Column(name = "cliente_desde", nullable = false) private LocalDate clienteDesde;

    public Cliente() {
        // Construtor Vazio
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getClienteDesde() {
        return clienteDesde;
    }

    public void setClienteDesde(LocalDate clienteDesde) {
        this.clienteDesde = clienteDesde;
    }
}
