package com.example.convergecodelab.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastMessage {
    private Context context;
    private String message;
    private Toast toast;
    private LinearLayout toastLayout;
    private TextView toastView;

    public ToastMessage(Context context, String msg) {
        this.context = context;
        this.message = msg;
    }

    public Toast getToast() {
        toast = android.widget.Toast.makeText(context, message, Toast.LENGTH_LONG);
        toastLayout = (LinearLayout) toast.getView();
        toastView = (TextView) toastLayout.getChildAt(0);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#45818E"));
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toastView.setTextSize(12);
        toastView.setTextColor(Color.parseColor("#FFFFFF"));
        return toast;
    }
}
