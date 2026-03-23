package com.das.infrasqlserver.service.healthcareprof;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SQLServerHealthCareProfJpaRepository extends JpaRepository<HealthCareProfRepEntity, String> {

    Optional<HealthCareProfRepEntity> findByEmail(String email);

    @Query("SELECT e FROM HealthCareProfRepEntity e WHERE e.name = :name OR e.surname = :surname")
    List<HealthCareProfRepEntity> findByNameOrSurname(@Param("name") String name, @Param("surname") String surname);

    @Query("SELECT DISTINCT e FROM HealthCareProfRepEntity e JOIN e.specialties s WHERE s.code = :specialtyCode")
    List<HealthCareProfRepEntity> findBySpecialtyCode(@Param("specialtyCode") String specialtyCode);
}
