package ru.job4j.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.services.ItemService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        model.addAttribute("items", service.findAll());
        return "item/index";
    }

    @GetMapping("/items/{id}")
    public String getDescription(Model model, @PathVariable("id") int id) {
        model.addAttribute("item", service.findById(id));
        return "item/item";
    }

    @GetMapping("/formSetDoneItem")
    public String setDone(@RequestParam("id") int id) {
        Item item = service.findById(id);
        item.setDone(true);
        service.update(id, item);
        return "redirect:/index";
    }

    @GetMapping("/formGetDeleteItem")
    public String getDelete(@RequestParam("id") int id) {
        service.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/formGetUpdateItem")
    public String getUpdate(@RequestParam("id") int id,
                            Model model) {
        model.addAttribute("item", service.findById(id));
        return "item/update_item";
    }

    @GetMapping("/items/new")
    public String createItem() {
        return "item/add_item";
    }

    @GetMapping("/completed_items")
    public String getCompletedItems(Model model) {
        model.addAttribute("completed", service.findCompleted());
        return "item/completed_items";
    }

    @GetMapping("/new_items")
    public String getNewItems(Model model) {
        model.addAttribute("newItems", service.findNew());
        return "item/new_items";
    }

    @PostMapping("/addItem")
    public String add(@ModelAttribute Item item) {
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        service.create(item);
        return "redirect:/index";
    }

    @PostMapping("updateItem")
    public String update(@RequestParam int id, @ModelAttribute Item item) {
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        service.update(id, item);
        return "redirect:/index";
    }
}
