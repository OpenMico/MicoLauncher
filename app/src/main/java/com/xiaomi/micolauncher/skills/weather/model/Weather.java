package com.xiaomi.micolauncher.skills.weather.model;

import android.content.Context;
import android.util.SparseIntArray;
import androidx.annotation.DrawableRes;
import com.blankj.utilcode.util.ToastUtils;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes3.dex */
public enum Weather {
    CLEAR("0", R.string.weather_clear, R.drawable.weather_clear, R.drawable.weather_clear_solid, 5),
    CLOUDY("1", R.string.weather_cloudy, R.drawable.weather_cloudy, R.drawable.weather_cloudy_solid, 4),
    OVERCAST("2", R.string.weather_overcast, R.drawable.weather_overcast, R.drawable.weather_overcast_solid, 6),
    SHOWER("3", R.string.weather_shower, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 13),
    THUNDER_SHOWER(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, R.string.weather_thunder_shower, R.drawable.weather_thunder_shower, R.drawable.weather_thunder_shower_solid, 19),
    HAIL(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, R.string.weather_hail, R.drawable.weather_hail, R.drawable.weather_hail_solid, 16),
    SLEET("6", R.string.weather_sleet, R.drawable.weather_sleet, R.drawable.weather_sleet_solid, 20),
    LIGHT_RAIN("7", R.string.weather_light_rain, R.drawable.weather_light_rain, R.drawable.weather_light_rain_solid, 8),
    MODERATE_RAIN("8", R.string.weather_moderate_rain, R.drawable.weather_moderate_rain, R.drawable.weather_moderate_rain_solid, 10),
    HEAVY_RAIN(Commands.ResolutionValues.BITSTREAM_4K, R.string.weather_heavy_rain, R.drawable.weather_heavy_rain, R.drawable.weather_heavy_rain_solid, 14),
    CLOUDBURST("10", R.string.weather_cloudburst, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 17),
    RAINSTORM("11", R.string.weather_rainstorm, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 18),
    EXTRA_RAINSTORM("12", R.string.weather_extraordinary_rainstorm, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 26),
    SNOW_SHOWER("13", R.string.weather_snow_shower, R.drawable.weather_heavy_snow, R.drawable.weather_heavy_snow_solid, 22),
    LIGHT_SNOW("14", R.string.weather_light_snow, R.drawable.weather_light_snow, R.drawable.weather_light_snow_solid, 11),
    MODERATE_SHOW("15", R.string.weather_moderate_snow, R.drawable.weather_moderate_snow, R.drawable.weather_moderate_snow_solid, 23),
    HEAVY_SNOW("16", R.string.weather_heavy_snow, R.drawable.weather_heavy_snow, R.drawable.weather_heavy_snow_solid, 24),
    SNOWSTORM("17", R.string.weather_snowstorm, R.drawable.weather_heavy_snow, R.drawable.weather_heavy_snow_solid, 27),
    FOG("18", R.string.weather_foggy, R.drawable.weather_foggy, R.drawable.weather_foggy_solid, 7),
    ICE_RAIN("19", R.string.weather_ice_rain, R.drawable.weather_ice_rain, R.drawable.weather_ice_rain_solid, 15),
    SANDSTORM("20", R.string.weather_sandstorm, R.drawable.weather_sandstorm, R.drawable.weather_sandstorm_solid, 12),
    RAIN_LIGHT_TO_MODERATE("21", R.string.weather_moderate_rain, R.drawable.weather_moderate_rain, R.drawable.weather_moderate_rain_solid, 10),
    RAIN_MODERATE_TO_HEAVY("22", R.string.weather_heavy_rain, R.drawable.weather_heavy_rain, R.drawable.weather_heavy_rain_solid, 14),
    RAIN_HEAVEY_TO_CLOUDBURST("23", R.string.weather_cloudburst, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 17),
    CLOUDBURST_TO_RAINSTORM("24", R.string.weather_rainstorm, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 18),
    RAINSTORM_TO_EXTRA("25", R.string.weather_extraordinary_rainstorm, R.drawable.weather_cloudburst, R.drawable.weather_cloudburst_solid, 26),
    SNOW_LIGHT_TO_M("26", R.string.weather_moderate_snow, R.drawable.weather_moderate_snow, R.drawable.weather_moderate_snow_solid, 23),
    SNOW_M_TO_H("27", R.string.weather_heavy_snow, R.drawable.weather_heavy_snow, R.drawable.weather_heavy_snow_solid, 24),
    SNOW_H_TO_SNOWSTORM("28", R.string.weather_snowstorm, R.drawable.weather_heavy_snow, R.drawable.weather_heavy_snow_solid, 27),
    DUST("29", R.string.weather_dust, R.drawable.weather_sandstorm, R.drawable.weather_sandstorm_solid, 1),
    BLOWING_SAND("30", R.string.weather_blowing_sand, R.drawable.weather_sandstorm, R.drawable.weather_sandstorm_solid, 2),
    HEAVY_SANDSTORM("31", R.string.weather_heavy_sandstorm, R.drawable.weather_sandstorm, R.drawable.weather_sandstorm_solid, 25),
    FOG_HEAVY("32", R.string.weather_foggy, R.drawable.weather_foggy, R.drawable.weather_foggy_solid, 7),
    SNOW("33", R.string.weather_snow, R.drawable.weather_light_snow, R.drawable.weather_light_snow_solid, 21),
    FOG_DENSE("49", R.string.weather_foggy, R.drawable.weather_foggy, R.drawable.weather_foggy_solid, 7),
    HAZE("53", R.string.weather_haze, R.drawable.weather_haze, R.drawable.weather_haze_solid, 3),
    HAZE_M("54", R.string.weather_haze, R.drawable.weather_haze, R.drawable.weather_haze_solid, 3),
    HAZE_H("55", R.string.weather_haze, R.drawable.weather_haze, R.drawable.weather_haze_solid, 3),
    HAZE_E("56", R.string.weather_haze, R.drawable.weather_haze, R.drawable.weather_haze_solid, 3),
    FOG_EXTRA("57", R.string.weather_foggy, R.drawable.weather_foggy, R.drawable.weather_foggy_solid, 7),
    FOG_STRONG("58", R.string.weather_foggy, R.drawable.weather_foggy, R.drawable.weather_foggy_solid, 7),
    UNKNOWN("99", R.string.weather_unknown, R.drawable.weather_cloudy, R.drawable.weather_cloudy_solid, 4),
    RAIN("301", R.string.weather_rain, R.drawable.weather_light_rain, R.drawable.weather_light_rain_solid, 9);
    
    public static final int DRAW_ID_PREFER_HOLLOW = 0;
    public static final int DRAW_ID_PREFER_SOLID = 1;
    public String code;
    public int drawId;
    private SparseIntArray preferColorToDrawId = new SparseIntArray(4);
    public int priority;
    public int strId;

    Weather(String str, int i, int i2, int i3, int i4) {
        this.code = str;
        this.strId = i;
        this.drawId = i2;
        this.priority = i4;
        this.preferColorToDrawId.put(0, i2);
        this.preferColorToDrawId.put(1, i3);
    }

    public int getDrawId(int i) {
        return this.preferColorToDrawId.get(i, this.drawId);
    }

    public int getDrawId() {
        return getDrawId(0);
    }

    public static Weather value(String str) {
        Weather[] values = values();
        for (Weather weather : values) {
            if (str.equals(weather.code)) {
                return weather;
            }
        }
        L.base.w("unknown weather code: %s", str);
        return UNKNOWN;
    }

    @DrawableRes
    public int getMainScreenIcon(Context context, SystemSetting.ThemeStyle themeStyle) {
        String str;
        String str2 = themeStyle == SystemSetting.ThemeStyle.Dark ? ToastUtils.MODE.DARK : "light";
        switch (this) {
            case MODERATE_RAIN:
            case RAIN_LIGHT_TO_MODERATE:
            case RAIN:
                str = MODERATE_RAIN.code;
                break;
            case SHOWER:
            case HEAVY_RAIN:
            case RAIN_MODERATE_TO_HEAVY:
            case CLOUDBURST:
            case RAIN_HEAVEY_TO_CLOUDBURST:
            case RAINSTORM:
            case CLOUDBURST_TO_RAINSTORM:
            case EXTRA_RAINSTORM:
            case RAINSTORM_TO_EXTRA:
                str = HEAVY_RAIN.code;
                break;
            case SNOW:
            case MODERATE_SHOW:
            case SNOW_LIGHT_TO_M:
                str = MODERATE_SHOW.code;
                break;
            case SNOW_SHOWER:
            case HEAVY_SNOW:
            case SNOWSTORM:
            case SNOW_M_TO_H:
            case SNOW_H_TO_SNOWSTORM:
                str = SNOW_H_TO_SNOWSTORM.code;
                break;
            case FOG:
            case FOG_HEAVY:
            case FOG_DENSE:
            case FOG_EXTRA:
            case FOG_STRONG:
                str = FOG.code;
                break;
            case SANDSTORM:
            case DUST:
            case BLOWING_SAND:
            case HEAVY_SANDSTORM:
                str = SANDSTORM.code;
                break;
            case HAZE:
            case HAZE_M:
            case HAZE_H:
            case HAZE_E:
                str = HAZE.code;
                break;
            default:
                str = this.code;
                break;
        }
        String str3 = "main_weather_%s_%s";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        int i = gregorianCalendar.get(11);
        boolean z = i < 20 && i > 6;
        if (str.equalsIgnoreCase(CLEAR.code)) {
            str3 = z ? "main_weather_%s_day_%s" : "main_weather_%s_night_%s";
        }
        int identifier = context.getResources().getIdentifier(String.format(str3, str2, str), "drawable", context.getPackageName());
        return identifier == 0 ? themeStyle == SystemSetting.ThemeStyle.Dark ? z ? R.drawable.main_weather_dark_day_0 : R.drawable.main_weather_dark_night_0 : z ? R.drawable.main_weather_dark_day_0 : R.drawable.main_weather_dark_night_0 : identifier;
    }

    @DrawableRes
    public int getWeatherBackgroundRes(Context context) {
        String str;
        String str2 = CLEAR.code;
        switch (this) {
            case MODERATE_RAIN:
            case RAIN_LIGHT_TO_MODERATE:
            case RAIN:
                str = MODERATE_RAIN.code;
                break;
            case SHOWER:
            case HEAVY_RAIN:
            case RAIN_MODERATE_TO_HEAVY:
            case CLOUDBURST:
            case RAIN_HEAVEY_TO_CLOUDBURST:
            case RAINSTORM:
            case CLOUDBURST_TO_RAINSTORM:
            case EXTRA_RAINSTORM:
            case RAINSTORM_TO_EXTRA:
                str = HEAVY_RAIN.code;
                break;
            case SNOW:
            case MODERATE_SHOW:
            case SNOW_LIGHT_TO_M:
                str = MODERATE_SHOW.code;
                break;
            case SNOW_SHOWER:
            case HEAVY_SNOW:
            case SNOWSTORM:
            case SNOW_M_TO_H:
            case SNOW_H_TO_SNOWSTORM:
                str = MODERATE_SHOW.code;
                break;
            case FOG:
            case FOG_HEAVY:
            case FOG_DENSE:
            case FOG_EXTRA:
            case FOG_STRONG:
                str = FOG.code;
                break;
            case SANDSTORM:
            case DUST:
            case BLOWING_SAND:
            case HEAVY_SANDSTORM:
                str = SANDSTORM.code;
                break;
            case HAZE:
            case HAZE_M:
            case HAZE_H:
            case HAZE_E:
                str = HAZE.code;
                break;
            case OVERCAST:
                str = OVERCAST.code;
                break;
            case CLEAR:
            case CLOUDY:
                str = CLEAR.code;
                break;
            default:
                str = this.code;
                break;
        }
        int identifier = context.getResources().getIdentifier(String.format("weather_bg_%s", str), "drawable", context.getPackageName());
        return identifier == 0 ? R.drawable.weather_bg_99 : identifier;
    }
}
