package logistica;

public class EnvioExterior extends Envio {

    public EnvioExterior(String codigo) {
        super(codigo);
    }

    public EnvioExterior(String codigo, Paquete paquete) {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        // Exterior: 80 por kilo + 80% de recargo
        return sumarPorKilos(80) * 1.8;
    }

    @Override
    public String tipo() {
        return "Exterior";
    }
}
