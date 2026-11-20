package logistica;

public class EnvioRapido extends Envio {

    public EnvioRapido(String codigo) {
        super(codigo);
    }

    public EnvioRapido(String codigo, Paquete paquete) throws OperacionInvalidaException {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        return sumarPorKilos(80) * 1.4;
    }

    @Override
    public String tipo() {
        return "Rapido";
    }
}
