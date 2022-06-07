package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ResourceRecycler.java */
/* loaded from: classes.dex */
public class q {
    private boolean a;
    private final Handler b = new Handler(Looper.getMainLooper(), new a());

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(Resource<?> resource, boolean z) {
        if (!this.a && !z) {
            this.a = true;
            resource.recycle();
            this.a = false;
        }
        this.b.obtainMessage(1, resource).sendToTarget();
    }

    /* compiled from: ResourceRecycler.java */
    /* loaded from: classes.dex */
    private static final class a implements Handler.Callback {
        a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            ((Resource) message.obj).recycle();
            return true;
        }
    }
}
