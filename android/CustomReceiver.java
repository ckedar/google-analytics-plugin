package com.danielcwilson.plugins.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

/*
 *  A simple Broadcast Receiver to receive an INSTALL_REFERRER
 *  intent and pass it to other receivers, including
 *  the Google Analytics receiver.
 */
public class CustomReceiver extends BroadcastReceiver {
  private static final String TAG = "CustomReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {

    final Bundle bundle = intent.getExtras();
    Log.v(TAG, "bundle.size:" + bundle.size());
    for(String key : bundle.keySet()) {
      Log.v(TAG, key + "=" + bundle.get(key));
    }
    final String referrer = bundle.getString("referrer");

    UniversalAnalyticsPlugin.referrer = referrer;

    final SharedPreferences sharedPreferences = context.getSharedPreferences("com.rapidue.uzed.referrer", Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("referrer", referrer);
    final boolean b = editor.commit();

    // Pass the intent to other receivers.

    // When you're done, pass the intent to the Google Analytics receiver.
    new CampaignTrackingReceiver().onReceive(context, intent);
  }
}