package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.HandlerWrapper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SystemHandlerWrapper.java */
/* loaded from: classes2.dex */
public final class a implements HandlerWrapper {
    @GuardedBy("messagePool")
    private static final List<C0073a> a = new ArrayList(50);
    private final Handler b;

    public a(Handler handler) {
        this.b = handler;
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public Looper getLooper() {
        return this.b.getLooper();
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean hasMessages(int i) {
        return this.b.hasMessages(i);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i) {
        return a().a(this.b.obtainMessage(i), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i, @Nullable Object obj) {
        return a().a(this.b.obtainMessage(i, obj), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i, int i2, int i3) {
        return a().a(this.b.obtainMessage(i, i2, i3), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i, int i2, int i3, @Nullable Object obj) {
        return a().a(this.b.obtainMessage(i, i2, i3, obj), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendMessageAtFrontOfQueue(HandlerWrapper.Message message) {
        return ((C0073a) message).a(this.b);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessage(int i) {
        return this.b.sendEmptyMessage(i);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessageDelayed(int i, int i2) {
        return this.b.sendEmptyMessageDelayed(i, i2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessageAtTime(int i, long j) {
        return this.b.sendEmptyMessageAtTime(i, j);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public void removeMessages(int i) {
        this.b.removeMessages(i);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public void removeCallbacksAndMessages(@Nullable Object obj) {
        this.b.removeCallbacksAndMessages(obj);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean post(Runnable runnable) {
        return this.b.post(runnable);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean postDelayed(Runnable runnable, long j) {
        return this.b.postDelayed(runnable, j);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean postAtFrontOfQueue(Runnable runnable) {
        return this.b.postAtFrontOfQueue(runnable);
    }

    private static C0073a a() {
        C0073a aVar;
        synchronized (a) {
            if (a.isEmpty()) {
                aVar = new C0073a();
            } else {
                aVar = a.remove(a.size() - 1);
            }
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C0073a aVar) {
        synchronized (a) {
            if (a.size() < 50) {
                a.add(aVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SystemHandlerWrapper.java */
    /* renamed from: com.google.android.exoplayer2.util.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0073a implements HandlerWrapper.Message {
        @Nullable
        private Message a;
        @Nullable
        private a b;

        private C0073a() {
        }

        public C0073a a(Message message, a aVar) {
            this.a = message;
            this.b = aVar;
            return this;
        }

        public boolean a(Handler handler) {
            boolean sendMessageAtFrontOfQueue = handler.sendMessageAtFrontOfQueue((Message) Assertions.checkNotNull(this.a));
            a();
            return sendMessageAtFrontOfQueue;
        }

        @Override // com.google.android.exoplayer2.util.HandlerWrapper.Message
        public void sendToTarget() {
            ((Message) Assertions.checkNotNull(this.a)).sendToTarget();
            a();
        }

        @Override // com.google.android.exoplayer2.util.HandlerWrapper.Message
        public HandlerWrapper getTarget() {
            return (HandlerWrapper) Assertions.checkNotNull(this.b);
        }

        private void a() {
            this.a = null;
            this.b = null;
            a.b(this);
        }
    }
}
