package com.example.antifake.brand.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antifake.R;
import com.example.antifake.dealer.ui.DealerInventorySearchActivity;
import com.peersafe.chainsql.core.Chainsql;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class BrandInventorySearchActivity extends AppCompatActivity {
    private Chainsql c=new Chainsql();
    private String address=null;
    private String secret=null;
    private EditText editTextManuId;
    private EditText editTextManuName;
    private EditText editTextDealerId;
    private EditText editTextDealerName;
    private Button btnManu;
    private Button btnDealer;
    private String manuId;
    private String manuName;
    private String dealerId;
    private String dealerName;
    private String searchAdd=null;
    private int deliveryOk=0;
    private int deliveryNo=0;
    private int deliveryAll=0;
    private int saleOk=0;
    private int saleNo=0;
    private int saleAll=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_inventory_search);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        editTextManuId=findViewById(R.id.editText_manu_id);
        editTextManuName=findViewById(R.id.editText_manu_name);
        editTextDealerId=findViewById(R.id.editText_de_id);
        editTextDealerName=findViewById(R.id.editText_de_name);
        btnManu=findViewById(R.id.button_manufacturer);
        btnDealer=findViewById(R.id.button_dealer);
        btnManu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manuId=editTextManuId.getText().toString();
                manuName=editTextManuName.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        //不存在该生产商
                        if(msg.what==0){
                            Toast.makeText(BrandInventorySearchActivity.this,
                                    "查询失败，该生产商不存在！", Toast.LENGTH_LONG).show();
                        }else if(msg.what==1){
                            Handler handler1=new Handler(){
                                public void handleMessage(Message msg) {
                                    if(msg.what==1) {
                                        String show = "已出库 ： " + deliveryOk
                                                + "\n\n未出库 ：" + deliveryNo
                                                + "\n\n共计 ：" + deliveryAll;
                                        String str_title = "生产商" + manuId + "库存查询";
                                        AlertDialog.Builder builder = new AlertDialog.Builder(BrandInventorySearchActivity.this);
                                        builder.setIcon(R.drawable.ic_search)
                                                .setTitle(str_title)
                                                .setMessage(show)
                                                .setNegativeButton("确定", null);
                                        builder.create().show();
                                    }
                                }
                            };
                            manuDeliverSearch(handler1,manuId);
                        }
                    };

                };
                if(manuId.isEmpty()&&manuName.isEmpty()) {
                    Toast.makeText(BrandInventorySearchActivity.this,
                            "输入为空，无法查询！", Toast.LENGTH_LONG).show();
                }else if(!(manuId.isEmpty())&&manuName.isEmpty()) {
                    searchId(handler,manuId);
                }else if(manuId.isEmpty()&&!(manuName.isEmpty())) {
                    searchName(handler,manuName);
                }else if(!(manuId.isEmpty())&&!(manuName.isEmpty())) {
                    searchId(handler, manuId);
                }
            }
        });
        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealerId=editTextDealerId.getText().toString();
                dealerName=editTextDealerName.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        //不存在该生产商
                        if(msg.what==0){
                            Toast.makeText(BrandInventorySearchActivity.this,
                                    "查询失败，该经销商不存在！", Toast.LENGTH_LONG).show();
                        }else if(msg.what==1){
                            Handler handler1=new Handler(){
                                public void handleMessage(Message msg) {
                                    if(msg.what==1) {
                                        String show = "已售出 ： " + saleOk
                                                + "\n\n未售出 ：" + saleNo
                                                + "\n\n共计 ：" + saleAll;
                                        String str_title = "经销商" + dealerId + "库存查询";
                                        AlertDialog.Builder builder = new AlertDialog.Builder(BrandInventorySearchActivity.this);
                                        builder.setIcon(R.drawable.ic_search)
                                                .setTitle(str_title)
                                                .setMessage(show)
                                                .setNegativeButton("确定", null);
                                        builder.create().show();
                                    }
                                }
                            };
                            dealerDeliverSearch(handler1,dealerId);
                        }
                    };

                };
                if(dealerId.isEmpty()&&dealerName.isEmpty()) {
                    Toast.makeText(BrandInventorySearchActivity.this,
                            "输入为空，无法查询！", Toast.LENGTH_LONG).show();
                }else if(!(dealerId.isEmpty())&&dealerName.isEmpty()) {
                    searchId(handler,dealerId);
                }else if(dealerId.isEmpty()&&!(manuName.isEmpty())) {
                    searchName(handler,dealerName);
                }else if(!(manuId.isEmpty())&&!(manuName.isEmpty())) {
                    searchId(handler, dealerId);
                }

            }
        });


    }

    private void searchId(final Handler handler,final String num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                String sTableName = "address_list";
                String str1 = "{'AccountId':'" + num + "'}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                try {
                    if (obj1.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(0);
                    else {
                        searchAdd =obj1.getJSONArray("lines")
                                .getJSONObject(0).getString("AccountAdd");
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void searchName(final Handler handler,final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                String sTableName = "address_list";
                String str1 = "{'AccountName':'" + name + "'}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                try {
                    if (obj1.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(0);
                    else {
                        searchAdd =obj1.getJSONArray("lines")
                                .getJSONObject(0).getString("AccountAdd");
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void manuDeliverSearch(final Handler handler,final String mId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.use(searchAdd);
                String sTableName = mId;
                //未出库
                String str1 = "{'DeliveryState':0}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已出库
                String str2 = "{'DeliveryState':1}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    deliveryOk=obj1.getJSONArray("lines").length();
                    deliveryNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                deliveryAll=deliveryOk+deliveryNo;
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private void dealerDeliverSearch(final Handler handler,final String dId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.use(searchAdd);
                String sTableName = dId;
                //未售出
                String str1 = "{'SaleState':0}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已售出
                String str2 = "{'SaleState':1}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    saleOk=obj1.getJSONArray("lines").length();
                    saleNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saleAll=saleOk+saleNo;
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}