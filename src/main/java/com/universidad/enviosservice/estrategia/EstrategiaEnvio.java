package com.universidad.enviosservice.estrategia;

/**
 * Patrón Strategy: Interfaz común para el cálculo del costo base por método de transporte.
 */
public interface EstrategiaEnvio {
    double calcular(double pesoKg, double distanciaKm);
}
