package com.example.antifake.dealer;

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
import com.example.antifake.brand.BrandMainActivity;
import com.example.antifake.dealer.ui.DealerComeinRecordActivity;
import com.example.antifake.dealer.ui.DealerInfRecordActivity;
import com.example.antifake.dealer.ui.DealerInventorySearchActivity;

public class DealerMainActivity extends AppCompatActivity {
    private ImageButton btn_comein=null;
    private ImageButton btn_record=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_search=null;
    private ImageButton btn_add_cert=null;

    private String address=null;
    private String secret=null;
    private String userCert=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btn_add_cert=findViewById(R.id.imageButton_add_cert_de);
        btn_add_cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DealerMainActivity.this, CertPathActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 777);// 跳转并要求返回值
            }
        });

        btn_comein=findViewById(R.id.imageButton_comein_record);
        btn_comein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerComeinRecordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_record=findViewById(R.id.imageButton_dealer_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInfRecordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_inventory=findViewById(R.id.imageButton_dealer_inventory);
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInventorySearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
                bundle.putString("userCert",userCert);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_search=findViewById(R.id.imageButton_dealer_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, SearchInfomationActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            userCert = bundle.getString("userCert");
        }
    }
}