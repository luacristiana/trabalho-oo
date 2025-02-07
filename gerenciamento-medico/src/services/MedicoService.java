package services;

import entidades.Medico;
import storage.StorageData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MedicoService {
    public static void menuMedicos(Scanner scanner) {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Gerenciar Médicos");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Editar Médico");
            System.out.println("4. Remover Médico");
            System.out.println("5. Voltar ao Menu Principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarMedico(scanner);
                    break;
                case 2:
                    listarMedicos();
                    break;
                case 3:
                    editarMedico(scanner);
                    break;
                case 4:
                    removerMedico(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void cadastrarMedico(Scanner scanner) {
        System.out.print("Digite o nome do médico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do médico (XXX.XXX.XXX-XX): ");
        String cpf = scanner.nextLine();

        if (StorageData.medicData.stream().anyMatch(m -> m.getCpf().equals(cpf))) {
            System.out.println("Erro: CPF já cadastrado!");
            return;
        }

        System.out.print("Digite a data de nascimento (dd-MM-yyyy): ");
        String dataNascimentoStr = scanner.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.print("Digite o CRM do médico: ");
        String crm = scanner.nextLine();

        System.out.print("Digite a especialidade do médico: ");
        String especialidade = scanner.nextLine();

        Medico medico = new Medico(nome, cpf, dataNascimento, crm, especialidade);
        StorageData.medicData.add(medico);

        System.out.println("\nMédico cadastrado com sucesso!");
    }

    public static void listarMedicos() {
        if (StorageData.medicData.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
            return;
        }

        for (int i = 0; i < StorageData.medicData.size(); i++) {
            Medico medico = StorageData.medicData.get(i);
            System.out.printf("%d. Nome: %s | CPF: %s | CRM: %s | Especialidade: %s%n",
                    i + 1, medico.getNome(), medico.getCpf(), medico.getCrm(), medico.getEspecialidade());
        }
    }

    public static void editarMedico(Scanner scanner) {
        listarMedicos();
        if (StorageData.medicData.isEmpty()) return;

        System.out.print("\nDigite o número do médico que deseja editar: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.medicData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Medico medico = StorageData.medicData.get(index);

        scanner.nextLine(); // Limpar buffer
        System.out.print("Novo nome (" + medico.getNome() + "): ");
        String novoNome = scanner.nextLine();

        if (!novoNome.isEmpty()) medico.setNome(novoNome);

        System.out.print("Nova data de nascimento (" + medico.getDataNascimento() + " - formato dd-MM-yyyy): ");
        String novaDataStr = scanner.nextLine();

        if (!novaDataStr.isEmpty()) {
            LocalDate novaData = LocalDate.parse(novaDataStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            medico.setDataNascimento(novaData);
        }

        System.out.print("Nova especialidade (" + medico.getEspecialidade() + "): ");
        String novaEspecialidade = scanner.nextLine();

        if (!novaEspecialidade.isEmpty()) medico.setEspecialidade(novaEspecialidade);

        System.out.print("Novo CRM (" + medico.getCrm() + "): ");
        String novoCrm = scanner.nextLine();

        if (!novoCrm.isEmpty()) medico.setCrm(novoCrm);

        System.out.println("\nMédico atualizado com sucesso!");
    }

    public static void removerMedico(Scanner scanner) {
        listarMedicos();
        if (StorageData.medicData.isEmpty()) return;

        System.out.print("\nDigite o número do médico que deseja remover: ");
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= StorageData.medicData.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Medico removido = StorageData.medicData.remove(index);
        System.out.printf("\nMédico '%s' removido com sucesso!%n", removido.getNome());
    }

}
