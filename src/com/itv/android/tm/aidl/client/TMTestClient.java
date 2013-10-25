package com.itv.android.tm.aidl.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UpgradeSystem.UpgradeSystem;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itv.android.market2.aidl.InAppOrderAidl;
import com.itv.android.market2.aidl.InAppQueryAidl;
import com.itv.android.tm.aidl.client.broadcast.BillingResultReceiver;
import com.itv.android.tm.test.R;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class TMTestClient extends Activity implements OnClickListener {
	private static final String		TAG				= TMTestClient.class.getSimpleName();
	/** Called when the activity is first created. */
	private Button					btn				= null;
	private Button					btn2			= null;
	private Button					btn3			= null;
	private Button					btn4			= null;
	private Button					btn5			= null;
	private Button					btn6			= null;
	private Button					btn7			= null;
	private Button					btn8			= null;
	private Button					btn9			= null;
	private Button					btn10			= null;

	private InAppOrderAidl			buyInAppService	= null;
	// ����Զ�̵��ö���
	private ServiceConnection		buyInAppConn	= new ServiceConnection() {

														public void onServiceConnected(ComponentName name,
																IBinder service) {
															buyInAppService = InAppOrderAidl.Stub.asInterface(service);
															Log.d(TAG, "InAppOrderAidl --> Bind Success:"
																	+ buyInAppService);
														}

														public void onServiceDisconnected(ComponentName name) {
															buyInAppService = null;
														}
													};

	private InAppQueryAidl			queryInAppService	= null;
	// ����Զ�̵��ö���
	private ServiceConnection		queryInAppConn	= new ServiceConnection() {

														public void onServiceConnected(ComponentName name,
																IBinder service) {
															queryInAppService = InAppQueryAidl.Stub.asInterface(service);
															Log.d(TAG, "InAppQueryAidl --> Bind Success:"
																	+ queryInAppService);
														}

														public void onServiceDisconnected(ComponentName name) {
															queryInAppService = null;
														}
													};

	private BillingResultReceiver	billingReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn = (Button) findViewById(R.id.Button01);
		btn2 = (Button) findViewById(R.id.Button02);
		btn3 = (Button) findViewById(R.id.Button03);
		btn4 = (Button) findViewById(R.id.Button04);
		btn5 = (Button) findViewById(R.id.Button05);
		btn6 = (Button) findViewById(R.id.Button06);
		btn7 = (Button) findViewById(R.id.Button07);
		btn8 = (Button) findViewById(R.id.Button08);
		btn9 = (Button) findViewById(R.id.Button09);
		btn10 = (Button) findViewById(R.id.Button10);
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);

		billingReceiver = new BillingResultReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BillingResultReceiver.INAPP_BILLING);
		registerReceiver(billingReceiver, intentFilter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		paramTest();
	}

	@Override
	public void finish() {
		super.finish();
		if (billingReceiver != null) {
			unregisterReceiver(billingReceiver);
		}
	}

	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.Button01:
				Intent buyservice = new Intent(InAppOrderAidl.class.getName());
				bindService(buyservice, buyInAppConn, BIND_AUTO_CREATE);
				Intent queryservice = new Intent(InAppQueryAidl.class.getName());
				bindService(queryservice, queryInAppConn, BIND_AUTO_CREATE);
				break;

			case R.id.Button02:
				if (buyInAppService != null) {
					buyInAppService.orderRequest(getTestDataFromAssets(this, "inapp_req_param.json"));
				}
				else {
					Log.e(TAG, "buyAppService = NULL ");
				}
				break;
			case R.id.Button03:
				doInstallation();
				break;
			case R.id.Button04:
				startActivity(new Intent(this, WebViewActivity.class));
				break;
			case R.id.Button05:
				Intent intentlink = new Intent(Intent.ACTION_VIEW, Uri.parse("www.baidu.com"));
				startActivity(intentlink);
				break;

			case R.id.Button06:
				updateOs();
				break;

			case R.id.Button07:
				updateAni();
				break;
			case R.id.Button08:
				updatelogo();
				break;
			case R.id.Button09:
				
				if (queryInAppService != null) {
					String queryResult = queryInAppService.detailsQuery(getTestDataFromAssets(this, "inapp_product_query_req_param.json"));
				
					if(!TextUtils.isEmpty(queryResult))
						Log.d(TAG, "In-App Product Query Result = " + queryResult);
					else
						Log.e(TAG, "In-App Product Query Failed! ");
				}
				else {
					Log.e(TAG, "queryInAppService = NULL ");
				}
				break;
			case R.id.Button10:
				
				if (queryInAppService != null) {
					String queryResult = queryInAppService.orderRecordQuery(getTestDataFromAssets(this, "inapp_order_query_req_param.json"));
				
					if(!TextUtils.isEmpty(queryResult))
						Log.d(TAG, "In-App Product Query Result = " + queryResult);
					else
						Log.e(TAG, "In-App Product Query Failed! ");
				}
				else {
					Log.e(TAG, "queryInAppService = NULL ");
				}
				break;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v(TAG, "keyCode = " + keyCode);

		return super.onKeyDown(keyCode, event);
	}

	public static String getTestDataFromAssets(Context context, String filename) {
		String data = "";
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(filename);
			if (is != null) {
				byte[] info = readInputStream(is);
				data = new String(info);
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public void updateOs() {
		UpgradeSystem.installPackage(this, new File("/data/data/update.zip"), 0);
	}

	public void updateAni() {
		UpgradeSystem.installPackage(this, new File("/data/data/animation.zip"), 3);
	}

	public void updatelogo() {
		UpgradeSystem.installPackage(this, new File("/data/data/logo.zip"), 2);
	}

	public void doInstallation() {
		// Settings.Secure.putInt(getContentResolver(),
		// Settings.Secure.INSTALL_NON_MARKET_APPS, 1);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.android.packageinstaller",
				"com.android.packageinstaller.PackageInstallerActivity"));
		intent.setDataAndType(Uri.fromFile(new File("/data/data/LuxtoneLauncher.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	private void paramTest() {
		Log.v(TAG, "INCREMENTAL = " + Build.VERSION.INCREMENTAL);
		Log.v(TAG, "BOARD = " + Build.BOARD);
		Log.v(TAG, "BRAND = " + Build.BRAND);
		Log.v(TAG, "MANUFACTURER = " + Build.MANUFACTURER);
		Log.v(TAG, "MODEL = " + Build.MODEL);
		Log.v(TAG, "STBID = " + Build.ID);
		Log.v(TAG, "SERIAL = " + Build.SERIAL);
		Log.v(TAG, "FINGERPRINT = " + Build.FINGERPRINT);
		Log.v(TAG, "VERSION.RELEASE = " + Build.VERSION.RELEASE);
		Log.v(TAG, "VERSION.SDK = " + Build.VERSION.SDK);
		Log.v(TAG, "DEVICE = " + Build.DEVICE);
		Log.v(TAG, "APKVersion = " + getAPKVersion(this));
		Log.d(TAG, "getMac prop.AccessType = " + SystemProperties.get("prop.AccessType", "UNKNOWN"));
		Log.d(TAG, "Mac = " + getMac());
		Log.d(TAG, "prop.AccessMethod = " + SystemProperties.get("prop.AccessMethod", "UNKNOWN"));
		Log.d(TAG, "prop.DHCPAccessUserName = " + SystemProperties.get("prop.DHCPAccessUserName", "UNKNOWN"));
		Log.d(TAG, "prop.PPPOEAccessUserName = " + SystemProperties.get("prop.PPPOEAccessUserName", "UNKNOWN"));
		Log.d(TAG, "prop.AudioIOtype = " + SystemProperties.get("prop.AudioIOtype", "UNKNOWN"));
	}

	public static String getAPKVersion(Context context) {
		PackageManager localPackageManager;
		try {
			localPackageManager = context.getPackageManager();
			String str1 = context.getPackageName();
			String str2 = localPackageManager.getPackageInfo(str1, 0).versionName;
			return str2;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��ǰʹ�õ���Mac prop.AccessType : Wlan���������� Cable����������
	 * 
	 * @return
	 */
	public static String getMac() {
		String access = SystemProperties.get("prop.AccessType", "-1");
		Log.d(TAG, "getMac prop.AccessType = " + access);
		if (access.contains("Wlan")) {
			return getWlanMacAddr();
		}
		else {
			return getEthMacAddr();
		}

	}

	/**
	 * ��ȡ�������Mac
	 * 
	 * @return
	 */
	public static String getWlanMacAddr() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				if (intf.getName().indexOf("wlan") >= 0) {
					return Hex.encodeHexStr(intf.getHardwareAddress(), false);
				}
			}
		} catch (SocketException ex) {
			Log.e(TAG, "getWlanMacAddr SocketException" + ex.toString());
		}
		return null;
	}

	/**
	 * ��ȡ�������Mac
	 * 
	 * @return
	 */
	public static String getEthMacAddr() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				if (intf.getName().indexOf("eth") >= 0) {
					return Hex.encodeHexStr(intf.getHardwareAddress(), false);
				}
			}
		} catch (SocketException ex) {
			Log.e(TAG, "getEthMacAddr SocketException" + ex.toString());
		}
		return null;
	}

}