package com.challenge2.literalura.repository;

import com.challenge2.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface iLibroRepository extends JpaRepository<Libro,Long> {
    boolean existsByTitulo(String titulo);

    Libro findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);



    @Query("SELECT l FROM Libro l ORDER BY l.cantidadDescargas DESC LIMIT 10")
    List<Libro> findTop10ByTituloByCantidadDescargas();

//    @Query("SELECT e FROM Libro s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
//    List<Episodio> episodiosPorNombre(String nombreEpisodio);
//
//    @Query("SELECT e FROM Libro s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5 ")
//    List<Episodio> top5Episodios(Libro serie);
}
