package com.example.moeda.dto;

import com.example.moeda.model.departamento.Departamento;

public class DepartamentoResponseDTO {
    private Long id;
    private String nome;
    
    public DepartamentoResponseDTO(Departamento departamento) {
        this.id = departamento.getId();
        this.nome = departamento.getNome();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
}