package com.das.cleanddd.domain.visit.usecases.dtos;

import java.time.LocalDateTime;

public record CreateVisitPlanInputDTO(
    LocalDateTime visitDateTime,
    String healthCareProfId,
    String visitComments,
    String visitSiteId,
    String medicalSalesRepId
) {}
