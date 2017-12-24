package com.ihavenodomain.pointsonthemap.model.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsResult {

    @SerializedName("pageSize")
    @Expose
    public int pageSize;
    @SerializedName("data")
    @Expose
    public List<Point> points = null;
    @SerializedName("hasMorePages")
    @Expose
    public boolean hasMorePages;

}