package com.das.cleanddd.domain.visitBoundedContext;

import java.util.List;
import java.util.Optional;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.criteria.Criteria;

public interface IVisitRepository {
    void save(Visit visit);

    Optional<Visit> search(Identifier id);

    List<Visit> matching(Criteria criteria);

    List<Visit> searchAll();

}
