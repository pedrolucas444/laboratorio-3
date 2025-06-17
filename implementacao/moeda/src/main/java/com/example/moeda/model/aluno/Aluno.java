package com.example.moeda.model.aluno;

import com.example.moeda.model.pessoa.Pessoa;
import com.example.moeda.model.vantagem.Vantagem;
import com.example.moeda.model.curso.Curso;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "alunos")
@Data
@EqualsAndHashCode(callSuper = true)
public class Aluno extends Pessoa {

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false, unique = true)
    private String rg;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Curso curso;

    @ManyToMany
    @JoinTable(name = "aluno_vantagem", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "vantagem_id"))
    private List<Vantagem> vantagens;
}
