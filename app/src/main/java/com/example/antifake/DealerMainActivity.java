package com.example.antifake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DealerMainActivity extends AppCompatActivity {
    private ImageButton btn_record=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_search=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);

        btn_record=findViewById(R.id.imageButton_dealer_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DealerMainActivity.this, DealerInfRecordActivity.class);
                startActivity(intent);
            }
        });

        btn_search=findViewById(R.id.imageButton_dealer_inventory);
        btn_search.setOnClickListener(new View.OnClickListener() {
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