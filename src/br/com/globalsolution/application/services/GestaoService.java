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

    public void completarTreinamento(String colaboradorId, String treinamentoId) {
        Colaborador colaborador = colaboradorRepo.findById(colaboradorId);
        Treinamento treinamento = treinamentoRepo.findById(treinamentoId);

        if (colaborador != null && treinamento != null) {

            treinamento.getCompetenciasDesenvolvidas().forEach(compTreinamento -> {

                boolean encontrada = false;

                for (Colaborador.CompetenciaNivel cn : colaborador.getCompetencias()) {
                    if (cn.getCompetencia().equals(compTreinamento)) {
                        cn.setNivel(Math.min(10, cn.getNivel() + 1));
                        encontrada = true;
                        break;
                    }
                }

                if (!encontrada) {
                    colaborador.adicionarCompetencia(compTreinamento, 1);
                }

            });

            colaboradorRepo.save(colaborador);
        }
    }



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