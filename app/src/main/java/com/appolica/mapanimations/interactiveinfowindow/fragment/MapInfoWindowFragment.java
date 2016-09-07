package com.appolica.mapanimations.interactiveinfowindow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.mapanimations.R;
import com.appolica.mapanimations.interactiveinfowindow.InfoWindowManager;
import com.appolica.mapanimations.interactiveinfowindow.customview.TouchInterceptFrameLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Bogomil Kolarov on 31.08.16.
 */
public class MapInfoWindowFragment extends Fragment {

    private GoogleMap googleMap;
    private OnMapReadyCallback onMapReadyCallback;
    private InfoWindowManager infoWindowManager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_map_infowindow, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoWindowManager =
                new InfoWindowManager(getChildFragmentManager());

        infoWindowManager.onParentViewCreated((TouchInterceptFrameLayout) view, savedInstanceState);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #googleMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            final SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

            mapFragment
                    .getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            if (onMapReadyCallback != null) {
                                onMapReadyCallback.onMapReady(googleMap);
                            }

                            MapInfoWindowFragment.this.googleMap = googleMap;
                            setUpMap();
                        }
                    });
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void setUpMap() {
        infoWindowManager.onMapReady(googleMap);
    }

    /*public void toggle(@NonNull final InfoWindow infoWindow, boolean animated) {
        infoWindowManager.toggle(infoWindow, animated);
    }

    public void show(@NonNull final InfoWindow infoWindow, boolean animated) {
        infoWindowManager.show(infoWindow, animated);
    }

    private void hide(@NonNull final InfoWindow infoWindow, boolean animated) {
        infoWindowManager.hide(infoWindow, animated);
    }
*/
    public InfoWindowManager getManager() {
        return infoWindowManager;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        infoWindowManager.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        infoWindowManager.onDestroy();
    }

    public void setWindowShowListener(InfoWindowManager.WindowShowListener windowShowListener) {
        infoWindowManager.setWindowShowListener(windowShowListener);
    }

    public void setOnMapReadyCallback(OnMapReadyCallback onMapReadyCallback) {
        this.onMapReadyCallback = onMapReadyCallback;
    }

    public void setOnMapClickListener(GoogleMap.OnMapClickListener onMapClickListener) {
        infoWindowManager.setOnMapClickListener(onMapClickListener);
    }

    public void setOnCameraIdleListener(GoogleMap.OnCameraIdleListener onCameraIdleListener) {
        infoWindowManager.setOnCameraIdleListener(onCameraIdleListener);
    }

    public void setOnCameraMoveStartedListener(GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener) {
        infoWindowManager.setOnCameraMoveStartedListener(onCameraMoveStartedListener);
    }

    public void setOnCameraMoveListener(GoogleMap.OnCameraMoveListener onCameraMoveListener) {
        infoWindowManager.setOnCameraMoveListener(onCameraMoveListener);
    }

    public void setOnCameraMoveCanceledListener(GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        infoWindowManager.setOnCameraMoveCanceledListener(onCameraMoveCanceledListener);
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        final SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(onMapReadyCallback);
    }
}
