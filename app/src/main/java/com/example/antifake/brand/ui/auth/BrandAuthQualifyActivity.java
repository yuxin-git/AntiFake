package com.example.antifake.brand.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.antifake.R;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class BrandAuthQualifyActivity extends AppCompatActivity {
    private Button btnOk;
    private Button btnCancel;
    private Chainsql c=new Chainsql();
    private String address=null;
    private String secret=null;
    private String accountType=null;
    private String accountId=null;
    private String accountAdd=null;
    private String accountName=null;
    private EditText editTextAccountId=null;
    private EditText editTextAccountAdd=null;
    private EditText editTextAccountName=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_auth_qualify);
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        secret=intent.getStringExtra("secret");

        Spinner textSpinner = findViewById(R.id.spinner_type);
        editTextAccountAdd=findViewById(R.id.editText_qualify_address);
        editTextAccountName=findViewById(R.id.editText_qualify_name);
        editTextAccountId=findViewById(R.id.editText_qualify_id);

        btnOk=findViewById(R.id.button_qualify_ok);
        btnCancel=findViewById(R.id.button_qualify_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=textSpinner.getSelectedItem().toString();
                if(type.equals("生产商"))
                    accountType="M";
                else if(type.equals("经销商"))
                    accountType="D";
                else if(type.equals("监管机构"))
                    accountType="R";
                accountId=editTextAccountId.getText().toString();
                accountAdd=editTextAccountAdd.getText().toString();
                accountName=editTextAccountName.getText().toString();

                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        if(msg.what==0) {
                            Toast.makeText(BrandAuthQualifyActivity.this,
                                    "授权成功！", Toast.LENGTH_LONG).show();
                        }
                        BrandAuthQualifyActivity.this.finish();
                    };

                };
                insertQualify(handler,accountType,accountId,accountName,accountAdd);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandAuthQualifyActivity.this.finish();
            }
        });
    }

    private void insertQualify(final Handler handler, final String acType,
                               final String acId, final String acName, final String acAdd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address,secret);
                String table="address_list";    //账户地址信息表
                String record="{AccountId:'"+ acId +"', 'AccountType':'"+acType
                        +"', 'AccountName':'"+acName +";, 'AccountAdd':'"+acAdd+"'}";
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