package com.ihavenodomain.pointsonthemap.model.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Example json result
 * {
 *  "pageSize": 1,
 *  "data": [
 *      {
 *      "id": 0,
 *      "name": "string",
 *      "lat": 0,
 *      "lng": 0,
 *      "categoryId": 0
 *      }
 *  ],
 *  "hasMorePages": true
 * }
 */
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