package com.example.antifake.dealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.dealer.ui.DealerInfRecordInetActivity;
import com.example.antifake.dealer.ui.DealerInfRecordOfflineActivity;
import com.example.antifake.dealer.ui.DealerInventorySearchActivity;

public class DealerMainActivity extends AppCompatActivity {
    private ImageButton btn_record_inet=null;
    private ImageButton btn_record_off=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_search=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);

        btn_record_inet=findViewById(R.id.imageButton_dealer_record_inet);
        btn_record_inet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInfRecordInetActivity.class);
                startActivity(intent);
            }
        });

        btn_record_off=findViewById(R.id.imageButton_dealer_record_offline);
        btn_record_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInfRecordOfflineActivity.class);
                startActivity(intent);
            }
        });

        btn_inventory=findViewById(R.id.imageButton_dealer_inventory);
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInventorySearchActivity.class);
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
}