package com.example.myapplication.utils;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A utility class for customizing {@link android.widget.Toast} on textSize, position, length etc.
 */
public class ToastUtil {

    private static Toast mToast;

    public static void toastSize(Context context, String content, int size) {
        Toast mToast = makeText(context, content, Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.BOTTOM, 20, 20);  // adjust Toast positions
        LinearLayout linearLayout = (LinearLayout) mToast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(size); // set text size of Toasts
        mToast.show();
    }

    public static void showLong(Context context, CharSequence sequence, int size) {
        if (mToast == null) {
            mToast = makeText(context, sequence, Toast.LENGTH_LONG);
        } else {
            mToast.cancel();
            mToast = makeText(context, sequence, Toast.LENGTH_LONG);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.setGravity(Gravity.BOTTOM, 20, 20); /// adjust Toast positions
        LinearLayout linearLayout = (LinearLayout) mToast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(size); // set text size of Toasts
        mToast.show();
    }
}
