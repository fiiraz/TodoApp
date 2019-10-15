package com.pirozgur.todoproject_new.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pirozgur.todoproject_new.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"list"})
@Table(name="todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_LIST_ID")
    @JsonIgnore
    private TodoList list;

    @OneToOne
    @JoinColumn(name = "OWNER_USER_ID")
    @JsonIgnore
    private User owner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long dependentId = 0L;

}
