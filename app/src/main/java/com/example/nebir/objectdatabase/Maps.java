package com.example.nebir.objectdatabase;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Db4O bd;
    private PolylineOptions ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        bd=new Db4O(this);
        mMap = googleMap;
//        PolylineOptions rectOptions = new PolylineOptions();
//        List<Posicion> posicionList = bd.getConsulta();
//        Posicion puntoInicial=posicionList.get(0);
//        LatLng punto = new LatLng(puntoInicial.getLatitud(), puntoInicial.getLongitud());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));
//        for(Posicion p : posicionList){
//            mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(p.getLatitud(), p.getLongitud()))
//                    .title("Hello world"));
//            rectOptions.add(new LatLng(p.getLatitud(),p.getLongitud()));
//            Log.v("XXX", "Marca puesta en: " + p.getLatitud() + "/" + p.getLongitud());
//        }
//        rectOptions.color( Color.parseColor("#CC0000FF") );
//        rectOptions.width( 5 );
//        rectOptions.visible( true );
//
//        mMap.clear();
//        mMap.addPolyline(rectOptions);
//        setRuta(1);
        ruta= new PolylineOptions();
        // Add a marker in Sydney and move the camera


        LatLng sydney = new LatLng(37.35, -122.0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(37.35, -122.0))
                .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
                .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
                .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
                .add(new LatLng(37.35, -122.0)); // Closes the polyline.

// Get back the mutable Polyline

        Polyline polyline = mMap.addPolyline(rectOptions);
        bd.close();

// Get back the mutable Polyline


    }

    public void setRuta(int dia) {
//        bd = new Db4O(this);
        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions();
        ArrayList<Posicion> pos = bd.getConsulta();
        Log.v("DIAAAAA", pos.toString());
        for (Posicion p : pos) {
            rectOptions.add(new LatLng(p.getLatitud(), p.getLongitud()));
        }

        // Get back the mutable Polyline
        mMap.clear();
        mMap.addPolyline(rectOptions);
        bd.close();
    }
}


