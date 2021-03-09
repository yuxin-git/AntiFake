package com.example.antifake.brand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.brand.ui.BrandDeallistSearchActivity;
import com.example.antifake.brand.ui.BrandInventorySearchActivity;

public class BrandMainActivity extends AppCompatActivity {
    private ImageButton btn_search=null;
    private ImageButton btn_inventory=null;
    private ImageButton btn_deallist=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_main);

        btn_search=findViewById(R.id.imageButton_brand_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, SearchInfomationActivity.class);
                startActivity(intent);
            }
        });

        btn_inventory=findViewById(R.id.imageButton_brand_inventory);
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, BrandInventorySearchActivity.class);
                startActivity(intent);
            }
        });

        btn_deallist=findViewById(R.id.imageButton_brand_deallist);
        btn_deallist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BrandMainActivity.this, BrandDeallistSearchActivity.class);
                startActivity(intent);
            }
        });

    }
}