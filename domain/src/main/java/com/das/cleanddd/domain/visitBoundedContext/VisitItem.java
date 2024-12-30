package com.das.cleanddd.domain.visitBoundedContext;

import java.util.UUID;

import com.das.cleanddd.domain.shared.Identifier;

public class VisitItem {

    private final UUID _visitItemId = UUID.randomUUID();
    private Identifier _productId;
    private int _quantity;

    public void AddUnits(int units)
    {
        if (units < 0)
        {
            //throw new OrderingDomainException("Invalid units");
        }
        else
        {
            _quantity += units;
        }        
    }
}
