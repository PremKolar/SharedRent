package com.example.sharedrent.math;

import com.example.sharedrent.Flat;
import com.example.sharedrent.Money;
import com.example.sharedrent.Tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentMathTools {
    private final GaussianElimination gaussianElimination;

    public RentMathTools() {
        gaussianElimination = new GaussianElimination();
    }

    public Map<Tenant, Money> solveEqualResidueRents(List<Tenant> tenants, Flat flat) {
        Map<Tenant, Money> res = new HashMap<Tenant, Money>();
        if (tenants.size()<1) return res;
        int s = tenants.size()+1;
        // fill first row of matrix
        int[][] M = new int[s][s];
        for (int i = 1; i < s; i++) {
            M[0][i] = 1;
        }
        // fill other lines
        for (int j = 1; j < s; j++) {
            M[j][0] = -1;
            for (int i = 1; i < s; i++) {
                M[j][i] = 0;
            }
            M[j][j] = -1;
        }
        // fill RHS vector
        int[] b = new int[s];
        b[0] = flat.getRent().getCents(); // R
        for (int j = 1; j < s; j++) {
            Tenant tenant = tenants.get(j - 1);
            b[j] = calcRHSforTenant(tenant);
        }
        // solve for vector
        double[] v = GaussianElimination.lsolve(twoDimIntToDouble(M),oneDimIntToDouble(b));
        Money residueForAll = new Money((int) v[0]);

        for (int i = 1; i < s; i++) {
            Money calcedRent = new Money((int) v[i]);
            res.put(tenants.get(i-1), calcedRent);
        }
        return res;
    }

    private double[] oneDimIntToDouble(int[] m) {
        double[] res = new double[m.length];
        for (int j = 0; j < m.length; j++) {
                res[j] = (double)m[j];
        }
        return res;
    }

    public double[][] twoDimIntToDouble(int[][] m) {
        double[][] res = new double[m.length][m[0].length];
        for (int j = 0; j < m.length; j++) {
            res[j] = oneDimIntToDouble(m[j]);
        }
        return res;
    }


    private int calcRHSforTenant(Tenant tenant) {
        return (int) (- tenant.getIncome().getCents() * tenant.getRelativeLivingAreaFraction());
    }
}
