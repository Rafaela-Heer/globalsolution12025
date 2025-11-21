package br.com.globalsolution.entities;

public class Competencia {

    private final String nome;
    private final String descricao;

    public Competencia(String nome, String descricao) {
        if (nome == null || nome.isEmpty()) throw new IllegalArgumentException("Nome obrigatório");
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Competencia that = (Competencia) obj;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() { return nome.hashCode(); }
}
