package principal;

import java.util.ArrayList;
import java.util.List;
import logistica.Envio;
import logistica.EnvioExterior;
import logistica.EnvioNormal;
import logistica.EnvioRapido;
import logistica.Paquete;

public class App {
    public static void main(String[] args) {
        List<Envio> jornada = new ArrayList<>();

        EnvioRapido rapido = new EnvioRapido("ENV01");
        rapido.cargarPaquete(new Paquete("A1", "Manuales", 3.0));
        rapido.cargarPaquete(new Paquete("A2", "Hojas A4", 2.5));
        rapido.comenzar();
        rapido.completar();
        jornada.add(rapido);

        EnvioExterior exterior = new EnvioExterior(
                "ENV02",
                new Paquete("B1", "Cargador", 0.8)
        );
        exterior.cargarPaquete(new Paquete("B2", 1.2));
        exterior.comenzar();
        exterior.completar();
        jornada.add(exterior);

        EnvioNormal normal = new EnvioNormal("ENV03");
        normal.cargarPaquete(new Paquete("C1", "Remera", 0.4));
        normal.cargarPaquete(new Paquete("C2", "Pantalon", 0.7));
        normal.cargarPaquete(new Paquete("C3", "Campera", 1.1));
        normal.comenzar();
        normal.completar();
        jornada.add(normal);

        System.out.println("\n===== Jornada de envios =====");
        for (Envio envio : jornada) {
            envio.mostrarResumen();
            System.out.println();
        }
    }
}
