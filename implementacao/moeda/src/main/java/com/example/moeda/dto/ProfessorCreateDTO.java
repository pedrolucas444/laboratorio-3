package com.example.moeda.dto;

public class ProfessorCreateDTO {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    //private String matricula;
    private Long instituicaoId;  
    private Long departamentoId;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
   // public String getMatricula() { return matricula; }
   // public void setMatricula(String matricula) { this.matricula = matricula; }
    public Long getInstituicaoId() { return instituicaoId; }
    public void setInstituicaoId(Long instituicaoId) { this.instituicaoId = instituicaoId; }
    public Long getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(Long departamentoId) { this.departamentoId = departamentoId; }
}