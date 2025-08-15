package main;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoverProduto {
    public static void Excluir() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o ID do produto a Excluir : ");
        int id = scanner.nextInt();

        try (Connection conexao = BancoDeDados.conectar()){
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Produto excluido com sucesso!");
            }else{
                System.out.println("Nenhum Produto Encontrado Com Este ID!");
            }

        }catch (SQLException e){
            System.out.println("Erro ao excluir Produto"+ e.getMessage());
        }
    }


    }




