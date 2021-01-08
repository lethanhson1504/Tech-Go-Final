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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class pickingLocationCustomer extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button bt_location;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button bt_confirm;
    String address_pick_up;
    double lat_loca;
    double long_loca;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_location_customer1);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        bt_location = findViewById(R.id.bt_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(com.example.techgo.pickingLocationCustomer.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocaion();
                } else {
                    ActivityCompat.requestPermissions(com.example.techgo.pickingLocationCustomer.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        bt_confirm = findViewById(R.id.confirm_picking_bt);
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("PICK_ADRS", address_pick_up);
                setResult(1,intent);
               // Location_db location_db = new Location_db(lat_loca, long_loca );
                intent.putExtra("LAT_LOCA", lat_loca);
                intent.putExtra("LONG_LOCA", long_loca);
                //location_db.createLocation(myRef);
                finish();//finishing activity
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.picking_map_customer);
        mapFragment.getMapAsync(this);

    }

    private void getLocaion() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location= task.getResult();
                if(location != null){

                    try {
                        Geocoder geocoder = new Geocoder(com.example.techgo.pickingLocationCustomer.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //set latitude on text view
                        /*
                        textView1.setText("" + addresses.get(0).getLatitude());
                        textView2.setText("" + );

                        textView3.setText(addresses.get(0).getCountryName());
                        textView4.setText(addresses.get(0).getLocality());

                        textView5.setText(addresses.get(0).getAddressLine(0));
*/
                        mMap.clear();

                        LatLng current_pos = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        address_pick_up = addresses.get(0).getAddressLine(0) ;
                        Marker current_mark = mMap.addMarker(new MarkerOptions().position(current_pos).title(address_pick_up));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_pos));

                        current_mark.showInfoWindow();

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_pos, 18));
                        lat_loca = addresses.get(0).getLatitude();
                        long_loca = addresses.get(0).getLongitude();

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
                    Geocoder geocoder = new Geocoder(com.example.techgo.pickingLocationCustomer.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude,1
                    );

                    lat_loca = latLng.latitude;
                    long_loca = latLng.longitude;

                    mMap.clear();
                    address_pick_up = addresses.get(0).getAddressLine(0);
                    Marker markerOptions = mMap.addMarker(
                            new MarkerOptions()
                                    .position(latLng)
                                    .title(address_pick_up));
                    markerOptions.showInfoWindow();


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

                } catch (IOException e){
                    e.printStackTrace();
                }



            }
        });

    }
}