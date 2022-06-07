package com.xiaomi.micolauncher.api.model;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ThirdParty {

    /* loaded from: classes3.dex */
    public class GrayUpdateResponse {
        public String latestVersion;
        public int latestVersionCode;
        public List<String> releaseNotes;
        public String url;

        public GrayUpdateResponse() {
        }
    }

    /* loaded from: classes3.dex */
    public class WeatherLocation {
        public String affiliation;
        public String latitude;
        public String locationKey;
        public String longitude;
        public String name;

        public WeatherLocation() {
        }
    }

    /* loaded from: classes3.dex */
    public class WeatherCurrentForecast {
        public Aqi currentAqi;
        public WeatherInfo currentWeather;
        public boolean localCache;

        public WeatherCurrentForecast() {
        }

        /* loaded from: classes3.dex */
        public class Aqi {
            public String aqi;
            public String co;
            public String pm10;
            public String pm25;

            public Aqi() {
            }
        }

        /* loaded from: classes3.dex */
        public class WeatherInfo {
            public Property feelsLike;
            public Property humidity;
            public Property temperature;
            public int weather;
            public Wind wind;

            public WeatherInfo() {
            }

            /* loaded from: classes3.dex */
            public class Wind {
                public Property direction;
                public Property speed;

                public Wind() {
                }
            }

            /* loaded from: classes3.dex */
            public class Property {
                public String unit;
                public String value;

                public Property() {
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public class WeatherAlert {
        public String alertId;
        public String detail;
        public String level;
        public String locationKey;
        public String pubTime;
        public String title;
        public String type;

        public WeatherAlert() {
        }
    }

    /* loaded from: classes3.dex */
    public static class CpInfo {
        public List<String> icons;
        public String name;

        public CpInfo() {
        }

        public CpInfo(String str, String str2) {
            this.name = str;
            this.icons = new ArrayList();
            this.icons.add(str2);
        }
    }
}
