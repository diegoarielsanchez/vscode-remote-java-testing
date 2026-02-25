package com.cleanddd.domain.healthcareprof;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProf;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfActive;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfEmail;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfFactory;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfId;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfName;
import com.das.cleanddd.domain.healthcareprof.entities.HealthCareProfRepository;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.CreateHealthCareProfInputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfMapper;
import com.das.cleanddd.domain.healthcareprof.usecases.dtos.HealthCareProfOutputDTO;
import com.das.cleanddd.domain.healthcareprof.usecases.services.CreateHealthCareProfUseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class CreateHealthCareProfUseCaseTest {

    private HealthCareProfRepository healthCareProfRepositoryMock = mock(HealthCareProfRepository.class);
    private HealthCareProfFactory healthCareProfFactoryMock = mock(HealthCareProfFactory.class);
    private HealthCareProfMapper healthCareProfMapperMock = mock(HealthCareProfMapper.class);
    private CreateHealthCareProfUseCase createHealthCareProfUseCase = new CreateHealthCareProfUseCase(healthCareProfRepositoryMock,healthCareProfFactoryMock,healthCareProfMapperMock);

    private final HealthCareProf healthCareProfMock = mock(HealthCareProf.class, Mockito.RETURNS_DEEP_STUBS);

    //private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
    private final HealthCareProfId validId = HealthCareProfIdMother.random();
    //private final String validName = "Foo Bar";
    private final HealthCareProfName validName = HealthCareProfNameMother.random();
    private final HealthCareProfName validSurname = HealthCareProfNameMother.random();
    //private final String validEmail = "foo@bar.com";
    private final HealthCareProfEmail validEmail = new HealthCareProfEmail("foo@bar.com");
    //private final HealthCareProfEmail validEmail = HealthCareProfEmailMother.random();
    private final HealthCareProfActive validActive = HealthCareProfActiveMother.create(false);
    //private final CreateHealthCareProfInputDTO validInputDTO = new CreateHealthCareProfInputDTO(validId.toString(), validName.toString(), validSurname.toString(), validEmail.toString());
    private final CreateHealthCareProfInputDTO validInputDTO = new CreateHealthCareProfInputDTO(validName.toString(), validSurname.toString(), validEmail.toString());
    //private final HealthCareProfOutputDTO validOutputDto = new HealthCareProfOutputDTO(validId.value(), validName.value(), validSurname.value(), validEmail.value(), validActive.value());

    // void prepareStubs() throws BusinessException {
    //     //when(healthCareProfFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
    //     //doReturn(healthCareProfMock).when(healthCareProfFactoryMock).createHealthCareProf(HealthCareProfNameMother.random(),HealthCareProfNameMother.random(),HealthCareProfEmailMother.random());
    //     doReturn(healthCareProfMock).when(healthCareProfFactoryMock).createHealthCareProf(HealthCareProfNameMother.random(),HealthCareProfNameMother.random(),HealthCareProfEmailMother.random());
    //     when(healthCareProfMapperMock.outputFromEntity(any())).thenReturn(validOutputDto);
    // }

    @BeforeEach
    void setUp() {
      // Reset mocks before each test
      healthCareProfRepositoryMock = mock(HealthCareProfRepository.class);
      healthCareProfFactoryMock = mock(HealthCareProfFactory.class);
      healthCareProfMapperMock = mock(HealthCareProfMapper.class);
      createHealthCareProfUseCase = new CreateHealthCareProfUseCase(healthCareProfRepositoryMock,healthCareProfFactoryMock,healthCareProfMapperMock);

      // // Set up default stubbing for factory and mapper to avoid null returns
      // Mockito.when(healthCareProfFactoryMock.createHealthCareProf(
      //         any(HealthCareProfName.class),
      //         any(HealthCareProfName.class),
      //         any(HealthCareProfEmail.class)
      // )).thenReturn(healthCareProfMock);

      // HealthCareProfOutputDTO expectedOutput = new HealthCareProfOutputDTO(
      //         validId.value(),
      //         validName.value(),
      //         validSurname.value(),
      //         validEmail.value(),
      //         validActive.value()
      // );
      // Mockito.when(healthCareProfMapperMock.outputFromEntity(any(HealthCareProf.class)))
      //         .thenReturn(expectedOutput);
    }

    @Test
    void shouldCreateHealthCareProfUseCase() {
      assertNotNull(createHealthCareProfUseCase);
    }
    @Test
    void shouldCreateHealthCareProfInputDTO() {
      assertNotNull(validInputDTO); 
    }

    @Test
    void shouldCreateHealthCareProfId() {
      assertNotNull(validId);
    }
    @Test
    void shouldCreateHealthCareProfName() {
      assertNotNull(validName);
    } 
    @Test
    void shouldCreateHealthCareProfSurname() {
      assertNotNull(validSurname);
    } 
    @Test
    void shouldCreateHealthCareProfEmail() {
      assertNotNull(validEmail);
    }   

    @Test
    void shouldCreateHealthCareProf() {
      assertNotNull(healthCareProfFactoryMock);
    }
/*     @Test
    void shouldCreateHealthCareProfRepository() {
      assertNotNull(healthCareProfRepositoryMock);
    } */
    @Test
    void shouldCreateHealthCareProfMapper() {
      assertNotNull(healthCareProfMapperMock);
    }
    @Test
    void shouldCallFactoriesAndDataAccess() throws BusinessException, DomainException {
      //prepareStubs();
      setUp();
      // Act: execute the use case with valid input
      createHealthCareProfUseCase.execute(validInputDTO);
      verify(healthCareProfFactoryMock, times(1)).createHealthCareProf( new HealthCareProfName(validInputDTO.name()), new HealthCareProfName(validInputDTO.surname()), new HealthCareProfEmail(validInputDTO.email()));
      verify(healthCareProfRepositoryMock, times(1)).save(any());
    }

    @Test
    void shouldCreateMedicalSales() throws DomainException, BusinessException {
      // Arrange: stub the mocks to return valid objects
      setUp();
      //HealthCareProf healthCareProf = mock(HealthCareProf.class);
      Mockito.when(healthCareProfFactoryMock.createHealthCareProf(
              any(HealthCareProfName.class),
              any(HealthCareProfName.class),
              any(HealthCareProfEmail.class)
      )).thenReturn(healthCareProfMock);

      HealthCareProfOutputDTO expectedOutput = new HealthCareProfOutputDTO(
              validId.value(),
              validName.value(),
              validSurname.value(),
              validEmail.value(),
              validActive.value()
      );
      Mockito.when(healthCareProfMapperMock.outputFromEntity(any(HealthCareProf.class)))
              .thenReturn(expectedOutput);

      // Act
      HealthCareProfOutputDTO outputDTO = createHealthCareProfUseCase.execute(validInputDTO);

      // Assert
      assertNotNull(outputDTO);
      assertNotNull(outputDTO.id());
      assertNotNull(outputDTO.name());
      assertNotNull(outputDTO.surname());
      assertNotNull(outputDTO.email());
      assertNotNull(outputDTO.active());
      assertEquals(validId.value(), outputDTO.id());
      assertEquals(outputDTO.id(),expectedOutput.id());
      assertEquals(expectedOutput.name(), outputDTO.name());
      assertEquals(expectedOutput.surname(), outputDTO.surname());
      assertEquals(expectedOutput.email(), outputDTO.email());
      assertEquals(expectedOutput.active(), outputDTO.active());
      // Additional assertions to check the values  
      // assertTrue(outputDTO.name().equals(validName.value()));
      // assertTrue(outputDTO.surname().equals(validSurname.value()));
      // assertTrue(outputDTO.email().equals(validEmail.value()));
      // assertTrue(outputDTO.active().equals(validActive.value()));
    }

  @Test
    void shouldThrowExceptionWhenNameInputIsInvalid() throws BusinessException {
      // Arrange: create an invalid input DTO
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("", "", ""); // Empty fields
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) { 
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());  
    }
  @Test
    void shouldThrowExceptionWhenSurNameInputIsInvalid() throws BusinessException {
      // Arrange: create an invalid input DTO
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("Valid Name", "","Valid email"); // Empty fields
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) { 
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Surname cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());  
    }    
  @Test
    void shouldThrowExceptionWhenEmailInputIsInvalid() throws BusinessException {
      // Arrange: create an invalid input DTO
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("Valid Name", "Valid Surname", ""); // Empty email
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Email cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenEmailInputIsInvalidFormat() throws BusinessException {
      // Arrange: create an invalid input DTO with an invalid email format
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("Valid Name", "Valid Surname", "invalid-email-format"); // Invalid email format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Email format is not valid.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_Numbers() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("123", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_Symbols() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("Invalid & Name", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_IsShort() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("X", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }    
   @Test
    void shouldThrowExceptionWhenEmailInputIsAlreadyExists() throws BusinessException {
      // Arrange: create an input DTO with an email that already exists
      String existingEmail = validEmail.value(); // Use the valid email defined earlier
      CreateHealthCareProfInputDTO inputDTO = new CreateHealthCareProfInputDTO("Valid Name", "Valid Surname", existingEmail); // Existing email  
      Mockito.when(healthCareProfRepositoryMock.findByEmail(any(HealthCareProfEmail.class)))
              .thenReturn(Optional.of(healthCareProfMock)); // Simulate existing email
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createHealthCareProfUseCase.execute(inputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("There is already a Medical Sales Representative with this email.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsNull() throws BusinessException {
      // Act & Assert: expect a DomainException to be thrown when input DTO is null
      try {
        createHealthCareProfUseCase.execute(null);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Input DTO cannot be null", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsEmpty() throws BusinessException {
      // Arrange: create an empty input DTO
      CreateHealthCareProfInputDTO emptyInputDTO = new CreateHealthCareProfInputDTO("", "", ""); // Empty fields
      // Act & Assert: expect a DomainException to be thrown
      try {
        createHealthCareProfUseCase.execute(emptyInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsInvalid() throws BusinessException {
      // Arrange: create an input DTO with invalid data
      CreateHealthCareProfInputDTO invalidInputDTO = new CreateHealthCareProfInputDTO("", "Valid Surname", "invalid-email-format"); // Empty name and invalid email format
      // Act & Assert: expect a DomainException to be thrown
      try {
        createHealthCareProfUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(healthCareProfFactoryMock, times(0)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenFactoryThrowsBusinessException() throws BusinessException {
      // Arrange: stub the factory to throw a BusinessException
      Mockito.when(healthCareProfFactoryMock.createHealthCareProf(
              any(HealthCareProfName.class),
              any(HealthCareProfName.class),
              any(HealthCareProfEmail.class)
      )).thenThrow(new BusinessException("Factory error"));
      // Act & Assert: expect a DomainException to be thrown
      try {
        createHealthCareProfUseCase.execute(validInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Factory error", ex.getMessage()); // Adjust the message based on your implementation  
      } 
      verify(healthCareProfFactoryMock, times(1)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
      verify(healthCareProfRepositoryMock, times(0)).save(any());    
    }
    // @Test
    // void shouldThrowExceptionWhenRepositoryThrowsBusinessException() throws BusinessException {
    //   // Arrange: stub the repository to throw a BusinessException
    //   Mockito.doThrow(new BusinessException("Repository error"))
    //           .when(healthCareProfRepositoryMock).save(any(HealthCareProf.class));
    //   // Act & Assert: expect a DomainException to be thrown
    //   try {
    //     createHealthCareProfUseCase.execute(validInputDTO);
    //   } catch (DomainException ex) {
    //     assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
    //     assertEquals("Repository error", ex.getMessage()); // Adjust the message based on your implementation
    //   }
    //   verify(healthCareProfFactoryMock, times(1)).createHealthCareProf(any(HealthCareProfName.class), any(HealthCareProfName.class), any(HealthCareProfEmail.class));
    //   verify(healthCareProfRepositoryMock, times(1)).save(any(HealthCareProf.class)); 
    // }

 }  

