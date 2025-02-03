package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private List<Consulta> historicoMedico;

    public Paciente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
        this.historicoMedico = new ArrayList<>();
    }

    public List<Consulta> getHistoricoMedico() {
        return historicoMedico;

    }

    public void adicionarConsulta(Consulta consulta) {
        historicoMedico.add(consulta);
    }

    @Override
    public String getDescricao() {
        return "Paciente: " + getNome() + "\n" + "CPF: " + getCpf() + "\n" + "Data de Nascimento: " + getDataNascimento();
    }
}

