package com.example.antifake.manufacturer.ui;

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

public class ManuInventorySearchActivity extends AppCompatActivity {
    private Chainsql c=new Chainsql();
    private String address=null;
    private String secret=null;
    private EditText editTextComId;
    private EditText editTextComName;
    private Button btnOk;
    private String comId=null;
    private String comName=null;
    private int sendOk=0;
    private int sendNo=0;
    private int all=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_inventory_search);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        editTextComId=findViewById(R.id.editText_manu_com_id);
        editTextComName=findViewById(R.id.editText_manu_com_name);
        btnOk=findViewById(R.id.button_manu_inventory_search);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comId=editTextComId.getText().toString();
                comName=editTextComName.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==1){
                            String show="未出库 ： "+sendNo
                                    +"\n\n已出库 ："+sendOk
                                    +"\n\n共计 ："+all;
                            AlertDialog.Builder builder = new AlertDialog.Builder(ManuInventorySearchActivity.this);
                            builder.setIcon(R.drawable.ic_search)
                                    .setTitle("生产库存查询")
                                    .setMessage(show)
                                    .setNegativeButton("确定", null);
                            builder.create().show();
                        }
                    };

                };
                if(comId.isEmpty()&&comName.isEmpty()) {
                    Toast.makeText(ManuInventorySearchActivity.this,
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
                String sTableName = "M001";
                //未售
                String str1 = "{'ProductTypeNum':'" + num + "','DeliveryState':1}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已售
                String str2 = "{'ProductTypeNum':'" + num + "','DeliveryState':0}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    sendOk=obj1.getJSONArray("lines").length();
                    sendNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                all=sendNo+sendOk;
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
                String sTableName = "D001";
                //未售
                //未售
                String str1 = "{'ProductName':'" + name + "','DeliveryState':1}";
                JSONObject obj1 = c.table(sTableName).get(c.array(str1)).submit();
                //已售
                String str2 = "{'ProductName':'" + name + "','DeliveryState':0}";
                JSONObject obj2 = c.table(sTableName).get(c.array(str2)).submit();
                try {
                    sendOk=obj1.getJSONArray("lines").length();
                    sendNo=obj2.getJSONArray("lines").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                all=sendNo+sendOk;
                handler.sendEmptyMessage(1);
            }

        }).start();
    }

}