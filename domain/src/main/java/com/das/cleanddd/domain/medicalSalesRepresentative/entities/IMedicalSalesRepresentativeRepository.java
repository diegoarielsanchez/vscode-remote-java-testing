package com.das.cleanddd.domain.medicalSalesRepresentative.entities;

import java.util.List;
import java.util.Optional;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.criteria.Criteria;

public interface IMedicalSalesRepresentativeRepository {

    void save(MedicalSalesRepresentative medicalSalesRepresentative);

    Optional<MedicalSalesRepresentative> search(Identifier id);

    List<MedicalSalesRepresentative> matching(Criteria criteria);

    List<MedicalSalesRepresentative> searchAll();

}