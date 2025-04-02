package com.ControlCitas.domain.ports;

import com.ControlCitas.domain.entities.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteRepository {
    Paciente save(Paciente paciente);
    List<Paciente> findAll();
    Optional<Paciente> findById(int id);
    void update(Paciente paciente);
    void delete(int id);
}