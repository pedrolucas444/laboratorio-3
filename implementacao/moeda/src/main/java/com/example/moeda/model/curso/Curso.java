package com.example.moeda.model.curso;

import com.example.moeda.model.departamento.Departamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    @JsonIgnoreProperties("cursos") 
    private Departamento departamento;

    // Construtores
    public Curso() {}

    public Curso(String nome, String codigo, Departamento departamento) {
        this.nome = nome;
        this.codigo = codigo;
        this.departamento = departamento;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}