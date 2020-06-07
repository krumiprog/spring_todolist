package com.learning.todos.controllers;

import com.learning.todos.models.Todo;
import com.learning.todos.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/")
    public String getMainPage(Model model) {
        Iterable<Todo> todos = todoRepository.findAll();

        model.addAttribute("title", "Todo list");
        model.addAttribute("todos", todos);

        return "main";
    }
}
