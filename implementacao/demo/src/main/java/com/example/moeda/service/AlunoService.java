package com.example.moeda.service;

import com.example.moeda.dto.AlunoDTO;
import java.util.List;

public interface AlunoService {
    AlunoDTO criarAluno(AlunoDTO alunoDTO);
    AlunoDTO buscarAlunoPorId(Long id);
    List<AlunoDTO> listarTodosAlunos();
    AlunoDTO atualizarAluno(Long id, AlunoDTO alunoDTO);
    void deletarAluno(Long id);
}