package com.xiaomi.push.service;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.at;
import com.xiaomi.push.cr;
import com.xiaomi.push.cu;
import com.xiaomi.push.cv;
import com.xiaomi.push.ee;
import com.xiaomi.push.ef;
import com.xiaomi.push.fc;
import com.xiaomi.push.fn;
import com.xiaomi.push.gp;
import com.xiaomi.push.gz;
import com.xiaomi.push.hb;
import com.xiaomi.push.service.ba;
import com.xiaomi.push.u;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class aq extends ba.a implements cv.a {
    private XMPushService a;
    private long b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class a implements cv.b {
        a() {
        }

        @Override // com.xiaomi.push.cv.b
        public String a(String str) {
            Uri.Builder buildUpon = Uri.parse(str).buildUpon();
            buildUpon.appendQueryParameter("sdkver", String.valueOf(38));
            buildUpon.appendQueryParameter("osver", String.valueOf(Build.VERSION.SDK_INT));
            buildUpon.appendQueryParameter(ai.x, gp.a(Build.MODEL + Constants.COLON_SEPARATOR + Build.VERSION.INCREMENTAL));
            buildUpon.appendQueryParameter("mi", String.valueOf(u.a()));
            String builder = buildUpon.toString();
            com.xiaomi.channel.commonutils.logger.b.c("fetch bucket from : " + builder);
            URL url = new URL(builder);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                String a = at.a(u.m1170a(), url);
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                hb.a(url.getHost() + Constants.COLON_SEPARATOR + port, (int) currentTimeMillis2, null);
                return a;
            } catch (IOException e) {
                hb.a(url.getHost() + Constants.COLON_SEPARATOR + port, -1, e);
                throw e;
            }
        }
    }

    /* loaded from: classes4.dex */
    static class b extends cv {
        protected b(Context context, cu cuVar, cv.b bVar, String str) {
            super(context, cuVar, bVar, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.push.cv
        public String a(ArrayList<String> arrayList, String str, String str2, boolean z) {
            try {
                if (gz.m951a().m952a()) {
                    str2 = ba.m1145a();
                }
                return super.a(arrayList, str, str2, z);
            } catch (IOException e) {
                hb.a(0, fc.GSLB_ERR.a(), 1, null, at.b(a) ? 1 : 0);
                throw e;
            }
        }
    }

    aq(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public static void a(XMPushService xMPushService) {
        aq aqVar = new aq(xMPushService);
        ba.a().a(aqVar);
        synchronized (cv.class) {
            cv.a(aqVar);
            cv.a(xMPushService, null, new a(), "0", "push", "2.2");
        }
    }

    @Override // com.xiaomi.push.cv.a
    public cv a(Context context, cu cuVar, cv.b bVar, String str) {
        return new b(context, cuVar, bVar, str);
    }

    @Override // com.xiaomi.push.service.ba.a
    public void a(ee.a aVar) {
    }

    @Override // com.xiaomi.push.service.ba.a
    public void a(ef.b bVar) {
        cr b2;
        if (bVar.mo890b() && bVar.mo888a() && System.currentTimeMillis() - this.b > 3600000) {
            com.xiaomi.channel.commonutils.logger.b.m149a("fetch bucket :" + bVar.mo888a());
            this.b = System.currentTimeMillis();
            cv a2 = cv.a();
            a2.m816a();
            a2.m819b();
            fn a3 = this.a.a();
            if (a3 != null && (b2 = a2.b(a3.m915a().c())) != null) {
                ArrayList<String> a4 = b2.m809a();
                boolean z = true;
                Iterator<String> it = a4.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().equals(a3.m916a())) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z && !a4.isEmpty()) {
                    com.xiaomi.channel.commonutils.logger.b.m149a("bucket changed, force reconnect");
                    this.a.a(0, (Exception) null);
                    this.a.a(false);
                }
            }
        }
    }
}
