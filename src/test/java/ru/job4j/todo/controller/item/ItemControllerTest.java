package ru.job4j.todo.controller.item;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.*;
import ru.job4j.todo.service.item.ItemService;
import ru.job4j.todo.service.item.category.CategoryService;
import ru.job4j.todo.service.item.priority.PriorityService;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Класс используется для выполнения модульных тестов
 * слоя контроллера задач пользователей
 */
public class ItemControllerTest {

    private ItemController controller;
    private ItemService itemService;
    private PriorityService priorityService;
    private CategoryService categoryService;
    private Model model;
    private User user;
    private List<Item> userItems;
    private List<Priority> priorities;
    private List<Category> categories;

    /**
     * Инициализация объекта контроллера задач пользователей,
     * инициализвация начальных данных до выполнения тестов
     */
    @Before
    public void whenSetUp() {
        itemService = mock(ItemService.class);
        priorityService = mock(PriorityService.class);
        categoryService = mock(CategoryService.class);
        controller = new ItemController(itemService, priorityService, categoryService);
        model = mock(Model.class);
        user = new User("userName", "userLogin", "userPassword");
        user.setId(1);
        user.setTimeZone(new TimeZone("Europe/Moscow", "+3"));
        priorities = List.of(new Priority("срочный", 1),
                             new Priority("обычный", 2));
        categories = List.of(new Category("Учёба"),
                             new Category("Домашние дела"),
                             new Category("Покупки"));
        Item firstItem = new Item("itemName1", "itemDesc1", false);
        firstItem.setId(1);
        firstItem.setCreated(Calendar.getInstance());
        firstItem.setPriority(priorities.get(0));
        firstItem.addCategoryToTask(categories.get(0));
        Item secondItem = new Item("itemName2", "itemDesc2", true);
        secondItem.setId(2);
        secondItem.setCreated(Calendar.getInstance());
        secondItem.setPriority(priorities.get(1));
        secondItem.addCategoryToTask(categories.get(1));
        secondItem.addCategoryToTask(categories.get(2));
        userItems = List.of(firstItem, secondItem);
    }

    /**
     * Тест проверяет сценарий корректного сопоставления вида
     * для начальной страницы сайта
     */
    @Test
    public void index() {
        String page = controller.index();
        assertThat(page, is("redirect:/items"));
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * в виде всех задач пользователя в объект модели
     * и корректного сопоставления вида
     */
    @Test
    public void whenFindAllUserItemsThenVerifyDataInModelAndReturnViewName() {
        doReturn(user).when(model).getAttribute("current");
        doReturn(userItems).when(itemService).findAll(user);
        String page = controller.all(model);
        assertThat(page, is("item/index"));
        verify(model).addAttribute("items", userItems);
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * в виде задачи пользователя в объект модели и
     * корректного сопоставления вида
     */
    @Test
    public void whenShowItemThenVerifyDataInModelAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        doReturn(user).when(model).getAttribute("current");
        doReturn(item).when(itemService).findById(id, user);
        String page = controller.getDescription(id, model);
        assertThat(page, is("item/item"));
        verify(model).addAttribute("item", item);
    }

    /**
     * Тест проверят сценарий отработки метода по переводу задачи
     * пользователя в статус решённой и корректного сопоставления вида
     */
    @Test
    public void whenItemIsSetDoneThenVerifyDataAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        doReturn(user).when(model).getAttribute("current");
        doReturn(item).when(itemService).findById(id, user);
        String page = controller.setDone(model, id);
        verify(itemService).update(item);
        assertThat(page, is("redirect:/index"));
    }

    /**
     * Тест проверят сценарий отработки метода по переводу задачи
     * пользователя в статус решённой, когда произошла ошибка в нахождении
     * задачи по id, и корректного сопоставления вида
     */
    @Test
    public void whenGetErrorPageForItemIsSetDoneDueToInvalidItemIdThenVerifyDataAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        Exception noSuchElementException =
                new NoSuchElementException("Item with id = " + id + "is not found!");
        doReturn(user).when(model).getAttribute("current");
        doThrow(noSuchElementException).when(itemService).findById(id, user);
        String page = controller.setDone(model, id);
        verify(model).addAttribute(
                "errorMessage", noSuchElementException.getMessage());
        assertThat(page, is("error/404"));
    }

    /**
     * Тест проверяет сценарий отработки метода по удалению задачи
     * и корректного сопоставления вида
     */
    @Test
    public void whenDeleteItemThenVerifyDataAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        doReturn(user).when(model).getAttribute("current");
        String page = controller.delete(id, model);
        verify(itemService).delete(id, user);
        assertThat(page, is("redirect:/index"));
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * и корректного сопоставления вида для формы обновления задачи
     */
    @Test
    public void whenGetFormForUpdateItemThenVerifyDataInModelAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        Priority itemPriority = item.getPriority();
        int position = itemPriority.getPosition();
        Set<Category> itemCategories = item.getCategories();
        doReturn(user).when(model).getAttribute("current");
        doReturn(item).when(itemService).findById(id, user);
        doReturn(itemPriority).when(priorityService).findByPosition(position);
        doReturn(priorities).when(priorityService).findAll();
        String page = controller.formUpdateItem(id, position, model);
        assertThat(page, is("item/update_item"));
        verify(model).addAttribute("item", item);
        verify(model).addAttribute("priority", itemPriority);
        verify(model).addAttribute("priorities", priorities);
        verify(model).addAttribute("itemCategories", itemCategories);
    }

    /**
     * Тест проверяет сценарий сопоставления вида для формы обновления задачи,
     * когда произошла ошибка в нахождении задачи по id
     */
    @Test
    public void whenGetErrorPageForUpdateItemDueToInvalidItemIdThenVerifyDataInModelAndReturnViewName() {
        Item item = userItems.get(0);
        int id = 3;
        int position = item.getPriority().getPosition();
        Exception noSuchElementException =
                new NoSuchElementException("Item with id = " + id + "is not found!");
        doReturn(user).when(model).getAttribute("current");
        doThrow(noSuchElementException).when(itemService).findById(id, user);
        String page = controller.formUpdateItem(id, position, model);
        assertThat(page, is("error/404"));
        verify(model).addAttribute(
                "errorMessage", noSuchElementException.getMessage());
    }

    /**
     * Тест проверяет сценарий сопоставления вида для формы обновления задачи,
     * когда произошла ошибка в нахождении приоритета задачи по его числовому
     * значению
     */
    @Test
    public void whenGetErrorPageForUpdateItemDueToInvalidPriorityPositionThenVerifyDataInModelAndReturnViewName() {
        Item item = userItems.get(0);
        int id = item.getId();
        int position = 4;
        Exception noSuchElementException =
                new NoSuchElementException(
                        "Priority with position = " + position + "is not found!");
        doReturn(user).when(model).getAttribute("current");
        doReturn(item).when(itemService).findById(id, user);
        doThrow(noSuchElementException).when(priorityService).findByPosition(position);
        String page = controller.formUpdateItem(id, position, model);
        assertThat(page, is("error/404"));
        verify(model).addAttribute(
                "errorMessage", noSuchElementException.getMessage());
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * и корректного сопоставления вида для формы создания новой задачи
     */
    @Test
    public void whenGetFormForCreateItemThenVerifyDataInModelAndReturnViewName() {
        doReturn(priorities).when(priorityService).findAll();
        doReturn(categories).when(categoryService).findAll();
        String page = controller.formCreateItem(model);
        assertThat(page, is("item/add_item"));
        verify(model).addAttribute("priorities", priorities);
        verify(model).addAttribute("categories", categories);
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * в виде всех выполненных задач пользователя в объект модели
     * и корректного сопоставления вида
     */
    @Test
    public void whenFindAllCompletedUserItemsThenVerifyDataInModelAndReturnViewName() {
        List<Item> completed = List.of(userItems.get(1));
        doReturn(user).when(model).getAttribute("current");
        doReturn(completed).when(itemService).findCompleted(user);
        String page = controller.allCompleted(model);
        assertThat(page, is("item/completed_items"));
        verify(itemService).findCompleted(user);
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * в виде всех новых (невыполненных) задач пользователя в объект модели
     * и корректного сопоставления вида
     */
    @Test
    public void whenFindAllNewUserItemsThenVerifyDataInModelAndReturnViewName() {
        List<Item> newItems = List.of(userItems.get(0));
        doReturn(user).when(model).getAttribute("current");
        doReturn(newItems).when(itemService).findNew(user);
        String page = controller.allNews(model);
        assertThat(page, is("item/new_items"));
        verify(itemService).findNew(user);
    }

    /**
     * Тест проверяет сценарий отработки метода по созданию новой задачи
     * и корректного сопоставления вида
     */
    @Test
    public void whenCreateItemWithSuccessThenVerifyDataAndReturnViewName() {
        Item newItem = new Item();
        int position = 1;
        Integer[] categoriesId = new Integer[] {2, 4};
        doReturn(user).when(model).getAttribute("current");
        doReturn(newItem).when(model).getAttribute("item");
        doReturn(priorities.get(0)).when(priorityService).findByPosition(position);
        doReturn(categories.get(1)).when(categoryService).findById(categoriesId[0]);
        doReturn(categories.get(2)).when(categoryService).findById(categoriesId[1]);
        String page = controller.add(newItem, position, categoriesId, model);
        assertThat(page, is("redirect:/index"));
        verify(itemService).create(newItem);
    }

    /**
     * Тест проверяет сценарий отработки метода по созданию новой задачи,
     * когда произошла ошибка, и корректного сопоставления вида
     */
    @Test
    public void whenCreateItemWithErrorThenVerifyDataAndReturnViewName() {
        Item newItem = new Item();
        int position = 4;
        Integer[] categoriesId = new Integer[] {2, 4};
        Exception noSuchElementException =
                new NoSuchElementException(
                        "Priority with position = " + position + " is not found!");
        doReturn(user).when(model).getAttribute("current");
        doReturn(newItem).when(model).getAttribute("item");
        doThrow(noSuchElementException).when(priorityService).findByPosition(position);
        String page = controller.add(newItem, position, categoriesId, model);
        assertThat(page, is("error/404"));
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * в виде задач всех пользователей в объект модели
     * и корректного сопоставления вида
     */
    @Test
    public void whenFindAllUsersItemsThenVerifyDataInModelAndReturnViewName() {
        doReturn(userItems).when(itemService).findAll();
        String page = controller.allByUsers(model);
        assertThat(page, is("/item/items_users"));
        verify(model).addAttribute("items", userItems);
    }
}