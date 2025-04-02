package com.ControlCitas.domain.ports;


import com.example.citasmedicas.domain.entities.Cita;

import java.util.List;

public interface ICitaRepository {
    Cita save(Cita cita);
    List<Cita> findAll();
    Cita findById(int id);
    void update(Cita cita);
    void delete(int id);
}