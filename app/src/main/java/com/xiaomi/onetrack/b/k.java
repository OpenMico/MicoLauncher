package com.xiaomi.onetrack.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.onetrack.a.l;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.p;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class k extends Handler {
    private static final String a = "UploadTimer";
    private static final int b = 5000;
    private static final int c = 15000;
    private static final int d = 1200000;
    private final int e = 1000;
    private final int f = 10000;
    private final int g = d;
    private int h = 10000;
    private AtomicBoolean i = new AtomicBoolean(false);
    private BroadcastReceiver j = new l(this);

    public k(Looper looper) {
        super(looper);
        a(a.a());
    }

    public void a(int i, boolean z) {
        if (hasMessages(1000)) {
            p.a(a, "in retry mode, return, prio=" + i);
            return;
        }
        if (z) {
            removeMessages(i);
        }
        if (!hasMessages(i)) {
            long a2 = z ? 0L : l.a(i);
            p.a(a, "will check prio=" + i + ", delay=" + a2);
            a(i, a2);
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (!l.a() || !l.c() || l.b()) {
            p.a(a, "不用处理消息, available=" + l.a() + ", 是否有网=" + l.c() + ", 数据库是否为空=" + l.b());
        } else if (message.what == 1000) {
            b();
        } else {
            int i = message.what;
            boolean a2 = n.a().a(i);
            p.a(a, "handleCheckUpload ret=" + a2 + ", prio=" + i);
            if (!a2) {
                p.a(a, "handleCheckUpload failed, will check if need to send retry msg");
                if (!hasMessages(1000)) {
                    sendEmptyMessageDelayed(1000, this.h);
                    p.a(a, "fire retry timer after " + this.h);
                }
            }
        }
    }

    private void b() {
        if (!n.a().a(2)) {
            removeMessages(1000);
            this.h *= 2;
            if (this.h > d) {
                this.h = d;
            }
            p.a(a, "will restart retry msg after " + this.h);
            sendEmptyMessageDelayed(1000, (long) this.h);
            return;
        }
        this.h = 10000;
        p.a(a, "retry success");
    }

    private void a(Context context) {
        if (context != null) {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                context.registerReceiver(this.j, intentFilter, null, this);
            } catch (Exception e) {
                p.a(a, "registerNetReceiver: " + e);
            }
        }
    }

    private void a(int i, long j) {
        removeMessages(i);
        p.a(a, "will post msg, prio=" + i + ", delay=" + j);
        sendEmptyMessageDelayed(i, j);
    }

    public void a() {
        i.a(new m(this));
    }
}
