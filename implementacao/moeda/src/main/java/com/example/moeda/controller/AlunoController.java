package com.example.moeda.controller;

import com.example.moeda.dto.AlunoCreateDTO;
import com.example.moeda.dto.CursoDTO;
import com.example.moeda.model.aluno.Aluno;
import com.example.moeda.model.curso.Curso;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.transacao.Transacao;
import com.example.moeda.repository.AlunoRepository;
import com.example.moeda.repository.CursoRepository;
import com.example.moeda.repository.InstituicaoRepository;
import com.example.moeda.repository.TransacaoRepository;
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
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Operações relacionadas a alunos")
public class AlunoController {
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TransacaoRepository transacaoRepository;

    public AlunoController(AlunoRepository alunoRepository,
            CursoRepository cursoRepository,
            InstituicaoRepository instituicaoRepository, TransacaoRepository transacaoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Operation(summary = "Criar novo aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso ou Instituição não encontrada")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AlunoCreateDTO alunoDTO) {
        try {
            // Find or validate related entities
            Optional<Curso> curso = cursoRepository.findById(alunoDTO.getCursoId());
            Optional<Instituicao> instituicao = instituicaoRepository.findById(alunoDTO.getInstituicaoId());

            if (curso.isEmpty() || instituicao.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Curso ou Instituição não encontrada");
            }

            // Create new Aluno
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setEmail(alunoDTO.getEmail());
            aluno.setSenha(alunoDTO.getSenha());
            aluno.setCpf(alunoDTO.getCpf());
            aluno.setSaldo(alunoDTO.getSaldo());
            aluno.setEndereco(alunoDTO.getEndereco());
            aluno.setRg(alunoDTO.getRg());
            aluno.setCurso(curso.get());
            aluno.setInstituicao(instituicao.get());

            Aluno savedAluno = alunoRepository.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro de integridade de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar aluno: " + e.getMessage());
        }
    }

    @Operation(summary = "Listar todas as instituições")
    @GetMapping("/instituicoes")
    public ResponseEntity<List<Instituicao>> getAllInstituicoes() {
        return ResponseEntity.ok(instituicaoRepository.findAll());
    }

    @Operation(summary = "Listar todos os cursos")
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoDTO> dtos = cursos.stream()
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getCodigo(),
                        curso.getDepartamento().getNome(),
                        curso.getDepartamento().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Listar todos os alunos")
    @GetMapping("/aluno")
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        return ResponseEntity.ok(alunoRepository.findAll());
    }

    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso", content = @Content(schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(
            @Parameter(description = "ID do aluno a ser atualizado", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Aluno com dados atualizados", required = true, content = @Content(schema = @Schema(implementation = Aluno.class))) @RequestBody Aluno aluno) {
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
            @Parameter(description = "ID do aluno a ser excluído", required = true) @PathVariable Long id) {
        if (!alunoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alunoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-cpf")
    public ResponseEntity<Boolean> verificarCpfExistente(@RequestParam String cpf) {
        boolean existe = alunoRepository.existsByCpf(cpf);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/verificar-rg")
    public ResponseEntity<Boolean> verificarRgExistente(@RequestParam String rg) {
        boolean existe = alunoRepository.existsByRg(rg);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/extrato/{alunoId}")
    public ResponseEntity<Map<String, Object>> getExtratoAluno(@PathVariable Long alunoId) {
        Map<String, Object> extrato = new HashMap<>();

        List<Transacao> recebidas = transacaoRepository.findByDestinatarioId(alunoId);
        List<Transacao> enviadas = transacaoRepository.findByRemetenteId(alunoId);

        int totalRecebido = recebidas.stream().mapToInt(Transacao::getValor).sum();
        int totalResgatado = enviadas.stream().mapToInt(Transacao::getValor).sum();

        extrato.put("transacoes", Stream.concat(recebidas.stream(), enviadas.stream())
                .sorted(Comparator.comparing(Transacao::getData).reversed())
                .collect(Collectors.toList()));
        extrato.put("totalRecebido", totalRecebido);
        extrato.put("totalResgatado", totalResgatado);
        extrato.put("saldoCalculado", totalRecebido - totalResgatado);

        return ResponseEntity.ok(extrato);
    }
}