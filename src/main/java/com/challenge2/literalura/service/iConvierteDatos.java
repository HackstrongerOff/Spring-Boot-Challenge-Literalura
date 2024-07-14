package com.challenge2.literalura.service;

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
