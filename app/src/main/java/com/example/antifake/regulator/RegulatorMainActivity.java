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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulator_main);

        btn_record=findViewById(R.id.imageButton_regu_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegulatorMainActivity.this, ReguInfRecordActivity.class);
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