package com.cleanddd.domain.medicalsalesrep;

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

import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRep;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepActive;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepEmail;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepFactory;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepId;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepName;
import com.das.cleanddd.domain.medicalsalesrep.entities.MedicalSalesRepRepository;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.CreateMedicalSalesRepInputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepMapper;
import com.das.cleanddd.domain.medicalsalesrep.usecases.dtos.MedicalSalesRepOutputDTO;
import com.das.cleanddd.domain.medicalsalesrep.usecases.services.CreateMedicalSalesRepUseCase;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.DomainException;

class CreateMedicalSalesRepUseCaseTest {

    private MedicalSalesRepRepository medicalSalesRepRepositoryMock = mock(MedicalSalesRepRepository.class);
    private MedicalSalesRepFactory medicalSalesRepFactoryMock = mock(MedicalSalesRepFactory.class);
    private MedicalSalesRepMapper medicalSalesRepMapperMock = mock(MedicalSalesRepMapper.class);
    private CreateMedicalSalesRepUseCase createMedicalSalesRepUseCase = new CreateMedicalSalesRepUseCase(medicalSalesRepRepositoryMock,medicalSalesRepFactoryMock,medicalSalesRepMapperMock);

    private final MedicalSalesRep medicalSalesRepMock = mock(MedicalSalesRep.class, Mockito.RETURNS_DEEP_STUBS);

    //private final UUID validId = UUID.fromString("e3119506-030a-4877-a219-389ef21118a4");
    private final MedicalSalesRepId validId = MedicalSalesRepIdMother.random();
    //private final String validName = "Foo Bar";
    private final MedicalSalesRepName validName = MedicalSalesRepNameMother.random();
    private final MedicalSalesRepName validSurname = MedicalSalesRepNameMother.random();
    //private final String validEmail = "foo@bar.com";
    private final MedicalSalesRepEmail validEmail = new MedicalSalesRepEmail("foo@bar.com");
    //private final MedicalSalesRepEmail validEmail = MedicalSalesRepEmailMother.random();
    private final MedicalSalesRepActive validActive = MedicalSalesRepActiveMother.create(false);
    //private final CreateMedicalSalesRepInputDTO validInputDTO = new CreateMedicalSalesRepInputDTO(validId.toString(), validName.toString(), validSurname.toString(), validEmail.toString());
    private final CreateMedicalSalesRepInputDTO validInputDTO = new CreateMedicalSalesRepInputDTO(validName.toString(), validSurname.toString(), validEmail.toString());
    //private final MedicalSalesRepOutputDTO validOutputDto = new MedicalSalesRepOutputDTO(validId.value(), validName.value(), validSurname.value(), validEmail.value(), validActive.value());

    // void prepareStubs() throws BusinessException {
    //     //when(medicalSalesRepFactoryMock.createAddress(anyString(), anyString(), anyString(), anyString())).thenReturn(addressMock);
    //     //doReturn(medicalSalesRepMock).when(medicalSalesRepFactoryMock).createMedicalSalesRep(MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepEmailMother.random());
    //     doReturn(medicalSalesRepMock).when(medicalSalesRepFactoryMock).createMedicalSalesRep(MedicalSalesRepNameMother.random(),MedicalSalesRepNameMother.random(),MedicalSalesRepEmailMother.random());
    //     when(medicalSalesRepMapperMock.outputFromEntity(any())).thenReturn(validOutputDto);
    // }

    @BeforeEach
    void setUp() {
      // Reset mocks before each test
      medicalSalesRepRepositoryMock = mock(MedicalSalesRepRepository.class);
      medicalSalesRepFactoryMock = mock(MedicalSalesRepFactory.class);
      medicalSalesRepMapperMock = mock(MedicalSalesRepMapper.class);
      createMedicalSalesRepUseCase = new CreateMedicalSalesRepUseCase(medicalSalesRepRepositoryMock,medicalSalesRepFactoryMock,medicalSalesRepMapperMock);

      // // Set up default stubbing for factory and mapper to avoid null returns
      // Mockito.when(medicalSalesRepFactoryMock.createMedicalSalesRep(
      //         any(MedicalSalesRepName.class),
      //         any(MedicalSalesRepName.class),
      //         any(MedicalSalesRepEmail.class)
      // )).thenReturn(medicalSalesRepMock);

      // MedicalSalesRepOutputDTO expectedOutput = new MedicalSalesRepOutputDTO(
      //         validId.value(),
      //         validName.value(),
      //         validSurname.value(),
      //         validEmail.value(),
      //         validActive.value()
      // );
      // Mockito.when(medicalSalesRepMapperMock.outputFromEntity(any(MedicalSalesRep.class)))
      //         .thenReturn(expectedOutput);
    }

    @Test
    void shouldCreateMedicalSalesRepUseCase() {
      assertNotNull(createMedicalSalesRepUseCase);
    }
    @Test
    void shouldCreateMedicalSalesRepInputDTO() {
      assertNotNull(validInputDTO); 
    }

    @Test
    void shouldCreateMedicalSalesRepId() {
      assertNotNull(validId);
    }
    @Test
    void shouldCreateMedicalSalesRepName() {
      assertNotNull(validName);
    } 
    @Test
    void shouldCreateMedicalSalesRepSurname() {
      assertNotNull(validSurname);
    } 
    @Test
    void shouldCreateMedicalSalesRepEmail() {
      assertNotNull(validEmail);
    }   

    @Test
    void shouldCreateMedicalSalesRep() {
      assertNotNull(medicalSalesRepFactoryMock);
    }
/*     @Test
    void shouldCreateMedicalSalesRepRepository() {
      assertNotNull(medicalSalesRepRepositoryMock);
    } */
    @Test
    void shouldCreateMedicalSalesRepMapper() {
      assertNotNull(medicalSalesRepMapperMock);
    }
    @Test
    void shouldCallFactoriesAndDataAccess() throws BusinessException, DomainException {
      //prepareStubs();
      setUp();
      // Act: execute the use case with valid input
      createMedicalSalesRepUseCase.execute(validInputDTO);
      verify(medicalSalesRepFactoryMock, times(1)).createMedicalSalesRep( new MedicalSalesRepName(validInputDTO.name()), new MedicalSalesRepName(validInputDTO.surname()), new MedicalSalesRepEmail(validInputDTO.email()));
      verify(medicalSalesRepRepositoryMock, times(1)).save(any());
    }

    @Test
    void shouldCreateMedicalSales() throws DomainException, BusinessException {
      // Arrange: stub the mocks to return valid objects
      setUp();
      //MedicalSalesRep medicalSalesRep = mock(MedicalSalesRep.class);
      Mockito.when(medicalSalesRepFactoryMock.createMedicalSalesRep(
              any(MedicalSalesRepName.class),
              any(MedicalSalesRepName.class),
              any(MedicalSalesRepEmail.class)
      )).thenReturn(medicalSalesRepMock);

      MedicalSalesRepOutputDTO expectedOutput = new MedicalSalesRepOutputDTO(
              validId.value(),
              validName.value(),
              validSurname.value(),
              validEmail.value(),
              validActive.value()
      );
      Mockito.when(medicalSalesRepMapperMock.outputFromEntity(any(MedicalSalesRep.class)))
              .thenReturn(expectedOutput);

      // Act
      MedicalSalesRepOutputDTO outputDTO = createMedicalSalesRepUseCase.execute(validInputDTO);

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
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("", "", ""); // Empty fields
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) { 
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());  
    }
  @Test
    void shouldThrowExceptionWhenSurNameInputIsInvalid() throws BusinessException {
      // Arrange: create an invalid input DTO
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("Valid Name", "","Valid email"); // Empty fields
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) { 
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Surname cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());  
    }    
  @Test
    void shouldThrowExceptionWhenEmailInputIsInvalid() throws BusinessException {
      // Arrange: create an invalid input DTO
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("Valid Name", "Valid Surname", ""); // Empty email
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Email cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenEmailInputIsInvalidFormat() throws BusinessException {
      // Arrange: create an invalid input DTO with an invalid email format
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("Valid Name", "Valid Surname", "invalid-email-format"); // Invalid email format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Email format is not valid.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_Numbers() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("123", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_Symbols() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("Invalid & Name", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenNameInputIsInvalidFormat_IsShort() throws BusinessException {
      // Arrange: create an input DTO with an invalid name format
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("X", "Valid Surname", " validEmail"); // Invalid name format
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name must be between 2 and 100 characters long and contain only letters and spaces.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }    
   @Test
    void shouldThrowExceptionWhenEmailInputIsAlreadyExists() throws BusinessException {
      // Arrange: create an input DTO with an email that already exists
      String existingEmail = validEmail.value(); // Use the valid email defined earlier
      CreateMedicalSalesRepInputDTO inputDTO = new CreateMedicalSalesRepInputDTO("Valid Name", "Valid Surname", existingEmail); // Existing email  
      Mockito.when(medicalSalesRepRepositoryMock.findByEmail(any(MedicalSalesRepEmail.class)))
              .thenReturn(Optional.of(medicalSalesRepMock)); // Simulate existing email
      // Act & Assert: expect a BusinessException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(inputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("There is already a Medical Sales Representative with this email.", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsNull() throws BusinessException {
      // Act & Assert: expect a DomainException to be thrown when input DTO is null
      try {
        createMedicalSalesRepUseCase.execute(null);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Input DTO cannot be null", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsEmpty() throws BusinessException {
      // Arrange: create an empty input DTO
      CreateMedicalSalesRepInputDTO emptyInputDTO = new CreateMedicalSalesRepInputDTO("", "", ""); // Empty fields
      // Act & Assert: expect a DomainException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(emptyInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
  @Test
    void shouldThrowExceptionWhenInputDTOIsInvalid() throws BusinessException {
      // Arrange: create an input DTO with invalid data
      CreateMedicalSalesRepInputDTO invalidInputDTO = new CreateMedicalSalesRepInputDTO("", "Valid Surname", "invalid-email-format"); // Empty name and invalid email format
      // Act & Assert: expect a DomainException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(invalidInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Name cannot be null or empty", ex.getMessage()); // Adjust the message based on your implementation 
      }
      verify(medicalSalesRepFactoryMock, times(0)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());
    }
    @Test
    void shouldThrowExceptionWhenFactoryThrowsBusinessException() throws BusinessException {
      // Arrange: stub the factory to throw a BusinessException
      Mockito.when(medicalSalesRepFactoryMock.createMedicalSalesRep(
              any(MedicalSalesRepName.class),
              any(MedicalSalesRepName.class),
              any(MedicalSalesRepEmail.class)
      )).thenThrow(new BusinessException("Factory error"));
      // Act & Assert: expect a DomainException to be thrown
      try {
        createMedicalSalesRepUseCase.execute(validInputDTO);
      } catch (DomainException ex) {
        assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
        assertEquals("Factory error", ex.getMessage()); // Adjust the message based on your implementation  
      } 
      verify(medicalSalesRepFactoryMock, times(1)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
      verify(medicalSalesRepRepositoryMock, times(0)).save(any());    
    }
    // @Test
    // void shouldThrowExceptionWhenRepositoryThrowsBusinessException() throws BusinessException {
    //   // Arrange: stub the repository to throw a BusinessException
    //   Mockito.doThrow(new BusinessException("Repository error"))
    //           .when(medicalSalesRepRepositoryMock).save(any(MedicalSalesRep.class));
    //   // Act & Assert: expect a DomainException to be thrown
    //   try {
    //     createMedicalSalesRepUseCase.execute(validInputDTO);
    //   } catch (DomainException ex) {
    //     assertTrue(ex instanceof DomainException); // Ensure the exception is of type DomainException
    //     assertEquals("Repository error", ex.getMessage()); // Adjust the message based on your implementation
    //   }
    //   verify(medicalSalesRepFactoryMock, times(1)).createMedicalSalesRep(any(MedicalSalesRepName.class), any(MedicalSalesRepName.class), any(MedicalSalesRepEmail.class));
    //   verify(medicalSalesRepRepositoryMock, times(1)).save(any(MedicalSalesRep.class)); 
    // }

 }  

