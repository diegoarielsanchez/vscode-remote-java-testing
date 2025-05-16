package com.das.cleanddd.service.medicalsalesrep;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.criteria.Criteria;

@Service
public final class InMemoryMedicalSalesRepRepository implements MedicalSalesRepRepository {
	
	 private final HashMap<String, MedicalSalesRep> medicalSalesReps = new HashMap<>();

	@Override
	public List<MedicalSalesRep> searchAll() {

		// TODO Auto-generated method stub
		return medicalSalesReps.entrySet().stream()
				.map(entry -> entry.getValue())
				.toList();
	}

	@Override
	public void save(MedicalSalesRep medicalSalesRep) {
		// Implement the method logic here
		medicalSalesReps.put(medicalSalesRep.id().value(), medicalSalesRep);
	}

	@Override
	public List<MedicalSalesRep> matching(Criteria criteria) {
		// Implement the method logic here
		return null;
	}

	@Override
	public List<MedicalSalesRep> findByName(MedicalSalesRepName name, MedicalSalesRepName surname) {
		// Implement the method logic here
		return medicalSalesReps.entrySet().stream()
				.map(entry -> entry.getValue())
				.filter(medicalSalesRep -> medicalSalesRep.getName().equals(name) && medicalSalesRep.getSurname().equals(surname))
				.toList();
	}

	@Override
	public Optional<MedicalSalesRep> findById(MedicalSalesRepId identifier) {
		// Implement the method logic here
		return Optional.ofNullable(medicalSalesReps.get(identifier.value()));
	}

	@Override
	public Optional<MedicalSalesRep> findByEmail(MedicalSalesRepEmail email) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(medicalSalesReps.get(email.value()));
		//throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
	}
}
