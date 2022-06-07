package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.hg;
import com.xiaomi.push.hl;
import java.util.List;

/* loaded from: classes4.dex */
public class i implements hg {
    private final XMPushService a;

    public i(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        return "com.xiaomi.xmsf".equals(str) ? "1000271" : this.a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    @Override // com.xiaomi.push.hg
    public void a(List<hl> list, String str, String str2) {
        b.m149a("TinyData LongConnUploader.upload items size:" + list.size() + "  ts:" + System.currentTimeMillis());
        this.a.a(new bj(this, 4, str, list, str2));
    }
}
