package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public abstract class Prescricao {
    private LocalDateTime dataPrescricao; // Data em que foi prescrita
    private Consulta consultaAssociada; // Consulta que gerou a prescrição

    public Prescricao(LocalDateTime dataPrescricao, Consulta consultaAssociada) {
        this.dataPrescricao = dataPrescricao;
        this.consultaAssociada = consultaAssociada;
    }

    public String getDataPrescricao() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataPrescricao.format(formatter);
    }

    public void setDataPrescricao(LocalDateTime dataPrescricao) {
        this.dataPrescricao = dataPrescricao;
    }

    public Consulta getConsultaAssociada() {
        return consultaAssociada;
    }

    public void setConsultaAssociada(Consulta consultaAssociada) {
        this.consultaAssociada = consultaAssociada;
    }

    // Método abstrato para cálculo de custo (implementado nas subclasses)
    public abstract double calcularCusto();

    @Override
    public String toString() {
        return "Data da Prescrição: " + getDataPrescricao() + ", Consulta Associada: " + consultaAssociada;
    }
}
