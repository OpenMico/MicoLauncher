package com.milink.runtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.milink.runtime.lock.LockContentProvider;
import java.util.Objects;

/* loaded from: classes2.dex */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            new Thread(new Runnable() { // from class: com.milink.runtime.-$$Lambda$BootCompleteReceiver$OVBAKhcL4IdAYwPJXQFbTDbIpkc
                @Override // java.lang.Runnable
                public final void run() {
                    LockContentProvider.prepare(context);
                }
            }).start();
        }
    }
}
