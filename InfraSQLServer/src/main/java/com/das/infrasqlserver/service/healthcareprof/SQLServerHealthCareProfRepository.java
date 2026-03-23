package com.das.infrasqlserver.service.healthcareprof;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.IHealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.entities.Specialty;
import com.das.cleanddd.domain.shared.criteria.Criteria;

@Service
public final class SQLServerHealthCareProfRepository implements IHealthCareProfRepository {

    @Autowired
    private SQLServerHealthCareProfJpaRepository jpaRepository;

    @Override
    public List<HealthCareProf> searchAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(HealthCareProf healthCareProf) {
        HealthCareProfRepEntity entity = toEntity(healthCareProf);
        if (entity != null) {
            jpaRepository.save(entity);
        }
    }

    @Override
    public List<HealthCareProf> matching(Criteria criteria) {
        return null;
    }

    @Override
    public List<HealthCareProf> findByName(HealthCareProfName name, HealthCareProfName surname, int page, int pageSize) {
        List<HealthCareProfRepEntity> entities = jpaRepository.findByNameOrSurname(name.value(), surname.value());
        return entities.stream()
                .map(this::toDomain)
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthCareProf> findBySpecialty(String specialtyCode, int page, int pageSize) {
        List<HealthCareProfRepEntity> entities = jpaRepository.findBySpecialtyCode(specialtyCode);
        return entities.stream()
                .map(this::toDomain)
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HealthCareProf> findById(HealthCareProfId id) {
        if (id == null || id.value() == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<HealthCareProf> findByEmail(HealthCareProfEmail email) {
        return jpaRepository.findByEmail(email.value())
                .map(this::toDomain);
    }

    private HealthCareProf toDomain(HealthCareProfRepEntity entity) {
        List<Specialty> specialties = entity.getSpecialties() == null ? null :
                entity.getSpecialties().stream()
                        .map(s -> new Specialty(s.getCode(), s.getSpecialtyName()))
                        .collect(Collectors.toList());
        return new HealthCareProf(
                new HealthCareProfId(entity.getId()),
                new HealthCareProfName(entity.getName()),
                new HealthCareProfName(entity.getSurname()),
                new HealthCareProfEmail(entity.getEmail()),
                new HealthCareProfActive(entity.getActive()),
                specialties
        );
    }

    private HealthCareProfRepEntity toEntity(HealthCareProf domain) {
        HealthCareProfRepEntity entity = new HealthCareProfRepEntity();
        entity.setId(domain.id().value());
        entity.setName(domain.getName().value());
        entity.setSurname(domain.getSurname().value());
        entity.setEmail(domain.getEmail().value());
        entity.setActive(domain.getActive().value());
        if (domain.getSpecialties() != null) {
            List<HealthCareProfRepEntity.SpecialtyEmbeddable> specialties = domain.getSpecialties().stream()
                    .map(s -> new HealthCareProfRepEntity.SpecialtyEmbeddable(s.code(), s.name()))
                    .collect(Collectors.toList());
            entity.setSpecialties(specialties);
        }
        return entity;
    }
}
