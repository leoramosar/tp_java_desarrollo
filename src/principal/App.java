package principal;

import java.util.ArrayList;
import java.util.List;
import logistica.Cliente;
import logistica.Envio;
import logistica.EnvioExterior;
import logistica.EnvioNormal;
import logistica.EnvioRapido;
import logistica.Paquete;
import logistica.Sucursal;

public class App {
    public static void main(String[] args) {
        // Clientes
        Cliente ana = new Cliente("40111222", "Ana Perez");
        Cliente luis = new Cliente("30123456", "Luis Gomez");

        // Sucursales del recorrido
        Sucursal viedma = new Sucursal("Sucursal Viedma", "Viedma");
        Sucursal bahia = new Sucursal("Sucursal Bahia Blanca", "Bahia Blanca");
        Sucursal bsas = new Sucursal("Sucursal Buenos Aires", "Buenos Aires");

        List<Envio> registrados = new ArrayList<>();

        // --- Envio rapido de Ana ---
        EnvioRapido envioAna = new EnvioRapido("ENV01");
        envioAna.cargarPaquete(new Paquete("A1", "Manuales", 3.0));
        envioAna.cargarPaquete(new Paquete("A2", "Hojas A4", 2.5));
        envioAna.asignarCliente(ana);

        viedma.recibirEnvio(envioAna);
        viedma.despacharEnvio(envioAna);
        bahia.recibirEnvio(envioAna);
        bahia.despacharEnvio(envioAna);
        bsas.recibirEnvio(envioAna);
        bsas.despacharEnvio(envioAna);
        envioAna.entregar();
        registrados.add(envioAna);

        // --- Envio exterior de Luis ---
        EnvioExterior envioLuis = new EnvioExterior(
                "ENV02",
                new Paquete("B1", "Cargador", 0.8)
        );
        envioLuis.cargarPaquete(new Paquete("B2", 1.2));
        envioLuis.asignarCliente(luis);

        viedma.recibirEnvio(envioLuis);
        viedma.despacharEnvio(envioLuis);
        bahia.recibirEnvio(envioLuis);
        bahia.despacharEnvio(envioLuis);
        envioLuis.entregar();
        registrados.add(envioLuis);

        // --- Envio normal (queda en camino, no se entrega) ---
        EnvioNormal envioAna2 = new EnvioNormal("ENV03");
        envioAna2.cargarPaquete(new Paquete("C1", "Remera", 0.4));
        envioAna2.cargarPaquete(new Paquete("C2", "Pantalon", 0.7));
        envioAna2.asignarCliente(ana);

        viedma.recibirEnvio(envioAna2);
        viedma.despacharEnvio(envioAna2);
        bahia.recibirEnvio(envioAna2);
        registrados.add(envioAna2);

        // Consultas de historial
        System.out.println("\n===== Historial ENV01 =====");
        envioAna.mostrarHistorial();
        envioAna.mostrarSucursalesRecorridas();
        envioAna.mostrarUltimoMovimiento();

        System.out.println("\n===== Historial ENV03 (en camino) =====");
        envioAna2.mostrarHistorial();
        envioAna2.mostrarSucursalesRecorridas();
        envioAna2.mostrarUltimoMovimiento();

        // Envios por cliente
        System.out.println();
        ana.mostrarEnvios();
        luis.mostrarEnvios();

        // Info general
        System.out.println("\n===== Envios registrados =====");
        double total = 0;
        int entregados = 0;
        for (Envio envio : registrados) {
            envio.mostrarResumen();
            total = total + envio.calcularCosto();
            if (envio.estaFinalizado()) {
                entregados++;
            }
            System.out.println();
        }
        System.out.println("Total envios: " + registrados.size());
        System.out.println("Entregados: " + entregados);
        System.out.println("Costo total: " + total);

        // Pruebas de reglas invalidas
        System.out.println("\n===== Pruebas de validacion =====");
        EnvioNormal vacio = new EnvioNormal("ENV-VACIO");
        vacio.asignarCliente(luis);
        viedma.recibirEnvio(vacio); // sin paquetes

        EnvioNormal temprano = new EnvioNormal("ENV-TEMP");
        temprano.cargarPaquete(new Paquete("X1", "Caja", 1.0));
        temprano.asignarCliente(ana);
        temprano.entregar(); // no fue despachado

        viedma.despacharEnvio(envioAna); // ya entregado
        envioAna.entregar(); // ya entregado
    }
}
