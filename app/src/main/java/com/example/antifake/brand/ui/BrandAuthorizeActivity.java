package com.example.antifake.brand.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.brand.BrandMainActivity;
import com.example.antifake.brand.ui.auth.BrandAuthManuActivity;
import com.example.antifake.brand.ui.auth.BrandAuthQualifyActivity;
import com.example.antifake.brand.ui.auth.BrandComAddActivity;

public class BrandAuthorizeActivity extends AppCompatActivity {
    private ImageButton btnAuthManu;
    private ImageButton btnAuthQuali;
    private ImageButton btnAuthComAdd;
    private String address=null;
    private String secret=null;
    private String userCert=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_authorize);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        btnAuthManu=findViewById(R.id.imageButton_auth_manu);
        btnAuthManu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandAuthorizeActivity.this, BrandAuthManuActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnAuthQuali=findViewById(R.id.imageButton_auth_qual);
        btnAuthQuali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandAuthorizeActivity.this, BrandAuthQualifyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnAuthComAdd=findViewById(R.id.imageButton_com_add);
        btnAuthComAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandAuthorizeActivity.this, BrandComAddActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}