package com.example.antifake;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.antifake.ui.main.SectionsPagerAdapter;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;
import com.peersafe.chainsql.core.Submit.SyncCond;
import com.peersafe.chainsql.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab_search);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchInfomationActivity.class);
                startActivity(intent);
            }
        });
        /*创建账户并激活代码
        String rootAddress = "zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh";
        String rootSecret = "xnoPBzXtMeMyMHUVTgbuqAfg1SUTb";
        Chainsql c = new Chainsql();
        c.connect(getString(R.string.severIP_1));
        c.connection.client.logger.setLevel(Level.SEVERE);
        System.out.println( c.generateAddress() );

        c.as(rootAddress,rootSecret);
        JSONObject obj = c.pay("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3","200").submit(SyncCond.validate_success);
        if(obj.has("error_message")){
            try {
                System.out.println("激活或转账失败。 失败原因: " + obj.getString("error_message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("激活或转账成功");
        }

         */






    }



}