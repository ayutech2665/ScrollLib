package com.kpit.listview.view;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.kpit.listview.R;
import com.kpit.listview.util.Constant;

import static android.content.Context.WINDOW_SERVICE;

public class ViewInitializer {

    private static final String TAG = ViewInitializer.class.getSimpleName();

    private static ViewInitializer mViewInitializer;
    public Activity Context;
    private WindowManager mWindowManger;
    private View mList_View = null;
    private WindowManager.LayoutParams mParameter = null;
    private LayoutInflater mLayoutInflater = null;
    private  RecycclerListView mRecyclerListView = null;


    public static int mWindowWidth, mWindowHeight;/////////////////////////

    public ViewInitializer() {
        mRecyclerListView = new RecycclerListView();
    }

    public static ViewInitializer getInstance() {
        if (mViewInitializer == null) {
            mViewInitializer = new ViewInitializer();
        }
        return mViewInitializer;
    }

    public void createView(Activity context) {

        Context = context;
        getWindowSize();
        mWindowManger = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        mLayoutInflater = LayoutInflater.from(context.getApplicationContext());
        mList_View = mLayoutInflater.inflate(R.layout.activity_main,null);

        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            flags = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mParameter = new WindowManager.LayoutParams(Constant.NAV_WINDOW_WIDTH, Constant.NAV_WINDOW_HEIGHT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);
        mParameter.x = Constant.NAV_WINDOW_X;
        mParameter.y = 0;

        //gravity
        mParameter.gravity = Constant.VIEW_GRAVITY;

        //width by percentage
        mParameter.width = (int) (mWindowWidth * Constant.WINDOW_WIDTH_PERCENTAGE);
        mParameter.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        mWindowManger.addView(mList_View, mParameter);
        childViewIntializer();
    }

    public void childViewIntializer() {
        mRecyclerListView.widgetInitializer(mList_View,Context);
    }

    public void hide() {
        Log.d("ViewInitlaizer", "inside hide");
        Context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mParameter.x = 2000;
                mParameter.y = 0;
                mWindowManger.updateViewLayout(mList_View, mParameter);
            }
        });
    }

    public void show() {
        Log.d("ViewInitlaizer", "inside Show ");
        Context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mParameter.x = 0;
                mParameter.y = 0;
                mWindowManger.updateViewLayout(mList_View, mParameter);
            }
        });
    }

    public void windowUpdate(String msgType, int status) {
        Log.d("ViewInitlaizer", "windowUpdate");

        if (msgType.equals("priority")) {
            Log.d("ViewInitlaizer", "windowUpdate received priority from launcher : " + status);

            if (status == 1) {
                Constant.VIEW_GRAVITY = Gravity.LEFT;
                Constant.NAV_WINDOW_X = 301;
            } else {
                Constant.VIEW_GRAVITY = Gravity.END;
                Constant.NAV_WINDOW_X = 0;
            }
        }
        if (msgType.equals("hideshow")) {
            Log.d("ViewInitlaizer", "windowUpdate received hideshow from launcher : " + status);
            if (status == 0) {
                mParameter.alpha = 0.0f;
            } else {
                mParameter.alpha = 1.0f;
            }
            mWindowManger.updateViewLayout(mList_View, mParameter);
        }
    }

    public void removeView() {
        if (mWindowManger != null && mList_View != null)
            mWindowManger.removeView(mList_View);
    }

    private void getWindowSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Context.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        mWindowHeight = displayMetrics.heightPixels;
        mWindowWidth = displayMetrics.widthPixels;

        Log.d(TAG, " H : " + mWindowHeight);
        Log.d(TAG, " W : " + mWindowWidth);
    }
}
