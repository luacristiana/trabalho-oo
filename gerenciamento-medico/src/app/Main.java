package app;

import entidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

    private static List<Paciente> listaPacientes = new ArrayList<>();
    private static List<Medico> listaMedicos = new ArrayList<>();
    private static List<Consulta> listaConsultas = new ArrayList<>();

    public static void menu(){
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("----------------------------------");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar um Médico");
            System.out.println("3. Gerenciamento de Consultas");
            System.out.println("4. Sair");
            System.out.print("Digite uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Opção: " + opcao);

            if (opcao == 1) {
                cadastrarPaciente(scanner);
            } else if (opcao == 2) {
                cadastrarMedico(scanner);;
            } else if (opcao == 3) {
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
            } else if (opcao == 4) {
                System.out.println("Saindo...");
                break;
            }
            else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();

    }

    public static void cadastrarPaciente(Scanner scanner) {
        System.out.print("Digite o nome do paciente: ");
        String nome = scanner.nextLine();


        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o ano de nascimento (ex: 1990): ");
        int ano = scanner.nextInt();

        System.out.print("Digite o mês de nascimento (1-12): ");
        int mes = scanner.nextInt();

        System.out.print("Digite o dia de nascimento (1-31): ");
        int dia = scanner.nextInt();

        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        Paciente paciente = new Paciente(nome, cpf, dataNascimento);
        listaPacientes.add(paciente);

        System.out.println("\nPaciente cadastrado:");
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Data de Nascimento: " + paciente.getDataNascimento());
    }

    public static void cadastrarMedico(Scanner scanner) {
        System.out.print("Digite o nome do Medico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do Medico: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o ano de nascimento (ex: 1990): ");
        int ano = scanner.nextInt();

        System.out.print("Digite o mês de nascimento (1-12): ");
        int mes = scanner.nextInt();

        System.out.print("Digite o dia de nascimento (1-31): ");
        int dia = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Digite o crm: ");
        String crm = scanner.nextLine();

        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        Medico medico = new Medico(nome, cpf, dataNascimento, especialidade, crm);

        listaMedicos.add(medico);

        System.out.println("\nMedico cadastrado:");
        System.out.println("Nome: " + medico.getNome());
        System.out.println("CPF: " + medico.getCpf());
        System.out.println("Data de Nascimento: " + medico.getDataNascimento());
        System.out.println("Especialidade: " + medico.getEspecialidade());
        System.out.println("Crm: " + medico.getCrm());
    }

    public static void agendarConsulta(Scanner scanner) {

        System.out.println("Selecione um paciente:");
        for (int i = 0; i < listaPacientes.size(); i++) {
            System.out.println((i + 1) + ". " + listaPacientes.get(i).getNome());
        }
        System.out.print("Digite o número do paciente: ");
        int pacienteIndex = scanner.nextInt();
        Paciente paciente = listaPacientes.get(pacienteIndex - 1);


        System.out.println("Selecione um médico:");
        for (int i = 0; i < listaMedicos.size(); i++) {
            System.out.println((i + 1) + ". " + listaMedicos.get(i).getNome());
        }
        System.out.print("Digite o número do médico: ");
        int medicoIndex = scanner.nextInt();
        Medico medico = listaMedicos.get(medicoIndex - 1);


        System.out.print("Digite a data da consulta (formato: dd-MM-yyyy HH:mm): ");
        scanner.nextLine();  // Limpar o buffer
        String dataHoraStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dataConsulta = LocalDateTime.parse(dataHoraStr, formatter);

        System.out.print("Digite o valor da consulta: ");
        double valorConsulta = scanner.nextDouble();

        Consulta consulta = new Consulta(dataConsulta, paciente, medico, valorConsulta);

        listaConsultas.add(consulta);

        System.out.println("\nConsulta agendada:");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Médico: " + medico.getNome());
        System.out.println("Data e Hora: " + consulta.getDataConsulta());
        System.out.println("Valor da Consulta: " + consulta.getValorConsulta());
    }

    public static void listarConsultas(Scanner scanner) {
        int numConsulta = 1;
        for (Consulta consulta: listaConsultas) {
            System.out.println(numConsulta + ". " + consulta.toString());
            numConsulta += 1;
        }

        System.out.println("Deseja ver os detalhes de uma consulta?(y/n)");
        String decision = scanner.nextLine();

        if (decision.toLowerCase(Locale.getDefault()).equals("y")){
            System.out.println("Digite o número da Consulta que deseja inspecionar: ");
            int choice_number = scanner.nextInt();

            if ((choice_number-1) > listaConsultas.size()) {
                System.out.println("Consulta não existente, retornando ao menu...");
            } else {

                Consulta consulta_escolhida = listaConsultas.get(choice_number - 1);

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
        if (listaConsultas.isEmpty()) {
            System.out.println("Não há consultas agendadas para remover.");
            return;
        }

        System.out.println("Consultas agendadas:");
        for (int i = 0; i < listaConsultas.size(); i++) {
            System.out.println((i + 1) + ". " + listaConsultas.get(i).toString());
        }

        System.out.print("Digite o número da consulta que deseja remover: ");
        int consultaIndex = scanner.nextInt();

        if (consultaIndex < 1 || consultaIndex > listaConsultas.size()) {
            System.out.println("Número inválido! Nenhuma consulta foi removida.");
            return;
        }

        Consulta consultaRemovida = listaConsultas.remove(consultaIndex - 1);
        System.out.println("Consulta removida com sucesso:");
        System.out.println("Paciente: " + consultaRemovida.getPaciente().getNome());
        System.out.println("Médico: " + consultaRemovida.getMedico().getNome());
        System.out.println("Data e Hora: " + consultaRemovida.getDataConsulta());
    }


    public static void main(String[] args) {
        menu();
    }
}


