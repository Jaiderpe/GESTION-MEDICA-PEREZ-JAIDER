package com.ControlCitas.domain.entities;


public class Especialidad {
    private int id;
    private String nombre;

    // Constructor
    public Especialidad() {}

    public Especialidad(String nombre) {
        this.nombre = nombre;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}