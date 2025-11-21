package br.com.globalsolution.ui;

import br.com.globalsolution.application.services.GestaoService;
import br.com.globalsolution.entities.Colaborador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelatorioPainel extends JPanel {
    private GestaoService service;
    private JTextArea relatorioArea;
    private JButton btnGerarRelatorio;

    public RelatorioPainel(GestaoService service) {
        this.service = service;
        setLayout(new BorderLayout());

        btnGerarRelatorio = new JButton("Gerar Relatório de Desempenho");
        btnGerarRelatorio.addActionListener(e -> gerarRelatorio());

        relatorioArea = new JTextArea();
        relatorioArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(relatorioArea);

        add(btnGerarRelatorio, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void gerarRelatorio() {
        List<Colaborador> colaboradores = service.getColaboradores();
        StringBuilder sb = new StringBuilder();
        for (Colaborador c : colaboradores) {
            sb.append("Colaborador: ").append(c.getNome()).append("\n");
            sb.append("Competências:\n");
            c.getCompetencias().forEach(cn -> {
                sb.append(" - ").append(cn.getCompetencia().getNome())
                        .append(": Nível ").append(cn.getNivel()).append("\n");
            });
            sb.append("\n");
        }
        relatorioArea.setText(sb.toString());
    }
}
