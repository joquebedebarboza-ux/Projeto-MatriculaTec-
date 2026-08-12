package br.edu.cetam.matriculatec.model;

public class Disciplina {
    private String nome;
    private int cargaHoraria;

    public Disciplina(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() { return nome; }
    public int getCargaHoraria() { return cargaHoraria; }

    @Override
    public String toString() {
        return String.format("%s (%dh)", nome, cargaHoraria);
    }
}