package com.example.sharedrent.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sharedrent.models.Flat;
import com.example.sharedrent.models.LivingArea;
import com.example.sharedrent.models.Money;
import com.example.sharedrent.models.Tenant;

import kotlin.jvm.Volatile;

@Database(entities = {Tenant.class, Flat.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {
    public abstract SharedRentDao sharedRentDao();
    @Volatile
    private static DataBase INSTANCE;

    public static synchronized DataBase getDataBase(Context context){
        DataBase tempInstance = INSTANCE;
        if(tempInstance!=null){
            return tempInstance;
        }
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {

            public void onCreate (SupportSQLiteDatabase db) {
                super.onCreate(db);
                Flat flat = new Flat("Flat 1");
                flat.setRent(new Money(100000));
                flat.setLivingArea(new LivingArea(80));
                Tenant joe = new Tenant("Joe");
                flat.moveInTenant(joe);
                AsyncTask.execute(()->getDataBase(context).sharedRentDao().insertFlat(flat));
                AsyncTask.execute(()->getDataBase(context).sharedRentDao().insertTenant(joe));
            }
        };

        DataBase db = Room.databaseBuilder(context,
                DataBase.class, "shared_rent_database_file").addCallback(rdc).fallbackToDestructiveMigration().build();

        INSTANCE = db;
        return db;

    }


}
