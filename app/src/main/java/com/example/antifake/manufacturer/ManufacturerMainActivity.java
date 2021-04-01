package com.example.antifake.manufacturer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.CertPathActivity;
import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.dealer.DealerMainActivity;
import com.example.antifake.manufacturer.ui.ManuInfRecordActivity;
import com.example.antifake.manufacturer.ui.ManuInventorySearchActivity;
import com.example.antifake.manufacturer.ui.ManuOutwarehouseActivity;

public class ManufacturerMainActivity extends AppCompatActivity {
    private ImageButton btn_add_cert=null;
    private ImageButton btn_record=null;
    private ImageButton btn_manuout=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_search=null;

    private String address=null;
    private String secret=null;
    private String userCert="-----BEGIN CERTIFICATE-----\n" +
            "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW/MAoGCCqGSM49BAMCMGoxCzAJ\n" +
            "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
            "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
            "cXEuY29tMB4XDTIxMDMzMTA2NTQyOFoXDTIxMDQzMDA2NTQyOFowRzELMAkGA1UE\n" +
            "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
            "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEIfWUWKqkl9D+\n" +
            "viKoOce/AYNkp+vzVH1ORxlUroX93z8z5WSeadWvdPqnf2EX/m+FM/rlnwyN9CSl\n" +
            "7PnvWJJTLTAKBggqhkjOPQQDAgNIADBFAiEA8g898rAH82ugiQm8qydgSrlaSV2f\n" +
            "PlAOrGFa2+I9ancCIAo9R+Qb08MROvCG42vhhdidizd6sbRQ6j6iGIIjTyux\n" +
            "-----END CERTIFICATE-----";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_main);
        //获取账户地址及密钥
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btn_add_cert=findViewById(R.id.imageButton_add_cert_manu);
        btn_add_cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ManufacturerMainActivity.this, CertPathActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 888);// 跳转并要求返回值
            }
        });

        btn_record=findViewById(R.id.imageButton_manu_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManufacturerMainActivity.this, ManuInfRecordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_manuout=findViewById(R.id.imageButton_manu_out);
        btn_manuout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManufacturerMainActivity.this, ManuOutwarehouseActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_inventory=findViewById(R.id.imageButton_manu_inventory);
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManufacturerMainActivity.this, ManuInventorySearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_search=findViewById(R.id.imageButton_manu_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManufacturerMainActivity.this, SearchInfomationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            userCert = bundle.getString("userCert");
        }
    }
}