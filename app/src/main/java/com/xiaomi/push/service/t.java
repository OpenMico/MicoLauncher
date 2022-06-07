package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.module.homepage.adapter.AudiobookAdapterWrap;
import com.xiaomi.push.gz;
import com.xiaomi.push.service.XMPushService;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class t {
    private static int f = 300000;
    private XMPushService a;
    private int d = 0;
    private int e = 0;
    private int b = 500;
    private long c = 0;

    public t(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    private int b() {
        double d;
        if (this.d > 8) {
            return AudiobookAdapterWrap.ITEM_TYPE_CONTENT;
        }
        double random = (Math.random() * 2.0d) + 1.0d;
        int i = this.d;
        if (i > 4) {
            d = 60000.0d;
        } else if (i > 1) {
            d = 10000.0d;
        } else if (this.c == 0) {
            return 0;
        } else {
            if (System.currentTimeMillis() - this.c < 310000) {
                int i2 = this.b;
                int i3 = f;
                if (i2 >= i3) {
                    return i2;
                }
                this.e++;
                if (this.e >= 4) {
                    return i3;
                }
                this.b = (int) (i2 * 1.5d);
                return i2;
            }
            this.b = 1000;
            this.e = 0;
            return 0;
        }
        return (int) (random * d);
    }

    public void a() {
        this.c = System.currentTimeMillis();
        this.a.a(1);
        this.d = 0;
    }

    public void a(boolean z) {
        if (!this.a.m1118a()) {
            b.c("should not reconnect as no client or network.");
        } else if (z) {
            if (!this.a.m1119a(1)) {
                this.d++;
            }
            this.a.a(1);
            XMPushService xMPushService = this.a;
            xMPushService.getClass();
            xMPushService.a(new XMPushService.d());
        } else if (!this.a.m1119a(1)) {
            int b = b();
            if (!this.a.m1119a(1)) {
                this.d++;
            }
            b.m149a("schedule reconnect in " + b + "ms");
            XMPushService xMPushService2 = this.a;
            xMPushService2.getClass();
            xMPushService2.a(new XMPushService.d(), (long) b);
            if (this.d == 2 && gz.m951a().m952a()) {
                ac.b();
            }
            if (this.d == 3) {
                ac.a();
            }
        }
    }
}
