package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.service.itemservice.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@AllArgsConstructor
public class ItemController implements ManageSession {

    private final ItemService itemService;

    @ModelAttribute("current")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "items",
                user.getId() == 0 ? List.of() : itemService.findAll(user)
        );
        return "item/index";
    }

    @GetMapping("/items/{id}/edit")
    public String getDescription(@PathVariable("id") int id,
                                 Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute("item", itemService.findById(id, user));
        return "item/item";
    }

    @GetMapping("/formSetDoneItem")
    public String setDone(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        Item item = itemService.findById(id, user);
        item.setDone(true);
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/formGetDeleteItem")
    public String getDelete(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    @GetMapping("/formGetUpdateItem")
    public String getUpdate(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "item", itemService.findById(id, user)
        );
        return "item/update_item";
    }

    @GetMapping("/items/new")
    public String formCreateItem() {
        return "item/add_item";
    }

    @GetMapping("/completed_items")
    public String getCompletedItems(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "completed",
                user.getId() == 0 ? List.of() : itemService.findCompleted(user)
        );
        return "item/completed_items";
    }

    @GetMapping("/new_items")
    public String getNewItems(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "newItems",
                user.getId() == 0 ? List.of() : itemService.findNew(user)
        );
        return "item/new_items";
    }

    @PostMapping("/addItem")
    public String add(@ModelAttribute Item item, Model model) {
        User user = (User) model.getAttribute("current");
        item.setCreated(
                Timestamp.valueOf(LocalDateTime.now().withNano(0))
        );
        item.setUser(user);
        itemService.create(item);
        return "redirect:/index";
    }

    @PostMapping("updateItem")
    public String update(@ModelAttribute Item item, Model model) {
        User user = (User) model.getAttribute("current");
        item.setCreated(
                Timestamp.valueOf(LocalDateTime.now().withNano(0))
        );
        item.setUser(user);
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/items/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    @GetMapping("/itemsOfUsers")
    public String getItemsByUsers(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "/item/items_users";
    }
}
