package com.example.moeda.model.aluno;

import com.example.moeda.model.pessoa.Pessoa;
import com.example.moeda.model.vantagem.Vantagem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

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
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Curso curso;

    @ManyToMany
    @JoinTable(name = "aluno_vantagem", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "vantagem_id"))
    private List<Vantagem> vantagens;

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

    public List<Vantagem> getVantagens() {
        return vantagens;
    }

    public void setVantagens(List<Vantagem> vantagens) {
        this.vantagens = vantagens;
    }
}