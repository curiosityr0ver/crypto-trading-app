package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entities.Coin;

public interface CoinRepository extends JpaRepository<Coin, String>
{

}
