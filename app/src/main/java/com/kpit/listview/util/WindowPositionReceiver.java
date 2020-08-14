package com.kpit.listview.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kpit.listview.view.ViewInitializer;


public class WindowPositionReceiver extends BroadcastReceiver {
    private static final String TAG = WindowPositionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Bundle bundle = intent.getBundleExtra("data");
        String msgType = bundle.getString("msgType");

        if (msgType.equals("hideshow")) {
            int status = (bundle.getBoolean("status", false)) ? 1 : 0;
            Log.d(TAG, "Media : received hideshow from launcher : " + status);

            ViewInitializer.getInstance().windowUpdate("hideshow", status);

        } else if (msgType.equals("priority")) {
            int priority = bundle.getInt("priority", 0);
            Log.d(TAG, "Media : received priority from launcher : " + priority);

            Toast.makeText(context, "Received Priority " + priority + " From launcher in Media", Toast.LENGTH_SHORT).show();

            ViewInitializer.getInstance().windowUpdate("priority", priority);
        }
    }
}
