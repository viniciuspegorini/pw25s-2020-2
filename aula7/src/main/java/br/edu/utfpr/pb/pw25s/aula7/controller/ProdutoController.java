package br.edu.utfpr.pb.pw25s.aula7.controller;

import br.edu.utfpr.pb.pw25s.aula7.model.ImagemProduto;
import br.edu.utfpr.pb.pw25s.aula7.model.Produto;
import br.edu.utfpr.pb.pw25s.aula7.service.CategoriaService;
import br.edu.utfpr.pb.pw25s.aula7.service.MarcaService;
import br.edu.utfpr.pb.pw25s.aula7.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping("produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private MarcaService marcaService;

	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("produtos", produtoService.findAll());
		return "produto/list";
	}
	
	@GetMapping(value = {"new", "novo", "form"})
	public String form(Model model) {
		model.addAttribute("produto", new Produto());
		carregarCombos(model);
		return "produto/form";
	}

	private void carregarCombos(Model model) {
		model.addAttribute("categorias", categoriaService.findAll());
		model.addAttribute("marcas", marcaService.findAll());
	}

	@GetMapping("{id}") // /produto/25
 	public String form(@PathVariable Long id, Model model) {
		model.addAttribute("produto", produtoService.findOne(id));
		carregarCombos(model);
		return "produto/form";
	}
	
	@DeleteMapping("{id}") // /produto/25
	public String delete(@PathVariable Long id,
						 RedirectAttributes attributes) {
		try {
			produtoService.delete(id);
			attributes.addFlashAttribute("sucesso", 
					"Registro removido com sucesso!");
		} catch (Exception e) {
			attributes.addFlashAttribute("erro", 
					"Falha ao remover o registro!");
		}
		return "redirect:/produto";
	}


	@PostMapping
	public String save(@Valid Produto entity,
					  BindingResult result,
					  Model model,
					  @RequestParam("anexo") MultipartFile anexo,
					  @RequestParam("anexos") MultipartFile[] anexos,
					  HttpServletRequest request,
					  RedirectAttributes attributes) {

		if ( result.hasErrors() ) {
			model.addAttribute("produto", entity);
			return "produto/form";
		}

		produtoService.save(entity);
		if (anexo != null && !anexo.getOriginalFilename().isEmpty()) {
			saveFile(entity, anexo, request);
			saveBase64(entity, anexo, request);
		}

		if (anexos != null && anexos.length > 0 && !anexos[0].getOriginalFilename().isEmpty()) {
			saveFiles(entity, anexos, request);
		}
		produtoService.save(entity);
		attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");
		return "redirect:/produto";
	}

	// Método que salva apenas um arquivo no disco.
	private void saveFile(Produto entity, MultipartFile anexo, HttpServletRequest request) {
		File dir = new File(request.getServletContext().getRealPath("/images/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String caminhoAnexo = request.getServletContext().getRealPath("/images/");
		String extensao = anexo.getOriginalFilename().substring(anexo.getOriginalFilename().lastIndexOf("."));

		String nomeArquivo = entity.getId() + extensao;
		try {
			FileOutputStream fileOut = new FileOutputStream(new File(caminhoAnexo + nomeArquivo));
			BufferedOutputStream stream = new BufferedOutputStream(fileOut);
			stream.write(anexo.getBytes());
			stream.close();
			entity.setImagem(nomeArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método que salva vários arquivos no disco.
	private void saveFiles(Produto entity, MultipartFile[] anexos, HttpServletRequest request) {
		File dir = new File(request.getServletContext()
				.getRealPath("/images/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String caminhoAnexo = request.getServletContext().getRealPath("/images/");

		List<ImagemProduto> imagemProdutos = new ArrayList<>();
		ImagemProduto imagemProduto;
		int i = 0; //vai ser o identificador do anexo salvo
		for (MultipartFile anexo : anexos) {
			i++;
			String extensao = anexo.getOriginalFilename().substring(anexo.getOriginalFilename().lastIndexOf("."));
			String nomeArquivo = entity.getId() + "_" + i + extensao;
			try {
				FileOutputStream fileOut = new FileOutputStream(new File(caminhoAnexo + nomeArquivo));
				BufferedOutputStream stream = new BufferedOutputStream(fileOut);
				stream.write(anexo.getBytes());
				stream.close();
				imagemProduto = new ImagemProduto();
				imagemProduto.setProduto(entity);
				imagemProduto.setNome(nomeArquivo);
				imagemProdutos.add(imagemProduto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		entity.setImagemProdutos(imagemProdutos);
	}

	public void saveBase64(Produto entity, MultipartFile anexo, HttpServletRequest request) {
		try {
			String extensao = anexo.getOriginalFilename().substring(anexo.getOriginalFilename().lastIndexOf("."));

			entity.setImagemB64(Base64.getEncoder().encode(anexo.getBytes()));
			entity.setExtensaoImagemb64("image/" + extensao);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}