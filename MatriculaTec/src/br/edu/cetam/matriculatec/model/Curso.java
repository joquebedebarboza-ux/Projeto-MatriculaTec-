package br.edu.cetam.matriculatec.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Curso {
    private String codigo;
    private String nome;
    private List<Modulo> listaModulos;

    public Curso(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.listaModulos = new ArrayList<>();
    }

    public void adicionarModulo(Modulo modulo) {
        this.listaModulos.add(modulo);
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public List<Modulo> getListaModulos() { 
        return Collections.unmodifiableList(listaModulos); 
    }

    public int getCargaHorariaTotal() {
        return listaModulos.stream().mapToInt(Modulo::getCargaHorariaTotal).sum();
    }

    @Override
    public String toString() {
        return String.format("[%s] Curso Técnico em %s (%dh)", codigo, nome, getCargaHorariaTotal());
    }
}