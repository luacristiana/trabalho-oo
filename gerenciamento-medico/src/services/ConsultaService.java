package services;

import entidades.Consulta;
import entidades.Medico;
import entidades.Paciente;
import storage.StorageData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class ConsultaService {

    public static void menuConsulta(Scanner scanner){
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciamento de Consultas");
            System.out.println("1. Cadastrar uma consulta");
            System.out.println("2. Listar consultas");
            System.out.println("3. Remover um consulta");
            System.out.println("4. Sair do menu de consultas");

            int opcaoConsulta = scanner.nextInt();

            if (opcaoConsulta == 1) {
                agendarConsulta(scanner);
            } else if (opcaoConsulta == 2) {
                listarConsultas(scanner);
            } else if (opcaoConsulta == 3) {
                removeConsulta(scanner);
            } else {
                System.out.println("Saindo do menu Consultas");
                break;
            }
        }
    }

    public static void agendarConsulta(Scanner scanner) {
        if (StorageData.pacientData.isEmpty() || StorageData.medicData.isEmpty()) {
            System.out.println("Erro: Não há pacientes ou médicos cadastrados para agendar consultas.");
            return;
        }

        System.out.println("Selecione um paciente:");
        for (int i = 0; i < StorageData.pacientData.size(); i++) {
            Paciente paciente = StorageData.pacientData.get(i);
            System.out.printf("%d. Nome: %s | CPF: %s | Pagamentos Pendentes: R$%.2f%n",
                    i + 1, paciente.getNome(), paciente.getCpf(), paciente.getPagamentosPendentes());
        }
        System.out.print("Digite o número do paciente: ");
        int pacienteIndex = scanner.nextInt() - 1;

        if (pacienteIndex < 0 || pacienteIndex >= StorageData.pacientData.size()) {
            System.out.println("Erro: Paciente inválido.");
            return;
        }

        Paciente paciente = StorageData.pacientData.get(pacienteIndex);

        if (paciente.temPagamentosPendentes()) {
            System.out.printf("Erro: O paciente '%s' possui pagamentos pendentes no valor de R$%.2f.%n",
                    paciente.getNome(), paciente.getPagamentosPendentes());
            return;
        }

        System.out.println("Selecione um médico:");
        for (int i = 0; i < StorageData.medicData.size(); i++) {
            System.out.println((i + 1) + ". " + StorageData.medicData.get(i).getNome());
        }
        System.out.print("Digite o número do médico: ");
        int medicoIndex = scanner.nextInt();
        Medico medico = StorageData.medicData.get(medicoIndex - 1);


        System.out.print("Digite a data da consulta (formato: dd-MM-yyyy HH:mm): ");
        scanner.nextLine();  // Limpar o buffer
        String dataHoraStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dataConsulta = LocalDateTime.parse(dataHoraStr, formatter);

        System.out.print("Digite o valor da consulta: ");
        double valorConsulta = scanner.nextDouble();

        Consulta consulta = new Consulta(dataConsulta, paciente, medico, valorConsulta);

        consulta.getPaciente().adicionarPagamentoPendente(consulta.getValorConsulta());
        StorageData.consultData.add(consulta);

        System.out.println("\nConsulta agendada:");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Médico: " + medico.getNome());
        System.out.println("Data e Hora: " + consulta.getDataConsulta());
        System.out.println("Valor da Consulta: " + consulta.getValorConsulta());
    }

    public static void listarConsultas(Scanner scanner) {
        int numConsulta = 1;
        for (Consulta consulta: StorageData.consultData) {
            System.out.println(numConsulta + ". " + consulta.toString());
            numConsulta += 1;
        }

        System.out.println("Deseja ver os detalhes de uma consulta?(y/n)");
        String decision = scanner.nextLine();

        if (decision.toLowerCase(Locale.getDefault()).equals("y")){
            System.out.println("Digite o número da Consulta que deseja inspecionar: ");
            int choice_number = scanner.nextInt();

            if ((choice_number-1) > StorageData.consultData.size()) {
                System.out.println("Consulta não existente, retornando ao menu...");
            } else {

                Consulta consulta_escolhida = StorageData.consultData.get(choice_number - 1);

                System.out.println("\nDados da Consulta:");
                System.out.println("Paciente: " + consulta_escolhida.getPaciente().getNome());
                System.out.println("Médico: " + consulta_escolhida.getMedico().getNome());
                System.out.println("Data e Hora: " + consulta_escolhida.getDataConsulta());
                System.out.println("Valor da Consulta: " + consulta_escolhida.getValorConsulta());
            }

        } else {
            System.out.println("Voltando ao menu...");
        }

    }

    public static void removeConsulta(Scanner scanner) {
        if (StorageData.consultData.isEmpty()) {
            System.out.println("Não há consultas agendadas para remover.");
            return;
        }

        System.out.println("Consultas agendadas:");
        for (int i = 0; i < StorageData.consultData.size(); i++) {
            System.out.println((i + 1) + ". " + StorageData.consultData.get(i).toString());
        }

        System.out.print("Digite o número da consulta que deseja remover: ");
        int consultaIndex = scanner.nextInt();

        if (consultaIndex < 1 || consultaIndex > StorageData.consultData.size()) {
            System.out.println("Número inválido! Nenhuma consulta foi removida.");
            return;
        }

        Consulta consultaRemovida = StorageData.consultData.remove(consultaIndex - 1);
        System.out.println("Consulta removida com sucesso:");
        System.out.println("Paciente: " + consultaRemovida.getPaciente().getNome());
        System.out.println("Médico: " + consultaRemovida.getMedico().getNome());
        System.out.println("Data e Hora: " + consultaRemovida.getDataConsulta());
    }
}
