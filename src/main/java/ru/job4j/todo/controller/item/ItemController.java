package ru.job4j.todo.controller.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.controller.SessionController;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.item.ItemService;
import ru.job4j.todo.service.item.category.CategoryService;
import ru.job4j.todo.service.item.priority.PriorityService;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Контроллер задач
 */
@Controller
@AllArgsConstructor
public class ItemController extends SessionController {

    /**
     * Ссылки на слои сервисов
     */
    private final ItemService itemService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    /**
     * Добавляет пользователя во все модели,
     * определённые в контроллере задач
     * @see ru.job4j.todo.controller.SessionController
     * @param session HttpSession
     * @return пользователя из текущей сессии
     */
    @ModelAttribute("current")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    /**
     * Перенаправляет на страницу со всеми задачами пользователя
     * @return перенаправление на страницу со всеми задачами
     * /items
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String all(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "items",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findAll(user), model));
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
    public String delete(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    @GetMapping("/formGetUpdateItem")
    public String formUpdateItem(@RequestParam("id") int id,
                                 @RequestParam("priority.position") int position,
                                 Model model) {
        User user = (User) model.getAttribute("current");
        try {
            Item item = itemService.findById(id, user);
            Priority priority = priorityService.findByPosition(position);
            model.addAttribute("item", item);
            model.addAttribute("priority", priority);
            model.addAttribute("priorities", priorityService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("itemCategories", item.getCategories());
        } catch (NoSuchElementException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error/404";
        }
        return "item/update_item";
    }

    @GetMapping("/items/new")
    public String formCreateItem(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "item/add_item";
    }

    @GetMapping("/completed_items")
    public String allCompleted(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "completed",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findCompleted(user), model)
        );
        return "item/completed_items";
    }

    @GetMapping("/new_items")
    public String allNews(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "newItems",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findNew(user), model)
        );
        return "item/new_items";
    }

    @PostMapping("/addItem")
    public String add(@ModelAttribute Item item,
                      @RequestParam("priority.position") int position,
                      @RequestParam("categories") Integer[] categories,
                      Model model) {
        User user = (User) model.getAttribute("current");
        try {
            setSomeTaskFields(item, position, categories, user);
            itemService.create(item);
        } catch (NoSuchElementException ex) {
            model.addAttribute("errorMessage", ex);
            return "error/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/items/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    @GetMapping("/itemsOfUsers")
    public String allByUsers(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "/item/items_users";
    }

    private void setSomeTaskFields(Item item, int position,
                                   Integer[] categories, User user) {
        Priority priority = priorityService.findByPosition(position);
        if (priority != null) {
            item.setPriority(priority);
        }
        item.setCreated(Calendar.getInstance());
        item.setUser(user);
        addCategoriesToTask(item, categories);
    }

    private void addCategoriesToTask(Item item, Integer[] categories) {
        Arrays.stream(categories)
                .map(categoryService::findById)
                .forEach(item::addCategoryToTask);
    }

    private List<Item> itemsForShowToUser(List<Item> items, Model model) {
        User user = (User) model.getAttribute("current");
        return items.stream()
                .peek(i -> i.getCreated().setTimeZone(
                                TimeZone.getTimeZone(
                                        Objects.requireNonNull(user)
                                                .getTimeZone()
                                                .getTimeZoneDbName()
                                ))
                ).collect(Collectors.toList());
    }
}
