package logistica;

public class EnvioNormal extends Envio {

    public EnvioNormal(int codigo) {
        super(codigo);
    }

    public EnvioNormal(int codigo, Paquete paquete) throws OperacionInvalidaException {
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
