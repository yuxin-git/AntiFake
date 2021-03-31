package com.example.antifake.brand.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.antifake.R;
import com.example.antifake.regulator.ui.ReguInfRecordActivity;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;


import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BrandAuthManuActivity extends AppCompatActivity {
    private EditText editTextManuNum=null;
    private EditText editTextProNum=null;
    private EditText editTextQuality=null;
    private EditText editTextIdBegin=null;
    private String address=null;
    private String secret=null;
    private String userCert=null;
    private Button btnOk;
    private Button btnCancel;
    private String manuNum;
    private Integer proNum;
    private Integer quality;
    private Integer idBegin;
    private Chainsql c = new Chainsql();
    private Integer i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_auth_manu);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        editTextManuNum=findViewById(R.id.editText_manu_num);
        editTextProNum=findViewById(R.id.editText_pro_num);
        editTextQuality=findViewById(R.id.editText_quanlity);
        editTextIdBegin=findViewById(R.id.editText_id_begin);
        btnOk=findViewById(R.id.button_auth_ok);
        btnCancel=findViewById(R.id.button_auth_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manuNum= editTextManuNum.getText().toString();
                proNum= Integer.valueOf(editTextProNum.getText().toString());
                quality= Integer.valueOf(editTextQuality.getText().toString());
                idBegin= Integer.valueOf(editTextIdBegin.getText().toString());
                System.out.println("1");
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(BrandAuthManuActivity.this,
                                    "授权成功！", Toast.LENGTH_LONG).show();
                        } else if(msg.what==1){
                            Toast.makeText(BrandAuthManuActivity.this,
                                    "授权失败！不存在该品类商品", Toast.LENGTH_LONG).show();
                        } else if(msg.what==2){
                            Toast.makeText(BrandAuthManuActivity.this,
                                    "该ID已存在，授权失败！", Toast.LENGTH_LONG).show();
                        }
                        BrandAuthManuActivity.this.finish();
                    };

                };
                System.out.println("2");
                insert(handler,manuNum,proNum,quality,idBegin);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandAuthManuActivity.this.finish();
            }
        });


    }

    public void insert(final Handler handler,final String manuNum,final int proNum,final int quality,final int idBegin){
        final int[] id = {idBegin};
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3");
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address,secret);
                String str1 = "{'ProductTypeNum':" + proNum + "}";
                c.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                c.useCert(userCert);
                String table="com_list";    //商品类型对应表
                JSONObject objProtype = c.table(table).get(c.array(str1)).submit();
                try {
                    if (objProtype.getString("lines").equals("[]"))
                        handler.sendEmptyMessage(1);
                    else{
                        String proName=objProtype.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductName");
                        i=0;
                        Integer id=0;
                        String sTableName = "com_infor";    //品牌商商品信息总表
                        while(i<quality){
                            id=i+idBegin;
                            String record="{id:"+ id +", 'ManufacturerNum':"+manuNum
                                    +", 'ProductTypeNum':"+proNum +", 'ProductName':'"+proName+"'}";
                            JSONObject obj =  c.table(sTableName).insert(c.array(record))
                                    .submit(Submit.SyncCond.db_success);
                            if(obj.has("error_message")){
                                System.out.println(obj);
                            }else {
                                System.out.println( "status" + obj.getString("status"));
                            }
                            i++;
                        }
                        handler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}