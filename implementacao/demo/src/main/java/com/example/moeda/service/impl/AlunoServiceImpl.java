package com.example.moeda.service.impl;

import com.example.moeda.dto.AlunoDTO;
import com.example.moeda.model.Aluno;
import com.example.moeda.repository.AlunoRepository;
import com.example.moeda.repository.InstituicaoRepository;
import com.example.moeda.service.AlunoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final InstituicaoRepository instituicaoRepository;
    
    public AlunoServiceImpl(AlunoRepository alunoRepository, InstituicaoRepository instituicaoRepository) {
        this.alunoRepository = alunoRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    @Override
    @Transactional
    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
            return alunoDTO;
    }
        
    @Override
    public AlunoDTO buscarAlunoPorId(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarAlunoPorId'");
    }

    @Override
    public List<AlunoDTO> listarTodosAlunos() {
        throw new UnsupportedOperationException("Unimplemented method 'listarTodosAlunos'");
    }

    @Override
    public AlunoDTO atualizarAluno(Long id, AlunoDTO alunoDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarAluno'");
    }

    @Override
    public void deletarAluno(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deletarAluno'");
    }
}