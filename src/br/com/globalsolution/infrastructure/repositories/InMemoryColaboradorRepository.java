package br.com.globalsolution.infrastructure.repositories;


import br.com.globalsolution.entities.Colaborador;
import br.com.globalsolution.entities.Competencia;
import br.com.globalsolution.repositories.ColaboradorRepository;
import java.io.*;
import java.util.*;

public class InMemoryColaboradorRepository implements ColaboradorRepository {
    private final Map<String, Colaborador> store = Collections.synchronizedMap(new HashMap<>());


    @Override
    public void save(Colaborador colaborador) {
        store.put(colaborador.getId(), colaborador);
        salvarEmArquivo();
    }

    @Override
    public Colaborador findById(String id) { return store.get(id); }

    @Override
    public List<Colaborador> findAll() { return new ArrayList<>(store.values()); }

    @Override
    public void delete(String id) { store.remove(id); salvarEmArquivo(); }

    private void salvarEmArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data.txt"))) {
            store.values().forEach(c -> {
                String competenciasStr = c.getCompetencias().stream()
                        .map(cn -> cn.getCompetencia().getNome() + ":" + cn.getNivel())
                        .reduce((a, b) -> a + ";" + b)
                        .orElse("");
                pw.println(c.getId() + "," + c.getNome() + "," + competenciasStr);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            br.lines().forEach(line -> {
                String[] parts = line.split(",", 3);
                if (parts.length >= 2) {
                    String id = parts[0];
                    String nome = parts[1];
                    Colaborador c = new Colaborador(id, nome);
                    if (parts.length > 2 && !parts[2].isEmpty()) {
                        String[] compParts = parts[2].split(";");
                        for (String comp : compParts) {
                            String[] compDetails = comp.split(":");
                            if (compDetails.length == 2) {
                                try {
                                    int nivel = Integer.parseInt(compDetails[1].trim());
                                    c.adicionarCompetencia(new Competencia(compDetails[0].trim(), ""), nivel);
                                } catch (NumberFormatException e) {
                                }
                            }
                        }
                    }
                    store.put(id, c);
                }
            });
        } catch (IOException e) {
        e.printStackTrace();
    }
}
}
