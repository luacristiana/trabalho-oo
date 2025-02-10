package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private List<Consulta> historicoMedico;
    private double pagamentosPendentes;

    public Paciente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
        this.historicoMedico = new ArrayList<>();
    }

    public List<Consulta> getHistoricoMedico() {
        return historicoMedico;

    }

    public double getPagamentosPendentes() {
        return pagamentosPendentes;
    }

    public void adicionarPagamentoPendente(double valor) {
        this.pagamentosPendentes += valor;
    }

    public void registrarPagamento(double valor) {
        if (valor > pagamentosPendentes) {
            System.out.println("Erro: O valor pago excede o total pendente.");
            return;
        }
        this.pagamentosPendentes -= valor;
    }

    public boolean temPagamentosPendentes() {
        return pagamentosPendentes > 0;
    }

    public void adicionarConsulta(Consulta consulta) {
        historicoMedico.add(consulta);
    }

    @Override
    public String getDescricao() {
        return "Paciente: " + super.getDescricao();
    }
}
