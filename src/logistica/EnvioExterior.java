package logistica;

public class EnvioExterior extends Envio {

    public EnvioExterior(int codigo) {
        super(codigo);
    }

    public EnvioExterior(int codigo, Paquete paquete) throws OperacionInvalidaException {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        return sumarPorKilos(80) * 1.8;
    }

    @Override
    public String tipo() {
        return "Exterior";
    }
}
