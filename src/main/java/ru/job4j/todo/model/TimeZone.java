package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных часовой пояс
 */
@Entity
@Table(name = "todo_time_zones")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class TimeZone {

    /**
     * Идентификатор часового пояса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     *Наименование часового пояса
     */
    @Column(name = "name")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String timeZoneDbName;

    /**
     * Временное смещение от UTC
     */
    @Column(name = "utc_offset")
    private String utcOffset;
}
