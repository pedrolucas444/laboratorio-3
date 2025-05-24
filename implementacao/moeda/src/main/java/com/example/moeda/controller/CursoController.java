package com.example.moeda.controller;

import com.example.moeda.dto.CursoDTO;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.repository.DepartamentoRepository;
import com.example.moeda.repository.CursoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoRepository cursoRepository = null;
    private final DepartamentoRepository departamentoRepository = null;

    @GetMapping("/instituicao/{instituicaoId}")
    public List<CursoDTO> getCursosByInstituicao(@PathVariable Long instituicaoId) {
        List<Departamento> departamentos = departamentoRepository.findByInstituicaoId(instituicaoId);
        
        return departamentos.stream()
            .flatMap(dept -> dept.getCursos().stream()
                .map(curso -> new CursoDTO(
                    curso.getId(),
                    curso.getNome(),
                    curso.getCodigo(),
                    dept.getNome(),
                    dept.getId()
                ))
            )
            .collect(Collectors.toList());
    }
}