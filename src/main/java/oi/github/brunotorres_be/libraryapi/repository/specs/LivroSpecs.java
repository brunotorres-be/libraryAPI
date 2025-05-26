package oi.github.brunotorres_be.libraryapi.repository.specs;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"),isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")),
                        "%" + titulo.toUpperCase() + "%"); //o % titulo % vai procurar em qualquer lugar(começo, ou meio ou fiz ou any) se tem ou nao esse titulo
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"),genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPuclicacao){
       // and to_char(datapublicacao, 'YYYY')
        return (root, query, cb) ->cb.equal(
                cb.function("to_char",
                        String.class,
                        root.get("dataPublicacao"),
                        cb.literal("YYYY")),
                anoPuclicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {
//            return cb.like(cb.upper(root.get("autor").get("nome")),"%" + nome.toUpperCase() + "%");
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")),"%" + nome.toUpperCase() + "%");

        };



    }

}
