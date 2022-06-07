package com.xiaomi.mico.settingslib.core;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.common.Hardware;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class NightModeConfig {
    private boolean a;
    private boolean b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private boolean g;
    private boolean h;

    public String toJsonStr() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sm", this.a);
            jSONObject.put("au", this.b);
            jSONObject.put("ts", this.c);
            jSONObject.put("te", this.d);
            jSONObject.put(ai.aC, this.e);
            jSONObject.put("tsm", this.f);
            jSONObject.put("cs", this.g);
            jSONObject.put("wk", this.h);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NightModeConfig parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            NightModeConfig nightModeConfig = new NightModeConfig();
            JSONObject jSONObject = new JSONObject(str);
            nightModeConfig.c = jSONObject.optInt("ts", 0);
            nightModeConfig.d = jSONObject.optInt("te", 0);
            nightModeConfig.e = jSONObject.optInt(ai.aC, 0);
            nightModeConfig.a = jSONObject.optBoolean("sm", false);
            nightModeConfig.b = jSONObject.optBoolean("au", false);
            nightModeConfig.f = jSONObject.optBoolean("tsm", false);
            nightModeConfig.g = jSONObject.optBoolean("cs", false);
            nightModeConfig.h = jSONObject.optBoolean("wk", false);
            return nightModeConfig;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEnable() {
        return this.a;
    }

    public void setEnable(boolean z) {
        this.a = z;
    }

    public int getTimeStart() {
        return this.c;
    }

    public void setTimeStart(int i) {
        this.c = i;
    }

    public int getTimeEnd() {
        return this.d;
    }

    public void setTimeEnd(int i) {
        this.d = i;
    }

    public int getVolume() {
        return this.e;
    }

    public void setVolume(int i) {
        this.e = i;
    }

    public boolean isTimeSleep() {
        return this.f;
    }

    public void setTimeSleep(boolean z) {
        this.f = z;
    }

    public boolean isCloseScreen() {
        return this.g;
    }

    public void setCloseScreen(boolean z) {
        this.g = z;
    }

    public boolean isAuto() {
        return this.b;
    }

    public void setAuto(boolean z) {
        this.b = z;
    }

    public boolean isWorking() {
        return this.h;
    }

    public void setWorking(boolean z) {
        this.h = z;
    }

    public static NightModeConfig getSleepMode(Context context) {
        NightModeConfig sleepMode = MicoSettings.getSleepMode(context);
        if (sleepMode != null) {
            return sleepMode;
        }
        NightModeConfig nightModeConfig = new NightModeConfig();
        nightModeConfig.setEnable(false);
        nightModeConfig.setTimeStart(1320);
        nightModeConfig.setTimeEnd(420);
        nightModeConfig.setVolume(getSleepModeDefaultVolume());
        return nightModeConfig;
    }

    public static boolean saveSleepMode(Context context, NightModeConfig nightModeConfig) {
        return MicoSettings.setSleepMode(context, nightModeConfig);
    }

    public static boolean isWorking(Context context) {
        NightModeConfig sleepMode = MicoSettings.getSleepMode(context);
        if (sleepMode != null) {
            return sleepMode.isWorking();
        }
        return false;
    }

    public static boolean isEnable(Context context) {
        NightModeConfig sleepMode = MicoSettings.getSleepMode(context);
        if (sleepMode != null) {
            return sleepMode.isEnable();
        }
        return false;
    }

    public static int getSleepModeDefaultVolume() {
        return Hardware.isBigScreen() ? 20 : 25;
    }
}
