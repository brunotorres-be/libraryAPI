package oi.github.brunotorres_be.libraryapi.repository;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    //select * from livro where Isbn = isbn
    List<Livro> findByIsbn(String isbn);

    //select * from livro where titulo = ? and preco = ?
    //Sempre quando for colocar dessa forma, o primeiro parametro deve ser igual ao colocado, nesse caso
    //Titulo - string titulo
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //JPQL -> referente as entidades e as propriedades (não ao nome da coluna no Banco de Dados, esquecer mapeamos da tabela)
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro>listarTodosOrdenadoPorTituloAndPreco();

    //
    @Query( " select a from Livro l join l.autor a")
    List<Autor> listarAutorDosLivros();

    @Query("select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero""")
    List<String> listarGenerosAutoresBrasileiros();


    //dessa forma que é parametrizado usando o Query
    // named parameters -> parametros nomeados
    @Query ("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    //Positional Parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);

    //Para deletar nao pode esquecer das Annotations
    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    //Para Fazer um Update nessa caso da data

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

}
