package com.example.moeda.controller;

import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.repository.EmpresaRepository;
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
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operações relacionadas a empresas parceiras")
public class EmpresaController {
    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Operation(summary = "Listar todas as empresas", description = "Retorna uma lista de todas as empresas parceiras cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Empresa.class)))
    @GetMapping
    public ResponseEntity<List<Empresa>> findAll() {
        List<Empresa> empresas = empresaRepository.findAll();
        return ResponseEntity.ok(empresas);
    }

    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa específica baseada no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa encontrada",
                content = @Content(schema = @Schema(implementation = Empresa.class))),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> findById(
            @Parameter(description = "ID da empresa a ser buscada", required = true)
            @PathVariable Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        return empresa.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova empresa", description = "Registra uma nova empresa parceira no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso",
                content = @Content(schema = @Schema(implementation = Empresa.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Empresa> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto Empresa a ser criado", required = true,
                content = @Content(schema = @Schema(implementation = Empresa.class)))
            @RequestBody Empresa empresa) {
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
    }

    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = Empresa.class))),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(
            @Parameter(description = "ID da empresa a ser atualizada", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto Empresa com dados atualizados", required = true,
                content = @Content(schema = @Schema(implementation = Empresa.class)))
            @RequestBody Empresa empresa) {
        if (!empresaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresa.setId(id);
        Empresa updatedEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.ok(updatedEmpresa);
    }

    @Operation(summary = "Remover empresa", description = "Exclui uma empresa parceira do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empresa excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da empresa a ser excluída", required = true)
            @PathVariable Long id) {
        if (!empresaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}