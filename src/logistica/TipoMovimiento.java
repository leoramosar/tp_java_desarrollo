package logistica;

// El tipo de acontecimiento es un objeto del dominio (enum), no un String libre
public enum TipoMovimiento {
    RECIBIDO_EN_SUCURSAL,
    DESPACHADO_DESDE_SUCURSAL,
    ENTREGADO
}
