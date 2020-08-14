package com.kpit.scrolllib.interfaces;

import android.app.Activity;
import android.content.Context;

public interface INotificationVIew {
    void showDialog(final String msg, String type, String header, final OnClickOkListener onOk);
}
