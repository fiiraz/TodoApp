package com.pirozgur.todoproject_new.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pirozgur.todoproject_new.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "list",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TodoItem> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "OWNER_USER_ID")
    @JsonIgnore
    private User owner;

    public TodoList(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

}
