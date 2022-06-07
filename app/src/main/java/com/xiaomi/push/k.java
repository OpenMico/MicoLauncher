package com.xiaomi.push;

import android.content.SharedPreferences;
import com.xiaomi.push.aj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class k extends aj.b {
    final /* synthetic */ String b;
    final /* synthetic */ aj c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k(aj ajVar, aj.a aVar, String str) {
        super(aVar);
        this.c = ajVar;
        this.b = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaomi.push.aj.b
    public void a() {
        super.a();
    }

    @Override // com.xiaomi.push.aj.b
    void b() {
        SharedPreferences sharedPreferences;
        sharedPreferences = this.c.e;
        sharedPreferences.edit().putLong(this.b, System.currentTimeMillis()).commit();
    }
}
