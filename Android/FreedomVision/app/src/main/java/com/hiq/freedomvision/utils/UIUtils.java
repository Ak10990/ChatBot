package com.hiq.freedomvision.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by akanksha on 22/10/16.
 */

public class UIUtils {

    /**
     * Keyboard Utils
     */

    public static void showSoftKeyboard(final EditText editText, Context context) {
        if (editText == null || context == null) {
            return;
        }
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.postDelayed(
                new Runnable() {
                    public void run() {
                        editText.requestFocus();
                        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 100);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
