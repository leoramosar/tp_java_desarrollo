package logistica;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String documento;
    private String nombre;
    private List<Envio> envios;

    public Cliente(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;
        this.envios = new ArrayList<>();
    }

    // El propio cliente registra que hizo un envio nuevo
    public void registrarEnvio(Envio envio) {
        if (envio == null) {
            System.out.println("No se puede registrar un envio nulo.");
            return;
        }
        envios.add(envio);
    }

    public void mostrarEnvios() {
        System.out.println("Envios de " + nombre + " (" + documento + "):");
        if (envios.isEmpty()) {
            System.out.println("  (sin envios)");
            return;
        }
        for (Envio envio : envios) {
            System.out.println("  - " + envio.getCodigo() + " (" + envio.tipo() + ")");
        }
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public int cantidadEnvios() {
        return envios.size();
    }

    public String toString() {
        return nombre + " [" + documento + "]";
    }
}
