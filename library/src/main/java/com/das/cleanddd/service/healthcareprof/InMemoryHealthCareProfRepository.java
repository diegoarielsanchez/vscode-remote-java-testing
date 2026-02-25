package com.das.cleanddd.service.healthcareprof;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.shared.criteria.Criteria;

@Service
public final class InMemoryHealthCareProfRepository implements HealthCareProfRepository {
	
	 private final HashMap<String, HealthCareProf> healthCareProfs = new HashMap<>();

	@Override
	public List<HealthCareProf> searchAll() {

		// Implement the method logic here
		return healthCareProfs.entrySet().stream()
				.map(entry -> entry.getValue())
				.toList();
	}

	@Override
	public void save(HealthCareProf healthCareProf) {
		// Implement the method logic here
		healthCareProfs.put(healthCareProf.id().value(), healthCareProf);
	}

	@Override
	public List<HealthCareProf> matching(Criteria criteria) {
		// Implement the method logic here
		return null;
	}

	@Override
	public List<HealthCareProf> findByName(HealthCareProfName name, HealthCareProfName surname, int page, int pageSize) {
		// Implement the method logic here
		return healthCareProfs.entrySet().stream()
				.map(entry -> entry.getValue())
				.filter(healthCareProf -> healthCareProf.getName().equals(name) || healthCareProf.getSurname().equals(surname))
				.skip(((long) (page - 1)) * pageSize)
				.limit(pageSize)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<HealthCareProf> findById(HealthCareProfId identifier) {
		// Implement the method logic here
		return Optional.ofNullable(healthCareProfs.get(identifier.value()));
	}

	@Override
	public Optional<HealthCareProf> findByEmail(HealthCareProfEmail email) {
		// Implement the method logic here
		return healthCareProfs.entrySet().stream()
				.map(entry -> entry.getValue())
				.filter(healthCareProf -> healthCareProf.getEmail().equals(email))
				.findFirst();
	}
}
