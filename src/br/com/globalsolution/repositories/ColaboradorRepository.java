package br.com.globalsolution.repositories;

import br.com.globalsolution.entities.Colaborador;
import java.util.List;

public interface ColaboradorRepository {
    void save(Colaborador colaborador);
    Colaborador findById(String id);
    List<Colaborador> findAll();
    void delete(String id);
}
