package com.curiosity.crypto.service;

import com.curiosity.crypto.model.Asset;
import com.curiosity.crypto.model.Coin;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.AssetsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AssetServiceImpl implements AssetService {


    @Autowired
    private AssetsRepository assetsRepository;
    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetsRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return  assetsRepository
                .findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {
        return assetsRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());
        return assetsRepository.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
            assetsRepository.findByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetsRepository.deleteById(assetId);
    }
}
