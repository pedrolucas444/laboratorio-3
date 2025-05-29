package com.example.moeda.controller;

import com.example.moeda.model.aluno.Aluno;
import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.model.professor.Professor;
import com.example.moeda.repository.AlunoRepository;
import com.example.moeda.repository.EmpresaRepository;
import com.example.moeda.repository.ProfessorRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Operações de login")
public class AuthController {
    private final AlunoRepository alunoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProfessorRepository professorRepository;

    public AuthController(AlunoRepository alunoRepository,
            EmpresaRepository empresaRepository,
            ProfessorRepository professorRepository) {
        this.alunoRepository = alunoRepository;
        this.empresaRepository = empresaRepository;
        this.professorRepository = professorRepository;
    }

    @Operation(summary = "Login de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Verifica se é um aluno
        Aluno aluno = alunoRepository.findByEmail(request.getEmail());
        if (aluno != null && aluno.getSenha().equals(request.getSenha())) {
            Map<String, Object> response = new HashMap<>();
            response.put("tipo", "aluno");
            response.put("usuario", aluno);
            return ResponseEntity.ok(response);
        }

        // Verifica se é uma empresa
        Empresa empresa = empresaRepository.findByEmail(request.getEmail());
        if (empresa != null && empresa.getSenha().equals(request.getSenha())) {
            Map<String, Object> response = new HashMap<>();
            response.put("tipo", "empresa");
            response.put("usuario", empresa);
            return ResponseEntity.ok(response);
        }

        // Verifica se é um professor
        Professor professor = professorRepository.findByEmail(request.getEmail());
        if (professor != null && professor.getSenha().equals(request.getSenha())) {
            Map<String, Object> response = new HashMap<>();
            response.put("tipo", "professor");
            response.put("usuario", professor);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @GetMapping("/verificar-email")
    public ResponseEntity<Boolean> verificarEmailExistente(@RequestParam String email) {
        boolean existe = alunoRepository.existsByEmail(email) ||
                professorRepository.existsByEmail(email) ||
                empresaRepository.existsByEmail(email);
        return ResponseEntity.ok(existe);
    }
}

class LoginRequest {
    private String email;
    private String senha;

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}