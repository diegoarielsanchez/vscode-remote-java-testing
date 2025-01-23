package com.das.cleanddd.domain.medicalSalesRepresentative.entities;

import com.das.cleanddd.domain.shared.exceptions.BusinessException;

//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultMedicalSalesRepresentative extends MedicalSalesRepresentative {

  private final MedicalSalesRepresentativeId id;

  //@With(AccessLevel.PRIVATE)
  //private final String name;

  //@With(AccessLevel.PRIVATE)
  private final String email;

  //@With(AccessLevel.PRIVATE)
  //private final String surname;

  private final Boolean isActive;

  //private final ValidationUtils validationUtils;

  protected DefaultMedicalSalesRepresentative(String name, String surname, String email, Boolean isActive) throws BusinessException {
    super(name, surname, email, isActive);
    
    //this.validationUtils = (new UtilsFactory()).getValidationUtils();

    this.id = MedicalSalesRepresentativeId.random();
    this.firstName = name;
    this.email = email;
    this.lastName = surname;
    this.isActive = isActive;
    //this.validate();
  }

  /* 
  @Override
  public void validate() throws BusinessException {
    if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
    if(this.validationUtils.isNullOrEmpty(this.name)) throw new RequiredFieldException("name");
    if(this.validationUtils.isNullOrEmpty(this.email)) throw new RequiredFieldException("email");
    if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
    this.address.validate();
  }
  */

  @Override
  public MedicalSalesRepresentativeId getId() {
    return this.id;
  }

  @Override
  public String getFirstName() {
    return this.lastName;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public String email() {
    return this.email;
  }

  @Override
  public Boolean isActive() {
    return this.isActive;
  }
  
  /* 
  @Override
  public MedicalSalesRepresentative changeName(String newName) throws BusinessException {
    DefaultMedicalSalesRepresentative c = this.lastName.equals(newName) ? this : this.withName(newName);
    //c.validate();
    return c;
  }

  @Override
  public MedicalSalesRepresentative changeEmail(String newEmail) throws BusinessException {
    DefaultMedicalSalesRepresentative c = this.email.equals(newEmail) ? this : this.withEmail(newEmail);
    c.validate();
    return c;
  }

  @Override
  public MedicalSalesRepresentative changeAddress(Address newAddr) throws BusinessException {
    DefaultMedicalSalesRepresentative c = this.address.equals(newAddr) ? this : this.withAddress(newAddr); 
    c.validate();
    return c;
  }

  @Override
  public MedicalSalesRepresentative activate() {
    return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
  }

  @Override
  public MedicalSalesRepresentative deactivate() {
    return this.active.equals(Boolean.FALSE) ? this : this.withActive(false);
*/    
}
