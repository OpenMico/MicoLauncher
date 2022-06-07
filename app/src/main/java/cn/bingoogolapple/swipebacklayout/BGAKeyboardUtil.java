package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* loaded from: classes.dex */
public class BGAKeyboardUtil {
    private BGAKeyboardUtil() {
    }

    public static void closeKeyboard(Activity activity) {
        View peekDecorView = activity.getWindow().peekDecorView();
        if (peekDecorView != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }

    public static void closeKeyboard(Dialog dialog) {
        View peekDecorView = dialog.getWindow().peekDecorView();
        if (peekDecorView != null) {
            ((InputMethodManager) dialog.getContext().getSystemService("input_method")).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(final Context context, final EditText editText) {
        new Handler().postDelayed(new Runnable() { // from class: cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil.1
            @Override // java.lang.Runnable
            public void run() {
                editText.requestFocus();
                EditText editText2 = editText;
                editText2.setSelection(editText2.getText().toString().length());
                ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(editText, 2);
            }
        }, 300L);
    }

    public static void clip(Context context, String str) {
        if (Build.VERSION.SDK_INT < 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setText(str);
        } else {
            ((android.content.ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("content", str));
        }
    }

    public static void toggleKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, 2);
        }
    }

    public static void handleAutoCloseKeyboard(boolean z, View view, MotionEvent motionEvent, Object obj) {
        if (z && motionEvent.getAction() == 0 && view != null && (view instanceof EditText) && obj != null) {
            int[] iArr = {0, 0};
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            int height = view.getHeight() + i2;
            int width = view.getWidth() + i;
            if (motionEvent.getX() > i && motionEvent.getX() < width && motionEvent.getY() > i2 && motionEvent.getY() < height) {
                return;
            }
            if (obj instanceof Dialog) {
                closeKeyboard((Dialog) obj);
            } else if (obj instanceof Activity) {
                closeKeyboard((Activity) obj);
            }
        }
    }
}
