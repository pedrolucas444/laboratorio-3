package com.example.moeda.model.empresa;

import com.example.moeda.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
public class Empresa extends Usuario {
    @Column(nullable = false, unique = true)
    private String cnpj;
}
