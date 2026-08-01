package br.edu.cetam.matriculatec.service;

import br.edu.cetam.matriculatec.exception.RegistroNaoEncontradoException;
import br.edu.cetam.matriculatec.model.*;
import br.edu.cetam.matriculatec.model.enums.SituacaoMatricula;

import java.util.*;
import java.util.stream.Collectors;

public class MatriculaService {
    private List<Aluno> bancoAlunos = new ArrayList<>();
    private List<Curso> bancoCursos = new ArrayList<>();
    private List<Turma> bancoTurmas = new ArrayList<>();

    public void cadastrarAluno(Aluno aluno) { bancoAlunos.add(aluno); }
    public List<Aluno> listarAlunos() { return bancoAlunos; }

    public Aluno buscarAlunoPorMatricula(String matricula) throws RegistroNaoEncontradoException {
        return bancoAlunos.stream()
                .filter(a -> a.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Aluno não encontrado com a matrícula: " + matricula));
    }

    public void atualizarNomeAluno(String matricula, String novoNome) throws RegistroNaoEncontradoException {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        aluno.setNome(novoNome);
    }

    public void removerAluno(String matricula) throws RegistroNaoEncontradoException {
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        bancoAlunos.remove(aluno);
        for (Turma t : bancoTurmas) {
            t.removerMatricula(matricula);
        }
    }

    public void cadastrarCurso(Curso curso) { bancoCursos.add(curso); }
    public List<Curso> listarCursos() { return bancoCursos; }

    public void cadastrarTurma(Turma turma) { bancoTurmas.add(turma); }
    public List<Turma> listarTurmas() { return bancoTurmas; }

    public Turma buscarTurmaPorCodigo(String codigo) throws RegistroNaoEncontradoException {
        return bancoTurmas.stream()
                .filter(t -> t.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Turma não encontrada: " + codigo));
    }

    public Map<Turma, List<Aluno>> mapearAlunosPorTurma() {
        Map<Turma, List<Aluno>> mapa = new HashMap<>();
        for (Turma turma : bancoTurmas) {
            List<Aluno> alunos = turma.getListaMatriculas().stream()
                    .filter(m -> m.getSituacao() == SituacaoMatricula.ATIVA)
                    .map(Matricula::getAluno)
                    .collect(Collectors.toList());
            mapa.put(turma, alunos);
        }
        return mapa;
    }

    public List<Aluno> listarAlunosAptosAvancar(Turma turma) {
        return turma.getListaMatriculas().stream()
                .filter(m -> m.getSituacao() == SituacaoMatricula.CONCLUIDA)
                .map(Matricula::getAluno)
                .collect(Collectors.toList());
    }
}