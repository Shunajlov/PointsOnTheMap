package com.ihavenodomain.pointsonthemap.model.dp;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ihavenodomain.pointsonthemap.model.points.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object
 */
@android.arch.persistence.room.Dao
public interface Dao {
    @Insert
    void insert(Point point);

    @Insert
    void insertAll(ArrayList<Point> points);

    @Query("SELECT * FROM points ORDER BY id ASC")
    List<Point> getAllSavedPoints();
}
