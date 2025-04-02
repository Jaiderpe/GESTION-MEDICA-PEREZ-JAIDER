package com.ControlCitas.domain.ports;


import com.example.citasmedicas.domain.entities.Medico;

import java.util.List;

public interface IMedicoRepository {
    Medico save(Medico medico);
    List<Medico> findAll();
    Medico findById(int id);
    void update(Medico medico);
    void delete(int id);
}