package com.example.antifake;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.antifake.ui.main.SectionsPagerAdapter;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

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
        Button btnTest=findViewById(R.id.button_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Handler handler= new android.os.Handler() {
                    public void handleMessage(Message msg){
                        Toast.makeText(MainActivity.this, "执行成功！", Toast.LENGTH_LONG).show();
                    };

                };
                operation(handler);
            }
        });








    }

    private void operation(Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //查询账户信息
                String rootAddress = "zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh";
                Chainsql c = new Chainsql();
                System.out.println(c.getAccountInfo(rootAddress));
/*
                //操作一：激活账户
                String rootAddress = "zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh";
                String rootSecret = "xnoPBzXtMeMyMHUVTgbuqAfg1SUTb";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_2));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(rootAddress,rootSecret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW+MAoGCCqGSM49BAMCMGoxCzAJ\n" +
                "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
                "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
                "cXEuY29tMB4XDTIxMDMzMTAyNTMyMVoXDTIxMDQzMDAyNTMyMVowRzELMAkGA1UE\n" +
                "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
                "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgGj19CTwvx4J\n" +
                "owoB6KV7uIQA3FUGCRucIIgxf1wSmCpH+PhMllmkfokie6X36wltaTEhq7gL2Q8T\n" +
                "1VlLU/hMVTAKBggqhkjOPQQDAgNIADBFAiEA4dJsMbIIqPrKDSaWjXvCYL/ljMdM\n" +
                "+wPDkNILytyOVMwCICzE7mI6VjUpSnzym3uND2huhFXj/qVNPTVGe1V3U0rL\n" +
                "-----END CERTIFICATE-----");
                JSONObject obj = c.pay("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3","5000").submit(SyncCond.validate_success);
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
/*
                //操作二：创建经销商表项
                String address = "zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR";
                String secret = "xhpgTk8ALwWMugf921ak9eJdqtG4Q";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnjCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXFMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjU2MjRaFw0yMTA2MDkxNjU2MjRaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABAOXZoS8w1vKEGT7Kj7QI6GQqBED\n" +
                        "wC/EVAkiEYwTkbCRPDeX4oqHwAoJdvt1g/SHUPmYazero2j7IJHgXI3zcWgwCgYI\n" +
                        "KoZIzj0EAwIDSAAwRQIgRMTE8FDsHKcUqtO53GrI0/uOixgPlH9X3DnASPWrWd4C\n" +
                        "IQDGfcxzvyKfWA6m/WseS6IkHmJAbYz2k8DMDpCj55XJWg==\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj1 = c.dropTable("D001").submit(Submit.SyncCond.db_success);
                if(obj1.has("error_message")){
                    System.out.println(obj1);
                }else {
                    try {
                        System.out.println( "status" + obj1.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj = c.createTable("D001", c.array(
                        "{'field':'id','type':'int','length':30,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'ProductTypeNum','type':'int','length':20}",
                        "{'field':'ProductName','type':'varchar','length':50,'default':null}",
                        "{'field':'DealerNum','type':'varchar','length':20}",
                        "{'field':'SaleState','type':'int','length':5,'default':0}",
                        "{'field':'SaleDate','type':'varchar','length':60,'default':null}",
                        "{'field':'SaleType','type':'varchar','length':20,'default':null}",
                        "{'field':'CustomerName','type':'varchar','length':50,'default':null}",
                        "{'field':'CustomerTel','type':'varchar','length':70}",
                        "{'field':'CustomerAdd','type':'varchar','length':70,'default':null}",
                        "{'field':'DeliveryNum','type':'varchar','length':70}"),
                        false)
                        .submit(Submit.SyncCond.db_success);

                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj4 = new JSONObject();
                obj4 = c.grant("D001", "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj4.toString());



 */

/*

                //操作三：创建生产商表项
                String address="zKhdUEXNWMYG3uEquQkhGvYM3mZRGqYqNf";
                String secret="xp1vcANddqbBhbfEr8i624pXcA5B4";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnjCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXEMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjU1NTlaFw0yMTA2MDkxNjU1NTlaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABCH1lFiqpJfQ/r4iqDnHvwGDZKfr\n" +
                        "81R9TkcZVK6F/d8/M+VknmnVr3T6p39hF/5vhTP65Z8MjfQkpez571iSUy0wCgYI\n" +
                        "KoZIzj0EAwIDSAAwRQIhAMT9Okw5TpJtv3iWtuuu1JKXTnUwusMtLj6s/jQcv39s\n" +
                        "AiB/qP5ALurRggXRfNYDEGjnPdYe0kRqKM8dikzwQpxtRg==\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj1 = c.dropTable("M001").submit(Submit.SyncCond.db_success);
                if(obj1.has("error_message")){
                    System.out.println(obj1);
                }else {
                    try {
                        System.out.println( "status" + obj1.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj = c.createTable("M001", c.array(
                        "{'field':'id','type':'int','length':30,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'ProductTypeNum','type':'int','length':20}",
                        "{'field':'ProductName','type':'varchar','length':50,'default':null}",
                        "{'field':'ManufacturerNum','type':'varchar','length':20}",
                        //删除"{'field':'ManufacturerName','type':'varchar','length':70,'default':null}",
                        "{'field':'DeliveryState','type':'int','length':5,'default':0}",
                        "{'field':'ProductDate','type':'varchar','length':70}",
                        "{'field':'DealerNum','type':'varchar','length':20}"),
                        false)
                        .submit(Submit.SyncCond.db_success);

                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj4 = new JSONObject();
                obj4 = c.grant("M001", "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj4.toString());

 */




/*
                //操作四：创建监管机构表项
                String address="zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z";
                String secret="xnejoG6irLTUgNgELJM5Y5ipsSwDT";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnzCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXGMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjU2NDVaFw0yMTA2MDkxNjU2NDVaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABBphn2qzTKAJunJfEsq+ALFY9/Sb\n" +
                        "Buhz8Zn/niihgsqGPWZ8pxDs1JFfU9N43bGoY2sEjEqd5JhGWh6Ilk3M6bkwCgYI\n" +
                        "KoZIzj0EAwIDSQAwRgIhAN5jG+a5q5y8fuT7xdWdY4icoNa2RcB1cZpd/chqYLwk\n" +
                        "AiEA3M/wyCI02+1fYak67LdF6ta8fIJmgB2nq/nVCu8UHX0=\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj1 = c.dropTable("R001").submit(Submit.SyncCond.db_success);
                if(obj1.has("error_message")){
                    System.out.println(obj1);
                }else {
                    try {
                        System.out.println( "status" + obj1.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj = c.createTable("R001", c.array(
                        "{'field':'id','type':'int','length':30,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'ProductTypeNum','type':'int','length':20}",
                        "{'field':'ProductName','type':'varchar','length':50,'default':null}",
                        "{'field':'ManufacturerNum','type':'varchar','length':20}",
                        //删除"{'field':'ManufacturerName','type':'varchar','length':70,'default':null}",
                        "{'field':'RegulatorNum','type':'varchar','length':20}",
                        //删除"{'field':'RegulatorName','type':'varchar','length':70,'default':null}",
                        "{'field':'WorkerNum','type':'varchar','length':20}",
                        "{'field':'RegulatorDate','type':'varchar','length':70}",
                        "{'field':'RegulatorResult','type':'varchar','length':70}"),
                        false)
                        .submit(Submit.SyncCond.db_success);

                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj4 = new JSONObject();
                obj4 = c.grant("R001", "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj4.toString());

 */





/*

                //操作五：创建品牌商表项(总表)
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnzCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXDMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjEwNTFaFw0yMTA2MDkxNjEwNTFaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABIBo9fQk8L8eCaMKAeile7iEANxV\n" +
                        "BgkbnCCIMX9cEpgqR/j4TJZZpH6JInul9+sJbWkxIau4C9kPE9VZS1P4TFUwCgYI\n" +
                        "KoZIzj0EAwIDSQAwRgIhALDNX6XVtA9qunR/PUuzvBTg2ijv3hWODq9gYB0V+vry\n" +
                        "AiEAm7fYkOI/d3pKKRJEwISW+Wq1lYQODFt4qANfuRejOdY=\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj1 = c.dropTable("com_infor").submit(Submit.SyncCond.db_success);
                if(obj1.has("error_message")){
                    System.out.println(obj1);
                }else {
                    try {
                        System.out.println( "status" + obj1.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj = c.createTable("com_infor", c.array(
                        "{'field':'id','type':'int','length':30,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'ProductTypeNum','type':'int','length':20}",
                        "{'field':'ProductName','type':'varchar','length':50,'default':null}",
                        "{'field':'ManufacturerNum','type':'varchar','length':20}",
                        "{'field':'DealerNum','type':'varchar','length':20}",
                        "{'field':'RegulatorNum','type':'varchar','length':20}",
                        "{'field':'ManuLedger','type':'int','length':20}",
                        "{'field':'DealerLedger','type':'int','length':20}",
                        "{'field':'ReguLedger','type':'int','length':20}"),
                        false)
                        .submit(Submit.SyncCond.db_success);
                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

 */
/*
                //品牌商总表授权
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnzCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXDMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjEwNTFaFw0yMTA2MDkxNjEwNTFaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABIBo9fQk8L8eCaMKAeile7iEANxV\n" +
                        "BgkbnCCIMX9cEpgqR/j4TJZZpH6JInul9+sJbWkxIau4C9kPE9VZS1P4TFUwCgYI\n" +
                        "KoZIzj0EAwIDSQAwRgIhALDNX6XVtA9qunR/PUuzvBTg2ijv3hWODq9gYB0V+vry\n" +
                        "AiEAm7fYkOI/d3pKKRJEwISW+Wq1lYQODFt4qANfuRejOdY=\n" +
                        "-----END CERTIFICATE-----");
                String sTableName = "com_infor";
                JSONObject obj1 = new JSONObject();
                obj1 = c.grant(sTableName, "zKhdUEXNWMYG3uEquQkhGvYM3mZRGqYqNf", "{insert:false,update:true,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj1.toString());
                JSONObject obj2 = new JSONObject();
                obj2 = c.grant(sTableName, "zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR", "{insert:false,update:true,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj2.toString());
                JSONObject obj3 = new JSONObject();
                obj3 = c.grant(sTableName, "zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z", "{insert:false,update:true,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj3.toString());
                JSONObject obj4 = new JSONObject();
                obj4 = c.grant(sTableName, "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj4.toString());



 */




/*
                //操作六：创建账户地址信息表
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBnzCCAUQCFGQ41AjfsOV17lzmuj/KYqug9eXDMAoGCCqGSM49BAMCMF8xCzAJ\n" +
                        "BgNVBAYTAmNhMQswCQYDVQQIDAJ5bjELMAkGA1UEBwwCaGgxDDAKBgNVBAoMA2h5\n" +
                        "eDEKMAgGA1UECwwBaDEKMAgGA1UEAwwBaDEQMA4GCSqGSIb3DQEJARYBaDAeFw0y\n" +
                        "MTA1MTAxNjEwNTFaFw0yMTA2MDkxNjEwNTFaMEcxCzAJBgNVBAYTAkNOMQswCQYD\n" +
                        "VQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCFBlZXJzYWZlMQswCQYDVQQD\n" +
                        "DAJSQzBWMBAGByqGSM49AgEGBSuBBAAKA0IABIBo9fQk8L8eCaMKAeile7iEANxV\n" +
                        "BgkbnCCIMX9cEpgqR/j4TJZZpH6JInul9+sJbWkxIau4C9kPE9VZS1P4TFUwCgYI\n" +
                        "KoZIzj0EAwIDSQAwRgIhALDNX6XVtA9qunR/PUuzvBTg2ijv3hWODq9gYB0V+vry\n" +
                        "AiEAm7fYkOI/d3pKKRJEwISW+Wq1lYQODFt4qANfuRejOdY=\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj7 = c.dropTable("address_list").submit(Submit.SyncCond.db_success);
                if(obj7.has("error_message")){
                    System.out.println(obj7);
                }else {
                    try {
                        System.out.println( "status" + obj7.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject obj = c.createTable("address_list", c.array(
                        "{'field':'AccountId','type':'varchar','length':20,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'AccountType','type':'varchar','length':5}",
                        "{'field':'AccountAdd','type':'varchar','length':100}",
                        "{'field':'AccountName','type':'varchar','length':70}",
                        "{'field':'AccountLedger','type':'int','length':20}"),
                        false)
                        .submit(Submit.SyncCond.db_success);

                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String sTableName = "address_list";
                JSONObject obj1 = new JSONObject();
                obj1 = c.grant(sTableName, "zKhdUEXNWMYG3uEquQkhGvYM3mZRGqYqNf", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj1.toString());
                JSONObject obj2 = new JSONObject();
                obj2 = c.grant(sTableName, "zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj2.toString());
                JSONObject obj3 = new JSONObject();
                obj3 = c.grant(sTableName, "zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj3.toString());
                JSONObject obj4 = new JSONObject();
                obj4 = c.grant(sTableName, "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83", "{insert:false,update:false,select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj4.toString());


 */



                /*
                //操作七：创建商品种类对应表
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW+MAoGCCqGSM49BAMCMGoxCzAJ\n" +
                        "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
                        "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
                        "cXEuY29tMB4XDTIxMDMzMTAyNTMyMVoXDTIxMDQzMDAyNTMyMVowRzELMAkGA1UE\n" +
                        "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
                        "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgGj19CTwvx4J\n" +
                        "owoB6KV7uIQA3FUGCRucIIgxf1wSmCpH+PhMllmkfokie6X36wltaTEhq7gL2Q8T\n" +
                        "1VlLU/hMVTAKBggqhkjOPQQDAgNIADBFAiEA4dJsMbIIqPrKDSaWjXvCYL/ljMdM\n" +
                        "+wPDkNILytyOVMwCICzE7mI6VjUpSnzym3uND2huhFXj/qVNPTVGe1V3U0rL\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj = c.createTable("com_list", c.array(
                        "{'field':'ProductTypeNum','type':'int','length':20,'PK':1,'NN':1,'UQ':1}",
                        "{'field':'ProductName','type':'varchar','length':100}"),
                        false)
                        .submit(SyncCond.db_success);
                if (obj.has("error_message")) {
                    System.out.println(obj);
                } else {
                    try {
                        System.out.println("status:" + obj.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                 */
/*
                //操作八：删除账户信息表
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW+MAoGCCqGSM49BAMCMGoxCzAJ\n" +
                        "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
                        "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
                        "cXEuY29tMB4XDTIxMDMzMTAyNTMyMVoXDTIxMDQzMDAyNTMyMVowRzELMAkGA1UE\n" +
                        "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
                        "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgGj19CTwvx4J\n" +
                        "owoB6KV7uIQA3FUGCRucIIgxf1wSmCpH+PhMllmkfokie6X36wltaTEhq7gL2Q8T\n" +
                        "1VlLU/hMVTAKBggqhkjOPQQDAgNIADBFAiEA4dJsMbIIqPrKDSaWjXvCYL/ljMdM\n" +
                        "+wPDkNILytyOVMwCICzE7mI6VjUpSnzym3uND2huhFXj/qVNPTVGe1V3U0rL\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj = c.pay("zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR","300").submit(Submit.SyncCond.validate_success);
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


                /*
                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                Chainsql c = new Chainsql();
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert("-----BEGIN CERTIFICATE-----\n" +
                        "MIIBqTCCAU8CFGQ41AjfsOV17lzmuj/KYqug9eW+MAoGCCqGSM49BAMCMGoxCzAJ\n" +
                        "BgNVBAYTAmMxMQowCAYDVQQIDAExMQowCAYDVQQHDAF5MQowCAYDVQQKDAF6MQow\n" +
                        "CAYDVQQLDAF6MQowCAYDVQQDDAF6MR8wHQYJKoZIhvcNAQkBFhA0Mzc4NzM1NjZA\n" +
                        "cXEuY29tMB4XDTIxMDMzMTAyNTMyMVoXDTIxMDQzMDAyNTMyMVowRzELMAkGA1UE\n" +
                        "BhMCQ04xCzAJBgNVBAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwIUGVlcnNh\n" +
                        "ZmUxCzAJBgNVBAMMAlJDMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgGj19CTwvx4J\n" +
                        "owoB6KV7uIQA3FUGCRucIIgxf1wSmCpH+PhMllmkfokie6X36wltaTEhq7gL2Q8T\n" +
                        "1VlLU/hMVTAKBggqhkjOPQQDAgNIADBFAiEA4dJsMbIIqPrKDSaWjXvCYL/ljMdM\n" +
                        "+wPDkNILytyOVMwCICzE7mI6VjUpSnzym3uND2huhFXj/qVNPTVGe1V3U0rL\n" +
                        "-----END CERTIFICATE-----");
                JSONObject obj = c.grant("address_list", "zpncip6ZUTrfqz3nSHpix4eQyMS4F1KM83"
                        , "{select:true}")
                        .submit(Submit.SyncCond.validate_success);
                System.out.println("grant result:" + obj.toString());

                 */






                handler.sendEmptyMessage(1);
            }
        }).start();

    }


}