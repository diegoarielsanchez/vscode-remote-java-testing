package com.das.cleanddd.domain.visitBoundedContext;

import java.time.LocalDate;
//import java.util.LinkedHashSet;
import java.util.Objects;
//import java.util.Set;

import com.das.cleanddd.domain.shared.AggregateRoot;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.TextValueObject;

public final class Visit extends AggregateRoot {

    private Identifier _visitId;
    private LocalDate _visitDate;
    private Identifier _prescriberId;
    private TextValueObject _visitComments;
    private Identifier _medicalSalesRepresentativeId;
    private Identifier _visitSiteId;
    //private final Set<ShoppingItem> shoppingItems = new LinkedHashSet<>();

    public Visit(Identifier visitId
        , LocalDate visitDate
        , Identifier prescriberId
        , TextValueObject visitComments
        , Identifier visitSiteId
        ,  Identifier medicalSalesRepresentativeId) {

        this._visitId       = visitId;
        this._visitDate = visitDate;
        this._prescriberId = prescriberId;
        this._visitComments = visitComments;
        this._visitSiteId = visitSiteId;
        this._medicalSalesRepresentativeId = medicalSalesRepresentativeId;
    }

    private Visit() {
        _visitId       = null;
        _visitDate = null;
        _prescriberId = null;
        _visitComments = null;
        _visitSiteId = null;
        _medicalSalesRepresentativeId = null;
    }

    public Identifier visitId() {
        return _visitId;
    }

    public Identifier prescriberId() {
        return _prescriberId;
    }

    public Identifier visitSideId() {
        return _visitSiteId;
    }

    public TextValueObject visitComments() {
        return _visitComments;
    }

    public LocalDate visitDate() {
        return _visitDate;
    }
    
    public Identifier medicalSalesRepresentativeId () {
        return _medicalSalesRepresentativeId;
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
        return _visitId.equals(visit._visitId) &&
               _visitDate.equals(visit._visitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_visitId, _visitDate);
    }


}
