package com.meritosystem.service.impl;

import com.meritosystem.dto.AlunoDTO;
import com.meritosystem.model.Aluno;
import com.meritosystem.repository.AlunoRepository;
import com.meritosystem.service.AlunoService;
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
        if (alunoRepository.existsByCpf(alunoDTO.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        
        if (alunoRepository.existsByEmail(alunoDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.getNome());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setRg(alunoDTO.getRg());
        aluno.setEndereco(alunoDTO.getEndereco());
        aluno.setCurso(alunoDTO.getCurso());
        
        aluno.setInstituicao(instituicaoRepository.findById(alunoDTO.getInstituicaoId())
            .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        
        Aluno alunoSalvo = alunoRepository.save(aluno);
        
        return convertToDTO(alunoSalvo);
    }

    
    private AlunoDTO convertToDTO(Aluno aluno) {
        AlunoDTO dto = new AlunoDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setCpf(aluno.getCpf());
        dto.setRg(aluno.getRg());
        dto.setEndereco(aluno.getEndereco());
        dto.setCurso(aluno.getCurso());
        dto.setInstituicaoId(aluno.getInstituicao().getId());
        return dto;
    }
}