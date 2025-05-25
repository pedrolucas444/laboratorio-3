package com.example.moeda.controller;

import com.example.moeda.dto.CursoDTO;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.repository.DepartamentoRepository;
import com.example.moeda.repository.CursoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoRepository cursoRepository;
    private final DepartamentoRepository departamentoRepository;

    public CursoController(CursoRepository cursoRepository, DepartamentoRepository departamentoRepository) {
        this.cursoRepository = cursoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @GetMapping("/instituicao/{instituicaoId}")
    public ResponseEntity<List<CursoDTO>> getCursosByInstituicao(@PathVariable Long instituicaoId) {
        List<Departamento> departamentos = departamentoRepository.findByInstituicaoId(instituicaoId);

        List<CursoDTO> cursosDTO = departamentos.stream()
                .flatMap(dept -> dept.getCursos().stream())
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getCodigo(),
                        curso.getDepartamento().getNome(),
                        curso.getDepartamento().getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(cursosDTO);
    }
}