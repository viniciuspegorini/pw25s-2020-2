package br.edu.utfpr.pb.pw25s.aula2.repository;

import br.edu.utfpr.pb.pw25s.aula2.model.Produto;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // SELECT * FROM produto WHERE nome like :nome
    List<Produto> findByNomeLike(String nome);

    // SELECT * FROM produto WHERE nome like :nome OR descricao like '%nome%' ORDER BY nome
    List<Produto> findByNomeContainingOrDescricaoContainingOrderByNome(String nome, String descricao);

    // SELECT * FROM produto WHERE valor >= valor ORDER BY valor
    List<Produto> findByValorGreaterThanEqualOrderByValor(Double valor);

    // SELECT * FROM Produto p WHERE p.valor >= valor ORDER BY p.valor
    @Query("SELECT p FROM Produto AS p WHERE p.valor >= :valor ORDER BY p.valor")
    List<Produto> findByAlgumaCoisa(@Param("valor") Double valor);

    // SELECT nome FROM Produto p WHERE p.valor >= valor ORDER BY p.valor
    @Query(value = "SELECT p.nome, p.valor, p.data_fabricacao FROM Produto AS p WHERE p.valor >= :valor ORDER BY p.valor", nativeQuery = true)
    List<Object[]> findByAlgumaCoisa2(@Param("valor") Double valor);


    List<Produto> findByDataFabricacaoBetween(LocalDate dataIni, LocalDate dataFim);

}
