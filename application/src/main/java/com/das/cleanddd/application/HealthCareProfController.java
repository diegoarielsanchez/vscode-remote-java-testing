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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.das.cleanddd.domain.healthcareprof.usecases.dtos.CreateHealthCareProfInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfIDDto;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfNamesInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.UpdateHealthCareProfInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.services.HealthCareProfUseCaseFactory;
import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyInput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

//import javax.validation.Valid;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/v1/healthcareprof")
//@RequiredArgsConstructor
//@Tag(name = "Medical Sales Rep", description = "Medical Sales Rep API")
//@SecurityRequirement(name = "bearerAuth")
//@Operation(summary = "Medical Sales Rep API", description = "API for managing Medical Sales Representatives")
public class HealthCareProfController {
    @Autowired
    private final UseCase<CreateHealthCareProfInputDTO, HealthCareProfOutputDTO> createHealthCareProfUseCase;
    private final UseCase<UpdateHealthCareProfInputDTO, HealthCareProfOutputDTO> updateHealthCareProfUseCase;
    private final UseCaseOnlyInput<HealthCareProfIDDto> activateHealthCareProfUseCase;
    private final UseCaseOnlyInput<HealthCareProfIDDto> deactivateHealthCareProfUseCase;
    private final UseCase<HealthCareProfIDDto, HealthCareProfOutputDTO> getGetHealthCareProfByIdUseCase;
    private final UseCase<HealthCareProfNamesInputDTO, List<HealthCareProfOutputDTO>> findHealthCareProfByNameUseCase;

    public HealthCareProfController(HealthCareProfUseCaseFactory medicalSalesRepUseCaseFactory) {
        this.createHealthCareProfUseCase = medicalSalesRepUseCaseFactory.getCreateHealthCareProfUseCase();
        this.updateHealthCareProfUseCase = medicalSalesRepUseCaseFactory.getUpdateHealthCareProfUseCase();
        this.activateHealthCareProfUseCase = medicalSalesRepUseCaseFactory.getActivateHealthCareProfUseCase();
        this.deactivateHealthCareProfUseCase = medicalSalesRepUseCaseFactory.getActivateHealthCareProfUseCase();
        this.getGetHealthCareProfByIdUseCase = medicalSalesRepUseCaseFactory.getGetHealthCareProfByIdUseCase();
        this.findHealthCareProfByNameUseCase = medicalSalesRepUseCaseFactory.getFindHealthCareProfByNameUseCase();
    }
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createHealthCareProf(@Valid @RequestBody CreateHealthCareProfInputDTO inputDTO) throws ResponseStatusException{
        try {
            return  ResponseEntity.status(HttpStatus.CREATED).body(createHealthCareProfUseCase.execute(inputDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateHealthCareProf(@Valid @RequestBody UpdateHealthCareProfInputDTO inputDTO) throws DomainException{
        try {
            return ResponseEntity.ok(updateHealthCareProfUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    //public ResponseEntity<EmptyBodyModel> activate(@RequestBody HealthCareProfIDDto inputDTO) throws ResponseStatusException {    
    public void activateHealthCareProf(@Valid @RequestBody HealthCareProfIDDto inputDTO) throws ResponseStatusException{
        try {
            activateHealthCareProfUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //activateHealthCareProfUseCase.execute(inputDTO);
    }
    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateHealthCareProf(@Valid @RequestBody HealthCareProfIDDto inputDTO) throws DomainException{
        try {
            deactivateHealthCareProfUseCase.execute(inputDTO);
        } catch (DomainException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        //deactivateHealthCareProfUseCase.execute(inputDTO);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getHealthCareProfByID(@Valid @RequestBody HealthCareProfIDDto inputDTO) throws ResponseStatusException{
        try {
            //return ResponseEntity.ok(getGetHealthCareProfByIdUseCase.execute(inputDTO));
            return ResponseEntity.ok(getGetHealthCareProfByIdUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        //return getGetHealthCareProfByIdUseCase.execute(inputDTO);
    }
    //@GetMapping("/list")
    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    //@ApiResponse(responseCode = "200", description = "List of Medical Sales Representatives", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HealthCareProfOutputDTO.class)))
    public ResponseEntity<Object> findHealthCareProfByName(
        @RequestParam(required = false, defaultValue = "") String firstName,
        @RequestParam(required = false, defaultValue = "") String lastName,
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize
        ) throws ResponseStatusException {
        try {
            HealthCareProfNamesInputDTO inputDTO = new HealthCareProfNamesInputDTO(firstName, lastName, page, pageSize);
            return ResponseEntity.ok(findHealthCareProfByNameUseCase.execute(inputDTO));
        } catch (DomainException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }    
    }
    
}
