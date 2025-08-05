package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Coin;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchList(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
