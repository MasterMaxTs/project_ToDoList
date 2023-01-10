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

    /**
     * Добавляет в модель все задачи пользователя, найденные в БД
     * @param model Model
     * @return вид item/index
     */
    @GetMapping("/items")
    public String all(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "items",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findAll(user), user));
        return "item/index";
    }

    /**
     * Добавляет в модель задачу пользователя, найденную в БД по id,
     * @param id id задачи
     * @param model Model
     * @return вид item/item
     */
    @GetMapping("/items/{id}/edit")
    public String getDescription(@PathVariable("id") int id,
                                 Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute("item", itemService.findById(id, user));
        return "item/item";
    }

    /**
     * Помечает задачу, найденную в БД по id, как выполненную
     * @param model Model
     * @param id id задачи
     * @return перенаправление на страницу со всеми задачами пользователя
     * /index, если задача успешно найдена и обновлена в БД, иначе возращает
     * вид error/404 с отображением ошибки пользователю
     */
    @GetMapping("/formSetDoneItem")
    public String setDone(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        try {
            Item item = itemService.findById(id, user);
            item.setDone(true);
            itemService.update(item);
        } catch (NoSuchElementException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error/404";
        }
        return "redirect:/index";
    }

    /**
     * Удаляет задачу пользователя
     * @param model Model
     * @param id id задачи
     * @return перенаправление на страницу со всеми задачами пользователя
     * /index
     */
    @GetMapping("/formGetDeleteItem")
    public String delete(Model model, @RequestParam("id") int id) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    /**
     * Предоставляет пользователю форму для обновления данных в задаче.
     * Для отображения данных в форме:
     * добавляет в модель текущую задачу,
     * добавляет в модель текущий приоритет задачи,
     * добавляет в модель список возможных приоритетов для задачи, хранимых в БД,
     * добавляет в модель список возможных категорий для задачи, хранимых в БД,
     * добавляет в модель список текущих категорий задачи
     * @param id id задачи
     * @param position числовое обозначение приоритета задачи
     * @param model Model
     * @return вид item/update_item, если задача и текущий приоритет задачи
     * найдены в БД, иначе возращает вид error/404 с отображением ошибки
     * пользователю
     */
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

    /**
     * Предоставляет пользователю форму для создания новой задачи.
     * Для отображения данных в форме:
     * добавляет в модель список возможных приоритетов для задачи, хранимых в БД,
     * добавляет в модель список возможных категорий для задачи, хранимых в БД,
     * @param model Model
     * @return вид item/add_item
     */
    @GetMapping("/items/new")
    public String formCreateItem(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "item/add_item";
    }

    /**
     * Добавляет в модель все выполненные задачи пользователя, найденные в БД
     * @param model Model
     * @return вид item/completed_items
     */
    @GetMapping("/completed_items")
    public String allCompleted(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "completed",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findCompleted(user), user)
        );
        return "item/completed_items";
    }

    /**
     * Добавляет в модель все незавершённые задачи пользователя, найденные в БД
     * @param model Model
     * @return вид item/new_items
     */
    @GetMapping("/new_items")
    public String allNews(Model model) {
        User user = (User) model.getAttribute("current");
        model.addAttribute(
                "newItems",
                Objects.requireNonNull(user).getId() == 0 ? List.of()
                        : itemsForShowToUser(itemService.findNew(user), user)
        );
        return "item/new_items";
    }

    /**
     * Добавляет либо обновляет задачу пользователя в БД сайта
     * @param item задача
     * @param position числовое значение приоритета задачи
     * @param categories целочисленный массив из id категорий задачи
     * @param model Model
     * @return перенаправление на страницу со всеми задачами пользователя
     * /index, если задача сохранилась в БД без ошибок,
     * иначе возвращает вид error/404 с отображением ошибки пользователю
     */
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

    /**
     * Удаляет задачу пользователя
     * @param id id задачи
     * @param model Model
     * @return перенаправление на страницу со всеми задачами пользователя
     * /index
     */
    @GetMapping("/items/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        User user = (User) model.getAttribute("current");
        itemService.delete(id, user);
        return "redirect:/index";
    }

    /**
     * Добавляет в модель все задачи пользователей, найденные в БД
     * (опция администратора сайта)
     * @param model Model
     * @return вид /item/items_users
     */
    @GetMapping("/itemsOfUsers")
    public String allByUsers(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "/item/items_users";
    }

    /**
     * Устанавливает значения полям задачи пользователя
     * @param item задача
     * @param position числовое значение приориета
     * @param categories целочисленный массив из id категорий задачи
     * @param user пользователь
     */
    private void setSomeTaskFields(Item item, int position,
                                   Integer[] categories, User user) {
        Priority priority = priorityService.findByPosition(position);
        item.setPriority(priority);
        item.setCreated(Calendar.getInstance());
        item.setUser(user);
        addCategoriesToTask(item, categories);
    }

    /**
     * Формирует список категорий задачи
     * @param item задача
     * @param categories целочисленный массив из id категорий задачи
     */
    private void addCategoriesToTask(Item item, Integer[] categories) {
        Arrays.stream(categories)
                .map(categoryService::findById)
                .forEach(item::addCategoryToTask);
    }

    /**
     * Возвращает список задач с временем создания с учётом часового
     * пояса для отображения пользователю
     * @param items список задач пользователя
     * @param user пользователь
     * @return список задач для отображения пользователю
     */
    private List<Item> itemsForShowToUser(List<Item> items, User user) {
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
