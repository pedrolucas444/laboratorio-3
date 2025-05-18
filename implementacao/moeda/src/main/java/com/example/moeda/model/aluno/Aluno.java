package com.example.moeda.model.aluno;

import com.example.moeda.model.pessoa.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.moeda.model.curso.Curso;
import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Aluno extends Pessoa {
    @Column(nullable = false)
    private String endereco;
    
    @Column(nullable = false, unique = true)
    private String rg;
    
    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Curso curso;

    // Getters e Setters
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}