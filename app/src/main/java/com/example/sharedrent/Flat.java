package com.example.sharedrent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashSet;

@Entity
public class Flat {
    @PrimaryKey @NonNull
    public String name = "";
    public Rent rent = new Rent(0);
    public LivingArea livingArea = new LivingArea(0);
    public HashSet<String> tenants_names = new HashSet<>();
    @Ignore
    public HashSet<Tenant> tenants = new HashSet<>();

    public Flat(String name){
        this.name = name;
    }

    public void setRent(Money rent) {
        this.rent.change(rent);
    }

    public LivingArea getLivingArea() {
        return livingArea;
    }

    public Money getRent() {
        return rent;
    }

    public String getName() {
        return name;
    }

    public void moveInTenant(Tenant tenant){
        tenants_names.add(tenant.getName());
        tenants.add(tenant);
        tenant.setFlat(this);
    }

    public void moveOutTenant(Tenant tenant){
        tenant.unsetFlat();
        tenants_names.remove(tenant.getName());
        tenants.remove(tenant);
    }

    public boolean tenantLivesHere(Tenant t) {
        return tenants_names.contains(t.getName());
    }

    public Money totalIncome() {
        Money total = new Money(0);
        for (Tenant t:tenants) {
              total.add(t.income);
        }
        return total;
    }

    public String getRentFormatted() {
        return getRent().getFormatted();
    }

    public String getLivingAreaFormatted() {
        return getLivingArea().getFormatted();
    }

    public void setLivingArea(LivingArea newArea) {
        livingArea = newArea;
    }

    public LivingArea getAllTenantsLivingArea() {
        LivingArea total = new LivingArea(0);
        for (Tenant t:tenants) {
            total.add(t.livingArea);
        }
        return total;
    }
}
