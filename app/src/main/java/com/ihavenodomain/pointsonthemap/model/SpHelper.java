package com.ihavenodomain.pointsonthemap.model;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ihavenodomain.pointsonthemap.App;

/**
 * Shared Preferences
 */
public class SpHelper {
    private final static String LAT = "lat";
    private final static String LNG = "lng";
    private final static String ZOOM = "zoom";
    private final static String POINT_SAVED = "pointSaved";

    public static void saveMapPos(CameraPosition pos) {
        double lat = pos.target.latitude;
        double lng = pos.target.longitude;
        float zoom = pos.zoom;

        App.EDITOR.putLong(LAT, Double.doubleToRawLongBits(lat)).apply();
        App.EDITOR.putLong(LNG, Double.doubleToRawLongBits(lng)).apply();
        App.EDITOR.putFloat(ZOOM, zoom).apply();

        App.EDITOR.putBoolean(POINT_SAVED, true).apply();
    }

    public static boolean pointSaved() {
        return App.SETTINGS.getBoolean(POINT_SAVED, false);
    }

    public static CameraPosition getMapPos() {
        LatLng centerPos = new LatLng(
                Double.longBitsToDouble(App.SETTINGS.getLong(LAT, 0)),
                Double.longBitsToDouble(App.SETTINGS.getLong(LNG, 0))
            );

        return new CameraPosition.Builder()
                .zoom(App.SETTINGS.getFloat(ZOOM, 5))
                .target(centerPos)
                .build();
    }
}
