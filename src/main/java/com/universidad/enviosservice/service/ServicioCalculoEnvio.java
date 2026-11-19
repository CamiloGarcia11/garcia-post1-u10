package com.universidad.enviosservice.service;

import com.universidad.enviosservice.estrategia.EnvioEstandarStrategy;
import com.universidad.enviosservice.estrategia.EnvioExpressStrategy;
import com.universidad.enviosservice.estrategia.EnvioInternacionalStrategy;
import com.universidad.enviosservice.estrategia.EnvioOvernightStrategy;
import com.universidad.enviosservice.estrategia.EstrategiaEnvio;

import java.util.Map;

/**
 * Servicio orquestador del cálculo de envíos.
 * Aplica el patrón Strategy para delegar los algoritmos de costos base en clases especializadas,
 * manteniendo de forma centralizada las reglas transversales (fragilidad y descuentos de cliente).
 */
public class ServicioCalculoEnvio {

    private final Map<String, EstrategiaEnvio> estrategias = Map.of(
            "ESTANDAR", new EnvioEstandarStrategy(),
            "EXPRESS", new EnvioExpressStrategy(),
            "OVERNIGHT", new EnvioOvernightStrategy(),
            "INTERNACIONAL", new EnvioInternacionalStrategy()
    );

    public double calcularCosto(String metodoEnvio, double pesoKg, double distanciaKm, String tipoCliente, boolean esFragil) {
        if (metodoEnvio == null) {
            throw new IllegalArgumentException("Metodo de envio no puede ser nulo");
        }

        EstrategiaEnvio estrategia = estrategias.get(metodoEnvio);
        if (estrategia == null) {
            throw new IllegalArgumentException("Metodo de envio no soportado: " + metodoEnvio);
        }

        double costo = estrategia.calcular(pesoKg, distanciaKm);

        if (esFragil) {
            costo = costo + costo * 0.15;
        }

        if (tipoCliente != null) {
            if (tipoCliente.equals("PREMIUM")) {
                costo = costo * 0.9;
            } else if (tipoCliente.equals("CORPORATIVO")) {
                costo = costo * 0.85;
            }
        }

        return costo;
    }
}
