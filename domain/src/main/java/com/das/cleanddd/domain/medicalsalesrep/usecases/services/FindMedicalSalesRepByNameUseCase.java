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
    if(inputDTO==null) {
      inputDTO = new MedcialSalesRepNamesInputDTO("", "", 1, 10);
    }
    if(inputDTO.page()<=0) {
      inputDTO = new MedcialSalesRepNamesInputDTO(inputDTO.name(), inputDTO.surname(), 1, inputDTO.pageSize());
    }
    if(inputDTO.pageSize()<=0) {
      inputDTO = new MedcialSalesRepNamesInputDTO(inputDTO.name(), inputDTO.surname(), inputDTO.page(), 10);
    }
    if(inputDTO.name()==null ) {
      inputDTO = new MedcialSalesRepNamesInputDTO("", inputDTO.surname(), inputDTO.page(), inputDTO.pageSize());
    }
    if(inputDTO.surname()==null) {
      inputDTO = new MedcialSalesRepNamesInputDTO(inputDTO.name(), "", inputDTO.page(), inputDTO.pageSize());
    } 
    MedicalSalesRepName medicalSalesRepName = new MedicalSalesRepName(inputDTO.name());
    MedicalSalesRepName medicalSalesRepSurname = new MedicalSalesRepName(inputDTO.surname());
    List<MedicalSalesRep> listMedicalSalesReps = repository.findByName(medicalSalesRepName, medicalSalesRepSurname, inputDTO.page(), inputDTO.pageSize());
    //List<MedicalSalesRep> listMedicalSalesReps = repository.findByName(inputDTO.name(), inputDTO.surname(), inputDTO.page(), inputDTO.pageSize());
    if(listMedicalSalesReps.isEmpty()) {
      throw new DomainException("Medical Sales Representative not found.");
    }    
    // Convert respose to output and return
    //return listMedicalSalesReps; //mapper.outputFromEntityList(listMedicalSalesReps);
    return mapper.outputFromEntityList(listMedicalSalesReps);
  }
}
