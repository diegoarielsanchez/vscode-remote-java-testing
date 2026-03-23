package com.das.cleanddd.service.visit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.criteria.Criteria;
import com.das.cleanddd.domain.visit.IVisitPlanRepository;
import com.das.cleanddd.domain.visit.entities.VisitPlan;

@Service
public final class VisitPlanRepository implements IVisitPlanRepository {
    private final Map<String, VisitPlan> visitPlans = new LinkedHashMap<>();

    public VisitPlanRepository() {
    }

    @Override
    public synchronized void save(VisitPlan visitPlan) {
        if (visitPlan == null || visitPlan.visitId() == null) {
            return;
        }
        visitPlans.put(visitPlan.visitId().value(), visitPlan);
    }

    @Override
    public Optional<VisitPlan> search(Identifier id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(visitPlans.get(id.value()));
    }

    @Override
    public List<VisitPlan> matching(Criteria criteria) {
        return new ArrayList<>(visitPlans.values());
    }

    @Override
    public synchronized List<VisitPlan> searchAll() {
        return new ArrayList<>(visitPlans.values());
    }
}
