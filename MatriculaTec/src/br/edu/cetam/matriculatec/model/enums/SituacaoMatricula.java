package br.edu.cetam.matriculatec.model.enums;

public enum SituacaoMatricula {
    ATIVA("Ativa"),
    TRANCADA("Trancada"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    SituacaoMatricula(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}