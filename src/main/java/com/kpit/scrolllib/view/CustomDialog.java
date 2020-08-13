package com.kpit.scrolllib.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kpit.scrolllib.R;
import com.kpit.scrolllib.interfaces.INotificationVIew;
import com.kpit.scrolllib.interfaces.OnClickOkListener;

import static com.kpit.scrolllib.util.Constant.DIALOG_WINDOW_HEIGHT;
import static com.kpit.scrolllib.util.Constant.DIALOG_WINDOW_WIDTH;
import static com.kpit.scrolllib.util.Constant.DIALOG_WINDOW_X;
import static com.kpit.scrolllib.util.Constant.DIALOG_WINDOW_Y;

public class CustomDialog implements INotificationVIew {

    private Context mContext ;

    private static final String WARNING = "WARNING";
    private static final String POPUP = "POPUP";

    public CustomDialog(Context context){
       mContext = context;
    }

    //showDialog function used to show dialog box whether it is warning popup box or normal popup onClickOkListener is used to get a reply when OK button is pressed in popbox


    public void showDialog(final String popup_msg, String popup_type, String popup_headertxt, final OnClickOkListener onOk) {

        switch (popup_type) {

            case WARNING:

                final Dialog warning_dialog = new Dialog(mContext);
                warning_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                warning_dialog.setContentView(R.layout.warning_popup_view);
                TextView warningText = (TextView) warning_dialog.findViewById(R.id.warning_textview);
                warningText.setText(popup_msg);
                TextView warningheader = (TextView) warning_dialog.findViewById(R.id.warning_header);
                warningheader.setText(popup_headertxt);
                final Button ok_warning = (Button) warning_dialog.findViewById(R.id.warning_button);
                Window window = warning_dialog.getWindow();
                window.setAttributes(getWindowLayoutParams(warning_dialog));
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                warning_dialog.show();

                ok_warning.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN: {

                                ok_warning.setBackgroundResource(R.drawable.ok_button_highlight);

                                break;
                            }
                            case MotionEvent.ACTION_UP:

                                warning_dialog.dismiss();

                                break;
                        }
                        return false;
                    }
                });

                break;


            case POPUP:
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.popup_view);
                TextView popuptext = (TextView) dialog.findViewById(R.id.popup_textview);
                popuptext.setText(popup_msg);
                TextView popupheader = (TextView) dialog.findViewById(R.id.pop_up_header);
                popupheader.setText(popup_headertxt);
                final Button ok_popup = (Button) dialog.findViewById(R.id.popup_ok_button);
                final Button cancel_popup = (Button) dialog.findViewById(R.id.popup_cancel_button);
                Window popup_window = dialog.getWindow();
                popup_window.setAttributes(getWindowLayoutParams(dialog));
                popup_window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup_window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                dialog.show();

                ok_popup.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                ok_popup.setBackgroundResource(R.drawable.ok_button_highlight);
                                break;
                            }
                            case MotionEvent.ACTION_UP:
                                dialog.cancel();
                                onOk.okstate("Clicked:OK");
                                break;
                        }
                        return false;
                    }
                });


                cancel_popup.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                cancel_popup.setBackgroundResource(R.drawable.ok_button_highlight);
                                break;
                            }
                            case MotionEvent.ACTION_UP:
                                dialog.cancel();
                                onOk.okstate("Clicked:OK");
                                break;
                        }

                        return false;
                    }
                });


                break;

            default:


                break;


        }
    }
    private WindowManager.LayoutParams getWindowLayoutParams(Dialog dialog) {
        WindowManager.LayoutParams popupparam = new WindowManager.LayoutParams();
        popupparam.copyFrom(dialog.getWindow().getAttributes());
        popupparam.width = DIALOG_WINDOW_WIDTH;
        popupparam.height = DIALOG_WINDOW_HEIGHT;
        popupparam.x = DIALOG_WINDOW_X;
        popupparam.y = DIALOG_WINDOW_Y;
        return popupparam;

    }

}