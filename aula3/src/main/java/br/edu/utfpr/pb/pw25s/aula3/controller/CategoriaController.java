package br.edu.utfpr.pb.pw25s.aula3.controller;

import br.edu.utfpr.pb.pw25s.aula3.model.Categoria;
import br.edu.utfpr.pb.pw25s.aula3.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private String save(Categoria categoria, Model model) {

        categoriaService.save(categoria);

        return "redirect:/categoria";
    }

    @GetMapping("{id}") // localhost:8025/categoria/1
    private String form(@PathVariable("id") Long id, Model model) {

        model.addAttribute("categoria", categoriaService.findOne(id));

        return "categoria/form";
    }

    @DeleteMapping("{id}")
    private String delete(@PathVariable("id") Long id) {
        try {
            categoriaService.delete(id);
        } catch (Exception ex) {

        }
        return "redirect:/categoria";
    }
}
