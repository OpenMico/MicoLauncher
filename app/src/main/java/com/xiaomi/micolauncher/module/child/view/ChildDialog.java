package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class ChildDialog extends FrameLayout implements View.OnClickListener {
    private Context a;
    private boolean b;
    private OnDialogClickListener c;
    private Builder d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private ImageView i;

    /* loaded from: classes3.dex */
    public interface OnDialogClickListener {
        void onCancelClickListener();

        void onConfirmClickListener();
    }

    public ChildDialog(@NonNull Context context, Builder builder) {
        super(context);
        this.a = context;
        this.d = builder;
        View inflate = LayoutInflater.from(context).inflate((builder == null || builder.c <= 0) ? R.layout.dialog_mitv_vip : builder.c, this);
        this.e = (TextView) inflate.findViewById(R.id.title);
        this.f = (TextView) inflate.findViewById(R.id.subtitle);
        this.f = (TextView) inflate.findViewById(R.id.subtitle);
        this.g = (TextView) inflate.findViewById(R.id.btn_sure);
        this.h = (TextView) inflate.findViewById(R.id.btn_cancel);
        this.i = (ImageView) inflate.findViewById(R.id.popup_image);
        if (builder != null) {
            if (builder.a != null) {
                this.e.setText(builder.a);
            }
            if (builder.b != null) {
                this.f.setText(builder.b);
            }
            if (builder.e != null) {
                this.g.setText(builder.e);
            }
            if (builder.f != null) {
                this.h.setText(builder.f);
            }
            if (builder.d > 0) {
                this.i.setBackgroundResource(builder.d);
            }
        }
        this.h.setOnClickListener(this);
        this.g.setOnClickListener(this);
    }

    public boolean isShowing() {
        return this.b;
    }

    public void show() {
        if (!this.b) {
            L.childContent.d("ChildDialog show");
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.type = 2038;
            layoutParams.flags = 67109888;
            layoutParams.gravity = 17;
            ((WindowManager) this.a.getSystemService(WindowManager.class)).addView(this, layoutParams);
            this.b = true;
        }
    }

    public void dismiss() {
        L.camera2.d("ChildDialog dismiss");
        ((WindowManager) this.a.getSystemService(WindowManager.class)).removeView(this);
        this.b = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        a();
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            a();
        } else if (id == R.id.btn_sure) {
            OnDialogClickListener onDialogClickListener = this.c;
            if (onDialogClickListener != null) {
                onDialogClickListener.onConfirmClickListener();
            }
            dismiss();
        }
    }

    private void a() {
        OnDialogClickListener onDialogClickListener = this.c;
        if (onDialogClickListener != null) {
            onDialogClickListener.onCancelClickListener();
        }
        dismiss();
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.c = onDialogClickListener;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private String a;
        private String b;
        private int c;
        private int d;
        private String e;
        private String f;

        public Builder setTitle(String str) {
            this.a = str;
            return this;
        }

        public Builder setSubTitle(String str) {
            this.b = str;
            return this;
        }

        public Builder setLayoutResId(int i) {
            this.c = i;
            return this;
        }

        public Builder setImageResId(int i) {
            this.d = i;
            return this;
        }

        public Builder setPositive(String str) {
            this.e = str;
            return this;
        }

        public Builder setNegative(String str) {
            this.f = str;
            return this;
        }

        public ChildDialog build(Context context) {
            return new ChildDialog(context, this);
        }
    }
}
