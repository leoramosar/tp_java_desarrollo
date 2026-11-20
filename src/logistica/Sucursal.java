package logistica;

public class Sucursal {
    private String nombre;
    private String ciudad;

    public Sucursal(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public void recibirEnvio(Envio envio) throws OperacionInvalidaException {
        if (envio == null) {
            throw new OperacionInvalidaException(
                    "No se puede recibir un envio inexistente en " + nombre);
        }
        envio.recibirEn(this);
    }

    public void despacharEnvio(Envio envio) throws OperacionInvalidaException {
        if (envio == null) {
            throw new OperacionInvalidaException(
                    "No se puede despachar un envio inexistente desde " + nombre);
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
