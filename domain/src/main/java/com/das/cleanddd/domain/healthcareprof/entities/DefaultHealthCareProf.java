package com.das.cleanddd.domain.healthcareprof.entities;

import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;


//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.With;

//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultHealthCareProf extends HealthCareProf {

  private final HealthCareProfId id;

  //@With(AccessLevel.PRIVATE)
  //private final String name;

  //@With(AccessLevel.PRIVATE)
  private final transient HealthCareProfEmail email;

  //@With(AccessLevel.PRIVATE)
  //private final String surname;

  private final transient HealthCareProfActive active;

  private final transient ValidationUtils validationUtils;

  protected DefaultHealthCareProf(HealthCareProfId id, HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email) throws BusinessException {
    super(id, name, surname, email, new HealthCareProfActive(false));
    
    this.validationUtils = (new UtilsFactory()).getValidationUtils();

    this.id = id != null ? id : HealthCareProfId.random();
    this.firstName    = name.toString();
    this.lastName = surname.toString();
    this.email   = email;
    this.active = new HealthCareProfActive(false);
    this.validate();
  }


  @Override
  public void validate() throws BusinessException {
    if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
    if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
    if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
    if(this.validationUtils.isNullOrEmpty(this.email.toString())) throw new RequiredFieldException("email");
    if(this.validationUtils.isNull(this.active)) throw new RequiredFieldException("active");

    //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
    //this.address.validate();
  }


  @Override
  public HealthCareProfId getId() {
    return this.id;
  }

  @Override
  public String getFirstName() {
    return  this.firstName;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public HealthCareProfEmail getEmail() {
    return this.email;
  }

  @Override
  public HealthCareProfActive getActive() {
    return this.active;
  }
  
  
  //@Override
/*   public HealthCareProf changeName(String newName) throws BusinessException {
    DefaultHealthCareProf c = this.lastName.equals(newName) ? this : this.withName(newName);
    //DefaultHealthCareProf c = this.lastName.equals(newName) ? this : this.withName(newName);
    c.validate();
    return c;
  }
 */
  //@Override
/*   public HealthCareProf changeEmail(String newEmail) throws BusinessException {
    DefaultHealthCareProf c = this.email.equals(newEmail) ? this : this.withEmail(newEmail);
    c.validate();
    return c;
  }
 */
  /*
  @Override
  public HealthCareProf changeAddress(Address newAddr) throws BusinessException {
    DefaultHealthCareProfve c = this.address.equals(newAddr) ? this : this.withAddress(newAddr); 
    c.validate();
    return c;
  }
  */

  //@Override
/*   public HealthCareProf activate() {
    //return this.active.equals(Boolean.TRUE) ? this : this.withActive(true);
    return this.active.equals(Boolean.TRUE) ? this : this.activate = true;
  }
 */
  //@Override
/*   public HealthCareProf deactivate() {
    return this.active.equals(Boolean.FALSE) ? this : this.withActive(false);
  } */
}
