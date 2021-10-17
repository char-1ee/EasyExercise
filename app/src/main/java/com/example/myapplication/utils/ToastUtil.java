package com.example.myapplication.utils;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast utilities.
 *
 * A utility class for customizing {@link android.widget.Toast} on textSize, position, length etc.
 *
 * @author Li Xingjian
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * Show toast with customized text.
     *
     * @param context current activity context
     * @param content {@link Toast} messages
     * @param size text size of {@link Toast} messages
     */
    public static void ToastSize(Context context, String content, int size) {
        Toast mToast = makeText(context, content, Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.BOTTOM, 20, 20);  // adjust Toast positions
        LinearLayout linearLayout = (LinearLayout) mToast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(size); // set text size of Toasts
        mToast.show();
    }

    /**
     * Show long toast.
     *
     * @param context current activity context
     * @param sequence {@link Toast} messages in {@code CharSequence}
     * @param size text size of {@link Toast} messages
     */
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
