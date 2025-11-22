package br.com.globalsolution.ui;

import br.com.globalsolution.application.services.GestaoService;
import br.com.globalsolution.entities.*;
import br.com.globalsolution.infrastructure.repositories.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {
    private GestaoService gestaoService;

    public MainWindow() {
        InMemoryColaboradorRepository colaboradorRepo = new InMemoryColaboradorRepository();
        colaboradorRepo.carregarDeArquivo();

        InMemoryTreinamentoRepository treinamentoRepo = new InMemoryTreinamentoRepository();
        treinamentoRepo.carregarDeArquivo();

        gestaoService = new GestaoService(colaboradorRepo, treinamentoRepo);

        setTitle("Gestão Cognitiva de Talentos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Colaboradores", new ColaboradorPainel(gestaoService));
        tabbedPane.addTab("Treinamentos", new TreinamentoPainel(gestaoService));
        tabbedPane.addTab("Recomendações", new RecomendacaoPainel(gestaoService));
        tabbedPane.addTab("Relatórios", new RelatorioPainel(gestaoService));
        add(tabbedPane);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
