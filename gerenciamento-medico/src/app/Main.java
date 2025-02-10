package app;

import java.util.Scanner;

import static services.PacienteService.menuPacientes;
import static services.MedicoService.menuMedicos;
import static services.ConsultaService.menuConsulta;
import static services.ExameService.menuExames;
import static services.MedicamentoService.menuMedicamentos;
import static services.PagamentoService.menuPagamentos;


public class Main {

    public static void menu(){
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("----------------------------------");
            System.out.println("1. Gerenciamento de Paciente");
            System.out.println("2. Gerenciamento de Medicos");
            System.out.println("3. Gerenciamento de Consultas");
            System.out.println("4. Gerenciamento de Exames");
            System.out.println("5. Gerenciamento de Medicamentos");
            System.out.println("6. Gestão de Pagamentos");
            System.out.println("7. Sair");
            System.out.print("Digite uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Opção: " + opcao);

            if (opcao == 1) {
                menuPacientes(scanner);
            } else if (opcao == 2) {
                menuMedicos(scanner);;
            } else if (opcao == 3) {
                menuConsulta(scanner);
            } else if (opcao == 4) {
                menuExames(scanner);
            } else if (opcao == 5) {
                menuMedicamentos(scanner);
            }else if (opcao == 6){
                menuPagamentos(scanner);
            } else if (opcao == 7) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();

    }

    public static void main(String[] args) {
        menu();
    }
}


