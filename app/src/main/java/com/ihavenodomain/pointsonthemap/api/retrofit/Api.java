package com.ihavenodomain.pointsonthemap.api.retrofit;

import com.ihavenodomain.pointsonthemap.model.points.PointsResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    /**
     *
     * @param startFrom int ID между 1 и 2147483647
     * @return <p>объект, содержащий список точек, состоящий из
     * {@link com.ihavenodomain.pointsonthemap.model.points.Point}</p>
     */
    @GET("places")
    Observable<PointsResult> getPoints(@Query("startFrom") Integer startFrom);
}
