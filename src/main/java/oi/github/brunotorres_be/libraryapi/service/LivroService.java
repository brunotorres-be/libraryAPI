package oi.github.brunotorres_be.libraryapi.service;

import lombok.RequiredArgsConstructor;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import oi.github.brunotorres_be.libraryapi.repository.LivroRepository;
import oi.github.brunotorres_be.libraryapi.repository.specs.LivroSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static oi.github.brunotorres_be.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;


    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);

    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    //isbn, titulo, nome autor, genero, ano de publicação
    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao
    ){

        //select * from livro where isbn = :isbn and nomeAutor =
//        Specification<Livro> specs = Specification.where(
//                LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        //select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(isbn != null){

            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }
        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return repository.findAll(specs);

    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para Atualizar, é necessario que o livro esteja salvo na base");
        }
        repository.save(livro);
    }
}
