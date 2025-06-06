package com.aluracursos.challenge.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AutorLibro {

    private String nombre;
    private int nacimiento;
    private int fallecimiento;

    public AutorLibro() {}

    public AutorLibro(String nombre, int nacimiento, int fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    @Override
    public String toString() {
        return nombre + " (" + nacimiento + "-" + fallecimiento + ")";
    }

    // Getters y Setters omitidos por brevedad

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(int fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }
}