package com.example.antifake.manufacturer.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.antifake.R;
import com.example.antifake.funClass.HashOperation;
import com.example.antifake.funClass.currentDate;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class ManuInfRecordActivity extends AppCompatActivity {

    private ImageButton btnScan=null;
    private EditText editTextManuRedId=null;
    private Button buttonManuRedOk=null;
    private Button buttonManuRedCancel=null;
    private Integer id;
    private String manuDate;
    private Chainsql c = new Chainsql();
    private String address=null;
    private String secret=null;
    private String userCert=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_inf_record);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        editTextManuRedId=findViewById(R.id.editText_manu_id_red);
        btnScan=findViewById(R.id.imageButton_scan);
        buttonManuRedOk=findViewById(R.id.button_manu_red_ok);
        buttonManuRedCancel=findViewById(R.id.button_manu_red_cancel);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ManuInfRecordActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        buttonManuRedOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id= Integer.valueOf(editTextManuRedId.getText().toString());
                @SuppressLint("HandlerLeak") Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(ManuInfRecordActivity.this,
                                    "该ID未被授权，入库失败！", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==1){
                            Toast.makeText(ManuInfRecordActivity.this,
                                    "入库成功！", Toast.LENGTH_LONG).show();
                            int ledIndex= 0;
                            try {
                                ledIndex = c.getLedgerVersion()
                                        .getInt("ledger_current_index")-1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            HashOperation hash=new HashOperation();
                            hash.setAddress(address);
                            hash.setSecret(secret);
                            hash.record(id,1,ledIndex);


                        }
                        ManuInfRecordActivity.this.finish();
                    };

                };
                currentDate cDate=new currentDate();
                manuDate=cDate.getcurrentDate();
                manuInsert(handler,id,manuDate);
            }
        });

        buttonManuRedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManuInfRecordActivity.this.finish();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                editTextManuRedId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void manuInsert(final Handler handler,final int id,final String manuDate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert(userCert);
                String sTableName = null;
                //通过账户地址查询该生产商编号
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
                String table = "com_infor";
                // 更新 id 等于 id 的记录
                String str1 = "{'id':" + id + "}";
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");

                JSONObject obj = c.table(table).get(c.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(0);
                    else if(obj.getString("lines").contains(sTableName)==false)
                        handler.sendEmptyMessage(0);
                    else {
                        String proTypeNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductTypeNum");
                        String proName=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductName");
                        c.use(address);

                        // 向表sTableName中插入一条记录.
                        String record="{ID:"+ id +",'DeliveryState':0, 'ManufacturerNum':'" +sTableName
                                +"','ProductTypeNum':'"+proTypeNum+"','ProductName':'"+proName
                                +"', 'ProductDate':'"+manuDate+"'}";
                        JSONObject obj2 = c.table(sTableName).insert(c.array(record))
                                .submit(Submit.SyncCond.db_success);
                        if(obj2.has("error_message")){
                            String str = obj2.getString("error_message");
                            if(str.indexOf("Duplicate entry") != -1)
                                handler.sendEmptyMessage(1);
                            else
                                handler.sendEmptyMessage(1);
                        }else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }).start();
    }



}