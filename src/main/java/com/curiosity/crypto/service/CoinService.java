package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Coin;

import java.util.List;

public interface CoinService {

    List<Coin> getCoins(int page) throws Exception;

    String getMarketChart(String coidId, int days) throws Exception;

    String getCoinDetails(String coidId) throws Exception;

    Coin findById(String coidId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTrendingCoins() throws Exception;
}
