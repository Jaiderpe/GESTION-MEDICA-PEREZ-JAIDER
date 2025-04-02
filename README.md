# GESTION-MEDICA-PEREZ-JAIDER


# Sistema de Control de Citas Médicas

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/) [![MySQL](https://img.shields.io/badge/MySQL-8.x-green)](https://www.mysql.com/) [![Maven](https://img.shields.io/badge/Maven-3.x-red)](https://maven.apache.org/)

El **Sistema de Control de Citas Médicas** es una aplicación diseñada para gestionar pacientes, médicos, citas y otros aspectos relacionados con la administración de consultas médicas. Este sistema permite registrar, actualizar, eliminar y consultar información de manera eficiente mediante una interfaz intuitiva y una base de datos robusta.

---

## Índice


---

## Características Principales

- **Gestión de Pacientes**: Registro, actualización y eliminación de información de pacientes.
- **Gestión de Citas Médicas**: Creación, modificación y cancelación de citas médicas.
- **Interfaz Amigable**: Menú interactivo para facilitar la interacción con el sistema.
- **Conexión a Base de Datos MySQL**: Persistencia de datos utilizando MySQL como motor de base de datos.
- **Validaciones Robustas**: Validaciones en capas de servicio y repositorio para garantizar la integridad de los datos.
- **Arquitectura Hexagonal**: Separación clara entre dominio, puertos y adaptadores para mejorar la mantenibilidad del código.

---

## Requisitos del Sistema

Para ejecutar este proyecto, necesitarás lo siguiente:

### Software
- **Java 17**: [Descargar Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **MySQL 8.x**: [Descargar MySQL](https://dev.mysql.com/downloads/mysql/)
- **Maven 3.x**: [Descargar Maven](https://maven.apache.org/download.cgi)
- **IDE (opcional)**: Se recomienda usar IntelliJ IDEA, Eclipse o VS Code.

### Dependencias
Asegúrate de tener las siguientes dependencias en tu archivo `pom.xml`:
- **JUnit 5**: Para pruebas unitarias.
- **Lombok**: Para reducir código repetitivo (getters, setters, constructores).
- **MySQL Connector**: Para la conexión con la base de datos.

---

## Configuración del Proyecto

1. **Clonar el Repositorio**
   ```bash
   git clone https://github.com/tu-usuario/ControlCitas.git
   cd ControlCitas
   ```

2. **Configurar Base de Datos**
   - Crea una base de datos MySQL llamada `ControlCitas`.
   - Ejecuta las consultas SQL incluidas en la carpeta `resources/database.sql` para crear las tablas necesarias.

3. **Configurar Archivo `database.properties`**
   Edita el archivo `src/main/resources/database.properties` con tus credenciales de MySQL:
   ```properties
   db.url=jdbc:mysql://localhost:3306/ControlCitas
   db.user=root
   db.password=123456
   ```

4. **Instalar Dependencias**
   Ejecuta el siguiente comando para descargar las dependencias necesarias:
   ```bash
   mvn clean install
   ```

---

## Ejecución del Proyecto

1. **Compilar el Proyecto**
   ```bash
   mvn compile
   ```

2. **Ejecutar la Aplicación**
   ```bash
   mvn exec:java -Dexec.mainClass="com.ControlCitas.Main"
   ```

3. **Interactuar con el Menú**
   Una vez iniciada la aplicación, verás un menú principal donde podrás realizar operaciones como:
   - Crear pacientes.
   - Listar pacientes.
   - Buscar pacientes por ID.
   - Actualizar información de pacientes.
   - Eliminar pacientes.

---

## Estructura del Proyecto

```
ControlCitas/
├── src/
│   ├── com.ControlCitas.domain.entities/
│   │   ├── Paciente.java
│   ├── com.ControlCitas.domain.ports/
│   │   ├── IPacienteRepository.java
│   ├── com.ControlCitas.infrastructure.adapters/
│   │   ├── PacienteRepositoryAdapter.java
│   ├── com.ControlCitas.infrastructure.database/
│   │   ├── ConnectionDb.java
│   ├── com.ControlCitas.services/
│   │   ├── PacienteService.java
│   ├── com.ControlCitas.controllers/
│   │   ├── PacienteController.java
│   ├── com.ControlCitas.Tests/
│   │   ├── PacienteRepositoryAdapterTest.java
│   ├── Main.java
├── resources/
│   ├── database.properties
│   ├── database.sql
```

---

## Base de Datos

La base de datos está diseñada para almacenar información sobre pacientes, médicos y citas médicas. Las principales tablas incluyen:

- **Pacientes**: Almacena datos personales de los pacientes.
- **Citas**: Registra las citas médicas con detalles como fecha, hora y estado.
- **Médicos**: Contiene información sobre los médicos y sus especialidades.

Puedes encontrar las consultas SQL necesarias para crear las tablas en el archivo `resources/database.sql`.

---

## Pruebas Unitarias

Las pruebas unitarias se encuentran en el paquete `com.ControlCitas.Tests`. Para ejecutarlas, utiliza el siguiente comando:

```bash
mvn test
```

Estas pruebas cubren los casos más comunes, como la creación, actualización y eliminación de pacientes.

---

## Contribuciones

Si deseas contribuir al proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -m "Añadir nueva funcionalidad"`).
4. Sube los cambios a tu rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request en GitHub.

---

## Licencia

Este proyecto está bajo la licencia [MIT License](LICENSE). Esto significa que puedes usar, modificar y distribuir el código libremente, siempre que incluyas la licencia original en tu proyecto.

---

Si tienes alguna pregunta o necesitas ayuda adicional, no dudes en abrir un issue en el repositorio o contactarme directamente.

¡Gracias por usar el **Sistema de Control de Citas Médicas**! 🚀
