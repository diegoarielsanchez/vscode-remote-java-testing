package com.das.cleanddd.service.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.criteria.Criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public final class InMemoryMedicalSalesRepRepository implements MedicalSalesRepRepository {
	
	 private HashMap<String, MedicalSalesRep> medicalSalesReps = new HashMap<>();

	@Override
	public List<MedicalSalesRep> searchAll() {
		// Implement the method logic here
/* 		String uuid = this.generator.generate();
		return Arrays.asList(
            new MedicalSalesRep(generator.generate(), "name", "surname", "email@mail.com"),
            new MedicalSalesRep(generator.generate(), "Other name", "Other surname", "another@mail.com")
			);
		
 */	
		return null;

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
