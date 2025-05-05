package oi.github.brunotorres_be.libraryapi.service;

import lombok.RequiredArgsConstructor;
import oi.github.brunotorres_be.libraryapi.exceptions.OperacaoNaoPermitidaException;
import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.repository.AutorRepository;
import oi.github.brunotorres_be.libraryapi.repository.LivroRepository;
import oi.github.brunotorres_be.libraryapi.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    // Esse contrutor foi substituido pela annotation @RequiredArgsConstructor, ao usar o final nos metodos que devem
    //injetados o springboot cria automaticamente os construtores.

    /*public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }*/

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
       if(autor.getId() == null){
           throw new IllegalArgumentException("Para Atualizar, é necessario que o autor esteja cadastrado na base");
       }
       validator.validar(autor);
       repository.save(autor);
    }



    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido exlcluir um Autor que pssui livros Cadastrados!");
        }
        repository.delete(autor);
    }

    //forma nao muito boa de implementar a pesquisa
    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
            }
            if (nome != null) {
                return repository.findByNome(nome);
            }
            if (nacionalidade != null) {
                return repository.findByNacionalidade(nacionalidade);
            }

            return repository.findAll();

        }

        public List<Autor> pesquisaByExample (String nome, String nacionalidade){
            var autor = new Autor();
            autor.setNome(nome);
            autor.setNacionalidade(nacionalidade);

            ExampleMatcher matcher = ExampleMatcher.
                    matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            Example<Autor> autorExample = Example.of(autor,matcher);

            return repository.findAll(autorExample);
        }

        public boolean possuiLivro (Autor autor) {
            return livroRepository.existsByAutor(autor);
        }



}