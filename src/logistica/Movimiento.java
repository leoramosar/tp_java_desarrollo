package logistica;

public class Movimiento {
    private TipoMovimiento tipo;
    private Sucursal sucursal;

    // Recibido o despachado en una sucursal
    public Movimiento(TipoMovimiento tipo, Sucursal sucursal) {
        this.tipo = tipo;
        this.sucursal = sucursal;
    }

    // Entrega final (sin sucursal)
    public Movimiento(TipoMovimiento tipo) {
        this.tipo = tipo;
        this.sucursal = null;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public boolean tieneSucursal() {
        return sucursal != null;
    }

    public String toString() {
        if (tieneSucursal()) {
            return tipo + " en " + sucursal.descripcion();
        }
        return tipo + " en Destino";
    }
}
