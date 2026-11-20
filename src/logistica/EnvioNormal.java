package logistica;

public class EnvioNormal extends Envio {

    public EnvioNormal(String codigo) {
        super(codigo);
    }

    public EnvioNormal(String codigo, Paquete paquete) throws OperacionInvalidaException {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        return sumarPorKilos(80);
    }

    @Override
    public String tipo() {
        return "Normal";
    }
}
