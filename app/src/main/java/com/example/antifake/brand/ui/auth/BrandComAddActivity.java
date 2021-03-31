package com.example.antifake.brand.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antifake.R;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONObject;

import java.util.logging.Level;

public class BrandComAddActivity extends AppCompatActivity {

    private Button btnOk;
    private Button btnCancel;
    private String address=null;
    private String secret=null;
    private String userCert=null;
    private EditText editTextComTypeId;
    private EditText editTextComName;
    private Chainsql c=new Chainsql();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_com_add);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");
        userCert=intent.getStringExtra("userCert");
        editTextComTypeId=findViewById(R.id.editText_com_type);
        editTextComName=findViewById(R.id.editText_dealer_com_name);

        btnOk=findViewById(R.id.button_comadd_ok);
        btnCancel=findViewById(R.id.button_comadd_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int comTypeId= Integer.parseInt(editTextComTypeId.getText().toString());
                String comName=editTextComName.getText().toString();
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(BrandComAddActivity.this,
                                    "授权成功！", Toast.LENGTH_LONG).show();
                        }
                        BrandComAddActivity.this.finish();
                    };

                };
                insertCom(handler,comTypeId,comName);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandComAddActivity.this.finish();
            }
        });

    }

    private void insertCom(final Handler handler, final int comId, final String comName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address,secret);
                c.useCert(userCert);
                c.use(address);
                String table="com_list";    //账户地址信息表
                String record="{'ProductTypeNum':'"+ comId +"', 'ProductName':'"+comName+"'}";
                JSONObject obj =  c.table(table).insert(c.array(record))
                        .submit(Submit.SyncCond.db_success);
                if(obj.has("error_message")){
                    System.out.println(obj);
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

}