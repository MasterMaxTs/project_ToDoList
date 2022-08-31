package ru.job4j.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.services.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class ItemController implements ManageSession {

    private final ItemService itemService;

    public ItemController(ItemService service) {
        this.itemService = service;
    }

    @ModelAttribute("user")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        User user = (User) model.getAttribute("user");
        model.addAttribute("items", itemService.findAll(user.getId()));
        return "item/index";
    }

    @GetMapping("/items/{id}")
    public String getDescription(@PathVariable("id") int id,
                                 Model model) {
        User user = (User) model.getAttribute("user");
        model.addAttribute("item", itemService.findById(id, user.getId()));
        return "item/item";
    }

    @GetMapping("/formSetDoneItem")
    public String setDone(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("user");
        Item item = itemService.findById(id, user.getId());
        item.setDone(true);
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/formGetDeleteItem")
    public String getDelete(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("user");
        itemService.delete(id, user.getId());
        return "redirect:/index";
    }

    @GetMapping("/formGetUpdateItem")
    public String getUpdate(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("user");
        model.addAttribute(
                "item", itemService.findById(id, user.getId())
        );
        return "item/update_item";
    }

    @GetMapping("/items/new")
    public String formCreateItem() {
        return "item/add_item";
    }

    @GetMapping("/completed_items")
    public String getCompletedItems(Model model) {
        User user = (User) model.getAttribute("user");
        model.addAttribute(
                "completed", itemService.findCompleted(user.getId())
        );
        return "item/completed_items";
    }

    @GetMapping("/new_items")
    public String getNewItems(Model model) {
        User user = (User) model.getAttribute("user");
        model.addAttribute(
                "newItems", itemService.findNew(user.getId())
        );
        return "item/new_items";
    }

    @PostMapping("/addItem")
    public String add(@ModelAttribute Item item, Model model) {
        User user = (User) model.getAttribute("user");
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        item.setUserId(user.getId());
        itemService.create(item);
        return "redirect:/index";
    }

    @PostMapping("updateItem")
    public String update(@ModelAttribute Item item, Model model) {
        User user = (User) model.getAttribute("user");
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        item.setUserId(user.getId());
        itemService.update(item);
        return "redirect:/index";
    }
}
