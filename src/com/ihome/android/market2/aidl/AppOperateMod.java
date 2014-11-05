package com.ihome.android.market2.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

public class AppOperateMod {
	private final static String			TAG								= AppOperateMod.class.getSimpleName();

	public static final int				FLAG_APPOPRATE_NORMAL			= 200;
	public static final int				FLAG_APPOPRATE_ERROR			= 201;

	public static final int				FLAG_APPOPRATE_NEEDUPDATE		= 202;
	public static final int				FLAG_APPOPRATE_NEEDNOTUPDATE	= 203;

	private static AppOperateAidl		appOperateService				= null;
	private static ServiceConnection	appOperateConn					= new ServiceConnection() {

																			public void onServiceConnected(ComponentName name, IBinder service) {
																				appOperateService = AppOperateAidl.Stub.asInterface(service);
																				Log.d(TAG, "AppOperateAidl --> Bind Success:" + appOperateService);
																			}

																			public void onServiceDisconnected(ComponentName name) {
																				appOperateService = null;
																			}
																		};

	public static void appCheckUpdate(final Context ctx, final Handler mHandle) {

		Log.v(TAG, "appCheckUpdate() -->");

		new Thread() {
			@Override
			public void run() {
				try {

					if (appOperateService == null) {
						Intent buyservice = new Intent(AppOperateAidl.class.getName());
						ctx.bindService(buyservice, appOperateConn, Context.BIND_AUTO_CREATE);

						sleep(1000);
					}

					if (appOperateService != null) {
						boolean isNeedUpdate = appOperateService.appUpdateCheck();
						Log.v(TAG, "appCheckUpdate() --> isNeedUpdate = " + isNeedUpdate);
						if (isNeedUpdate) {

							if (mHandle != null) {
								mHandle.sendEmptyMessage(FLAG_APPOPRATE_NEEDUPDATE);
							}
						}
						else {
							if (mHandle != null) {
								mHandle.sendEmptyMessage(FLAG_APPOPRATE_NEEDNOTUPDATE);
							}

						}
					}
					else {
						Log.e(TAG, "appOperateService is NULL");
						if (mHandle != null) {
							mHandle.sendEmptyMessage(FLAG_APPOPRATE_ERROR);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (mHandle != null) {
						mHandle.sendEmptyMessage(FLAG_APPOPRATE_ERROR);
					}
				} finally {

				}
			}
		}.start();
	}

	public static boolean appUpdate(final Context ctx, final Handler handler) {

		Log.v(TAG, "appUpdate() --> start app self upgrade");

		final AppOperateReqInfo info = new AppOperateReqInfo("custom1234", "UPDATE", "");
		new Thread() {
			@Override
			public void run() {
				try {

					if (appOperateService == null) {
						Intent oprateservice = new Intent(AppOperateAidl.class.getName());
						ctx.bindService(oprateservice, appOperateConn, Context.BIND_AUTO_CREATE);

						sleep(1000);
					}

					if (appOperateService != null) {
						String result = appOperateService.appOperate(new Gson().toJson(info));
						if (!TextUtils.isEmpty(result)) {
							Log.v(TAG, "appOperate result = " + result);

							if (handler != null) {
								handler.sendMessage(handler.obtainMessage(FLAG_APPOPRATE_NORMAL, result));
							}
						}
					}
					else {
						Log.e(TAG, "appOperateService is NULL");
						if (handler != null) {
							handler.sendEmptyMessage(FLAG_APPOPRATE_ERROR);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (handler != null) {
						handler.sendEmptyMessage(FLAG_APPOPRATE_ERROR);
					}
				} finally {

				}
			}
		}.start();

		return true;
	}

	public static void close(Context ctx) {
		if (appOperateService != null) {
			Log.v(TAG, "ctx.unbindService(getMarketInfoConn)");
			ctx.unbindService(appOperateConn);
		}
	}

}
