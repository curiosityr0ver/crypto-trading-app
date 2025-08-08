package com.trading.service;

import java.util.List;

import com.trading.entities.Asset;
import com.trading.entities.Coin;
import com.trading.entities.User;

public interface AssetService 
{
	Asset createAsset(User user,Coin coin, double quantity);
	
	Asset getAssetById(Long assetId) throws Exception;
	
	Asset getAssetByUserId(Long userId, Long assetId);

	List<Asset> getUserAssets(Long userId);
	
	Asset updateAsset(Long assetId, double quantity) throws Exception;
	
	Asset findAssetByUserIdAndCoinId(Long userId, String coinId);
	
	void deleteAsset(Long assetId);
	
}
