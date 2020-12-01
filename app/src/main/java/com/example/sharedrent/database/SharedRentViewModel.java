package com.example.sharedrent.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharedrent.Flat;
import com.example.sharedrent.LivingArea;
import com.example.sharedrent.Money;
import com.example.sharedrent.Tenant;

import java.util.ArrayList;
import java.util.List;

public class SharedRentViewModel extends ViewModel {

    private Application app;
    private SharedRentDao dao;
    //    private final SharedRentRepository repo;
    public LiveData<List<Tenant>> getAllTenantsRaw;
    public LiveData<List<Flat>> getAllFlats;
    public Flat currentFlat;

    public void init(Application application){
        app = application;
        dao = DataBase.getDataBase(application).sharedRentDao();
//        repo = new SharedRentRepository(dao);
        getAllFlats = dao.getAllFlats();
        getAllTenantsRaw = dao.getAllTenants();
        tryToSetCurrentFlat();
//        if (getAllFlats.getValue() == null || getAllFlats.getValue().isEmpty()) buildDummyFlat();
    }

    private void tryToSetCurrentFlat() {
        if (getAllFlats.getValue() == null || getAllFlats.getValue().size()<1) return;
        currentFlat = getAllFlats.getValue().get(0);
    }

    private void insertFlat(Flat flat) {
        AsyncTask.execute(()->dao.insertFlat(flat));
    }

    public void insertTenant(Tenant tenant){
        AsyncTask.execute(()->dao.insertTenant(tenant));
    }

    public List<Tenant> getAllTenants(){
        List<Tenant> tenants = getAllTenantsRaw.getValue();
        if (tenants==null) return null;
        for (Tenant t: tenants) {
            Flat flat = getFlatForTenant(t);
            t.setFlat(flat);
        }
        return tenants;
    }

    private Flat getFlatForTenant(Tenant t) {
        for (Flat flat: getAllFlats.getValue()) {
            if (flat.tenantLivesHere(t)){
                return flat;
            }
        }
        return null;
    }

    public List<Tenant> getAllTenantsForFlat(Flat flat) {
        List<Tenant> tenants = new ArrayList<Tenant>();
        if (getAllTenants() == null) return null;
        for (Tenant tenant:getAllTenants()) {
            if (flat.tenantLivesHere(tenant)) {
                tenants.add(tenant);
                flat.moveInTenant(tenant); // make sure tenant objects are known to flat
            }
        }
        return tenants;
    }

    public List<Tenant> makeTenantList() {
        if (currentFlat == null) tryToSetCurrentFlat();
        if (currentFlat == null) return null;
        return getAllTenantsForFlat(currentFlat);
    }

    public void addTenantWithName(String nameForNewTenant) {
        if (currentFlat == null) tryToSetCurrentFlat();
        if (currentFlat == null) return;
        Tenant dummy = new Tenant(nameForNewTenant);
        currentFlat.moveInTenant(dummy);
        insertTenant(dummy);
    }

    public void addFlatWithName(String name) {
        Flat flat = new Flat(name);
        insertFlat(flat);
        currentFlat = flat;
    }

    public ArrayList<String> getAllFlatsNames() {
        ArrayList<String> out = new ArrayList();
        for (Flat flat: getAllFlats.getValue()) {
            out.add(flat.name);
        }
        return out;
    }

    public String getCurrentRentFormatted() {
        return currentFlat.getRentFormatted();
    }

    public String getCurrentLivingspaceFormatted() {
        return currentFlat.getLivingAreaFormatted();
    }

    public boolean ready() {
        return getAllFlats.getValue() != null && getAllTenantsRaw.getValue() != null && currentFlat != null;
    }

    public void setCurrentFlatByName(String choice) {
        for (Flat flat: getAllFlats.getValue()) {
            if (flat.getName().equals(choice)){
                currentFlat = flat;
                return;
            }
        }
    }

    public boolean tryToSetCurrentRentByUserInput(String input) {
        try {
            int cents = 100*Integer.parseInt(input.replace("€",""));
            Money newRent = new Money(cents);
            currentFlat.setRent(newRent);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean tryToSetCurrentLivingAreaByUserInput(String input) {
        try {
            double msq = Double.parseDouble(input.replaceAll("[^\\d.]", ""));
            LivingArea newArea = new LivingArea(msq);
            currentFlat.setLivingArea(newArea);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void updateTenant(Tenant tenant) {
        AsyncTask.execute(()->dao.update(tenant));
    }
}

