package com.xiaomi.push;

import android.util.Log;
import com.xiaomi.push.am;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class dc extends am.b {
    final /* synthetic */ di a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public dc(di diVar) {
        this.a = diVar;
    }

    @Override // com.xiaomi.push.am.b
    public void b() {
        List list;
        String str;
        String str2;
        list = di.g;
        if (!list.isEmpty()) {
            try {
                if (!ab.d()) {
                    str2 = this.a.d;
                    Log.w(str2, "SDCard is unavailable.");
                    return;
                }
                this.a.b();
            } catch (Exception e) {
                str = this.a.d;
                Log.e(str, "", e);
            }
        }
    }
}
