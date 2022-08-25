package ru.job4j.todo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

//    public static final DateTimeFormatter FORMATTER =
//            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;
    private String description;
    private Timestamp created;
    private boolean done;

    public Item(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.done = done;
    }

//    @Override
//    public String toString() {
//        return String.format("Item [name = %s, created = %s, done = %b]",
//                name, FORMATTER.format(created), done);
//    }
}
