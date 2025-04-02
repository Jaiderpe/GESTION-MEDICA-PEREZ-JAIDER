package com.ControlCitas.domain.entities;


import java.time.LocalTime;

public class Medico {
    private int id;
    private String nombre;
    private int especialidadId;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;

    // Constructor
    public Medico() {}

    public Medico(String nombre, int especialidadId, LocalTime horarioInicio, LocalTime horarioFin) {
        this.nombre = nombre;
        this.especialidadId = especialidadId;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEspecialidadId() { return especialidadId; }
    public void setEspecialidadId(int especialidadId) { this.especialidadId = especialidadId; }
    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }
    public LocalTime getHorarioFin() { return horarioFin; }
    public void setHorarioFin(LocalTime horarioFin) { this.horarioFin = horarioFin; }
}