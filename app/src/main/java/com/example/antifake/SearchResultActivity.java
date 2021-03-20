package com.example.antifake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.antifake.funClass.HashOperation;
import com.peersafe.chainsql.core.Chainsql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class SearchResultActivity extends AppCompatActivity {
    String result=null;
    private TextView textViewResult=null;
    public Chainsql cClient = new Chainsql();
    private int id;
    private TextView textViewId=null;
    private TextView textViewManu=null;
    private TextView textViewRegu=null;
    private TextView textViewDealer=null;
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
    private String reguDate=null;
    private String reguResult=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent=getIntent();
        id= Integer.parseInt(intent.getStringExtra("id"));
        textViewResult=findViewById(R.id.textView_result);
        textViewId=findViewById(R.id.textView_id);
        textViewManu=findViewById(R.id.textView_manu);
        textViewRegu=findViewById(R.id.textView_regu);
        textViewDealer=findViewById(R.id.textView_dealer);
        Handler handler= new Handler() {
            public void handleMessage(Message msg){
                System.out.println("查询成功");
                /*
                String result="----------------------------------------------------------"
                        +"\n商品ID："+id
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
                        +"\n----------------------------------------------------------";
                textViewResult.setText(result);

                 */
                String textId="商品ID："+id+"\n商品名称："+proName;
                String textManu="生产商名称："+manuName
                        +"\n生产日期："+productDate;
                String textRegu="质检机构名称："+reguName
                        +"\n质检日期："+reguDate
                        +"\n质检结果："+reguResult;
                String textDealer="经销商名称："+dealerName
                        +"\n销售日期："+saleDate
                        +"\n销售形式："+saleType
                        +"\n顾客姓名："+cusName
                        +"\n顾客电话："+cusTel
                        +"\n顾客地址："+cusAdd
                        +"\n物流单号："+deliveryNum;
                textViewId.setText(textId);
                textViewManu.setText(textManu);
                textViewRegu.setText(textRegu);
                textViewDealer.setText(textDealer);
            }

        };
        searchInfor(handler,id);

        //测试：查询区块信息，待完善
        HashOperation h=new HashOperation();
        JSONObject testb = null;
        JSONArray testa=new JSONArray();
        Object testc=null;
        try {
            testb = h.getManuLedgerInfor(id).getJSONObject("ledger");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            testa=testb.getJSONArray("transactions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            testc=testa.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String test=testc.toString();
        JSONObject dd=cClient.getTransaction(test);
        int timeStamp= 0;
        try {
            timeStamp = dd.getInt("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long time= timeStamp+946684800;
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time*1000);
        System.out.println("10位数的时间戳（秒）-----》Date:"+result1);
        Date date1 = new Date(time*1000);
        System.out.println(date1);
        long time3 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2021-03-19 20:07:17",new ParsePosition(0)).getTime()/1000;

        //区块高度，交易类型，完成时间，交易账户，交易hash






    }


    private void searchInfor(final Handler handler, final int id){
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
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                handler.sendEmptyMessage(1);
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
            saleType=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("SaleType");
            cusName=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerName");
            cusTel=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerTel");
            cusAdd=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("CustomerAdd");
            deliveryNum=obj2.getJSONArray("lines")
                    .getJSONObject(0).getString("DeliveryNum");
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