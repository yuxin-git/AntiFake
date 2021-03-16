package com.example.antifake.dealer.ui;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.antifake.R;
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.brand.ui.auth.BrandAuthManuActivity;
import com.example.antifake.currentDate;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.base.client.Client;
import com.peersafe.base.client.pubsub.Publisher;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.logging.Level;

public class  DealerInfRecordInetActivity extends AppCompatActivity {
    private TextView textViewId=null;
    private ImageButton btnScan=null;
    private EditText editTextCusName=null;
    private EditText editTextCusTel=null;
    private EditText editTextCusAdd=null;
    private EditText editTextDeliveryNum=null;
    private Button btnDeInetOk=null;
    private Button btnDeInetCancel=null;
    private Integer id;
    private String cusName;
    private String cusTel;
    private String cusAdd;
    private String deliverNum;
    private String deDate;
    private Chainsql c = new Chainsql();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_inf_record_inet);

        textViewId=findViewById(R.id.textView_de_inrec_id);
        editTextCusName=findViewById(R.id.editText_de_inrec_cus_name);
        editTextCusTel=findViewById(R.id.editText_de_inrec_cus_tel);
        editTextCusAdd=findViewById(R.id.editText_de_inrec_cus_add);
        editTextDeliveryNum=findViewById(R.id.editText_de_inrec_delivery_num);
        btnDeInetOk=findViewById(R.id.button_dealer_inet_record);
        btnDeInetCancel=findViewById(R.id.button_dealer_inet_cancel);
        btnScan=findViewById(R.id.imageButton_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(DealerInfRecordInetActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        btnDeInetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusName= editTextCusName.getText().toString();
                cusTel= editTextCusTel.getText().toString();
                cusAdd= editTextCusAdd.getText().toString();
                deliverNum=editTextDeliveryNum.getText().toString();
                id= Integer.valueOf(textViewId.getText().toString());
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(DealerInfRecordInetActivity.this,
                                    "该ID未被授权，登记失败！", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==1){
                            Toast.makeText(DealerInfRecordInetActivity.this,
                                    "登记成功！", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==2){
                            Toast.makeText(DealerInfRecordInetActivity.this,
                                    "该ID已被登记，登记失败！", Toast.LENGTH_LONG).show();
                        }
                        DealerInfRecordInetActivity.this.finish();
                    };

                };
                currentDate cDate=new currentDate();
                deDate=cDate.getcurrentDate();
                deInetInsert(handler,id,cusName,cusTel,cusAdd,deliverNum,deDate);
            }
        });


        btnDeInetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealerInfRecordInetActivity.this.finish();
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
                textViewId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void deInetInsert(final Handler handler,final int id,final String cusName,
                              final String cusTel,final String cusAdd,final String deliverNum,final String deDate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String address="zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR";
                String secret="xhpgTk8ALwWMugf921ak9eJdqtG4Q";
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                String sTableName = "D001";
                String table = "com_infor";
                // 更新 id 等于 id 的记录
                String str1 = "{'id':" + id + "}";
                String str2 = "{'DealerNum':'" + sTableName + "'}";
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                JSONObject obj = c.table(table).get(c.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]")) {
                        handler.sendEmptyMessage(0);
                    } else {
                        String proTypeNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductTypeNum");
                        String proName=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductName");
                        c.table(table).get(c.array(str1)).update(str2).submit(Submit.SyncCond.db_success);
                        c.use("zNoePXrfYvz8jvDiDNr3RNi4PwtBYhQxAR");
                        // 向表sTableName中插入一条记录.
                        String record="{ID:"+ id
                                +",'ProductTypeNum':'"+proTypeNum+"','ProductName':'"+proName
                                +"','SaleState':'1','SaleType':'0', 'CustomerName':'"+cusName
                                +"', 'CustomerTel':'"+cusTel +"', 'CustomerAdd':'"+cusAdd
                                +"', 'DeliveryNum':'"+deliverNum+"','DealerNum':'"+sTableName
                                +"', 'SaleDate':'"+deDate+"'}";
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