package com.example.antifake.dealer.ui;

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
import com.example.antifake.SearchResultActivity;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class DealerInventorySearchActivity extends AppCompatActivity {
    private Chainsql c=new Chainsql();
    private String address=null;
    private String secret=null;
    private String userCert=null;
    private EditText editTextComId;
    private EditText editTextComName;
    private Button btnOk;
    private String comId=null;
    private String comName=null;
    private int saleOk=0;
    private int saleNo=0;
    private int saleAll=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_inventory_search);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        editTextComId=findViewById(R.id.editText_dealer_com_id);
        editTextComName=findViewById(R.id.editText_dealer_com_name);
        btnOk=findViewById(R.id.button_inventory_search);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comId=editTextComId.getText().toString();
                comName=editTextComName.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==1){
                            String show="已售 ： "+saleOk
                                    +"\n\n未售 ："+saleNo
                                    +"\n\n共计 ："+saleAll;
                            AlertDialog.Builder builder = new AlertDialog.Builder(DealerInventorySearchActivity.this);
                            builder.setIcon(R.drawable.ic_search)
                                    .setTitle("销售库存查询")
                                    .setMessage(show)
                                    .setNegativeButton("确定", null);
                            builder.create().show();
                        }
                    };

                };
                if(comId.isEmpty()&&comName.isEmpty()) {
                    Toast.makeText(DealerInventorySearchActivity.this,
                            "输入为空，无法查询！", Toast.LENGTH_LONG).show();
                }else if(!(comId.isEmpty())&&comName.isEmpty()) {
                    int id = Integer.parseInt(comId);
                    searchId(handler,id);
                }else if(comId.isEmpty()&&!(comName.isEmpty())) {
                    searchName(handler,comName);
                }else if(!(comId.isEmpty())&&!(comName.isEmpty())) {
                    int id = Integer.parseInt(comId);
                    searchId(handler,id);
                }
            }
        });

    }

    private void searchId(final Handler handler,final int num){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert(userCert);
                String sTableName = null;
                //通过账户地址查询该经销商编号
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                String strAdd = "{'AccountAdd':'" + address + "'}";
                JSONObject objAdd = c.table("address_list").get(c.array(strAdd)).submit();
                try {
                    if (objAdd.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(3);
                    else {
                        sTableName = objAdd.getJSONArray("lines")
                                .getJSONObject(0).getString("AccountId");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //未售
                String str1 = "{'ProductTypeNum':'" + num + "','SaleState':1}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已售
                String str2 = "{'ProductTypeNum':'" + num + "','SaleState':0}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    saleOk=obj1.getJSONArray("lines").length();
                    saleNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saleAll=saleNo+saleOk;
                handler.sendEmptyMessage(1);
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
                c.useCert(userCert);
                String sTableName = null;
                //通过账户地址查询该经销商编号
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                String strAdd = "{'AccountAdd':'" + address + "'}";
                JSONObject objAdd = c.table("address_list").get(c.array(strAdd)).submit();
                try {
                    if (objAdd.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(3);
                    else {
                        sTableName = objAdd.getJSONArray("lines")
                                .getJSONObject(0).getString("AccountId");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //未售
                String str1 = "{'ProductName':'" + name + "','SaleState':1}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已售
                String str2 = "{'ProductName':'" + name + "','SaleState':0}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    saleOk=obj1.getJSONArray("lines").length();
                    saleNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saleAll=saleNo+saleOk;
                handler.sendEmptyMessage(1);
            }

        }).start();
    }
}