package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaomi.mico.settingslib.core.NightModeConfig;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes3.dex */
public class NightMode implements UBus.UbusHandler {
    private static final String CODE = "code";
    private static final String INFO = "info";
    private static final String METHOD_GET = "status";
    private static final String METHOD_SET = "set";
    private static final String MODE = "total";
    private static final String NIGHT = "night";
    public static final String NIGHT_MODE = "nightmode";
    private static final String NORMAL = "normal";
    private static final String TIME_END = "stop";
    private static final String TIME_START = "start";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return NIGHT_MODE.equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        int i;
        ParseException e;
        NightModeConfig sleepModeConfig = SleepMode.getInstance().getSleepModeConfig(context);
        JsonObject jsonObject = new JsonObject();
        if ("set".equals(str2)) {
            JsonObject jsonObject2 = (JsonObject) JsonParser.parseString(str3);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            int i2 = -1;
            try {
                Date parse = simpleDateFormat.parse(jsonObject2.get("start").getAsString());
                int hours = parse.getHours();
                int minutes = parse.getMinutes();
                i = (hours < 0 || hours >= 24 || minutes < 0 || minutes >= 60) ? -1 : minutes + (hours * 60);
                try {
                    Date parse2 = simpleDateFormat.parse(jsonObject2.get("stop").getAsString());
                    int hours2 = parse2.getHours();
                    int minutes2 = parse2.getMinutes();
                    if (hours2 >= 0 && hours2 < 24 && minutes2 >= 0 && minutes2 < 60) {
                        i2 = (hours2 * 60) + minutes2;
                    }
                } catch (ParseException e2) {
                    e = e2;
                    L.sleep_mode.e("NightMode exception:", e);
                    if (i >= 0) {
                        sleepModeConfig.setTimeSleep(true);
                        sleepModeConfig.setTimeStart(i);
                        sleepModeConfig.setTimeEnd(i2);
                    }
                    SleepMode.getInstance().onConfigChange(sleepModeConfig);
                    NightModeConfig.saveSleepMode(context, sleepModeConfig);
                    jsonObject.addProperty("code", (Number) 0);
                    return jsonObject.toString();
                }
            } catch (ParseException e3) {
                e = e3;
                i = -1;
            }
            if (i >= 0 && i2 >= 0) {
                sleepModeConfig.setTimeSleep(true);
                sleepModeConfig.setTimeStart(i);
                sleepModeConfig.setTimeEnd(i2);
            }
            SleepMode.getInstance().onConfigChange(sleepModeConfig);
            NightModeConfig.saveSleepMode(context, sleepModeConfig);
            jsonObject.addProperty("code", (Number) 0);
        } else if ("status".equals(str2)) {
            jsonObject.addProperty("code", (Number) 0);
            JsonObject jsonObject3 = new JsonObject();
            if (sleepModeConfig.isTimeSleep()) {
                jsonObject3.addProperty(MODE, NIGHT);
            } else {
                jsonObject3.addProperty(MODE, "normal");
            }
            DecimalFormat decimalFormat = new DecimalFormat("00");
            jsonObject3.addProperty("start", String.format("%s:%s", decimalFormat.format(SleepMode.getInstance().getTimeStart() / 60), decimalFormat.format(SleepMode.getInstance().getTimeStart() % 60)));
            jsonObject3.addProperty("stop", String.format("%s:%s", decimalFormat.format(SleepMode.getInstance().getTimeEnd() / 60), decimalFormat.format(SleepMode.getInstance().getTimeEnd() % 60)));
            jsonObject.addProperty("info", jsonObject3.toString());
        }
        return jsonObject.toString();
    }
}
