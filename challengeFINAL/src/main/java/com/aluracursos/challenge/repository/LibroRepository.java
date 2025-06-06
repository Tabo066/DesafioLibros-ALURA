package com.aluracursos.challenge.repository;

import com.aluracursos.challenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
}