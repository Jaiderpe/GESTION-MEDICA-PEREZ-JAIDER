package com.ControlCitas.domain.entities;


import java.time.LocalDateTime;

public class Cita {
    private int id;
    private int pacienteId;
    private int medicoId;
    private LocalDateTime fechaHora;
    private String estado;

    // Constructor
    public Cita() {}

    public Cita(int pacienteId, int medicoId, LocalDateTime fechaHora, String estado) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }
    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}