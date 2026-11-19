package logistica;

public class Paquete {
    private String codigo;
    private String detalle;
    private double kilos;
    private Estado estado;

    // Minimo: solo codigo y kilos
    public Paquete(String codigo, double kilos) {
        this.codigo = codigo;
        this.detalle = "Sin detalle";
        this.kilos = kilos;
        this.estado = Estado.RECIBIDO;
    }

    // Completo: codigo, detalle y kilos
    public Paquete(String codigo, String detalle, double kilos) {
        this.codigo = codigo;
        this.detalle = detalle;
        this.kilos = kilos;
        this.estado = Estado.RECIBIDO;
    }

    public void pasarAPreparacion() {
        cambiarEstado(Estado.RECIBIDO, Estado.EN_PREPARACION);
    }

    public void pasarADistribucion() {
        cambiarEstado(Estado.EN_PREPARACION, Estado.EN_DISTRIBUCION);
    }

    public void pasarAEntregado() {
        cambiarEstado(Estado.EN_DISTRIBUCION, Estado.ENTREGADO);
    }

    // Solo el propio paquete controla si el cambio es valido
    private void cambiarEstado(Estado desde, Estado hacia) {
        if (estado == desde) {
            estado = hacia;
        }
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
