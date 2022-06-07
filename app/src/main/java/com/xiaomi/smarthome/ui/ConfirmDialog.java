package com.xiaomi.smarthome.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class ConfirmDialog {
    private final Dialog a;
    private final View b;
    private final TextView c;
    private final TextView d;
    private final TextView e;
    private String f;
    private String g;
    private String h;
    private int i = -100;
    private int j = -100;
    private OnRightClickListener k;
    private OnLeftClickListener l;

    /* loaded from: classes4.dex */
    public interface OnLeftClickListener {
        void onLeftClick(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnRightClickListener {
        void onRightClick(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnTextChangeListener {
        void onTextChanged(Editable editable);
    }

    public ConfirmDialog(@NonNull Context context) {
        this.a = new Dialog(context, R.style.common_dialog_style);
        Window window = this.a.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            window.setSoftInputMode(3);
            attributes.width = context.getResources().getDimensionPixelOffset(R.dimen.confirm_dialog_width);
            attributes.height = -2;
            window.setAttributes(attributes);
            window.setGravity(17);
        }
        window.setDimAmount(0.9f);
        this.b = LayoutInflater.from(context).inflate(R.layout.dialog_confim, (ViewGroup) null, false);
        this.c = (TextView) this.b.findViewById(R.id.cancel);
        this.d = (TextView) this.b.findViewById(R.id.confirm);
        this.e = (TextView) this.b.findViewById(R.id.tvTitle);
        TextView textView = this.c;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.-$$Lambda$ConfirmDialog$gIZyCrFTJNuvnTnh5AcqFzAih24
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ConfirmDialog.this.b(view);
                }
            });
        }
        TextView textView2 = this.d;
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.-$$Lambda$ConfirmDialog$FkGe9BryY2rHKQjBM057evSHyZA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ConfirmDialog.this.a(view);
                }
            });
        }
        this.a.setContentView(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        OnLeftClickListener onLeftClickListener = this.l;
        if (onLeftClickListener != null) {
            onLeftClickListener.onLeftClick(view);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        OnRightClickListener onRightClickListener = this.k;
        if (onRightClickListener != null) {
            onRightClickListener.onRightClick(view);
        }
        dismiss();
    }

    public void dismiss() {
        this.a.dismiss();
    }

    public String getTitle() {
        return this.f;
    }

    @MainThread
    public ConfirmDialog setTitle(String str) {
        this.f = str;
        if (this.e != null) {
            if (!TextUtils.isEmpty(str)) {
                this.e.setVisibility(0);
                this.e.setText(str);
            } else {
                this.e.setVisibility(8);
            }
        }
        return this;
    }

    public String getLeftText() {
        return this.g;
    }

    @MainThread
    public ConfirmDialog setLeftText(String str) {
        this.g = str;
        TextView textView = this.c;
        if (textView != null) {
            textView.setVisibility(0);
            this.c.setText(str);
        }
        return this;
    }

    public String getRightText() {
        return this.h;
    }

    @MainThread
    public ConfirmDialog setRightText(String str) {
        this.h = str;
        TextView textView = this.d;
        if (textView != null) {
            textView.setText(str);
        }
        return this;
    }

    public int getLeftTextColor() {
        return this.i;
    }

    @MainThread
    public ConfirmDialog setLeftTextColor(@ColorInt int i) {
        this.i = i;
        TextView textView = this.c;
        if (textView != null) {
            textView.setTextColor(i);
        }
        return this;
    }

    public int getRightTextColor() {
        return this.j;
    }

    @MainThread
    public ConfirmDialog setRightTextColor(@ColorInt int i) {
        this.j = i;
        TextView textView = this.d;
        if (textView != null) {
            textView.setTextColor(i);
        }
        return this;
    }

    public ConfirmDialog setCancelable(boolean z) {
        this.a.setCancelable(z);
        this.a.setCanceledOnTouchOutside(z);
        return this;
    }

    public void show() {
        this.a.show();
    }

    public ConfirmDialog setGravity(int i) {
        if (this.a.getWindow() != null) {
            this.a.getWindow().setGravity(i);
        }
        return this;
    }

    public ConfirmDialog setWindowSize(int i, int i2) {
        Window window = this.a.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = i;
            attributes.height = i2;
            if (attributes.width == -1) {
                window.getDecorView().setPadding(0, 0, 0, 0);
            }
            window.setAttributes(attributes);
        }
        return this;
    }

    public ConfirmDialog setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.k = onRightClickListener;
        return this;
    }

    public ConfirmDialog setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.l = onLeftClickListener;
        return this;
    }

    public boolean isShowing() {
        return this.a.isShowing();
    }
}
