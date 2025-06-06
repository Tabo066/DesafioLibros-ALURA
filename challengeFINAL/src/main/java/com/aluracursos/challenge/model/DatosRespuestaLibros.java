package com.aluracursos.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaLibros(
        int count,
        String next,
        String previous,
        List<DatosLibro> results
) {}