package org.ni.edu.uam.examen1.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidades para formateo, parseo y validación de rangos de fechas.
 */
public class DateUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatear(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(FORMATTER);
    }

    public static LocalDate parsear(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(fechaStr.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean esRangoValido(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return false;
        }
        return !fechaFin.isBefore(fechaInicio);
    }
}
