package com.example.sharedrent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Entity(tableName = "tenants")
public class Tenant {
    @PrimaryKey @NonNull
    public String name = "";
    public Money income = new Money(0);
    public LivingArea livingArea = new LivingArea(1);

    public String relativeLivingAreaPercentFormatted;
    public String rentPercentFormatted;
    public String rentFormatted;
    private Money equalResidueRent;
    private double relativeLivingAreaFraction = 1;

    public Tenant(String name) {
        this.name = name;
    }

    public Money getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public String getIncomeFormatted() {
        return income.getFormatted();
    }

    public String getLivingAreaFormatted(){
        return livingArea.getFormatted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return name.equals(tenant.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIncomeByString(String s) {
        try {
            Money newIncome = new Money(100*parse(s, Locale.FRANCE));
            setIncome(newIncome);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // TODO: 28.11.20 to tools class
    private int parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return format.parse(amount.replaceAll("[^\\d.,]","")).intValue();
    }

    private void setIncome(Money m) {
        this.income = m;

    }

    public void setLivingAreaByString(String area) {
        double darea = Double.parseDouble(area.replaceAll("[^\\d.]", ""));
        LivingArea newArea = new LivingArea(darea);
        setLivingArea(newArea);
    }

    private void setLivingArea(LivingArea newArea) {
        livingArea = newArea;
    }

    public void setEqualResidueRent(Money money) {
        equalResidueRent = money;
    }

    public Money getEqualResidueRent() {
        return equalResidueRent;
    }

    // to be set by landlord
    public void setRelativeLivingAreaFraction(double rlaf) {
        relativeLivingAreaFraction = rlaf;
    }

    public double getRelativeLivingAreaFraction() {
        return relativeLivingAreaFraction;
    }
}