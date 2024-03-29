package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных категория задачи
 */
@Entity
@Table(name = "todo_categories")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

    /**
     * Идентификатор категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название категории
     */
    @Column(name = "name")
    @NonNull
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
