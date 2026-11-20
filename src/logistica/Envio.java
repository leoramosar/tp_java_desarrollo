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

    public Envio(String codigo, Paquete paquete) throws OperacionInvalidaException {
        this(codigo);
        cargarPaquete(paquete);
    }

    public void cargarPaquete(Paquete paquete) throws OperacionInvalidaException {
        if (!todaviaNoSalio()) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " ya salio. No se agregan paquetes.");
        }
        if (!hayLugar()) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " ya tiene el maximo de 3 paquetes.");
        }
        if (!tienePesoCorrecto(paquete)) {
            throw new OperacionInvalidaException(
                    "Peso incorrecto en el paquete " + paquete.getCodigo());
        }
        listaPaquetes.add(paquete);
    }

    public void asignarCliente(Cliente cliente) throws OperacionInvalidaException {
        if (cliente == null) {
            throw new OperacionInvalidaException(
                    "Cliente invalido para el envio " + codigo);
        }
        this.cliente = cliente;
        cliente.registrarEnvio(this);
    }

    public void recibirEn(Sucursal sucursal) throws OperacionInvalidaException {
        validarQuePuedeOperar();
        validarQueTienePaquetes();

        this.sucursalActual = sucursal;
        prepararPaquetesAlRecibir();
        registrarMovimiento(TipoMovimiento.RECIBIDO_EN_SUCURSAL, sucursal);
    }

    public void despacharDesde(Sucursal sucursal) throws OperacionInvalidaException {
        validarQuePuedeOperar();
        validarQueEstaEnSucursal(sucursal);
        ponerPaquetesEnViaje();
        registrarMovimiento(TipoMovimiento.DESPACHADO_DESDE_SUCURSAL, sucursal);
    }

    public void entregar() throws OperacionInvalidaException {
        if (estaFinalizado()) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " ya fue entregado.");
        }
        validarQueTienePaquetes();
        entregarPaquetes();

        finalizado = true;
        sucursalActual = null;
        registrarMovimiento(TipoMovimiento.ENTREGADO, null);
    }

    // Consultas: devuelven datos (App los muestra)
    public List<Movimiento> obtenerHistorial() {
        return historial;
    }

    public List<Sucursal> obtenerSucursalesRecorridas() {
        List<Sucursal> sucursales = new ArrayList<>();
        for (Movimiento m : historial) {
            if (m.tieneSucursal() && !sucursales.contains(m.getSucursal())) {
                sucursales.add(m.getSucursal());
            }
        }
        return sucursales;
    }

    public Movimiento obtenerUltimoMovimiento() throws OperacionInvalidaException {
        if (historial.isEmpty()) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " no tiene movimientos.");
        }
        return historial.get(historial.size() - 1);
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

    private void prepararPaquetesAlRecibir() throws OperacionInvalidaException {
        for (Paquete p : listaPaquetes) {
            if (p.estaRecibido()) {
                p.pasarAPreparacion();
            }
        }
    }

    private void ponerPaquetesEnViaje() throws OperacionInvalidaException {
        for (Paquete p : listaPaquetes) {
            if (p.estaEnPreparacion()) {
                p.iniciarViaje();
            } else if (!p.estaEnViaje()) {
                throw new OperacionInvalidaException(
                        "No se puede despachar. Paquete " + p.getCodigo()
                                + " esta en " + p.getEstado());
            }
        }
    }

    private void entregarPaquetes() throws OperacionInvalidaException {
        for (Paquete p : listaPaquetes) {
            p.entregar();
        }
    }

    private void registrarMovimiento(TipoMovimiento tipo, Sucursal sucursal)
            throws OperacionInvalidaException {
        if (tipo == null) {
            throw new OperacionInvalidaException("Movimiento incoherente: tipo nulo.");
        }
        if (tipo == TipoMovimiento.ENTREGADO) {
            historial.add(new Movimiento(tipo));
            return;
        }
        if (sucursal == null) {
            throw new OperacionInvalidaException(
                    "Movimiento incoherente: falta la sucursal.");
        }
        historial.add(new Movimiento(tipo, sucursal));
    }

    private void validarQuePuedeOperar() throws OperacionInvalidaException {
        if (estaFinalizado()) {
            throw new OperacionInvalidaException(
                    "Operacion invalida: el envio " + codigo + " ya fue entregado.");
        }
    }

    private void validarQueTienePaquetes() throws OperacionInvalidaException {
        if (!tienePaquetes()) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " no tiene paquetes.");
        }
    }

    private void validarQueEstaEnSucursal(Sucursal sucursal)
            throws OperacionInvalidaException {
        if (sucursalActual == null || !sucursalActual.getNombre().equals(sucursal.getNombre())) {
            throw new OperacionInvalidaException(
                    "El envio " + codigo + " no esta en " + sucursal.descripcion());
        }
    }

    private boolean hayLugar() {
        return listaPaquetes.size() < 3;
    }

    private boolean todaviaNoSalio() {
        return !finalizado && sucursalActual == null && historial.isEmpty();
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

    public String resumen() {
        String nombreCliente = (cliente == null) ? "sin cliente" : cliente.getNombre();
        return tipo() + " " + codigo
                + " | cliente: " + nombreCliente
                + " | costo: " + calcularCosto()
                + " | paquetes: " + listaPaquetes.size()
                + " | entregado: " + finalizado;
    }

    public String toString() {
        return tipo() + "[" + codigo + "] costo=" + calcularCosto()
                + " paquetes=" + listaPaquetes;
    }
}
