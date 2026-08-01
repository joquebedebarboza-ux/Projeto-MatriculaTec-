package br.edu.cetam.matriculatec.model;

import br.edu.cetam.matriculatec.model.interfaces.RelatorioExportavel;
import java.time.LocalDate;

public class Aluno extends Pessoa implements RelatorioExportavel {
    private String matricula;

    public Aluno(String nome, String matricula, LocalDate dataNascimento) {
        super(nome, dataNascimento);
        this.matricula = matricula;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    @Override
    public String obterIdentificador() {
        return "MAT-ALUNO: " + matricula;
    }

    @Override
    public String gerarRelatorio() {
        return String.format("Aluno: %-30s | Matrícula: %-10s | Data Nasc: %s", getNome(), matricula, getDataNascimento());
    }

    @Override
    public String toString() {
        return getNome() + " (" + matricula + ")";
    }
}