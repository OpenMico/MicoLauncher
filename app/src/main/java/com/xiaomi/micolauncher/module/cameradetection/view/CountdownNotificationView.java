package com.xiaomi.micolauncher.module.cameradetection.view;

import android.app.PendingIntent;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class CountdownNotificationView extends FrameLayout {
    private static final long a = TimeUnit.SECONDS.toMillis(1);
    private final OnMicoNotificationViewListener b;
    private Context c;
    private PendingIntent d;
    private boolean e;
    private float f;
    private CountDownTimer g;
    private TextView h;

    /* loaded from: classes3.dex */
    public interface OnMicoNotificationViewListener {
        void onDismiss();
    }

    public CountdownNotificationView(@NotNull Builder builder) {
        super(builder.a);
        this.c = builder.a;
        this.d = builder.c;
        this.b = builder.h;
        a(builder);
    }

    private void a(@NotNull Builder builder) {
        LayoutInflater.from(getContext()).inflate(R.layout.mico_notification_countdown_view, this);
        ((TextView) findViewById(R.id.title_tv)).setText(builder.d);
        this.h = (TextView) findViewById(R.id.countdown_tv);
        if (builder.g != 0) {
            this.h.setText(builder.e);
            this.h.setVisibility(0);
            b(builder);
            builder.b = builder.g;
        } else {
            this.h.setVisibility(8);
        }
        setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.cameradetection.view.-$$Lambda$CountdownNotificationView$tUE30kLa_ZocN183zW2i4LtkByQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountdownNotificationView.this.a(view);
            }
        });
        if (builder.b > 0) {
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.cameradetection.view.-$$Lambda$w137xpeu81fsmLFlc6BrCB2fiL0
                @Override // java.lang.Runnable
                public final void run() {
                    CountdownNotificationView.this.dismiss();
                }
            }, builder.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a();
    }

    private void b(Builder builder) {
        this.g = new CountDownTimer(builder.g, a) { // from class: com.xiaomi.micolauncher.module.cameradetection.view.CountdownNotificationView.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                CountdownNotificationView.this.setTimeTv(((int) TimeUnit.MILLISECONDS.toSeconds(j)) + 1);
            }
        };
        this.g.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTimeTv(int i) {
        L.notification.i("set notification text %d", Integer.valueOf(i));
        this.h.setText(String.format("%ds", Integer.valueOf(i)));
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
                if (this.b != null) {
                    this.b.onDismiss();
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
        try {
            L.notification.i("execute pending intent");
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
                this.f = motionEvent.getY();
                break;
            case 1:
                if (this.f - motionEvent.getY() > 50.0f) {
                    dismiss();
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
        private String d;
        private String e;
        private int f;
        private long g;
        private OnMicoNotificationViewListener h;

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

        public Builder setTitle(String str) {
            this.d = str;
            return this;
        }

        public Builder setCountdownTime(long j) {
            this.g = j;
            return this;
        }

        public Builder setIntention(String str) {
            this.e = str;
            return this;
        }

        public Builder setIntentionColor(int i) {
            this.f = i;
            return this;
        }

        public Builder setOnMicoNotificationViewListener(OnMicoNotificationViewListener onMicoNotificationViewListener) {
            this.h = onMicoNotificationViewListener;
            return this;
        }

        public CountdownNotificationView build() {
            return new CountdownNotificationView(this);
        }
    }
}
