package br.com.globalsolution.ui;

import br.com.globalsolution.application.services.GestaoService;
import br.com.globalsolution.entities.Competencia;
import br.com.globalsolution.entities.Treinamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreinamentoPainel extends JPanel {
    private GestaoService service;

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtCompetencias;
    private JTable tabelaTreinamentos;
    private DefaultTableModel model;

    public TreinamentoPainel(GestaoService service) {
        this.service = service;
        setLayout(new BorderLayout());

        // Painel cadastro
        JPanel painelCad = new JPanel(new GridLayout(4, 2, 5, 5));
        painelCad.setBorder(BorderFactory.createTitledBorder("Cadastrar Treinamento"));

        painelCad.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCad.add(txtNome);

        painelCad.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelCad.add(txtDescricao);

        painelCad.add(new JLabel("Competências (nome: descrição; separado por ';'):"));
        txtCompetencias = new JTextField();
        painelCad.add(txtCompetencias);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarTreinamento());
        painelCad.add(btnCadastrar);

        add(painelCad, BorderLayout.NORTH);

        // Tabela treinamentos
        model = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Competências"}, 0);
        tabelaTreinamentos = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabelaTreinamentos);

        add(scroll, BorderLayout.CENTER);

        carregarTreinamentos();
    }

    private void cadastrarTreinamento() {
        try {
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String competenciasStr = txtCompetencias.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Competencia> competencias = new ArrayList<>();
            if (!competenciasStr.isEmpty()) {
                String[] compsArr = competenciasStr.split(";");
                for (String c : compsArr) {
                    String[] partes = c.split(":");
                    if (partes.length == 2) {
                        competencias.add(new Competencia(partes[0].trim(), partes[1].trim()));
                    } else {
                        JOptionPane.showMessageDialog(this, "Formato de competências inválido. Use nome: descrição;", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            String id = java.util.UUID.randomUUID().toString();
            Treinamento treinamento = new Treinamento(id, nome, descricao, competencias);
            service.cadastrarTreinamento(treinamento);

            JOptionPane.showMessageDialog(this, "Treinamento cadastrado com sucesso!");
            limparCampos();
            carregarTreinamentos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtCompetencias.setText("");
    }

    private void carregarTreinamentos() {
        model.setRowCount(0);
        List<Treinamento> lista = service.getTreinamentos();
        for (Treinamento t : lista) {
            StringBuilder compStr = new StringBuilder();
            for (Competencia c : t.getCompetenciasDesenvolvidas()) {
                compStr.append(c.getNome()).append(":").append(c.getDescricao()).append("; ");
            }
            model.addRow(new Object[]{t.getId(), t.getNome(), t.getDescricao(), compStr.toString()});
        }
    }
}
