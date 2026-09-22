package org.ni.edu.uam.examen1.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ni.edu.uam.examen1.model.EstadoPrestamo;
import org.ni.edu.uam.examen1.model.Prestamo;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio centralizado con persistencia en memoria para la gestión de préstamos.
 * Utiliza ObservableList para permitir sincronización automática con la interfaz JavaFX.
 * Asignado a: Persona 1 (Arquitectura, Modelos y Servicios)
 */
public class PrestamoService {
    private static PrestamoService instance;

    private final ObservableList<Prestamo> listaPrestamos;
    private final AtomicInteger generadorId;

    private PrestamoService() {
        this.listaPrestamos = FXCollections.observableArrayList();
        this.generadorId = new AtomicInteger(100);
        cargarDatosSemilla();
    }

    /**
     * Acceso singleton para compartir el estado entre componentes.
     */
    public static synchronized PrestamoService getInstance() {
        if (instance == null) {
            instance = new PrestamoService();
        }
        return instance;
    }

    /**
     * Carga de datos semilla iniciales para pruebas del sistema.
     */
    public void cargarDatosSemilla() {
        listaPrestamos.clear();
        LocalDate hoy = LocalDate.now();

        // 1. Préstamo Activo
        registrarPrestamo("Carlos Mendoza", "Laptop Dell Latitude 5420", hoy.minusDays(2), hoy.plusDays(5));

        // 2. Préstamo Activo por vencer hoy
        registrarPrestamo("Ana Sofía Morales", "Proyector Epson PowerLite HDMI", hoy.minusDays(3), hoy);

        // 3. Préstamo Devuelto
        Prestamo p3 = new Prestamo(generadorId.incrementAndGet(), "Roberto Silva", "Libro: Patrones de Diseño (GoF)",
                hoy.minusDays(10), hoy.minusDays(3), EstadoPrestamo.DEVUELTO);
        listaPrestamos.add(p3);

        // 4. Préstamo Vencido
        Prestamo p4 = new Prestamo(generadorId.incrementAndGet(), "Valeria Gómez", "Cámara DSLR Sony Alpha 4K",
                hoy.minusDays(12), hoy.minusDays(2), EstadoPrestamo.VENCIDO);
        listaPrestamos.add(p4);

        // 5. Préstamo Activo
        registrarPrestamo("David Gutiérrez", "Tablet iPad Air 10.9\"", hoy.minusDays(1), hoy.plusDays(6));
    }

    public ObservableList<Prestamo> getPrestamos() {
        return listaPrestamos;
    }

    public Prestamo registrarPrestamo(String usuario, String recurso, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
        if (recurso == null || recurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El recurso no puede estar vacío");
        }
        if (fechaPrestamo == null || fechaDevolucion == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser anterior a la de préstamo");
        }

        int nuevoId = generadorId.incrementAndGet();
        Prestamo nuevo = new Prestamo(nuevoId, usuario.trim(), recurso.trim(), fechaPrestamo, fechaDevolucion, EstadoPrestamo.ACTIVO);
        listaPrestamos.add(nuevo);
        return nuevo;
    }

    public boolean marcarComoDevuelto(Prestamo prestamo) {
        if (prestamo != null) {
            prestamo.setEstado(EstadoPrestamo.DEVUELTO);
            int index = listaPrestamos.indexOf(prestamo);
            if (index >= 0) {
                listaPrestamos.set(index, prestamo);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarPrestamo(Prestamo prestamo) {
        return listaPrestamos.remove(prestamo);
    }

    public long getCantidadActivos() {
        return listaPrestamos.stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.ACTIVO)
                .count();
    }

    public long getCantidadDevueltos() {
        return listaPrestamos.stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.DEVUELTO)
                .count();
    }

    public long getCantidadVencidos() {
        return listaPrestamos.stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.VENCIDO)
                .count();
    }

    public int getTotalPrestamos() {
        return listaPrestamos.size();
    }
}
