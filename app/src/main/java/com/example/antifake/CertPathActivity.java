package com.example.antifake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
    private Button bt;
    private TextView tx;
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "ChooseFile";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_path);
        bt = (Button) findViewById(R.id.bt);
        tx = (TextView) findViewById(R.id.tx);
        bt.setOnClickListener(new View.OnClickListener() {
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
                        String s = loadFile(path);
                        String str = readFile(path);
                        Log.e(TAG, "读取到的数据：" + str);
                        tx.setText("文件中读取到的数据：\n" + str);
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

    //获取选择文件的路径
    public String getPath(Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    //根据路径及文件名加载文件数据
    public String loadFile(String fileName) {
        String data = "";
        try {
            File file = new File(fileName);
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader
                        = new InputStreamReader(instream, "utf-8");
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                while ((line = buffreader.readLine()) != null) {
                    data += line;
                }
                instream.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "****Load " + fileName + " Error****");
            Log.e(TAG, "loadFile: " + e.toString());
            e.printStackTrace();
        }
        return data;
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