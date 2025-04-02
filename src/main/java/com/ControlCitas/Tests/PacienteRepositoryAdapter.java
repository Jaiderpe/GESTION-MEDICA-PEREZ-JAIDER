package com.ControlCitas.Tests;

import java.time.LocalDate;
import java.util.List;



import com.ControlCitas.domain.entities.Paciente;
import com.ControlCitas.domain.repository.PacienteRepositoryAdapter;

import java.util.ArrayList;


public class PacienteRepositoryAdapter implements PacienteRepository {
    private final List<Paciente> pacientes = new ArrayList<>();

    @Override
    public Paciente guardar(Paciente paciente) {
        paciente.setId(pacientes.size() + 1); // Simulación de ID único
        pacientes.add(paciente);
        return paciente;
    }

    @Override
    public List<Paciente> listar() {
        return pacientes;
    }
}