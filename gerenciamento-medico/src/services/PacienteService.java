package services;

import entidades.*;
import storage.StorageData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PacienteService {
    public static void menuPacientes(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciar Pacientes");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Editar Paciente");
            System.out.println("4. Remover Paciente");
            System.out.println("5. Voltar ao Menu Principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPaciente(scanner);
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    editarPaciente(scanner);
                    break;
                case 4:
                    removerPaciente(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void cadastrarPaciente(Scanner scanner) {
        System.out.print("Digite o nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do paciente (XXX.XXX.XXX-XX): ");
        String cpf = scanner.nextLine();

        if (StorageData.pacientData.stream().anyMatch(p -> p.getCpf().equals(cpf))) {
            System.out.println("Erro: CPF já cadastrado!");
            return;
        }

        System.out.print("Digite a data de nascimento (dd-MM-yyyy): ");
        String dataNascimentoStr = scanner.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Paciente paciente = new Paciente(nome, cpf, dataNascimento);
        StorageData.pacientData.add(paciente);

        System.out.println("\nPaciente cadastrado com sucesso!");
    }

    public static void listarPacientes() {
        if (StorageData.pacientData.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (int i = 0; i < StorageData.pacientData.size(); i++) {
            Paciente paciente = StorageData.pacientData.get(i);
            System.out.printf("%d. Nome: %s | CPF: %s | Data de Nascimento: %s%n",
                    i + 1, paciente.getNome(), paciente.getCpf(), paciente.getDataNascimento());
        }
    }

    public static void editarPaciente(Scanner scanner) {
        listarPacientes();
        if (StorageData.pacientData.isEmpty()) return;

        System.out.print("\nDigite o número do paciente que deseja editar: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.pacientData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Paciente paciente = StorageData.pacientData.get(index);

        scanner.nextLine(); // Limpar buffer
        System.out.print("Novo nome (" + paciente.getNome() + "): ");
        String novoNome = scanner.nextLine();

        if (!novoNome.isEmpty()) paciente.setNome(novoNome);

        System.out.print("Nova data de nascimento (" + paciente.getDataNascimento() + " - formato dd-MM-yyyy): ");
        String novaDataStr = scanner.nextLine();

        if (!novaDataStr.isEmpty()) {
            LocalDate novaData = LocalDate.parse(novaDataStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            paciente.setDataNascimento(novaData);
        }

        System.out.println("\nPaciente atualizado com sucesso!");
    }

    public static void removerPaciente(Scanner scanner) {
        listarPacientes();
        if (StorageData.pacientData.isEmpty()) return;

        System.out.print("\nDigite o número do paciente que deseja remover: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.pacientData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Paciente removido = StorageData.pacientData.remove(index);
        System.out.printf("\nPaciente '%s' removido com sucesso!%n", removido.getNome());
    }
}
