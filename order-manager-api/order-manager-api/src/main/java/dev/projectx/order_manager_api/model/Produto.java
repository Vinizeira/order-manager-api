package dev.projectx.order_manager_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Produto() {
    }

    public Produto(String nome, BigDecimal preco, Integer estoque, Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
}