package com.meritosystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas_parceiras")
public class EmpresaParceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cnpj;
    
    @Column(nullable = false)
    private String endereco;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vantagem> vantagens = new ArrayList<>();
    
    
    public EmpresaParceira() {
    }


    public void addVantagem(Vantagem vantagem) {
        vantagens.add(vantagem);
        vantagem.setEmpresa(this);
    }
    

}