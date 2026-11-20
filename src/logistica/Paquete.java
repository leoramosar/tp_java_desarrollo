package logistica;

public class Paquete {
    private String codigo;
    private String detalle;
    private double kilos;
    private Estado estado;

    public Paquete(String codigo, double kilos) {
        this.codigo = codigo;
        this.detalle = "Sin detalle";
        this.kilos = kilos;
        this.estado = Estado.RECIBIDO;
    }

    public Paquete(String codigo, String detalle, double kilos) {
        this.codigo = codigo;
        this.detalle = detalle;
        this.kilos = kilos;
        this.estado = Estado.RECIBIDO;
    }

    public void pasarAPreparacion() throws OperacionInvalidaException {
        if (!estaRecibido()) {
            throw new OperacionInvalidaException(
                    "Paquete " + codigo + " no puede pasar a preparacion desde " + estado);
        }
        estado = Estado.EN_PREPARACION;
    }

    public void iniciarViaje() throws OperacionInvalidaException {
        if (!estaEnPreparacion()) {
            throw new OperacionInvalidaException(
                    "Paquete " + codigo + " no puede iniciar viaje desde " + estado);
        }
        estado = Estado.EN_VIAJE;
    }

    public void entregar() throws OperacionInvalidaException {
        if (!estaEnViaje()) {
            throw new OperacionInvalidaException(
                    "Paquete " + codigo + " no puede entregarse desde " + estado);
        }
        estado = Estado.ENTREGADO;
    }

    public boolean estaRecibido() {
        return estado == Estado.RECIBIDO;
    }

    public boolean estaEnPreparacion() {
        return estado == Estado.EN_PREPARACION;
    }

    public boolean estaEnViaje() {
        return estado == Estado.EN_VIAJE;
    }

    public boolean estaEntregado() {
        return estado == Estado.ENTREGADO;
    }

    public double getKilos() {
        return kilos;
    }

    public String getCodigo() {
        return codigo;
    }

    public Estado getEstado() {
        return estado;
    }

    public String toString() {
        return codigo + " (" + detalle + ", " + kilos + " kg, " + estado + ")";
    }
}
