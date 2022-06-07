package com.xiaomi.micolauncher.common.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class MangGuoTipDialog extends Dialog {
    View view;

    public MangGuoTipDialog(@NonNull Context context) {
        this(context, R.style.DialogFullScreen);
    }

    @SuppressLint({"InflateParams"})
    public MangGuoTipDialog(@NonNull Context context, int i) {
        super(context, i);
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_mangguo, (ViewGroup) null);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(this.view);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 17;
            attributes.width = -1;
            attributes.height = -1;
            window.setAttributes(attributes);
        }
    }
}
