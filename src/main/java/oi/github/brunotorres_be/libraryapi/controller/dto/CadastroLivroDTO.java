package oi.github.brunotorres_be.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        String isbn,

        @NotBlank(message = "campo obrigatório")
        String titulo,

        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser data futura")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,

        @NotNull(message = "campo obrigatório")
        UUID idAutor
        ) {
}
