package com.das.inframongodb.service.medicalsalesrep;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.criteria.Criteria;

@Service
public final class MongoDBMedicalSalesRepRepository implements MedicalSalesRepRepository {

    @Autowired
    private MedicalSalesRepMongoRepository mongoRepository;

    @Override
    public List<MedicalSalesRep> searchAll() {
        return mongoRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MedicalSalesRep medicalSalesRep) {
        MedicalSalesRepEntity entity = toEntity(medicalSalesRep);
        if (entity == null) {
            throw new IllegalArgumentException("Failed to convert MedicalSalesRep to entity");
        }
        mongoRepository.save(entity);
    }

    @Override
    public List<MedicalSalesRep> matching(Criteria criteria) {
        // Implement criteria-based search if needed
        return null;
    }

    @Override
    public List<MedicalSalesRep> findByName(MedicalSalesRepName name, MedicalSalesRepName surname, int page, int pageSize) {
        List<MedicalSalesRepEntity> entities = mongoRepository.findByNameOrSurname(name.value(), surname.value());
        return entities.stream()
                .map(this::toDomain)
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MedicalSalesRep> findById(MedicalSalesRepId identifier) {
        return mongoRepository.findById(Objects.requireNonNull(identifier.value()))
                .map(this::toDomain);
    }

    @Override
    public Optional<MedicalSalesRep> findByEmail(MedicalSalesRepEmail email) {
        return mongoRepository.findByEmail(email.value())
                .map(this::toDomain);
    }

    private MedicalSalesRep toDomain(MedicalSalesRepEntity entity) {
        return new MedicalSalesRep(
                new MedicalSalesRepId(entity.getId()),
                new MedicalSalesRepName(entity.getName()),
                new MedicalSalesRepName(entity.getSurname()),
                new MedicalSalesRepEmail(entity.getEmail()),
                new MedicalSalesRepActive(entity.getActive())
        );
    }

    private MedicalSalesRepEntity toEntity(MedicalSalesRep domain) {
        MedicalSalesRepEntity entity = new MedicalSalesRepEntity();
        entity.setId(domain.id().value());
        entity.setName(domain.getName().value());
        entity.setSurname(domain.getSurname().value());
        entity.setEmail(domain.getEmail().value());
        entity.setActive(domain.getActive().value());
        return entity;
    }
}
