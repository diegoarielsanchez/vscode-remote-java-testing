package com.das.cleanddd.domain.medicalsalesrep.entities;

import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;

//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultMedicalSalesRep extends MedicalSalesRep {

  private final MedicalSalesRepId id;

  //@With(AccessLevel.PRIVATE)
  //private final String name;

  //@With(AccessLevel.PRIVATE)
  private final String email;

  //@With(AccessLevel.PRIVATE)
  //private final String surname;

  private final Boolean active;

  private final ValidationUtils validationUtils;

  protected DefaultMedicalSalesRep(MedicalSalesRepId id, String name, String surname, String email, Boolean isActive) throws BusinessException {
    super(id, name, surname, email, isActive);
    
    this.validationUtils = (new UtilsFactory()).getValidationUtils();

    this.id = MedicalSalesRepId.random();
    this.firstName = name;
    this.email = email;
    this.lastName = surname;
    this.active = isActive;
    //this.validate();
  }


  @Override
  public void validate() throws BusinessException {
    if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
    if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
    if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
    if(this.validationUtils.isNullOrEmpty(this.email)) throw new RequiredFieldException("email");
    //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
    //this.address.validate();
  }


  @Override
  public MedicalSalesRepId getId() {
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
    return this.active;
  }
  
  
  @Override
  public MedicalSalesRep changeName(String newName) throws BusinessException {
    DefaultMedicalSalesRep c = this.lastName.equals(newName) ? this : this.withName(newName);
    c.validate();
    return c;
  }

  @Override
  public MedicalSalesRep changeEmail(String newEmail) throws BusinessException {
    DefaultMedicalSalesRep c = this.email.equals(newEmail) ? this : this.withEmail(newEmail);
    c.validate();
    return c;
  }

  /*
  @Override
  public MedicalSalesRep changeAddress(Address newAddr) throws BusinessException {
    DefaultMedicalSalesRepve c = this.address.equals(newAddr) ? this : this.withAddress(newAddr); 
    c.validate();
    return c;
  }
  */

  @Override
  public MedicalSalesRep activate() {
    return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
  }

  @Override
  public MedicalSalesRep deactivate() {
    return this.active.equals(Boolean.FALSE) ? this : this.withActive(false);
  }
}
