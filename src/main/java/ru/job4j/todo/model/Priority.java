package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных приоритет
 */
@Entity
@Table(name = "todo_priorities")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Priority {

    /**
     * Идентификатор приоритета
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название приоритета
     */
    @Column(name = "name")
    private String name;

    /**
     * Числовое обозначение приоритета
     */
    @Column(name = "position")
    private int position;

    @Override
    public String toString() {
        return name;
    }
}
