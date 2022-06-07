package com.xiaomi.micolauncher.module.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.widget.CustomDialog;

/* loaded from: classes3.dex */
public class LocationDialogHelper {
    public static CustomDialog crateLocationDialog(Context context) {
        String string = context.getString(R.string.weather_location_hint_1);
        String string2 = context.getString(R.string.weather_location_hint_2);
        String string3 = context.getString(R.string.weather_location_hint_3);
        int color = context.getColor(R.color.color_2800FF);
        int length = string.length();
        SpannableString spannableString = new SpannableString(string + string2 + "\n" + string3);
        spannableString.setSpan(new ForegroundColorSpan(color), length, string2.length() + length, 33);
        return new CustomDialog.Builder().setTitleResId(R.string.weather_location_hint).setMessage(spannableString).build();
    }

    public static void dismissDialog(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
