package com.kpit.listview.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.kpit.listview.view.ViewInitializer;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.bluetooth_view);

        requestForPermission(REQUEST_CODE);
        if (savedInstanceState == null) {
            Log.d(TAG, "savedInstanceState null");
            //requestPermissions(getPermissionList(), 101);
        } else {
            Log.d(TAG, "savedInstanceState not null");
        }

    }

    public void viewCreation() {
        Log.d(TAG, "onCreate of MainActivity");
        ViewInitializer.getInstance().createView(this);
        finish();
    }

    public String[] getPermissionList() {
        String newArr[] = new String[50];
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);

            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    Log.d(TAG, "Permissions: " + p);
                }

                return info.requestedPermissions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    private void requestForPermission(final int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*check if we already have permission to draw over other apps*/
            if (!Settings.canDrawOverlays(this)) {
                /*if not,construct intent to request permission*/
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, requestCode);
            } else {
                Log.d(TAG, "Overlay Permission already given");
                viewCreation();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "On Activity result");
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    viewCreation();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "permissions request code = " + requestCode);
        viewCreation();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
