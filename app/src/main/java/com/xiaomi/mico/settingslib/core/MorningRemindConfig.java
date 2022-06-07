package com.xiaomi.mico.settingslib.core;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MorningRemindConfig {
    private boolean a;
    private int b;
    private int c;

    public boolean isEnable() {
        return this.a;
    }

    public void setEnable(boolean z) {
        this.a = z;
    }

    public int getTimeStart() {
        return this.b;
    }

    public void setTimeStart(int i) {
        this.b = i;
    }

    public int getTimeEnd() {
        return this.c;
    }

    public void setTimeEnd(int i) {
        this.c = i;
    }

    public String toJsonStr() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mr", this.a);
            jSONObject.put("ts", this.b);
            jSONObject.put("te", this.c);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MorningRemindConfig parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MorningRemindConfig morningRemindConfig = new MorningRemindConfig();
            JSONObject jSONObject = new JSONObject(str);
            morningRemindConfig.b = jSONObject.optInt("ts", 0);
            morningRemindConfig.c = jSONObject.optInt("te", 0);
            morningRemindConfig.a = jSONObject.optBoolean("mr", false);
            return morningRemindConfig;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MorningRemindConfig getMorningRemind(Context context) {
        MorningRemindConfig morningRemind = MicoSettings.getMorningRemind(context);
        if (morningRemind != null) {
            return morningRemind;
        }
        MorningRemindConfig morningRemindConfig = new MorningRemindConfig();
        morningRemindConfig.setEnable(false);
        morningRemindConfig.setTimeStart(420);
        morningRemindConfig.setTimeEnd(660);
        return morningRemindConfig;
    }

    public static boolean saveMorningRemind(Context context, MorningRemindConfig morningRemindConfig) {
        return MicoSettings.setMorningRemind(context, morningRemindConfig);
    }

    public static boolean isMorningRemindEnable(Context context) {
        return getMorningRemind(context).isEnable();
    }
}
