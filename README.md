# Sistema de Gestion de Envios (TP1 + TP2)

Proyecto evolucionado: el TP2 se construye sobre el TP1 (no es un proyecto nuevo).

## Estructura

- `principal/App.java` → prueba del sistema
- `logistica/Paquete.java` → objeto transportado y sus estados
- `logistica/Estado.java` → RECIBIDO / EN_PREPARACION / EN_VIAJE / ENTREGADO
- `logistica/Envio.java` → clase base (paquetes, cliente, historial, reglas)
- `logistica/EnvioNormal.java` / `EnvioRapido.java` / `EnvioExterior.java`
- `logistica/Cliente.java` → quien solicita envios
- `logistica/Sucursal.java` → recibe y despacha envios
- `logistica/Movimiento.java` → un acontecimiento del recorrido

## Como se relacionan (TP2)

1. Un **Cliente** puede tener varios envios (`List<Envio>`).
2. Una **Sucursal** recibe/despacha: le manda mensajes al **Envio**.
3. El **Envio** actualiza paquetes y guarda **Movimientos** en su historial.
4. Las validaciones viven cerca del objeto que conoce el estado (Paquete/Envio), no en App con muchos if.

## Preguntas de reflexion (TP1)

### 1. ¿Que responsabilidades asignaste a cada objeto?

Al **Paquete** le di guardar codigo, detalle, kilos y estado. El mismo paquete cambia de etapa.

Al **Envio** le di juntar hasta 3 paquetes, coordinar el recorrido y calcular el costo. Como hay tres tipos, Envio es abstracta.

En **App** creo los objetos, los relaciono y muestro resultados.

### 2. ¿Que decisiones te resultaron dificiles?

Me costo armar bien el cambio de estados sin setters y entender la herencia para el calculo de costos.

### 3. ¿Que metodos decidiste hacer privados y por que?

En Paquete, `cambiarEstado` y `transicionValida` son privados. En Envio, cosas como `registrarMovimiento`, `puedeOperar` y `tienePaquetes` tambien: son reglas internas.

### 4. ¿Que setters evitaste implementar y por que?

No hice setEstado. Si existiera, se podria saltar etapas. El estado cambia solo con metodos del negocio.

### 5. ¿Que codigo tuviste que cambiar al incorporar herencia?

Pase Envio a abstracta y cree EnvioNormal, EnvioRapido y EnvioExterior.

## Decisiones del TP2 (resumen)

- **Cliente**: guarda documento, nombre y su lista de envios. El registra con `registrarEnvio`.
- **Sucursal**: no cambia estados a mano; llama a `recibirEn` / `despacharDesde` del envio.
- **Movimiento**: guarda que paso y donde (alcanza para reconstruir el recorrido).
- **Historial**: lo administra el propio Envio (`mostrarHistorial`, sucursales, ultimo movimiento).
- **Validaciones**: no entregar sin estar EN_VIAJE, no operar un envio ya entregado, no recibir sin paquetes.
