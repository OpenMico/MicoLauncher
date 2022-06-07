package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class DownloadManagerBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.DOWNLOAD_COMPLETE")) {
            long longExtra = intent.getLongExtra("extra_download_id", -1L);
            Logger logger = L.storage;
            logger.d("DownloadManagerBroadcastReceiver  recv ACTION_DOWNLOAD_COMPLETE Id: " + longExtra);
        }
    }
}
