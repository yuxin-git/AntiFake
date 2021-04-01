package com.example.antifake.brand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.antifake.CertPathActivity;
import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.brand.ui.BrandAuthorizeActivity;
import com.example.antifake.brand.ui.BrandInventorySearchActivity;

public class BrandMainActivity extends AppCompatActivity {
    private ImageButton btn_auth=null;
    private ImageButton btn_search=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_add_ca=null;
    private String address=null;
    private String secret=null;
    private String userCert=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_main);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btn_add_ca=findViewById(R.id.imageButton_add_ca);
        btn_add_ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BrandMainActivity.this, CertPathActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 666);// 跳转并要求返回值
            }
        });



        btn_auth=findViewById(R.id.imageButton_authorize_manu);
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, BrandAuthorizeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_inventory=findViewById(R.id.imageButton_auth_qual);
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, BrandInventorySearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_search=findViewById(R.id.imageButton_brand_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, CertPathActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            userCert = bundle.getString("userCert");
        }
    }
}