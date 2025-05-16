package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedcialSalesRepNamesInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public class FindMedicalSalesRepByNameUseCase implements UseCase<MedcialSalesRepNamesInputDTO, List<MedicalSalesRepOutputDTO>> {

    @Autowired
    private final MedicalSalesRepRepository repository; 
    @Autowired
    private final MedicalSalesRepMapper mapper;

    public FindMedicalSalesRepByNameUseCase(MedicalSalesRepRepository repository
        , MedicalSalesRepMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;   
    }

    @Override
    public List<MedicalSalesRepOutputDTO> execute(MedcialSalesRepNamesInputDTO inputDTO) throws DomainException {
    // Validate Input
    if(inputDTO.name()==null || inputDTO.name().trim().isBlank() || inputDTO.name().trim().length()<3) {
      throw new DomainException("Medical Sales Representative Name is required (At least 3 char).");
    }
    if(inputDTO.surname()==null || inputDTO.surname().trim().isBlank() || inputDTO.surname().trim().length()<3) {
      throw new DomainException("Medical Sales Representative Surname is required (At least 3 char).");
    }
    MedicalSalesRepName medicalSalesRepName = new MedicalSalesRepName(inputDTO.name());
    MedicalSalesRepName medicalSalesRepSurname = new MedicalSalesRepName(inputDTO.surname());
    List<MedicalSalesRep> listMedicalSalesReps = repository.findByName(medicalSalesRepName, medicalSalesRepSurname);
    if(listMedicalSalesReps.isEmpty()) {
      throw new DomainException("Medical Sales Representative not found.");
    }    
    // Convert respose to output and return
    //return listMedicalSalesReps; //mapper.outputFromEntityList(listMedicalSalesReps);
    return mapper.outputFromEntityList(listMedicalSalesReps);
  }
}
