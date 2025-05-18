package com.example.moeda.config;

import com.example.moeda.model.curso.Curso;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.repository.CursoRepository;
import com.example.moeda.repository.InstituicaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(InstituicaoRepository instituicaoRepository, 
                                 CursoRepository cursoRepository) {
        return args -> {
            if (instituicaoRepository.count() == 0) {
                instituicaoRepository.save(new Instituicao("Universidade Federal", "12345678901234", "Av. Principal, 1000"));
                instituicaoRepository.save(new Instituicao("Faculdade Tecnológica", "98765432109876", "Rua Secundária, 500"));
                instituicaoRepository.save(new Instituicao("Instituto Federal", "56789012345678", "Alameda dos Estudantes, 300"));
            }

            if (cursoRepository.count() == 0) {
                cursoRepository.save(new Curso("Ciência da Computação", "CC001"));
                cursoRepository.save(new Curso("Engenharia de Software", "ES002"));
                cursoRepository.save(new Curso("Administração", "ADM003"));
            }
        };
    }
}