package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import DAO.ProdutosDAO;
import DTO.ProdutosDTO;

public class vendasVIEW extends JFrame {
    private JTable tabelaProdutosVendidos;
    private JButton btnVoltar;

    public vendasVIEW() {
        initComponents();
        listarProdutosVendidosNaView();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Produtos Vendidos");

        tabelaProdutosVendidos = new JTable(new DefaultTableModel(
                new Object[][] {}, // Inicialmente vazio
                new String[] { "ID", "Nome", "Valor", "Status" }));

        JScrollPane scrollPane = new JScrollPane(tabelaProdutosVendidos);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarTelaAnterior());

        add(scrollPane);
        add(btnVoltar);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
    }

    private void voltarTelaAnterior() {
        this.dispose(); // Fecha a janela de vendas
        // Adicione lógica para voltar à tela anterior, se necessário
    }

    private void listarProdutosVendidosNaView() {
        try {
            ProdutosDAO produtosDAO = new ProdutosDAO();
            DefaultTableModel model = (DefaultTableModel) tabelaProdutosVendidos.getModel();
            model.setNumRows(0);

            ArrayList<ProdutosDTO> produtosVendidos = produtosDAO.listarProdutosVendidos();
            for (ProdutosDTO produto : produtosVendidos) {
                model.addRow(new Object[] {
                        produto.getId(),
                        produto.getNome(),
                        produto.getValor(),
                        produto.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
