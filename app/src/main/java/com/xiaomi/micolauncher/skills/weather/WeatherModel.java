package com.xiaomi.micolauncher.skills.weather;

import com.xiaomi.micolauncher.skills.weather.model.MiWeather;

/* loaded from: classes3.dex */
public class WeatherModel {
    private static volatile WeatherModel a;
    private MiWeather b;
    private String c;

    public static WeatherModel getInstance() {
        if (a == null) {
            synchronized (WeatherModel.class) {
                if (a == null) {
                    a = new WeatherModel();
                }
            }
        }
        return a;
    }

    private WeatherModel() {
    }

    public void setMiWeather(MiWeather miWeather) {
        this.b = miWeather;
    }

    public MiWeather getMiWeather() {
        return this.b;
    }

    public String getDialogId() {
        return this.c;
    }

    public void setDialogId(String str) {
        this.c = str;
    }

    public void release(String str) {
        if (str != null && str.equals(this.c)) {
            this.b = null;
            a = null;
            this.c = null;
        }
    }
}
