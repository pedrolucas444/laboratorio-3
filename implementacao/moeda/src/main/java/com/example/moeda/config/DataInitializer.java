package com.example.moeda.config;

import com.example.moeda.model.curso.Curso;
import com.example.moeda.model.departamento.Departamento;
import com.example.moeda.model.instituicao.Instituicao;
import com.example.moeda.model.aluno.Aluno;
import com.example.moeda.model.professor.Professor;
import com.example.moeda.model.empresa.Empresa;
import com.example.moeda.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

        @Bean
        CommandLineRunner initDatabase(InstituicaoRepository instituicaoRepo,
                        DepartamentoRepository departamentoRepo,
                        CursoRepository cursoRepo, AlunoRepository alunoRepo,
                        ProfessorRepository professorRepo,
                        PessoaRepository pessoaRepo, EmpresaRepository empresaRepo) {

                return args -> {
                        alunoRepo.deleteAll();
                        professorRepo.deleteAll();
                        cursoRepo.deleteAll();
                        departamentoRepo.deleteAll();
                        instituicaoRepo.deleteAll();
                        empresaRepo.deleteAll();

                        Instituicao uf = instituicaoRepo
                                        .save(new Instituicao("Universidade Federal", "12345678901234",
                                                        "Av. Principal, 1000"));
                        Instituicao faculdade = instituicaoRepo
                                        .save(new Instituicao("Faculdade Tecnológica", "98765432109876",
                                                        "Rua Secundária, 500"));

                        Departamento deptComp = departamentoRepo.save(new Departamento("Computação", uf));
                        Departamento deptEng = departamentoRepo.save(new Departamento("Engenharia", uf));
                        Departamento deptAdm = departamentoRepo.save(new Departamento("Administração", faculdade));

                        Curso cc = cursoRepo.save(new Curso("Ciência da Computação", "CC001", deptComp));
                        Curso es = cursoRepo.save(new Curso("Engenharia de Software", "ES002", deptComp));
                        Curso ec = cursoRepo.save(new Curso("Engenharia Civil", "EC003", deptEng));
                        Curso adm = cursoRepo.save(new Curso("Administração", "ADM004", deptAdm));


                        criarAluno("leo", "leonardovieiramachado@gmail.com", "123456",
                                        "12345678911", "98456213", "Rua A, 123", uf, cc, alunoRepo);
                        criarAluno("aluno1", "aluno1@gmail.com", "123456",
                                        "11111111111", "12345678", "Rua A, 123", uf, cc, alunoRepo);
                        criarAluno("aluno2", "aluno2@gmail.com", "123456",
                                        "22222222222", "23456789", "Rua B, 456", uf, cc, alunoRepo);
                        criarAluno("aluno3", "aluno3@gmail.com", "123456",
                                        "33333333333", "34567890", "Rua C, 789", uf, es, alunoRepo);
                        criarAluno("aluno4", "aluno4@gmail.com", "123456",
                                        "44444444444", "45678901", "Rua D, 101", uf, es, alunoRepo);
                        criarAluno("aluno5", "aluno5@gmail.com", "123456",
                                        "55555555555", "56789012", "Rua E, 202", uf, ec, alunoRepo);
                        criarAluno("aluno6", "aluno6@gmail.com", "123456",
                                        "66666666666", "67890123", "Rua F, 303", uf, ec, alunoRepo);
                        criarAluno("aluno7", "alun7o@gmail.com", "123456",
                                        "77777777777", "78901234", "Rua G, 404", faculdade, adm, alunoRepo);
                        criarAluno("aluno8", "aluno8@gmail.com", "123456",
                                        "88888888888", "89012345", "Rua H, 505", faculdade, adm, alunoRepo);

                        // Criar professores
                        criarProfessor("professor1", "professor1@gmail.com", "123456",
                                        "99999999999", uf, deptComp, professorRepo);
                        criarProfessor("professor2", "professor2@gmail.com", "123456",
                                        "10101010101", uf, deptEng, professorRepo);
                        criarProfessor("professor3", "professor3@gmail.com", "123456",
                                        "12121212121", faculdade, deptAdm, professorRepo);


                        criarEmpresa("empresa1", "empresa1@gmail.com", "123456", "11111111111111", empresaRepo);

                        System.out.println("Dados iniciais criados:");
                        System.out.println("- Instituições: " + instituicaoRepo.count());
                        System.out.println("- Departamentos: " + departamentoRepo.count());
                        System.out.println("- Cursos: " + cursoRepo.count());
                        System.out.println("- Alunos: " + alunoRepo.count());
                        System.out.println("- Professores: " + professorRepo.count());
                };
        }

        private void criarAluno(String nome, String email, String senha, String cpf, String rg,
                        String endereco, Instituicao instituicao, Curso curso, AlunoRepository alunoRepo) {
                Aluno aluno = new Aluno();
                aluno.setNome(nome);
                aluno.setEmail(email);
                aluno.setSenha(senha);
                aluno.setCpf(cpf);
                aluno.setRg(rg);
                aluno.setEndereco(endereco);
                aluno.setInstituicao(instituicao);
                aluno.setCurso(curso);
                aluno.setSaldo(0);
                alunoRepo.save(aluno);
        }

        private void criarProfessor(String nome, String email, String senha, String cpf,
                        Instituicao instituicao, Departamento departamento, ProfessorRepository professorRepo) {
                Professor professor = new Professor();
                professor.setNome(nome);
                professor.setEmail(email);
                professor.setSenha(senha);
                professor.setCpf(cpf);
                professor.setInstituicao(instituicao);
                professor.setDepartamento(departamento);
                professor.setSaldo(0);
                professorRepo.save(professor);
        }

        private void criarEmpresa(String nome, String email, String senha, String cnpj, EmpresaRepository empresaRepo) {
                Empresa empresa = new Empresa();
                empresa.setNome(nome);
                empresa.setEmail(email);
                empresa.setSenha(senha);
                empresa.setCnpj(cnpj);
                empresaRepo.save(empresa);
        }
}