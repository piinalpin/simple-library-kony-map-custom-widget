package com.maverick.bismillah;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.konylabs.android.KonyMain;

import java.lang.reflect.Method;

public class CustomFragment extends Fragment {

    private CustomMapView customMapView;
    private GoogleMap gMap;
    private CustomMapWidget customMapWidget;
    private RelativeLayout layout;

    public void setCustomMapWidget(CustomMapWidget customMapWidget) {
        this.customMapWidget = customMapWidget;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            Class classObject = Class
                    .forName("com.google.android.gms.maps.MapsInitializer");
            Method method = classObject.getMethod("initialize",
                    Context.class);
            method.invoke(classObject, getActivity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        customMapView = new CustomMapView(KonyMain.getActContext());
        customMapView.onCreate(savedInstanceState);

        layout = new RelativeLayout(KonyMain.getAppContext());
        customMapView.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        layout.addView(customMapView);

        View transparentView = new View(KonyMain.getAppContext());

        transparentView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        transparentView.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        layout.addView(transparentView);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        return layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        customMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        customMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customMapView.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                customMapWidget.setGoogleMap(gMap);
            }
        });

    }

}
