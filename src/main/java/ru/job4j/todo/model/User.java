package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/**
 * Модель данных пользователь
 */
@Entity
@Table(name = "todo_users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private int id;

    /**
     * Имя пользователя
     */
    @Column(name = "name")
    private String name;

    /**
     * Логин пользователя
     */
    @Column(name = "login")
    private String login;

    /**
     * Пароль пользователя
     */
    @Column(name = "password")
    private String password;

    /**
     * Список задач пользователя
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Item> items;

    /**
     * Часовой пояс пользователя
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param name имя
     * @param login логин
     * @param password пароль
     */
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
