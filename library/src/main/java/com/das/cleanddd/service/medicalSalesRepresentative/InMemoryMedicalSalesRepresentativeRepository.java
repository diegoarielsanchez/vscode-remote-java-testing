package com.das.cleanddd.service.medicalSalesRepresentative;

import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeRepository;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentative;
import com.das.cleanddd.domain.medicalSalesRepresentative.entities.MedicalSalesRepresentativeId;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.UuidGenerator;
import com.das.cleanddd.domain.shared.criteria.Criteria;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public final class InMemoryMedicalSalesRepresentativeRepository implements MedicalSalesRepresentativeRepository {
	
	private UuidGenerator generator;

	public InMemoryMedicalSalesRepresentativeRepository(UuidGenerator generator) {
        this.generator = generator;
    }

	@Override
	public List<MedicalSalesRepresentative> searchAll() {
		// Implement the method logic here
	    return Arrays.asList(
            new MedicalSalesRepresentative(new MedicalSalesRepresentativeId(generator.generate()), "name", "surname", "email@mail.com"),
            new MedicalSalesRepresentative(new MedicalSalesRepresentativeId(generator.generate()), "Other name", "Other surname", "another@mail.com")
		);
		//return null;
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
