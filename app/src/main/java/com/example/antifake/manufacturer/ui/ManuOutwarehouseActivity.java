package com.example.antifake.manufacturer.ui;

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
import com.example.antifake.SearchInfomationActivity;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class ManuOutwarehouseActivity extends AppCompatActivity {

    private ImageButton btnScan=null;
    private EditText editTextOutId=null;
    private EditText editTextOutDeNum=null;
    private Button btnOutOk=null;
    private Button btnOutCancel=null;
    private Integer id;
    private Integer deNum;
    private Chainsql c = new Chainsql();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_outwarehouse);
        editTextOutId=findViewById(R.id.editText_id);
        editTextOutDeNum=findViewById(R.id.editText_manu_red_denum);
        btnOutOk=findViewById(R.id.button_out_ok);
        btnOutCancel=findViewById(R.id.button_out_cancel);
        btnScan=findViewById(R.id.imageButton_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ManuOutwarehouseActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });
        btnOutOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id= Integer.valueOf(editTextOutId.getText().toString());
                deNum= Integer.valueOf(editTextOutDeNum.getText().toString());
                System.out.println("1");
                Handler handler= new Handler() {
                    public void handleMessage(Message msg){
                        Toast.makeText(ManuOutwarehouseActivity.this, "出库成功！", Toast.LENGTH_LONG).show();
                        ManuOutwarehouseActivity.this.finish();
                    };

                };
                System.out.println("2");
                manuOut(handler,id,deNum);
            }
        });
        btnOutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManuOutwarehouseActivity.this.finish();
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
                editTextOutId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void manuOut(final Handler handler,final int id,final int deNum){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3");
                String address="zKhdUEXNWMYG3uEquQkhGvYM3mZRGqYqNf";
                String secret="xp1vcANddqbBhbfEr8i624pXcA5B4";
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address,secret);
                String sTableName = "manu_list_01";
                // 向表sTableName中插入一条记录.
                //待完善，更新表
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}