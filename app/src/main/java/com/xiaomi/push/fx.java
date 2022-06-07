package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.am;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class fx extends am.b {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fx(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.push.am.b
    public void b() {
        Object obj;
        ArrayList arrayList;
        List list;
        List list2;
        obj = gs.d;
        synchronized (obj) {
            list = gs.e;
            arrayList = new ArrayList(list);
            list2 = gs.e;
            list2.clear();
        }
        gs.b(this.a, arrayList);
    }
}
