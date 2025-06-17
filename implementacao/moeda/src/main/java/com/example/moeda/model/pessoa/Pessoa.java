package com.example.moeda.model.pessoa;

import com.example.moeda.model.usuario.Usuario;
import com.example.moeda.model.instituicao.Instituicao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class Pessoa extends Usuario {

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private int saldo;

    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "pessoas" })
    private Instituicao instituicao;
}
