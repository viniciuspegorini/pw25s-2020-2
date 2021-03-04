package br.edu.utfpr.pb.pw25s.aula3;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.model.Produto;
import br.edu.utfpr.pb.pw25s.aula3.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aula3Application {

	public static void main(String[] args) {
		SpringApplication.run(Aula3Application.class, args);
	}


}
