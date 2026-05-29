package dev.projectx.order_manager_api.model;


import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf; // 000.000.000-00

    public static String limparcpf(String cpf){
    return cpf.replaceAll("\\D", "");
    }

    public static String formatarCpf(String cpf){
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private static String limparTelefone(String telefone) {
        return telefone.replaceAll("\\D", "");
    }

    private static String formatarTelefone(String telefone) {
        return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
    }

    private static String validarEmail(String email) {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        return email.toLowerCase();
    }

    public Cliente() {}

    public Cliente(String nome, String email, String telefone, String cpf) {
        this.nome = nome;
        this.email = validarEmail(email);
        this.telefone = limparTelefone(telefone);
        this.cpf = limparcpf(cpf);
    }


    public String getCpf() {
        return cpf;
    }
    public String getCpfFormatado() {
        return formatarCpf(this.cpf);
    }

    public String getTelefone() {
        return telefone;
    }
    public String getTelefoneFormatado() {
        return formatarTelefone(this.telefone);
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
}
