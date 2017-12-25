package com.ihavenodomain.pointsonthemap;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ihavenodomain.pointsonthemap.model.dp.Dao;
import com.ihavenodomain.pointsonthemap.model.dp.MyDb;
import com.ihavenodomain.pointsonthemap.model.points.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MyDbTest {
    private Dao pointsDao;
    private MyDb database;

    @Before
    public void initDb() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                MyDb.class).build();
        pointsDao = database.getDao();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void writePointAndReadInList() throws Exception {
        Point point = new Point();
        point.id = 1;
        point.name = "testName";
        pointsDao.insert(point);
        List<Point> byId = pointsDao.getAllSavedPoints();
        assertThat(byId.get(0), equalTo(point));
    }
}
