package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    private String getAll(Model model) {
        model.addAttribute("categorias", categoriaService.findAllByOrderById());
        return "categoria/list";
    }

    @GetMapping(value = {"new", "novo"})
    private String form(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @PostMapping
    private String save(@Valid Categoria categoria,
                        BindingResult result,
                        RedirectAttributes attributes,
                        Model model
                        ) {

        if (result.hasErrors()) {
            model.addAttribute("categoria", categoria);
            return "categoria/form";
        }

        categoriaService.save(categoria);
        attributes.addFlashAttribute("sucesso", "Registro salvo com sucesso!");
        return "redirect:/categoria";
    }

    @GetMapping("{id}") // localhost:8025/categoria/1
    private String form(@PathVariable("id") Long id, Model model) {

        model.addAttribute("categoria", categoriaService.findOne(id));

        return "categoria/form";
    }

    @DeleteMapping("{id}")
    private String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            categoriaService.delete(id);
            attributes.addFlashAttribute("sucesso", "Registro removido com sucesso!");
        } catch (Exception ex) {
            attributes.addFlashAttribute("erro", "Falha ao remover o registro.");
        }
        return "redirect:/categoria";
    }
}
