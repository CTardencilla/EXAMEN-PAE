package org.ni.edu.uam.examen1.model;

/**
 * Enumeración que define los posibles estados de un préstamo en el sistema.
 * Asignado a: Persona 1 (Arquitectura, Modelos y Servicios)
 */
public enum EstadoPrestamo {
    ACTIVO("Activo"),
    DEVUELTO("Devuelto"),
    VENCIDO("Vencido");

    private final String descripcion;

    EstadoPrestamo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
