package com.mycompany.app;

import java.util.List;

@Service
public class LibraryAssetService {
    @Autowired private LibraryAssetRepository assetRepository;

    public LibraryAsset findByCode(@NonNull String assetCode) {
        return this.assetRepository.findByAssetCode(assetCode);
    }

    public List<LibraryAsset> findByCreator(@NonNull String assetCreator) {
        return this.assetRepository.findByCreatorName(assetCreator);
    }

    public List<LibraryAsset> findByTitle(@NonNull String assetTitle) {
        return this.assetRepository.findByAssetTitle(assetTitle);
    }

    public List<LibraryAsset> findByCategory(@NonNull String assetCategory) {
        return this.assetRepository.findByAssetCategory(assetCategory);
    }

    public List<LibraryAsset> findByType(@NonNull String assetType) {
        return this.assetRepository.findByAssetType(assetType);
    }

    public List<LibraryAsset> findByPublisher(@NonNull String assetPublisher) {
        return this.assetRepository.findByPublisherName(assetPublisher);
    }

    @Transactional
    public void addAsset(@NonNull LibraryAsset assetToAdd) {
        this.assetRepository.save(assetToAdd);
    }

    public List<LibraryAsset> getAllAssets() {
        return this.assetRepository.findAll();
    }

    @Transactional
    public void removeAsset(@NonNull String assetCode) {
        LibraryAsset queriedAsset = this.findByCode(assetCode);
        this.assetRepository.delete(queriedAsset);
    }

    @Transactional
    public void removeAsset(@NonNull LibraryAsset assetToRemove) {
        this.assetRepository.delete(assetToRemove);
    }

    @Transactional
    public LibraryAsset updateAssetAttributes(@NonNull String assetCode, @NonNull AssetAttributesDTO dtoAttrs) {
        LibraryAsset updAsset = this.assetRepository.findByAssetCode(assetCode);

        if (dtoAttrs.getEditionYear() != null && dtoAttrs.getEditionYear() > (short) 0) {
            updAsset.setEditionYear(dtoAttrs.getEditionYear());
        }

        if (dtoAttrs.getCreatorName() != null && !dtoAttrs.getCreatorName().isEmpty()) {
            updAsset.setCreatorName(dtoAttrs.getCreatorName());
        }

        if (dtoAttrs.getPublisherName() != null && !dtoAttrs.getPublisherName().isEmpty()) {
            updAsset.setPublisherName(dtoAttrs.getPublisherName());
        }

        if (dtoAttrs.getAssetCategory() != null && !dtoAttrs.getAssetCategory().isEmpty()) {
            updAsset.setAssetCategory(dtoAttrs.getAssetCategory());
        }

        if (dtoAttrs.getAssetType() != null && !dtoAttrs.getAssetType().isEmpty()) {
            updAsset.setAssetType(dtoAttrs.getAssetType());
        }

        if (dtoAttrs.getAssetTitle() != null && !dtoAttrs.getAssetTitle().isEmpty()) {
            updAsset.setAssetTitle(dtoAttrs.getAssetTitle());
        }

        if (dtoAttrs.getPriceUSD() != null && !dtoAttrs.getPriceUSD().isEmpty()) {
            updAsset.setPriceUSD(dtoAttrs.getPriceUSD());
        }

        return this.assetRepository.save(updAsset);
    }
}