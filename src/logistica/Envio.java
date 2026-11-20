package logistica;

import java.util.ArrayList;
import java.util.List;

public abstract class Envio {
    private String codigo;
    private List<Paquete> listaPaquetes;
    private Cliente cliente;
    private Sucursal sucursalActual;
    private List<Movimiento> historial;
    private boolean finalizado;

    public Envio(String codigo) {
        this.codigo = codigo;
        this.listaPaquetes = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.finalizado = false;
    }

    public Envio(String codigo, Paquete paquete) {
        this(codigo);
        cargarPaquete(paquete);
    }

    public void cargarPaquete(Paquete paquete) {
        if (finalizado) {
            System.out.println("El envio " + codigo + " ya esta entregado. No se agregan paquetes.");
            return;
        }
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

    // Relacion Cliente <-> Envio
    public void asignarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente invalido para el envio " + codigo);
            return;
        }
        this.cliente = cliente;
        cliente.registrarEnvio(this);
    }

    // Llega a una sucursal
    public void recibirEn(Sucursal sucursal) {
        if (!puedeOperar()) {
            return;
        }
        if (!tienePaquetes()) {
            System.out.println("No se puede recibir el envio " + codigo + " sin paquetes.");
            return;
        }

        this.sucursalActual = sucursal;

        // Primera sucursal: los paquetes pasan a preparacion
        for (Paquete p : listaPaquetes) {
            if (p.getEstado() == Estado.RECIBIDO) {
                p.pasarAPreparacion();
            }
        }

        registrarMovimiento("Envio recibido", sucursal.descripcion());
        System.out.println("Envio " + codigo + " recibido en " + sucursal.descripcion());
    }

    // Sale de la sucursal actual
    public void despacharDesde(Sucursal sucursal) {
        if (!puedeOperar()) {
            return;
        }
        if (sucursalActual == null || !sucursalActual.getNombre().equals(sucursal.getNombre())) {
            System.out.println("El envio " + codigo + " no esta en " + sucursal.descripcion());
            return;
        }

        boolean ok = true;
        for (Paquete p : listaPaquetes) {
            if (p.getEstado() == Estado.EN_PREPARACION) {
                ok = p.iniciarViaje() && ok;
            } else if (p.getEstado() != Estado.EN_VIAJE) {
                System.out.println("No se puede despachar. Paquete " + p.getCodigo()
                        + " esta en " + p.getEstado());
                ok = false;
            }
        }

        if (!ok) {
            return;
        }

        registrarMovimiento("Envio despachado", sucursal.descripcion());
        System.out.println("Envio " + codigo + " despachado desde " + sucursal.descripcion());
    }

    // Llega al destino final
    public void entregar() {
        if (finalizado) {
            System.out.println("El envio " + codigo + " ya fue entregado.");
            return;
        }
        if (!tienePaquetes()) {
            System.out.println("No se puede entregar el envio " + codigo + " sin paquetes.");
            return;
        }

        boolean ok = true;
        for (Paquete p : listaPaquetes) {
            if (!p.estaEnViaje()) {
                System.out.println("No se puede entregar. Paquete " + p.getCodigo()
                        + " no esta EN_VIAJE (estado: " + p.getEstado() + ")");
                ok = false;
            }
        }
        if (!ok) {
            return;
        }

        for (Paquete p : listaPaquetes) {
            p.entregar();
        }

        finalizado = true;
        sucursalActual = null;
        registrarMovimiento("Envio entregado", "Destino");
        System.out.println("Envio " + codigo + " entregado.");
    }

    public void mostrarHistorial() {
        System.out.println("Historial del envio " + codigo + ":");
        if (historial.isEmpty()) {
            System.out.println("  (sin movimientos)");
            return;
        }
        for (Movimiento m : historial) {
            System.out.println("  - " + m);
        }
    }

    public void mostrarSucursalesRecorridas() {
        System.out.println("Sucursales del envio " + codigo + ":");
        List<String> vistas = new ArrayList<>();
        for (Movimiento m : historial) {
            String lugar = m.getDonde();
            if (lugar.equals("Destino")) {
                continue;
            }
            if (!vistas.contains(lugar)) {
                vistas.add(lugar);
                System.out.println("  - " + lugar);
            }
        }
        if (vistas.isEmpty()) {
            System.out.println("  (todavia no paso por sucursales)");
        }
    }

    public void mostrarUltimoMovimiento() {
        if (historial.isEmpty()) {
            System.out.println("El envio " + codigo + " no tiene movimientos.");
            return;
        }
        Movimiento ultimo = historial.get(historial.size() - 1);
        System.out.println("Ultimo movimiento de " + codigo + ": " + ultimo);
    }

    public abstract double calcularCosto();

    public abstract String tipo();

    protected double sumarPorKilos(double precioKg) {
        double suma = 0;
        for (Paquete p : listaPaquetes) {
            suma = suma + (p.getKilos() * precioKg);
        }
        return suma;
    }

    private void registrarMovimiento(String quePaso, String donde) {
        historial.add(new Movimiento(quePaso, donde));
    }

    private boolean puedeOperar() {
        if (finalizado) {
            System.out.println("Operacion invalida: el envio " + codigo + " ya fue entregado.");
            return false;
        }
        return true;
    }

    private boolean tienePaquetes() {
        return !listaPaquetes.isEmpty();
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

    public Cliente getCliente() {
        return cliente;
    }

    public boolean estaFinalizado() {
        return finalizado;
    }

    public void mostrarResumen() {
        String nombreCliente = (cliente == null) ? "sin cliente" : cliente.getNombre();
        System.out.println(tipo() + " " + codigo
                + " | cliente: " + nombreCliente
                + " | costo: " + calcularCosto()
                + " | paquetes: " + listaPaquetes.size()
                + " | entregado: " + finalizado);
        for (Paquete p : listaPaquetes) {
            System.out.println("   - " + p);
        }
    }

    public String toString() {
        return tipo() + "[" + codigo + "] costo=" + calcularCosto()
                + " paquetes=" + listaPaquetes;
    }
}
