package com.proyecto.proyecto.util;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    AUTHOR_NOT_FOUND("ERR_ATR_001", "Autor no encontrado."),
    INVALID_AUTHOR("ERR_ATR_002", "Parámetros invalido de autor."),

    BOOK_NOT_FOUND("ERR_BK_001", "Libro no encontrado."),
    INVALID_BOOK("ERR_BK_002", "Parámetros invalido de estudiante."),

    CATEGORY_NOT_FOUND("ERR_CAT_001", "Categoría no encontrada."),
    INVALID_CATEGORY("ERR_CAT_002", "Parámetros inválidos de categoría."),

    RESERVATION_NOT_FOUND("ERR_RES_001", "Reserva no encontrada."),
    INVALID_RESERVATION("ERR_RES_002", "Parámetros inválidos de reserva."),

    USER_NOT_FOUND("ERR_USR_001", "Usuario no encontrado."),
    INVALID_USER("ERR_USR_002", "Parámetros inválidos de usuario."),

    HISTORY_NOT_FOUND("ERR_HIS_001", "Historial no encontrado."),
    INVALID_HISTORY("ERR_HIS_002", "Parámetros inválidos de historial."),

    PENALTY_NOT_FOUND("ERR_PEN_001", "Penalidad no encontrada."),
    INVALID_PENALTY("ERR_PEN_002", "Parámetros inválidos de penalidad."),

    REPORT_NOT_FOUND("ERR_REP_001", "Reporte no encontrado."),
    INVALID_REPORT("ERR_REP_002", "Parámetros inválidos de reporte."),

    RETURN_NOT_FOUND("ERR_RET_001", "Devolución no encontrada."),
    INVALID_RETURN("ERR_RET_002", "Parámetros inválidos de devolución."),

    RESERVATION_STATUS_NOT_FOUND("ERR_RS_001", "Estado de reserva no encontrado."),
    INVALID_RESERVATION_STATUS("ERR_RS_002", "Parámetros inválidos de estado de reserva."),


    GENERIC_ERROR("ERR_GEN_001", "Un error inesperado ocurrió.");

    private final String code;
    private final String message;

    ErrorCatalog(String name, String message) {
        this.code = name;
        this.message = message;
    }
}
