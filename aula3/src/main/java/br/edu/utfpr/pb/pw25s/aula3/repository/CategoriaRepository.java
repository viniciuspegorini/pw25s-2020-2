package br.edu.utfpr.pb.pw25s.aula3.repository;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    List<Categoria> findAllByOrderById();
}
