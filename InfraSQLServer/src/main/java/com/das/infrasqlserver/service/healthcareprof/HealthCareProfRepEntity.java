package com.das.infrasqlserver.service.healthcareprof;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "health_care_profs")
public class HealthCareProfRepEntity {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private Boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "health_care_prof_specialties", joinColumns = @JoinColumn(name = "health_care_prof_id"))
    private List<SpecialtyEmbeddable> specialties = new ArrayList<>();

    // Default constructor
    public HealthCareProfRepEntity() {}

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<SpecialtyEmbeddable> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<SpecialtyEmbeddable> specialties) {
        this.specialties = specialties;
    }

    @Embeddable
    public static class SpecialtyEmbeddable {

        private String code;
        private String specialtyName;

        public SpecialtyEmbeddable() {}

        public SpecialtyEmbeddable(String code, String specialtyName) {
            this.code = code;
            this.specialtyName = specialtyName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSpecialtyName() {
            return specialtyName;
        }

        public void setSpecialtyName(String specialtyName) {
            this.specialtyName = specialtyName;
        }
    }
}
