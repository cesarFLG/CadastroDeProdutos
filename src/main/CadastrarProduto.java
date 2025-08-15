package main;


import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;



public class CadastrarProduto {

    public static void Cadastrar() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String nome;
        int quantidade = 0;
        double preco = 0.0;

        while (true) {
            System.out.println("Digite o nome do produto: ");
            nome = scanner.next();
            if (!nome.isBlank()) {
                break;
            }else{
                System.out.println("ERRO! O Nome Não Pode Estar Vazio.");
            }
        }

        while (true) {
            System.out.println("Digite o quantidade do produto: ");
            quantidade = scanner.nextInt();
            if (quantidade <= 0) {
                break;
            }else{
                System.out.println( "ERRO! A Quantidade Deve Ser Maior Que Zero");
            }
    }



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
