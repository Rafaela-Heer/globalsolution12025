package br.com.globalsolution.ui;

import br.com.globalsolution.application.services.GestaoService;
import br.com.globalsolution.entities.Colaborador;
import br.com.globalsolution.entities.Treinamento;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RecomendacaoPainel extends JPanel {
    private GestaoService service;
    private JComboBox<String> colaboradorCombo;
    private JButton recomendarBtn;
    private JList<String> recomendacaoList;
    private JButton completarBtn;
    private JTextArea resultadoArea;
    private Map<String, String> colaboradorMap = new HashMap<>();
    private List<Treinamento> recomendacoesAtuais;

    public RecomendacaoPainel(GestaoService service) {
        this.service = service;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Selecionar Colaborador:"), gbc);
        gbc.gridx = 1;
        colaboradorCombo = new JComboBox<>();
        for (Colaborador c : service.getColaboradores()) {
            String display = c.getNome() + " (" + c.getId() + ")";
            colaboradorCombo.addItem(display);
            colaboradorMap.put(display, c.getId());
        }
        add(colaboradorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        recomendarBtn = new JButton("Gerar Recomendações");
        recomendarBtn.addActionListener(e -> gerarRecomendacoes());
        add(recomendarBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        recomendacaoList = new JList<>();
        recomendacaoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(recomendacaoList), gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        completarBtn = new JButton("Completar Treinamento");
        completarBtn.addActionListener(e -> completarTreinamento());
        add(completarBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        resultadoArea = new JTextArea(5, 30);
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), gbc);
    }

    private void gerarRecomendacoes() {
        String selected = (String) colaboradorCombo.getSelectedItem();
        if (selected != null) {
            String id = colaboradorMap.get(selected);
            recomendacoesAtuais = service.recomendar(id);
            DefaultListModel<String> model = new DefaultListModel<>();
            recomendacoesAtuais.forEach(t -> model.addElement(t.getNome() + " (" + t.getId() + ")"));
            recomendacaoList.setModel(model);
            resultadoArea.setText("Recomendações geradas. Selecione um para completar.");
        }
    }

    private void completarTreinamento() {
        String selectedColaborador = (String) colaboradorCombo.getSelectedItem();
        String selectedTreinamentoDisplay = recomendacaoList.getSelectedValue();
        if (selectedColaborador != null && selectedTreinamentoDisplay != null) {
            String colaboradorId = colaboradorMap.get(selectedColaborador);
            int open = selectedTreinamentoDisplay.lastIndexOf('(');
            int close = selectedTreinamentoDisplay.lastIndexOf(')');
            if (open < 0 || close < 0 || close <= open) {
                JOptionPane.showMessageDialog(this, "Formato inválido do item selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String treinamentoId = selectedTreinamentoDisplay.substring(open + 1, close).trim();
            service.completarTreinamento(colaboradorId, treinamentoId);
            JOptionPane.showMessageDialog(this, "Treinamento completo! Níveis atualizados.");
            resultadoArea.setText("Treinamento completo. Gere recomendações novamente para ver mudanças.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um colaborador e um treinamento.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}