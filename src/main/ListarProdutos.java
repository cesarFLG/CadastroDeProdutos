package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListarProdutos {
    public static void Listar() {
        System.out.println("\n===== LISTA DE PRODUTOS =====");

        String sql = "SELECT * FROM produtos";

        try (Connection conexao = BancoDeDados.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean temResultados = false;

            while (rs.next()) {
                temResultados = true;
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double preco = rs.getDouble("preco");

                System.out.printf("ID: %d | Nome: %s | Quantidade: %d | Preço: R$ %.2f%n",
                        id, nome, quantidade, preco);
            }

            if (!temResultados) {
                System.out.println("Nenhum produto cadastrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }
}

