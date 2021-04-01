package com.example.antifake.regulator;

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
import com.example.antifake.manufacturer.ManufacturerMainActivity;
import com.example.antifake.manufacturer.ui.ManuInfRecordActivity;
import com.example.antifake.regulator.ui.ReguInfRecordActivity;

public class RegulatorMainActivity extends AppCompatActivity {
    private ImageButton btn_add_cert=null;
    private ImageButton btn_record=null;
    private ImageButton btn_search=null;

    private String address=null;
    private String secret=null;
    private String userCert=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulator_main);
        //获取账户地址及密钥
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btn_add_cert=findViewById(R.id.imageButton_add_cert_regu);
        btn_add_cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegulatorMainActivity.this, CertPathActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 999);// 跳转并要求返回值
            }
        });

        btn_record=findViewById(R.id.imageButton_regu_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegulatorMainActivity.this, ReguInfRecordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_search=findViewById(R.id.imageButton_regu_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegulatorMainActivity.this, SearchInfomationActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            userCert = bundle.getString("userCert");
        }
    }
}