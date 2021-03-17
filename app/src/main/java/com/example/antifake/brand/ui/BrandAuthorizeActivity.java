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
    private String address=null;
    private String secret=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_authorize);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btnAuth=findViewById(R.id.imageButton_auth_manu);
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandAuthorizeActivity.this, BrandAuthManuActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}