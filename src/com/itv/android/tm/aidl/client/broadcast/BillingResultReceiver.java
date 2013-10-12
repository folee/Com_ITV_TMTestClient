package com.itv.android.tm.aidl.client.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class BillingResultReceiver extends BroadcastReceiver {
	private final String		TAG				= BillingResultReceiver.class.getSimpleName();
	public static final String	BILLINGRESULT	= "BILLINGRESULT";
	public static final String	INAPP_BILLING	= "com.itv.android.market2.billing.IN_APP_NOTIFY";

	public BillingResultReceiver() {
		super();
	}

	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(INAPP_BILLING)) {
			Log.d(TAG, "----------INAPP_BILLING Result Received----------");
			String result = intent.getStringExtra(BILLINGRESULT);
			if(TextUtils.isEmpty(result)){
				Log.e(TAG, "----------INAPP_BILLING Error Result = NULL----------");
				Toast.makeText(context, "----------INAPP_BILLING Error Result = NULL----------", Toast.LENGTH_LONG).show();
				
			}else{
				Log.d(TAG, " --> BILLINGRESULT = " + result);
				Toast.makeText(context, "----------INAPP_BILLING SUCCESS----------", Toast.LENGTH_LONG).show();
				//TODO 这边做数据处理或传递
			}
		}
	}
}