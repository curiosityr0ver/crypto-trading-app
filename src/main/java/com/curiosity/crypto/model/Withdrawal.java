package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.WITHDRAWAL_STATUS;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private WITHDRAWAL_STATUS status;

    private Long amount;

    @ManyToOne
    private User user;

    private LocalDateTime date;
}