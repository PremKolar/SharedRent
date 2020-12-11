package com.sr.sharedrent.models;

public class LivingArea {
    private double msquared = 0;

    public LivingArea(double squareMeters) {
        msquared = squareMeters;
    }

    // TODO: 11.12.20 locale
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

    public LivingArea divideBy(double divisor) {
        return new LivingArea(msquared/divisor);
    }

    public LivingArea minus(LivingArea otherLivingArea) {
        return new LivingArea(msquared - otherLivingArea.msquared);
    }

    public LivingArea plus(LivingArea otherLivingArea) {
        return new LivingArea(msquared + otherLivingArea.msquared);
    }
}
