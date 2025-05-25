package com.example.moeda.repository;

import com.example.moeda.model.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    //Professor findByMatricula(String matricula);
    Professor findByEmail(String email);
    Professor findByCpf(String cpf);
}