package br.edu.utfpr.pb.pw25s.aula7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("")
	public String index(Model model) {
		return "index";
	}

	@GetMapping(value = {"teste", "teste2"})
	public String teste() {
		return "login";
	}
	
}
