package br.edu.cetam.matriculatec.model;

import br.edu.cetam.matriculatec.exception.MatriculaDuplicadaException;
import br.edu.cetam.matriculatec.exception.VagasEsgotadasException;
import br.edu.cetam.matriculatec.model.enums.SituacaoMatricula;
import br.edu.cetam.matriculatec.model.interfaces.RelatorioExportavel;

import java.util.ArrayList;
import java.util.List;

public class Turma implements RelatorioExportavel {
    private String codigo;
    private Modulo modulo;
    private int limiteVagas;
    private List<Matricula> listaMatriculas;

    public Turma(String codigo, Modulo modulo, int limiteVagas) {
        this.codigo = codigo;
        this.modulo = modulo;
        this.limiteVagas = limiteVagas;
        this.listaMatriculas = new ArrayList<>();
    }

    public void matricularAluno(Aluno aluno) throws VagasEsgotadasException, MatriculaDuplicadaException {
        if (getVagasOcupadas() >= limiteVagas) {
            throw new VagasEsgotadasException("Limite de vagas (" + limiteVagas + ") atingido na turma " + codigo);
        }

        boolean duplicado = listaMatriculas.stream()
                .anyMatch(m -> m.getAluno().getMatricula().equalsIgnoreCase(aluno.getMatricula()));

        if (duplicado) {
            throw new MatriculaDuplicadaException("Aluno " + aluno.getNome() + " já está matriculado nesta turma.");
        }

        this.listaMatriculas.add(new Matricula(aluno, this));
    }

    public void removerMatricula(String matriculaAluno) {
        listaMatriculas.removeIf(m -> m.getAluno().getMatricula().equalsIgnoreCase(matriculaAluno));
    }

    public long getVagasOcupadas() {
        return listaMatriculas.stream()
                .filter(m -> m.getSituacao() == SituacaoMatricula.ATIVA || m.getSituacao() == SituacaoMatricula.CONCLUIDA)
                .count();
    }

    public double getTaxaOcupacao() {
        if (limiteVagas == 0) return 0.0;
        return ((double) getVagasOcupadas() / limiteVagas) * 100;
    }

    public String getCodigo() { return codigo; }
    public Modulo getModulo() { return modulo; }
    public int getLimiteVagas() { return limiteVagas; }
    public List<Matricula> getListaMatriculas() { return listaMatriculas; }

    @Override
    public String gerarRelatorio() {
        return String.format("TURMA %s [%s] | Ocupação: %d/%d (%.1f%%)",
                codigo, modulo.getNome(), getVagasOcupadas(), limiteVagas, getTaxaOcupacao());
    }
}