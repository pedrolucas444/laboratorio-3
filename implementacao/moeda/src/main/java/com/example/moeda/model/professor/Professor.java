package com.example.moeda.model.professor;

import com.example.moeda.model.pessoa.Pessoa;
import com.example.moeda.model.departamento.Departamento;
import jakarta.persistence.*;

@Entity
@Table(name = "professores")
public class Professor extends Pessoa {
   // @Column(nullable = false, unique = true)
   // private String matricula;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    // Getters e Setters
   // public String getMatricula() {
   //     return matricula;
   // }

    //public void setMatricula(String matricula) {
     //   this.matricula = matricula;
    //}

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}