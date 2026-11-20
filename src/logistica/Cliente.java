package logistica;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int dni;
    private String nombre;
    private List<Envio> envios;

    public Cliente(int dni, String nombre) {
        this.dni = dni;
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

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public int cantidadEnvios() {
        return envios.size();
    }

    public String toString() {
        return nombre + " [DNI " + dni + "]";
    }
}
