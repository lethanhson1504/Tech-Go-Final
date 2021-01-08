package com.example.techgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class HomeScreen extends AppCompatActivity implements Serializable {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String user_id;
    String wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_home_screen);
        Button bt_wallet = findViewById(R.id.wallet_home_screen);



        user_id = getIntent().getStringExtra("DATA_USER");
        myRef.child("users").orderByKey().equalTo(user_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            HashMap <String,Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                            Collection<Object> my = td.values();
                            for (Object elem : my) {
                                String[] arr = elem.toString().split(",");
                                for (int i=0;i< arr.length ;i++){
                                    if (arr[i].contains("wallet")){
                                        wallet = arr[i].split("=")[1];
                                        TextView wallet_text = findViewById(R.id.balance_vnd_text);
                                        wallet_text.setText(wallet);
                                        //System.out.println(wallet);
                                    }
                                }
                            }
                        }else{

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HomeScreen.this, wallet.class);
                startActivity(intent);
            }
        });

        Button bt_book = findViewById(R.id.book_trip_bt);
        bt_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("DATA_ID", user_id);
                intent.setClass(HomeScreen.this, bookScreen.class);
                startActivity(intent);
            }
        });

      /*  BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
*/
    }

}