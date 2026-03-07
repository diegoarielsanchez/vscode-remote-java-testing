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

import com.das.cleanddd.domain.shared.UseCase;
import com.das.cleanddd.domain.shared.UseCaseOnlyOutput;
import com.das.cleanddd.domain.shared.exceptions.DomainException;
import com.das.cleanddd.domain.visit.usecases.dtos.CreateVisitInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.UpdateVisitInputDTO;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitIDDto;
import com.das.cleanddd.domain.visit.usecases.dtos.VisitOutputDTO;
import com.das.cleanddd.domain.visit.usecases.services.VisitUseCaseFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/v1/visit")
@Tag(name = "Visit", description = "API for managing Visits")
@SecurityRequirement(name = "bearerAuth")
public class VisitController {

    @Autowired
    private final UseCase<CreateVisitInputDTO, VisitOutputDTO> createVisitUseCase;
    private final UseCase<UpdateVisitInputDTO, VisitOutputDTO> updateVisitUseCase;
    private final UseCase<VisitIDDto, VisitOutputDTO> getVisitByIdUseCase;
    private final UseCaseOnlyOutput<List<VisitOutputDTO>> listVisitsUseCase;

    public VisitController(VisitUseCaseFactory visitUseCaseFactory) {
        this.createVisitUseCase = visitUseCaseFactory.getCreateVisitUseCase();
        this.updateVisitUseCase = visitUseCaseFactory.getUpdateVisitUseCase();
        this.getVisitByIdUseCase = visitUseCaseFactory.getVisitByIdUseCase();
        this.listVisitsUseCase = visitUseCaseFactory.getListVisitsUseCase();
    }

    @PostMapping("/create")
    @Operation(summary = "Create visit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createVisit(@Valid @RequestBody CreateVisitInputDTO inputDTO) throws ResponseStatusException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(createVisitUseCase.execute(inputDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update visit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateVisit(@Valid @RequestBody UpdateVisitInputDTO inputDTO) throws ResponseStatusException {
        try {
            return ResponseEntity.ok(updateVisitUseCase.execute(inputDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    @Operation(summary = "Get visit by ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getVisitById(@Valid @RequestBody VisitIDDto inputDTO) throws ResponseStatusException {
        try {
            return ResponseEntity.ok(getVisitByIdUseCase.execute(inputDTO));
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/list")
    @Operation(summary = "List visits")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> listVisits() throws ResponseStatusException {
        try {
            return ResponseEntity.ok(listVisitsUseCase.execute());
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
