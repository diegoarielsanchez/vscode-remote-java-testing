package com.das.inframongodb.service.medicalsalesrep;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MedicalSalesRepMongoRepository extends MongoRepository<MedicalSalesRepEntity, String> {

    Optional<MedicalSalesRepEntity> findByEmail(String email);

    @Query("{ '$or': [ { 'name': ?0 }, { 'surname': ?1 } ] }")
    List<MedicalSalesRepEntity> findByNameOrSurname(String name, String surname);
}