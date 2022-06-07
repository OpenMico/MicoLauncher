package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.ag;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class g implements AbstractPushManager {
    private static volatile g a;
    private Context b;
    private PushConfiguration c;
    private boolean d = false;
    private Map<f, AbstractPushManager> e = new HashMap();

    private g(Context context) {
        this.b = context.getApplicationContext();
    }

    public static g a(Context context) {
        if (a == null) {
            synchronized (g.class) {
                if (a == null) {
                    a = new g(context);
                }
            }
        }
        return a;
    }

    private void a() {
        AbstractPushManager a2;
        AbstractPushManager a3;
        AbstractPushManager a4;
        AbstractPushManager a5;
        PushConfiguration pushConfiguration = this.c;
        if (pushConfiguration != null) {
            if (pushConfiguration.getOpenHmsPush()) {
                StringBuilder sb = new StringBuilder();
                sb.append("ASSEMBLE_PUSH : ");
                sb.append(" HW user switch : " + this.c.getOpenHmsPush() + " HW online switch : " + j.m744a(this.b, f.ASSEMBLE_PUSH_HUAWEI) + " HW isSupport : " + ap.HUAWEI.equals(o.a(this.b)));
                b.m149a(sb.toString());
            }
            if (this.c.getOpenHmsPush() && j.m744a(this.b, f.ASSEMBLE_PUSH_HUAWEI) && ap.HUAWEI.equals(o.a(this.b))) {
                if (!m741a(f.ASSEMBLE_PUSH_HUAWEI)) {
                    a(f.ASSEMBLE_PUSH_HUAWEI, at.a(this.b, f.ASSEMBLE_PUSH_HUAWEI));
                }
                b.c("hw manager add to list");
            } else if (m741a(f.ASSEMBLE_PUSH_HUAWEI) && (a5 = a(f.ASSEMBLE_PUSH_HUAWEI)) != null) {
                m740a(f.ASSEMBLE_PUSH_HUAWEI);
                a5.unregister();
            }
            if (this.c.getOpenFCMPush()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("ASSEMBLE_PUSH : ");
                sb2.append(" FCM user switch : " + this.c.getOpenFCMPush() + " FCM online switch : " + j.m744a(this.b, f.ASSEMBLE_PUSH_FCM) + " FCM isSupport : " + o.m747a(this.b));
                b.m149a(sb2.toString());
            }
            if (this.c.getOpenFCMPush() && j.m744a(this.b, f.ASSEMBLE_PUSH_FCM) && o.m747a(this.b)) {
                if (!m741a(f.ASSEMBLE_PUSH_FCM)) {
                    a(f.ASSEMBLE_PUSH_FCM, at.a(this.b, f.ASSEMBLE_PUSH_FCM));
                }
                b.c("fcm manager add to list");
            } else if (m741a(f.ASSEMBLE_PUSH_FCM) && (a4 = a(f.ASSEMBLE_PUSH_FCM)) != null) {
                m740a(f.ASSEMBLE_PUSH_FCM);
                a4.unregister();
            }
            if (this.c.getOpenCOSPush()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("ASSEMBLE_PUSH : ");
                sb3.append(" COS user switch : " + this.c.getOpenCOSPush() + " COS online switch : " + j.m744a(this.b, f.ASSEMBLE_PUSH_COS) + " COS isSupport : " + o.b(this.b));
                b.m149a(sb3.toString());
            }
            if (this.c.getOpenCOSPush() && j.m744a(this.b, f.ASSEMBLE_PUSH_COS) && o.b(this.b)) {
                a(f.ASSEMBLE_PUSH_COS, at.a(this.b, f.ASSEMBLE_PUSH_COS));
            } else if (m741a(f.ASSEMBLE_PUSH_COS) && (a3 = a(f.ASSEMBLE_PUSH_COS)) != null) {
                m740a(f.ASSEMBLE_PUSH_COS);
                a3.unregister();
            }
            if (this.c.getOpenFTOSPush() && j.m744a(this.b, f.ASSEMBLE_PUSH_FTOS) && o.c(this.b)) {
                a(f.ASSEMBLE_PUSH_FTOS, at.a(this.b, f.ASSEMBLE_PUSH_FTOS));
            } else if (m741a(f.ASSEMBLE_PUSH_FTOS) && (a2 = a(f.ASSEMBLE_PUSH_FTOS)) != null) {
                m740a(f.ASSEMBLE_PUSH_FTOS);
                a2.unregister();
            }
        }
    }

    public AbstractPushManager a(f fVar) {
        return this.e.get(fVar);
    }

    public void a(PushConfiguration pushConfiguration) {
        this.c = pushConfiguration;
        this.d = ag.a(this.b).a(hm.AggregatePushSwitch.a(), true);
        if (this.c.getOpenHmsPush() || this.c.getOpenFCMPush() || this.c.getOpenCOSPush()) {
            ag.a(this.b).a(new aq(this, 101, "assemblePush"));
        }
    }

    /* renamed from: a */
    public void m740a(f fVar) {
        this.e.remove(fVar);
    }

    public void a(f fVar, AbstractPushManager abstractPushManager) {
        if (abstractPushManager != null) {
            if (this.e.containsKey(fVar)) {
                this.e.remove(fVar);
            }
            this.e.put(fVar, abstractPushManager);
        }
    }

    /* renamed from: a */
    public boolean m741a(f fVar) {
        return this.e.containsKey(fVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean b(f fVar) {
        boolean z = false;
        switch (as.a[fVar.ordinal()]) {
            case 1:
                PushConfiguration pushConfiguration = this.c;
                if (pushConfiguration != null) {
                    return pushConfiguration.getOpenHmsPush();
                }
                return false;
            case 2:
                PushConfiguration pushConfiguration2 = this.c;
                if (pushConfiguration2 != null) {
                    return pushConfiguration2.getOpenFCMPush();
                }
                return false;
            case 3:
                PushConfiguration pushConfiguration3 = this.c;
                if (pushConfiguration3 != null) {
                    z = pushConfiguration3.getOpenCOSPush();
                    break;
                }
                break;
            case 4:
                break;
            default:
                return false;
        }
        PushConfiguration pushConfiguration4 = this.c;
        return pushConfiguration4 != null ? pushConfiguration4.getOpenFTOSPush() : z;
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void register() {
        b.m149a("ASSEMBLE_PUSH : assemble push register");
        if (this.e.size() <= 0) {
            a();
        }
        if (this.e.size() > 0) {
            for (AbstractPushManager abstractPushManager : this.e.values()) {
                if (abstractPushManager != null) {
                    abstractPushManager.register();
                }
            }
            j.d(this.b);
        }
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void unregister() {
        b.m149a("ASSEMBLE_PUSH : assemble push unregister");
        for (AbstractPushManager abstractPushManager : this.e.values()) {
            if (abstractPushManager != null) {
                abstractPushManager.unregister();
            }
        }
        this.e.clear();
    }
}
