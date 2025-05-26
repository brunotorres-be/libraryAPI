package oi.github.brunotorres_be.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.github.brunotorres_be.libraryapi.controller.dto.CadastroLivroDTO;
import oi.github.brunotorres_be.libraryapi.controller.dto.ErroResposta;
import oi.github.brunotorres_be.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import oi.github.brunotorres_be.libraryapi.controller.mappers.LivroMapper;
import oi.github.brunotorres_be.libraryapi.exceptions.RegistroDuplicadoException;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import oi.github.brunotorres_be.libraryapi.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        //mapear dto para entidade (Map Struct)
        Livro livro = mapper.toEntity(dto);

        //enviar a entidade para o sercvice evalidar e salvar na base
        service.salvar(livro);

        //criar url para acesso dos dados do livro

        var url = gerarHeaderLocation(livro.getId());

        //retornar codigo create com header location
        return ResponseEntity.created(url).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return  ResponseEntity.ok(dto);
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return  ResponseEntity.noContent().build();
                }).orElseGet(() ->ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao
    ){
        var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);

                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());

    }
}

