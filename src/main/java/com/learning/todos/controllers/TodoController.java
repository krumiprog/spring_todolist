package com.learning.todos.controllers;

import com.learning.todos.models.Todo;
import com.learning.todos.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/add")
    public String getAddForm(Model model) {
        model.addAttribute("title", "Add todo");
        return "add";
    }

    @PostMapping("/add")
    public String createItem(@RequestParam String title, @RequestParam String text) {
        Todo todo = new Todo(title, text);
        todoRepository.save(todo);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String watchItem(@PathVariable Long id, Model model) {
        Optional<Todo> result = todoRepository.findById(id);

        if (result.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("title", "Detail todo");
        model.addAttribute("todo", result.get());

        return "detail";
    }

    @GetMapping("/{id}/edit")
    public String selectItem(@PathVariable Long id, Model model) {
        Optional<Todo> result = todoRepository.findById(id);

        if (result.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("title", "Edit todo");
        model.addAttribute("todo", result.get());

        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateItem(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String text
    ) {
        Optional<Todo> result = todoRepository.findById(id);

        if (result.isPresent()) {
            Todo todo = result.get();
            todo.setTitle(title);
            todo.setText(text);
            todoRepository.save(todo);
        }

        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        Optional<Todo> result = todoRepository.findById(id);

        result.ifPresent(todoRepository::delete);

        return "redirect:/";
    }
}
