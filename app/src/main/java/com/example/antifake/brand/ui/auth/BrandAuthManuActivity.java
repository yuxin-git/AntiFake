package com.example.antifake.brand.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.antifake.R;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class BrandAuthManuActivity extends AppCompatActivity {
    private EditText editTextManuNum=null;
    private EditText editTextProNum=null;
    private EditText editTextQuality=null;
    private EditText editTextIdBegin=null;
    private Button btnOk;
    private Integer manuNum;
    private Integer proNum;
    private Integer quality;
    private Integer idBegin;
    private Chainsql c = new Chainsql();
    private Integer i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_auth_manu);
        editTextManuNum=findViewById(R.id.editText_manu_num);
        editTextProNum=findViewById(R.id.editText_pro_num);
        editTextQuality=findViewById(R.id.editText_quanlity);
        editTextIdBegin=findViewById(R.id.editText_id_begin);
        btnOk=findViewById(R.id.button_auth_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manuNum= Integer.valueOf(editTextManuNum.getText().toString());
                proNum= Integer.valueOf(editTextProNum.getText().toString());
                quality= Integer.valueOf(editTextQuality.getText().toString());
                idBegin= Integer.valueOf(editTextIdBegin.getText().toString());

                String address="zEX33AirGeFUyY4H56viye5hp5J9WwKUv3";
                String secret="xp1kUTT42HcwEWFxH9kkg6BGd1VBE";
                c.connect(getString(R.string.severIP_1));
                c.connection.client.logger.setLevel(Level.SEVERE);
                c.as(address,secret);
                while(i<quality){
                    String sTableName = "commodity_infor";
                    // 向表sTableName中插入一条记录.
                    String record="{id:"+idBegin+", 'ManufacturerNum':"+manuNum+", 'ProductTypeNum':"+proNum+"}";
                    JSONObject obj =  c.table(sTableName).insert(c.array(record))
                            .submit(Submit.SyncCond.db_success);

                    if(obj.has("error_message")){
                        System.out.println(obj);
                    }else {
                        try {
                            System.out.println( "status" + obj.getString("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    idBegin++;
                    i++;
                }

            }
        });


    }
}