package br.com.globalsolution.repositories;

import br.com.globalsolution.entities.Treinamento;
import java.util.List;

public interface TreinamentoRepository {
    void save(Treinamento treinamento);
    Treinamento findById(String id);
    List<Treinamento> findAll();
    void delete(String id);
}