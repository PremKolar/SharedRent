package com.example.sharedrent.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharedrent.models.Flat;
import com.example.sharedrent.models.LandLord;
import com.example.sharedrent.models.LivingArea;
import com.example.sharedrent.models.Money;
import com.example.sharedrent.models.ShareMode;
import com.example.sharedrent.models.Tenant;
import com.example.sharedrent.math.RentMathTools;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SharedRentViewModel extends ViewModel {

    private Application app;
    private SharedRentDao dao;
    public LiveData<List<Tenant>> getAllTenantsRaw;
    public LiveData<List<Flat>> getAllFlats;
    private Flat currentFlat;
    private LandLord landLord;

    public void init(Application application){
        app = application;
        dao = DataBase.getDataBase(application).sharedRentDao();
        getAllFlats = dao.getAllFlats();
        getAllTenantsRaw = dao.getAllTenants();
        landLord = new LandLord(getAllFlats,getAllTenantsRaw);
    }

    private void cleanUpDataBase() throws InterruptedException {
        while (getAllTenantsRaw.getValue() == null || getAllFlats.getValue() == null){
            Thread.sleep(1);
        }

        for (Tenant tenant: getAllTenantsRaw.getValue()) {
            if (landLord.getFlatForTenant(tenant) == null){
                deleteTenant(tenant);
            }
        }
    }


    public Flat getCurrentFlat() {
        return currentFlat;
    }

    public void tryToSetCurrentFlat() {
        if (getAllFlats.getValue() == null || getAllFlats.getValue().size()<1) return;
        setCurrentFlat(getAllFlats.getValue().get(0));
    }

    public void setCurrentFlat(Flat flat) {
        currentFlat = flat;
    }


    private void insertFlat(Flat flat) {
        AsyncTask.execute(()->dao.insertFlat(flat));
    }

    private void insertTenant(Tenant tenant){
        AsyncTask.execute(()->dao.insertTenant(tenant));
    }

    private void updateFlat(Flat flat) {
        AsyncTask.execute(()->dao.update(flat));
    }

    public void deleteTenant(Tenant tenant) {
        AsyncTask.execute(()->deleteTenantAsync(tenant));
    }
    private void deleteTenantAsync(Tenant tenant) {
        landLord.moveTenantOut(tenant);
        dao.deleteTenant(tenant);
        landLord.recalc();
    }

    private void deleteFlat(Flat flat) {
        for (Tenant tenant:landLord.getAllTenantsForFlat(flat)) {
            deleteTenant(tenant);
        }
        AsyncTask.execute(()->dao.deleteFlat(flat));
    }

    public void deleteCurrentFlat(){
        deleteFlat(currentFlat);
    }

    public List<Tenant> makeTenantList() {
        if (currentFlat == null) tryToSetCurrentFlat();
        if (currentFlat == null) return null;
        landLord.recalc();
        return landLord.getAllTenantsForFlat(currentFlat);
    }

    private boolean flatIsReady(Flat flat) {
        if (flat == null) return false;
        if (anyOfTheFlatsTenantsIsNull(flat)) return false;
        return true;
    }

    private boolean tenantIsReady(Tenant tenant) {
        Flat flat = landLord.getFlatForTenant(tenant);
        if (flat == null) return false;
        boolean ok = flat != null;
        if (anyOfTheFlatsTenantsIsNull(flat)) return false;
        return ok;
    }


    boolean anyOfTheFlatsTenantsIsNull(Flat flat) {
        for (String tenantName:flat.tenants_names) {
            if (landLord.findTenantByName(tenantName) == null) return true;
        }
        return false;
    }

    public void addTenantWithName(String nameForNewTenant) {
        Tenant newTenant = createNewTenantFromName(nameForNewTenant);
        if (newTenant == null) return;
        insertTenant(newTenant);
        updateFlat(currentFlat);
    }

    @Nullable
    private Tenant createNewTenantFromName(String nameForNewTenant) {
        if (currentFlat == null) tryToSetCurrentFlat();
        if (currentFlat == null) return null;
        Tenant dummy = new Tenant(nameForNewTenant);
        landLord.moveNewTenantIntoFlat(currentFlat,dummy);
        return dummy;
    }


    public void addFlatWithName(String name) {
        Flat flat = new Flat(name);
        insertFlat(flat);
        currentFlat = flat;
    }

    public ArrayList<String> getAllFlatsNames() {
        return landLord.getAllFlatsNames();
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
            updateFlat(currentFlat);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean tryToSetCurrentLivingAreaByUserInput(String input) {
        try {
            LivingArea newArea = new LivingArea(RentMathTools.cleanUpAreaString(input));
            currentFlat.setLivingArea(newArea);
            updateFlat(currentFlat);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void updateTenant(Tenant tenant) {
        AsyncTask.execute(()->dao.update(tenant));
    }

    public void refresh() {
        landLord.recalc();
    }

    public void setShareMode(ShareMode mode) {
        landLord.setShareMode(mode);
    }
}