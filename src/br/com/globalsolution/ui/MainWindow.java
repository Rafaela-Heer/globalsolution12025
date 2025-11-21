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





class RecomendacaoPainel extends JPanel {
    private GestaoService service;
    private JComboBox<String> colaboradorCombo;
    private JButton recomendarBtn;
    private JTextArea resultadoArea;

    public RecomendacaoPainel(GestaoService service) {
        this.service = service;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Selecionar Colaborador:"), gbc);
        gbc.gridx = 1;
        colaboradorCombo = new JComboBox<>();

        service.getColaboradores().forEach(c -> colaboradorCombo.addItem(c.getNome() + " (" + c.getId() + ")"));
        add(colaboradorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        recomendarBtn = new JButton("Gerar Recomendações");
        recomendarBtn.addActionListener(e -> {
            String selected = (String) colaboradorCombo.getSelectedItem();
            if (selected != null) {
                String id = selected.split("\\(")[1].replace(")", "");  // Extrair ID
                List<Treinamento> recomendacoes = service.recomendar(id);
                StringBuilder sb = new StringBuilder("Recomendações:\n");
                recomendacoes.forEach(t -> sb.append("- ").append(t.getNome()).append("\n"));
                resultadoArea.setText(sb.toString());
            }
        });
        add(recomendarBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), gbc);
    }
}
