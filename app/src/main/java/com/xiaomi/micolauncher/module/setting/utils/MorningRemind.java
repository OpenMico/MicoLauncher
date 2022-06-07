package com.xiaomi.micolauncher.module.setting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.settingslib.core.MorningRemindConfig;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MorningRemind {
    private static final long a = TimeUnit.SECONDS.toMillis(30);
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MorningRemind b;
    private boolean c;
    private int d;
    private int e;
    private Handler f = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MorningRemind$HJmJAaJ79KB5zGtnFo59SC0nIeU
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = MorningRemind.this.a(message);
            return a2;
        }
    });

    private MorningRemind(Context context) {
        MorningRemindConfig morningRemind = MorningRemindConfig.getMorningRemind(context);
        this.c = morningRemind.isEnable();
        this.d = morningRemind.getTimeStart();
        this.e = morningRemind.getTimeEnd();
        if (this.c) {
            this.f.sendEmptyMessageDelayed(1, a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        this.f.removeMessages(message.what);
        L.base.d("%s handleMessage=%s", "[MorningRemind]: ", Integer.valueOf(message.what));
        if (message.what == 1) {
            int e = e();
            if (this.c && e > 0) {
                this.f.sendEmptyMessageDelayed(1, e);
            }
        }
        return false;
    }

    public static void initialize(Context context) {
        synchronized (MorningRemind.class) {
            if (b == null) {
                b = new MorningRemind(context);
            }
        }
    }

    public static MorningRemind getInstance() {
        if (b != null) {
            return b;
        }
        throw new IllegalStateException("need to call MorningRemind.instance() first!");
    }

    public void destroy() {
        Handler handler = this.f;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void a() {
        L.base.d("[MorningRemind]: startRemind");
    }

    private void b() {
        L.base.d("[MorningRemind]: stopRemind");
    }

    private boolean c() {
        int i = (Calendar.getInstance().get(11) * 60) + Calendar.getInstance().get(12);
        L.base.d("[MorningRemind]: inDuringTime.current=%d, start=%d, end=%d", Integer.valueOf(i), Integer.valueOf(this.d), Integer.valueOf(this.e));
        int i2 = this.d;
        int i3 = this.e;
        if (i2 > i3) {
            return i > i2 || i < i3;
        }
        if (i2 == i3) {
            return true;
        }
        return i > i2 && i < i3;
    }

    private boolean d() {
        Logger logger = L.base;
        logger.d("[MorningRemind]: shouldStartRemind.switcher=" + this.c);
        return this.c && c();
    }

    public void switcherOn() {
        L.base.d("[MorningRemind]: remind switch on");
        this.c = true;
        if (d()) {
            a();
        }
        this.f.removeMessages(1);
        this.f.sendEmptyMessage(1);
    }

    public void switcherOff() {
        L.base.d("[MorningRemind]: remind switch off");
        this.c = false;
        this.f.removeMessages(1);
    }

    public void setTimeStart(int i) {
        L.base.d("%s   setTimeStart   %d", "[MorningRemind]: ", Integer.valueOf(i));
        if (d()) {
            a();
        }
    }

    public void setTimeEnd(int i) {
        L.base.d("%s   setTimeEnd   %d", "[MorningRemind]: ", Integer.valueOf(i));
        if (d()) {
            a();
        }
    }

    public int getTimeStart() {
        return this.d;
    }

    public int getTimeEnd() {
        return this.e;
    }

    private int e() {
        if (this.c) {
            int i = (Calendar.getInstance().get(11) * 60) + Calendar.getInstance().get(12);
            L.base.d("[MorningRemind]: current=%d, start=%d, end=%d", Integer.valueOf(i), Integer.valueOf(this.d), Integer.valueOf(this.e));
            if (i == this.d) {
                a();
                return 60000;
            } else if (i == this.e) {
                b();
                return 60000;
            }
        }
        return 10000;
    }
}
