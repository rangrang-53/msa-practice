package com.example.loginproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @GetMapping("/")
    public String boardList(){
        return "board-list";
    }

    @GetMapping("/write")
    public String boardWrite(){
        return "board-write";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "board-detail";
    }

    @GetMapping("/update/{id}")
    public String boardUpdate(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "board-update";
    }
}
