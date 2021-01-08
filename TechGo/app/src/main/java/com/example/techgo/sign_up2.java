package com.example.techgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class sign_up2 extends AppCompatActivity implements Serializable {

    private EditText displayname_text;
    private Button bt_finish;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        Intent i =  getIntent();
        final sign_up data_sign_up = (sign_up)i.getSerializableExtra("data_sign_up");

        displayname_text = findViewById(R.id.display_name_register_text);

        bt_finish = findViewById(R.id.finish_register_bt);

        CheckBox agree = findViewById(R.id.checkBox_register);


        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String display_name = displayname_text.getText().toString();

                //password
                String password = getIntent().getStringExtra("PW");

                //email and phone number
                String email = getIntent().getStringExtra("EMAIL");
                String phone = getIntent().getStringExtra("PHONE");

                //string, user type
                String us_type = getIntent().getStringExtra("IS_CUS");


                if(!agree.isChecked())
                    alertDialog("Please agree with our terms of service!");

                else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(sign_up2.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        User newUser = new User( email, password, phone
                                                , display_name, us_type, 10,10);
                                        myRef.child("users").child(user.getUid()).setValue(newUser);
                                        System.out.println(newUser);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        System.out.println("error");
                                    }

                                    // ...
                                }
                            });
                    Intent i = new Intent(sign_up2.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }

            }
        }) ;





        Button bt_pre = findViewById(R.id.previous_register_bt);
        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }) ;

    }

    private void alertDialog(String alert) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(alert);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}