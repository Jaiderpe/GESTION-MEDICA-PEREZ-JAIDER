package com.ControlCitas.controllers;

import com.ControlCitas.services.PacienteService;
import com.ControlCitas.domain.entities.Paciente;

import java.time.LocalDate;
import java.util.List;

public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void crearPaciente(String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String email) {
        Paciente paciente = new Paciente(nombre, apellido, fechaNacimiento, direccion, telefono, email);
        pacienteService.crearPaciente(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    public Paciente buscarPacientePorId(int id) {
        return pacienteService.buscarPacientePorId(id);
    }

    public void actualizarPaciente(int id, String nombre, String apellido, LocalDate fechaNacimiento, String direccion, String telefono, String email) {
        Paciente paciente = new Paciente(nombre, apellido, fechaNacimiento, direccion, telefono, email);
        paciente.setId(id);
        pacienteService.actualizarPaciente(paciente);
    }

    public void eliminarPaciente(int id) {
        pacienteService.eliminarPaciente(id);
    }
}