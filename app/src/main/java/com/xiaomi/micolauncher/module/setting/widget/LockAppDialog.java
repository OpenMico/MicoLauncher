package com.xiaomi.micolauncher.module.setting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.multicp.events.LockViewVisibilityEvent;
import com.xiaomi.micolauncher.module.setting.widget.InputPassView;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes3.dex */
public class LockAppDialog extends ConstraintLayout {
    private final View a;
    private InputPassView b;
    private final OnLockAppPassCorrectListener c;
    private final Context d;
    private final String e;

    /* loaded from: classes3.dex */
    public interface OnLockAppPassCorrectListener {
        void onPassCorrect();
    }

    public LockAppDialog(Context context, OnLockAppPassCorrectListener onLockAppPassCorrectListener) {
        super(context);
        this.d = context;
        this.c = onLockAppPassCorrectListener;
        this.e = LockSetting.getLockPass(context);
        this.a = LayoutInflater.from(context).inflate(R.layout.dialog_lock_app, this);
        a();
    }

    public void show() {
        L.lock.i("%s show", "LockAppDialog");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.type = 2038;
        layoutParams.flags = 67109888;
        layoutParams.gravity = 17;
        EventBusRegistry.getEventBus().post(new LockViewVisibilityEvent(true));
        ((WindowManager) this.d.getSystemService(WindowManager.class)).addView(this, layoutParams);
    }

    public void dismiss() {
        L.lock.d("%s dismiss", "LockAppDialog");
        WindowManager windowManager = (WindowManager) this.d.getSystemService(WindowManager.class);
        EventBusRegistry.getEventBus().post(new LockViewVisibilityEvent(false));
        if (!isAttachedToWindow()) {
            L.lock.w("%s#dismiss() error ,view has not been attached to window!", "LockAppDialog");
        } else {
            windowManager.removeView(this);
        }
    }

    private void a() {
        this.b = (InputPassView) this.a.findViewById(R.id.input_pass_view);
        this.b.setOnInputPassClickListener(new InputPassView.OnInputPassClickListener() { // from class: com.xiaomi.micolauncher.module.setting.widget.-$$Lambda$LockAppDialog$l_B9OjolPskJ5WRPY_tyu2ek9to
            @Override // com.xiaomi.micolauncher.module.setting.widget.InputPassView.OnInputPassClickListener
            public final void onInputDone(String str, boolean z) {
                LockAppDialog.this.a(str, z);
            }
        });
        this.a.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.widget.-$$Lambda$LockAppDialog$VROZmDgUx6kgR7z0rDGFQd_MSP4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockAppDialog.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(String str, boolean z) {
        if (this.e.equals(str)) {
            L.lock.i("input pass %s  store pass  %s", str, this.e);
            this.b.setSuccess();
            this.b.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.widget.-$$Lambda$LockAppDialog$j7sRVYVgdKYnNCqkuwrQWmFHT50
                @Override // java.lang.Runnable
                public final void run() {
                    LockAppDialog.this.b();
                }
            }, 500L);
            return;
        }
        this.b.setFailed();
    }

    public /* synthetic */ void b() {
        OnLockAppPassCorrectListener onLockAppPassCorrectListener = this.c;
        if (onLockAppPassCorrectListener != null) {
            onLockAppPassCorrectListener.onPassCorrect();
        }
        dismiss();
        this.b.reset();
    }

    public /* synthetic */ void a(View view) {
        this.b.reset();
        dismiss();
    }

    public void setTip(String str) {
        this.b.setTip(str);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        dismiss();
        return true;
    }
}
