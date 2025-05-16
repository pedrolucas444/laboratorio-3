package com.example.moeda.model.empresa;

import com.example.moeda.model.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
public class Empresa extends Usuario {
    @Column(nullable = false, unique = true)
    private String cnpj;

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}