package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private LocalDateTime dataConsulta; // Data e horário da consulta
    private int duracaoMinutos; // Duração da consulta (padrão: 30 minutos)
    private String status; // Status da consulta (AGENDADA, CANCELADA, REALIZADA)
    private Paciente paciente; // Paciente associado à consulta
    private Medico medico; // Médico responsável pela consulta
    private List<Exame> examesPrescritos = new ArrayList<>();
    private List<Medicamento> medicamentosPrescritos = new ArrayList<>();
    private double valorConsulta; // Valor da consulta

    public Consulta(LocalDateTime dataConsulta, Paciente paciente, Medico medico, double valorConsulta) {
        this.dataConsulta = dataConsulta;
        this.duracaoMinutos = duracaoMinutos > 0 ? duracaoMinutos : 30;
        this.status = "AGENDADA"; // Status inicial
        this.paciente = paciente;
        this.medico = medico;
        this.examesPrescritos = new ArrayList<>();
        this.medicamentosPrescritos = new ArrayList<>();
        this.valorConsulta = valorConsulta;
    }

    // Getters e Setters
    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Exame> getExamesPrescritos() {
        return examesPrescritos;
    }

    public void adicionarExame(Exame exame) {
        this.examesPrescritos.add(exame);
    }

    public List<Medicamento> getMedicamentosPrescritos() {
        return medicamentosPrescritos;
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        this.medicamentosPrescritos.add(medicamento);
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
}
