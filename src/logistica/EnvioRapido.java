package logistica;

public class EnvioRapido extends Envio {

    public EnvioRapido(String codigo) {
        super(codigo);
    }

    public EnvioRapido(String codigo, Paquete paquete) {
        super(codigo, paquete);
    }

    @Override
    public double calcularCosto() {
        // Rapido: 80 por kilo + 40% de recargo
        return sumarPorKilos(80) * 1.4;
    }

    @Override
    public String tipo() {
        return "Rapido";
    }
}
