package com.ihavenodomain.pointsonthemap.model.dp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ihavenodomain.pointsonthemap.model.points.Point;

@Database(entities = {Point.class}, version = 1)
public abstract class MyDb extends RoomDatabase {
    private static MyDb ourInstance;

    public static MyDb getDb(Context context) {
        if (ourInstance == null)
           ourInstance = Room.databaseBuilder(
                   context.getApplicationContext(),
                   MyDb.class,
                   "my_db").allowMainThreadQueries().build();
        return ourInstance;
    }

    public abstract Dao getDao();
}
