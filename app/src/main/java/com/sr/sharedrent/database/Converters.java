package com.sr.sharedrent.database;

import androidx.room.TypeConverter;

import com.sr.sharedrent.models.Flat;
import com.sr.sharedrent.models.LivingArea;
import com.sr.sharedrent.models.Money;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

public class Converters {

    @TypeConverter
    public static Money toMoney(int cents) {
        return new Money(cents);
    }

    @TypeConverter
    public static int fromMoney(Money money) {
        return money == null ? 0 : money.getCents();
    }


    @TypeConverter
    public static LivingArea toLivingArea(double msq){
        return new LivingArea(msq);
    }

    @TypeConverter
    public static double fromLivingArea(LivingArea la){
        return la == null ? 0 : la.getMsquared();
    }

    @TypeConverter
    public static String fromFlat(Flat flat){
        return flat == null ? "" : flat.getName();
    }

    @TypeConverter
    public  static Flat toFlat(String name){
        return null; // is set a posteriori programmatically
    }


    @TypeConverter
    public static HashSet<String> toHashSet(String value) {
        Type listType = new TypeToken<HashSet<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter   public static String fromHashSet(HashSet<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}