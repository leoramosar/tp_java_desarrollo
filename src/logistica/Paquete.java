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

    public boolean pasarAPreparacion() {
        return cambiarEstado(Estado.RECIBIDO, Estado.EN_PREPARACION);
    }

    public boolean iniciarViaje() {
        return cambiarEstado(Estado.EN_PREPARACION, Estado.EN_VIAJE);
    }

    public boolean entregar() {
        return cambiarEstado(Estado.EN_VIAJE, Estado.ENTREGADO);
    }

    private boolean cambiarEstado(Estado desde, Estado hacia) {
        if (!transicionValida(desde, hacia)) {
            System.out.println("Paquete " + codigo + ": no se puede pasar de "
                    + estado + " a " + hacia);
            return false;
        }
        estado = hacia;
        return true;
    }

    private boolean transicionValida(Estado desde, Estado hacia) {
        return estado == desde;
    }

    public boolean estaEntregado() {
        return estado == Estado.ENTREGADO;
    }

    public boolean estaEnViaje() {
        return estado == Estado.EN_VIAJE;
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
