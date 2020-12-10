package com.example.sharedrent;

public class Rent extends Money {
    public Rent(int cents) {
        super(cents);
    }

    public void change(Money rent) {
        cents = rent.cents;
    }
}
