package com.das.infra.service.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke test for catalog-infra's JPA mapping — this module had no automated tests at
 * all until now. Verifies ProductEntity actually persists and round-trips through a
 * real (in-memory) database, and that the atomic stock-reservation query behaves
 * correctly under its own success/failure boundary.
 */
@DataJpaTest
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository repository;

    private ProductEntity aProduct(int stock) {
        ProductEntity entity = new ProductEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName("Amoxicillin 500mg");
        entity.setDescription("Antibiotic, 500mg capsules");
        entity.setPrice(BigDecimal.valueOf(12.50));
        entity.setUnit("box");
        entity.setStock(stock);
        entity.setActive(true);
        return entity;
    }

    @Test
    void savesAndReloadsAProduct() {
        ProductEntity entity = aProduct(100);

        repository.save(entity);

        Optional<ProductEntity> found = repository.findById(entity.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Amoxicillin 500mg");
        assertThat(found.get().getStock()).isEqualTo(100);
    }

    @Test
    void reserveStock_succeedsAndDecrementsWhenEnoughIsOnHand() {
        ProductEntity entity = aProduct(10);
        repository.save(entity);

        int rowsUpdated = repository.reserveStock(entity.getId(), 4);

        assertThat(rowsUpdated).isEqualTo(1);
        assertThat(repository.findById(entity.getId()).orElseThrow().getStock()).isEqualTo(6);
    }

    @Test
    void reserveStock_failsAtomically_whenNotEnoughIsOnHand() {
        ProductEntity entity = aProduct(2);
        repository.save(entity);

        int rowsUpdated = repository.reserveStock(entity.getId(), 5);

        assertThat(rowsUpdated).isZero();
        assertThat(repository.findById(entity.getId()).orElseThrow().getStock())
                .as("stock must be untouched when the reservation is rejected")
                .isEqualTo(2);
    }

    @Test
    void findByNameStartingWith_matchesCaseInsensitivePrefix() {
        repository.save(aProduct(10));
        ProductEntity ibuprofen = aProduct(5);
        ibuprofen.setName("Ibuprofen 200mg");
        repository.save(ibuprofen);

        var results = repository.findByNameStartingWith("amox");

        assertThat(results).extracting(ProductEntity::getName).containsExactly("Amoxicillin 500mg");
    }
}
