package services;

import entidades.Consulta;
import entidades.Medico;
import entidades.Paciente;
import excecoes.HorarioIndisponivelException;
import excecoes.PagamentoPendenteException;
import excecoes.EspecialidadeInvalidaException;
import storage.StorageData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;


public class ConsultaService {

    public static void menuConsulta(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciamento de Consultas");
            System.out.println("1. Cadastrar uma consulta");
            System.out.println("2. Listar consultas");
            System.out.println("3. Editar uma consulta");
            System.out.println("4. Remover uma consulta");
            System.out.println("5. Sair do menu de consultas");

            System.out.print("Digite a opção desejada: ");
            int opcaoConsulta = scanner.nextInt();

            switch (opcaoConsulta) {
                case 1:
                    try {
                        agendarConsulta(scanner);
                    } catch (PagamentoPendenteException | EspecialidadeInvalidaException | HorarioIndisponivelException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    listarConsultas(scanner);
                    break;
                case 3:
                    editarConsulta(scanner);
                    break;
                case 4:
                    removerConsulta(scanner);
                    break;
                case 5:
                    System.out.println("Saindo do menu Consultas...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
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
            String errorMensagem = String.format(
                    "Erro: O paciente '%s' possui pagamentos pendentes no valor de R$%.2f.%n",
                    paciente.getNome(), paciente.getPagamentosPendentes()
            );

            throw new PagamentoPendenteException(errorMensagem);
        }

        System.out.println("Selecione um médico:");
        for (int i = 0; i < StorageData.medicData.size(); i++) {
            Medico medico = StorageData.medicData.get(i);
            System.out.printf("%d. Nome: %s | Especialidade: %s%n", i + 1, medico.getNome(), medico.getEspecialidade());
        }
        System.out.print("Digite o número do médico: ");
        int medicoIndex = scanner.nextInt() - 1;

        if (medicoIndex < 0 || medicoIndex >= StorageData.medicData.size()) {
            System.out.println("Erro: Médico inválido.");
            return;
        }
        Medico medico = StorageData.medicData.get(medicoIndex);
        try {
            scanner.nextLine(); // Limpar buffer
            System.out.print("Digite a data da consulta (formato: dd-MM-yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dataConsulta = LocalDateTime.parse(dataHoraStr, formatter);

            // Verificar conflitos de horário
            for (Consulta consultaAgendada : StorageData.consultData) {
                if (consultaAgendada.getMedico().equals(medico)) {
                    LocalDateTime inicioExistente = consultaAgendada.getDataHora();
                    LocalDateTime fimExistente = inicioExistente.plusMinutes(30);
                    LocalDateTime fimNovaConsulta = dataConsulta.plusMinutes(30);

                    boolean conflito = !(dataConsulta.isAfter(fimExistente) || fimNovaConsulta.isBefore(inicioExistente));
                    if (conflito) {
                        String errorMensagem = String.format(
                                "Erro: O horário solicitado (%s) conflita com outra consulta agendada para este médico em %s.",
                                dataConsulta.format(formatter), inicioExistente.format(formatter)
                        );
                        throw new HorarioIndisponivelException(errorMensagem);
                    }
                }
            }

            System.out.print("Digite o valor da consulta (formato: xx.yy): ");
            String valorStr = scanner.nextLine();
            double valorConsulta = Double.parseDouble(valorStr.replace(",", "."));

            System.out.print("Digite a especialidade da Consulta: ");
            String especialidade = scanner.nextLine();

            if (!medico.getEspecialidade().equals(especialidade)) {
                String errorMensagem = String.format(
                        "Erro: O médico '%s' não possui a especialidade requerida para esta consulta: '%s'.",
                        medico.getNome(), especialidade
                );

                throw new EspecialidadeInvalidaException(errorMensagem);
            }


            Consulta consulta = new Consulta(dataConsulta, paciente, medico, valorConsulta, especialidade);

            consulta.getPaciente().adicionarPagamentoPendente(consulta.getValorConsulta());
            StorageData.consultData.add(consulta);

            System.out.println("\nConsulta agendada com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro ao criar consulta: formato da Data e Hora incorreto");
        } catch (NumberFormatException e) {
            System.out.println("Erro ao criar consulta: formato de Valor incorreto");
        } catch (InputMismatchException e) {
            System.out.println("Erro ao criar consulta: entrada inválida");
        }

    }


    public static void listarConsultas(Scanner scanner) {
        if (StorageData.consultData.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
            return;
        }

        for (int i = 0; i < StorageData.consultData.size(); i++) {
            Consulta consulta = StorageData.consultData.get(i);
            System.out.printf("%d. Paciente: %s | Médico: %s | Data e Hora: %s | Valor: R$%.2f%n",
                    i + 1, consulta.getPaciente().getNome(), consulta.getMedico().getNome(),
                    consulta.getDataConsulta().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    consulta.getValorConsulta());
        }
    }

    public static void editarConsulta(Scanner scanner) {
        listarConsultas(scanner);

        if (StorageData.consultData.isEmpty()) return;

        System.out.print("\nDigite o número da consulta que deseja editar: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.consultData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Consulta consulta = StorageData.consultData.get(index);

        scanner.nextLine(); // Limpar buffer
        System.out.print("Nova data e hora da consulta (" + consulta.getDataConsulta().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + "): ");
        String novaDataHoraStr = scanner.nextLine();

        if (!novaDataHoraStr.isEmpty()) {
            LocalDateTime novaDataHora = LocalDateTime.parse(novaDataHoraStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            consulta.setDataConsulta(novaDataHora);
        }

        System.out.print("Novo valor da consulta (" + consulta.getValorConsulta() + "): ");
        String novoValorStr = scanner.nextLine();

        if (!novoValorStr.isEmpty()) {
            double novoValor = Double.parseDouble(novoValorStr);

            double diferencaValor = novoValor - consulta.getValorConsulta();
            consulta.setValorConsulta(novoValor);

            Paciente paciente = consulta.getPaciente();
            paciente.adicionarPagamentoPendente(diferencaValor);

            if (paciente.temPagamentosPendentes()) {
                System.out.printf("\nAviso: O saldo pendente do paciente '%s' foi atualizado para R$%.2f.%n",
                        paciente.getNome(), paciente.getPagamentosPendentes());
            }

        }

        System.out.println("\nConsulta atualizada com sucesso!");
    }

    public static void removerConsulta(Scanner scanner) {
        listarConsultas(scanner);

        if (StorageData.consultData.isEmpty()) {
            System.out.println("Não há consultas cadastradas para remover.");
            return;
        }

        System.out.print("\nDigite o número da consulta que deseja remover: ");
        int consultaIndex = scanner.nextInt() - 1;

        if (consultaIndex < 0 || consultaIndex >= StorageData.consultData.size()) {
            System.out.println("Erro: Número inválido! Nenhuma consulta foi removida.");
            return;
        }

        Consulta consultaRemovida = StorageData.consultData.remove(consultaIndex);

        Paciente paciente = consultaRemovida.getPaciente();
        paciente.registrarPagamento(consultaRemovida.getValorConsulta());

        System.out.println("\nConsulta removida com sucesso!");
        System.out.println("Paciente: " + consultaRemovida.getPaciente().getNome());
        System.out.println("Médico: " + consultaRemovida.getMedico().getNome());
        System.out.println("Data e Hora: " + consultaRemovida.getDataConsulta());
    }
}