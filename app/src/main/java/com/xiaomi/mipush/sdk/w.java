package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ev;
import com.xiaomi.push.hl;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class w implements ev.a {
    @Override // com.xiaomi.push.ev.a
    public void a(Context context, hl hlVar) {
        MiTinyDataClient.upload(context, hlVar);
    }
}
