package com.xiaomi.onetrack.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class w extends BroadcastReceiver {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(f fVar) {
        this.a = fVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
                    this.a.i();
                }
            }
        } catch (Exception unused) {
        }
    }
}
