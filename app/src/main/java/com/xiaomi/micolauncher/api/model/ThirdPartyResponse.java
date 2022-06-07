package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.c;
import com.xiaomi.mico.common.Gsons;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ThirdPartyResponse {

    /* loaded from: classes3.dex */
    public static class AppVersionInfo extends VersionInfo implements Serializable {
        private static final long serialVersionUID = 244831732543473368L;
        public UpgradeInfo upgradeInfo;
    }

    /* loaded from: classes3.dex */
    public static class ChildProfile implements Serializable {
        private static final long serialVersionUID = 2599176925493927197L;
        public long birthday;
        public String nickName;
        public int sex;
    }

    /* loaded from: classes3.dex */
    public static class CityData {
        @SerializedName("City")
        public List<HashMap<String, List<CityDetail>>> city;
    }

    /* loaded from: classes3.dex */
    public static class CityDetail {
        public String key;
        public String name;
    }

    /* loaded from: classes3.dex */
    public static class DidiPOIResponse implements Serializable {
        private static final long serialVersionUID = -5584075369517440451L;
        public String address;
        public String city;
        @SerializedName(c.C)
        public double latitude;
        @SerializedName(c.D)
        public double longitude;
        @SerializedName("displayname")
        public String name;
    }

    /* loaded from: classes3.dex */
    public static class GrayUpdateData {
        public GrayUpdateInfo currentInfo;
        @SerializedName("upgradeInfo")
        public GrayUpdateInfo updateInfo;
    }

    /* loaded from: classes3.dex */
    public static class GrayUpdateInfo {
        public String changelogUrl;
        public String description;
        public String hash;
        public String link;
        public String otherParam;
        public int size;
        public String upgradeId;
        @SerializedName("toVersion")
        public String version = "";
        public int weight;
    }

    /* loaded from: classes3.dex */
    public static class GrayUpgradeResponse {
        public int code;
        public GrayUpdateData data;
    }

    /* loaded from: classes3.dex */
    public static class ImageUploadData {
        public List<String> list;
    }

    /* loaded from: classes3.dex */
    public static class ImageUploadResponse {
        public ImageUploadData data;
    }

    /* loaded from: classes3.dex */
    public static class POIResponse implements Serializable {
        private static final long serialVersionUID = -6434066585657187461L;
        public String city;
        public String district;
        public double latitude;
        public double longitude;
        public String name;
    }

    /* loaded from: classes3.dex */
    public static class PlateInfo implements Serializable {
        private static final long serialVersionUID = 6010549617031797376L;
        public int index = -1;
        public String name;
        public boolean newEnergy;
        public String number;
    }

    /* loaded from: classes3.dex */
    public static class RomVersionInfo extends VersionInfo implements Serializable {
        private static final long serialVersionUID = -5392236825848492395L;
        public List<UpgradeInfo> upgradeSteps;
    }

    /* loaded from: classes3.dex */
    public static class UpdateData {
        public AppVersionInfo appInfo;
        public boolean conflict;
        public List<RomVersionInfo> deviceInfo;
    }

    /* loaded from: classes3.dex */
    public static class UpdateResponse {
        public int code;
        public UpdateData data;
    }

    /* loaded from: classes3.dex */
    public static class UpgradeInfo implements Serializable {
        private static final long serialVersionUID = -4619172415803890838L;
        public String changelogUrl;
        public String channel;
        public String hash;
        public String link;
        public long size;
        public String version;
    }

    /* loaded from: classes3.dex */
    public static class UserPrivacy {
        public boolean musicPersonalization;
        public boolean wakeWordUpload;
    }

    /* loaded from: classes3.dex */
    public static class VersionInfo {
        public String changelogUrl;
        public String channel;
        public String deviceId;
        public String deviceName;
        @SerializedName("hardwareVersion")
        public String model;
        public boolean needUpgrade;
        @SerializedName("romVersion")
        public String version;
    }

    /* loaded from: classes3.dex */
    public static class WXTokenResponse {
        public String accessToken;
        public int expiresIn;
        public String openID;
        public String refreshToken;
    }

    /* loaded from: classes3.dex */
    public class TrafficInfo {
        public Integer alarmModel = 1;
        public Integer alermCycle = 0;
        public String arriveTime;
        public String destination;
        public Integer estimateDuration;
        public Integer navigationMode;
        public String origination;
        public List<PlateInfo> plateInfoList;
        public List<String> plateNumberList;
        public String speakerLocation;

        public TrafficInfo() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public static class TrafficAddress {
        public String city;
        public String district;
        public double lat;
        public double lng;
        public String name;

        public TrafficAddress(POIResponse pOIResponse) {
            this.name = pOIResponse.name;
            this.lat = pOIResponse.latitude;
            this.lng = pOIResponse.longitude;
            this.city = pOIResponse.city;
            this.district = pOIResponse.district;
        }

        public POIResponse toPOIResponse() {
            POIResponse pOIResponse = new POIResponse();
            pOIResponse.name = this.name;
            pOIResponse.latitude = this.lat;
            pOIResponse.longitude = this.lng;
            pOIResponse.city = this.city;
            pOIResponse.district = this.district;
            return pOIResponse;
        }

        public String toString() {
            return Gsons.getGson().toJson(this);
        }

        public static TrafficAddress from(String str) {
            return (TrafficAddress) Gsons.getGson().fromJson(str, (Class<Object>) TrafficAddress.class);
        }
    }

    /* loaded from: classes3.dex */
    public class FeedbackResponse {
        public String id;

        public FeedbackResponse() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class FeedbackHistoryResponse {
        public List<FeedbackHistory> feedbackList;

        public FeedbackHistoryResponse() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class FeedbackHistory {
        public long createTime;
        public String id;
        public String memo;
        public String query;
        public String response;
        public int status;
        public long updateTime;

        public FeedbackHistory() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class BabySleepMode {
        public String end;
        public boolean isOpen;
        public String start;

        public BabySleepMode() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class LanguageMap {
        @SerializedName("newsCategoryToLocale")
        public List<Map<String, String>> news;
        @SerializedName("sexToLocale")
        public List<Map<String, String>> sex;
        @SerializedName("zodiacSignToLocale")
        public List<Map<String, String>> xingzuo;

        public LanguageMap() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class QrLoginResponse {
        public String loginUrl;
        public String lp;
        public String qr;

        public QrLoginResponse() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class QrLoginResultResponse {
        public String cUserId;
        public String captchaUrl;
        public String desc;
        public String passToken;
        public String psecurity;
        public String pwd;
        public String qs;
        public String ssecurity;
        public String userId;

        public QrLoginResultResponse() {
            ThirdPartyResponse.this = r1;
        }

        public String toString() {
            return "QrLoginResultResponse{captchaUrl='" + this.captchaUrl + "', desc='" + this.desc + "', passToken='" + this.passToken + "', pwd='" + this.pwd + "', ssecurity='" + this.ssecurity + "', cUserId='" + this.cUserId + "', userId='" + this.userId + "', psecurity='" + this.psecurity + "', qs='" + this.qs + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public class UserCardResponse {
        public UserCardData data;

        public UserCardResponse() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class UserCardData {
        public List<UserCard> list;

        public UserCardData() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class UserCard {
        public String aliasNick;
        public String miliaoIcon;
        public String miliaoNick;
        public String userId;

        public UserCard() {
            ThirdPartyResponse.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public static class CmccDevice {
        public String cmccID;
        public String hardware;
        public String miotDID;

        public String toString() {
            return "CmccDevice{miotDID='" + this.miotDID + "', cmccID='" + this.cmccID + "', hardware='" + this.hardware + "'}";
        }
    }
}
