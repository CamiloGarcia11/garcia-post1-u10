package com.universidad.enviosservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioCalculoEnvioTest {

    private final ServicioCalculoEnvio servicio = new ServicioCalculoEnvio();

    @Test
    @DisplayName("Calcula costo estándar básico para peso bajo (Prueba inicial)")
    void calculaEnvioEstandarPesoBajo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.01);
    }

    @Test
    @DisplayName("Aplica descuento de 10% para cliente PREMIUM (Prueba inicial)")
    void aplicaDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.01);
    }

    @Test
    @DisplayName("Aplica recargo del 15% para paquete frágil")
    void aplicaRecargoPorFragilidad() {
        // Base: 100 * 0.5 = 50.0. Con fragilidad (+15%): 50.0 * 1.15 = 57.5
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", true);
        assertEquals(57.5, costo, 0.01);
    }

    @Test
    @DisplayName("Aplica descuento del 15% para cliente CORPORATIVO")
    void aplicaDescuentoClienteCorporativo() {
        // Base: 100 * 0.5 = 50.0. Con descuento (-15%): 50.0 * 0.85 = 42.5
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", false);
        assertEquals(42.5, costo, 0.01);
    }

    @Test
    @DisplayName("Aplica ambos ajustes: fragilidad (+15%) y cliente CORPORATIVO (-15%)")
    void aplicaFragilidadYDescuentoCorporativo() {
        // Base: 50.0. Con fragilidad: 57.5. Con corporativo: 57.5 * 0.85 = 48.875
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", true);
        assertEquals(48.875, costo, 0.01);
    }

    @Test
    @DisplayName("Lanza IllegalArgumentException si el método de envío no es soportado")
    void rechazaMetodoEnvioNoSoportado() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                servicio.calcularCosto("DRON_AEREO", 2.0, 50.0, "REGULAR", false));
        assertEquals("Metodo de envio no soportado: DRON_AEREO", ex.getMessage());
    }

    @Test
    @DisplayName("Lanza IllegalArgumentException si el método de envío es nulo")
    void rechazaMetodoEnvioNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                servicio.calcularCosto(null, 2.0, 50.0, "REGULAR", false));
    }

    @Test
    @DisplayName("Orquesta correctamente llamadas con diferentes estrategias registradas")
    void orquestaDiferentesEstrategias() {
        // Express: (100 * 1.2) * 1.3 = 156.0
        double express = servicio.calcularCosto("EXPRESS", 3.0, 100.0, "REGULAR", false);
        assertEquals(156.0, express, 0.01);

        // Overnight: 100 * 2.0 + 20 = 220.0
        double overnight = servicio.calcularCosto("OVERNIGHT", 3.0, 100.0, "REGULAR", false);
        assertEquals(220.0, overnight, 0.01);

        // Internacional: 1000 * 1.8 + 50 = 1850.0
        double internacional = servicio.calcularCosto("INTERNACIONAL", 4.0, 1000.0, "REGULAR", false);
        assertEquals(1850.0, internacional, 0.01);
    }
}
