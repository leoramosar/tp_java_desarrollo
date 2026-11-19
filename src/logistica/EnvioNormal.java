package logistica;

public class EnvioNormal extends Envio {

    public EnvioNormal(String codigo) {
        super(codigo);
    }

    public EnvioNormal(String codigo, Paquete paquete) {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        // Precio simple: 80 por kilo
        return sumarPorKilos(80);
    }

    @Override
    public String tipo() {
        return "Normal";
    }
}
