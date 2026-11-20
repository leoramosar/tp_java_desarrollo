package logistica;

public class Sucursal {
    private String nombre;
    private String ciudad;

    public Sucursal(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    // La sucursal recibe el mensaje y le pide al envio que se actualice
    public void recibirEnvio(Envio envio) {
        if (envio == null) {
            System.out.println("No hay envio para recibir en " + nombre);
            return;
        }
        envio.recibirEn(this);
    }

    // La sucursal despacha y el envio coordina sus paquetes e historial
    public void despacharEnvio(Envio envio) {
        if (envio == null) {
            System.out.println("No hay envio para despachar desde " + nombre);
            return;
        }
        envio.despacharDesde(this);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String descripcion() {
        return nombre + " (" + ciudad + ")";
    }

    public String toString() {
        return descripcion();
    }
}
