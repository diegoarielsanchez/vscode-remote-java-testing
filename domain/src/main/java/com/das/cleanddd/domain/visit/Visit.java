package com.das.cleanddd.domain.visit;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.das.cleanddd.domain.share.AggregateRoot;
import com.das.cleanddd.domain.share.Identifier;

public final class Visit extends AggregateRoot {

    private Identifier _id;
    private LocalDate _visitDate;
    private MedicalSalesRepresentative _medicalSalesRepresentative;
    //private final Set<ShoppingItem> shoppingItems = new LinkedHashSet<>();

    public Visit(Identifier id, LocalDate visitDate, MedicalSalesRepresentative medicalSalesRepresentative) {
        this._id       = id;
        this._visitDate = visitDate;
        this._medicalSalesRepresentative = medicalSalesRepresentative;
    }

    private Visit() {
        _id       = null;
        _visitDate = null;
        _medicalSalesRepresentative = null;
    }

    public Identifier id() {
        return _id;
    }

    public LocalDate visitDate() {
        return _visitDate;
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visit visit = (Visit) o;
        return _id.equals(visit._id) &&
               _visitDate.equals(visit._visitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _visitDate);
    }


}
