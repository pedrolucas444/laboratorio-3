package com.example.moeda.controller;

import com.example.moeda.dto.EmpresaCreateDTO;
import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.repository.EmpresaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operações relacionadas a empresas parceiras")
public class EmpresaController {
    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Operation(summary = "Cadastrar nova empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmpresaCreateDTO empresaDTO) {
        try {
            Empresa empresa = new Empresa();
            empresa.setNome(empresaDTO.getNome());
            empresa.setEmail(empresaDTO.getEmail());
            empresa.setSenha(empresaDTO.getSenha());
            empresa.setCnpj(empresaDTO.getCnpj());

            Empresa savedEmpresa = empresaRepository.save(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro de integridade de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar empresa: " + e.getMessage());
        }
    }

    @Operation(summary = "Listar todas as empresas")
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        return ResponseEntity.ok(empresaRepository.findAll());
    }

    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso", content = @Content(schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(
            @Parameter(description = "ID da empresa a ser atualizada", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Empresa com dados atualizados", required = true, content = @Content(schema = @Schema(implementation = Empresa.class))) @RequestBody Empresa empresa) {
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
            @Parameter(description = "ID da empresa a ser excluída", required = true) @PathVariable Long id) {
        if (!empresaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-cnpj")
    public ResponseEntity<Boolean> verificarCnpjExistente(@RequestParam String cnpj) {
        boolean existe = empresaRepository.existsByCnpj(cnpj);
        return ResponseEntity.ok(existe);
    }
}