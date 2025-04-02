package com.ControlCitas.domain.ports;


import com.example.citasmedicas.domain.entities.Especialidad;

import java.util.List;

public interface IEspecialidadRepository {
    Especialidad save(Especialidad especialidad);
    List<Especialidad> findAll();
    Especialidad findById(int id);
    void update(Especialidad especialidad);
    void delete(int id);
}