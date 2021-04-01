package com.example.antifake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import com.example.antifake.funClass.UriPath;

public class CertPathActivity extends AppCompatActivity {
    private Button btn_path;
    private Button btn_ok;
    private TextView tx;
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "ChooseFile";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String str=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_path);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        btn_path = (Button) findViewById(R.id.button_cert_select);
        tx = (TextView) findViewById(R.id.textView_cert_show);
        btn_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Android6.0需要申请权限
                int permission = ActivityCompat.checkSelfPermission(CertPathActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            CertPathActivity.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                } else {
                    //已经申请过
                    showFileChooser();
                }
            }
        });
        btn_ok=findViewById(R.id.button_cert_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("userCert", str);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                CertPathActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                CertPathActivity.this.finish();
            }
        });

    }

    //显示文件浏览器
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //包含所有类型，image/*  video/*
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装一个文件浏览器.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e(TAG, "文件Uri: " + uri.toString());
                    try {
                        UriPath uu = new UriPath();
                        String path = uu.getRealPathFromUri_AboveApi19(getApplicationContext(), uri);
                        //String path = uri.getPath();
                        Log.e(TAG, "选择的文件路径: " + path);
                        str = readFile(path);
                        Log.e(TAG, "读取到的数据：" + str);
                        tx.setText("证书内容：\n" + str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                showFileChooser();
                break;
        }
    }

    public static String readFile(String filename) {
        StringBuilder fileContent = new StringBuilder("");
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        String str = null;
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(filename));
                while ((str = bufferedReader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //closeIO(bufferedReader);
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContent.toString();
    }

}