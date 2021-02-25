package br.edu.utfpr.pb.pw25s.aula2;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula2.repository.CategoriaRepository;
import br.edu.utfpr.pb.pw25s.aula2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aula2Application implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Aula2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.listarCategorias();

		Categoria c1 = new Categoria();
		c1.setDescricao("Nova categoria!!!");
		this.salvarCategoria(c1);

		Categoria c2 = this.categoriaRepository.findById(3).orElse(new Categoria());
		c2.setDescricao("Eletrônicos");
		this.salvarCategoria(c2);

		this.listarCategorias();

		this.listarCategoriasDescricaoContaining("ic");

		this.listarProdutos();
	}

	private void listarCategorias() {
		System.out.println("\n *************** Lista de Categorias ****************");
		this.categoriaRepository.findByOrderByDescricao().forEach(c -> System.out.println(c));
		System.out.println("\n *************** FIM Lista de Categorias **************** \n");
	}

	private void salvarCategoria(Categoria categoria) {
		System.out.println("\n *************** Salvando uma categoria ****************");
		this.categoriaRepository.save(categoria);
		System.out.println("Categoria salva com sucesso. - " + categoria.toString() );
	}

	private void removerCategoria(Integer id) {
		System.out.println("\n *************** Removendo uma categoria ****************");
		this.categoriaRepository.deleteById(id);
		System.out.println("******* Categoria removida com sucesso! ******************" );
	}

	private void listarCategoriasDescricaoContaining(String descricao) {
		System.out.println("\n *************** Lista de Categorias Containing ****************");
		this.categoriaRepository.findByDescricaoContainingOrderByDescricao(descricao).forEach(c -> System.out.println(c));
		System.out.println("\n *************** FIM Lista de Categorias Containing **************** \n");
	}

	private void listarProdutos() {
		System.out.println("\n *************** Lista de Produtos ****************");
		this.produtoRepository.findAll().forEach(p -> System.out.println(p));
		System.out.println("\n *************** FIM Lista de Produtos **************** \n");
	}
}
