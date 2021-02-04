package com.example.edutrails.ui.discover;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.edutrails.ContentScreen;
import com.example.edutrails.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class DiscoverOverlay4 extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    public final int REQEST_CODE_PERMISSION = 111;
    private static final String TAG = com.example.edutrails.ui.startTour.StartTourFragment.class.getSimpleName();
    Marker dreiBild;
    Marker meditation, wegkapelleM, laPoete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_overlay4, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapViewOverlay4);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                createMarkers(mMap);
                zoomToMarker(mMap);

                googleMap = mMap;
                getMapStyle(googleMap);

                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext()
                        , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQEST_CODE_PERMISSION
                    );

                } else {
                    getCurrentLocation();
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(dreiBild))
                        {
                            discoverOverlay();
                        }
                        if (marker.equals(meditation))
                        {
                            discoverOverlay2();
                        }
                        if (marker.equals(wegkapelleM))
                        {
                            discoverOverlay3();
                        }
                        if (marker.equals(laPoete))
                        {
                            discoverOverlay4();
                        }
                        return false;
                    }
                });


                googleMap.setMyLocationEnabled(true);

                // Setting a click event handler for the map
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng latLng) {

                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(latLng);

                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        // Animating to the touched position
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                        // Placing a marker on the touched position
                        googleMap.addMarker(markerOptions);

                    }
                });
            }


        });

        return rootView;
    }

    private void createMarkers(GoogleMap mMap) {
        LatLng raumZurMeditation = new LatLng(48.421333, 9.955561);
        LatLng dreiBildsaeulen = new LatLng(48.421459, 9.956280);
        LatLng ulmerSpitze = new LatLng(48.421900, 9.956953);
        LatLng fourOpenRectangles = new LatLng(48.422235, 9.957506);
        LatLng wegkapelle = new LatLng(48.394776, 9.952822);
        LatLng klosterhof = new LatLng(48.396333, 9.954621);
        LatLng poete = new LatLng(48.423343, 9.952609);


        laPoete = mMap.addMarker(new MarkerOptions().position(poete).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("poete"));
        dreiBild = mMap.addMarker(new MarkerOptions().position(dreiBildsaeulen).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Drei Bildsäulen"));
        meditation = mMap.addMarker(new MarkerOptions().position(raumZurMeditation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Raum zur Meditation"));
        wegkapelleM =  mMap.addMarker(new MarkerOptions().position(wegkapelle).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Wegkapelle"));
        mMap.addMarker(new MarkerOptions().position(ulmerSpitze).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Ulmer Spitze"));
        mMap.addMarker(new MarkerOptions().position(fourOpenRectangles).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Four Open Rectangles"));

        mMap.addMarker(new MarkerOptions().position(klosterhof).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Klosterhof"));


    }

    private void zoomToMarker(GoogleMap mMap) {
        LatLng marker = new LatLng(48.423343, 9.952609);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 0050, null);
    }
    private void discoverOverlay2() {

        DiscoverOverlay2 nextFrag= new DiscoverOverlay2();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay2, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

    }


    private void discoverOverlay4() {

        DiscoverOverlay4 nextFrag= new DiscoverOverlay4();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay4, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

    }
    private void discoverOverlay3() {

        DiscoverOverlay3 nextFrag= new DiscoverOverlay3();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay3, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

    }
    private void discoverOverlay() {
        DiscoverOverlay nextFrag= new DiscoverOverlay();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay2, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    private void getMapStyle(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity().getApplicationContext(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    private void getCurrentLocation() {
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQEST_CODE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}