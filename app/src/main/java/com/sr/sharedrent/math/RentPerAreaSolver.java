package com.sr.sharedrent.math;

import com.sr.sharedrent.models.Flat;
import com.sr.sharedrent.models.Money;
import com.sr.sharedrent.models.Tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentPerAreaSolver extends RentSolver {
    @Override
    public Map<Tenant, Money> solve(List<Tenant> tenants, Flat flat) {
        Map<Tenant, Money> res = new HashMap<>();
        for (int i = 0; i < tenants.size(); i++) {
            Tenant tenant = tenants.get(i);
            Money rent = flat.getRent().times(getRentFactor(tenant));
            res.put(tenant,rent);
        }
        return res;
    }

    private double getRentFactor(Tenant tenant) {
        return tenant.getRelativeLivingAreaFraction();
    }
}
