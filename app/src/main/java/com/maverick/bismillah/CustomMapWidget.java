package com.maverick.bismillah;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.konylabs.android.KonyMain;
import com.konylabs.api.ui.KonyCustomWidget;

public class CustomMapWidget extends KonyCustomWidget implements GoogleMap.OnCameraChangeListener {

    private LinearLayout mLayout;
    private GoogleMap gMap;

    private int width;
    private int height;
    private float zoomLevel = 1;

    public void setGoogleMap(GoogleMap gMap) {
        this.gMap = gMap;
        init();
    }

    private void init() {
        if (gMap != null) {
            LatLng sydney = new LatLng(-34, 151);
            gMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney"));
            final CameraPosition cameraPosition = CameraPosition.builder()
                    .target(sydney)
                    .zoom(10)
                    .bearing(30)
                    .tilt(30)
                    .build();

            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    @Override
    public View onCreateView(Context context) {
        mLayout = new LinearLayout(context);
        mLayout.setId(R.id.main_layout);

        String widthParam = getPropertyFromModel("width").toString().replace("dp", "");
        String heightParam = getPropertyFromModel("height").toString().replace("dp", "");

        Log.d("MaverickCustomWidget", "### ID LinearLayout: " + R.id.main_layout);
        Log.d("MaverickCustomWidget", "### WidthParam: " + widthParam);
        Log.d("MaverickCustomWidget", "### HeightParam: " + heightParam);
        if (widthParam != null) {
            width = (int) (((double) (Integer.parseInt(widthParam)) / 100) * getDisplayWidth());
        }

        if (heightParam != null) {
            height = (int) (((double) (Integer.parseInt(heightParam)) / 100) * getDisplayHeight());
        }

        FragmentManager FM = KonyMain.getActContext()
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = FM.beginTransaction();
        CustomFragment mapFragment = new CustomFragment();
        mapFragment.setCustomMapWidget(this);
        fragmentTransaction.add(Math.abs(R.id.main_layout), mapFragment, String.valueOf(R.id.main_layout));
        fragmentTransaction.commitAllowingStateLoss();

        mLayout.setLayoutParams(new ViewGroup.LayoutParams(width, height));

        return mLayout;
    }

    @Override
    public void onDestroyView(View view) {
        mLayout = null;
        gMap = null;
    }

    @Override
    public void setWidth(int i) {

    }

    @Override
    public void setHeight(int i) {

    }

    private int getDisplayWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        KonyMain.getActContext().getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.widthPixels;
    }

    private int getDisplayHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        KonyMain.getActContext().getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.heightPixels;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }
}
