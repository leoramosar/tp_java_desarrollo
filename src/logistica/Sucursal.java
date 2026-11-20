package logistica;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Sucursal {
    private String codigo;
    private String nombre;
    private String ciudad;
    // Cola FIFO: los envios esperan turno para salir en el camion
    private Queue<Envio> colaDespacho;

    public Sucursal(String codigo, String nombre, String ciudad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.colaDespacho = new ArrayDeque<>();
    }

    // Recibe el envio (negocio) y lo pone al final de la cola
    public void recibirEnvio(Envio envio) throws OperacionInvalidaException {
        if (envio == null) {
            throw new OperacionInvalidaException(
                    "No se puede recibir un envio inexistente en " + nombre);
        }
        envio.recibirEn(this);
        colaDespacho.offer(envio);
    }

    // Sale como maximo "cantidad" envios, en orden de llegada
    public List<Envio> despachar(int cantidad) throws OperacionInvalidaException {
        if (colaDespacho.isEmpty()) {
            throw new OperacionInvalidaException(
                    "La cola de " + nombre + " esta vacia. No hay nada para despachar.");
        }

        List<Envio> salieron = new ArrayList<>();
        int salidas = 0;

        while (salidas < cantidad && !colaDespacho.isEmpty()) {
            Envio envio = colaDespacho.poll();
            envio.despacharDesde(this);
            salieron.add(envio);
            salidas++;
        }

        return salieron;
    }

    public int cantidadEnCola() {
        return colaDespacho.size();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String descripcion() {
        return nombre + " (" + ciudad + ")";
    }

    public String toString() {
        return codigo + " - " + descripcion();
    }
}
