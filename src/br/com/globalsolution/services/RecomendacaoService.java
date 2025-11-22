package br.com.globalsolution.services;

import br.com.globalsolution.entities.*;
import br.com.globalsolution.repositories.ColaboradorRepository;
import br.com.globalsolution.repositories.TreinamentoRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacaoService {
    private ColaboradorRepository colaboradorRepo;
    private TreinamentoRepository treinamentoRepo;

    public RecomendacaoService(ColaboradorRepository cr, TreinamentoRepository tr) {
        this.colaboradorRepo = cr;
        this.treinamentoRepo = tr;
    }

    public List<Treinamento> recomendarTreinamentos(String colaboradorId) {
        Colaborador c = colaboradorRepo.findById(colaboradorId);
        if (c == null) {
            return List.of();
        }
        List<Competencia> lacunas = c.getCompetencias().stream()
                .filter(cn -> cn.getNivel() < 5)
                .map(Colaborador.CompetenciaNivel::getCompetencia)
                .collect(Collectors.toList());
        return treinamentoRepo.findAll().stream()
                .filter(t -> t.getCompetenciasDesenvolvidas().stream().anyMatch(lacunas::contains))
                .collect(Collectors.toList());
    }
}
