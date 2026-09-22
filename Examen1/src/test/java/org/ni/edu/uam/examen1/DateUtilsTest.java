package org.ni.edu.uam.examen1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ni.edu.uam.examen1.util.DateUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    @DisplayName("Debe formatear fechas correctamente al patrón dd/MM/yyyy")
    void testFormatearFecha() {
        LocalDate fecha = LocalDate.of(2026, 4, 15);
        assertEquals("15/04/2026", DateUtils.formatear(fecha));
        assertEquals("", DateUtils.formatear(null));
    }

    @Test
    @DisplayName("Debe validar rangos de fechas correctamente")
    void testEsRangoValido() {
        LocalDate inicio = LocalDate.of(2026, 9, 22);
        LocalDate posterior = LocalDate.of(2026, 9, 29);
        LocalDate anterior = LocalDate.of(2026, 9, 15);

        assertTrue(DateUtils.esRangoValido(inicio, posterior));
        assertTrue(DateUtils.esRangoValido(inicio, inicio));
        assertFalse(DateUtils.esRangoValido(inicio, anterior));
        assertFalse(DateUtils.esRangoValido(null, posterior));
        assertFalse(DateUtils.esRangoValido(inicio, null));
    }

    @Test
    @DisplayName("Debe parsear cadenas de fecha válidas")
    void testParsearFecha() {
        LocalDate parsed = DateUtils.parsear("10/12/2026");
        assertNotNull(parsed);
        assertEquals(LocalDate.of(2026, 12, 10), parsed);
        assertNull(DateUtils.parsear("fecha-invalida"));
    }
}
