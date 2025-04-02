package com.ControlCitas;

import com.ControlCitas.services.PacienteService;
import com.ControlCitas.controllers.PacienteController;
import com.ControlCitas.domain.entities.Paciente;
import com.ControlCitas.infrastructure.adapters.PacienteRepositoryAdapter;
import com.ControlCitas.infrastructure.database.ConnectionDb;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        
        ConnectionDb connectionDb = new ConnectionDb();
        Connection connection = connectionDb.getConexion();
        // Inicializar dependencias
        PacienteRepositoryAdapter pacienteRepository = new PacienteRepositoryAdapter();
        PacienteService pacienteService = new PacienteService(pacienteRepository);
        PacienteController pacienteController = new PacienteController(pacienteService);

        // Mostrar menú principal
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Crear Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Buscar Paciente por ID");
            System.out.println("4. Actualizar Paciente");
            System.out.println("5. Eliminar Paciente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearPaciente(pacienteController);
                    break;
                case 2:
                    listarPacientes(pacienteController);
                    break;
                case 3:
                    buscarPacientePorId(pacienteController);
                    break;
                case 4:
                    actualizarPaciente(pacienteController);
                    break;
                case 5:
                    eliminarPaciente(pacienteController);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void crearPaciente(PacienteController pacienteController) {
        System.out.println("\n=== CREAR PACIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        pacienteController.crearPaciente(nombre, apellido, fechaNacimiento, direccion, telefono, email);
        System.out.println("Paciente creado exitosamente.");
    }

    private static void listarPacientes(PacienteController pacienteController) {
        System.out.println("\n=== LISTAR PACIENTES ===");
        List<Paciente> pacientes = pacienteController.listarPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente p : pacientes) {
                System.out.println(p.getId() + " - " + p.getNombre() + " " + p.getApellido());
            }
        }
    }

    private static void buscarPacientePorId(PacienteController pacienteController) {
        System.out.println("\n=== BUSCAR PACIENTE POR ID ===");
        System.out.print("Ingrese el ID del paciente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Paciente paciente = pacienteController.buscarPacientePorId(id);
        if (paciente != null) {
            System.out.println("Paciente encontrado:");
            System.out.println("ID: " + paciente.getId());
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Apellido: " + paciente.getApellido());
            System.out.println("Fecha de Nacimiento: " + paciente.getFechaNacimiento());
            System.out.println("Dirección: " + paciente.getDireccion());
            System.out.println("Teléfono: " + paciente.getTelefono());
            System.out.println("Email: " + paciente.getEmail());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private static void actualizarPaciente(PacienteController pacienteController) {
        System.out.println("\n=== ACTUALIZAR PACIENTE ===");
        System.out.print("Ingrese el ID del paciente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Paciente pacienteExistente = pacienteController.buscarPacientePorId(id);
        if (pacienteExistente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        System.out.print("Nuevo Nombre (" + pacienteExistente.getNombre() + "): ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo Apellido (" + pacienteExistente.getApellido() + "): ");
        String apellido = scanner.nextLine();
        System.out.print("Nueva Fecha de Nacimiento (" + pacienteExistente.getFechaNacimiento() + "): ");
        String fechaStr = scanner.nextLine();
        LocalDate fechaNacimiento = fechaStr.isEmpty() ? pacienteExistente.getFechaNacimiento() : LocalDate.parse(fechaStr);
        System.out.print("Nueva Dirección (" + pacienteExistente.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        System.out.print("Nuevo Teléfono (" + pacienteExistente.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        System.out.print("Nuevo Email (" + pacienteExistente.getEmail() + "): ");
        String email = scanner.nextLine();

        pacienteController.actualizarPaciente(id, nombre, apellido, fechaNacimiento, direccion, telefono, email);
        System.out.println("Paciente actualizado exitosamente.");
    }

    private static void eliminarPaciente(PacienteController pacienteController) {
        System.out.println("\n=== ELIMINAR PACIENTE ===");
        System.out.print("Ingrese el ID del paciente a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Paciente pacienteExistente = pacienteController.buscarPacientePorId(id);
        if (pacienteExistente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        pacienteController.eliminarPaciente(id);
        System.out.println("Paciente eliminado exitosamente.");
    }
}