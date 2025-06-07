package com.example.moeda.controller;

import com.example.moeda.dto.DepositoSemestralDTO;
import com.example.moeda.dto.ResgateVantagemDTO;
import com.example.moeda.dto.TransacaoDTO;
import com.example.moeda.model.aluno.Aluno;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.pessoa.Pessoa;
import com.example.moeda.model.transacao.Transacao;
import com.example.moeda.model.vantagem.Vantagem;
import com.example.moeda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            // Buscar as entidades relacionadas
            Optional<Pessoa> remetenteOpt = pessoaRepository.findById(transacaoDTO.getRemetenteId());
            Optional<Pessoa> destinatarioOpt = pessoaRepository.findById(transacaoDTO.getDestinatarioId());
            Optional<Instituicao> instituicaoOpt = instituicaoRepository.findById(transacaoDTO.getInstituicaoId());

            if (remetenteOpt.isEmpty() || destinatarioOpt.isEmpty() || instituicaoOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Pessoa remetente = remetenteOpt.get();
            Pessoa destinatario = destinatarioOpt.get();
            Instituicao instituicao = instituicaoOpt.get();

            // Verificar se o remetente tem saldo suficiente
            if (remetente.getSaldo() < transacaoDTO.getValor()) {
                return ResponseEntity.badRequest().build();
            }

            // Atualizar os saldos
            remetente.setSaldo(remetente.getSaldo() - transacaoDTO.getValor());
            destinatario.setSaldo(destinatario.getSaldo() + transacaoDTO.getValor());

            pessoaRepository.save(remetente);
            pessoaRepository.save(destinatario);

            // Criar a transação
            Transacao transacao = new Transacao();
            transacao.setData(LocalDateTime.now());
            transacao.setRemetente(remetente);
            transacao.setDestinatario(destinatario);
            transacao.setInstituicao(instituicao);
            transacao.setValor(transacaoDTO.getValor());
            transacao.setMensagem(transacaoDTO.getMensagem());

            Transacao transacaoSalva = transacaoRepository.save(transacao);

            return ResponseEntity.ok(transacaoSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/deposito-semestral")
    public ResponseEntity<Transacao> realizarDepositoSemestral(
            @RequestBody DepositoSemestralDTO depositoDTO) {

        try {
            // Buscar professor e instituição
            Optional<Pessoa> professorOpt = pessoaRepository.findById(depositoDTO.getProfessorId());
            Optional<Instituicao> instituicaoOpt = instituicaoRepository.findById(depositoDTO.getInstituicaoId());

            if (professorOpt.isEmpty() || instituicaoOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Pessoa professor = professorOpt.get();
            Instituicao instituicao = instituicaoOpt.get();

            // Verificar se já houve depósito nos últimos 6 meses
            LocalDateTime seisMesesAtras = LocalDateTime.now().minusMonths(6);
            Optional<Transacao> ultimoDeposito = transacaoRepository
                    .findTopByRemetenteIdAndValorOrderByDataDesc(professor.getId(), 1000);

            // if (ultimoDeposito.isPresent() &&
            // ultimoDeposito.get().getData().isAfter(seisMesesAtras)) {
            if (false) {
                return ResponseEntity.badRequest().build();
            }

            // Atualizar saldo do professor
            professor.setSaldo(professor.getSaldo() + 1000);
            pessoaRepository.save(professor);

            // Criar transação de depósito
            Transacao transacao = new Transacao();
            transacao.setData(LocalDateTime.now());
            transacao.setRemetente(professor);
            transacao.setDestinatario(professor);
            transacao.setInstituicao(instituicao);
            transacao.setValor(1000);
            transacao.setMensagem("Depósito semestral");

            Transacao transacaoSalva = transacaoRepository.save(transacao);

            return ResponseEntity.ok(transacaoSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/alunos-por-instituicao/{instituicaoId}")
    public ResponseEntity<List<Aluno>> listarAlunosPorInstituicao(@PathVariable Long instituicaoId) {
        List<Aluno> alunos = alunoRepository.findByInstituicaoId(instituicaoId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/deposito-semestral/status/{professorId}")
    public ResponseEntity<Map<String, Object>> verificarStatusDeposito(@PathVariable Long professorId) {
        Map<String, Object> response = new HashMap<>();

        Optional<Transacao> ultimoDeposito = transacaoRepository
                .findTopByRemetenteIdAndValorOrderByDataDesc(professorId, 1000);

        boolean podeSolicitar = true;
        if (ultimoDeposito.isPresent()) {
            LocalDateTime seisMesesAtras = LocalDateTime.now().minusMonths(6);
            podeSolicitar = ultimoDeposito.get().getData().isBefore(seisMesesAtras);
        }

        response.put("ultimoDeposito", ultimoDeposito.orElse(null));
        response.put("podeSolicitar", podeSolicitar);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/por-remetente/{pessoaId}")
    public ResponseEntity<List<Transacao>> listarTransacoesComoRemetente(@PathVariable Long pessoaId) {
        List<Transacao> transacoes = transacaoRepository.findByRemetenteId(pessoaId);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/por-destinatario/{pessoaId}")
    public ResponseEntity<List<Transacao>> listarTransacoesComoDestinatario(@PathVariable Long pessoaId) {
        List<Transacao> transacoes = transacaoRepository.findByDestinatarioId(pessoaId);
        return ResponseEntity.ok(transacoes);
    }

    @PostMapping("/resgatar-vantagem")
    public ResponseEntity<Transacao> resgatarVantagem(@RequestBody ResgateVantagemDTO resgateDTO) {
        try {
            Aluno aluno = alunoRepository.findById(resgateDTO.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            Vantagem vantagem = vantagemRepository.findById(resgateDTO.getVantagemId())
                    .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));

            // Verificar saldo suficiente
            if (aluno.getSaldo() < vantagem.getValor()) {
                return ResponseEntity.badRequest().build();
            }

            // Atualizar saldo do aluno
            aluno.setSaldo(aluno.getSaldo() - vantagem.getValor());
            alunoRepository.save(aluno);

            Transacao transacao = new Transacao();
            transacao.setData(LocalDateTime.now());
            transacao.setRemetente(aluno);
            transacao.setDestinatario(aluno);
            transacao.setInstituicao(aluno.getInstituicao());
            transacao.setValor(vantagem.getValor());
            transacao.setMensagem("Resgate: " + vantagem.getTitulo());

            Transacao savedTransacao = transacaoRepository.save(transacao);

            return ResponseEntity.ok(savedTransacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}