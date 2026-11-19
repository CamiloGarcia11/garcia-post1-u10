package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest(name = "peso {0}kg, dist {1}km -> costo esperado {2}")
    @CsvSource({
            "3.0, 100.0, 156.0",    // peso <= 5: (100*1.2)*1.3 = 120 * 1.3 = 156.0
            "10.0, 100.0, 165.75",  // 5 < peso <= 20: (100*1.2 + 5*1.5)*1.3 = (120 + 7.5)*1.3 = 127.5*1.3 = 165.75
            "25.0, 100.0, 198.25"   // peso > 20: (100*1.2 + 15*1.5 + 5*2.0)*1.3 = (120 + 22.5 + 10)*1.3 = 152.5*1.3 = 198.25
    })
    @DisplayName("Calcula costo EXPRESS por tramo de peso con multiplicador 1.3")
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    @DisplayName("Aplica recargo por distancia mayor a 500 km en EXPRESS")
    void aplicaRecargoDistanciaLarga() {
        // peso 3kg, dist 600km: (600*1.2 + 25)*1.3 = (720 + 25)*1.3 = 745 * 1.3 = 968.5
        double costo = estrategia.calcular(3.0, 600.0);
        assertEquals(968.5, costo, 0.01);
    }
}
