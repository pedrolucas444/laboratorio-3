package com.example.moeda.model.departamento;

import com.example.moeda.model.curso.Curso;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.professor.Professor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "departamentos")
@Data
@NoArgsConstructor
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    @JsonIgnoreProperties("departamentos")
    private Instituicao instituicao;

    @OneToMany(mappedBy = "departamento")
    @JsonIgnore
    private List<Curso> cursos;

    @OneToMany(mappedBy = "departamento")
    @JsonIgnoreProperties("departamento")
    private List<Professor> professores;

    // Construtor customizado mantido
    public Departamento(String nome, Instituicao instituicao) {
        this.nome = nome;
        this.instituicao = instituicao;
    }
}
