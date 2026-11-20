package principal;

import logistica.Cliente;
import logistica.Envio;
import logistica.EnvioExterior;
import logistica.EnvioNormal;
import logistica.EnvioRapido;
import logistica.Movimiento;
import logistica.OperacionInvalidaException;
import logistica.Paquete;
import logistica.RegistroCentral;
import logistica.Sucursal;

public class App {
    public static void main(String[] args) {
        // Una sola instancia del registro (base en memoria)
        RegistroCentral registro = new RegistroCentral();

        try {
            // --- Alta de clientes y sucursales en el registro ---
            Cliente ana = new Cliente(40111222, "Ana Perez");
            Cliente luis = new Cliente(30123456, "Luis Gomez");
            registro.registrarCliente(ana);
            registro.registrarCliente(luis);

            Sucursal viedma = new Sucursal("S01", "Sucursal Viedma", "Viedma");
            Sucursal bahia = new Sucursal("S02", "Sucursal Bahia Blanca", "Bahia Blanca");
            Sucursal bsas = new Sucursal("S03", "Sucursal Buenos Aires", "Buenos Aires");
            registro.registrarSucursal(viedma);
            registro.registrarSucursal(bahia);
            registro.registrarSucursal(bsas);

            // --- Crear envios, asociar cliente y registrar ---
            EnvioRapido envio1 = new EnvioRapido(1001);
            envio1.cargarPaquete(new Paquete("A1", "Manuales", 3.0));
            envio1.cargarPaquete(new Paquete("A2", "Hojas A4", 2.5));
            envio1.asignarCliente(ana);
            registro.registrarEnvio(envio1);

            EnvioExterior envio2 = new EnvioExterior(1002, new Paquete("B1", "Cargador", 0.8));
            envio2.cargarPaquete(new Paquete("B2", 1.2));
            envio2.asignarCliente(luis);
            registro.registrarEnvio(envio2);

            EnvioNormal envio3 = new EnvioNormal(1003);
            envio3.cargarPaquete(new Paquete("C1", "Remera", 0.4));
            envio3.cargarPaquete(new Paquete("C2", "Pantalon", 0.7));
            envio3.asignarCliente(ana);
            registro.registrarEnvio(envio3);

            // --- Cola de despacho en Viedma (FIFO) ---
            System.out.println("===== Cola en Viedma =====");
            viedma.recibirEnvio(registro.buscarEnvio(1001));
            viedma.recibirEnvio(registro.buscarEnvio(1002));
            viedma.recibirEnvio(registro.buscarEnvio(1003));
            System.out.println("En cola: " + viedma.cantidadEnCola());

            // El camion solo lleva 2: salen 1001 y 1002 (orden de llegada)
            System.out.println("Despacho capacidad 2:");
            for (Envio e : viedma.despachar(2)) {
                System.out.println("  salio envio " + e.getCodigo());
            }
            System.out.println("Quedan en cola: " + viedma.cantidadEnCola());

            // Sale el que faltaba
            for (Envio e : viedma.despachar(1)) {
                System.out.println("  salio envio " + e.getCodigo());
            }

            // Continua recorrido de 1001 hasta entrega
            Envio e1001 = registro.buscarEnvio(1001);
            bahia.recibirEnvio(e1001);
            bahia.despachar(1);
            bsas.recibirEnvio(e1001);
            bsas.despachar(1);
            e1001.entregar();

            // 1002 llega a Bahia y se entrega
            Envio e1002 = registro.buscarEnvio(1002);
            bahia.recibirEnvio(e1002);
            bahia.despachar(1);
            e1002.entregar();

            // 1003 queda en Bahia (recibido, sin despachar del todo el resto)
            Envio e1003 = registro.buscarEnvio(1003);
            bahia.recibirEnvio(e1003);

            // --- Busquedas desde el registro ---
            System.out.println("\n===== Busquedas =====");
            Cliente c = registro.buscarClienteSecuencial(40111222);
            System.out.println("Cliente por DNI: " + c);

            Envio encontrado = registro.buscarEnvio(1001);
            System.out.println("Envio por codigo: " + encontrado.resumen());

            System.out.println("\nClientes registrados:");
            for (Cliente cli : registro.listarClientes()) {
                System.out.println("  - " + cli);
            }

            System.out.println("\n===== Historial 1001 =====");
            for (Movimiento m : encontrado.obtenerHistorial()) {
                System.out.println("  - " + m);
            }

            // --- Casos invalidos del registro / cola ---
            System.out.println("\n===== Validaciones =====");
            probar("envio duplicado", () -> registro.registrarEnvio(new EnvioNormal(1001)));
            probar("envio inexistente", () -> registro.buscarEnvio(9999));
            probar("cliente inexistente", () -> registro.buscarClienteSecuencial(11111111));
            probar("cola vacia", () -> viedma.despachar(1));

        } catch (OperacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void probar(String caso, Prueba prueba) {
        try {
            prueba.ejecutar();
            System.out.println("Fallo (debia avisar): " + caso);
        } catch (OperacionInvalidaException e) {
            System.out.println("OK - " + caso + " -> " + e.getMessage());
        }
    }

    private interface Prueba {
        void ejecutar() throws OperacionInvalidaException;
    }
}
