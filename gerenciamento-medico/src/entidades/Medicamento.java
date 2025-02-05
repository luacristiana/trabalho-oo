package entidades;

import java.time.LocalDateTime;

public class Medicamento extends Prescricao {
    private String nomeMedicamento; // Nome do medicamento

    public Medicamento(LocalDateTime dataPrescricao, Consulta consultaAssociada, String nomeMedicamento) {
        super(dataPrescricao, consultaAssociada);
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    @Override
    public double calcularCusto() {
        return 0.0; // Medicamentos podem ter custo zero ou ser calculados externamente
    }

    @Override
    public String toString() {
        return super.toString() + ", Medicamento: " + nomeMedicamento;
    }
}
