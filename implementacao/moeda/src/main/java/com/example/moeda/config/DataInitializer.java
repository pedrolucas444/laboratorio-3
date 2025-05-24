package com.example.moeda.config;

import com.example.moeda.model.curso.Curso;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(InstituicaoRepository instituicaoRepo,
            DepartamentoRepository departamentoRepo,
            CursoRepository cursoRepo) {
        return args -> {
            cursoRepo.deleteAll();
            departamentoRepo.deleteAll();
            instituicaoRepo.deleteAll();

            Instituicao uf = instituicaoRepo
                    .save(new Instituicao("Universidade Federal", "12345678901234", "Av. Principal, 1000"));
            Instituicao faculdade = instituicaoRepo
                    .save(new Instituicao("Faculdade Tecnológica", "98765432109876", "Rua Secundária, 500"));

            Departamento deptComp = departamentoRepo.save(new Departamento("Computação", uf));
            Departamento deptEng = departamentoRepo.save(new Departamento("Engenharia", uf));
            Departamento deptAdm = departamentoRepo.save(new Departamento("Administração", faculdade));

            cursoRepo.save(new Curso("Ciência da Computação", "CC001", deptComp));
            cursoRepo.save(new Curso("Engenharia de Software", "ES002", deptComp));
            cursoRepo.save(new Curso("Engenharia Civil", "EC003", deptEng));
            cursoRepo.save(new Curso("Administração", "ADM004", deptAdm));

            System.out.println("Dados iniciais criados:");
            System.out.println("- Instituições: " + instituicaoRepo.count());
            System.out.println("- Departamentos: " + departamentoRepo.count());
            System.out.println("- Cursos: " + cursoRepo.count());
        };
    }
}