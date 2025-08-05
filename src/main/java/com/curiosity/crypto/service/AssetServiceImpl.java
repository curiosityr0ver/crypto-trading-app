package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Asset;
import com.curiosity.crypto.model.Coin;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AssetServiceImpl implements AssetService {


    @Autowired
    private AssetsRepository assetsRepository;
    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        return null;
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return null;
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {
        return List.of();
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) {
        return null;
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return null;
    }

    @Override
    public void deleteAsset(Long assetId) {

    }
}
