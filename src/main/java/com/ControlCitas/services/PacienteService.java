package com.ControlCitas.services;

import com.ControlCitas.domain.entities.Paciente;
import com.ControlCitas.domain.ports.IPacienteRepository;

import java.util.List;
import java.util.Optional;

public class PacienteService {
    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPacientePorId(int id) {
        return pacienteRepository.findById(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.update(paciente);
    }

    public void eliminarPaciente(int id) {
        pacienteRepository.delete(id);
    }
}