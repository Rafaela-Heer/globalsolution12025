package br.com.globalsolution.infrastructure.repositories;


import br.com.globalsolution.entities.Colaborador;
import br.com.globalsolution.repositories.ColaboradorRepository;
import java.io.*;
import java.util.*;

public class InMemoryColaboradorRepository implements ColaboradorRepository {
    private Map<String, Colaborador> store = new HashMap<>();

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
            store.values().forEach(c -> pw.println(c.getId() + "," + c.getNome()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void carregarDeArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            br.lines().forEach(line -> {
                String[] parts = line.split(",");
                store.put(parts[0], new Colaborador(parts[0], parts[1]));
            });
        } catch (IOException e) { /* Arquivo pode não existir */ }
    }
}
