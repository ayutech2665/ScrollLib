package com.kpit.scrolllib.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kpit.scrolllib.R;

public class CustomToast {
        private Context mContext;
        private WindowManager wm;
        private int mDuration;
        private View mNextView;
        public static final int LENGTH_SHORT = 1500;
        public static final int LENGTH_LONG = 3000;

        public CustomToast(Context context) {
            mContext = context.getApplicationContext();
            wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        public CustomToast makeText(CharSequence text,
                                    int duration) {
            CustomToast result = new CustomToast(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View view = inflater.inflate(R.layout.toast,null);
            if (view != null){
                TextView tv = (TextView) view.findViewById(R.id.tostmessage);
                tv.setText(text);
            }
            mNextView = view;
            mDuration = duration;
            return result;
        }


        // show() function is used to show the toast message
        public void show() {
            if (mNextView != null) {
                mNextView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.gravity = Gravity.CENTER | Gravity.BOTTOM;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                params.format = PixelFormat.TRANSLUCENT;
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                params.windowAnimations = android.R.style.Animation_Toast;
                params.x = 120;
                params.y = 30;

                if (wm == null){

                    wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

                }

                wm.addView(mNextView, params);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (mNextView != null) {
                            wm.removeView(mNextView);
                            mNextView = null;
                            wm = null;
                        }
                    }
                }, mDuration);
            }
        }

    }


