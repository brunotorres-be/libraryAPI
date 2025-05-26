package oi.github.brunotorres_be.libraryapi.controller;

import oi.github.brunotorres_be.libraryapi.controller.dto.ErroResposta;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarHeaderLocation(UUID id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
