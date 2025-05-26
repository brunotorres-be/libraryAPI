package oi.github.brunotorres_be.libraryapi.controller;

import jakarta.validation.Valid;
import oi.github.brunotorres_be.libraryapi.controller.dto.AutorDTO;

import oi.github.brunotorres_be.libraryapi.controller.mappers.AutorMapper;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.service.AutorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") //http://localhost:8080/autores

public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;


    public AutorController(AutorService service, AutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) { //o @valid vai verificar se todas as validacoes do AutorDTO estao certas para poder liberar.
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    // indempontente
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();


    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }

}
