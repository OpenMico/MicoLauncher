package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ha implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ q d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ha(q qVar, String str, String str2, String str3) {
        this.d = qVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        context = this.d.b;
        SharedPreferences.Editor edit = context.getSharedPreferences(this.a, 4).edit();
        edit.putString(this.b, this.c);
        edit.commit();
    }
}
