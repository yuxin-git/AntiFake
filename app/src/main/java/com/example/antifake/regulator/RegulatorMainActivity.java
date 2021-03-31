package com.example.antifake.regulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.manufacturer.ManufacturerMainActivity;
import com.example.antifake.manufacturer.ui.ManuInfRecordActivity;
import com.example.antifake.regulator.ui.ReguInfRecordActivity;

public class RegulatorMainActivity extends AppCompatActivity {
    private ImageButton btn_record=null;
    private ImageButton btn_search=null;

    private String address=null;
    private String secret=null;
    private String userCert="-----BEGIN CERTIFICATE-----\n" +
            "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eXBMAoGCCqGSM49BAMCMGoxCzAJ\n" +
            "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
            "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
            "cXEuY29tMB4XDTIxMDMzMTA3Mzc1MFoXDTIxMDQzMDA3Mzc1MFowRzELMAkGA1UE\n" +
            "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
            "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEGmGfarNMoAm6\n" +
            "cl8Syr4AsVj39JsG6HPxmf+eKKGCyoY9ZnynEOzUkV9T03jdsahjawSMSp3kmEZa\n" +
            "HoiWTczpuTAKBggqhkjOPQQDAgNIADBFAiAZDg+jKbyhpxE6hGZpY4o/oWA8PWvz\n" +
            "sfSC03qfprRR7QIhALmI0KWRXAJ9f92io2Px+Pj+5R+moJai0EwpYBLdICJd\n" +
            "-----END CERTIFICATE-----";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulator_main);
        //获取账户地址及密钥
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
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
}