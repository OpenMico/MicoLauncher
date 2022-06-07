package com.xiaomi.smarthome.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.micolauncher.common.L;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

@SuppressLint({"Registered"})
/* loaded from: classes4.dex */
public abstract class BaseActivity extends FragmentActivity {
    public static final long DEFAULT_CLOSE_DURATION = TimeUnit.MINUTES.toMillis(1);
    protected static final int MSG_CLOSE = 1;
    private long a;
    private String b;
    private int c;
    private long d = -1;
    protected Handler handler;

    /* JADX INFO: Access modifiers changed from: protected */
    public void initViews() {
    }

    protected void loadData() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observeData() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Configs.Instance.setupRxJavaErrorHandler();
        this.c = Process.myTid();
        this.handler = new Handler(Looper.getMainLooper(), new a());
        setScheduleDuration(DEFAULT_CLOSE_DURATION);
        L.base.d("%s onCreate ", this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        removeCloseScheduler();
        L.base.d("%s onDestroy ", this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        L.base.d("%s onPause ", this);
        removeCloseScheduler();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        L.base.d("%s onRestart ", this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        L.base.d("%s onStart ", this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        L.base.d("%s onStop ", this);
    }

    protected void showToast(String str) {
        showToast(str, false);
    }

    protected void showToast(final String str, final boolean z) {
        if (str != null) {
            if (System.currentTimeMillis() - this.a >= SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS || !str.equals(this.b)) {
                if (this.c != Process.myTid()) {
                    post(new Runnable() { // from class: com.xiaomi.smarthome.base.-$$Lambda$BaseActivity$ULya28x25XN0PLImhAZ8q19jNBo
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseActivity.this.a(z, str);
                        }
                    });
                } else if (z) {
                    Toast.makeText(this, str, 1).show();
                } else {
                    Toast.makeText(this, str, 0).show();
                }
                this.a = System.currentTimeMillis();
                this.b = str;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, String str) {
        if (z) {
            Toast.makeText(this, str, 1).show();
        } else {
            Toast.makeText(this, str, 0).show();
        }
    }

    protected void post(Runnable runnable) {
        this.handler.post(runnable);
    }

    protected boolean isValidContext(Activity activity) {
        return !activity.isDestroyed() && !activity.isFinishing();
    }

    protected AutoDisposeConverter<?> autoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        L.base.d("%s onResume ", this);
        scheduleToClose(this.d);
    }

    public void scheduleToClose(long j) {
        Handler handler;
        this.d = j;
        removeCloseScheduler();
        if (j > 0 && (handler = this.handler) != null) {
            handler.sendEmptyMessageDelayed(1, j);
        }
    }

    public void removeCloseScheduler() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    public void setScheduleDuration(long j) {
        this.d = j;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                removeCloseScheduler();
                break;
            case 1:
                long j = this.d;
                if (j > 0) {
                    scheduleToClose(j);
                    break;
                }
                break;
        }
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    /* loaded from: classes4.dex */
    private static class a implements Handler.Callback {
        private final WeakReference<BaseActivity> a;

        private a(BaseActivity baseActivity) {
            this.a = new WeakReference<>(baseActivity);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            BaseActivity baseActivity;
            WeakReference<BaseActivity> weakReference = this.a;
            if (weakReference == null || (baseActivity = weakReference.get()) == null || message.what != 1) {
                return false;
            }
            baseActivity.removeCloseScheduler();
            baseActivity.finish();
            L.activity.i("finish activity %s", baseActivity);
            return true;
        }
    }
}
