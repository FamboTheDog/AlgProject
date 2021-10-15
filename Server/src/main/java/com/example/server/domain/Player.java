package com.example.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    private String username;
//    private int x;
//    private int y;
}
