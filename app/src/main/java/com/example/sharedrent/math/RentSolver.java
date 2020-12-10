package com.example.sharedrent.math;

import com.example.sharedrent.Flat;
import com.example.sharedrent.Money;
import com.example.sharedrent.Tenant;

import java.util.List;
import java.util.Map;

public abstract class RentSolver {

    public abstract Map<Tenant, Money> solve(List<Tenant> tenants, Flat flat);


    public void fillMapWithResults(List<Tenant> tenants, Map<Tenant, Money> res, int s, double[] v) {
        for (int i = 1; i < s; i++) {
            Money calcedRent = new Money((int) v[i]);
            res.put(tenants.get(i-1), calcedRent);
        }
    }

    protected int calcWeightedIncome(Tenant tenant) {
        return (int) ( tenant.getIncome().getCents() * tenant.getRelativeLivingAreaFraction());
    }
}
