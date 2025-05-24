package com.example.moeda.dto;

public class CursoDTO {
    private Long id;
    private String nome;
    private String codigo;
    private String departamentoNome;
    private Long departamentoId;
    
    public CursoDTO() {}
    
    public CursoDTO(Long id, String nome, String codigo, String departamentoNome, Long departamentoId) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.departamentoNome = departamentoNome;
        this.departamentoId = departamentoId;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDepartamentoNome() { return departamentoNome; }
    public void setDepartamentoNome(String departamentoNome) { this.departamentoNome = departamentoNome; }
    public Long getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(Long departamentoId) { this.departamentoId = departamentoId; }
}