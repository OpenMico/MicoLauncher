package com.xiaomi.micolauncher.common.push;

import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class MicoNotificationView extends FrameLayout {
    private final OnMicoNotificationViewListener a;
    private OnMicoNotificationAutoDismissListener b;
    private Context c;
    private PendingIntent d;
    private boolean e;
    private boolean f;
    private boolean g;
    private float h;

    /* loaded from: classes3.dex */
    public interface OnMicoNotificationAutoDismissListener {
        void onAutoDisMiss();
    }

    /* loaded from: classes3.dex */
    public interface OnMicoNotificationViewListener {
        void onDismiss();
    }

    public MicoNotificationView(@NotNull Builder builder) {
        super(builder.a);
        this.c = builder.a;
        this.d = builder.c;
        this.a = builder.i;
        this.b = builder.j;
        this.f = builder.h;
        a(builder);
    }

    private void a(@NotNull Builder builder) {
        LayoutInflater.from(getContext()).inflate(this.f ? R.layout.mico_notification_center_view : R.layout.mico_notification_view, this);
        GlideUtils.bindImageView(this.c, builder.d, (ImageView) findViewById(R.id.icon_iv));
        ((TextView) findViewById(R.id.title_tv)).setText(builder.e);
        TextView textView = (TextView) findViewById(R.id.intention_tv);
        View findViewById = findViewById(R.id.divider_iv);
        if (!TextUtils.isEmpty(builder.f)) {
            textView.setText(builder.f);
            textView.setTextColor(this.c.getColor(builder.g));
            textView.setVisibility(0);
            findViewById.setVisibility(0);
        } else {
            textView.setVisibility(8);
            findViewById.setVisibility(8);
        }
        setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.push.-$$Lambda$MicoNotificationView$JPPhesoOcwn2Wr5oERrEWiJoxRU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MicoNotificationView.this.a(view);
            }
        });
        if (builder.b > 0) {
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.push.-$$Lambda$MicoNotificationView$lmDhOBFBihjwrdeCMKfkFTx1Yg0
                @Override // java.lang.Runnable
                public final void run() {
                    MicoNotificationView.this.b();
                }
            }, builder.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        if (!this.g) {
            dismiss();
            OnMicoNotificationAutoDismissListener onMicoNotificationAutoDismissListener = this.b;
            if (onMicoNotificationAutoDismissListener != null) {
                onMicoNotificationAutoDismissListener.onAutoDisMiss();
            }
        }
    }

    public void show() {
        L.notification.i("show notification view");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.type = 2038;
        layoutParams.flags = 40;
        layoutParams.gravity = 49;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation_Notification;
        ((WindowManager) this.c.getSystemService(WindowManager.class)).addView(this, layoutParams);
    }

    public void dismiss() {
        L.notification.i("dismiss notification view");
        try {
            if (!this.e) {
                ((WindowManager) this.c.getSystemService(WindowManager.class)).removeView(this);
                this.e = true;
                if (this.a != null) {
                    this.a.onDismiss();
                }
            }
        } catch (IllegalArgumentException e) {
            L.notification.e("dismiss notification view failed", e);
        }
    }

    public boolean isHasDismissed() {
        return this.e;
    }

    private void a() {
        L.notification.i("execute pending intent");
        this.g = true;
        try {
            try {
                if (this.d != null) {
                    this.d.send();
                }
            } catch (PendingIntent.CanceledException e) {
                L.notification.e("execute pending intent failed", e);
            }
        } finally {
            dismiss();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.h = motionEvent.getY();
                break;
            case 1:
                if (this.h - motionEvent.getY() > 50.0f) {
                    dismiss();
                    OnMicoNotificationAutoDismissListener onMicoNotificationAutoDismissListener = this.b;
                    if (onMicoNotificationAutoDismissListener == null) {
                        return true;
                    }
                    onMicoNotificationAutoDismissListener.onAutoDisMiss();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private Context a;
        private long b;
        private PendingIntent c;
        private int d;
        private String e;
        private String f;
        private int g;
        private boolean h;
        private OnMicoNotificationViewListener i;
        private OnMicoNotificationAutoDismissListener j;

        public Builder(Context context) {
            this.a = context;
        }

        public Builder setTimeoutAfter(long j) {
            this.b = j;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            this.c = pendingIntent;
            return this;
        }

        public Builder setIconRes(int i) {
            this.d = i;
            return this;
        }

        public Builder setTitle(String str) {
            this.e = str;
            return this;
        }

        public Builder setIntention(String str) {
            this.f = str;
            return this;
        }

        public Builder setIntentionColor(int i) {
            this.g = i;
            return this;
        }

        public Builder setOnMicoNotificationViewListener(OnMicoNotificationViewListener onMicoNotificationViewListener) {
            this.i = onMicoNotificationViewListener;
            return this;
        }

        public Builder setOnMicoNotificationAutoDismissListener(OnMicoNotificationAutoDismissListener onMicoNotificationAutoDismissListener) {
            this.j = onMicoNotificationAutoDismissListener;
            return this;
        }

        public Builder setIsShowCenter(boolean z) {
            this.h = z;
            return this;
        }

        public MicoNotificationView build() {
            return new MicoNotificationView(this);
        }
    }
}
