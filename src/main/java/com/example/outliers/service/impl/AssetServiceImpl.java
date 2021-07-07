package com.example.outliers.service.impl;

import com.example.outliers.controller.AssetController;
import com.example.outliers.model.Asset;
import com.example.outliers.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The concrete implementation of the asset service
 */
@Service
public class AssetServiceImpl implements AssetService {
    private static final Logger LOG = LoggerFactory.getLogger(AssetServiceImpl.class);
    /**
     * The repository DOA layer
     */
    private final AssetRepository repository;

    @Autowired
    private AssetServiceImpl(final AssetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Asset save(final Asset asset) {
        return repository.save(asset);
    }

    @Override
    public List<Asset> findAll() {
        return repository.findAll();
    }

    @Override
    public Asset update(Asset updatedAsset) {
        Asset asset = findById(updatedAsset.getId());
        LOG.info("Response from {}", AssetServiceImpl.class.getSimpleName());
        asset.setAge(updatedAsset.getAge());
        asset.setUptime(updatedAsset.getUptime());
        asset.setNumOfFailures(updatedAsset.getNumOfFailures());
        return repository.save(asset);
    }

    @Override
    public Asset findById(Long id) {
        Asset asset = repository.findById(id).orElse(null);
        return asset;
    }

    @Override
    public List<Long> findOutliersInAssetAge() {
        List<Asset> assets = findAll();
        Map<Double, Long> mapAssetIdValue = new HashMap<>();
        List<Double> actualPeriods = new ArrayList<>();
        List<Double> outlierAges = new ArrayList<>();
        List<Long> outlierAssetIds = new ArrayList<>();

        //Iterate Asset ages and normalise date to days
        for (Asset asset : assets) {
            if (asset != null) {
                LOG.debug("Response from {}", AssetServiceImpl.class.getSimpleName());
                if (asset.getAge() != null && asset.getAge() != "") {
                    String age = asset.getAge();
                    String[] ageArray = age.split(" ");
                    int number = Integer.parseInt(ageArray[0]);
                    String timeUnit = ageArray[1];
                    //normalise the ages into days
                    int unitsToDays = timeUnitByValue(timeUnit);
                    double actualPeriod = number * unitsToDays;

                    mapAssetIdValue.put(actualPeriod, asset.getId());
                    actualPeriods.add(actualPeriod);
                }
            }
        }
        //find the outliers
        if (actualPeriods != null && actualPeriods.size() > 0) {
            outlierAges = getOutliers(actualPeriods);
            for (double outlierAge : outlierAges) {
                outlierAssetIds.add(mapAssetIdValue.get(outlierAge));
            }
        }

        return outlierAssetIds;
    }

    public List<Double> getOutliers(List<Double> actualPeriods) {
        List<Double> output = new ArrayList<Double>();
        List<Double> data1 = new ArrayList<Double>();
        List<Double> data2 = new ArrayList<Double>();
        if (actualPeriods.size() % 2 == 0) {
            data1 = actualPeriods.subList(0, actualPeriods.size() / 2);
            data2 = actualPeriods.subList(actualPeriods.size() / 2, actualPeriods.size());
        } else {
            data1 = actualPeriods.subList(0, actualPeriods.size() / 2);
            data2 = actualPeriods.subList(actualPeriods.size() / 2 + 1, actualPeriods.size());
        }
        double q1 = getMedian(data1);
        double q3 = getMedian(data2);
        double iqr = q3 - q1;
        double lowerFence = q1 - 1.5 * iqr;
        double upperFence = q3 + 1.5 * iqr;
        //use foreach
        for (int i = 0; i < actualPeriods.size(); i++) {
            if (actualPeriods.get(i) < lowerFence || actualPeriods.get(i) > upperFence)
                output.add(actualPeriods.get(i));
        }
        return output;
    }

    private double getMedian(List<Double> data) {
        if (data.size() % 2 == 0)
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        else
            return data.get(data.size() / 2);
    }

    private int timeUnitByValue(String timeUnit) {
        int result = 0;
        if (timeUnit != null && timeUnit != "") {
            LOG.debug("Response from {}", AssetServiceImpl.class.getSimpleName());
            switch (timeUnit) {
                case "year":
                    result = 365;
                    break;
                case "month":
                    result = 31;
                    break;
                case "day":
                    result = 1;
                    break;
                default:
                    result = 0;
            }
        }

        return result;
    }


}
