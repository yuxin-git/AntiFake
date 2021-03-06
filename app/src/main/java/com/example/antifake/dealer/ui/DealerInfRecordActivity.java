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

import static com.peersafe.base.client.enums.Command.submit;

public class DealerInfRecordActivity extends AppCompatActivity {
    private EditText editTextId=null;
    private ImageButton btnScan=null;
    private EditText editTextSaleType=null;
    private EditText editTextCusTel=null;
    private EditText editTextCusAdd=null;
    private EditText editTextdeliNum=null;
    private EditText editTextCusName=null;
    private Button btnReOk;
    private Button btnReCancel;
    private Integer id;
    private String saleType;
    private String cusTel;
    private String cusName;
    private String cusAdd;
    private String deliveryNum;
    private String deDate;
    private Chainsql c = new Chainsql();
    private String address=null;
    private String secret=null;
    private String userCert=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_inf_record);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        editTextId=findViewById(R.id.editText_id);
        editTextSaleType=findViewById(R.id.editText_sale_type);
        editTextCusTel=findViewById(R.id.editText_cus_tel);
        editTextCusName=findViewById(R.id.editText_cus_name);
        editTextCusAdd=findViewById(R.id.editText_cus_add);
        editTextdeliNum=findViewById(R.id.editText_delivery_num);
        btnReOk=findViewById(R.id.button_dealer_off_record);
        btnReCancel=findViewById(R.id.button_dealer_off_cancel);

        btnScan=findViewById(R.id.imageButton_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(DealerInfRecordActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*?????????????????????????????????????????????????????????????????????????????????*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        btnReOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleType = editTextSaleType.getText().toString();
                cusName= editTextCusName.getText().toString();
                cusTel= editTextCusTel.getText().toString();
                cusAdd=editTextCusAdd.getText().toString();
                deliveryNum=editTextdeliNum.getText().toString();
                id= Integer.valueOf(editTextId.getText().toString());
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(DealerInfRecordActivity.this,
                                    "???ID???????????????????????????", Toast.LENGTH_LONG).show();
                        }
                        else if(msg.what==1){
                            Toast.makeText(DealerInfRecordActivity.this,
                                    "???????????????", Toast.LENGTH_LONG).show();
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
                            hash.record(id,2,ledIndex);
                        }
                            DealerInfRecordActivity.this.finish();
                    };

                };
                currentDate cDate=new currentDate();
                deDate=cDate.getcurrentDate();
                deOffInsert(handler,id,saleType,cusName,cusTel,cusAdd,deliveryNum,deDate);
            }
        });
        btnReCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealerInfRecordActivity.this.finish();
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

    private void deOffInsert(final Handler handler,final int id,final String saleType,
                             final String cusName, final String cusTel,
                             final String cusAdd,final String deliveryNum,
                             final String deDate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address, secret);
                c.useCert(userCert);
                String sTableName = null;
                //??????????????????????????????????????????
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
                // ?????? id ?????? id ?????????
                String str1 = "{'id':" + id + "}";
                JSONObject obj = c.table(sTableName).get(c.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]")) {
                        handler.sendEmptyMessage(0);
                    } else {
                        c.use(address);
                        // ??????sTableName?????????????????????.
                        String record = "{'SaleState':'1','SaleDate':'" + deDate
                                + "','SaleType':'"+saleType+"','CustomerName':'" + cusName
                                + "','CustomerTel':'" + cusTel + "','DealerNum':'" + sTableName
                                + "','DeliveryNum':'" + deliveryNum + "','CustomerAdd':'" + cusAdd + "'}";
                        JSONObject obj2 = c.table(sTableName).get(c.array(str1))
                                .update(record).submit(Submit.SyncCond.db_success);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }
        }).start();
    }
}