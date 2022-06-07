package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ErrorCode;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.common.ubus.UBusError;
import com.xiaomi.micolauncher.common.utils.BatteryUtils;
import com.xiaomi.micolauncher.common.utils.ResettingUtil;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.net.URLDecoder;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class InternalHandler implements UBus.UbusHandler {
    public static final boolean ENABLE_FORCE_UPDATE = false;
    private static final boolean FORBID_RESET = false;
    private static final String GET = "GET";
    private static final String KEY_LEVEL = "level";
    private static final String MIBRAIN_LEVEL = "/internal/mibrain_level";
    public static final String PATH_OTA = "/internal/ota";
    public static final String POST = "POST";
    private static final String REBOOT_REASON_SET_MI_BRAIN_LEVEL = "change mico environment";
    private static final String REPAIR = "/internal/repair";
    public static final String SYNC_UNBIND_DEVICE = "/internal/sync_unbind_device";
    private static final String UPLOAD_LOG = "/internal/upload_log";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.startsWith("/internal");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
        if (r8.equals(com.xiaomi.micolauncher.common.ubus.handlers.InternalHandler.MIBRAIN_LEVEL) != false) goto L_0x0080;
     */
    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String handle(final android.content.Context r7, java.lang.String r8, java.lang.String r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.ubus.handlers.InternalHandler.handle(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private String upgrade(Context context, String str, HashMap<String, String> hashMap) {
        UBus.OtaInfo otaInfo;
        UBus.Response response = new UBus.Response();
        response.code = UBusError.REMOTE_UBUS_ERROR.getCode();
        if (!"POST".equals(str)) {
            return null;
        }
        String str2 = hashMap.get(SchemaActivity.KEY_PAYLOAD);
        L.update.i("payload %s", str2);
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            otaInfo = (UBus.OtaInfo) Gsons.getGson().fromJson(URLDecoder.decode(str2, "UTF-8"), (Class<Object>) UBus.OtaInfo.class);
        } catch (Exception e) {
            L.update.e("failed to update", e);
        }
        if (otaInfo == null) {
            L.update.e("ubus upgrade protocol : failed to decode payload %s", str2);
            return null;
        }
        L.update.i("enable force update %s, force update ? %s", false, false);
        if (VoipModel.getInstance().isVoipActive()) {
            return null;
        }
        if (!otaInfo.isValid()) {
            L.base.i("payload parameter is illegal");
        } else if (RomUpdateAdapter.getInstance().isUpdateOngoing()) {
            L.base.i("upgrade Updating");
            response.code = 0;
            return response.toString();
        } else if (!SystemSetting.isIntroMoviePlayed(context)) {
            L.base.i("Introduction movie is not finished playing. Device does not finish initialization.");
            response.code = ErrorCode.UNKNOWN_ERROR.getCode();
            response.info = "device has not initialized";
            L.base.i("Return code 1 and \"device has not initialized\" to server. Server will retry pushing OTA.");
            return response.toString();
        } else if (!BatteryUtils.isBatteryPresent(context) || !BatteryUtils.isLowBatterLevel(context)) {
            int forceUpdate = RomUpdateAdapter.getInstance().forceUpdate(str2);
            if (forceUpdate == 0) {
                L.update.i("Force update is started.");
                SpeechManager.getInstance().ttsRequest(context.getString(R.string.local_tts_upgrade_now));
                response.code = 0;
            } else {
                L.update.i("Force update is failed with error %d.", Integer.valueOf(forceUpdate));
                return null;
            }
        } else {
            L.update.w("Battery level is low. Don't update.");
            return null;
        }
        return response.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doMasterClear(Context context) {
        ResettingUtil.doMasterClear(context, true);
    }
}
