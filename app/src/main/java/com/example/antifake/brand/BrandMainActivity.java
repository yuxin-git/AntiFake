package com.example.antifake.brand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.brand.ui.BrandAuthorizeActivity;
import com.example.antifake.brand.ui.BrandInventorySearchActivity;

public class BrandMainActivity extends AppCompatActivity {
    private ImageButton btn_auth=null;
    private ImageButton btn_search=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_deallist=null;
    private String address=null;
    private String secret=null;
    private String userCert="-----BEGIN CERTIFICATE-----\n" +
            "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW+MAoGCCqGSM49BAMCMGoxCzAJ\n" +
            "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
            "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
            "cXEuY29tMB4XDTIxMDMzMTAyNTMyMVoXDTIxMDQzMDAyNTMyMVowRzELMAkGA1UE\n" +
            "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
            "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgGj19CTwvx4J\n" +
            "owoB6KV7uIQA3FUGCRucIIgxf1wSmCpH+PhMllmkfokie6X36wltaTEhq7gL2Q8T\n" +
            "1VlLU/hMVTAKBggqhkjOPQQDAgNIADBFAiEA4dJsMbIIqPrKDSaWjXvCYL/ljMdM\n" +
            "+wPDkNILytyOVMwCICzE7mI6VjUpSnzym3uND2huhFXj/qVNPTVGe1V3U0rL\n" +
            "-----END CERTIFICATE-----";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_main);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

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

        btn_search=findViewById(R.id.imageButton_brand_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, SearchInfomationActivity.class);
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
                startActivity(intent);
            }
        });



    }
}