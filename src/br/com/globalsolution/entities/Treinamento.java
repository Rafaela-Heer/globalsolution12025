package br.com.globalsolution.entities;

import java.util.List;

public class Treinamento {
    private String id;
    private String nome;
    private String descricao;
    private List<Competencia> competenciasDesenvolvidas;

    public Treinamento(String id, String nome, String descricao, List<Competencia> competencias) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.competenciasDesenvolvidas = competencias;
    }

    public String getDescricao() { return descricao; }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public List<Competencia> getCompetenciasDesenvolvidas() { return competenciasDesenvolvidas; }
}
