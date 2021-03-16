package com.example.antifake;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.antifake.dealer.ui.DealerInfRecordInetActivity;
import com.example.antifake.manufacturer.ui.ManuInfRecordActivity;
import com.example.antifake.qrscan.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

public class SearchInfomationActivity extends AppCompatActivity {

    private EditText editTextId=null;
    private ImageButton btnScan=null;
    private Button btnSearch=null;
    private Integer id=null;
    public Chainsql cClient = new Chainsql();
    //查询结果,定义为全局变量
    private String proName=null;
    private String manuName=null;
    private String dealerName=null;
    private String reguName=null;
    private String productDate=null;
    private String saleDate=null;
    private String saleType=null;
    private String cusName=null;
    private String cusTel=null;
    private String cusAdd=null;
    private String deliveryNum=null;
    private String salePlaceName=null;
    private String reguDate=null;
    private String reguResult=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_infomation);
        editTextId=findViewById(R.id.editText_id);
        btnScan=findViewById(R.id.imageButton_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(SearchInfomationActivity.this);
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
            }
        });

        btnSearch=findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id= Integer.valueOf(editTextId.getText().toString());
                if(null==id) {
                    Toast.makeText(SearchInfomationActivity.this, "输入为空！", Toast.LENGTH_LONG).show();
                } else{
                    Handler handler= new Handler() {
                        public void handleMessage(Message msg){
                            if(msg.what==0) {
                                //查询失败弹窗
                                ImageView img = new ImageView(SearchInfomationActivity.this);
                                img.setImageResource(R.drawable.ic_error);
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchInfomationActivity.this);
                                builder.setIcon(R.drawable.ic_search)
                                        .setTitle("查询结果")
                                        .setMessage("        查询失败,该商品ID不存在！")
                                        .setView(img)
                                        .setNegativeButton("确定", null);
                                builder.create().show();
                            }else if(msg.what==1) {
                                ImageView img = new ImageView(SearchInfomationActivity.this);
                                img.setImageResource(R.drawable.ic_correct);
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchInfomationActivity.this);
                                builder.setIcon(R.drawable.ic_search)
                                        .setTitle("查询结果")
                                        .setMessage("                   查询成功！")
                                        .setView(img)
                                        .setNegativeButton("返回", null)
                                        .setPositiveButton("查看防伪溯源结果", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //溯源结果
                                                String result="\n----------------------------------------------------------"
                                                        +"\n商品名称："+proName
                                                        +"\n----------------------------------------------------------"
                                                        +"\n生产商名称："+manuName
                                                        +"\n生产日期："+productDate
                                                        +"\n----------------------------------------------------------"
                                                        +"\n质检机构名称："+reguName
                                                        +"\n质检日期："+reguDate
                                                        +"\n质检结果："+reguResult
                                                        +"\n----------------------------------------------------------"
                                                        +"\n经销商名称："+dealerName
                                                        +"\n销售日期："+saleDate
                                                        +"\n销售形式："+saleType
                                                        +"\n顾客姓名："+cusName
                                                        +"\n顾客电话："+cusTel
                                                        +"\n顾客地址："+cusAdd
                                                        +"\n物流单号："+deliveryNum
                                                        +"\n销售门店："+salePlaceName
                                                        +"\n----------------------------------------------------------";
                                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchInfomationActivity.this);
                                                builder.setIcon(R.drawable.ic_search)
                                                        .setTitle("防伪溯源结果")
                                                        .setMessage(result)
                                                        .setNegativeButton("确定", null);
                                                builder.create().show();

                                            }
                                        });
                                builder.create().show();
                            }

                        };

                    };
                    search(handler,id);
                }

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

    private void search(final Handler handler,final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cClient.connect(getString(R.string.severIP_1));
                cClient.connection.client.logger.setLevel(Level.SEVERE);
                cClient.as(getString(R.string.client_address),getString(R.string.client_secret));
                String tableCom = "com_infor";
                String str1 = "{'id':" + id + "}";
                cClient.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
                JSONObject obj = cClient.table(tableCom).get(cClient.array(str1)).submit();
                try {
                    if (obj.getString("lines").equals("[]")) {
                        handler.sendEmptyMessage(0);
                    } else {
                        proName=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ProductName");
                        String manuNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("ManufacturerNum");
                        queryManuInfor(id,manuNum);
                        String dealerNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("DealerNum");
                        queryDealerInfor(id,dealerNum);
                        String reguNum=obj.getJSONArray("lines")
                                .getJSONObject(0).getString("RegulatorNum");
                        queryReguInfor(id,reguNum);
                        handler.sendEmptyMessage(1);

                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }
        }).start();
    }

    //查询生产商相关信息
    private void queryManuInfor(final int id,final String manuNum){
        //查找品牌商的账户地址信息表，查询出对应生产商的名称及账户地址
        cClient.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
        String tableAccount="address_list";//账户信息表，查找生产商名称及账户地址
        String str1 = "{'AccountId':'" + manuNum + "'}";
        JSONObject obj1 = cClient.table(tableAccount).get(cClient.array(str1)).submit();
        String manuAdd=null;
        try {
            manuName=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountName");
            manuAdd=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountAdd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //查找对应生产商的生产信息表，查询出对应的生产日期
        cClient.use(manuAdd);
        String str2="{'id':" + id + "}";
        JSONObject obj2 = cClient.table(manuNum).get(cClient.array(str2)).submit();
        try {
            productDate=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("ProductDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //查询经销商相关信息
    private void queryDealerInfor(final int id,final String dealerNum){
        //查找品牌商的账户地址信息表，查询出对应经销商的名称及账户地址
        cClient.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
        String tableAccount="address_list";//账户信息表，查找生产商名称及账户地址
        String str1 = "{'AccountId':'" + dealerNum + "'}";
        JSONObject obj1 = cClient.table(tableAccount).get(cClient.array(str1)).submit();
        String dealerAdd=null;
        try {
            dealerName=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountName");
            dealerAdd=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountAdd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //查找对应生产商的生产信息表，查询出对应的生产日期
        cClient.use(dealerAdd);
        String str2="{'id':" + id + "}";
        JSONObject obj2 = cClient.table(dealerNum).get(cClient.array(str2)).submit();
        try {
            saleDate=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("SaleDate");
            String type=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("SaleType");
            if(type.equals("0"))
                saleType="线上";
            else if(type.equals("1"))
                saleType="线下";
            cusName=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerName");
            cusTel=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerTel");
            cusAdd=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerAdd");
            deliveryNum=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("DeliveryNum");
            salePlaceName=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("SalePlaceName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //查询监管机构相关信息
    private void queryReguInfor(final int id,final String reguNum){
        //查找品牌商的账户地址信息表，查询出对应监管机构的名称及账户地址
        cClient.use("zEX33AirGeFUyY4H56viye5hp5J9WwKUv3");
        String tableAccount="address_list";//账户信息表，查找生产商名称及账户地址
        String str1 = "{'AccountId':'" + reguNum + "'}";
        JSONObject obj1 = cClient.table(tableAccount).get(cClient.array(str1)).submit();
        String reguAdd=null;
        try {
            reguName=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountName");
            reguAdd=obj1.getJSONArray("lines")
                    .getJSONObject(0).getString("AccountAdd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //查找对应监管机构的质检信息表，查询出对应质检日期和质检结果
        cClient.use(reguAdd);
        String str2="{'id':" + id + "}";
        JSONObject obj2 = cClient.table(reguNum).get(cClient.array(str2)).submit();
        try {
            reguDate=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("RegulatorDate");
            reguResult=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("RegulatorResult");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}