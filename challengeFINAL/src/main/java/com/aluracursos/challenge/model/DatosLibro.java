package com.aluracursos.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        int id,
        String title,
        List<Autor> authors,
        List<String> subjects,
        List<String> languages,
        String media_type,
        boolean copyright,
        Map<String, String> formats,
        int download_count
) {}