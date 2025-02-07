package app;

import entidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import static services.PacienteService.menuPacientes;
import static services.MedicoService.menuMedicos;
import static services.ConsultaService.menuConsulta;


public class Main {

    public static void menu(){
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("----------------------------------");
            System.out.println("1. Gerenciamento de Paciente");
            System.out.println("2. Gerenciamento de Medicos");
            System.out.println("3. Gerenciamento de Consultas");
            System.out.println("4. Sair");
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
                System.out.println("Saindo...");
                break;
            }
            else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();

    }
    
    public static void main(String[] args) {
        menu();
    }
}


