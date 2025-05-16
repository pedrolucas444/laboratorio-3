package com.example.moeda.controller;

import com.example.moeda.model.aluno.Aluno;
import com.example.moeda.repository.AlunoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Operações relacionadas a alunos")
public class AlunoController {
    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista de todos os alunos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Aluno.class)))
    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok(alunos);
    }

    @Operation(summary = "Buscar aluno por ID", description = "Retorna um aluno específico baseado no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno encontrado",
                content = @Content(schema = @Schema(implementation = Aluno.class))),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(
            @Parameter(description = "ID do aluno a ser buscado", required = true)
            @PathVariable Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar novo aluno", description = "Cadastra um novo aluno no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                content = @Content(schema = @Schema(implementation = Aluno.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Aluno> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto Aluno a ser criado", required = true,
                content = @Content(schema = @Schema(implementation = Aluno.class)))
            @RequestBody Aluno aluno) {
        Aluno savedAluno = alunoRepository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
    }

    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = Aluno.class))),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(
            @Parameter(description = "ID do aluno a ser atualizado", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto Aluno com dados atualizados", required = true,
                content = @Content(schema = @Schema(implementation = Aluno.class)))
            @RequestBody Aluno aluno) {
        if (!alunoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aluno.setId(id);
        Aluno updatedAluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(updatedAluno);
    }

    @Operation(summary = "Excluir aluno", description = "Remove um aluno do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do aluno a ser excluído", required = true)
            @PathVariable Long id) {
        if (!alunoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alunoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}