package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.push.at;
import com.xiaomi.push.service.as;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ai extends ContentObserver {
    final /* synthetic */ ay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ai(ay ayVar, Handler handler) {
        super(handler);
        this.a = ayVar;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        Context context;
        Integer num;
        Context context2;
        Context context3;
        ay ayVar = this.a;
        context = ayVar.c;
        ayVar.l = Integer.valueOf(as.a(context).a());
        num = this.a.l;
        if (num.intValue() != 0) {
            context2 = this.a.c;
            context2.getContentResolver().unregisterContentObserver(this);
            context3 = this.a.c;
            if (at.b(context3)) {
                this.a.c();
            }
        }
    }
}
