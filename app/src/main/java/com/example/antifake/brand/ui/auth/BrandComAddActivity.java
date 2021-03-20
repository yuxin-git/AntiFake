package com.example.antifake.brand.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.antifake.R;

public class BrandComAddActivity extends AppCompatActivity {

    private Button btnOk;
    private Button btnCancel;
    private String address=null;
    private String secret=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_com_add);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btnOk=findViewById(R.id.button_comadd_ok);
        btnCancel=findViewById(R.id.button_comadd_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandComAddActivity.this.finish();
            }
        });

    }
}