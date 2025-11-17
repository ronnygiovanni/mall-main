package com.example.MallManagement.repository;

import com.example.MallManagement.model.ElectricalAsset;
import org.springframework.stereotype.Repository;

@Repository
public class ElectricalAssetRepository extends InFileRepository<ElectricalAsset> {
    public ElectricalAssetRepository() {
        super("electrical_assets.json", ElectricalAsset.class);
    }
}