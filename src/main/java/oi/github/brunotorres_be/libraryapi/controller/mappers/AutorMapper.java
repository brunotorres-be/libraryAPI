package oi.github.brunotorres_be.libraryapi.controller.mappers;

import oi.github.brunotorres_be.libraryapi.controller.dto.AutorDTO;
import oi.github.brunotorres_be.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //vai transformar em componente, podendo injetar
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
