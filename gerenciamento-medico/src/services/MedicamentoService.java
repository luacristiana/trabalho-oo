package services;

import entidades.Consulta;
import entidades.Medicamento;
import storage.StorageData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MedicamentoService {
    public static void menuMedicamentos(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciamento de Medicamentos");
            System.out.println("1. Prescrever Medicamento");
            System.out.println("2. Listar Medicamentos");
            System.out.println("3. Editar Medicamento");
            System.out.println("4. Remover Medicamento");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Digite uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    prescreverMedicamento(scanner);
                    break;
                case 2:
                    listarMedicamentos();
                    break;
                case 3:
                    editarMedicamento(scanner);
                    break;
                case 4:
                    removerMedicamento(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void prescreverMedicamento(Scanner scanner) {
        if (StorageData.consultData.isEmpty()) {
            System.out.println("Erro: Não há consultas cadastradas para associar um medicamento.");
            return;
        }

        System.out.println("Selecione uma consulta para associar o medicamento:");
        for (int i = 0; i < StorageData.consultData.size(); i++) {
            Consulta consulta = StorageData.consultData.get(i);
            System.out.printf("%d. Paciente: %s | Médico: %s | Data: %s%n",
                    i + 1,
                    consulta.getPaciente().getNome(),
                    consulta.getMedico().getNome(),
                    consulta.getDataConsulta().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }
        System.out.print("Digite o número da consulta: ");
        int consultaIndex = scanner.nextInt() - 1;

        if (consultaIndex < 0 || consultaIndex >= StorageData.consultData.size()) {
            System.out.println("Erro: Consulta inválida.");
            return;
        }
        Consulta consultaAssociada = StorageData.consultData.get(consultaIndex);

        scanner.nextLine();
        System.out.print("Digite o nome do medicamento: ");
        String nomeMedicamento = scanner.nextLine();

        LocalDateTime dataPrescricao = LocalDateTime.now();

        Medicamento medicamento = new Medicamento(dataPrescricao, consultaAssociada, nomeMedicamento);
        StorageData.medicationData.add(medicamento);

        System.out.println("\nMedicamento prescrito com sucesso!");
    }

    public static void listarMedicamentos() {
        if (StorageData.medicationData.isEmpty()) {
            System.out.println("Nenhum medicamento prescrito.");
            return;
        }

        for (int i = 0; i < StorageData.medicationData.size(); i++) {
            Medicamento medicamento = StorageData.medicationData.get(i);
            System.out.printf("%d. Nome: %s | Paciente: %s | Médico: %s | Data Prescrição: %s%n",
                    i + 1,
                    medicamento.getNomeMedicamento(),
                    medicamento.getConsultaAssociada().getPaciente().getNome(),
                    medicamento.getConsultaAssociada().getMedico().getNome(),
                    medicamento.getDataPrescricao());
        }
    }

    public static void editarMedicamento(Scanner scanner) {
        listarMedicamentos();
        if (StorageData.medicationData.isEmpty()) return;

        System.out.print("\nDigite o número do medicamento que deseja editar: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.medicationData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Medicamento medicamento = StorageData.medicationData.get(index);

        scanner.nextLine();
        System.out.print("Novo nome do medicamento (" + medicamento.getNomeMedicamento() + "): ");
        String novoNome = scanner.nextLine();

        if (!novoNome.isEmpty()) medicamento.setNomeMedicamento(novoNome);

        System.out.println("\nMedicamento atualizado com sucesso!");
    }

    public static void removerMedicamento(Scanner scanner) {
        listarMedicamentos();
        if (StorageData.medicationData.isEmpty()) return;

        System.out.print("\nDigite o número do medicamento que deseja remover: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.medicationData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Medicamento removido = StorageData.medicationData.remove(index);

        Consulta consultaAssociada = removido.getConsultaAssociada();

        // TODO: adicionar lógica para atualizar ou notificar a consulta associada

        System.out.printf("\nMedicamento '%s' removido com sucesso!%n", removido.getNomeMedicamento());
    }
}
