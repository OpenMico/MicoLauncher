package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.umeng.analytics.pro.c;
import kotlin.Metadata;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"sendSnsBroadcast", "", c.R, "Landroid/content/Context;", "result", "", "passportui_release"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AuthSnsProviderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSnsBroadcast(Context context, String str) {
        Intent intent = new Intent(SNSAuthProvider.ACTION_PASSPORT_SNS_EVENTS);
        intent.putExtra(SNSAuthProvider.EXTRA_KEY_SNS_RESULT, str);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
