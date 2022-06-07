package com.xiaomi.mipush.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.exoplayer2.PlaybackException;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.MessageHandleService;
import com.xiaomi.push.ew;

/* loaded from: classes4.dex */
public abstract class PushMessageReceiver extends BroadcastReceiver {
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
    }

    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
    }

    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        ew a;
        String packageName;
        int i;
        String str;
        MessageHandleService.addJob(context.getApplicationContext(), new MessageHandleService.a(intent, this));
        try {
            int intExtra = intent.getIntExtra("eventMessageType", -1);
            if (intExtra == 2000) {
                a = ew.a(context.getApplicationContext());
                packageName = context.getPackageName();
                i = 2003;
                str = "receive passThough message broadcast";
            } else if (intExtra == 6000) {
                a = ew.a(context.getApplicationContext());
                packageName = context.getPackageName();
                i = PlaybackException.ERROR_CODE_DRM_DISALLOWED_OPERATION;
                str = "receive register push broadcast";
            } else {
                return;
            }
            a.a(packageName, intent, i, str);
        } catch (Exception e) {
            b.a(e);
        }
    }

    @Deprecated
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
    }

    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
    }

    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
    }

    public void onRequirePermissions(Context context, String[] strArr) {
    }
}
