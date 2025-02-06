package com.das.cleanddd.domain.medicalsalesrep.entities;

import java.util.List;
import java.util.Optional;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.criteria.Criteria;

public interface MedicalSalesRepRepository {

    void save(MedicalSalesRep medicalSalesRepresentative);

    Optional<MedicalSalesRep> search(MedicalSalesRepId id);

    List<MedicalSalesRep> matching(Criteria criteria);

    List<MedicalSalesRep> searchAll();

    Optional<MedicalSalesRep> findByEmail(MedicalSalesRepEmail email);

}