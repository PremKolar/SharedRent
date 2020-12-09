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
    }

    public void moveOutTenant(Tenant tenant){
        moveOutTenantByName(tenant.getName());
    }

    public void moveOutTenantByName(String tenantsName) {
        tenants_names.remove(tenantsName);
    }

    public boolean tenantLivesHere(Tenant t) {
        return tenants_names.contains(t.getName());
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

    public int getNumberOfTenants() {
        return tenants_names.size();
    }

}
