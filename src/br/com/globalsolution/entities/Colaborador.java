package br.com.globalsolution.entities;

import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    private String id;
    private String nome;
    private List<CompetenciaNivel> competencias;

    public Colaborador(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.competencias = new ArrayList<>();
    }

    public void adicionarCompetencia(Competencia competencia, int nivel) {
        competencias.add(new CompetenciaNivel(competencia, nivel));
    }

    public void atualizarNivel(Competencia competencia, int novoNivel) {
        competencias.stream()
                .filter(cn -> cn.getCompetencia().equals(competencia))
                .findFirst()
                .ifPresent(cn -> cn.setNivel(novoNivel));
    }

    public List<CompetenciaNivel> getCompetencias() { return competencias; }
    public String getId() { return id; }
    public String getNome() { return nome; }

    public static class CompetenciaNivel {
        private Competencia competencia;
        private int nivel;

        public CompetenciaNivel(Competencia competencia, int nivel) {
            this.competencia = competencia;
            this.nivel = nivel;
        }

        public Competencia getCompetencia() { return competencia; }
        public int getNivel() { return nivel; }
        public void setNivel(int nivel) { this.nivel = nivel; }
    }
}
