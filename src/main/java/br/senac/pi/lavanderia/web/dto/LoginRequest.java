package br.senac.pi.lavanderia.web.dto;

public class LoginRequest {
    private String cpf;
    private String matricula;

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
