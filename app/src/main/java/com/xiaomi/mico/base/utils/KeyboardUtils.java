package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* loaded from: classes3.dex */
public class KeyboardUtils {
    public static void showKeyboard(final EditText editText) {
        editText.requestFocus();
        editText.postDelayed(new Runnable() { // from class: com.xiaomi.mico.base.utils.KeyboardUtils.1
            @Override // java.lang.Runnable
            public void run() {
                ((InputMethodManager) editText.getContext().getSystemService("input_method")).showSoftInput(editText, 2);
            }
        }, 100L);
    }

    public static void hideKeyboard(EditText editText) {
        ((InputMethodManager) editText.getContext().getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void hideKeyboard(Context context, IBinder iBinder) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(iBinder, 0);
    }
}
