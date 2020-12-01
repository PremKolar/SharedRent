package com.example.sharedrent;

public class LivingArea {
    private double msquared = 0;

    public LivingArea(double squareMeters) {
        msquared = squareMeters;
    }

    public String getFormatted() {
        return String.format("%.1f m²", msquared);
    }

    public double divideBy(LivingArea livingArea) {
        return (msquared) / (livingArea.msquared);
    }

    public double getMsquared() {
        return msquared;
    }

    public void add(LivingArea summand) {
        msquared += summand.msquared;
    }
}
