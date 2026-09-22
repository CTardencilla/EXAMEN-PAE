package org.ni.edu.uam.examen1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ni.edu.uam.examen1.model.EstadoPrestamo;
import org.ni.edu.uam.examen1.model.Prestamo;
import org.ni.edu.uam.examen1.service.PrestamoService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la lógica de negocio y persistencia en memoria.
 * Asignado a: Persona 1 (Arquitectura, Modelos y Servicios)
 */
class PrestamoServiceTest {

    private PrestamoService service;

    @BeforeEach
    void setUp() {
        service = PrestamoService.getInstance();
        service.cargarDatosSemilla();
    }

    @Test
    @DisplayName("Debe cargar los datos semilla iniciales correctamente")
    void testCargarDatosSemilla() {
        assertTrue(service.getTotalPrestamos() >= 5, "Deben existir al menos 5 préstamos semilla");
        assertTrue(service.getCantidadActivos() > 0, "Debe haber préstamos activos");
        assertTrue(service.getCantidadDevueltos() > 0, "Debe haber préstamos devueltos");
    }

    @Test
    @DisplayName("Debe registrar un nuevo préstamo válido y asignarle ID incremental")
    void testRegistrarPrestamoValido() {
        int totalInicial = service.getTotalPrestamos();
        LocalDate prestamo = LocalDate.now();
        LocalDate devolucion = prestamo.plusDays(7);

        Prestamo nuevo = service.registrarPrestamo("Lucía Arana", "Laptop Lenovo ThinkPad", prestamo, devolucion);

        assertNotNull(nuevo);
        assertTrue(nuevo.getId() > 0);
        assertEquals("Lucía Arana", nuevo.getUsuario());
        assertEquals("Laptop Lenovo ThinkPad", nuevo.getRecurso());
        assertEquals(EstadoPrestamo.ACTIVO, nuevo.getEstado());
        assertEquals(totalInicial + 1, service.getTotalPrestamos());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el usuario o recurso están vacíos")
    void testValidacionCamposObligatorios() {
        LocalDate hoy = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () ->
                service.registrarPrestamo("", "Laptop Dell", hoy, hoy.plusDays(3)));

        assertThrows(IllegalArgumentException.class, () ->
                service.registrarPrestamo("Juan", "   ", hoy, hoy.plusDays(3)));

        assertThrows(IllegalArgumentException.class, () ->
                service.registrarPrestamo(null, "Laptop Dell", hoy, hoy.plusDays(3)));
    }

    @Test
    @DisplayName("Debe rechazar préstamos con fecha de devolución anterior a la de préstamo")
    void testValidacionFechasIncoherentes() {
        LocalDate prestamo = LocalDate.of(2026, 5, 20);
        LocalDate devolucionInvalida = LocalDate.of(2026, 5, 10);

        assertThrows(IllegalArgumentException.class, () ->
                service.registrarPrestamo("Juan Pérez", "Proyector", prestamo, devolucionInvalida));
    }

    @Test
    @DisplayName("Debe marcar correctamente un préstamo como devuelto")
    void testMarcarComoDevuelto() {
        LocalDate hoy = LocalDate.now();
        Prestamo p = service.registrarPrestamo("Pedro Rivas", "Osciloscopio", hoy, hoy.plusDays(4));

        boolean res = service.marcarComoDevuelto(p);

        assertTrue(res);
        assertEquals(EstadoPrestamo.DEVUELTO, p.getEstado());
        assertEquals("Devuelto", p.getDiasRestantes());
    }

    @Test
    @DisplayName("Debe eliminar un préstamo de la colección")
    void testEliminarPrestamo() {
        int totalAntes = service.getTotalPrestamos();
        Prestamo primero = service.getPrestamos().get(0);

        boolean eliminado = service.eliminarPrestamo(primero);

        assertTrue(eliminado);
        assertEquals(totalAntes - 1, service.getTotalPrestamos());
        assertFalse(service.getPrestamos().contains(primero));
    }
}
