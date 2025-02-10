package services;

import entidades.Paciente;

import java.util.Scanner;

import storage.StorageData;

public class PagamentoService {
    public static void menuPagamentos(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciamento de Pagamentos");
            System.out.println("1. Listar Pagamentos Pendentes");
            System.out.println("2. Registrar Pagamento");
            System.out.println("3. Voltar ao Menu Principal");
            System.out.print("Digite uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarPagamentosPendentes();
                    break;
                case 2:
                    registrarPagamento(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void listarPagamentosPendentes() {
        if (StorageData.pacientData.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente paciente : StorageData.pacientData) {
            System.out.printf("Paciente: %s | CPF: %s | Pagamentos Pendentes: R$%.2f%n",
                    paciente.getNome(), paciente.getCpf(), paciente.getPagamentosPendentes());
        }
    }

    public static void registrarPagamento(Scanner scanner) {
        listarPagamentosPendentes();
        if (StorageData.pacientData.isEmpty()) return;

        System.out.print("\nDigite o CPF do paciente que deseja registrar o pagamento: ");
        String cpf = scanner.nextLine();

        Paciente paciente = StorageData.pacientData.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);

        if (paciente == null) {
            System.out.println("Erro: Paciente não encontrado.");
            return;
        }

        System.out.print("Digite o valor do pagamento: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Erro: O valor do pagamento deve ser maior que zero.");
            return;
        }

        paciente.registrarPagamento(valor);
        System.out.printf("\nPagamento registrado com sucesso! Novo saldo pendente: R$%.2f%n", paciente.getPagamentosPendentes());
    }

}
