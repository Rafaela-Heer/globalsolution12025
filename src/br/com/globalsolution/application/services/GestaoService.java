package br.com.globalsolution.application.services;

import br.com.globalsolution.entities.*;
import br.com.globalsolution.repositories.*;
import br.com.globalsolution.services.RecomendacaoService;
import java.util.List;

public class GestaoService {
    private ColaboradorRepository colaboradorRepo;
    private TreinamentoRepository treinamentoRepo;
    private RecomendacaoService recomendacaoService;

    public GestaoService(ColaboradorRepository cr, TreinamentoRepository tr) {
        this.colaboradorRepo = cr;
        this.treinamentoRepo = tr;
        this.recomendacaoService = new RecomendacaoService(cr, tr);
    }

    public void cadastrarColaborador(Colaborador c) {
        colaboradorRepo.save(c);
    }

    // Retorna lista de colaboradores via repositório
    public List<Colaborador> getColaboradores() {
        return colaboradorRepo.findAll();
    }

    public List<Treinamento> recomendar(String id) {
        return recomendacaoService.recomendarTreinamentos(id);
    }

    public void cadastrarTreinamento(Treinamento t) {
        treinamentoRepo.save(t);
    }
    public List<Treinamento> getTreinamentos() {
        return treinamentoRepo.findAll();
    }

}