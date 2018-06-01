package xtanapp.xtapp.com.dictionaryquery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class QueryDictionaryActivity extends Activity {
	private EditText et_phone;
	private Button bt_query;
	private TextView tv_query_result;
	private String mAddress;
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
		et_phone =  findViewById(R.id.et_index);
		bt_query = findViewById(R.id.bt_query);
		tv_query_result = findViewById(R.id.tv_query_result);
		tv_query_result.setMovementMethod(ScrollingMovementMethod.getInstance());

		// 点击查询功能
		bt_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = et_phone.getText().toString();
				if (!TextUtils.isEmpty(phone)) {
					// 2,查询是耗时操作,所以开启子线程
					query(phone);
				} else {
					Toast.makeText(getApplicationContext(),"您输入的为空!",Toast.LENGTH_SHORT).show();
					// 手机震动效果
					Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					// 设置震动的毫秒值
					vibrator.vibrate(500);
					// 规律震动(震动规律(不震动时间,不震动时间,...),重复次数)
					vibrator.vibrate(new long[] { 200, 200, 200, 200 }, -1);
				}
			}
		});

	}

	/**
	 * 耗时操作查询
	 */
	private void query(final String string) {
		new Thread() {
			@Override
			public void run() {
				mAddress = DictionartDao.getValue(string);
				mHhandler.sendEmptyMessage(0);
			}
		}.start();
	}


}
