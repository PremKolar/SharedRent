package com.example.sharedrent.database;

import androidx.lifecycle.LiveData;

import com.example.sharedrent.Tenant;

import java.util.List;

public class SharedRentRepository {
    private final SharedRentDao dao;
    LiveData<List<Tenant>> tenants;

    public SharedRentRepository(SharedRentDao sharedRentDao){
        dao = sharedRentDao;
        tenants = dao.getAllTenants();
    }

}
