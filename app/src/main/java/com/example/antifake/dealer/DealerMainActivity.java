package com.example.antifake.dealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.dealer.ui.DealerComeinRecordActivity;
import com.example.antifake.dealer.ui.DealerInfRecordActivity;
import com.example.antifake.dealer.ui.DealerInventorySearchActivity;

public class DealerMainActivity extends AppCompatActivity {
    private ImageButton btn_comein=null;
    private ImageButton btn_record=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_search=null;

    private String address=null;
    private String secret=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        btn_comein=findViewById(R.id.imageButton_comein_record);
        btn_comein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerComeinRecordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address", address);
                bundle.putString("secret",secret);
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
}