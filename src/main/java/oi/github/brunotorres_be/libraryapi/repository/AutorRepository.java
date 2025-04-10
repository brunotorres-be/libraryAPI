package oi.github.brunotorres_be.libraryapi.repository;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
// o JPA repository tem que ter o tipo (no caso objeto) e o tipo do ID que no caso é UUID
public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
