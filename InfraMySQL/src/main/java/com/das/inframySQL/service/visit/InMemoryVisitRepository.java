package com.das.cleanddd.service.visit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.UuidGenerator;
import com.das.cleanddd.domain.shared.criteria.Criteria;
import com.das.cleanddd.domain.visit.IVisitRepository;
import com.das.cleanddd.domain.visit.Visit;
import com.das.cleanddd.domain.visit.VisitId;

@Service
public final class InMemoryVisitRepository implements IVisitRepository {
    private UuidGenerator generator;

    public InMemoryVisitRepository(UuidGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void save(Visit visit) {};

    public Optional<Visit> search(Identifier id) {
            return null;
            
    };

    public List<Visit> matching(Criteria criteria) {
            return new ArrayList<>();
    };

    @Override
    public List<Visit> searchAll() {
        return Arrays.asList(
            new Visit(new VisitId(generator.generate()), null, null, null, null, null, null),
            new Visit(new VisitId(generator.generate()), null, null, null, null, null, null)
        );
    }
}
