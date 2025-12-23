package br.senac.pi.lavanderia.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "operadores", uniqueConstraints = {
        @UniqueConstraint(name="uk_operador_matricula", columnNames = "matricula")
})
public class Operador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nome;

    @Column(nullable=false, length=11)
    private String cpf; // normalizado

    @Column(nullable=false)
    private String matricula;

    @Column(nullable=false)
    private String perfil; // ADMIN, OPERADOR, USUARIO (string por simplicidade)

    public Operador() {}

    public Operador(String nome, String cpf, String matricula, String perfil) {
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.perfil = perfil;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}
