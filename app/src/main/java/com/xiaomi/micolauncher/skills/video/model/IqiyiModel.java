package com.xiaomi.micolauncher.skills.video.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.qiyi.video.pad.service.IQYPlayerService;
import com.qiyi.video.pad.service.QYPlayerInfo;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.QiyiAppCommandProcessor;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class IqiyiModel {
    public static final String PLAY_FROM_ID = "play_from_id";
    public static final String PLAY_HISTORY_FROM_ID = "play_history_from_id";
    private static final IqiyiModel a = new IqiyiModel();
    private IQYPlayerService b;
    private ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.micolauncher.skills.video.model.IqiyiModel.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            IqiyiModel.this.b = null;
            L.base.w("[IqiyiModel]: iqiyi onServiceDisconnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IqiyiModel.this.b = IQYPlayerService.Stub.asInterface(iBinder);
            L.base.d("[IqiyiModel]: iqiyi onServiceConnected");
        }
    };

    private IqiyiModel() {
    }

    public static IqiyiModel getInstance() {
        return a;
    }

    public void init(Context context) {
        a(context);
    }

    public QYPlayerInfo getPlayerInfo() {
        IQYPlayerService iQYPlayerService = this.b;
        if (iQYPlayerService == null) {
            L.base.d("[IqiyiModel]: getPlayerInfo, mPlayerService=null");
            return null;
        }
        try {
            return iQYPlayerService.getPlayerInfo();
        } catch (Exception e) {
            Logger logger = L.base;
            logger.e("[IqiyiModel]: getPlayerInfo: exception=" + e);
            return null;
        }
    }

    private void a(Context context) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.qiyi.video.speaker", "com.qiyi.video.pad.service.QYPlayerService");
            context.bindService(intent, this.c, 1);
        } catch (Exception e) {
            e.printStackTrace();
            L.base.w(e);
        }
    }

    public static String getStartVideoIntentUri(String str, boolean z) {
        return a(str, "", false, z);
    }

    static String a(String str, String str2, boolean z, boolean z2) {
        if (z && TextUtils.isEmpty(str2)) {
            L.video.w("%s episode is empty while ordered to add episode for %s", "[IqiyiModel]: ", str);
            z = false;
        }
        if (z) {
            str = str + Constants.ACCEPT_TIME_SEPARATOR_SP + str2;
        }
        return QiyiAppCommandProcessor.URL_IQIYI_APP_PREFIX + (z2 ? "play_history_from_id" : "play_from_id") + "&param=" + str;
    }
}
