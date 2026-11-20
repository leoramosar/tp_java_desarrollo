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

    public void registrarEnvio(Envio envio) throws OperacionInvalidaException {
        if (envio == null) {
            throw new OperacionInvalidaException("No se puede registrar un envio inexistente.");
        }
        envios.add(envio);
    }

    public List<Envio> obtenerEnvios() {
        return envios;
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
