package com.ControlCitas.infrastructure.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum AppSingleton {
    INSTANCIA;

    private final Properties propiedades = new Properties();

    AppSingleton() {
        cargarConfiguraciones("configmysql.properties");
    }

    private void cargarConfiguraciones(String rutaArchivo) {
        try (InputStream archivo = getClass().getClassLoader().getResourceAsStream(rutaArchivo)) {
            if (archivo == null) {
                System.err.println("❌ Error cargando configuración: Archivo no encontrado: " + rutaArchivo);
                return; // Salir del método si el archivo no se encuentra
            }

            propiedades.load(archivo);
        } catch (IOException e) {
            System.err.println("❌ Error cargando configuración: " + e.getMessage());
            throw new RuntimeException("Error cargando configuración", e);
        }
    }

    public String get(String clave) {
        return propiedades.getProperty(clave, "No encontrado");
    }

}