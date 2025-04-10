package oi.github.brunotorres_be.libraryapi.repository;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    //Salvar sem ser em cascata
    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,11,30));

        Autor autor = autorRepository.findById(UUID.fromString("e6a628d6-9cb4-4262-826d-f841ec191131")).orElse(null);


        livro.setAutor(autor);

        repository.save(livro);
    }

    //Sem Cascade mas salva o autor e o livro manualmente
    @Test
    void salvarAutorELibroTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,11,30));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1971,8,11));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,11,30));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1971,8,11));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("98b52f9f-b042-4d9e-b491-f26652a9b257");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("e6a628d6-9cb4-4262-826d-f841ec191131");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("98b52f9f-b042-4d9e-b491-f26652a9b257");
       repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("1f89b8e0-e1b4-4021-9a4d-1de2d10a72e4");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("c1539a0c-b480-4ad4-8d62-76f8ff0b3d05");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor :");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTeste(){
        List<Livro> lista = repository.findByTitulo("Assombrado");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTeste(){
        List<Livro> lista = repository.findByIsbn("200007-84874");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTeste(){
        var preco = BigDecimal.valueOf(100,00);
        String tituloPesquisa = "UFO";
        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa,preco);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEIsbnTeste(){
        String isbnPesquisa= "98887-84874";
        String tituloPesquisa = "UFO";
        List<Livro> lista = repository.findByTituloOrIsbn(tituloPesquisa,isbnPesquisa);
        lista.forEach(System.out::println);
    }
}