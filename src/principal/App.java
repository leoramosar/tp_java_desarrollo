package principal;

import java.util.ArrayList;
import java.util.List;
import logistica.Cliente;
import logistica.Envio;
import logistica.EnvioExterior;
import logistica.EnvioNormal;
import logistica.EnvioRapido;
import logistica.Movimiento;
import logistica.OperacionInvalidaException;
import logistica.Paquete;
import logistica.Sucursal;

public class App {
    public static void main(String[] args) {
        Cliente ana = new Cliente("40111222", "Ana Perez");
        Cliente luis = new Cliente("30123456", "Luis Gomez");

        Sucursal viedma = new Sucursal("Sucursal Viedma", "Viedma");
        Sucursal bahia = new Sucursal("Sucursal Bahia Blanca", "Bahia Blanca");
        Sucursal bsas = new Sucursal("Sucursal Buenos Aires", "Buenos Aires");

        List<Envio> registrados = new ArrayList<>();

        try {
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

            // --- Envio normal (queda en camino) ---
            EnvioNormal envioAna2 = new EnvioNormal("ENV03");
            envioAna2.cargarPaquete(new Paquete("C1", "Remera", 0.4));
            envioAna2.cargarPaquete(new Paquete("C2", "Pantalon", 0.7));
            envioAna2.asignarCliente(ana);

            viedma.recibirEnvio(envioAna2);
            viedma.despacharEnvio(envioAna2);
            bahia.recibirEnvio(envioAna2);
            registrados.add(envioAna2);

            // Consultas: App pide datos y despues muestra
            System.out.println("\n===== Historial ENV01 =====");
            mostrarHistorial(envioAna);
            mostrarSucursales(envioAna);
            mostrarUltimo(envioAna);

            System.out.println("\n===== Historial ENV03 =====");
            mostrarHistorial(envioAna2);
            mostrarSucursales(envioAna2);
            mostrarUltimo(envioAna2);

            System.out.println("\nEnvios de " + ana.getNombre() + ":");
            for (Envio e : ana.obtenerEnvios()) {
                System.out.println("  - " + e.getCodigo() + " (" + e.tipo() + ")");
            }

            System.out.println("\nEnvios de " + luis.getNombre() + ":");
            for (Envio e : luis.obtenerEnvios()) {
                System.out.println("  - " + e.getCodigo() + " (" + e.tipo() + ")");
            }

            System.out.println("\n===== Envios registrados =====");
            double total = 0;
            int entregados = 0;
            for (Envio envio : registrados) {
                System.out.println(envio.resumen());
                total = total + envio.calcularCosto();
                if (envio.estaFinalizado()) {
                    entregados++;
                }
            }
            System.out.println("Total envios: " + registrados.size());
            System.out.println("Entregados: " + entregados);
            System.out.println("Costo total: " + total);

            // Pruebas con excepciones
            System.out.println("\n===== Pruebas de validacion =====");
            probar("recibir envio sin paquetes", () -> {
                EnvioNormal vacio = new EnvioNormal("ENV-VACIO");
                vacio.asignarCliente(luis);
                viedma.recibirEnvio(vacio);
            });

            probar("entregar sin despachar", () -> {
                EnvioNormal temprano = new EnvioNormal("ENV-TEMP");
                temprano.cargarPaquete(new Paquete("X1", "Caja", 1.0));
                temprano.asignarCliente(ana);
                temprano.entregar();
            });

            probar("despachar envio ya entregado", () -> viedma.despacharEnvio(envioAna));
            probar("entregar envio ya entregado", envioAna::entregar);

        } catch (OperacionInvalidaException e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void mostrarHistorial(Envio envio) {
        System.out.println("Historial de " + envio.getCodigo() + ":");
        for (Movimiento m : envio.obtenerHistorial()) {
            System.out.println("  - " + m);
        }
    }

    private static void mostrarSucursales(Envio envio) {
        System.out.println("Sucursales de " + envio.getCodigo() + ":");
        for (Sucursal s : envio.obtenerSucursalesRecorridas()) {
            System.out.println("  - " + s.descripcion());
        }
    }

    private static void mostrarUltimo(Envio envio) throws OperacionInvalidaException {
        Movimiento ultimo = envio.obtenerUltimoMovimiento();
        System.out.println("Ultimo movimiento: " + ultimo);
    }

    private static void probar(String caso, Prueba prueba) {
        try {
            prueba.ejecutar();
            System.out.println("Fallo la prueba (debia lanzar excepcion): " + caso);
        } catch (OperacionInvalidaException e) {
            System.out.println("OK - " + caso + " -> " + e.getMessage());
        }
    }

    private interface Prueba {
        void ejecutar() throws OperacionInvalidaException;
    }
}
