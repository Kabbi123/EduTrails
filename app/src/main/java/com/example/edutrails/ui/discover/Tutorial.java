package com.example.edutrails.ui.discover;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.edutrails.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Tutorial extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    public final int REQEST_CODE_PERMISSION = 111;
    private static final String TAG = com.example.edutrails.ui.startTour.StartTourFragment.class.getSimpleName();
    Marker dreiBild;
    Marker meditation, wegkapelleM, laPoete, firstPOI, ulmerSpitze1, fourOpenRectangles1;

    LocationManager mLocationManager;
    Location currentLocation;
    double longitude, latitude;
    boolean isUlmerSpitzeClicked, isFourOpenRectanglesClicked, hasDialogPoppedUp;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start_tour, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        initVariables();


/**
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup1, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setElevation(20);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(popupView, 0, 0);

*/



        /**get location*/
        
        openDialog("Herzlich Willkommen bei Edutrails.", "Der nächst gelegene POI wurde markiert. Begebe dich zu ihm und entdecke dein erstes Ziel in der realen Welt.");


        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext()
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQEST_CODE_PERMISSION
            );

        } else {
            getCurrentLocation();
        }
        currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);




        /**get location**/

        final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext()
                        , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQEST_CODE_PERMISSION
                    );

                } else {
                    getCurrentLocation();
                }
                currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                longitude = currentLocation.getLongitude();
                latitude = currentLocation.getLatitude();

                Location dreiBildLocation = new Location("");
                dreiBildLocation.setLatitude(dreiBild.getPosition().latitude);
                dreiBildLocation.setLongitude(dreiBild.getPosition().longitude);

                showDistanceToMarker(currentLocation, dreiBild);
                if(currentLocation.distanceTo(dreiBildLocation) < 40 && !hasDialogPoppedUp){
                    openDialog("Herzlichen Glückwunsch!", "Sie haben das Tutorial erfolgreich abgeschlossen. Sie haben eine Medaille erhalten und können diese unter Errungenschaften einsehen.");
                    showDistanceToMarker(currentLocation, dreiBild);
                    hasDialogPoppedUp = true;
                }

                if(isUlmerSpitzeClicked){
                    isFourOpenRectanglesClicked = false;
                    showDistanceToMarker(currentLocation, ulmerSpitze1);
                }
                if(isFourOpenRectanglesClicked){
                    isUlmerSpitzeClicked = false;
                    showDistanceToMarker(currentLocation, fourOpenRectangles1 );
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };








        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, mLocationListener);



        // initialize map
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                createMarkers(mMap);
                zoomToUni(mMap);

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
                            showMarkerInfoInPopup(R.layout.fragment_dreibild_popup);
                        }
                        if (marker.equals(meditation))
                        {
                            showMarkerInfoInPopup(R.layout.fragment_meditation_popup);
                        }
                        if (marker.equals(wegkapelleM))
                        {
                            showMarkerInfoInPopup(R.layout.fragment_wegkapelle_popup);
                        }
                        if (marker.equals(fourOpenRectangles1))
                        {
                            isUlmerSpitzeClicked = false;
                            isFourOpenRectanglesClicked = true;
                            showDistanceToMarker(currentLocation, fourOpenRectangles1);
                        }
                        if(marker.equals(firstPOI)){
                            openDialog("Errungenschaft", "Glückwunsch, du hast deinen ersten POI Freigeschalten");
                        }
                        if(marker.equals(ulmerSpitze1)){
                            isFourOpenRectanglesClicked = false;
                            isUlmerSpitzeClicked = true;
                            showDistanceToMarker(currentLocation, ulmerSpitze1);
                        }
                        if(marker.equals(laPoete)){
                            showMarkerInfoInPopup(R.layout.fragment_lapoete_popup);
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

    private void showMarkerInfoInPopup(int fragment) {
        View popupView = LayoutInflater.from(getActivity()).inflate(fragment, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM|Gravity.LEFT, 0, 0);
    }

    private void openDialog(String title, String desc) {
        TutorialDialog tutorialDialog = new TutorialDialog(title, desc);
        tutorialDialog.show(getActivity().getSupportFragmentManager(), "adsfkljöasdöflk");
    }

    private static void showDistanceToMarker(Location currentLocation, Marker marker) {
        Log.i("Testoutput", "test5");
        Location markerLocation = new Location("");
        markerLocation.setLatitude(marker.getPosition().latitude);
        markerLocation.setLongitude(marker.getPosition().longitude);

        double distance = Math.floor(currentLocation.distanceTo(markerLocation));
        String dis = Integer.toString((int) distance);
        marker.setSnippet(dis + " m");
        marker.showInfoWindow();
    }

    private void initVariables() {
        hasDialogPoppedUp = false;
        isUlmerSpitzeClicked = false;
        isFourOpenRectanglesClicked = false;
    }




    private void createMarkers(GoogleMap mMap) {
        LatLng raumZurMeditation = new LatLng(48.421333, 9.955561);
        LatLng dreiBildsaeulen = new LatLng(48.421459, 9.956280);
        LatLng ulmerSpitze = new LatLng(48.421900, 9.956953);
        LatLng fourOpenRectangles = new LatLng(48.422235, 9.957506);
        LatLng wegkapelle = new LatLng(48.394776, 9.952822);
        LatLng klosterhof = new LatLng(48.396333, 9.954621);
        LatLng poete = new LatLng(48.423343, 9.952609);
        LatLng firstPOIT = new LatLng(48.423573, 9.956659);


        firstPOI = mMap.addMarker(new MarkerOptions().position(firstPOIT).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("FirstPOI"));
        laPoete = mMap.addMarker(new MarkerOptions().position(poete).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("poete"));
        dreiBild = mMap.addMarker(new MarkerOptions().position(dreiBildsaeulen).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Drei Bildsäulen"));
        meditation = mMap.addMarker(new MarkerOptions().position(raumZurMeditation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Raum zur Meditation"));
        wegkapelleM = mMap.addMarker(new MarkerOptions().position(wegkapelle).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Wegkapelle"));
        ulmerSpitze1 = mMap.addMarker(new MarkerOptions().position(ulmerSpitze).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Ulmer Spitze"));
        fourOpenRectangles1 = mMap.addMarker(new MarkerOptions().position(fourOpenRectangles).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Four Open Rectangles"));
        mMap.addMarker(new MarkerOptions().position(klosterhof).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Klosterhof"));

        showDistanceToMarker(currentLocation, dreiBild);


    }


    private void zoomToUni(GoogleMap mMap) {
        LatLng zoomIn = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoomIn));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomIn,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
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
        if(requestCode == REQEST_CODE_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
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