package com.example.moeda.repository;

import com.example.moeda.model.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    Optional<Pessoa> findByCpf(String cpf);
    
    Optional<Pessoa> findByEmail(String email);
    
    List<Pessoa> findByInstituicaoId(Long instituicaoId);
    
    @Query("SELECT p FROM Pessoa p WHERE TYPE(p) = Aluno AND p.instituicao.id = :instituicaoId")
    List<Pessoa> findAlunosByInstituicaoId(Long instituicaoId);
    
    @Query("SELECT p FROM Pessoa p WHERE TYPE(p) = Professor AND p.instituicao.id = :instituicaoId")
    List<Pessoa> findProfessoresByInstituicaoId(Long instituicaoId);
}