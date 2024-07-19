package com.example.sakilademo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @Column(name = "actor_id")
    @Getter
    @Setter
    private short id;

    @Setter
    @Getter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Getter
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name="actor_id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id")}
    )private List<Film> films = new ArrayList<>();

}

