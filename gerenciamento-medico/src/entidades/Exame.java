package entidades;

import java.time.LocalDateTime;

public class Exame extends Prescricao {
    private String tipo; // Tipo do exame (ex.: SANGUE, RAIO_X, ULTRASSOM)
    private LocalDateTime dataRealizacao; // Data em que o exame foi realizado
    private String resultado; // Resultado do exame
    private double custo; // Custo do exame

    public Exame(LocalDateTime dataPrescricao, Consulta consultaAssociada, String tipo, double custo) {
        super(dataPrescricao, consultaAssociada);
        this.tipo = tipo;
        this.custo = custo;
        this.dataRealizacao = null; // Inicialmente não realizado
        this.resultado = null; // Inicialmente sem resultado
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public double calcularCusto() {
        return custo; // Retorna o custo fixo do exame
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: " + tipo + ", Custo: R$" + custo +
                ", Data de Realização: " + (dataRealizacao != null ? dataRealizacao : "Não realizado") +
                ", Resultado: " + (resultado != null ? resultado : "Pendente");
    }
}
