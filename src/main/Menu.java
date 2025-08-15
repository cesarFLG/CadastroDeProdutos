package main;

import java.util.Scanner;



public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BancoDeDados.CriarTabela();

    while (true){
        System.out.println("\n==== MENU Do Sistema ====");
        System.out.println("1.Cadastrar Produto");
        System.out.println("2.Remover Produto");
        System.out.println("3.Listar Produtos");
        System.out.println("4.Editar Produto");
        System.out.println("5.Gerar Relatorio De Estoque");
        System.out.println("6.Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> CadastrarProduto.Cadastrar();
            case 2 -> ExcluirProduto.Excluir();
            case 3 -> ListarProdutos.Listar();
            case 4 -> EditarProduto.Editar();
            case 5 -> GerarRelatorio.Relatorio();
            case 0 -> {
                System.out.println("Saindo...");
                return;
            }
            default -> System.out.println("Opção inválida!");
        }
    }
}
}
