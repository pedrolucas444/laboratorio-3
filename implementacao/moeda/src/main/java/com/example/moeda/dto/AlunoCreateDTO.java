package com.example.moeda.dto;

public class AlunoCreateDTO {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private int saldo;
    private String endereco;
    private String rg;
    private Long instituicaoId;
    private Long cursoId;
    
    // Getters and Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public int getSaldo() { return saldo; }
    public void setSaldo(int saldo) { this.saldo = saldo; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public Long getInstituicaoId() { return instituicaoId; }
    public void setInstituicaoId(Long instituicaoId) { this.instituicaoId = instituicaoId; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
}