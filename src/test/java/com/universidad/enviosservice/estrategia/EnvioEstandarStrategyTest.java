package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioEstandarStrategyTest {

    private final EnvioEstandarStrategy estrategia = new EnvioEstandarStrategy();

    @ParameterizedTest(name = "peso {0}kg, dist {1}km -> costo esperado {2}")
    @CsvSource({
            "4.0, 100.0, 50.0",     // peso <= 5: 100*0.5 = 50.0
            "10.0, 100.0, 54.0",    // 5 < peso <= 20: 100*0.5 + (10-5)*0.8 = 50 + 4 = 54.0
            "25.0, 100.0, 68.0"     // peso > 20: 100*0.5 + 15*0.8 + (25-20)*1.2 = 50 + 12 + 6 = 68.0
    })
    @DisplayName("Calcula costo ESTANDAR por tramo de peso")
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    @DisplayName("Aplica recargo por distancia mayor a 500 km en ESTANDAR")
    void aplicaRecargoDistanciaLarga() {
        // peso 4kg (<=5), dist 600km (>500): 600*0.5 + 10 = 310.0
        double costo = estrategia.calcular(4.0, 600.0);
        assertEquals(310.0, costo, 0.01);
    }
}
