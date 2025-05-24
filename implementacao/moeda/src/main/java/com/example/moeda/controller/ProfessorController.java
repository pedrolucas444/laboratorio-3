package com.example.moeda.controller;

import com.example.moeda.dto.ProfessorCreateDTO;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.professor.Professor;
import com.example.moeda.repository.DepartamentoRepository;
import com.example.moeda.repository.InstituicaoRepository;
import com.example.moeda.repository.ProfessorRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final DepartamentoRepository departamentoRepository;

    public ProfessorController(ProfessorRepository professorRepository,
            InstituicaoRepository instituicaoRepository,
            DepartamentoRepository departamentoRepository) {
        this.professorRepository = professorRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProfessorCreateDTO professorDTO) {
        try {
            // Valida instituição
            Instituicao instituicao = instituicaoRepository.findById(professorDTO.getInstituicaoId())
                    .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

            // Valida departamento
            Departamento departamento = departamentoRepository.findById(professorDTO.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

            // Verifica se o departamento pertence à instituição
            if (!departamento.getInstituicao().getId().equals(instituicao.getId())) {
                throw new RuntimeException("O departamento não pertence à instituição informada");
            }

            Professor professor = new Professor();
            professor.setNome(professorDTO.getNome());
            professor.setEmail(professorDTO.getEmail());
            professor.setSenha(professorDTO.getSenha());
            professor.setCpf(professorDTO.getCpf());
            professor.setMatricula(professorDTO.getMatricula());
            professor.setInstituicao(instituicao);
            professor.setDepartamento(departamento);
            professor.setSaldo(0);

            Professor savedProfessor = professorRepository.save(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/instituicoes")
    public ResponseEntity<List<Instituicao>> getAllInstituicoes() {
        return ResponseEntity.ok(instituicaoRepository.findAll());
    }

    @GetMapping("/departamentos/instituicao/{instituicaoId}")
    public ResponseEntity<List<Departamento>> getDepartamentosByInstituicao(@PathVariable Long instituicaoId) {
        return ResponseEntity.ok(departamentoRepository.findByInstituicaoId(instituicaoId));
    }
}