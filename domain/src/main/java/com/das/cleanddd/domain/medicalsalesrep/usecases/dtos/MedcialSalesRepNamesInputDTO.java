package com.das.cleanddd.domain.medicalsalesrep.usecases.dtos;

public record MedcialSalesRepNamesInputDTO(
  String name,
  String surname,
  int page,
  int pageSize
) {}
