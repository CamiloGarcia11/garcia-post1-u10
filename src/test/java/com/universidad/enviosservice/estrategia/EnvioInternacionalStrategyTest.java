package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioInternacionalStrategyTest {

    private final EnvioInternacionalStrategy estrategia = new EnvioInternacionalStrategy();

    @ParameterizedTest(name = "peso {0}kg, dist {1}km -> costo esperado {2}")
    @CsvSource({
            "4.0, 1000.0, 1850.0",   // peso <= 5: 1000*1.8 + 50 = 1850.0
            "10.0, 1000.0, 1865.0",  // 5 < peso <= 20: 1000*1.8 + 50 + (10-5)*3.0 = 1850 + 15 = 1865.0
            "25.0, 1000.0, 1917.5"   // peso > 20: 1000*1.8 + 50 + 15*3.0 + (25-20)*4.5 = 1850 + 45 + 22.5 = 1917.5
    })
    @DisplayName("Calcula costo INTERNACIONAL por tramo de peso con tarifa base")
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    @DisplayName("Aplica recargo por distancia mayor a 5000 km en INTERNACIONAL")
    void aplicaRecargoDistanciaUltraLarga() {
        // peso 4kg, dist 6000km: 6000*1.8 + 50 + 100 = 10800 + 150 = 10950.0
        double costo = estrategia.calcular(4.0, 6000.0);
        assertEquals(10950.0, costo, 0.01);
    }
}
