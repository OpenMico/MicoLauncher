package com.xiaomi.micolauncher.module.intercom;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiHelper;
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes3.dex */
public class DeviceConfigIntercom {
    public static final String SP_INTERCOM_SETTINGS = "intercom_settings";
    private static volatile DeviceConfigIntercom a;
    private String b = "";

    public static DeviceConfigIntercom getInstance() {
        if (a == null) {
            synchronized (DeviceConfigIntercom.class) {
                if (a == null) {
                    a = new DeviceConfigIntercom();
                }
            }
        }
        return a;
    }

    public String getHomeId(Context context) {
        if (TextUtils.isEmpty(this.b)) {
            String settingString = PreferenceUtils.getSettingString(context, "device_home_info", "");
            if (!TextUtils.isEmpty(settingString)) {
                return ((DeviceHomeInfo) ((List) Gsons.getGson().fromJson(settingString, new TypeToken<List<DeviceHomeInfo>>() { // from class: com.xiaomi.micolauncher.module.intercom.DeviceConfigIntercom.1
                }.getType())).get(0)).homeId;
            }
        }
        return this.b;
    }

    public Observable<BaseIntercomResponse<IntercomSettings>> getIntercomSettings(Context context) {
        return ApiHelper.getIntercomSettings("DEFAULT");
    }

    public Observable<BaseIntercomResponse<IntercomSettings>> uploadIntercomSettings(String str) {
        return ApiHelper.uploadIntercomSettings("DEFAULT", str);
    }
}
