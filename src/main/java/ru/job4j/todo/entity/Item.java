package ru.job4j.todo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "todo_tasks")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar created;

    @Column(name = "done")
    private boolean done;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "todo_tasks_categories",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private final Set<Category> categories = new HashSet<>();

    public Item(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public void addCategoryToTask(Category category) {
        categories.add(category);
    }
}
