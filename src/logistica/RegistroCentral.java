package logistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base de datos en memoria del sistema.
 * Solo registra, busca y lista. No hace acciones de negocio.
 */
public class RegistroCentral {
    private List<Cliente> clientes;
    private Map<Integer, Envio> envios;
    private Map<String, Sucursal> sucursales;

    public RegistroCentral() {
        this.clientes = new ArrayList<>();
        this.envios = new HashMap<>();
        this.sucursales = new HashMap<>();
    }

    public void registrarCliente(Cliente c) throws OperacionInvalidaException {
        if (c == null) {
            throw new OperacionInvalidaException("No se puede registrar un cliente inexistente.");
        }
        if (buscarClienteEnLista(c.getDni()) != null) {
            throw new OperacionInvalidaException(
                    "Ya existe un cliente con DNI " + c.getDni());
        }
        clientes.add(c);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    public void registrarSucursal(Sucursal s) throws OperacionInvalidaException {
        if (s == null) {
            throw new OperacionInvalidaException("No se puede registrar una sucursal inexistente.");
        }
        if (sucursales.containsKey(s.getCodigo())) {
            throw new OperacionInvalidaException(
                    "Ya existe una sucursal con codigo " + s.getCodigo());
        }
        sucursales.put(s.getCodigo(), s);
    }

    public Sucursal buscarSucursal(String codigo) throws OperacionInvalidaException {
        Sucursal s = sucursales.get(codigo);
        if (s == null) {
            throw new OperacionInvalidaException(
                    "No existe sucursal con codigo " + codigo);
        }
        return s;
    }

    public void registrarEnvio(Envio e) throws OperacionInvalidaException {
        if (e == null) {
            throw new OperacionInvalidaException("No se puede registrar un envio inexistente.");
        }
        if (envios.containsKey(e.getCodigo())) {
            throw new OperacionInvalidaException(
                    "Ya existe un envio con codigo " + e.getCodigo());
        }
        envios.put(e.getCodigo(), e);
    }

    public Envio buscarEnvio(int codigo) throws OperacionInvalidaException {
        Envio e = envios.get(codigo);
        if (e == null) {
            throw new OperacionInvalidaException(
                    "No existe envio con codigo " + codigo);
        }
        return e;
    }

    // Parte 4.a: recorre la lista desde el principio
    public Cliente buscarClienteSecuencial(int dni) throws OperacionInvalidaException {
        Cliente encontrado = buscarClienteEnLista(dni);
        if (encontrado == null) {
            throw new OperacionInvalidaException(
                    "No existe cliente con DNI " + dni);
        }
        return encontrado;
    }

    private Cliente buscarClienteEnLista(int dni) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }

    public List<Envio> listarEnvios() {
        return new ArrayList<>(envios.values());
    }
}
