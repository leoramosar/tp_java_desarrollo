# Sistema de Gestion de Envios (TP1 → TP4)

Proyecto evolucionado. El TP4 agrega estructuras de datos sobre el sistema del TP2/TP2.1.

## Estructura

- `principal/App.java` → crea el `RegistroCentral` y prueba el sistema
- `logistica/RegistroCentral.java` → guarda, busca y lista (no hace negocio)
- `logistica/Sucursal.java` → cola FIFO de despacho (`Queue`)
- `logistica/Cliente.java` / `Envio*.java` / `Paquete.java` / `Movimiento.java` ...
- `TEORICO_TP4.md` → respuestas teóricas (exportar a PDF para entregar)
- `DIAGRAMAS.md` → diagramas del TP2.1 (si hacen falta)

## Qué aporta el TP4 (práctico)

| Parte | Qué hay |
|-------|---------|
| 1 | `RegistroCentral` (una instancia en el main) |
| 2 | Envios en `HashMap<Integer, Envio>` + sucursales en `HashMap` |
| 3 | `Queue` en cada sucursal: `recibirEnvio` / `despachar(cantidad)` |
| 4.a | `buscarClienteSecuencial(int dni)` sobre `ArrayList` |

Regla: el registro **busca**; `Envio` / `Sucursal` **hacen** la accion (`entregar`, `despachar`, etc.).

## Entrega TP4

1. Codigo del proyecto (partes 1, 2, 3 y 4.a)
2. PDF con la teoria → exportar `TEORICO_TP4.md` a PDF
