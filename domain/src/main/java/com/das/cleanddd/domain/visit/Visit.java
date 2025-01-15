package com.das.cleanddd.domain.visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import java.util.LinkedHashSet;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.das.cleanddd.domain.shared.AggregateRoot;
import com.das.cleanddd.domain.shared.Identifier;
import com.das.cleanddd.domain.shared.TextValueObject;

@Service
public final class Visit extends AggregateRoot {

    private VisitId _visitId;
    private LocalDate _visitDate;
    private Identifier _prescriberId;
    private TextValueObject _visitComments;
    private Identifier _medicalSalesRepresentativeId;
    private Identifier _visitSiteId;
    private final List<VisitItem> _visitItems = new ArrayList<>();
    //private final Set<ShoppingItem> shoppingItems = new LinkedHashSet<>();

    public Visit(VisitId visitId
        , LocalDate visitDate
        , Identifier prescriberId
        , TextValueObject visitComments
        , Identifier visitSiteId
        , List<VisitItem> visitItems
        ,  Identifier medicalSalesRepresentativeId) {

        this._visitId       = visitId;
        this._visitDate = visitDate;
        this._prescriberId = prescriberId;
        this._visitComments = visitComments;
        this._visitSiteId = visitSiteId;
        //this._visitItems = visitItems;
        this._medicalSalesRepresentativeId = medicalSalesRepresentativeId;
    }

    @SuppressWarnings("unused")
    private Visit() {
        _visitId       = null;
        _visitDate = null;
        _prescriberId = null;
        _visitComments = null;
        _visitSiteId = null;
        //_visitItems = null;
        _medicalSalesRepresentativeId = null;
    }

    public void addItem(VisitItem visitItem) {
        _visitItems.add(visitItem);
    }

    public void removeItem(VisitItem visitItem) {
        _visitItems.remove(visitItem);
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
