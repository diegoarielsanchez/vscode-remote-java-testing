package com.das.cleanddd.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedcialSalesRepNamesInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepIDDto;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.UpdateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.MedicalSalesRepUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/v1/medicalsalesrep")
//@RequiredArgsConstructor
//@Tag(name = "Medical Sales Rep", description = "Medical Sales Rep API")
//@SecurityRequirement(name = "bearerAuth")
//@Operation(summary = "Medical Sales Rep API", description = "API for managing Medical Sales Representatives")
public class MedicalSalesRepController {
    @Autowired
    private final UseCase<CreateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> createMedicalSalesRepUseCase;
    private final UseCase<UpdateMedicalSalesRepInputDTO, MedicalSalesRepOutputDTO> updateMedicalSalesRepUseCase;
    private final UseCaseOnlyInput<MedicalSalesRepIDDto> activateMedicalSalesRepUseCase;
    private final UseCaseOnlyInput<MedicalSalesRepIDDto> deactivateMedicalSalesRepUseCase;
    private final UseCase<MedicalSalesRepIDDto, MedicalSalesRepOutputDTO> getGetMedicalSalesRepByIdUseCase;
    private final UseCase<MedcialSalesRepNamesInputDTO, List<MedicalSalesRepOutputDTO>> findMedicalSalesRepByNameUseCase;

    public MedicalSalesRepController(MedicalSalesRepUseCaseFactory medicalSalesRepUseCaseFactory) {
        this.createMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getCreateMedicalSalesRepUseCase();
        this.updateMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getUpdateMedicalSalesRepUseCase();
        this.activateMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getActivateMedicalSalesRepUseCase();
        this.deactivateMedicalSalesRepUseCase = medicalSalesRepUseCaseFactory.getDeActivateMedicalSalesRepUseCase();
        this.getGetMedicalSalesRepByIdUseCase = medicalSalesRepUseCaseFactory.getGetMedicalSalesRepByIdUseCase();
        this.findMedicalSalesRepByNameUseCase = medicalSalesRepUseCaseFactory.findMedicalSalesRepByNameUseCase();
    }
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalSalesRepOutputDTO createMedicalSalesRep(@RequestBody CreateMedicalSalesRepInputDTO inputDTO) throws DomainException{
        try {
            return createMedicalSalesRepUseCase.execute(inputDTO);
        } catch (DomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //return createMedicalSalesRepUseCase.execute(inputDTO);
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public MedicalSalesRepOutputDTO updateMedicalSalesRep(@RequestBody UpdateMedicalSalesRepInputDTO inputDTO) throws DomainException{
        try {
            return updateMedicalSalesRepUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //return updateMedicalSalesRepUseCase.execute(inputDTO);
    }
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateMedicalSalesRep(@RequestBody MedicalSalesRepIDDto inputDTO) throws DomainException{
        try {
            activateMedicalSalesRepUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //activateMedicalSalesRepUseCase.execute(inputDTO);
    }
    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateMedicalSalesRep(@RequestBody MedicalSalesRepIDDto inputDTO) throws DomainException{
        try {
            deactivateMedicalSalesRepUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //deactivateMedicalSalesRepUseCase.execute(inputDTO);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public MedicalSalesRepOutputDTO getMedicalSalesRepByID(@RequestBody MedicalSalesRepIDDto inputDTO) throws DomainException{
        try {
            return getGetMedicalSalesRepByIdUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //return getGetMedicalSalesRepByIdUseCase.execute(inputDTO);
    }
    //@GetMapping("/list")
    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    //@ApiResponse(responseCode = "200", description = "List of Medical Sales Representatives", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicalSalesRepOutputDTO.class)))
    public List<MedicalSalesRepOutputDTO> findMedicalSalesRepByName(
        @RequestBody MedcialSalesRepNamesInputDTO inputDTO
        // , 
        // //@Parameter(description = "Page number", example = 1)
        // @RequestParam(defaultValue = "1") int page
        // ,
        // //@Parameter(description = "Page size", example = "10")
        // @RequestParam(defaultValue = "10") int pageSize
        // ,
        // //@Parameter(description = "Sort by field", example = "name")
        // //@RequestParam(defaultValue = "surname") String sortBy
        ) throws DomainException{
        try {
            return findMedicalSalesRepByNameUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }    
        //return findMedicalSalesRepByNameUseCase.execute(inputDTO);
    }
    
}
