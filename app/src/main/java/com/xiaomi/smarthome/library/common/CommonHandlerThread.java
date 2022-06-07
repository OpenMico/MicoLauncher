package com.xiaomi.smarthome.library.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CommonHandlerThread extends HandlerThread {
    private Handler a;
    private Handler b;
    private final List<Pair<Message, Long>> c = new ArrayList();
    private WeakReference<Handler> d;

    public CommonHandlerThread(String str) {
        super(str);
        a((Handler) null);
    }

    public CommonHandlerThread(String str, Handler handler) {
        super(str);
        a(handler);
    }

    public void setThreadHandler(Handler handler) {
        this.d = new WeakReference<>(handler);
    }

    private void a(Handler handler) {
        setThreadHandler(handler);
        this.b = new Handler(Looper.getMainLooper());
    }

    @Override // android.os.HandlerThread
    protected void onLooperPrepared() {
        this.a = new Handler(getLooper()) { // from class: com.xiaomi.smarthome.library.common.CommonHandlerThread.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Handler handler;
                if (CommonHandlerThread.this.d != null && (handler = (Handler) CommonHandlerThread.this.d.get()) != null) {
                    handler.handleMessage(message);
                }
            }
        };
        synchronized (this.c) {
            for (Pair<Message, Long> pair : this.c) {
                this.a.sendMessageDelayed((Message) pair.first, ((Long) pair.second).longValue());
            }
            this.c.clear();
        }
    }

    public void post(Runnable runnable) {
        this.b.post(runnable);
    }

    public void postDelayed(Runnable runnable, long j) {
        this.b.postDelayed(runnable, j);
    }

    public void sendMessageDelayed(Message message, long j) {
        Handler handler = this.a;
        if (handler != null) {
            handler.sendMessageDelayed(message, j);
            return;
        }
        synchronized (this.c) {
            this.c.add(new Pair<>(message, Long.valueOf(j)));
        }
    }

    public void sendMessage(Message message) {
        sendMessageDelayed(message, 0L);
    }

    public void sendMessage(int i, Object obj) {
        sendMessage(obtainMessage(i, obj));
    }

    public Message obtainMessage(int i, Object obj) {
        return Message.obtain(null, i, obj);
    }

    public void executeDelayed(Runnable runnable, long j) {
        sendMessageDelayed(Message.obtain((Handler) null, runnable), j);
    }

    public void execute(Runnable runnable) {
        executeDelayed(runnable, 0L);
    }

    public void sendEmptyMessageDelayed(int i, long j) {
        sendMessageDelayed(Message.obtain((Handler) null, i), j);
    }

    public void sendEmptyMessage(int i) {
        sendEmptyMessageDelayed(i, 0L);
    }

    @Override // java.lang.Thread
    public void destroy() {
        this.a.removeCallbacksAndMessages(null);
        super.quit();
    }

    public boolean hasMessages(int i) {
        Handler handler = this.a;
        if (handler != null) {
            return handler.hasMessages(i);
        }
        synchronized (this.c) {
            for (Pair<Message, Long> pair : this.c) {
                if (((Message) pair.first).what == i) {
                    return true;
                }
            }
            return false;
        }
    }

    public void removeMessages(int i) {
        Handler handler = this.a;
        if (handler != null) {
            handler.removeMessages(i);
            return;
        }
        synchronized (this.c) {
            ArrayList arrayList = new ArrayList();
            for (Pair<Message, Long> pair : this.c) {
                if (((Message) pair.first).what == i) {
                    arrayList.add(pair);
                }
            }
            this.c.removeAll(arrayList);
        }
    }
}
