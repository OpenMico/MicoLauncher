package com.xiaomi.micolauncher.skills.weather.model;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.skills.update.VersionUtil;
import com.xiaomi.onetrack.api.b;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MiWeather {
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final String TIME_PATTERN = "yyyyMMdd HH:mm";
    public String currentAqi;
    public WeacheDate date;
    public WeacheDuration duration;
    public ForecastDaily forecastDaily;
    public List<WeatherItem> forecastDailyItems;
    public boolean hasMentionedLocation;
    public Location location;
    private boolean mDataValid;
    public WeatherItem singleDayWeather;
    public WeatherNow weatherNow;

    /* loaded from: classes3.dex */
    public enum WeatherType {
        TODAY,
        ONE_DAY,
        DURATION
    }

    public static int caculateWindOrientation(double d) {
        if ((d > 337.5d && d <= 360.0d) || (d >= 0.0d && d < 22.5d)) {
            return 0;
        }
        if (d >= 22.5d && d <= 67.5d) {
            return 1;
        }
        if (d > 67.5d && d < 112.5d) {
            return 2;
        }
        if (d >= 112.5d && d <= 157.5d) {
            return 3;
        }
        if (d > 157.5d && d < 202.5d) {
            return 4;
        }
        if (d >= 202.5d && d <= 247.5d) {
            return 5;
        }
        if (d > 247.5d && d < 292.5d) {
            return 6;
        }
        if (d < 292.5d || d > 337.5d) {
            return (d != 361.0d && d == 360.0d) ? 0 : 0;
        }
        return 7;
    }

    public static int calculateWindLevel(double d) {
        if (d < 1.0d) {
            return 0;
        }
        if (d < 6.0d) {
            return 1;
        }
        if (d < 12.0d) {
            return 2;
        }
        if (d < 20.0d) {
            return 3;
        }
        if (d < 29.0d) {
            return 4;
        }
        if (d < 39.0d) {
            return 5;
        }
        if (d < 50.0d) {
            return 6;
        }
        if (d < 62.0d) {
            return 7;
        }
        if (d < 75.0d) {
            return 8;
        }
        if (d < 89.0d) {
            return 9;
        }
        if (d < 103.0d) {
            return 10;
        }
        if (d < 117.0d) {
            return 11;
        }
        return d >= 117.0d ? 12 : 0;
    }

    /* loaded from: classes3.dex */
    public enum AQI {
        A(R.string.weather_aqi_A, R.drawable.weather_aqi_a),
        B(R.string.weather_aqi_B, R.drawable.weather_aqi_b),
        C(R.string.weather_aqi_C, R.drawable.weather_aqi_c),
        D(R.string.weather_aqi_D, R.drawable.weather_aqi_d),
        E(R.string.weather_aqi_E, R.drawable.weather_aqi_e),
        F(R.string.weather_aqi_F, R.drawable.weather_aqi_f);
        
        public int drawId;
        public int strId;

        AQI(int i, int i2) {
            this.strId = i;
            this.drawId = i2;
        }
    }

    /* loaded from: classes3.dex */
    public static class WUnit {
        public String unit;
        public String value;

        public WUnit parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.unit = jSONObject.optString("unit", "");
                this.value = jSONObject.optString(b.p, "");
            }
            return this;
        }
    }

    public List<WeacherDay> getMultiDaysWeatherV2(int i) {
        ArrayList arrayList = new ArrayList(i);
        int min = Math.min(i, this.forecastDailyItems.size());
        for (int i2 = 0; i2 < min; i2++) {
            WeatherItem weatherItem = this.forecastDailyItems.get(i2);
            WeacherDay weacherDay = new WeacherDay();
            weacherDay.date = weatherItem.date;
            if (weatherItem.weatherCode != null) {
                weacherDay.weaterFrom = Weather.value(weatherItem.weatherCode.from);
                weacherDay.weaterTo = Weather.value(weatherItem.weatherCode.to);
            }
            if (!(weatherItem.wind == null || weatherItem.wind.speed == null)) {
                weacherDay.windSpeed = weatherItem.wind.speed.getBigValue();
                weacherDay.windDirection = weatherItem.wind.direction.getBigValue();
            }
            if (weatherItem.aqi != null) {
                weacherDay.aqi = calculateAqi(weatherItem.aqi);
            }
            weacherDay.dayTemp = new WTrans(weatherItem.lowTemperature, weatherItem.highTemperature);
            weacherDay.care = isCareDate(i2);
            arrayList.add(weacherDay);
        }
        return arrayList;
    }

    /* loaded from: classes3.dex */
    public static class WValues<T> {
        public String pubTime;
        public int status;
        public String unit;
        public List<T> value;

        public WValues parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.pubTime = jSONObject.optString("pubTime", "");
                this.unit = jSONObject.optString("unit", "");
                this.status = jSONObject.optInt("status", 0);
                JSONArray optJSONArray = jSONObject.optJSONArray(b.p);
                if (optJSONArray != null) {
                    this.value = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            this.value.add(new WTrans().parser(optJSONObject));
                        } else {
                            Integer valueOf = Integer.valueOf(optJSONArray.optInt(i, -1));
                            if (valueOf.intValue() != -1) {
                                this.value.add(valueOf);
                            }
                        }
                    }
                }
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public static class Wind {
        public WUnit direction;
        public WUnit speed;

        Wind parser(JSONObject jSONObject) {
            JSONObject optJSONObject = jSONObject.optJSONObject("direction");
            if (optJSONObject != null) {
                this.direction = new WUnit().parser(optJSONObject);
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject("speed");
            if (optJSONObject2 != null) {
                this.speed = new WUnit().parser(optJSONObject2);
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public static class Winds {
        public WValues<WTrans> direction;
        public WValues<WTrans> speed;

        Winds parser(JSONObject jSONObject) {
            JSONObject optJSONObject = jSONObject.optJSONObject("direction");
            if (optJSONObject != null) {
                this.direction = new WValues().parser(optJSONObject);
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject("speed");
            if (optJSONObject2 != null) {
                this.speed = new WValues().parser(optJSONObject2);
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public class Location {
        public String city;

        public Location() {
            MiWeather.this = r1;
        }

        Location parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.city = jSONObject.optString("city", "");
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherNow {
        public WUnit temperature;
        public String weather;
        public Wind wind;

        WeatherNow parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                JSONObject optJSONObject = jSONObject.optJSONObject("temperature");
                if (optJSONObject != null) {
                    this.temperature = new WUnit().parser(optJSONObject);
                }
                JSONObject optJSONObject2 = jSONObject.optJSONObject("wind");
                if (optJSONObject2 != null) {
                    this.wind = new Wind().parser(optJSONObject2);
                }
                this.weather = jSONObject.optString(DomainConfig.Weather.DOMAIN_NAME, "");
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public static class ForecastDaily {
        public WValues<Integer> aqi;
        public String pubTime;
        public int status;
        public WValues<WTrans> sunRiseSet;
        public WValues<WTrans> temperature;
        public WValues<WTrans> weather;
        public Winds wind;

        public int getDateIndex(Date date) {
            int size = this.sunRiseSet.value.size();
            for (int i = 0; i < size; i++) {
                Date date2 = TimeCalculator.date(XMPassport.SIMPLE_DATE_FORMAT, this.sunRiseSet.value.get(i).from);
                if (date2 != null && TimeCalculator.isSameDate(date2, date)) {
                    return i;
                }
            }
            return 0;
        }

        public AQI getAqiWithIndex(int i) {
            if (this.aqi.value == null || this.aqi.value.size() <= i) {
                return null;
            }
            return MiWeather.calculateAqi(this.aqi.value.get(i).intValue());
        }

        public ForecastDaily parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.pubTime = jSONObject.optString("pubTime", "");
                this.status = jSONObject.optInt("status", 0);
                JSONObject optJSONObject = jSONObject.optJSONObject("aqi");
                if (optJSONObject != null) {
                    this.aqi = new WValues().parser(optJSONObject);
                }
                JSONObject optJSONObject2 = jSONObject.optJSONObject("sunRiseSet");
                if (optJSONObject2 != null) {
                    this.sunRiseSet = new WValues().parser(optJSONObject2);
                }
                JSONObject optJSONObject3 = jSONObject.optJSONObject("temperature");
                if (optJSONObject3 != null) {
                    this.temperature = new WValues().parser(optJSONObject3);
                }
                JSONObject optJSONObject4 = jSONObject.optJSONObject(DomainConfig.Weather.DOMAIN_NAME);
                if (optJSONObject4 != null) {
                    this.weather = new WValues().parser(optJSONObject4);
                }
                JSONObject optJSONObject5 = jSONObject.optJSONObject("wind");
                if (optJSONObject5 != null) {
                    this.wind = new Winds().parser(optJSONObject5);
                }
            }
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public class ForecastHourly {
        public WValues<Integer> aqi;
        public String desc;
        public String pubTime;
        public int status;
        public WValues<Integer> temperature;
        public WValues<Integer> weather;
        public WValues<HWind> wind;

        public ForecastHourly() {
            MiWeather.this = r1;
        }

        /* loaded from: classes3.dex */
        class HWind {
            public String datetime;
            public String direction;
            public String speed;

            HWind() {
                ForecastHourly.this = r1;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class WeacheDuration {
        public String duration;
        public Date durationEnd;
        public String durationEndStr;
        public long durationLength;
        public Date durationStar;
        public String durationStartStr;
        public int endIndexDaily;
        public int startIndexDaily;

        WeacheDuration(String str) {
            this.duration = str;
            try {
                String[] split = str.split("/");
                if (split.length == 2) {
                    this.durationStartStr = split[0];
                    this.durationEndStr = split[1];
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MiWeather.DATE_PATTERN);
                    this.durationStar = simpleDateFormat.parse(this.durationStartStr);
                    this.durationEnd = simpleDateFormat.parse(this.durationEndStr);
                    this.durationLength = (this.durationEnd.getTime() - this.durationStar.getTime()) / 86400000;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "WeacheDuration{duration='" + this.duration + "', durationLength=" + this.durationLength + ", durationStar=" + this.durationStar + ", durationEnd=" + this.durationEnd + ", startIndexDaily=" + this.startIndexDaily + ", endIndexDaily=" + this.endIndexDaily + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class WeacheDate {
        public Date date;
        public String dateStr;
        public int indexDaily;

        public WeacheDate() {
        }

        WeacheDate(String str) {
            this.dateStr = str;
            try {
                this.date = new SimpleDateFormat(MiWeather.DATE_PATTERN).parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "WeacheDate{dateStr='" + this.dateStr + "', date=" + this.date + ", indexDaily=" + this.indexDaily + '}';
        }
    }

    public static AQI calculateAqi(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return calculateAqi(Integer.parseInt(str));
            }
        } catch (NumberFormatException e) {
            L.weather.e("calculateAqi", e);
        }
        return AQI.B;
    }

    public static AQI calculateAqi(int i) {
        if (i <= 50) {
            return AQI.A;
        }
        if (i <= 100) {
            return AQI.B;
        }
        if (i <= 150) {
            return AQI.C;
        }
        if (i <= 200) {
            return AQI.D;
        }
        if (i <= 300) {
            return AQI.E;
        }
        return AQI.F;
    }

    public static String getWindStr(@NonNull Context context, @Nullable WeatherToday weatherToday) {
        return (weatherToday == null || weatherToday.wind == null || weatherToday.wind.speed == null) ? "" : getWindStr(context, weatherToday.wind.speed.value);
    }

    public static String getWindStr(@NonNull Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int calculateWindLevel = calculateWindLevel(str);
        return calculateWindLevel == 0 ? context.getResources().getString(R.string.weather_wind_breeze) : String.format(context.getResources().getString(R.string.weather_wind_rank), Integer.valueOf(calculateWindLevel));
    }

    private static int calculateWindLevel(String str) {
        double parseDouble;
        if (!TextUtils.isEmpty(str)) {
            try {
                parseDouble = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                Logger logger = L.weather;
                logger.e("failed to parse wind speed " + str, e);
            }
            return calculateWindLevel(parseDouble);
        }
        parseDouble = 0.0d;
        return calculateWindLevel(parseDouble);
    }

    public boolean isDataValid() {
        return this.mDataValid;
    }

    private boolean isCareDate(int i) {
        if (isOneDayWeather()) {
            return this.date.indexDaily == i;
        }
        if (isDurationWeather()) {
            return i >= this.duration.startIndexDaily && i <= this.duration.endIndexDaily;
        }
        return false;
    }

    public List<Integer> getMuitlDaysUpTemp(int i) {
        if (this.forecastDailyItems != null) {
            return getMuitlDaysUpTempV2(i);
        }
        ArrayList arrayList = new ArrayList();
        int min = Math.min(i, this.forecastDaily.temperature.value.size());
        for (int i2 = 0; i2 < min; i2++) {
            arrayList.add(Integer.valueOf(Integer.parseInt(this.forecastDaily.temperature.value.get(i2).from)));
        }
        return arrayList;
    }

    public List<Integer> getMuitlDaysDownTemp(int i) {
        if (this.forecastDailyItems != null) {
            return getMuitlDaysDownTempV2(i);
        }
        ArrayList arrayList = new ArrayList();
        int min = Math.min(i, this.forecastDaily.temperature.value.size());
        for (int i2 = 0; i2 < min; i2++) {
            arrayList.add(Integer.valueOf(Integer.parseInt(this.forecastDaily.temperature.value.get(i2).to)));
        }
        return arrayList;
    }

    public boolean isTodayWeather() {
        WeacheDate weacheDate = this.date;
        return weacheDate != null && TimeCalculator.isToday(weacheDate.date);
    }

    public boolean isOneDayWeather() {
        WeacheDate weacheDate = this.date;
        return weacheDate != null && this.duration == null && !TimeCalculator.isToday(weacheDate.date);
    }

    public boolean isDurationWeather() {
        return this.date == null && this.duration != null;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean hasMentionedLocation() {
        return this.hasMentionedLocation;
    }

    public WeacheDate getDate() {
        return this.date;
    }

    public MiWeather translateWeather(Template.WeatherV2 weatherV2, Instruction instruction) {
        WeacheDate weacheDate;
        this.mDataValid = false;
        this.location = new Location();
        this.location.city = weatherV2.getLocation().getMainTitle();
        parseSingleDayWeather(weatherV2);
        parseLauncher(weatherV2);
        parseAuxiliaryIntention(instruction);
        if (!(this.singleDayWeather != null || this.forecastDailyItems == null || (weacheDate = this.date) == null)) {
            this.singleDayWeather = ContainerUtil.isOutOfBound((long) weacheDate.indexDaily, this.forecastDailyItems) ? null : this.forecastDailyItems.get(this.date.indexDaily);
        }
        L.weather.i("Weather date=%s,duration=%s,hasMentionedLocation=%s", this.date, this.duration, Boolean.valueOf(this.hasMentionedLocation));
        if (!(this.singleDayWeather == null || this.date == null || ContainerUtil.isEmpty(this.forecastDailyItems))) {
            this.mDataValid = true;
        }
        if (this.duration != null && !ContainerUtil.isEmpty(this.forecastDailyItems)) {
            this.mDataValid = true;
        }
        return this;
    }

    private void parseSingleDayWeather(Template.WeatherV2 weatherV2) {
        WeatherItem transform;
        List<FullScreenTemplate.WeatherFragmentData> weather = weatherV2.getWeather();
        if (!ContainerUtil.isEmpty(weather)) {
            for (FullScreenTemplate.WeatherFragmentData weatherFragmentData : weather) {
                if (weatherFragmentData.getType() == FullScreenTemplate.WeatherFragmentDataType.CURRENT_HORIZONTAL || weatherFragmentData.getType() == FullScreenTemplate.WeatherFragmentDataType.DAY) {
                    if (weatherFragmentData.getSingleDayWeather().isPresent() && (transform = new WeatherItem().transform(weatherFragmentData.getSingleDayWeather().get())) != null) {
                        this.singleDayWeather = transform;
                        this.currentAqi = transform.aqi;
                    }
                }
            }
        }
    }

    private void parseAuxiliaryIntention(Instruction instruction) {
        if (instruction != null && (instruction.getPayload() instanceof Nlp.AuxiliaryIntention)) {
            Nlp.AuxiliaryIntention auxiliaryIntention = (Nlp.AuxiliaryIntention) instruction.getPayload();
            if (auxiliaryIntention.getType() == Nlp.IntentionType.WEATHER) {
                ObjectNode intention = auxiliaryIntention.getIntention();
                if (intention.has(Common.DATE)) {
                    parseDateV2(intention.get(Common.DATE).asText());
                }
                if (intention.has("time")) {
                    parseTime(intention.get("time").asText());
                }
                if (intention.has("duration")) {
                    parseDurationV2(intention.get("duration").asText());
                }
                this.hasMentionedLocation = intention.has("poi") || intention.has("district") || intention.has("city") || intention.has("province") || intention.has(ai.O);
            }
        }
    }

    private void parseLauncher(Template.WeatherV2 weatherV2) {
        Template.AndroidIntent androidIntent;
        this.forecastDailyItems = null;
        if (weatherV2.getLauncher() != null && weatherV2.getLauncher().isPresent()) {
            Template.Launcher launcher = weatherV2.getLauncher().get();
            if (launcher.getIntent() != null && launcher.getIntent().isPresent() && (androidIntent = launcher.getIntent().get()) != null && androidIntent.getParams().isPresent()) {
                ObjectNode objectNode = androidIntent.getParams().get();
                if (objectNode.has("big_card_data")) {
                    try {
                        JSONArray jSONArray = new JSONArray(objectNode.get("big_card_data").toString());
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            String string = jSONObject.getString("type");
                            if ("DAY".equals(string) && this.singleDayWeather == null) {
                                this.singleDayWeather = (WeatherItem) Gsons.getGson().fromJson(jSONObject.getJSONObject("single_day_weather").toString(), (Class<Object>) WeatherItem.class);
                                this.currentAqi = this.singleDayWeather.aqi;
                            }
                            if ("FORECAST_DAILY".equals(string)) {
                                this.forecastDailyItems = (List) Gsons.getGson().fromJson(jSONObject.getJSONArray("multi_weather_items").toString(), new TypeToken<List<WeatherItem>>() { // from class: com.xiaomi.micolauncher.skills.weather.model.MiWeather.1
                                }.getType());
                            }
                        }
                        L.weather.d("singleDayWeather   : %s", Gsons.getGson().toJson(this.singleDayWeather));
                        L.weather.d("forecastDailyItems : %s", Gsons.getGson().toJson(this.forecastDailyItems));
                    } catch (Exception e) {
                        this.mDataValid = false;
                        L.weather.e("parse Template.WeatherV2 error", e);
                    }
                }
            }
        }
    }

    private void parseDateV2(String str) {
        try {
            WeacheDate weacheDate = new WeacheDate(str);
            weacheDate.indexDaily = indexOfForecastDaily(str);
            this.date = weacheDate;
            L.weather.d("date : %s", this.date);
        } catch (Exception e) {
            e.printStackTrace();
            L.weather.e("parse parseDateV2 error", e);
        }
    }

    private void parseTime(String str) {
        WeacheDate weacheDate = new WeacheDate();
        try {
            weacheDate.date = new SimpleDateFormat(TIME_PATTERN).parse(str);
            weacheDate.dateStr = new SimpleDateFormat(DATE_PATTERN).format(weacheDate.date);
            weacheDate.indexDaily = indexOfForecastDaily(weacheDate.dateStr);
            this.date = weacheDate;
        } catch (ParseException e) {
            e.printStackTrace();
            L.weather.e("parse parseTime error", e);
        }
    }

    private int indexOfForecastDaily(String str) {
        if (TextUtils.isEmpty(str)) {
            L.weather.i("dateStr is empty");
            return 0;
        }
        if (ContainerUtil.hasData(this.forecastDailyItems)) {
            for (int i = 0; i < this.forecastDailyItems.size(); i++) {
                WeatherItem weatherItem = this.forecastDailyItems.get(i);
                L.weather.d("dateStr : %s, itemDate : %s", str, weatherItem.date);
                if (str.equals(weatherItem.date)) {
                    return i;
                }
            }
        }
        L.weather.i("no match date hasData=%s,dateStr=%s", Boolean.valueOf(ContainerUtil.hasData(this.forecastDailyItems)), str);
        return 0;
    }

    private void parseDurationV2(String str) {
        try {
            WeacheDuration weacheDuration = new WeacheDuration(str);
            weacheDuration.startIndexDaily = indexOfForecastDaily(weacheDuration.durationStartStr);
            weacheDuration.endIndexDaily = indexOfForecastDaily(weacheDuration.durationEndStr);
            this.duration = weacheDuration;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeatherToday getCurrentWeatherInfoV2() {
        int parseInt;
        WeatherToday weatherToday = new WeatherToday();
        Location location = this.location;
        if (location != null) {
            weatherToday.city = location.city;
        }
        String str = this.currentAqi;
        if (str != null && (parseInt = Integer.parseInt(str)) > 0) {
            weatherToday.aqi = calculateAqi(parseInt);
        }
        WeatherItem weatherItem = this.singleDayWeather;
        if (weatherItem != null) {
            weatherToday.currentTemp = weatherItem.currentTemperature;
            WTrans wTrans = new WTrans();
            wTrans.from = this.singleDayWeather.highTemperature;
            wTrans.to = this.singleDayWeather.lowTemperature;
            weatherToday.dayTemp = wTrans;
            String str2 = TextUtils.isEmpty(this.singleDayWeather.weatherCode.current) ? this.singleDayWeather.weatherCode.from : this.singleDayWeather.weatherCode.current;
            if (!TextUtils.isEmpty(str2)) {
                weatherToday.weather = Weather.value(str2);
            }
            weatherToday.currentTemp = this.singleDayWeather.currentTemperature;
            Wind wind = new Wind();
            wind.speed = new WUnit();
            if (!(this.singleDayWeather.wind == null || this.singleDayWeather.wind.speed == null)) {
                wind.speed.value = this.singleDayWeather.wind.speed.from;
            }
            wind.direction = new WUnit();
            if (!(this.singleDayWeather.wind == null || this.singleDayWeather.wind.direction == null)) {
                wind.direction.value = this.singleDayWeather.wind.direction.from;
            }
            weatherToday.wind = wind;
            weatherToday.windSpeed = wind.speed.value;
        }
        WeacheDate weacheDate = this.date;
        if (weacheDate != null) {
            weatherToday.date = weacheDate.dateStr;
        }
        weatherToday.hasMentionedLocation = this.hasMentionedLocation;
        return weatherToday;
    }

    public WeatherToday getDateWeatherInfoV2(String str) {
        WeatherToday weatherToday = new WeatherToday();
        weatherToday.city = this.location.city;
        int indexOfForecastDaily = indexOfForecastDaily(str);
        WeatherItem weatherItem = ContainerUtil.isOutOfBound((long) indexOfForecastDaily, this.forecastDailyItems) ? null : this.forecastDailyItems.get(indexOfForecastDaily);
        if (weatherItem != null) {
            weatherToday.windSpeed = (weatherItem.wind == null || weatherItem.wind.speed == null) ? "" : weatherItem.wind.speed.getBigValue();
            weatherToday.aqi = calculateAqi(weatherItem.aqi);
            weatherToday.dayTemp = new WTrans();
            weatherToday.dayTemp.from = weatherItem.highTemperature;
            weatherToday.dayTemp.to = weatherItem.lowTemperature;
            if (weatherItem.weatherCode != null) {
                Weather value = Weather.value(weatherItem.weatherCode.from);
                Weather value2 = Weather.value(weatherItem.weatherCode.to);
                if (value.priority > value2.priority) {
                    value2 = value;
                }
                weatherToday.weather = value2;
            }
        }
        weatherToday.date = str;
        weatherToday.hasMentionedLocation = this.hasMentionedLocation;
        return weatherToday;
    }

    /* loaded from: classes3.dex */
    public static class WTrans {
        public String from;
        public String to;

        public WTrans() {
        }

        public WTrans(String str, String str2) {
            this.from = str;
            this.to = str2;
        }

        public String getBigValue() {
            return Float.parseFloat(this.from) > Float.parseFloat(this.to) ? this.from : this.to;
        }

        public String getSmallValue() {
            return Float.parseFloat(this.from) < Float.parseFloat(this.to) ? this.from : this.to;
        }

        public WTrans parser(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.from = jSONObject.optString("from", "");
                this.to = jSONObject.optString("to", "");
            }
            return this;
        }
    }

    public List<Integer> getMuitlDaysUpTempV2(int i) {
        ArrayList arrayList = new ArrayList();
        int min = Math.min(i, this.forecastDailyItems.size());
        for (int i2 = 0; i2 < min; i2++) {
            arrayList.add(Integer.valueOf(Integer.parseInt(this.forecastDailyItems.get(i2).highTemperature)));
        }
        return arrayList;
    }

    public List<Integer> getMuitlDaysDownTempV2(int i) {
        ArrayList arrayList = new ArrayList();
        int min = Math.min(i, this.forecastDailyItems.size());
        for (int i2 = 0; i2 < min; i2++) {
            arrayList.add(Integer.valueOf(Integer.parseInt(this.forecastDailyItems.get(i2).lowTemperature)));
        }
        return arrayList;
    }

    /* loaded from: classes3.dex */
    public static class WeatherItem {
        @SerializedName("aqi")
        public String aqi;
        @SerializedName("current_temperature")
        public String currentTemperature;
        @SerializedName(Common.DATE)
        public String date;
        @SerializedName("high_temperature")
        public String highTemperature;
        @SerializedName("location")
        public String location;
        @SerializedName("low_temperature")
        public String lowTemperature;
        @SerializedName("weather_code")
        public WeatherCode weatherCode;
        @SerializedName("wind")
        public WeatherWind wind;

        public WeatherItem transform(Template.WeatherItem weatherItem) {
            this.date = weatherItem.getDate();
            if (weatherItem.getAqi().isPresent()) {
                this.aqi = weatherItem.getAqi().get();
            }
            this.location = weatherItem.getLocation();
            if (!weatherItem.getCurrentTemperature().isPresent()) {
                return null;
            }
            this.currentTemperature = weatherItem.getCurrentTemperature().get();
            if (!weatherItem.getHighTemperature().isPresent()) {
                return null;
            }
            this.highTemperature = weatherItem.getHighTemperature().get();
            if (!weatherItem.getLowTemperature().isPresent()) {
                return null;
            }
            this.lowTemperature = weatherItem.getLowTemperature().get();
            this.weatherCode = new WeatherCode();
            if (!weatherItem.getWeatherCode().isPresent()) {
                return null;
            }
            Template.WeatherCode weatherCode = weatherItem.getWeatherCode().get();
            if (weatherCode.getCurrent().isPresent()) {
                this.weatherCode.current = weatherCode.getCurrent().get();
            }
            if (weatherCode.getFrom().isPresent()) {
                this.weatherCode.from = weatherCode.getFrom().get();
            }
            if (weatherCode.getTo().isPresent()) {
                this.weatherCode.to = weatherCode.getTo().get();
            }
            this.wind = new WeatherWind();
            if (weatherItem.getWind().isPresent()) {
                Template.WeatherWind weatherWind = weatherItem.getWind().get();
                this.wind.speed = new WindUnit();
                if (weatherWind.getSpeed().isPresent()) {
                    Template.WeatherWindSpeed weatherWindSpeed = weatherWind.getSpeed().get();
                    if (weatherWindSpeed.getFrom().isPresent()) {
                        this.wind.speed.from = weatherWindSpeed.getFrom().get();
                    }
                    if (weatherWindSpeed.getTo().isPresent()) {
                        this.wind.speed.to = weatherWindSpeed.getTo().get();
                    }
                }
                this.wind.direction = new WindUnit();
                if (weatherWind.getDirection().isPresent()) {
                    Template.WeatherWindDirection weatherWindDirection = weatherWind.getDirection().get();
                    if (weatherWindDirection.getFrom().isPresent()) {
                        this.wind.direction.from = weatherWindDirection.getFrom().get();
                    }
                    if (weatherWindDirection.getTo().isPresent()) {
                        this.wind.direction.to = weatherWindDirection.getTo().get();
                    }
                }
            }
            return this;
        }

        public String toString() {
            return "WeatherItem{date='" + this.date + "', aqi='" + this.aqi + "', location='" + this.location + "', highTemperature='" + this.highTemperature + "', lowTemperature='" + this.lowTemperature + "', currentTemperature='" + this.currentTemperature + "', weatherCode=" + this.weatherCode.toString() + ", wind=" + this.wind.toString() + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherCode {
        @SerializedName(VersionUtil.VERSION_CURRENT)
        public String current;
        @SerializedName("from")
        public String from;
        @SerializedName("to")
        public String to;

        public String toString() {
            return "WeatherCode{from='" + this.from + "', to='" + this.to + "', current='" + this.current + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherWind {
        @SerializedName("direction")
        public WindUnit direction;
        @SerializedName("speed")
        public WindUnit speed;

        public String toString() {
            return "WeatherWind{direction=" + this.direction.toString() + ", speed=" + this.speed.toString() + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class WindUnit {
        @SerializedName(VersionUtil.VERSION_CURRENT)
        public String current;
        @SerializedName("from")
        public String from;
        @SerializedName("text")
        public String text;
        @SerializedName("to")
        public String to;

        public String getBigValue() {
            return Float.parseFloat(this.from) > Float.parseFloat(this.to) ? this.from : this.to;
        }

        public String getSmallValue() {
            return Float.parseFloat(this.from) < Float.parseFloat(this.to) ? this.from : this.to;
        }

        public String toString() {
            return "WindUnit{text='" + this.text + "', from='" + this.from + "', to='" + this.to + "'}";
        }
    }
}
