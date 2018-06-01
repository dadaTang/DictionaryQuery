package xtanapp.xtapp.com.dictionaryquery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button btn_next;

    private String path = null;
    private static boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initPression();
    }

    private void initPression() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

                Toast.makeText(getApplicationContext(), "xuyaoquanx", Toast.LENGTH_SHORT).show();
            } else {
                initDB();
            }
        } else {
            initDB();
        }
    }


    private void initDB() {
        boolean sdCardExist= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist){
            //判断数据文件是否存在

            //将数据文件存放到指定目录
            FileUtils.getInstance(getApplication().getApplicationContext() ).copyAssetsToSD("","zfile");
        }else{
            Toast.makeText(getApplication(),"放弃吧",Toast.LENGTH_SHORT).show();
        }

    }



    private void initUI() {
        btn_next = findViewById(R.id.btn_next);
        // 点击查询功能
        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        QueryDictionaryActivity.class));
            }
        });
    }

}
