package com.happybaby.kcs.bd.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.happybaby.kcs.bd.room.dao.ShoppingCartDao;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;

@Database(entities = {ShoppingCartProduct.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingCartDao shoppingCartDao();

    private static AppDatabase appDataBase;

    public static AppDatabase getInstance(Context context){
        if(null == appDataBase){
            appDataBase = buildDataBaseInstance(context);
        }
        return appDataBase;
    }

    private static AppDatabase buildDataBaseInstance(Context context){
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "app_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
    }

    private void cleanUp(){
        appDataBase = null;
    }
}