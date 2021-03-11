package br.edu.utfpr.pb.pw25s.aula3.service;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;

import java.util.List;

public interface CategoriaService extends CrudService<Categoria, Long> {

    List<Categoria> findAllByOrderById();

}
