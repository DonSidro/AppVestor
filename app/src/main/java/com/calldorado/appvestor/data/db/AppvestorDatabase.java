package com.calldorado.appvestor.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.calldorado.appvestor.data.GraphItemDao;
import com.calldorado.appvestor.data.db.entity.GraphItem;
import com.calldorado.appvestor.data.db.entity.Investments;
import com.calldorado.appvestor.data.InvestmentDao;

@Database(entities = {Investments.class, GraphItem.class}, version = 1, exportSchema = false)
public abstract class AppvestorDatabase extends RoomDatabase {

    private static final String TAG = "AppvestorDatabase";
    private static AppvestorDatabase instance;



    public abstract InvestmentDao investmentDao();
    public abstract GraphItemDao graphItemDao();

    private static final  Object sLock = new Object();

    public static AppvestorDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppvestorDatabase.class, "Appvestor.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return instance;
        }
    }

}
