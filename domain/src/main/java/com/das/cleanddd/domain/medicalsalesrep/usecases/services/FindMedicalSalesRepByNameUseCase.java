package com.das.cleanddd.domain.medicalsalesrep.usecases.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepNamesInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

public class FindMedicalSalesRepByNameUseCase implements UseCase<MedicalSalesRepNamesInputDTO, List<MedicalSalesRepOutputDTO>> {

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
    public List<MedicalSalesRepOutputDTO> execute(MedicalSalesRepNamesInputDTO inputDTO) throws DomainException {
    // Validate Input
    if(inputDTO==null) {
      inputDTO = new MedicalSalesRepNamesInputDTO("", "", 1, 10);
    }
    if(inputDTO.page()<=0) {
      inputDTO = new MedicalSalesRepNamesInputDTO(inputDTO.name(), inputDTO.surname(), 1, inputDTO.pageSize());
    }
    if(inputDTO.pageSize()<=0) {
      inputDTO = new MedicalSalesRepNamesInputDTO(inputDTO.name(), inputDTO.surname(), inputDTO.page(), 10);
    }
    if(inputDTO.name()==null ) {
      inputDTO = new MedicalSalesRepNamesInputDTO("", inputDTO.surname(), inputDTO.page(), inputDTO.pageSize());
    }
    if(inputDTO.surname()==null) {
      inputDTO = new MedicalSalesRepNamesInputDTO(inputDTO.name(), "", inputDTO.page(), inputDTO.pageSize());
    } 
    MedicalSalesRepName medicalSalesRepName = new MedicalSalesRepName(inputDTO.name());
    MedicalSalesRepName medicalSalesRepSurname = new MedicalSalesRepName(inputDTO.surname());
    List<MedicalSalesRep> listMedicalSalesReps = repository.findByName(medicalSalesRepName, medicalSalesRepSurname, inputDTO.page(), inputDTO.pageSize());
    //List<MedicalSalesRep> listMedicalSalesReps = repository.findByName(inputDTO.name(), inputDTO.surname(), inputDTO.page(), inputDTO.pageSize());
    if(listMedicalSalesReps.isEmpty()) {
      throw new DomainException("Medical Sales Representative not found.");
    }    
    // Convert response to output and return
    //return listMedicalSalesReps; //mapper.outputFromEntityList(listMedicalSalesReps);
    return mapper.outputFromEntityList(listMedicalSalesReps);
  }
}
