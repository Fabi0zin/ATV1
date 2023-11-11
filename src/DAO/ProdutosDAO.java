package DAO;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProdutosDTO;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());

            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void venderProduto(int id) {
        try {
            conn = new conectaDAO().connectDB();
            String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM produtos");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listaProdutos.add(produto);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdutos;
    }

    public static ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        try {
            Connection conn = new conectaDAO().connectDB();

            String query = "SELECT * FROM produtos WHERE status = 'Vendido'";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtosVendidos.add(produto);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtosVendidos;
    }
}
