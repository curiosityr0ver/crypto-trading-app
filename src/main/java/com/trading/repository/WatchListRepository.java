package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entities.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long>
{

	WatchList findByUserId(Long userId);
}
