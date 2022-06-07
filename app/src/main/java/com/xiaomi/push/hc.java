package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ag;
import java.io.File;

/* loaded from: classes4.dex */
public class hc implements XMPushService.l {
    private static boolean a = false;
    private Context b;
    private boolean c;
    private int d;

    public hc(Context context) {
        this.b = context;
    }

    private String a(String str) {
        return "com.xiaomi.xmsf".equals(str) ? "1000271" : this.b.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    private void a(Context context) {
        this.c = ag.a(context).a(hm.TinyDataUploadSwitch.a(), true);
        this.d = ag.a(context).a(hm.TinyDataUploadFrequency.a(), 7200);
        this.d = Math.max(60, this.d);
    }

    public static void a(boolean z) {
        a = z;
    }

    private boolean a(hg hgVar) {
        return at.b(this.b) && hgVar != null && !TextUtils.isEmpty(a(this.b.getPackageName())) && new File(this.b.getFilesDir(), "tiny_data.data").exists() && !a;
    }

    private boolean b() {
        return Math.abs((System.currentTimeMillis() / 1000) - this.b.getSharedPreferences("mipush_extra", 4).getLong("last_tiny_data_upload_timestamp", -1L)) > ((long) this.d);
    }

    @Override // com.xiaomi.push.service.XMPushService.l
    public void a() {
        a(this.b);
        if (this.c && b()) {
            b.m149a("TinyData TinyDataCacheProcessor.pingFollowUpAction ts:" + System.currentTimeMillis());
            hg a2 = hf.a(this.b).a();
            if (!a(a2)) {
                b.m149a("TinyData TinyDataCacheProcessor.pingFollowUpAction !canUpload(uploader) ts:" + System.currentTimeMillis());
                return;
            }
            a = true;
            hd.a(this.b, a2);
        }
    }
}
