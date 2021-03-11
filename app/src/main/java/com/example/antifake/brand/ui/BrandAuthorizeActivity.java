package com.example.antifake.brand.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.brand.BrandMainActivity;
import com.example.antifake.brand.ui.auth.BrandAuthManuActivity;

public class BrandAuthorizeActivity extends AppCompatActivity {
    private ImageButton btnAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_authorize);

        btnAuth=findViewById(R.id.imageButton_auth_manu);
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandAuthorizeActivity.this, BrandAuthManuActivity.class);
                startActivity(intent);
            }
        });
    }
}