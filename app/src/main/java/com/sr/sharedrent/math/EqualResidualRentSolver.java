package com.sr.sharedrent.math;

import com.sr.sharedrent.models.Flat;
import com.sr.sharedrent.models.Money;
import com.sr.sharedrent.models.Tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualResidualRentSolver extends RentSolver {
    @Override
    public Map<Tenant, Money> solve(List<Tenant> tenants, Flat flat) {
        Map<Tenant, Money> res = new HashMap<>();
        if (tenants.size()<1) return res;

        int s = tenants.size()+1;

        int[][] M = makeM(s);

        int[] b = makeRHSvector(tenants, flat, s);

        double[] v = solveM(M, b);

        fillMapWithResults(tenants, res, s, v);

        return res;
    }

    private double[] solveM(int[][] m, int[] b) {
        double[] v = GaussianElimination.lsolve(RentMathTools.twoDimIntToDouble(m),RentMathTools.oneDimIntToDouble(b));
        Money residueForAll = new Money((int) v[0]); // not needed...
        return v;
    }

    private int[] makeRHSvector(List<Tenant> tenants, Flat flat, int s) {
        int[] b = new int[s];
        b[0] = flat.getRent().getCents(); // R
        for (int j = 1; j < s; j++) {
            Tenant tenant = tenants.get(j - 1);
            b[j] = (int) (-calcWeightedIncome(tenant) * ((double)tenants.size()));
//            b[j] = (int) (-calcWeightedIncome(tenant));
        }
        return b;
    }

    private int[][] makeM(int s) {
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
        return M;
    }
}
