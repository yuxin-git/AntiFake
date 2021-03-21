package com.example.antifake.dealer.ui;

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
import com.example.antifake.manufacturer.ui.ManuInfRecordActivity;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class DealerComeinRecordActivity extends AppCompatActivity {
    private Chainsql c = new Chainsql();
    private String address=null;
    private String secret=null;
    private ImageButton btnScan=null;
    private Button btnComeinOk=null;
    private Button btnComeinCancel=null;
    private EditText editTextComeinId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_comein_record);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        editTextComeinId=findViewById(R.id.editText_comein_id);
        btnScan=findViewById(R.id.imageButton_scan);
        btnComeinOk=findViewById(R.id.button_comein_ok);
        btnComeinCancel=findViewById(R.id.button_comein_cancel);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(DealerComeinRecordActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        btnComeinOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= Integer.valueOf(editTextComeinId.getText().toString());
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(DealerComeinRecordActivity.this,
                                    "该ID未被授权，入库失败！", Toast.LENGTH_LONG).show();
                        } else if(msg.what==1){
                            Toast.makeText(DealerComeinRecordActivity.this,
                                    "入库成功！", Toast.LENGTH_LONG).show();
                        }else if(msg.what==2){
                            Toast.makeText(DealerComeinRecordActivity.this,
                                    "该ID已被登记，登记失败！", Toast.LENGTH_LONG).show();
                        }
                        DealerComeinRecordActivity.this.finish();
                    };

                };
                dealerInsert(handler,id);
            }
        });

        btnComeinCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealerComeinRecordActivity.this.finish();
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
                editTextComeinId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void dealerInsert(final Handler handler,final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                String sTableName = "D001";
                String table = "com_infor";
                // 更新 id 等于 id 的记录
                String str1 = "{'id':" + id + "}";
                String str2="{'DealerNum':'"+sTableName+"'}";
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                JSONObject obj = c.table(table).get(c.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(0);
                    else {
                        String proTypeNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductTypeNum");
                        String proName=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductName");
                        JSONObject obj1 = c.table(table).get(c.array(str1))
                                .update(str2).submit(Submit.SyncCond.db_success);
                        c.use(address);
                        // 向表sTableName中插入一条记录.
                        String record="{ID:"+ id +",'SaleState':0, 'DealerNum':'" +sTableName
                                +"','ProductTypeNum':'"+proTypeNum+"','ProductName':'"+proName +"'}";
                        JSONObject obj2 = c.table(sTableName).insert(c.array(record))
                                .submit(Submit.SyncCond.db_success);
                        if(obj2.has("error_message")){
                            String str = obj2.getString("error_message");
                            if(str.indexOf("Duplicate entry") != -1)
                                handler.sendEmptyMessage(2);
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