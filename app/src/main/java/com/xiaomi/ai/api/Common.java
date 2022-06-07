package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Common {

    /* loaded from: classes3.dex */
    public enum AudioType {
        UNKNOWN(-1),
        MUSIC(0),
        NEWS(1),
        RADIO_STATION(2),
        BOOKS(3),
        ANCIENT_POEM(4),
        WHITE_NOISE(5),
        VOICE(6),
        JOKE(7),
        TRANSLATION(8),
        TTS(9),
        ALERT_SOUND(10);
        
        private int id;

        AudioType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CommonDeviceCategory {
        UNKNOWN(-1),
        DEVICE_SOUNDBOX(0),
        DEVICE_STORYTELLER(1),
        DEVICE_TV(2),
        DEVICE_APPLIANCES(3),
        DEVICE_SMARTHOME(4),
        DEVICE_WEARABLES(5),
        DEVICE_PHONE(6),
        DEVICE_ROBOTS(7),
        DEVICE_HEADPHONES(8),
        DEVICE_AUTOMOTIVE(9),
        DEVICE_PC(10),
        DEVICE_TRANSPORTATIONS(11),
        APP_APPLICATION(12);
        
        private int id;

        CommonDeviceCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum Gender {
        UNKNOWN(0),
        MALE(1),
        FEMALE(2),
        CHILD(3);
        
        private int id;

        Gender(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum GenderType {
        MALE(0),
        FEMALE(1),
        CHILD(2);
        
        private int id;

        GenderType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LocaleRegion {
        MAINLAND(0),
        SINGAPORE(1);
        
        private int id;

        LocaleRegion(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MobileMIUI13DeviceAuthCode {
        UNKNOWN(-1),
        READ_WRITE_STORAGE(0),
        CAMERA(1),
        RECORDING(2),
        SEND_MESSAGE(3),
        READ_MESSAGE(4),
        READ_MULTIMEDIA_MESSAGE(5),
        CALL_TELEPHONE(6),
        TELEPHONE(7),
        CHANGE_CONTACTS(8),
        READ_CONTACTS(9),
        READ_TEL_RECORDS(10),
        LOCATION(11),
        ACCESS_MOBILE_INFO(12),
        READ_APPLICATIONS(13),
        ACCESS_CALENDAR(14),
        ACCESS_MOBILEPHONE_ACCOUNT(15),
        SYSTEM_SETTING(16),
        SHOW_FLOATING_WINDOW(17);
        
        private int id;

        MobileMIUI13DeviceAuthCode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PageLoadType {
        REFRESH(0),
        APPEND(1),
        PARTIAL_REFRESH(2);
        
        private int id;

        PageLoadType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PaymentType {
        Free(1),
        Pay(2);
        
        private int id;

        PaymentType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TTSCodec {
        MP3(0),
        WAV(1),
        OPUS(2),
        OPUS2(3),
        PCM(4);
        
        private int id;

        TTSCodec(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TimeFormat {
        HOUR_12(0),
        HOUR_24(1);
        
        private int id;

        TimeFormat(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoMediaType {
        LONG_VIDEO(0),
        SHORT_VIDEO(1),
        STATION(2),
        NEWS(3),
        GAME(4),
        OPEN_CLASS(5),
        STATION_VIDEO(6),
        NEWS_VIDEO(7),
        GAME_VIDEO(8),
        OPEN_CLASS_VIDEO(9);
        
        private int id;

        VideoMediaType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum Weekday {
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);
        
        private int id;

        Weekday(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class GeneralQuickAppInfo {
        @Required
        private String path;
        @Required
        private String pkg_name;
        private Optional<String> parameters = Optional.empty();
        private Optional<ObjectNode> extra = Optional.empty();

        public GeneralQuickAppInfo() {
        }

        public GeneralQuickAppInfo(String str, String str2) {
            this.pkg_name = str;
            this.path = str2;
        }

        @Required
        public GeneralQuickAppInfo setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public GeneralQuickAppInfo setPath(String str) {
            this.path = str;
            return this;
        }

        @Required
        public String getPath() {
            return this.path;
        }

        public GeneralQuickAppInfo setParameters(String str) {
            this.parameters = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getParameters() {
            return this.parameters;
        }

        public GeneralQuickAppInfo setExtra(ObjectNode objectNode) {
            this.extra = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getExtra() {
            return this.extra;
        }
    }

    /* loaded from: classes3.dex */
    public static class LoadmoreInfo {
        private Optional<Boolean> needs_loadmore = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<Integer> delta = Optional.empty();
        private Optional<String> token = Optional.empty();

        public LoadmoreInfo setNeedsLoadmore(boolean z) {
            this.needs_loadmore = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedsLoadmore() {
            return this.needs_loadmore;
        }

        public LoadmoreInfo setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public LoadmoreInfo setDelta(int i) {
            this.delta = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDelta() {
            return this.delta;
        }

        public LoadmoreInfo setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }
    }

    /* loaded from: classes3.dex */
    public static class MultiExecutionSequences {
        @Required
        private List<ArrayNode> secondaries;

        public MultiExecutionSequences() {
        }

        public MultiExecutionSequences(List<ArrayNode> list) {
            this.secondaries = list;
        }

        @Required
        public MultiExecutionSequences setSecondaries(List<ArrayNode> list) {
            this.secondaries = list;
            return this;
        }

        @Required
        public List<ArrayNode> getSecondaries() {
            return this.secondaries;
        }
    }

    /* loaded from: classes3.dex */
    public static class PropItem {
        @Required
        private String name;
        @Required
        private String value;

        public PropItem() {
        }

        public PropItem(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        @Required
        public PropItem setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public PropItem setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }
    }

    /* loaded from: classes3.dex */
    public static class TTSTone {
        @Required
        private String vendor;
        private Optional<String> speaker = Optional.empty();
        private Optional<String> lang = Optional.empty();

        public TTSTone() {
        }

        public TTSTone(String str) {
            this.vendor = str;
        }

        @Required
        public TTSTone setVendor(String str) {
            this.vendor = str;
            return this;
        }

        @Required
        public String getVendor() {
            return this.vendor;
        }

        public TTSTone setSpeaker(String str) {
            this.speaker = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSpeaker() {
            return this.speaker;
        }

        public TTSTone setLang(String str) {
            this.lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLang() {
            return this.lang;
        }
    }

    /* loaded from: classes3.dex */
    public static class UserInfo {
        @Required
        private Gender gender;
        private Optional<Long> user_id = Optional.empty();

        public UserInfo() {
        }

        public UserInfo(Gender gender) {
            this.gender = gender;
        }

        public UserInfo setUserId(long j) {
            this.user_id = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getUserId() {
            return this.user_id;
        }

        @Required
        public UserInfo setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        @Required
        public Gender getGender() {
            return this.gender;
        }
    }
}
