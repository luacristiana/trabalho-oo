package app;

import entidades.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Pessoa paciente = new Paciente("João Silva", "123.456.789-00", LocalDate.of(1990, 5, 15));
        Pessoa medico = new Medico("Dra. Ana Souza", "987.654.321-11", LocalDate.of(1980, 7, 12), "Cardiologia", "52525251125");

        // Polimorfismo por sobrescrita: o método getDescricao() é chamado com base na implementação da subclasse
        System.out.println(paciente.getDescricao()); // Saída: Paciente: João Silva, CPF: 123.456.789-00
        System.out.println(medico.getDescricao());   // Saída: Médico: Dra. Ana Souza, Especialidade: Cardiologia
    }
}
