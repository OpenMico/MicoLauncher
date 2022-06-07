package com.xiaomi.mipush.sdk;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.xiaomi.channel.commonutils.logger.b;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public abstract class BaseService extends Service {
    private a a;

    /* loaded from: classes4.dex */
    public static class a extends Handler {
        private WeakReference<BaseService> a;

        public a(WeakReference<BaseService> weakReference) {
            this.a = weakReference;
        }

        public void a() {
            if (hasMessages(1001)) {
                removeMessages(1001);
            }
            sendEmptyMessageDelayed(1001, 1000L);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            WeakReference<BaseService> weakReference;
            BaseService baseService;
            if (message.what == 1001 && (weakReference = this.a) != null && (baseService = weakReference.get()) != null) {
                b.c("TimeoutHandler" + baseService.toString() + "  kill self");
                if (!baseService.mo721a()) {
                    baseService.stopSelf();
                    return;
                }
                b.c("TimeoutHandler has job");
                sendEmptyMessageDelayed(1001, 1000L);
            }
        }
    }

    /* renamed from: a */
    protected abstract boolean mo721a();

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
        if (this.a == null) {
            this.a = new a(new WeakReference(this));
        }
        this.a.a();
    }
}
