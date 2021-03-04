package br.edu.utfpr.pb.pw25s.aula3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping()
    private String index() {
        return "index";
    }

    @GetMapping("teste")
    private String teste() {
        return "index";
    }

    @GetMapping("home")
    private String home() {
        return "index";
    }

    @GetMapping("home2")
    private String home2() {
        return "home";
    }

}
