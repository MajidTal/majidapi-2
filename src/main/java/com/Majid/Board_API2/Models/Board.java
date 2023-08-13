package com.Majid.Board_API2.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    private String title;



    @ElementCollection
    @CollectionTable(name = "board_columns", joinColumns = @JoinColumn(name = "board_id"))
    @MapKeyColumn(name = "column_number")
    @Column(name = "column_name")
    private Map<Integer, String> columns = new HashMap<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();
}