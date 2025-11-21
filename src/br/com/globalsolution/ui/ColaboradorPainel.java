package br.com.globalsolution.ui;

import br.com.globalsolution.application.services.GestaoService;
import br.com.globalsolution.entities.Colaborador;
import br.com.globalsolution.entities.Competencia;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorPainel extends JPanel {
    private GestaoService service;

    private JTextField nomeField;
    private JTextArea competenciasArea;
    private JButton cadastrarBtn;

    public ColaboradorPainel(GestaoService service) {
        this.service = service;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(20);
        add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("<html>Competências (formato:<br>nome:nivel; separados por ';'):</html>"), gbc);
        gbc.gridx = 1;
        competenciasArea = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(competenciasArea);
        add(scroll, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        cadastrarBtn = new JButton("Cadastrar");
        cadastrarBtn.addActionListener(e -> cadastrarColaborador());
        add(cadastrarBtn, gbc);
    }

    private void cadastrarColaborador() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Colaborador colaborador = new Colaborador(java.util.UUID.randomUUID().toString(), nome);

        String compStr = competenciasArea.getText().trim();
        if (!compStr.isEmpty()) {
            String[] comps = compStr.split(";");
            for (String c : comps) {
                String[] partes = c.split(":");
                if (partes.length == 2) {
                    String nomeComp = partes[0].trim();
                    int nivel;
                    try {
                        nivel = Integer.parseInt(partes[1].trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Nível deve ser número inteiro", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    colaborador.adicionarCompetencia(new Competencia(nomeComp, ""), nivel);
                } else {
                    JOptionPane.showMessageDialog(this, "Formato das competências inválido. Use nome:nivel;", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        service.cadastrarColaborador(colaborador);
        JOptionPane.showMessageDialog(this, "Colaborador cadastrado com sucesso!");
        nomeField.setText("");
        competenciasArea.setText("");
    }
}
