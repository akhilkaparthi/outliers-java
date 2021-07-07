package com.example.outliers.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.outliers.model.Asset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AssetServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class AssetServiceImplTest {
    @MockBean
    private AssetRepository assetRepository;

    @Autowired
    private AssetServiceImpl assetServiceImpl;

    @Test
    public void testSave() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("1 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(10);
        when(this.assetRepository.save((Asset) any())).thenReturn(asset);
        assertSame(asset, this.assetServiceImpl.save(new Asset()));
        verify(this.assetRepository).save((Asset) any());
        assertTrue(this.assetServiceImpl.findAll().isEmpty());
    }

    @Test
    public void testFindAll() {
        ArrayList<Asset> assetList = new ArrayList<Asset>();
        when(this.assetRepository.findAll()).thenReturn(assetList);
        List<Asset> actualFindAllResult = this.assetServiceImpl.findAll();
        assertSame(assetList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.assetRepository).findAll();
    }

    @Test
    public void testUpdate() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("1 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(10);
        Optional<Asset> ofResult = Optional.<Asset>of(asset);

        Asset asset1 = new Asset();
        asset1.setId(2L);
        asset1.setAge("2 year");
        asset1.setUptime("Uptime");
        asset1.setNumOfFailures(10);
        when(this.assetRepository.save((Asset) any())).thenReturn(asset1);
        when(this.assetRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(asset1, this.assetServiceImpl.update(new Asset()));
        verify(this.assetRepository).findById((Long) any());
        verify(this.assetRepository).save((Asset) any());
        assertTrue(this.assetServiceImpl.findAll().isEmpty());
    }

    @Test
    public void testUpdate2() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("Response from {}");
        asset.setUptime("Response from {}");
        asset.setNumOfFailures(10);
        Optional<Asset> ofResult = Optional.<Asset>of(asset);

        Asset asset1 = new Asset();
        asset1.setId(2L);
        asset1.setAge("1 year");
        asset1.setUptime("Uptime");
        asset1.setNumOfFailures(10);
        when(this.assetRepository.save((Asset) any())).thenReturn(asset1);
        when(this.assetRepository.findById((Long) any())).thenReturn(ofResult);
        Asset asset2 = mock(Asset.class);
        when(asset2.getNumOfFailures()).thenReturn(1);
        when(asset2.getUptime()).thenReturn("foo");
        when(asset2.getAge()).thenReturn("foo");
        when(asset2.getId()).thenReturn(1L);
        assertSame(asset1, this.assetServiceImpl.update(asset2));
        verify(this.assetRepository).findById((Long) any());
        verify(this.assetRepository).save((Asset) any());
        verify(asset2).getAge();
        verify(asset2).getId();
        verify(asset2).getNumOfFailures();
        verify(asset2).getUptime();
        assertTrue(this.assetServiceImpl.findAll().isEmpty());
    }

    @Test
    public void testFindById() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("1 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(10);
        Optional<Asset> ofResult = Optional.<Asset>of(asset);
        when(this.assetRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(asset, this.assetServiceImpl.findById(1L));
        verify(this.assetRepository).findById((Long) any());
        assertTrue(this.assetServiceImpl.findAll().isEmpty());
    }

    @Test
    public void testFindOutliersInAssetAge() {
        when(this.assetRepository.findAll()).thenReturn(new ArrayList<Asset>());
        assertTrue(this.assetServiceImpl.findOutliersInAssetAge().isEmpty());
        verify(this.assetRepository).findAll();
        assertTrue(this.assetServiceImpl.findAll().isEmpty());
    }

    @Test
    public void testFindOutliersInAssetAgeForFourAssets() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("100 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(1);

        Asset asset1 = new Asset();
        asset1.setId(2L);
        asset1.setAge("90 day");
        asset1.setUptime("Uptime");
        asset1.setNumOfFailures(6);

        Asset asset2 = new Asset();
        asset2.setId(3L);
        asset2.setAge("3 month");
        asset2.setUptime("Uptime");
        asset2.setNumOfFailures(5);

        Asset asset3 = new Asset();
        asset3.setId(4L);
        asset3.setAge("9 month");
        asset3.setUptime("Uptime");
        asset3.setNumOfFailures(10);

        Asset asset4 = new Asset();
        asset4.setId(5L);
        asset4.setAge("9 month");
        asset4.setUptime("Uptime");
        asset4.setNumOfFailures(6);

        Asset asset5 = new Asset();
        asset5.setId(6L);
        asset5.setAge("12 month");
        asset5.setUptime("Uptime");
        asset5.setNumOfFailures(0);

        ArrayList<Asset> assetList = new ArrayList<Asset>();
        assetList.add(asset);
        assetList.add(asset1);
        assetList.add(asset2);
        assetList.add(asset3);
        assetList.add(asset4);
        assetList.add(asset5);

        when(this.assetRepository.findAll()).thenReturn(assetList);
        assertFalse(this.assetServiceImpl.findOutliersInAssetAge().isEmpty());
        verify(this.assetRepository).findAll();
        assertEquals(1, this.assetServiceImpl.findOutliersInAssetAge().size());
    }


    @Test
    public void testGetOutliers() {
        ArrayList<Double> resultDoubleList = new ArrayList<Double>();
        resultDoubleList.add(10.0);
        resultDoubleList.add(10.0);
        assertTrue(this.assetServiceImpl.getOutliers(resultDoubleList).isEmpty());
    }

}

