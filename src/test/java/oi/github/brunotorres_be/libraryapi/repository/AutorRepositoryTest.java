package oi.github.brunotorres_be.libraryapi.repository;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salverTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,3,31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("b3610c23-bebc-467d-ad06-699a60db9191");

        Optional<Autor> possivelAutor = repository.findById(id);
        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));

            repository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTeste(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletarPorIdTeste(){
        var id = UUID.fromString("b3610c23-bebc-467d-ad06-699a60db9191");
        repository.deleteById(id);

    }

    @Test
    public void deletarTeste() {
        var id = UUID.fromString("ca89e09c-ada0-44a9-ba9c-c9ba2871cbe6");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTeste(){
        Autor autor = new Autor();
        autor.setNome("Joff");
        autor.setNacionalidade("EUA");
        autor.setDataNascimento(LocalDate.of(1971,10,15));

        Livro livro = new Livro();
        livro.setIsbn("22887-84874");
        livro.setPreco(BigDecimal.valueOf(223));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Assombrado");
        livro.setDataPublicacao(LocalDate.of(1992,9,30));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("200007-84874");
        livro2.setPreco(BigDecimal.valueOf(174));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Meteoro de Pegasus");
        livro2.setDataPublicacao(LocalDate.of(1998,8,30));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

       // livroRepository.saveAll(autor.getLivros()); //pode ser retirado pois esta com cascade
    }

    @Test
    @Transactional
    void listarLivrosAutor(){
        var id = UUID.fromString("e6a628d6-9cb4-4262-826d-f841ec191131");
        var autor = repository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
