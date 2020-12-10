package com.example.sharedrent.database;

import androidx.lifecycle.LiveData;

import com.example.sharedrent.Flat;
import com.example.sharedrent.LivingArea;
import com.example.sharedrent.Money;
import com.example.sharedrent.ShareMode;
import com.example.sharedrent.Tenant;
import com.example.sharedrent.math.EqualResidualRentSolver;
import com.example.sharedrent.math.ProportionalRentSolver;
import com.example.sharedrent.math.RentMathTools;
import com.example.sharedrent.math.RentPerAreaSolver;
import com.example.sharedrent.math.RentSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class LandLord {

    private final RentMathTools mathTools;
    private LiveData<List<Flat>> flats;
    private LiveData<List<Tenant>> tenants;
    private ShareMode shareMode = ShareMode.PROPORTIONAL;

    public LandLord(LiveData<List<Flat>> liveFlats,LiveData<List<Tenant>> liveTenants){
        flats = liveFlats;
        tenants = liveTenants;
        mathTools = new RentMathTools();
    }

    public void setShareMode(ShareMode mode) {
        shareMode = mode;
    }

    public Flat getFlatForTenant(Tenant t) {
        for (Flat flat: flats.getValue()) {
            if (flat.tenantLivesHere(t)){
                return flat;
            }
        }
        return null;
    }

    public List<Tenant> getAllTenantsForFlat(Flat flat) {
        List<Tenant> tenantsForFlat = new ArrayList<Tenant>();
        if (tenants.getValue() == null) return null;
        for (Tenant tenant:tenants.getValue()) {
            if (flat.tenantLivesHere(tenant)) {
                tenantsForFlat.add(tenant);
                flat.moveInTenant(tenant);
            }
        }
        cleanOutOldTenants(flat, tenantsForFlat);
        //
        return tenantsForFlat;
    }

    private void cleanOutOldTenants(Flat flat, List<Tenant> tenantsForFlat) {
        HashSet cloned_set = new HashSet();
        cloned_set = (HashSet)flat.tenants_names.clone();
        for (Object tenantsName:cloned_set) {
            if (!isIn((String) tenantsName,tenantsForFlat)){
                flat.moveOutTenantByName((String) tenantsName);
            }
        }
    }

    private boolean isIn(String tenantsName, List<Tenant> tenantsForFlat) {
        for (int i = 0; i < tenantsForFlat.size(); i++) {
            if (tenantsForFlat.get(i).getName().equals(tenantsName)) return true;
        }
        return false;
    }

    public void moveNewTenantIntoFlat(Flat currentFlat, Tenant newTenant) {
        currentFlat.moveInTenant(newTenant);
    }


    public void moveTenantOut(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        flat.moveOutTenant(tenant);
    }

    public ArrayList<String> getAllFlatsNames() {
        ArrayList<String> out = new ArrayList();
        for (Flat flat: flats.getValue()) {
            out.add(flat.name);
        }
        return out;
    }

    public Money calcTotalIncomeOfFlat(Flat flat) {
        Money total = new Money(0);
        for (String tenantName: flat.tenants_names) {
            Tenant tenant = findTenantByName(tenantName);
            total.add(tenant.income);
        }
        return total;
    }

    public LivingArea getAllTenantsLivingAreaForFlat(Flat flat) {
        LivingArea total = new LivingArea(0);
        for (String tenantName: flat.tenants_names) {
            Tenant tenant = findTenantByName(tenantName);
            try{
                total.add(tenant.livingArea);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return total;
    }

    Tenant findTenantByName(String tenantName) {
        for (Tenant t: tenants.getValue()) {
            if (t.getName().equals(tenantName)){
                return t;
            }
        }
        return null;
    }

    private LivingArea getRelativeLivingAreaForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        LivingArea allRoomsLivingArea = getAllTenantsLivingAreaForFlat(flat);
        LivingArea communalSpace = flat.getLivingArea().minus(allRoomsLivingArea);
        LivingArea communalSpaceShareForOneTenant = communalSpace.divideBy((double)flat.getNumberOfTenants());
        LivingArea totalLivingAreaForTenant = tenant.livingArea.plus(communalSpaceShareForOneTenant);
        return totalLivingAreaForTenant;
    }

    private double getRelativeLivingAreaFractionForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        return getRelativeLivingAreaForTenant(tenant).divideBy(flat.getLivingArea());
    }

    private double getNormalizedRelativeLivingAreaFractionForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        return getRelativeLivingAreaFractionForTenant(tenant)*((double)flat.getNumberOfTenants());
    }

    public void calcRelativeLivingAreaPercentFormattedForTenant(Tenant tenant){
        tenant.setRelativeLivingAreaRatio(getRelativeLivingAreaFractionForTenant(tenant));
    }

    public void calcRelativeLivingAreaFormattedForTenant(Tenant tenant){
        tenant.setEffectiveLivingArea(getRelativeLivingAreaForTenant(tenant));
    }

    public void calcRentPercentFormattedForTenant(Tenant tenant){
        Flat flat = getFlatForTenant(tenant);
        tenant.rentPercentFormatted = String.format("%.1f%%",100*tenant.getRent().dividedBy(flat.getRent()));
    }

    public Map<Tenant, Money> calcAllRentsForFlat(Flat flat){
        RentSolver solver;
        List<Tenant> tenants = getAllTenantsForFlat(flat);
        setRelativeLivingAreaFractionsForTenants(tenants);

        switch (shareMode){
            case EQUALRESIDUALFUNDS:
                solver = new EqualResidualRentSolver();
                break;
            case PROPORTIONAL:
                solver = new ProportionalRentSolver();
                break;
            case AREAONLY:
                solver = new RentPerAreaSolver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + shareMode);
        }

        Map<Tenant, Money> rents = solver.solve(tenants, flat);
        return rents;
    }

    private void setRelativeLivingAreaFractionsForTenants(List<Tenant> tenants) {
        for (Tenant tenant:tenants) {
//            tenant.setRelativeLivingAreaFraction(getNormalizedRelativeLivingAreaFractionForTenant(tenant));
            tenant.setRelativeLivingAreaFraction(getRelativeLivingAreaFractionForTenant(tenant));
        }
    }

    public void recalcForAllTenants(){
        for (Flat flat: flats.getValue()) {
            Map<Tenant, Money> rents = calcAllRentsForFlat(flat);
            for (Tenant tenant:getAllTenantsForFlat(flat)) {
                if (getFlatForTenant(tenant) == null) {
                    moveTenantOut(tenant);
                    continue;
                };
                tenant.setRent(rents.get(tenant));
                // set some stuff for the UI on the Tenant objects
                calcRentPercentFormattedForTenant(tenant);
                calcRelativeLivingAreaPercentFormattedForTenant(tenant);
                calcRelativeLivingAreaFormattedForTenant(tenant);

            }
        }
    }



    public void recalc() {
        try {
            recalcForAllTenants();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}