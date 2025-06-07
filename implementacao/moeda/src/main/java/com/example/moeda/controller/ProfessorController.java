package com.example.moeda.controller;

import com.example.moeda.dto.ProfessorCreateDTO;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.professor.Professor;
import com.example.moeda.model.transacao.Transacao;
import com.example.moeda.repository.DepartamentoRepository;
import com.example.moeda.repository.InstituicaoRepository;
import com.example.moeda.repository.ProfessorRepository;
import com.example.moeda.repository.TransacaoRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final TransacaoRepository transacaoRepository;

    public ProfessorController(ProfessorRepository professorRepository,
            InstituicaoRepository instituicaoRepository,
            DepartamentoRepository departamentoRepository, TransacaoRepository transacaoRepository) {
        this.professorRepository = professorRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.departamentoRepository = departamentoRepository;
        this.transacaoRepository = transacaoRepository;
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
            // professor.setMatricula(professorDTO.getMatricula());
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

    @GetMapping("/verificar-cpf")
    public ResponseEntity<Boolean> verificarCpfExistente(@RequestParam String cpf) {
        boolean existe = professorRepository.existsByCpf(cpf);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/extrato/{professorId}")
    public ResponseEntity<Map<String, Object>> getExtratoProfessor(@PathVariable Long professorId) {
        Map<String, Object> extrato = new HashMap<>();

        List<Transacao> recebidas = transacaoRepository.findByDestinatarioId(professorId);
        List<Transacao> enviadas = transacaoRepository.findByRemetenteId(professorId);

        int totalRecebido = recebidas.stream().mapToInt(Transacao::getValor).sum();
        int totalTransferido = enviadas.stream().mapToInt(Transacao::getValor).sum();

        extrato.put("transacoes", Stream.concat(recebidas.stream(), enviadas.stream())
                .sorted(Comparator.comparing(Transacao::getData).reversed())
                .collect(Collectors.toList()));
        extrato.put("totalRecebido", totalRecebido);
        extrato.put("totalTransferido", totalTransferido);
        extrato.put("saldoCalculado", totalRecebido - totalTransferido);

        return ResponseEntity.ok(extrato);
    }
}