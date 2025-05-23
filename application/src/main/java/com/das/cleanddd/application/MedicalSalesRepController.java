package com.das.cleanddd.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//import javax.validation.Valid;

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
    public ResponseEntity<Object> createMedicalSalesRep(@RequestBody CreateMedicalSalesRepInputDTO inputDTO) throws ResponseStatusException{
        try {
            return  ResponseEntity.status(HttpStatus.CREATED).body(createMedicalSalesRepUseCase.execute(inputDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateMedicalSalesRep(@RequestBody UpdateMedicalSalesRepInputDTO inputDTO) throws DomainException{
        try {
            return ResponseEntity.ok(updateMedicalSalesRepUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    //public ResponseEntity<EmptyBodyModel> activate(@RequestBody MedicalSalesRepIDDto inputDTO) throws ResponseStatusException {    
    public void activateMedicalSalesRep(@RequestBody MedicalSalesRepIDDto inputDTO) throws ResponseStatusException{
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
    public ResponseEntity<Object> getMedicalSalesRepByID(@RequestBody MedicalSalesRepIDDto inputDTO) throws ResponseStatusException{
        try {
            //return ResponseEntity.ok(getGetMedicalSalesRepByIdUseCase.execute(inputDTO));
            return ResponseEntity.ok(getGetMedicalSalesRepByIdUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        //return getGetMedicalSalesRepByIdUseCase.execute(inputDTO);
    }
    //@GetMapping("/list")
    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    //@ApiResponse(responseCode = "200", description = "List of Medical Sales Representatives", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicalSalesRepOutputDTO.class)))
    public ResponseEntity<Object> findMedicalSalesRepByName(
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
        ) throws ResponseStatusException {
        try {
            //return ResponseEntity.ok(findMedicalSalesRepByNameUseCase.execute(inputDTO));
            return ResponseEntity.ok(findMedicalSalesRepByNameUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }    
        //return findMedicalSalesRepByNameUseCase.execute(inputDTO);
    }
    
}
