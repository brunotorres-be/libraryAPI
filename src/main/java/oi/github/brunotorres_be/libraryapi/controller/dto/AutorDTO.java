package oi.github.brunotorres_be.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,

        @NotBlank (message = "Campo obrigatorio")//Usado em String, não permite que venha Nula ou Vazia (branca)
        @Size(min = 2, max = 100, message = "Campo fora do tamanha padrao")
        String nome,

        @NotNull (message = "Campo obrigatorio")
        @Past (message = "Não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank (message = "Campo obrigatorio")
        @Size(min = 2, max = 50, message = "Campo fora do tamanha padrao")
        String nacionalidade)
{


}
