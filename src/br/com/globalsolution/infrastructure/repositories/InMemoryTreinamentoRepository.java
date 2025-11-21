package br.com.globalsolution.infrastructure.repositories;

import br.com.globalsolution.entities.Treinamento;
import br.com.globalsolution.entities.Competencia;
import br.com.globalsolution.repositories.TreinamentoRepository;
import java.io.*;
        import java.util.*;

public class InMemoryTreinamentoRepository implements TreinamentoRepository {
    private Map<String, Treinamento> store = new HashMap<>();

    // Método auxiliar para salvar dados em arquivo (persistência simples)
    private void salvarEmArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("treinamentos.txt"))) {
            store.values().forEach(t -> {
                String competenciasStr = t.getCompetenciasDesenvolvidas().stream()
                        .map(c -> c.getNome() + ":" + c.getDescricao())
                        .reduce((a, b) -> a + "; " + b)
                        .orElse("");
                pw.println(t.getId() + "," + t.getNome() + "," + t.getDescricao() + "," + competenciasStr);
            });
        } catch (IOException e) {
            e.printStackTrace();  // Em produção, use logging
        }
    }

    // Método auxiliar para carregar dados do arquivo
    public void carregarDeArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("treinamentos.txt"))) {
            br.lines().forEach(line -> {
                String[] parts = line.split(",", 4);  // Máximo 4 partes: id, nome, descricao, competencias
                if (parts.length >= 3) {
                    String id = parts[0];
                    String nome = parts[1];
                    String descricao = parts[2];
                    List<Competencia> competencias = new ArrayList<>();
                    if (parts.length > 3 && !parts[3].isEmpty()) {
                        String[] compParts = parts[3].split("; ");
                        for (String comp : compParts) {
                            String[] compDetails = comp.split(":");
                            if (compDetails.length == 2) {
                                competencias.add(new Competencia(compDetails[0], compDetails[1]));
                            }
                        }
                    }
                    Treinamento t = new Treinamento(id, nome, descricao, competencias);
                    store.put(id, t);
                }
            });
        } catch (IOException e) {
            // Arquivo pode não existir na primeira execução - ignorar
        }
    }

    @Override
    public void save(Treinamento treinamento) {
        store.put(treinamento.getId(), treinamento);
        salvarEmArquivo();  // Persistir após salvar
    }

    @Override
    public Treinamento findById(String id) {
        return store.get(id);
    }

    @Override
    public List<Treinamento> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
        salvarEmArquivo();  // Persistir após deletar
    }
}