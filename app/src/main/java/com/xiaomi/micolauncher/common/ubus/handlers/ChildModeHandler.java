package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.WakeupPlayer;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes3.dex */
public class ChildModeHandler implements UBus.UbusHandler {
    private static final int ERROR_SET = 10900;
    private static final int ERROR_STATUS = 10600;
    private static final String METHOD_RESET_PWD = "reset_pwd";
    private static final String METHOD_SET = "set";
    private static final String METHOD_STATUS = "status";
    public static final String PATH = "path_child_mode";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return PATH.equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        UBus.Response response = new UBus.Response();
        if ("status".equals(str2)) {
            ChildModeConfig loadStatus = loadStatus(context);
            L.base.d("ChildModeConfig %s", loadStatus);
            if (loadStatus != null) {
                response.info = Gsons.getGson().toJson(loadStatus);
            } else {
                response.code = ERROR_STATUS;
            }
        } else if ("set".equals(str2)) {
            if (ContainerUtil.isEmpty(str3)) {
                L.childmode.e("input params is empty");
                response.code = ERROR_SET;
                return response.toString();
            }
            ChildModeConfig childMode = setChildMode(context, JSONObject.parseObject(str3).getString("message"));
            if (childMode == null) {
                response.code = ERROR_SET;
            } else if (childMode.getAntiAddiction().watchTimeRestricted) {
                ChildModeManager.getManager().startAllTiming();
                VideoDataManager.getManager().refreshChildModeCache();
                response.info = Gsons.getGson().toJson(childMode);
            } else {
                ChildModeManager.getManager().setLock(false);
            }
        } else if (METHOD_RESET_PWD.equals(str2)) {
            LockSetting.clearPass(context);
        }
        return response.toString();
    }

    @Nullable
    private ChildModeConfig setChildMode(Context context, String str) {
        ChildModeConfig fromStr = ChildModeStorage.JsonConverter.fromStr(str);
        if (fromStr == null || !ChildModeStorage.getInstance().onConfigChange(context, fromStr, true)) {
            return null;
        }
        ChildModeConfig.saveChildModeConfig(context, fromStr);
        WakeupPlayer.onToneVendorChanged(context);
        StatPoints.recordPoint(StatPoints.Event.micolog_feature, StatPoints.FeatureKey.kid_mode_action, fromStr.isChildMode ? "on" : "off");
        return fromStr;
    }

    @Nullable
    private ChildModeConfig loadStatus(Context context) {
        return ChildModeStorage.getInstance().load(context);
    }
}
