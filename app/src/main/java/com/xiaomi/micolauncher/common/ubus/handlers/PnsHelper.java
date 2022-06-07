package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.MultiDialogSwitcherEvent;
import com.xiaomi.micolauncher.common.event.NearByWakeupEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.utils.NearByWakeupHelper;
import com.xiaomi.micolauncher.common.speech.utils.PrivacyHelper;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PnsHelper implements UBus.UbusHandler {
    private static final String CODE = "code";
    public static final String INFO = "info";
    private static final int MULTI_DIALOG_GET_STATUS = 6;
    private static final int MULTI_DIALOG_SET_OFF = 4;
    private static final int MULTI_DIALOG_SET_ON = 5;
    private static final int NEARBY_WAKEUP_GET_STATUS = 2;
    private static final int NEARBY_WAKEUP_SET_OFF = 0;
    private static final int NEARBY_WAKEUP_SET_ON = 1;
    private static final int PNS_TOAST_TIP_DURATION = (int) TimeUnit.SECONDS.toMillis(2);
    private static final int PRIVACY_STATUS_CHANGE = 3;
    private static final String STAT = "status";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return "pnshelper".equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        if ("event_notify".equals(str2)) {
            JSONObject parseObject = JSONObject.parseObject(str3);
            int intValue = parseObject.getInteger("src").intValue();
            int intValue2 = parseObject.getInteger("event").intValue();
            if (intValue == 2) {
                eventHandle(intValue2, jSONObject);
            } else {
                jSONObject.put("code", (Object) 100);
            }
        }
        return jSONObject.toString();
    }

    private void eventHandle(int i, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        switch (i) {
            case 0:
                if (SpeechConfig.supportNearByWakeup()) {
                    SystemSetting.setNearbyWakeup(false);
                    EventBusRegistry.getEventBus().post(new NearByWakeupEvent());
                    NearByWakeupHelper.setNearbyStatus(false);
                }
                jSONObject.put("code", (Object) 0);
                break;
            case 1:
                if (SpeechConfig.supportNearByWakeup()) {
                    SystemSetting.setNearbyWakeup(true);
                    EventBusRegistry.getEventBus().post(new NearByWakeupEvent());
                    NearByWakeupHelper.setNearbyStatus(true);
                }
                jSONObject.put("code", (Object) 0);
                break;
            case 2:
                jSONObject.put("code", (Object) 0);
                if (SystemSetting.getNearbyWakeup() && SpeechConfig.supportNearByWakeup()) {
                    jSONObject2.put("status", (Object) "on");
                    break;
                } else {
                    jSONObject2.put("status", (Object) "off");
                    break;
                }
                break;
            case 3:
                PrivacyHelper.privacyInit();
                jSONObject.put("code", (Object) 0);
                break;
            case 4:
                FullDuplexSpeechHelper.disable(2);
                jSONObject.put("code", (Object) 0);
                EventBusRegistry.getEventBus().post(new MultiDialogSwitcherEvent());
                ToastUtil.showCustomToast(R.string.setting_multi_dialog_closed, PNS_TOAST_TIP_DURATION);
                break;
            case 5:
                FullDuplexSpeechHelper.enable(2);
                jSONObject.put("code", (Object) 0);
                EventBusRegistry.getEventBus().post(new MultiDialogSwitcherEvent());
                ToastUtil.showCustomToast(R.string.setting_multi_dialog_opened, PNS_TOAST_TIP_DURATION);
                break;
            case 6:
                jSONObject.put("code", (Object) 0);
                if (!FullDuplexSpeechHelper.isEnabled()) {
                    jSONObject2.put("status", (Object) "off");
                    break;
                } else {
                    jSONObject2.put("status", (Object) "on");
                    break;
                }
        }
        jSONObject.put("info", (Object) jSONObject2.toString());
    }
}
