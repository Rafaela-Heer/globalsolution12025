package br.com.globalsolution.entities;

import java.time.LocalDate;

public class Avaliacao extends EventoDominio {
    private Colaborador colaborador;
    private int nota;

    public Avaliacao(Colaborador colaborador, int nota, LocalDate data) {
        super(data);
        this.colaborador = colaborador;
        this.nota = nota;
    }

    public void aplicarAvaliacao() {
        colaborador.getCompetencias().forEach(cn -> {
            int novoNivel = Math.min(10, cn.getNivel() + (nota / 10));
            cn.setNivel(novoNivel);
        });
    }

    public Colaborador getColaborador() { return colaborador; }
}
