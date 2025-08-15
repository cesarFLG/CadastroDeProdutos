package main;


import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;



public class CadastrarProduto {

    public static void Cadastrar() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.println("Digite a Quantidade do produto: ");
        int quantidade = scanner.nextInt();
        System.out.println("Digite o valor do produto: ");
        double preco = scanner.nextDouble();


    try(Connection conexao = BancoDeDados.conectar()){
        String sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setInt(2, quantidade);
        stmt.setDouble(3, preco);

        stmt.executeUpdate();
        System.out.println("Produto Cadastrado Com Sucesso");
        }catch (SQLException e){
        System.out.println("Erro ao Cadastrar Produto" + e.getMessage());
        }
    }
}
