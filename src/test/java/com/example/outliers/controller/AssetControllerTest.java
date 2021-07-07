package com.example.outliers.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.outliers.model.Asset;
import com.example.outliers.service.AssetService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AssetControllerTest {
    @Test
    public void testConstructor() {
        new AssetController(null);
        assertNull(null);
    }

    @Test
    public void testGetAssets() {
        AssetService assetService = mock(AssetService.class);
        when(assetService.findAll()).thenReturn(new ArrayList<Asset>());
        AssetController assetController = new AssetController(assetService);
        ResponseEntity<List<Asset>> actualAssets = assetController.getAssets();
        assertEquals("<200 OK OK,[],[]>", actualAssets.toString());
        assertTrue(actualAssets.hasBody());
        assertEquals(HttpStatus.OK, actualAssets.getStatusCode());
        assertTrue(actualAssets.getHeaders().isEmpty());
        verify(assetService).findAll();
        assertEquals(actualAssets, assetController.getOutliersFromAssetAges());
    }

    @Test
    public void testAddAsset() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("1 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(10);
        AssetService assetService = mock(AssetService.class);
        when(assetService.save((Asset) any())).thenReturn(asset);
        AssetController assetController = new AssetController(assetService);
        ResponseEntity<Asset> actualAddAssetResult = assetController.addAsset(new Asset());
        assertEquals("<200 OK OK,Asset{id=1, age='1 year', uptime='Uptime', numOfFailures=10},[]>",
                actualAddAssetResult.toString());
        assertTrue(actualAddAssetResult.getHeaders().isEmpty());
        assertTrue(actualAddAssetResult.hasBody());
        assertEquals(HttpStatus.OK, actualAddAssetResult.getStatusCode());
        verify(assetService).save((Asset) any());
    }

    @Test
    public void testUpdateAsset() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAge("1 year");
        asset.setUptime("Uptime");
        asset.setNumOfFailures(10);
        AssetService assetService = mock(AssetService.class);
        when(assetService.update((Asset) any())).thenReturn(asset);
        AssetController assetController = new AssetController(assetService);
        ResponseEntity<Asset> actualUpdateAssetResult = assetController.updateAsset(new Asset());
        assertEquals("<200 OK OK,Asset{id=1, age='1 year', uptime='Uptime', numOfFailures=10},[]>",
                actualUpdateAssetResult.toString());
        assertTrue(actualUpdateAssetResult.getHeaders().isEmpty());
        assertTrue(actualUpdateAssetResult.hasBody());
        assertEquals(HttpStatus.OK, actualUpdateAssetResult.getStatusCode());
        verify(assetService).update((Asset) any());
    }

    @Test
    public void testGetOutliersFromAssetAges() {
        AssetService assetService = mock(AssetService.class);
        when(assetService.findOutliersInAssetAge()).thenReturn(new ArrayList<Long>());
        AssetController assetController = new AssetController(assetService);
        ResponseEntity<List<Long>> actualOutliersFromAssetAges = assetController.getOutliersFromAssetAges();
        assertEquals("<200 OK OK,[],[]>", actualOutliersFromAssetAges.toString());
        assertTrue(actualOutliersFromAssetAges.hasBody());
        assertEquals(HttpStatus.OK, actualOutliersFromAssetAges.getStatusCode());
        assertTrue(actualOutliersFromAssetAges.getHeaders().isEmpty());
        verify(assetService).findOutliersInAssetAge();
        assertEquals(actualOutliersFromAssetAges, assetController.getAssets());
    }
}

