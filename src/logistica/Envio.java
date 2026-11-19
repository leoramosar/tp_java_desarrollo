package logistica;

import java.util.ArrayList;
import java.util.List;

public abstract class Envio {
    private String codigo;
    private List<Paquete> listaPaquetes;

    public Envio(String codigo) {
        this.codigo = codigo;
        this.listaPaquetes = new ArrayList<>();
    }

    public Envio(String codigo, Paquete paquete) {
        this(codigo);
        cargarPaquete(paquete);
    }

    public void cargarPaquete(Paquete paquete) {
        if (listaPaquetes.size() >= 3) {
            System.out.println("El envio " + codigo + " ya tiene 3 paquetes.");
            return;
        }
        if (!tienePesoCorrecto(paquete)) {
            System.out.println("Peso incorrecto en " + paquete.getCodigo());
            return;
        }
        listaPaquetes.add(paquete);
    }

    public void comenzar() {
        for (Paquete p : listaPaquetes) {
            p.pasarAPreparacion();
        }
        System.out.println("Comenzo el envio " + codigo);
    }

    public void completar() {
        for (Paquete p : listaPaquetes) {
            p.pasarADistribucion();
            p.pasarAEntregado();
        }
        System.out.println("Se completo el envio " + codigo);
    }

    public abstract double calcularCosto();

    public abstract String tipo();

    // Cada hijo usa esto y despues aplica su recargo
    protected double sumarPorKilos(double precioKg) {
        double suma = 0;
        for (Paquete p : listaPaquetes) {
            suma = suma + (p.getKilos() * precioKg);
        }
        return suma;
    }

    private boolean tienePesoCorrecto(Paquete paquete) {
        return paquete.getKilos() > 0;
    }

    public List<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public String getCodigo() {
        return codigo;
    }

    public void mostrarResumen() {
        System.out.println(tipo() + " " + codigo
                + " | costo: " + calcularCosto()
                + " | paquetes: " + listaPaquetes.size());
        for (Paquete p : listaPaquetes) {
            System.out.println("   - " + p);
        }
    }

    public String toString() {
        return tipo() + "[" + codigo + "] costo=" + calcularCosto()
                + " paquetes=" + listaPaquetes;
    }
}
