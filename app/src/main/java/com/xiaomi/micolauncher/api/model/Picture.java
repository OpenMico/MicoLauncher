package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

/* loaded from: classes3.dex */
public class Picture {

    /* loaded from: classes3.dex */
    public class ScreenSaver {
        public String date;
        public String description;
        public String id;
        public String md5;
        public String status;
        public String tag;
        public String tags;
        public String title;
        public String url;

        public ScreenSaver() {
        }
    }

    /* loaded from: classes3.dex */
    public class Advertise implements Serializable {
        public List<Pictorial> adList;
        public List<Integer> adPositions;

        public Advertise() {
        }
    }

    /* loaded from: classes3.dex */
    public class Pictorial implements Serializable {
        public static final String TYPE_PICTURE = "picture";
        public static final String TYPE_VIDEO = "video";
        @SerializedName("miuiAdInfo")
        public AdInfo adInfo;
        public String description;
        public long id;
        public boolean isAd;
        public String jumpUrl;
        public String title;
        public String type;
        public String url;
        public List<VideoInfo> videoInfoList;

        public Pictorial() {
        }

        public boolean isVideo() {
            return "video".equals(this.type);
        }

        public boolean isPicture() {
            return "picture".equals(this.type);
        }

        public String getRes480() {
            if (ContainerUtil.isEmpty(this.videoInfoList)) {
                return "";
            }
            for (VideoInfo videoInfo : this.videoInfoList) {
                if ("480".equals(videoInfo.videoResolution)) {
                    return videoInfo.downloadUrl;
                }
            }
            return this.videoInfoList.get(0).downloadUrl;
        }

        public String toString() {
            return "Pictorial{id=" + this.id + ", url='" + this.url + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public class AdInfo implements Serializable {
        @SerializedName("clickMonitorUrls")
        public List<String> clickMonitorUrls;
        @SerializedName("deeplink")
        public String deeplink;
        @SerializedName("ex")
        public String ex;
        @SerializedName("landingPageUrl")
        public String landingPageUrl;
        @SerializedName("targetType")
        public int targetType;
        @SerializedName("viewMonitorUrls")
        public List<String> viewMonitorUrls;

        public AdInfo() {
        }

        public boolean isUrl() {
            return this.targetType == 1;
        }

        public boolean onlyShow() {
            return TextUtils.isEmpty(this.landingPageUrl) && TextUtils.isEmpty(this.deeplink);
        }

        public String toString() {
            return "AdInfo{ex='" + this.ex + "', viewMonitorUrls=" + this.viewMonitorUrls + ", clickMonitorUrls=" + this.clickMonitorUrls + ", targetType=" + this.targetType + ", landingPageUrl='" + this.landingPageUrl + "', deeplink='" + this.deeplink + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public class VideoInfo implements Serializable {
        private static final String RES_240 = "240";
        private static final String RES_480 = "480";
        private static final String RES_720 = "720";
        public String downloadUrl;
        public String videoEncodingType;
        public String videoResolution;

        public VideoInfo() {
        }
    }

    /* loaded from: classes3.dex */
    public class ReportResponse {
        public int code;
        public String message;

        public ReportResponse() {
        }
    }

    /* loaded from: classes3.dex */
    public class Dial {
        public int dialId;
        public List<DialInfo> dialInfoList;
        public String dialName;

        public Dial() {
        }
    }

    /* loaded from: classes3.dex */
    public class DialInfo {
        private static final int GROUP_DARK_NIGHT = 6;
        private static final int GROUP_NIGHT = 5;
        public String background;
        public String beginTime;
        public String description;
        public String endTime;
        public String foreground;
        public int group;
        public String mountains;

        public DialInfo() {
        }

        public boolean isNight() {
            int i = this.group;
            return i == 5 || i == 6;
        }

        public boolean inTimePeriod(int[] iArr) {
            try {
                int[] StringArrayToIntArray = StringArrayToIntArray(this.beginTime.split(Constants.COLON_SEPARATOR));
                int[] StringArrayToIntArray2 = StringArrayToIntArray(this.endTime.split(Constants.COLON_SEPARATOR));
                return compareIntArray(StringArrayToIntArray, StringArrayToIntArray2) ? compareIntArray(StringArrayToIntArray, iArr) && compareIntArray(iArr, StringArrayToIntArray2) : compareIntArray(StringArrayToIntArray, iArr) || compareIntArray(iArr, StringArrayToIntArray2);
            } catch (Exception unused) {
                return false;
            }
        }

        boolean compareIntArray(int[] iArr, int[] iArr2) {
            return iArr2[0] > iArr[0] || (iArr2[0] == iArr[0] && iArr2[1] >= iArr[1]);
        }

        int[] StringArrayToIntArray(String[] strArr) {
            return Stream.of((Object[]) strArr).mapToInt($$Lambda$wddj3hVVrg0MkscpMtYt3BzY8Y.INSTANCE).toArray();
        }
    }
}
