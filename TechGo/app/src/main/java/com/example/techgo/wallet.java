package com.example.techgo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class wallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Button bt_recharge = findViewById(R.id.recharge_wallet_bt);
        bt_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechargeDialog dialog_recharge = new rechargeDialog();
                dialog_recharge.showPopupWindow(v);

            }
        }) ;

        Button bt_withdraw = findViewById(R.id.withdraw_wallet_bt);
        bt_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawDialog dialog = new withdrawDialog();
                dialog.showPopupWindow(v);

            }
        }) ;
    }
}
