package com.das.cleanddd.service.medicalSalesRepresentative;

import com.das.cleanddd.domain.medicalSalesRepresentative.entities.IMedicalSalesRepresentativeRepository;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentative;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public final class InMemoryMedicalSalesRepresentativeRepository implements IMedicalSalesRepresentativeRepository {

	@Override
	public List<MedicalSalesRepresentative> searchAll() {
		// Implement the method logic here
		return null;
	}

	@Override
	public void save(MedicalSalesRepresentative medicalSalesRepresentative) {
		// Implement the method logic here
	}

	@Override
	public List<MedicalSalesRepresentative> matching(Criteria criteria) {
		// Implement the method logic here
		return null;
	}

	@Override
	public Optional<MedicalSalesRepresentative> search(Identifier identifier) {
		// Implement the method logic here
		return Optional.empty();
	}
}
