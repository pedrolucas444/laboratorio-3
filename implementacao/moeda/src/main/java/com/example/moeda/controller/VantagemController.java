package com.example.moeda.controller;

import com.example.moeda.dto.VantagemCreateDTO;
import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.model.vantagem.Vantagem;
import com.example.moeda.repository.EmpresaRepository;
import com.example.moeda.repository.VantagemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vantagens")
@Tag(name = "Vantagens", description = "Operações relacionadas a vantagens oferecidas por empresas")
public class VantagemController {
    private final VantagemRepository vantagemRepository;
    private final EmpresaRepository empresaRepository;

    public VantagemController(VantagemRepository vantagemRepository, EmpresaRepository empresaRepository) {
        this.vantagemRepository = vantagemRepository;
        this.empresaRepository = empresaRepository;
    }

    @Operation(summary = "Criar nova vantagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vantagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody VantagemCreateDTO vantagemDTO) {
        Optional<Empresa> empresa = empresaRepository.findById(vantagemDTO.getEmpresaId());
        if (empresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }

        Vantagem vantagem = new Vantagem();
        vantagem.setTitulo(vantagemDTO.getTitulo());
        vantagem.setValor(vantagemDTO.getValor());
        vantagem.setDescricao(vantagemDTO.getDescricao());
        vantagem.setEmpresa(empresa.get());

        Vantagem savedVantagem = vantagemRepository.save(vantagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVantagem);
    }

    @Operation(summary = "Listar vantagens por empresa")
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Vantagem>> getByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(vantagemRepository.findByEmpresaId(empresaId));
    }

    @Operation(summary = "Listar todas as vantagens")
    @GetMapping
    public ResponseEntity<List<Vantagem>> getAll() {
        return ResponseEntity.ok(vantagemRepository.findAll());
    }
}