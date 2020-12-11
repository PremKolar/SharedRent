package com.sr.sharedrent.models;

public class Money {
    protected int cents = 0;
    public Money(int cents) {
        this.cents = cents;
    }

    // TODO: 11.12.20 locale
    public String getFormatted() {
        return String.format("%.2f €",((double)cents)/100.0);
    }

    public void change(Money rent) {
        cents = rent.cents;
    }

    public double dividedBy(Money divisor) {
        if (divisor==null) return 1;
        return (((double)cents) / ((double)divisor.cents));
    }

    public int getCents() {
        return cents;
    }

    public void add(Money summand) {
        cents += summand.cents;
    }

    public Money dividedBy(double v) {
        return new Money((int) (cents/v));
    }
    public Money times(double m) {
        return dividedBy(1/m);
    }


    public Object asDouble() {
        return ((double)cents)/100;
    }

    public Money minus(Money m2) {
        return new Money(cents - m2.cents);
    }
}
