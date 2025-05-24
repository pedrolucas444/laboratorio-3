package com.example.moeda.repository;

import com.example.moeda.model.departamento.Departamento;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    @EntityGraph(attributePaths = {"cursos"})
    List<Departamento> findByInstituicaoId(Long instituicaoId);
}