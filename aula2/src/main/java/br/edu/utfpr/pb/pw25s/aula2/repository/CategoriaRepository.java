package br.edu.utfpr.pb.pw25s.aula2.repository;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByOrderById();

    List<Categoria> findByOrderByDescricao();

    // SELECT * FROM categoria WHERE descricao LIKE 'teste'
    List<Categoria> findByDescricaoContainingOrderByDescricao(String descricao);
}
