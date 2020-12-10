package com.example.sharedrent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sharedrent.math.RentMathTools;

@Entity(tableName = "tenants")
public class Tenant {

    // for UI
    public double relativeLivingAreaRatio;
    public String rentPercentFormatted;

    @PrimaryKey @NonNull
    public String name = "";
    public Money income = new Money(0);
    public LivingArea livingArea = new LivingArea(1);
    private Money rent;
    private double relativeLivingAreaFraction = 1;
    private LivingArea effectiveLivingArea;

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
        Money newIncome = new Money((int) (RentMathTools.cleanUpMoneyString(s)*100));
        setIncome(newIncome);
    }

    private void setIncome(Money m) {
        this.income = m;
    }

    public void setLivingAreaByString(String area) {
        LivingArea newArea = new LivingArea(RentMathTools.cleanUpAreaString(area));
        setLivingArea(newArea);
    }

    private void setLivingArea(LivingArea newArea) {
        livingArea = newArea;
    }

    public Money getRent() {
        return rent;
    }

    public double getRelativeLivingAreaFraction() {
        return relativeLivingAreaFraction;
    }

    public Money getFundsAfterRent(){
        return income.minus(rent);
    }
    public String getFundsAfterRentFormatted(){
        return getFundsAfterRent().getFormatted();
    }

    public LivingArea getEffectiveLivingArea(){
        return effectiveLivingArea;
    }


    public String getRelativeLivingAreaPercentFormatted(){
        return String.format("%.1f%%", 100* relativeLivingAreaRatio);
    }



    // TO BE SET BY LANDLORD -----------------------------------------------------------------------

    public void setRent(Money money) {
        rent = money;
    }

    public void setRelativeLivingAreaFraction(double rlaf) {
        relativeLivingAreaFraction = rlaf;
    }
    public void setRelativeLivingAreaRatio(double livingAreaRatio) {
        relativeLivingAreaRatio = livingAreaRatio;
    }
    public void setEffectiveLivingArea(LivingArea totalLivingArea) {
        effectiveLivingArea = totalLivingArea;
    }

    // ---------------------------------------------------------------------------------------------

}