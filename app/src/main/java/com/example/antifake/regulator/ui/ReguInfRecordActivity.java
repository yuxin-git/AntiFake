package com.example.antifake.regulator.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.antifake.brand.ui.auth.BrandAuthManuActivity;
import com.example.antifake.currentDate;
import com.example.antifake.dealer.ui.DealerInfRecordInetActivity;
import com.example.antifake.dealer.ui.DealerInfRecordOfflineActivity;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.logging.Level;

public class ReguInfRecordActivity extends AppCompatActivity {
    private EditText editTextId=null;
    private ImageButton btnScan=null;
    private EditText editTextReguName=null;
    private EditText editTextReguResult=null;
    private Button btnReguOk=null;
    private Button btnReguCancel=null;
    private Integer reguId=null;
    private String reguName=null;
    private String reguResult=null;
    private String reguDate=null;
    private Chainsql c = new Chainsql();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regu_inf_record);

        editTextId=findViewById(R.id.editText_id);
        btnScan=findViewById(R.id.imageButton_scan);
        editTextReguName=findViewById(R.id.editText_regu_name);
        editTextReguResult=findViewById(R.id.editText_regu_result);
        btnReguOk=findViewById(R.id.button_regu_ok);
        btnReguCancel=findViewById(R.id.button_regu_cancel);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ReguInfRecordActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        btnReguOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reguId= Integer.valueOf(editTextId.getText().toString());
                reguName= editTextReguName.getText().toString();
                reguResult= editTextReguResult.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(ReguInfRecordActivity.this,
                                    "该ID未被授权，登记失败！", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==1){
                            Toast.makeText(ReguInfRecordActivity.this,
                                    "登记成功！", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==2){
                            Toast.makeText(ReguInfRecordActivity.this,
                                    "该ID已被登记，登记失败！", Toast.LENGTH_LONG).show();
                        }
                        ReguInfRecordActivity.this.finish();
                    };

                };
                currentDate cDate=new currentDate();
                reguDate=cDate.getcurrentDate();
                reguInsert(handler,reguId,reguName,reguResult,reguDate);
            }
        });
        btnReguCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReguInfRecordActivity.this.finish();
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
                editTextId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void reguInsert(final Handler handler,final int idregu,final String nameregu,final String resultregu,final  String dateregu){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String address="zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z";
                String secret="xnejoG6irLTUgNgELJM5Y5ipsSwDT";
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                String sTableName = "R001";
                String table = "com_infor";
                // 更新 id 等于 id 的记录
                String str1 = "{'id':" + idregu + "}";
                String str2 = "{'RegulatorNum':'" + sTableName + "'}";
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                JSONObject obj = c.table(table).get(c.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]")) {
                        handler.sendEmptyMessage(0);
                    } else {
                        JSONObject obj1 = c.table(table).get(c.array(str1)).update(str2).submit(Submit.SyncCond.db_success);
                        c.use("zL36kWKGdqx9fXK4dzVc95ErriGuCQng5z");
                        // 向表sTableName中插入一条记录.
                        String record="{id:"+ idregu +", 'WorkerNum':"+nameregu+",'RegulatorNum':"
                                +sTableName+",'RegulatorDate':'"+dateregu+"', 'RegulatorResult':"+resultregu+"}";
                        JSONObject obj2 = c.table(sTableName).insert(c.array(record))
                                .submit(Submit.SyncCond.db_success);
                        if(obj2.has("error_message")){
                            String str = obj2.getString("error_message");
                            if(str.contains("Duplicate entry"))
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