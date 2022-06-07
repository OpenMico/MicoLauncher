package com.xiaomi.micolauncher.common.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.elvishew.xlog.Logger;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class LogUploader {
    private static final String BUG_REPORT_LOG_ID = "logID";
    private static final String BUG_REPORT_TIME_STAMP = "timeStamp";
    private final long timeStamp = System.currentTimeMillis();
    private final String logId = String.format("common-%s-%s-%d", Hardware.getBuildModel(), Constants.getSn(), Long.valueOf(this.timeStamp));

    /* loaded from: classes3.dex */
    public interface LogUploadListener {
        void onFailed(String str);

        void onSuccess();
    }

    public void upload(Context context) {
        upload(context, null);
    }

    public void upload(Context context, final LogUploadListener logUploadListener) {
        if (context == null) {
            L.base.i("upload log failed, context is null");
            return;
        }
        Intent intent = new Intent("com.xiaomi.mico.feedback.action.DUMP_LOG");
        intent.setPackage("com.xiaomi.mico.feedback");
        intent.putExtra(BUG_REPORT_LOG_ID, this.logId);
        intent.putExtra(BUG_REPORT_TIME_STAMP, this.timeStamp);
        context.startService(intent);
        if (logUploadListener != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.xiaomi.mico.feedback.UPLOAD_LOG_RESULT");
            context.registerReceiver(new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.common.crash.LogUploader.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent2) {
                    boolean booleanExtra = intent2.getBooleanExtra("isSuccess", false);
                    Logger logger = L.base;
                    StringBuilder sb = new StringBuilder();
                    sb.append("upload log result is ");
                    sb.append(booleanExtra ? "succeed" : MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                    logger.i(sb.toString());
                    if (booleanExtra) {
                        logUploadListener.onSuccess();
                    } else {
                        logUploadListener.onFailed(MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                    }
                    context2.unregisterReceiver(this);
                }
            }, intentFilter);
        }
    }

    public String getLogId() {
        return this.logId;
    }
}
