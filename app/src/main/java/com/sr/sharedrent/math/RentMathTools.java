package com.sr.sharedrent.math;

public class RentMathTools {
    private final GaussianElimination gaussianElimination;

    public RentMathTools() {
        gaussianElimination = new GaussianElimination();
    }

    public static double cleanUpMoneyString(String s) {
        double res =  Double.parseDouble(s.replaceAll("[^0-9\\,\\.\\-]","").replace(",","."));
        return res;
    }

    public static double cleanUpAreaString(String s) {
        double res =  Double.parseDouble(s.replaceAll("[^0-9\\,\\.]","").replace(",","."));
        return res;
    }

    static double[] oneDimIntToDouble(int[] m) {
        double[] res = new double[m.length];
        for (int j = 0; j < m.length; j++) {
            res[j] = (double)m[j];
        }
        return res;
    }

    public static double[][] twoDimIntToDouble(int[][] m) {
        double[][] res = new double[m.length][m[0].length];
        for (int j = 0; j < m.length; j++) {
            res[j] = oneDimIntToDouble(m[j]);
        }
        return res;
    }

}
