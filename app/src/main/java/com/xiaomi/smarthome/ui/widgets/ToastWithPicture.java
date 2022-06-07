package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ToastWithPicture {
    public static void showToast(Context context, int i, String str) {
        Toast toast = new Toast(context);
        toast.setView(a(context, i, str));
        toast.setDuration(0);
        toast.show();
    }

    private static View a(Context context, int i, String str) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.toast_with_picture, (ViewGroup) null);
        ((ImageView) inflate.findViewById(R.id.image)).setImageResource(i);
        ((TextView) inflate.findViewById(R.id.message)).setText(str);
        return inflate;
    }

    private ToastWithPicture() {
    }
}
