package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.ubus.UBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LockScreenMode implements UBus.UbusHandler {
    private static final String ITEM_KEY = "key";
    private static final String METHOD_GET = "get";
    private static final String METHOD_SET = "set";
    public static final String PATH = "screenlockmode";
    private static final String RES_CODE = "code";
    private static final String RES_INFO = "info";

    /* loaded from: classes3.dex */
    private static class LockScreenItem {
        String key;
        boolean selected;
        String subtitle;
        String title;
        String url;

        private LockScreenItem(String str, String str2, String str3, String str4, boolean z) {
            this.key = str;
            this.title = str2;
            this.subtitle = str3;
            this.url = str4;
            this.selected = z;
        }
    }

    /* loaded from: classes3.dex */
    private static class LockScreenItems {
        List<LockScreenItem> modes;

        public LockScreenItems(List<LockScreenItem> list) {
            this.modes = list;
        }
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return PATH.equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        if ("set".equals(str2)) {
            try {
                SystemSetting.setLockScreen(context, Integer.parseInt(JSONObject.parseObject(str3).getString(ITEM_KEY)));
            } catch (Exception unused) {
                L.base.e("LockScreenMode.handle.path=%s, method=%s, paramsStr=%s", str, str2, str3);
            }
            jSONObject.put("code", (Object) 0);
        } else if ("get".equals(str2)) {
            jSONObject.put("code", (Object) 0);
            ArrayList arrayList = new ArrayList();
            SystemSetting.LockScreen[] values = SystemSetting.LockScreen.values();
            for (SystemSetting.LockScreen lockScreen : values) {
                if (lockScreen.isUserType() && (!Hardware.isBigScreen() || lockScreen.getNameRes() != R.string.lock_screen_time_domestic)) {
                    arrayList.add(new LockScreenItem(String.valueOf(lockScreen.getId()), context.getString(lockScreen.getNameRes()), context.getString(lockScreen.getSubtitleRes()), lockScreen.getImageUrl(), SystemSetting.getLockScreen().getId() == lockScreen.getId()));
                }
            }
            jSONObject.put("info", (Object) Gsons.getGson().toJson(new LockScreenItems(arrayList)));
        }
        return jSONObject.toString();
    }
}
