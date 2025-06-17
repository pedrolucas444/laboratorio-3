package com.example.moeda.model.vantagem;

import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.model.aluno.Aluno;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "vantagens")
@Getter
@Setter
@NoArgsConstructor
public class Vantagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int valor;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, length = 500)
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToMany(mappedBy = "vantagens")
    private List<Aluno> alunos;
}
