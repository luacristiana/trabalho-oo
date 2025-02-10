package services;

import entidades.Consulta;
import storage.StorageData;
import entidades.Exame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExameService {
    public static void menuExames(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciamento de Exames");
            System.out.println("1. Prescrever Exame");
            System.out.println("2. Listar Exames");
            System.out.println("3. Editar Exame");
            System.out.println("4. Remover Exame");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Digite uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    prescreverExame(scanner);
                    break;
                case 2:
                    listarExames();
                    break;
                case 3:
                    editarExame(scanner);
                    break;
                case 4:
                    removerExame(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void prescreverExame(Scanner scanner) {
        if (StorageData.consultData.isEmpty()) {
            System.out.println("Erro: Não há consultas cadastradas para associar um exame.");
            return;
        }

        // Selecionar consulta associada
        System.out.println("Selecione uma consulta para associar o exame:");
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
        System.out.print("Digite o tipo do exame (ex.: SANGUE, RAIO_X, ULTRASSOM): ");
        String tipo = scanner.nextLine();

        System.out.print("Digite o custo do exame: ");
        double custo = scanner.nextDouble();

        LocalDateTime dataPrescricao = LocalDateTime.now();

        Exame exame = new Exame(dataPrescricao, consultaAssociada, tipo, custo);
        StorageData.examData.add(exame);

        System.out.println("\nExame prescrito com sucesso!");
    }

    public static void listarExames() {
        if (StorageData.examData.isEmpty()) {
            System.out.println("Nenhum exame prescrito.");
            return;
        }

        for (int i = 0; i < StorageData.examData.size(); i++) {
            Exame exame = StorageData.examData.get(i);
            System.out.printf("%d. Tipo: %s | Paciente: %s | Médico: %s | Data Prescrição: %s | Custo: R$%.2f%n",
                    i + 1,
                    exame.getTipo(),
                    exame.getConsultaAssociada().getPaciente().getNome(),
                    exame.getConsultaAssociada().getMedico().getNome(),
                    exame.getDataPrescricao(),
                    exame.calcularCusto());
        }
    }

    public static void editarExame(Scanner scanner) {
        listarExames();
        if (StorageData.examData.isEmpty()) return;

        System.out.print("\nDigite o número do exame que deseja editar: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.examData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Exame exame = StorageData.examData.get(index);

        scanner.nextLine();
        System.out.print("Novo tipo de exame (" + exame.getTipo() + "): ");
        String novoTipo = scanner.nextLine();

        if (!novoTipo.isEmpty()) exame.setTipo(novoTipo);

        System.out.print("Novo custo do exame (" + exame.calcularCusto() + "): ");
        String novoCustoStr = scanner.nextLine();

        if (!novoCustoStr.isEmpty()) {
            double novoCusto = Double.parseDouble(novoCustoStr);
            exame.setCusto(novoCusto);
        }

        System.out.print("Deseja informar a data de realização? (y/n): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("y")) {
            System.out.print("Digite a data de realização (dd-MM-yyyy HH:mm): ");
            String dataRealizacaoStr = scanner.nextLine();
            LocalDateTime dataRealizacao = LocalDateTime.parse(dataRealizacaoStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            exame.setDataRealizacao(dataRealizacao);

            System.out.print("Digite o resultado do exame: ");
            String resultado = scanner.nextLine();
            exame.setResultado(resultado);
        }

        System.out.println("\nExame atualizado com sucesso!");
    }

    public static void removerExame(Scanner scanner) {
        listarExames();
        if (StorageData.examData.isEmpty()) return;

        System.out.print("\nDigite o número do exame que deseja remover: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.examData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Exame removido = StorageData.examData.remove(index);

        Consulta consultaAssociada = removido.getConsultaAssociada();

        // TODO: adicionar lógica para atualizar ou notificar a consulta associada

        System.out.printf("\nExame '%s' removido com sucesso!%n", removido.getTipo());
    }

}
