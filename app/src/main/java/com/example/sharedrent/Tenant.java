package com.example.sharedrent;

import android.os.Build;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = "tenants")
public class Tenant {
    @PrimaryKey @NonNull
    public String name = "";
    public Money income = new Money(0);
    public LivingArea livingArea = new LivingArea(1);
    public Flat flat;

    public Tenant(String name) {
        this.name = name;
    }

    public Money getIncome() {
        return income;
    }

    public Flat getFlat() {
        return flat;
    }



    @Ignore
    public Tenant(String name,Flat flat) {
        this.name = name;
        setFlat(flat);
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public String getName() {
        return name;
    }

    public String getIncomeFormatted() {
        return income.getFormatted();
    }
    public void setIncomeFormatted(String s) {
        setIncomeByString(s);
    }

    public String getLivingAreaFormatted(){
        return livingArea.getFormatted();
    }

    public String getLivingAreaPercentFormatted(){
        return String.format("%.1f%%", 100*getLivingAreaFraction());
    }
    public String getRelativeLivingAreaPercentFormatted(){
        return String.format("%.1f%%", 100*getRelativeLivingAreaFraction());
    }


    private double getLivingAreaFraction() {
        return livingArea.divideBy(flat.getLivingArea());
    }

    private double getRelativeLivingAreaFraction() {
        return livingArea.divideBy(flat.getAllTenantsLivingArea());
    }

    public String getRentFormatted(){
        return calcRent().getFormatted();
    }

    public String getRentPercentFormatted(){
        return String.format("%.1f%%",100*calcRent().dividedBy(flat.getRent()));
    }

    public Money calcRent(){
        double fractionOfFlat = getRelativeLivingAreaFraction();
        double fractionOfTotalIncome = getFractionOfIncome();
        double meanFraction = (fractionOfFlat + fractionOfTotalIncome)/2;
        Money res = flat.getRent().dividedBy(1/meanFraction);
        return res;
    }


    private double getFractionOfIncome() {
        return income.dividedBy(flat.totalIncome());
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

    public void unsetFlat() {
        flat = null;
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
}