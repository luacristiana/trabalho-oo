package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa {
    private String especialidade;
    private String crm;
    private List<Consulta> historicoMedico;

    public Medico(String nome, String cpf, LocalDate dataNascimento, String especialidade, String crm) {
        super(nome, cpf, dataNascimento);
        this.especialidade = especialidade;
        this.crm = crm;
        this.historicoMedico = new ArrayList<>();
    }

    public List<Consulta> getHistoricoMedico() {
        return historicoMedico;
    }

    public String getCrm() {
        return crm;
    }

    //public void setCrm(String crm) {
     //   this.crm = crm;
  //  }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String getDescricao() {
        return "Médico: " + super.getDescricao() + ", Especialidade: " + especialidade + ", CRM: " + crm;
    }
}


