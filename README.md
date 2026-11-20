# Sistema de Gestion de Envios (TP1 + TP2 + TP2.1)

Proyecto evolucionado. El TP2.1 corrige y completa el TP2 (no es un trabajo nuevo).

## Estructura

- `principal/App.java` → prueba del sistema
- `logistica/Paquete.java` / `Estado.java`
- `logistica/Envio.java` (+ Normal / Rapido / Exterior)
- `logistica/Cliente.java` / `Sucursal.java`
- `logistica/Movimiento.java` + `TipoMovimiento.java`
- `logistica/OperacionInvalidaException.java`
- `DIAGRAMAS.md` → diagrama de clases + 2 de secuencia (exportar en mermaid.live)

## Correcciones TP2.1

1. **Movimiento**: el tipo NO es un String. Usa `TipoMovimiento` (enum del dominio) + sucursal.
2. **Consultas**: `obtenerHistorial()`, `obtenerSucursalesRecorridas()`, `obtenerUltimoMovimiento()` devuelven datos; App muestra.
3. **Excepciones**: operaciones invalidas lanzan `OperacionInvalidaException`.
4. **Metodos privados**: `hayLugar()`, `todaviaNoSalio()`, validaciones y pasos internos.
5. **Estados**: cada cambio esta en un metodo del Paquete (sin cadena larga de if/else).

## Flujo de colaboracion

`App → Sucursal → Envio → Paquete` (+ registro de `Movimiento` en el historial del envio)

## Entrega

- Codigo en repositorio online
- Diagrama de clases (grafico)
- 2 diagramas de secuencia (grafico) → ver `DIAGRAMAS.md`
