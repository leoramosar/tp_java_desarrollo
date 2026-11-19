# Trabajo Practico N1 - Sistema de Gestion de Envios

## Estructura del proyecto

- `principal/App.java` → prueba del sistema
- `logistica/Paquete.java` → el objeto que se transporta
- `logistica/Estado.java` → etapas del ciclo de vida
- `logistica/Envio.java` → clase base abstracta
- `logistica/EnvioNormal.java` → envio estandar
- `logistica/EnvioRapido.java` → envio express
- `logistica/EnvioExterior.java` → envio internacional

## Preguntas de reflexion

### 1. ¿Que responsabilidades asignaste a cada objeto?

Al **Paquete** le di guardar codigo, detalle, kilos y estado. El mismo paquete cambia de etapa con pasarAPreparacion, pasarADistribucion y pasarAEntregado.

Al **Envio** le di juntar hasta 3 paquetes, comenzar/completar el recorrido y calcular el costo. Como hay tres tipos, Envio es abstracta y cada clase hija responde distinto a calcularCosto y tipo.

En **App** creo los objetos, los relaciono, los meto en una lista de la jornada y muestro un resumen.

### 2. ¿Que decisiones te resultaron dificiles?

Me costo armar bien el cambio de estados sin usar setters. Tambien pensar como no repetir el calculo de kilos en cada tipo de envio: por eso arme sumarPorKilos en la clase padre. El ArrayList en App al principio me confundo, pero despues se entiende al recorrer la jornada.

### 3. ¿Que metodos decidiste hacer privados y por que?

En Paquete, `cambiarEstado` es privado porque es una regla interna: solo se usa desde los metodos publicos del mismo paquete. En Envio, `tienePesoCorrecto` tambien es privado: sirve solo al cargar un paquete y no hace falta exponerlo.

### 4. ¿Que setters evitaste implementar y por que?

No hice setEstado ni setKilos. Si existiera setEstado, se podria pasar de RECIBIDO a ENTREGADO de un salto y romper el flujo real del negocio. Prefiero que el estado cambie solo con los metodos del ciclo.

### 5. ¿Que codigo tuviste que cambiar al incorporar herencia?

Pase Envio a abstracta, agregue calcularCosto y tipo como metodos abstractos, y cree EnvioNormal, EnvioRapido y EnvioExterior. Cada una calcula distinto sin usar if por tipo.
