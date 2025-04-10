package oi.github.brunotorres_be.libraryapi.repository;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
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
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //JPQL -> referente as entidades e as propriedades (não ao nome da coluna no Banco de Dados, esquecer mapeamos da tabela)
    @Query(" select l from livros as L order by L.titulo")
    List<Livro>listarTodos();
}
