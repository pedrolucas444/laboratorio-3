package com.example.moeda.controller;

import com.example.moeda.dto.DepartamentoResponseDTO;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.repository.DepartamentoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {
    private final DepartamentoRepository departamentoRepository = null;

    @GetMapping("/instituicao/{instituicaoId}")
    public ResponseEntity<List<DepartamentoResponseDTO>> getByInstituicao(@PathVariable Long instituicaoId) {
        List<Departamento> departamentos = departamentoRepository.findByInstituicaoId(instituicaoId);
        
        List<DepartamentoResponseDTO> response = departamentos.stream()
            .map(DepartamentoResponseDTO::new)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(response);
    }
}