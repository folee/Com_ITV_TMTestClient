package com.itv.android.tm.test;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ihome.android.market2.aidl.AppOperateMod;
import com.itv.android.tm.test.R;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class TMTestClient extends Activity implements OnClickListener {
	private static final String	TAG		= TMTestClient.class.getSimpleName();
	/** Called when the activity is first created. */
	private Button				btn		= null;

	private Handler				handler	= new Handler() {
											@Override
											public void dispatchMessage(Message msg) {
												super.dispatchMessage(msg);
												switch (msg.what) {

												//有更新
												case AppOperateMod.FLAG_APPOPRATE_NEEDUPDATE:
													Log.v(TAG, "AppOperateMod.FLAG_APPOPRATE_NEEDUPDATE ");
													AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
													builder.setTitle(getString(R.string.app_name));
													builder.setMessage(getString(R.string.upgrade_tip));
													builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															AppOperateMod.appUpdate(getApplicationContext(), handler);
														}
													});
													builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {}
													});
													AlertDialog ad = builder.create();
													ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
													ad.setCanceledOnTouchOutside(true);
													ad.show();
													break;
												//无更新
												case AppOperateMod.FLAG_APPOPRATE_NEEDNOTUPDATE:
													Log.v(TAG, "AppOperateMod.FLAG_APPOPRATE_NEEDNOTUPDATE ");
													break;

												default:
													break;
												}
											}
										};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn = (Button) findViewById(R.id.Button01);
		btn.setOnClickListener(this);

	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Button01:
			AppOperateMod.appCheckUpdate(getApplicationContext(), handler);
			break;
		}
	}

}