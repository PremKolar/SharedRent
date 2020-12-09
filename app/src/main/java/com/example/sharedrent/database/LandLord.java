package com.example.sharedrent.database;

import androidx.lifecycle.LiveData;

import com.example.sharedrent.Flat;
import com.example.sharedrent.LivingArea;
import com.example.sharedrent.Money;
import com.example.sharedrent.Tenant;
import com.example.sharedrent.math.RentMathTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LandLord {

    private final RentMathTools mathTools;
    private LiveData<List<Flat>> flats;
    private LiveData<List<Tenant>> tenants;

    public LandLord(LiveData<List<Flat>> liveFlats,LiveData<List<Tenant>> liveTenants){
        flats = liveFlats;
        tenants = liveTenants;
        mathTools = new RentMathTools();
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
        return tenantsForFlat;
    }

    public void moveNewTenantIntoFlat(Flat currentFlat, Tenant newTenant) {
        currentFlat.moveInTenant(newTenant);
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

    public double getLivingAreaFractionForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        return tenant.livingArea.divideBy(flat.getLivingArea());
    }

    public String getLivingAreaPercentFormattedForTenant(Tenant tenant){
        return String.format("%.1f%%", 100*getLivingAreaFractionForTenant(tenant));
    }

    private double getRelativeLivingAreaFractionForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        LivingArea allRoomsLivingArea = getAllTenantsLivingAreaForFlat(flat);
        LivingArea communalSpace = flat.getLivingArea().minus(allRoomsLivingArea);
        LivingArea communalSpaceShareForOneTenant = communalSpace.divideBy((double)flat.getNumberOfTenants());
        LivingArea totalLivingAreaForTenant = tenant.livingArea.plus(communalSpaceShareForOneTenant);
        return totalLivingAreaForTenant.divideBy(flat.getLivingArea());
    }

    private double getNormalizedRelativeLivingAreaFractionForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        return getRelativeLivingAreaFractionForTenant(tenant)*((double)flat.getNumberOfTenants());
    }


    public String getRelativeLivingAreaPercentFormattedForTenant(Tenant tenant){
        tenant.relativeLivingAreaPercentFormatted = String.format("%.1f%%", 100*getRelativeLivingAreaFractionForTenant(tenant));
        return tenant.relativeLivingAreaPercentFormatted;
    }

    public String getRentPercentFormattedForTenant(Tenant tenant){
        Flat flat = getFlatForTenant(tenant);
        tenant.rentPercentFormatted = String.format("%.1f%%",100*calcRentForTenant(tenant).dividedBy(flat.getRent()));
        return tenant.rentPercentFormatted;
    }

    public double getFractionOfIncomeForTenant(Tenant tenant) {
        Flat flat = getFlatForTenant(tenant);
        return tenant.getIncome().dividedBy(calcTotalIncomeOfFlat(flat));
    }

    public Map<Tenant, Money> calcAllRentsForFlat(Flat flat){
        List<Tenant> tenants = getAllTenantsForFlat(flat);
        setRelativeLivingAreaFractionsForTenants(tenants);
        Map<Tenant, Money> residueRents = mathTools.solveEqualResidueRents(tenants, flat);
        return residueRents;
    }

    private void setRelativeLivingAreaFractionsForTenants(List<Tenant> tenants) {
        for (Tenant tenant:tenants) {
            tenant.setRelativeLivingAreaFraction(getNormalizedRelativeLivingAreaFractionForTenant(tenant));
        }
    }

    // TODO: 06.12.20 wrong!
    public Money calcRentForTenant(Tenant tenant){
//        Flat flat = getFlatForTenant(tenant);
//        double fractionOfFlat = getRelativeLivingAreaFractionForTenant(tenant);
//        double fractionOfTotalIncome = getFractionOfIncomeForTenant(tenant);
//        double meanFraction = (fractionOfFlat + fractionOfTotalIncome)/2;

        // TODO: 06.12.20 switch on mode
        Money res = tenant.getEqualResidueRent();

//        Money res = flat.getRent().dividedBy(1/meanFraction);
        return res;
    }

    public String getRentFormattedForTenant(Tenant tenant){
        tenant.rentFormatted = calcRentForTenant(tenant).getFormatted();
        return tenant.rentFormatted;
    }

    // TODO: 01.12.20 rename all
    public void recalcForAllTenants(){
        for (Flat flat: flats.getValue()) {
            Map<Tenant, Money> equalResidueRents = calcAllRentsForFlat(flat);
            for (Tenant tenant:getAllTenantsForFlat(flat)) {
                if (getFlatForTenant(tenant) == null) continue;
                getRelativeLivingAreaFractionForTenant(tenant);
                tenant.setEqualResidueRent(equalResidueRents.get(tenant));
                getRentPercentFormattedForTenant(tenant);
                getRentFormattedForTenant(tenant);
            }
        }
    }



    public void recalc() {
        recalcForAllTenants();
    }
}