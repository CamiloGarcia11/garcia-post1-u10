package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvioOvernightStrategyTest {

    private final EnvioOvernightStrategy estrategia = new EnvioOvernightStrategy();

    @ParameterizedTest(name = "peso {0}kg, dist {1}km -> costo esperado {2}")
    @CsvSource({
            "3.0, 100.0, 220.0",   // peso <= 5: 100*2 + 20 = 220.0
            "8.0, 100.0, 227.5",   // 5 < peso <= 20: 100*2 + 20 + (8-5)*2.5 = 227.5
            "25.0, 100.0, 272.5"   // peso > 20: 100*2 + 20 + 15*2.5 + (25-20)*3.0 = 272.5
    })
    @DisplayName("Calcula costo OVERNIGHT por tramo de peso")
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    @DisplayName("Rechaza distancias mayores a 300 km en OVERNIGHT")
    void rechazaDistanciasMayoresA300Km() {
        assertThrows(IllegalArgumentException.class, () -> estrategia.calcular(3.0, 301.0));
    }
}
