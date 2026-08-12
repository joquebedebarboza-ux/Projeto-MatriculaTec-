package br.edu.cetam.matriculatec.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Modulo {
    private String nome;
    private List<Disciplina> listaDisciplinas;

    public Modulo(String nome) {
        this.nome = nome;
        this.listaDisciplinas = new ArrayList<>();
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        this.listaDisciplinas.add(disciplina);
    }

    public String getNome() { 
        return nome; 
    }

    public List<Disciplina> getListaDisciplinas() { 
        return Collections.unmodifiableList(listaDisciplinas); 
    }

    public int getCargaHorariaTotal() {
        return listaDisciplinas.stream().mapToInt(Disciplina::getCargaHoraria).sum();
    }

    @Override
    public String toString() {
        return nome;
    }
}