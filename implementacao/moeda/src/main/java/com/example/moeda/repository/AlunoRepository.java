package com.example.moeda.repository;

import com.example.moeda.model.aluno.Aluno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Aluno findByCpf(String cpf);
    Aluno findByRg(String rg);
    Aluno findByEmail(String email);
    boolean existsByRg(String rg);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<Aluno> findByInstituicaoId(Long instituicaoId);
}