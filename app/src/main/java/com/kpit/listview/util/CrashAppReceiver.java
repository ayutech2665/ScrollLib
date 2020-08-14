package com.kpit.listview.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kpit.listview.view.ViewInitializer;


public class CrashAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //throw new RuntimeException("Crash me of Media");`
        Log.d("CrashAppReceiver", " Media");

        ViewInitializer.getInstance().removeView();
    }
}
