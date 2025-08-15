package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EditarProduto {
    private static final Scanner scanner = new Scanner(System.in);

    public static void Editar() {
        System.out.println("\n=== EDIÇÃO DE PRODUTO ===");

        try (Connection conn = BancoDeDados.conectar()) {

            listarProdutos(conn);

            System.out.print("\nDigite o ID do produto a ser editado: ");
            int idProduto = lerInteiro();

            if (!existeProduto(conn, idProduto)) {
                System.out.println("Produto não encontrado!");
                return;
            }

            exibirMenuEdicao(conn, idProduto);

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    private static void listarProdutos(Connection conn) throws SQLException {
        String sql = "SELECT id, nome, preco, quantidade FROM produtos";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nLista de Produtos:");
            System.out.println("ID | Nome | Preço | Quantidade");
            while (rs.next()) {
                System.out.printf("%d | %s | R$%.2f | %d%n",
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"));
            }
        }
    }

    private static boolean existeProduto(Connection conn, int id) throws SQLException {
        String sql = "SELECT 1 FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeQuery().next();
        }
    }

    private static void exibirMenuEdicao(Connection conn, int idProduto) throws SQLException {
        int opcao;
        do {
            System.out.println("\nO que deseja editar?");
            System.out.println("1. Nome");
            System.out.println("2. Preço");
            System.out.println("3. Quantidade");
            System.out.println("4. Todos os campos");
            System.out.println("0. Cancelar");

            System.out.print("Opção: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> atualizarCampo(conn, idProduto, "nome");
                case 2 -> atualizarCampo(conn, idProduto, "preco");
                case 3 -> atualizarCampo(conn, idProduto, "quantidade");
                case 4 -> atualizarTodosCampos(conn, idProduto);
                case 0 -> System.out.println("Edição cancelada.");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void atualizarCampo(Connection conn, int id, String campo) throws SQLException {
        System.out.printf("\nNovo %s: ", campo.equals("preco") ? "preço (R$)" : campo);

        String sql = String.format("UPDATE produtos SET %s = ? WHERE id = ?", campo);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (campo) {
                case "nome" -> {
                    String novoNome = scanner.nextLine();
                    stmt.setString(1, novoNome);
                }
                case "preco" -> {
                    double novoPreco = lerDouble();
                    stmt.setDouble(1, novoPreco);
                }
                case "quantidade" -> {
                    int novaQuantidade = lerInteiro();
                    stmt.setInt(1, novaQuantidade);
                }
            }

            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Nenhuma alteração realizada.");
            }
        }
    }

    private static void atualizarTodosCampos(Connection conn, int id) throws SQLException {
        System.out.println("\n=== Editar Todos os Campos ===");

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();

        System.out.print("Novo preço (R$): ");
        double novoPreco = lerDouble();

        System.out.print("Nova quantidade: ");
        int novaQuantidade = lerInteiro();

        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setDouble(2, novoPreco);
            stmt.setInt(3, novaQuantidade);
            stmt.setInt(4, id);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Produto atualizado!" : "Nenhuma alteração realizada.");
        }
    }

    // Métodos auxiliares para validação de entrada
    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número inteiro válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static double lerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, digite um número decimal válido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }
}