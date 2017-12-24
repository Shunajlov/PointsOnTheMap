package com.ihavenodomain.pointsonthemap.model.points;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

@Entity(tableName = "points")
public class Point implements ClusterItem {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    public int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    public String name;

    @SerializedName("lat")
    @Expose
    @ColumnInfo(name = "lat")
    public double lat;

    @SerializedName("lng")
    @Expose
    @ColumnInfo(name = "lng")
    public double lng;

    @SerializedName("categoryId")
    @Expose
    @ColumnInfo(name = "categoryId")
    public int categoryId;

    @Override
    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}