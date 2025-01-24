package com.das.cleanddd.service.medicalsalesrep;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.UuidGenerator;
import com.das.cleanddd.domain.shared.criteria.Criteria;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public final class InMemoryMedicalSalesRepRepository implements MedicalSalesRepRepository {
	
	private UuidGenerator generator;

	public InMemoryMedicalSalesRepRepository(UuidGenerator generator) {
        this.generator = generator;
    }

	@Override
	public List<MedicalSalesRep> searchAll() {
		// Implement the method logic here
/* 		String uuid = this.generator.generate();
		return Arrays.asList(
            new MedicalSalesRep(generator.generate(), "name", "surname", "email@mail.com"),
            new MedicalSalesRep(generator.generate(), "Other name", "Other surname", "another@mail.com")
			);
 */		return null;

	}

	@Override
	public void save(MedicalSalesRep medicalSalesRepresentative) {
		// Implement the method logic here
	}

	@Override
	public List<MedicalSalesRep> matching(Criteria criteria) {
		// Implement the method logic here
		return null;
	}

	@Override
	public Optional<MedicalSalesRep> search(Identifier identifier) {
		// Implement the method logic here
		return Optional.empty();
	}
}
