package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.skills.mitv.MiTvManager;

/* loaded from: classes3.dex */
public class MiTvHandler implements UBus.UbusHandler {
    private static final String LOG_TAG = "[MiTvHandler]: ";
    private static final String MODEL_NAME = "upnp-disc";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.equals(MODEL_NAME);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        UBus.Response response = new UBus.Response();
        JSONObject parseObject = JSONObject.parseObject(str3);
        L.mitv.i("%s [handle method]: %s", LOG_TAG, str2);
        if (str2 != null) {
            char c = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -1298848381) {
                if (hashCode != -906021636) {
                    if (hashCode != -795228353) {
                        if (hashCode == 3322014 && str2.equals("list")) {
                            c = 3;
                        }
                    } else if (str2.equals("wakeup")) {
                        c = 2;
                    }
                } else if (str2.equals("select")) {
                    c = 1;
                }
            } else if (str2.equals("enable")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    L.mitv.i("%s enable: %s", LOG_TAG, Integer.valueOf(parseObject.getInteger(str2).intValue()));
                    break;
                case 1:
                    String string = parseObject.getString("udn");
                    L.mitv.i("%s udn: %s", LOG_TAG, string);
                    response.code = MiTvManager.getInstance().selectMiTvDevice(string);
                    break;
                case 2:
                    String string2 = parseObject.getString("udn");
                    L.mitv.i("%s udn: %s", LOG_TAG, string2);
                    MiTvManager.getInstance().wakeUpMiTv(string2);
                    break;
                case 3:
                    response.code = 0;
                    response.info = MiTvManager.getInstance().miTvGetLookupList();
                    L.mitv.i("%s response info: %s", LOG_TAG, response.info);
                    break;
            }
        }
        return response.toString();
    }
}
