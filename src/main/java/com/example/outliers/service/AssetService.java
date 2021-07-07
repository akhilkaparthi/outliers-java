package com.example.outliers.service;


import com.example.outliers.model.Asset;

import java.util.List;

/**
 * The asset service
 */
public interface AssetService {
    /**
     * Saves a asset to the database. If the asset does not exist is inserted and it's id is populated.
     *
     * @param asset the asset to save
     * @return the saved asset
     */
    Asset save(final Asset asset);

    /**
     * Obtains a list of asset all assets.
     *
     * @return Obtains a list of all assets
     */
    List<Asset> findAll();

    /**
     * Updates a asset to the database. If the asset does not exist is inserted and it's id is populated.
     *
     * @param updateAsset the asset to save
     * @return the saved asset
     */
    Asset update(final Asset updateAsset);

    /**
     * Obtains a list of asset with a  match to the supplied
     *
     * @param id the wildcard id of assets to find
     * @return Obtains a list of matching assets
     */
    Asset findById(Long id);

    /**
     * Obtains a list of asset id which fall under outliers
     *
     * @return Obtains a list of all assets
     */
    List<Long> findOutliersInAssetAge();

}
