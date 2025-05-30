package oi.github.brunotorres_be.libraryapi.controller.mappers;


import oi.github.brunotorres_be.libraryapi.controller.dto.CadastroLivroDTO;
import oi.github.brunotorres_be.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import oi.github.brunotorres_be.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null)) ")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
