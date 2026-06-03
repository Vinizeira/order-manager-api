package dev.projectx.order_manager_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String nome;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable=false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario() {}
    public Usuario(String nome, String email, String senha, Role role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    private static String validarEmail(String email) {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        return email;

    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public long getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }
}

