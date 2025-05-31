package com.example.moeda.dto;

public class DepositoSemestralDTO {
    private Long professorId;
    private Long instituicaoId;

    // Getters e Setters
    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Long instituicaoId) {
        this.instituicaoId = instituicaoId;
    }
}