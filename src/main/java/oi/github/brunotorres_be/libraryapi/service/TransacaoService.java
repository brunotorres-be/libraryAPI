package oi.github.brunotorres_be.libraryapi.service;

import oi.github.brunotorres_be.libraryapi.model.Autor;
import oi.github.brunotorres_be.libraryapi.model.GeneroLivro;
import oi.github.brunotorres_be.libraryapi.model.Livro;
import oi.github.brunotorres_be.libraryapi.repository.AutorRepository;
import oi.github.brunotorres_be.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void SalvarLivroComFoto(){
        //salvar o livro
        // repository,save(Livro)

        //pegar o id do livro = livro.getId();
        //var id = livro.getId();

        //salvar foto do livro -> bucket na nuvem
        //bucketService.salvar(livro.getFoto(), id+ "png);

        //atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id+ ".png)
        //repository.save(livro);
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("23faa8d4-05c5-46e1-abb8-80745c241f4b")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2025,1,11));
        //livroRepository.save(livro); nao precisa dessa etapa porque ja é uma transação que esta em estado maneged e com isso qualquer alteração ja vai ser salva
    }

    @Transactional
    public void executar(){

        //Salvar o autor
        Autor autor = new Autor();
        autor.setNome("Vera");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1971,8,11));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("98857-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Livro da Vera");
        livro.setDataPublicacao(LocalDate.of(1980,11,30));

        livro.setAutor(autor);

        livroRepository.save(livro);


        if(autor.getNome().equals("José")){
            throw new RuntimeException("Rollback");
        }

    }
}
