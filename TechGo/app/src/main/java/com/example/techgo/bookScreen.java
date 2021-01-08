package com.example.techgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class bookScreen extends AppCompatActivity  implements Serializable {
    TextView from_textView;
    TextView to_textView;
    Button car;
    Button bike;
    String vehicle_type = "bike";
    Double pick_lat;
    Double pick_long;
    Double des_long;
    Double des_lat;
    String user_id;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        user_id = getIntent().getStringExtra("DATA_ID");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);


        from_textView = findViewById(R.id.from_booking_text);
        from_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(bookScreen.this, pickingLocationCustomer.class);
                startActivityForResult(intent, 1);

            }

        }) ;

        to_textView = findViewById(R.id.to_booking_text);
        to_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(bookScreen.this, DestinationLocation.class);
                startActivityForResult(intent, 2);

            }

        }) ;

        Button book = findViewById(R.id.book_bt);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").orderByKey().equalTo(user_id)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //Display UI about "User was registered" notifications to the user
                                    HashMap <String,Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                                    Collection<Object> my = td.values();
                                    Vector<String> arr = new Vector<>();
                                    for (Object elem : my) {
                                        String ele = elem.toString();
                                        String trips = ele.substring(ele.indexOf("trips") + 7,ele.indexOf("email") - 3);
                                        int i = 0;int k = 0;int nk = 0;
                                        int j = 0;int f = 0;int nf= 0;
                                        while (true){
                                            i = trips.indexOf("{", i+1);
                                            nk += 1;
                                            j = trips.indexOf("}" , j+1);
                                            if (j==-1){
                                                break;
                                            }
                                            nf += 1;
                                            if (nk !=0 && nk % 3 == 0 && nk == nf){
                                                String sub = trips.substring(k,j+1);
                                                arr.add(sub);
                                                k = j + 1;
                                            }
                                        }
                                        //trip2={driverCancel=false, cost=0, partnerUid=pt1klWorWAcdo8aKUDs9CsYjDd93,
                                        // paymentMethod=cash, from={lng=106.68223607086696, lat=10.763134334307447},
                                        // to={lng=106.68223607086696, lat=10.763134334307447}, isActive=true, vehicleType=bike}
                                        for (int nl = 0; nl < arr.size() ;nl++){
                                            String subHandle = arr.get(nl);
                                            String dC = subHandle.substring(subHandle.indexOf("driverCancel") + 13, subHandle.indexOf(",", subHandle.indexOf("driverCancel")));
                                            String cost = subHandle.substring(subHandle.indexOf("cost") + 5, subHandle.indexOf(",", subHandle.indexOf("cost")));
                                            String pU = subHandle.substring(subHandle.indexOf("partnerUid") + 11, subHandle.indexOf(",", subHandle.indexOf("partnerUid")));
                                            String pM = subHandle.substring(subHandle.indexOf("paymentMethod") + 14, subHandle.indexOf(",", subHandle.indexOf("paymentMethod")));
                                            int ln1 = subHandle.indexOf("lng") + 4;
                                            int lat1 = subHandle.indexOf("lat") + 4;
                                            String fromlng = subHandle.substring(ln1 , subHandle.indexOf(",", ln1 - 4));
                                            String fromlat = subHandle.substring(lat1 , subHandle.indexOf(",", lat1 - 4) -1);
                                            ln1 = subHandle.indexOf("lng" ,ln1 + 4) + 4;
                                            lat1 = subHandle.indexOf("lat", lat1 + 4) + 4;
                                            String tolng = subHandle.substring(ln1 , subHandle.indexOf(",", ln1 - 4));
                                            String tolat = subHandle.substring(lat1 , subHandle.indexOf(",", lat1 - 4) - 1);
                                            String isActive = subHandle.substring(subHandle.indexOf("isActive") + 9, subHandle.indexOf(",", subHandle.indexOf("isActive")));
                                            String vT = subHandle.substring(subHandle.indexOf("vehicleType") + 12, subHandle.indexOf("}", subHandle.indexOf("vehicleType")));
                                        }
                                    }

                                    //HashMap map = new HashMap();
                                    //map.put("completeTrip", true);
                                    //myRef.child("users").child(user_id).updateChildren(map);
                                }else{

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                Intent intent = new Intent();
                intent.setClass(bookScreen.this, findingDriver.class);
                startActivityForResult(intent, 2);

            }

        }) ;

        car = findViewById(R.id.book_car_bt);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_type = "car";
            }
        }) ;

        bike = findViewById(R.id.book_bike_bt);
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_type = "bike";
            }
        }) ;

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.payment_method, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 1)
        {
            String message=data.getStringExtra("PICK_ADRS");
            from_textView.setText(message);

            pick_lat =  data.getDoubleExtra("LAT_LOCA" , 1);
            pick_long = data.getDoubleExtra("LONG_LOCA", 1);
        }
        if (requestCode == 2)
        {
            String message=data.getStringExtra("DES_ADRS");
            to_textView.setText(message);

            des_lat = data.getDoubleExtra("DES_LAT" , 1);
            des_long = data.getDoubleExtra("DES_LONG" , 1);
        }

    }
}

