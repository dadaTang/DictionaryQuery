package xtanapp.xtapp.com.dictionaryquery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class QueryDictionaryActivity extends Activity {
    private EditText et_index;
    private Button bt_query;
    private TextView tv_query_result;
    private String mAddress;

    private RadioGroup rg_chooseDictionary;
    private RadioButton rb_Chinese;
    private RadioButton rb_CN_EN;
    private RadioButton rb_Idio;

    private int DICTIONARY_TYPE = 0;

    private Handler mHhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 4,空间使用查询结果
            tv_query_result.setText(mAddress);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);
        initUI();

    }

    private void initUI() {
        rg_chooseDictionary = findViewById(R.id.rg_chooseDictionary);
        rb_Chinese = findViewById(R.id.rb_Chinese);
        rb_CN_EN = findViewById(R.id.rb_CN_EN);
        rb_Idio = findViewById(R.id.rb_Idio);

        et_index = findViewById(R.id.et_index);
        bt_query = findViewById(R.id.bt_query);
        tv_query_result = findViewById(R.id.tv_query_result);
        tv_query_result.setMovementMethod(ScrollingMovementMethod.getInstance());


        rg_chooseDictionary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb_Chinese.getId() == checkedId) {
                    DICTIONARY_TYPE = 0;

                }
                if (rb_CN_EN.getId() == checkedId) {
                    DICTIONARY_TYPE = 1;

                }
                if (rb_Idio.getId() == checkedId) {
                    DICTIONARY_TYPE = 2;
                }

            }
        });

        // 点击查询功能
        bt_query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String indexString = et_index.getText().toString().trim();
                if (!TextUtils.isEmpty(indexString)) {
                    // 查询耗时操作,开启子线程
                    query(indexString, DICTIONARY_TYPE);
                } else {
                    Toast.makeText(getApplicationContext(), "您输入的为空!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    /**
     * 耗时操作查询
     */
    private void query(final String string, final int type) {
        new Thread() {
            @Override
            public void run() {
                if (type == 0) {
                    mAddress = ChineseDictionartDao.getValue(string);
                    mHhandler.sendEmptyMessage(0);
                }
                if (type == 1) {
                    mAddress = CNENDictionartDao.getValue(string);
                    mHhandler.sendEmptyMessage(0);
                } if (type == 2) {
                    mAddress = IdioDictionartDao.getValue(string);
                    mHhandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

}
