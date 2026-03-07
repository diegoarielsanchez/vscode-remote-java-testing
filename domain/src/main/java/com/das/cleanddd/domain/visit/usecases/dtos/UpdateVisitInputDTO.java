package com.das.cleanddd.domain.visit.usecases.dtos;

import java.time.LocalDate;

public record UpdateVisitInputDTO(
    String id,
    LocalDate visitDate,
    String healthCareProfId,
    String visitComments,
    String visitSiteId,
    String medicalSalesRepId
) {}
