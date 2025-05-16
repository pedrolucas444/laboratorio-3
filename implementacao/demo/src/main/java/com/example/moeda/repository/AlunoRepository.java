package com.example.moeda.repository;

import com.example.moeda.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}