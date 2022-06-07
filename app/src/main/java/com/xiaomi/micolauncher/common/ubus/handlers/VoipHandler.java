package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VoipHandler implements UBus.UbusHandler {
    public static final String CUNIQ_REMOTE_CTRL_DISABLE = "cuniq_remote_ctrl_disable";
    public static final String CUNIQ_REMOTE_CTRL_ENABLE = "cuniq_remote_ctrl_enable";
    private static final int GET_UBUS_RESPONSE_TIMEOUT_SECONDS = 5;
    private static final String METHOD = "method";
    private static final String PARAM = "param";
    private static final String PATH = "path";
    public static final String PATH_VOIP = "voip";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.equals(PATH_VOIP);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        L.voip.i("handle:path=%s,method=%s,param=%s", str, str2, str3);
        Intent intent = new Intent();
        intent.putExtra("path", str);
        intent.putExtra("method", str2);
        intent.putExtra(PARAM, str3);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MicoVoipClient.getInstance(context).setCountDownLatch(countDownLatch);
        MicoVoipClient.getInstance(context).sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_VOIP_UBUS, intent);
        try {
            countDownLatch.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            countDownLatch.countDown();
            L.voip.e("get ubus response timeout", e);
        }
        return MicoVoipClient.getInstance(context).ubusResponse;
    }
}
