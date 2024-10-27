package com.mycompany.app;

import java.util.List;
import java.util.Optional;

public interface LibraryAssetRepository extends JpaRepository<LibraryAsset, Long> {
    LibraryAsset findByAssetCode(String assetCode);
    List<LibraryAsset> findByAssetTitle(String assetTitle);
    List<LibraryAsset> findByCreatorName(String assetCreator);
    List<LibraryAsset> findByPublisherName(String publisherName);
    List<LibraryAsset> findByAssetCategory(String assetCategory);
    List<LibraryAsset> findByAssetType(String assetCategory);
}
