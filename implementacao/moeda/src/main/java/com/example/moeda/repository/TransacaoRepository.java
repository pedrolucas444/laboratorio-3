package com.example.moeda.repository;

import com.example.moeda.model.transacao.Transacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByRemetenteId(Long remetenteId);

    List<Transacao> findByDestinatarioId(Long destinatarioId);

    List<Transacao> findByInstituicaoId(Long instituicaoId);

    Optional<Transacao> findTopByRemetenteIdAndValorOrderByDataDesc(Long id, int i);
}