package com.agencia.viagens.model;

public class AvaliacaoRequest {
    
    private Integer nota;

    public AvaliacaoRequest() {
    }

    public AvaliacaoRequest(Integer nota) {
        this.nota = nota;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
