package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных приоритет
 */
@Entity
@Table(name = "todo_priorities")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
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
    @NonNull
    private String name;

    /**
     * Числовое обозначение приоритета
     */
    @Column(name = "position")
    @NonNull
    private int position;

    @Override
    public String toString() {
        return name;
    }
}
