package com.reviews.task.data.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews;

    public User() {}
    public User(Long id) {
        this.id = id;
    }
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}

