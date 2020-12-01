package com.example.sharedrent.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sharedrent.Flat;
import com.example.sharedrent.Tenant;

import java.util.List;

@Dao
public interface SharedRentDao {
//    @Query("SELECT * FROM tenant WHERE flat = :flatName")
//    List<Tenant> getAllTenantsLivingInFlat(String flatName);

    @Query("SELECT * FROM Flat")
    LiveData<List<Flat>> getAllFlats();

    @Query("SELECT * FROM tenants")
    LiveData<List<Tenant>> getAllTenants();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTenant(Tenant tenant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlat(Flat flat);

    @Update
    void update(Tenant tenant);

    @Update
    void update(Flat flat);

}
