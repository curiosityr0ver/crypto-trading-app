package com.curiosity.crypto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double sellingPrice;

    private double buyingPrice;

//    fix later
    @ManyToOne
    private Coin coin;

    @ManyToOne
    private User user;
}