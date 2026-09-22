package org.ni.edu.uam.examen1.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Modelo de datos que representa un Préstamo de Recurso.
 * Asignado a: Persona 1 (Arquitectura, Modelos y Servicios)
 */
public class Prestamo {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id;
    private String usuario;
    private String recurso;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;

    public Prestamo() {
    }

    public Prestamo(int id, String usuario, String recurso, LocalDate fechaPrestamo, LocalDate fechaDevolucion, EstadoPrestamo estado) {
        this.id = id;
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        actualizarEstadoPorFecha();
    }

    /**
     * Evalúa si un préstamo activo ha sobrepasado su fecha de devolución.
     */
    public void actualizarEstadoPorFecha() {
        if (this.estado != EstadoPrestamo.DEVUELTO) {
            if (this.fechaDevolucion != null && LocalDate.now().isAfter(this.fechaDevolucion)) {
                this.estado = EstadoPrestamo.VENCIDO;
            } else if (this.estado == null) {
                this.estado = EstadoPrestamo.ACTIVO;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
        actualizarEstadoPorFecha();
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    // Métodos utilitarios de presentación y negocio
    public String getFechaPrestamoStr() {
        return fechaPrestamo != null ? fechaPrestamo.format(FORMATTER) : "";
    }

    public String getFechaDevolucionStr() {
        return fechaDevolucion != null ? fechaDevolucion.format(FORMATTER) : "";
    }

    public String getEstadoStr() {
        return estado != null ? estado.getDescripcion() : "";
    }

    public String getDiasRestantes() {
        if (estado == EstadoPrestamo.DEVUELTO) {
            return "Devuelto";
        }
        if (fechaDevolucion == null) {
            return "-";
        }
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), fechaDevolucion);
        if (dias < 0) {
            return "Vencido hace " + Math.abs(dias) + " d";
        } else if (dias == 0) {
            return "Vence hoy";
        } else {
            return dias + " días restantes";
        }
    }

    @Override
    public String toString() {
        return "Prestamo #" + id + " [" + usuario + " - " + recurso + " (" + estado + ")]";
    }
}
