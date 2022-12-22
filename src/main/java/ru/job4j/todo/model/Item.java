package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель данных задача
 */
@Entity
@Table(name = "todo_tasks")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    /**
     * Идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название задачи
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание задачи
     */
    @Column(name = "description")
    private String description;

    /**
     * Локальная дата создания задачи без учёта часового пояса
     */
    @Column(name = "created")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar created;

    /**
     * Статус завершённости задачи
     */
    @Column(name = "done")
    private boolean done;

    /**
     * Пользователь - создатель задачи
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Приоритет задачи
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "priority_id")
    private Priority priority;

    /**
     * Совокупность категорий задачи
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "todo_tasks_categories",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private final Set<Category> categories = new HashSet<>();

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * Используется в тестировании
     * @param name название
     * @param description описание
     * @param done выполнение
     */
    public Item(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.done = done;
    }

    /**
     * Добавляет категорию в список категорий, в котором числится задача
     * @param category категория
     */
    public void addCategoryToTask(Category category) {
        categories.add(category);
    }
}
