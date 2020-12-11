package com.sr.sharedrent.math;

import com.sr.sharedrent.models.Flat;
import com.sr.sharedrent.models.Money;
import com.sr.sharedrent.models.Tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProportionalRentSolver extends RentSolver {

    @Override
    public Map<Tenant, Money> solve(List<Tenant> tenants, Flat flat) {
        Map<Tenant, Money> res = new HashMap<>();
        int summedWeightedIncomes = makeSummedWeightedIncomes(tenants);
        for (int i = 0; i < tenants.size(); i++) {
            Tenant tenant = tenants.get(i);
            Money rent = flat.getRent().times(getRentFactor(summedWeightedIncomes, tenant));
            res.put(tenant,rent);
        }
        return res;
    }

    private double getRentFactor(double summedWeightedIncomes, Tenant tenant) {
        return ((double)calcWeightedIncome(tenant))/ summedWeightedIncomes;
    }

    private int makeSummedWeightedIncomes(List<Tenant> tenants) {
        int res = 0;
        for (Tenant t:tenants) {
            res += calcWeightedIncome(t);
        }
        return res;
    }
}
