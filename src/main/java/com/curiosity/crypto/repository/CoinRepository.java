package com.curiosity.crypto.repository;

import com.curiosity.crypto.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
