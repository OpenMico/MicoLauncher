package org.hapjs.features.channel.transparentactivity;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class TransparentActivityManager {
    private boolean a;
    private WeakReference<TransparentActivity> b;
    private Handler c = new Handler() { // from class: org.hapjs.features.channel.transparentactivity.TransparentActivityManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                TransparentActivityManager.this.a();
            }
        }
    };

    /* loaded from: classes5.dex */
    public static class a {
        static final TransparentActivityManager a = new TransparentActivityManager();
    }

    public static TransparentActivityManager getInstance() {
        return a.a;
    }

    public void onActivityLaunch(TransparentActivity transparentActivity) {
        TransparentActivity transparentActivity2;
        if (this.a) {
            transparentActivity.finish();
            return;
        }
        WeakReference<TransparentActivity> weakReference = this.b;
        if (!(weakReference == null || (transparentActivity2 = weakReference.get()) == null)) {
            if (transparentActivity2 == transparentActivity) {
                this.c.removeMessages(0);
            } else {
                a();
            }
        }
        this.b = new WeakReference<>(transparentActivity);
        this.c.sendEmptyMessageDelayed(0, 10000L);
    }

    public void onActivityDestroy() {
        a();
    }

    public void onServiceCreated() {
        this.a = true;
        a();
    }

    public void a() {
        WeakReference<TransparentActivity> weakReference = this.b;
        if (weakReference != null) {
            TransparentActivity transparentActivity = weakReference.get();
            if (transparentActivity != null) {
                transparentActivity.finish();
            }
            this.b = null;
        }
        this.c.removeMessages(0);
    }

    public void onServiceDestroy() {
        this.a = false;
    }
}
