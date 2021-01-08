package com.example.techgo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DestinationLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button bt_location;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button bt_confirm;
    String address_destination;
    double des_lat;
    double des_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        bt_location = findViewById(R.id.bt_destin_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(com.example.techgo.DestinationLocation.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocaion();
                } else {
                    ActivityCompat.requestPermissions(com.example.techgo.DestinationLocation.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        bt_confirm = findViewById(R.id.confirm_destination_bt);
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("DES_ADRS", address_destination);
                setResult(2, intent);

                intent.putExtra("DES_LAT", des_lat);
                intent.putExtra("DES_LONG", des_long);

                finish();//finishing activity
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.destination_map_customer);
        mapFragment.getMapAsync(this);

    }

    private void getLocaion() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location= task.getResult();
                if(location != null){

                    try {
                        Geocoder geocoder = new Geocoder(com.example.techgo.DestinationLocation.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        mMap.clear();

                        LatLng current_pos = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        address_destination = addresses.get(0).getAddressLine(0) ;
                        //Marker current_mark = mMap.addMarker(new MarkerOptions().position(current_pos).title(address_destination));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_pos));

                        //current_mark.showInfoWindow();

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_pos, 13));

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                try {
                    Geocoder geocoder = new Geocoder(com.example.techgo.DestinationLocation.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude,1
                    );

                    mMap.clear();
                    address_destination = addresses.get(0).getAddressLine(0);
                    Marker markerOptions = mMap.addMarker(
                            new MarkerOptions()
                                    .position(latLng)
                                    .title(address_destination));
                    markerOptions.showInfoWindow();


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

                } catch (IOException e){
                    e.printStackTrace();
                }



            }
        });

    }
}