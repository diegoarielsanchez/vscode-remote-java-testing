package com.das.cleanddd.domain.visit.usecases.dtos;

import java.time.LocalDate;

public record CreateVisitInputDTO(
    LocalDate visitDate,
    String healthCareProfId,
    String visitComments,
    String visitSiteId,
    String medicalSalesRepId
) {}
