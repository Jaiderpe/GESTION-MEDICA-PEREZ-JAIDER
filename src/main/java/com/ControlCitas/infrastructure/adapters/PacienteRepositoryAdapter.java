package com.ControlCitas.infrastructure.adapters;

import com.ControlCitas.infrastructure.database.ConnectionDb;
import com.ControlCitas.domain.entities.Paciente;
import com.ControlCitas.domain.ports.IPacienteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteRepositoryAdapter implements IPacienteRepository {
    private final ConnectionDb connectionDb;

    public PacienteRepositoryAdapter(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public Paciente save(Paciente paciente) {
        String sql = "INSERT INTO Pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                paciente.setId(generatedKeys.getInt(1));
            }
            return paciente;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el paciente", e);
        }
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM Pacientes";
        try (Connection conn = connectionDb.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente paciente = mapPaciente(rs);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes", e);
        }
        return pacientes;
    }

    @Override
    public Optional<Paciente> findById(int id) {
        String sql = "SELECT * FROM Pacientes WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapPaciente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar paciente por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Paciente paciente) {
        String sql = "UPDATE Pacientes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());
            stmt.setInt(7, paciente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar paciente", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Pacientes WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }

    private Paciente mapPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setId(rs.getInt("id"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        paciente.setDireccion(rs.getString("direccion"));
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setEmail(rs.getString("email"));
        return paciente;
    }
}