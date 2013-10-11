package com.itv.android.tm.aidl.client.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BillingResultReceiver extends BroadcastReceiver {
	private final String		TAG						= BillingResultReceiver.class.getSimpleName();
	public static final String	INAPP_BILLING			= "com.itv.android.market2.billing.IN_APP_NOTIFY ";
	public static final String	INAPP_BILLING_RECEIVED	= "com.itv.android.market2.billing.IN_APP_NOTIFY_RECEIVED ";

	public BillingResultReceiver() {
		super();
	}

	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(INAPP_BILLING)) {
			Log.i(TAG, "----------INAPP_BILLING SUCCESS----------");
			Toast.makeText(context, "----------INAPP_BILLING SUCCESS----------", Toast.LENGTH_LONG).show();
			
			try {
				Thread.sleep(20*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Intent in = new Intent(INAPP_BILLING_RECEIVED);
			context.sendBroadcast(in);
		}
	}
}