package com.trading.service;

import com.trading.entities.Coin;
import com.trading.entities.User;
import com.trading.entities.WatchList;

public interface WatchListService
{
	WatchList findUserWatchList(Long userId) throws Exception;
	
	WatchList createWatchList(User user);
	
	WatchList findById(Long id) throws Exception;
	
	Coin addItemToWatchList(Coin coin, User user) throws Exception;

}
