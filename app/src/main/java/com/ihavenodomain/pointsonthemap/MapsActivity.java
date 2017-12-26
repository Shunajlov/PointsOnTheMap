package com.ihavenodomain.pointsonthemap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.ihavenodomain.pointsonthemap.api.retrofit.ApiConnection;
import com.ihavenodomain.pointsonthemap.model.SpHelper;
import com.ihavenodomain.pointsonthemap.model.dp.MyDb;
import com.ihavenodomain.pointsonthemap.model.points.Point;
import com.ihavenodomain.pointsonthemap.model.points.PointsResult;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ClusterManager<Point> clusterManager;

    private MyDb db;

    private GoogleMap googleMap;

    private Integer lastPointId = null;

    private static boolean toastShown;

    static boolean loading;

    Subscription subscription;

    /**
     * Just for statistics
     */
    private int totalSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Using <fragment> in xml
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        clusterManager = new ClusterManager<>(this, this.googleMap);

        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);

        if (!SpHelper.pointSaved()) {
            // show map somewhere in Moscow
            LatLng moscow = new LatLng(55.746390, 37.624239);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, 10));
        } else {
            // show last saved map camera position
            CameraPosition pos = SpHelper.getMapPos();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
        }
        if (!loading) {
            loadOfflineMarkers();
            loadMarkers();
            loading = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleMap != null) {
            SpHelper.saveMapPos(googleMap.getCameraPosition());
        }
        App.activityPaused();
        subscription.unsubscribe(); // stop loading
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.activityPaused();
        subscription.unsubscribe(); // stop loading
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();
        loadMarkers(); // resume loading
    }

    /**
     * Load markers via local db
     */
    private void loadOfflineMarkers() {
        db = MyDb.getDb(this.getApplicationContext());
        ArrayList<Point> savedPoints = new ArrayList<>(db.getDao().getAllSavedPoints());
        if (!savedPoints.isEmpty()) {
            clusterManager.addItems(savedPoints);
            lastPointId = savedPoints.get(savedPoints.size() - 1).id;
        }
        totalSize = savedPoints.size();
    }

    /**
     * Load markers via rest api
     */
    private void loadMarkers() {
        Observable<PointsResult> result = ApiConnection.getInstance()
                .getApi().getPoints(lastPointId);

        subscription = result.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PointsResult>() {
                    @Override
                    public void onCompleted() {
                        loading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading = false;
                    }

                    @Override
                    public void onNext(PointsResult pointsResult) {
                        ArrayList<Point> points = new ArrayList<>(pointsResult.points);
                        for (int i = 0; i < points.size(); i++) {
                            double offset = i / 300d;
                            LatLng pos = points.get(i).getPosition();
                            double lat = pos.latitude + offset;
                            double lng = pos.longitude + offset;

                            points.get(i).lat = lat;
                            points.get(i).lng = lng;

                            lastPointId = points.get(i).id;
                        }
                        clusterManager.addItems(points);
                        db.getDao().insertAll(points); // insert data into db

                        totalSize += points.size();

                        if (points.size() > 0)
                            toast("Added elements: " + points.size()
                                    + ". Total: " + totalSize);

                        if (pointsResult.hasMorePages)
                            loadMarkers();
                        else if (!toastShown) {
                            toast("All elements loaded (" + totalSize + ")");
                            toastShown = true;
                        }
                    }
                });
    }

    private void toast(String msg) {
        if (App.isActivityVisible()) // don't show toast while activity is not foreground
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
