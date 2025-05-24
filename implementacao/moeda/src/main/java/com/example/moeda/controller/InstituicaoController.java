package com.example.moeda.controller;

import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.repository.InstituicaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoController {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoController(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    @GetMapping
    public List<Instituicao> getAllInstituicoes() {
        return instituicaoRepository.findAll();
    }
}