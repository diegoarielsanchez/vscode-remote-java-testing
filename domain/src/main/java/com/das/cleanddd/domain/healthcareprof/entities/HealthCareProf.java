package com.das.cleanddd.domain.healthcareprof.entities;

import java.util.ArrayList;
import java.util.List;

import com.das.cleanddd.domain.shared.PersonJavaBean;
import com.das.cleanddd.domain.shared.UtilsFactory;
import com.das.cleanddd.domain.shared.ValidationUtils;
import com.das.cleanddd.domain.shared.exceptions.BusinessException;
import com.das.cleanddd.domain.shared.exceptions.RequiredFieldException;


public class HealthCareProf extends PersonJavaBean {

    private final HealthCareProfId id;
    private final transient HealthCareProfEmail    email;
    private final transient HealthCareProfActive active;
    private final transient ValidationUtils validationUtils;
    private final List<Specialty> specialties;

    public HealthCareProf(HealthCareProfId id
            , HealthCareProfName name
            , HealthCareProfName surname
            , HealthCareProfEmail email
            , HealthCareProfActive active
            , List<Specialty> specialties
        ) 
        {
        this.id      = id == null ? HealthCareProfId.random() : id;
        this.firstName    = name.toString();
        this.lastName = surname.toString();
        this.email   = email == null ? new HealthCareProfEmail("") : email;
        this.active =  active == null ? new HealthCareProfActive(false) : active;
        this.specialties = specialties == null ? null : List.copyOf(specialties);
        this.validationUtils = (new UtilsFactory()).getValidationUtils();
    }

    public HealthCareProfId id() {
        return this.id;
    }

    public HealthCareProfName getName() {
        return new HealthCareProfName(firstName) {
        } ;
    }

    public HealthCareProfName getSurname() {
        return new HealthCareProfName(lastName) {
        } ;
    }

    public HealthCareProfEmail getEmail() {
        return this.email;
    }

    public HealthCareProfActive getActive() {
        return this.active;
    }
    public Boolean isActive() {
        return this.active != null && Boolean.TRUE.equals(this.active.value()) ? Boolean.TRUE : Boolean.FALSE;
    }
    public HealthCareProfId getId() {
        return this.id;
    }
    public static HealthCareProf create(HealthCareProfId id, HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email, HealthCareProfActive active, List<Specialty> specialties) {
        return new HealthCareProf(id, name, surname, email, active, specialties);
    }

    public static HealthCareProf create(HealthCareProfName name, HealthCareProfName surname, HealthCareProfEmail email, List<Specialty> specialties) {
        return new HealthCareProf(null, name, surname, email, null, specialties);
    }
 
    public HealthCareProf changeName(HealthCareProfName newName) throws BusinessException {
        return new HealthCareProf(this.id, newName, new HealthCareProfName(this.lastName), this.email, this.active, this.specialties);
    }

    public HealthCareProf changeSurname(HealthCareProfName newSurname) throws BusinessException {
        return new HealthCareProf(this.id, new HealthCareProfName(this.firstName), newSurname, this.email, this.active, this.specialties);
    }

    public HealthCareProf changeEmail(HealthCareProfEmail newEmail) throws BusinessException {
        return new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), newEmail, this.active, this.specialties);
    }

    public HealthCareProf changeSpecialties(List<Specialty> newSpecialties) throws BusinessException {
        if (newSpecialties == null || newSpecialties.isEmpty()) {
            throw new RequiredFieldException("specialties");
        }

        List<Specialty> normalizedSpecialties = List.copyOf(newSpecialties);
        if (this.specialties != null && this.specialties.equals(normalizedSpecialties)) {
            return this;
        }

        return new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, this.active, normalizedSpecialties);
    }

    public HealthCareProf addSpecialty(Specialty specialty) throws BusinessException {
        if (specialty == null) {
            throw new RequiredFieldException("specialty");
        }

        List<Specialty> updatedSpecialties = this.specialties == null
                ? new ArrayList<>()
                : new ArrayList<>(this.specialties);

        if (updatedSpecialties.contains(specialty)) {
            return this;
        }

        updatedSpecialties.add(specialty);
        return new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, this.active, updatedSpecialties);
    }

    public HealthCareProf removeSpecialty(Specialty specialty) throws BusinessException {
        if (specialty == null) {
            throw new RequiredFieldException("specialty");
        }
        if (this.specialties == null || this.specialties.isEmpty()) {
            throw new RequiredFieldException("specialties");
        }

        List<Specialty> updatedSpecialties = new ArrayList<>(this.specialties);
        boolean removed = updatedSpecialties.remove(specialty);
        if (!removed) {
            return this;
        }
        if (updatedSpecialties.isEmpty()) {
            throw new RequiredFieldException("specialties");
        }

        return new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, this.active, updatedSpecialties);
    }

    public List<Specialty> getSpecialties() {
        return this.specialties;
    }

    public void validate() throws BusinessException {
        if(this.validationUtils.isNull(this.id)) throw new RequiredFieldException("id");
        if(this.validationUtils.isNullOrEmpty(this.firstName)) throw new RequiredFieldException("firstName");
        if(this.validationUtils.isNullOrEmpty(this.lastName)) throw new RequiredFieldException("lastName");
        if(this.validationUtils.isNullOrEmpty(this.email.toString())) throw new RequiredFieldException("email");
        if(this.specialties == null || this.specialties.isEmpty()) throw new RequiredFieldException("specialties");
        //if(this.validationUtils.isNull(this.address)) throw new RequiredFieldException("address");
        //this.address.validate();
        }

    public HealthCareProf setActivate() {
        return this.active != null && this.active.value() ? this : new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, new HealthCareProfActive(true), this.specialties);
    }
    public HealthCareProf setDeactivate() {
        return this.active != null && !this.active.value() ? this : new HealthCareProf(this.id, new HealthCareProfName(this.firstName), new HealthCareProfName(this.lastName), this.email, new HealthCareProfActive(false), this.specialties);
    }
}
