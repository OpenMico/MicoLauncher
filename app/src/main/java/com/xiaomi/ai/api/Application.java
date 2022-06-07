package com.xiaomi.ai.api;

import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Application {

    @NamespaceName(name = "CancelUserDeviceExecution", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class CancelUserDeviceExecution implements EventPayload {
    }

    @NamespaceName(name = "DisableDriveMode", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class DisableDriveMode implements InstructionPayload {
    }

    @NamespaceName(name = "EnableDriveMode", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class EnableDriveMode implements InstructionPayload {
    }

    @NamespaceName(name = "StopRelayContent", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class StopRelayContent implements InstructionPayload {
    }

    @NamespaceName(name = "WholeHousePlay", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class WholeHousePlay implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum AdsTrackType {
        TOAST(0),
        AUDIO_PLAYER(1),
        ATTACHMENT(2),
        SPEAKER(3);
        
        private int id;

        AdsTrackType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ApplicationOp {
        UNKNOWN(-1),
        OPEN(0),
        INSTALL(1),
        UNINSTALL(2),
        CLOSE(3),
        SEARCH(4);
        
        private int id;

        ApplicationOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CameraLensType {
        UNKNOWN(-1),
        BACK(0),
        FRONT(1);
        
        private int id;

        CameraLensType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CameraModuleType {
        UNKNOWN(-1),
        VIDEO(Opcodes.IF_ICMPGE),
        PHOTO(Opcodes.IF_ICMPGT),
        PORTRAIT(171);
        
        private int id;

        CameraModuleType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CpLevel {
        ONE(1),
        MULTIPLE(2),
        AGGREGATE(3);
        
        private int id;

        CpLevel(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ExamEventType {
        UNKNOWN(-1),
        REGISTRATION(0),
        PRINT_ADMISSION_CARD(1),
        TAKE_EXAM(2),
        RESULT_PUBLISH(3);
        
        private int id;

        ExamEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ExamName {
        UNKNOWN(-1),
        CET_4(0),
        CET_6(1),
        UNGEE(2);
        
        private int id;

        ExamName(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum HintTag {
        UNKNOWN(-1),
        EXECUTE(0),
        TOO_MANY(1),
        NOT_INSTALLED(2),
        NOT_SUPPORTED(3),
        ACCURATE_MATCH(4),
        NO_MATCH(5),
        CANCEL(6);
        
        private int id;

        HintTag(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MIOTExecuteMode {
        ONLINE_NLP_OFFLINE_EXEC(0),
        ONLINE_NLP_ONLINE_EXEC(1),
        RACING_NLP_OFFLINE_EXEC(2),
        OFFLINE_NLP_OFFLINE_EXEC(3);
        
        private int id;

        MIOTExecuteMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MiPlayAudioRelayDeviceStatus {
        UNKNOWN(-1),
        IDLE(0),
        PLAYING(1),
        CAST_PLAYING(2),
        CAST_PAUSED(3);
        
        private int id;

        MiPlayAudioRelayDeviceStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum RelayContentType {
        VIDEO_TELEPHONE(0),
        VIDEO(1),
        AUDIO(2);
        
        private int id;

        RelayContentType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ShareStyleType {
        CARD(0),
        BUTTON(1),
        HIGHLIGHT_BUTTON(2);
        
        private int id;

        ShareStyleType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ShareType {
        UNKNOWN(-1),
        LINK(0),
        PIC(1),
        ANIMATION(2),
        WX_MINI_PROGRAM(3);
        
        private int id;

        ShareType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SimulateClickType {
        UNKNOWN(-1),
        DEFAULT(0),
        WECHAT(1),
        PERSONALIZE(2);
        
        private int id;

        SimulateClickType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SwitchFeature {
        UNKNOWN(-1),
        SMART_KID_PROTECTION(0),
        VOICE_ASSISTANT_BROADCAST_IN_SILENT(1),
        AI_READING(2);
        
        private int id;

        SwitchFeature(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TTSActionSourceType {
        UNKNOWN(-1),
        SPEAK(0),
        AUDIO_PLAY(1);
        
        private int id;

        TTSActionSourceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ToneName {
        UNKNOWN(-1),
        MI_TANG(0),
        QING_CONG(1),
        MO_LI(2),
        PAO_FU(3),
        MATURE_MALE(4),
        MATURE_FEMALE(5);
        
        private int id;

        ToneName(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ToneType {
        UNKNOWN(-1),
        MALE(0),
        FEMALE(1),
        CHILD(2);
        
        private int id;

        ToneType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TranslationType {
        UNKNOWN(-1),
        DIALOG(0),
        WORD(1),
        SWITCH_WORD(2),
        SWITCH_SYNONYM_WORD(3);
        
        private int id;

        TranslationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum UserVipType {
        UNKNOWN(-1),
        KIDS(1),
        EDU_PRIMARY(2),
        EDU_JUNIOR(3),
        EDU_SENIOR(4),
        CHANGBA(5),
        QUANMIN_KTV(6),
        QQ_MUSIC(7),
        DVD(8),
        MOVIES(9);
        
        private int id;

        UserVipType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VoiceWakeupType {
        UNKNOWN(-1),
        TYPE_A(0);
        
        private int id;

        VoiceWakeupType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum WXMiniProgramType {
        TEST(0),
        PREVIEW(1),
        RELEASE(2);
        
        private int id;

        WXMiniProgramType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum WeiXinDriveModeOp {
        UNKNOWN(-1),
        REPROMPT(0),
        READ_MESSAGE(1),
        NO_READ(2),
        SEND_MESSAGE(3),
        REINPUT_MESSAGE(4),
        SKIP(5),
        CLOSE_IM_BROADCAST(6),
        BLOCK_MESSAGE_SENDER(7),
        PROACTIVE_SEND_MESSAGE(8),
        PROACTIVE_SEND_MESSAGE_SKIP(9);
        
        private int id;

        WeiXinDriveModeOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AdsInfo", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class AdsInfo implements InstructionPayload {
        private Optional<List<AdsTrackInfo>> track_info = Optional.empty();

        public AdsInfo setTrackInfo(List<AdsTrackInfo> list) {
            this.track_info = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AdsTrackInfo>> getTrackInfo() {
            return this.track_info;
        }
    }

    /* loaded from: classes3.dex */
    public static class AdsTrackInfo {
        @Required
        private List<String> click_monitor_urls;
        @Required
        private String extra;
        private Optional<String> id = Optional.empty();
        private Optional<AdsTrackType> type = Optional.empty();
        @Required
        private List<String> view_monitor_urls;

        public AdsTrackInfo() {
        }

        public AdsTrackInfo(String str, List<String> list, List<String> list2) {
            this.extra = str;
            this.view_monitor_urls = list;
            this.click_monitor_urls = list2;
        }

        @Required
        public AdsTrackInfo setExtra(String str) {
            this.extra = str;
            return this;
        }

        @Required
        public String getExtra() {
            return this.extra;
        }

        @Required
        public AdsTrackInfo setViewMonitorUrls(List<String> list) {
            this.view_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getViewMonitorUrls() {
            return this.view_monitor_urls;
        }

        @Required
        public AdsTrackInfo setClickMonitorUrls(List<String> list) {
            this.click_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getClickMonitorUrls() {
            return this.click_monitor_urls;
        }

        public AdsTrackInfo setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public AdsTrackInfo setType(AdsTrackType adsTrackType) {
            this.type = Optional.ofNullable(adsTrackType);
            return this;
        }

        public Optional<AdsTrackType> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "AppDetail", namespace = AIApiConstants.Application.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class AppDetail implements ContextPayload {
        @Required
        private List<AppItem> available_apps;
        private Optional<AppItem> foreground_app = Optional.empty();

        public AppDetail() {
        }

        public AppDetail(List<AppItem> list) {
            this.available_apps = list;
        }

        public AppDetail setForegroundApp(AppItem appItem) {
            this.foreground_app = Optional.ofNullable(appItem);
            return this;
        }

        public Optional<AppItem> getForegroundApp() {
            return this.foreground_app;
        }

        @Required
        public AppDetail setAvailableApps(List<AppItem> list) {
            this.available_apps = list;
            return this;
        }

        @Required
        public List<AppItem> getAvailableApps() {
            return this.available_apps;
        }
    }

    @NamespaceName(name = "AppDetailV1", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class AppDetailV1 implements ContextPayload {
        @Required
        private List<AppItem> available_apps;
        private Optional<String> foreground_app = Optional.empty();
        private Optional<List<String>> recently_used_apps = Optional.empty();
        private Optional<List<AppItem>> available_quick_apps = Optional.empty();

        public AppDetailV1() {
        }

        public AppDetailV1(List<AppItem> list) {
            this.available_apps = list;
        }

        @Required
        public AppDetailV1 setAvailableApps(List<AppItem> list) {
            this.available_apps = list;
            return this;
        }

        @Required
        public List<AppItem> getAvailableApps() {
            return this.available_apps;
        }

        public AppDetailV1 setForegroundApp(String str) {
            this.foreground_app = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForegroundApp() {
            return this.foreground_app;
        }

        public AppDetailV1 setRecentlyUsedApps(List<String> list) {
            this.recently_used_apps = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getRecentlyUsedApps() {
            return this.recently_used_apps;
        }

        public AppDetailV1 setAvailableQuickApps(List<AppItem> list) {
            this.available_quick_apps = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AppItem>> getAvailableQuickApps() {
            return this.available_quick_apps;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppExtra {
        @Required
        private String pkg_name;
        private Optional<Template.AppControl> control = Optional.empty();
        private Optional<Template.AppAds> ads = Optional.empty();

        public AppExtra() {
        }

        public AppExtra(String str) {
            this.pkg_name = str;
        }

        @Required
        public AppExtra setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        public AppExtra setControl(Template.AppControl appControl) {
            this.control = Optional.ofNullable(appControl);
            return this;
        }

        public Optional<Template.AppControl> getControl() {
            return this.control;
        }

        public AppExtra setAds(Template.AppAds appAds) {
            this.ads = Optional.ofNullable(appAds);
            return this;
        }

        public Optional<Template.AppAds> getAds() {
            return this.ads;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppItem {
        @Required
        private String pkg_name;
        @Required
        private int version_code;
        private Optional<String> version_name = Optional.empty();
        private Optional<Integer> framework_version = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<Boolean> accessibility = Optional.empty();
        private Optional<Boolean> is_background = Optional.empty();

        public AppItem() {
        }

        public AppItem(String str, int i) {
            this.pkg_name = str;
            this.version_code = i;
        }

        @Required
        public AppItem setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public AppItem setVersionCode(int i) {
            this.version_code = i;
            return this;
        }

        @Required
        public int getVersionCode() {
            return this.version_code;
        }

        public AppItem setVersionName(String str) {
            this.version_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVersionName() {
            return this.version_name;
        }

        public AppItem setFrameworkVersion(int i) {
            this.framework_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getFrameworkVersion() {
            return this.framework_version;
        }

        public AppItem setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public AppItem setAccessibility(boolean z) {
            this.accessibility = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAccessibility() {
            return this.accessibility;
        }

        public AppItem setIsBackground(boolean z) {
            this.is_background = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isBackground() {
            return this.is_background;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppState {
        private Optional<Boolean> support_smart_app = Optional.empty();
        private Optional<Boolean> show_cp_icon = Optional.empty();

        public AppState setSupportSmartApp(boolean z) {
            this.support_smart_app = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSupportSmartApp() {
            return this.support_smart_app;
        }

        public AppState setShowCpIcon(boolean z) {
            this.show_cp_icon = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShowCpIcon() {
            return this.show_cp_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class ApplicationStatePayload {
        private Optional<ApplicationSupport> supports = Optional.empty();

        public ApplicationStatePayload setSupports(ApplicationSupport applicationSupport) {
            this.supports = Optional.ofNullable(applicationSupport);
            return this;
        }

        public Optional<ApplicationSupport> getSupports() {
            return this.supports;
        }
    }

    /* loaded from: classes3.dex */
    public static class ApplicationSupport {
        private Optional<Boolean> smart_alarm = Optional.empty();
        private Optional<Boolean> ai_subtitles = Optional.empty();

        public ApplicationSupport setSmartAlarm(boolean z) {
            this.smart_alarm = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSmartAlarm() {
            return this.smart_alarm;
        }

        public ApplicationSupport setAiSubtitles(boolean z) {
            this.ai_subtitles = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAiSubtitles() {
            return this.ai_subtitles;
        }
    }

    /* loaded from: classes3.dex */
    public static class AvatarAction {
        private Optional<String> action = Optional.empty();
        private Optional<String> expression = Optional.empty();
        private Optional<Boolean> is_empty = Optional.empty();
        @Required
        private int location;

        public AvatarAction() {
        }

        public AvatarAction(int i) {
            this.location = i;
        }

        @Required
        public AvatarAction setLocation(int i) {
            this.location = i;
            return this;
        }

        @Required
        public int getLocation() {
            return this.location;
        }

        public AvatarAction setAction(String str) {
            this.action = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAction() {
            return this.action;
        }

        public AvatarAction setExpression(String str) {
            this.expression = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpression() {
            return this.expression;
        }

        public AvatarAction setIsEmpty(boolean z) {
            this.is_empty = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEmpty() {
            return this.is_empty;
        }
    }

    /* loaded from: classes3.dex */
    public static class AvatarActionInfo {
        private Optional<Boolean> is_end = Optional.empty();
        @Required
        private String time_info;

        public AvatarActionInfo() {
        }

        public AvatarActionInfo(String str) {
            this.time_info = str;
        }

        @Required
        public AvatarActionInfo setTimeInfo(String str) {
            this.time_info = str;
            return this;
        }

        @Required
        public String getTimeInfo() {
            return this.time_info;
        }

        public AvatarActionInfo setIsEnd(boolean z) {
            this.is_end = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnd() {
            return this.is_end;
        }
    }

    @NamespaceName(name = "AvatarRequest", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class AvatarRequest implements EventPayload {
        private Optional<String> query = Optional.empty();
        private Optional<Settings.TtsConfig> tts = Optional.empty();

        public AvatarRequest setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public AvatarRequest setTts(Settings.TtsConfig ttsConfig) {
            this.tts = Optional.ofNullable(ttsConfig);
            return this;
        }

        public Optional<Settings.TtsConfig> getTts() {
            return this.tts;
        }
    }

    @NamespaceName(name = "BaikeEvent", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class BaikeEvent implements EventPayload {
        private Optional<Template.WikiEvent> wiki_data = Optional.empty();

        public BaikeEvent setWikiData(Template.WikiEvent wikiEvent) {
            this.wiki_data = Optional.ofNullable(wikiEvent);
            return this;
        }

        public Optional<Template.WikiEvent> getWikiData() {
            return this.wiki_data;
        }
    }

    @NamespaceName(name = "CameraState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class CameraState implements ContextPayload {
        @Required
        private CameraLensType lens;
        @Required
        private CameraModuleType module;

        public CameraState() {
        }

        public CameraState(CameraModuleType cameraModuleType, CameraLensType cameraLensType) {
            this.module = cameraModuleType;
            this.lens = cameraLensType;
        }

        @Required
        public CameraState setModule(CameraModuleType cameraModuleType) {
            this.module = cameraModuleType;
            return this;
        }

        @Required
        public CameraModuleType getModule() {
            return this.module;
        }

        @Required
        public CameraState setLens(CameraLensType cameraLensType) {
            this.lens = cameraLensType;
            return this;
        }

        @Required
        public CameraLensType getLens() {
            return this.lens;
        }
    }

    /* loaded from: classes3.dex */
    public static class CheckAppFailedItem {
        @Required
        private int cur_version;
        @Required
        private String pkg_name;
        private Optional<Boolean> launched = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<Boolean> is_system_app = Optional.empty();

        public CheckAppFailedItem() {
        }

        public CheckAppFailedItem(String str, int i) {
            this.pkg_name = str;
            this.cur_version = i;
        }

        @Required
        public CheckAppFailedItem setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public CheckAppFailedItem setCurVersion(int i) {
            this.cur_version = i;
            return this;
        }

        @Required
        public int getCurVersion() {
            return this.cur_version;
        }

        public CheckAppFailedItem setLaunched(boolean z) {
            this.launched = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLaunched() {
            return this.launched;
        }

        public CheckAppFailedItem setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public CheckAppFailedItem setIsSystemApp(boolean z) {
            this.is_system_app = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSystemApp() {
            return this.is_system_app;
        }
    }

    /* loaded from: classes3.dex */
    public static class CheckAppItem {
        @Required
        private int min_version;
        @Required
        private String pkg_name;
        private Optional<Boolean> launched = Optional.empty();
        private Optional<String> name = Optional.empty();

        public CheckAppItem() {
        }

        public CheckAppItem(String str, int i) {
            this.pkg_name = str;
            this.min_version = i;
        }

        @Required
        public CheckAppItem setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public CheckAppItem setMinVersion(int i) {
            this.min_version = i;
            return this;
        }

        @Required
        public int getMinVersion() {
            return this.min_version;
        }

        public CheckAppItem setLaunched(boolean z) {
            this.launched = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLaunched() {
            return this.launched;
        }

        public CheckAppItem setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }
    }

    @NamespaceName(name = "CheckApps", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class CheckApps implements InstructionPayload {
        @Required
        private List<CheckAppItem> apps;
        private Optional<List<String>> names = Optional.empty();

        public CheckApps() {
        }

        public CheckApps(List<CheckAppItem> list) {
            this.apps = list;
        }

        @Required
        public CheckApps setApps(List<CheckAppItem> list) {
            this.apps = list;
            return this;
        }

        @Required
        public List<CheckAppItem> getApps() {
            return this.apps;
        }

        public CheckApps setNames(List<String> list) {
            this.names = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getNames() {
            return this.names;
        }
    }

    @NamespaceName(name = "CheckAppsFailed", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class CheckAppsFailed implements EventPayload {
        @Required
        private List<CheckAppFailedItem> apps;
        private Optional<String> dialog_id = Optional.empty();

        public CheckAppsFailed() {
        }

        public CheckAppsFailed(List<CheckAppFailedItem> list) {
            this.apps = list;
        }

        @Required
        public CheckAppsFailed setApps(List<CheckAppFailedItem> list) {
            this.apps = list;
            return this;
        }

        @Required
        public List<CheckAppFailedItem> getApps() {
            return this.apps;
        }

        public CheckAppsFailed setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }
    }

    @NamespaceName(name = "CheckAuths", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class CheckAuths implements InstructionPayload {
        @Required
        private List<Common.MobileMIUI13DeviceAuthCode> voice_assistant_auth_codes;

        public CheckAuths() {
        }

        public CheckAuths(List<Common.MobileMIUI13DeviceAuthCode> list) {
            this.voice_assistant_auth_codes = list;
        }

        @Required
        public CheckAuths setVoiceAssistantAuthCodes(List<Common.MobileMIUI13DeviceAuthCode> list) {
            this.voice_assistant_auth_codes = list;
            return this;
        }

        @Required
        public List<Common.MobileMIUI13DeviceAuthCode> getVoiceAssistantAuthCodes() {
            return this.voice_assistant_auth_codes;
        }
    }

    /* loaded from: classes3.dex */
    public static class CpState {
        @Required
        private CpLevel cp_level;
        @Required
        private List<String> cps;

        public CpState() {
        }

        public CpState(List<String> list, CpLevel cpLevel) {
            this.cps = list;
            this.cp_level = cpLevel;
        }

        @Required
        public CpState setCps(List<String> list) {
            this.cps = list;
            return this;
        }

        @Required
        public List<String> getCps() {
            return this.cps;
        }

        @Required
        public CpState setCpLevel(CpLevel cpLevel) {
            this.cp_level = cpLevel;
            return this;
        }

        @Required
        public CpLevel getCpLevel() {
            return this.cp_level;
        }
    }

    @NamespaceName(name = "DisplayAvatar", namespace = AIApiConstants.Application.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class DisplayAvatar implements InstructionPayload {
        @Required
        private String action;

        public DisplayAvatar() {
        }

        public DisplayAvatar(String str) {
            this.action = str;
        }

        @Required
        public DisplayAvatar setAction(String str) {
            this.action = str;
            return this;
        }

        @Required
        public String getAction() {
            return this.action;
        }
    }

    @NamespaceName(name = "DisplayAvatarV2", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class DisplayAvatarV2 implements InstructionPayload {
        @Required
        private List<AvatarAction> avatar_list;

        public DisplayAvatarV2() {
        }

        public DisplayAvatarV2(List<AvatarAction> list) {
            this.avatar_list = list;
        }

        @Required
        public DisplayAvatarV2 setAvatarList(List<AvatarAction> list) {
            this.avatar_list = list;
            return this;
        }

        @Required
        public List<AvatarAction> getAvatarList() {
            return this.avatar_list;
        }
    }

    @NamespaceName(name = "DisplayAvatarV3", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class DisplayAvatarV3 implements InstructionPayload {
        private Optional<TTSActionSourceType> source_type = Optional.empty();
        private Optional<AvatarActionInfo> avatar_action_info = Optional.empty();
        private Optional<List<AvatarAction>> body_action = Optional.empty();
        private Optional<String> depend_instruction_id = Optional.empty();
        private Optional<Integer> sequence_id = Optional.empty();
        private Optional<Boolean> no_time_info = Optional.empty();

        public DisplayAvatarV3 setSourceType(TTSActionSourceType tTSActionSourceType) {
            this.source_type = Optional.ofNullable(tTSActionSourceType);
            return this;
        }

        public Optional<TTSActionSourceType> getSourceType() {
            return this.source_type;
        }

        public DisplayAvatarV3 setAvatarActionInfo(AvatarActionInfo avatarActionInfo) {
            this.avatar_action_info = Optional.ofNullable(avatarActionInfo);
            return this;
        }

        public Optional<AvatarActionInfo> getAvatarActionInfo() {
            return this.avatar_action_info;
        }

        public DisplayAvatarV3 setBodyAction(List<AvatarAction> list) {
            this.body_action = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AvatarAction>> getBodyAction() {
            return this.body_action;
        }

        public DisplayAvatarV3 setDependInstructionId(String str) {
            this.depend_instruction_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDependInstructionId() {
            return this.depend_instruction_id;
        }

        public DisplayAvatarV3 setSequenceId(int i) {
            this.sequence_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSequenceId() {
            return this.sequence_id;
        }

        public DisplayAvatarV3 setNoTimeInfo(boolean z) {
            this.no_time_info = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNoTimeInfo() {
            return this.no_time_info;
        }
    }

    @NamespaceName(name = "DriveModeState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class DriveModeState implements ContextPayload {
        private Optional<Boolean> available = Optional.empty();
        private Optional<Boolean> enable = Optional.empty();
        private Optional<Boolean> call = Optional.empty();
        private Optional<WeChatStateInDriveMode> wechat_state = Optional.empty();
        private Optional<Boolean> no_mask = Optional.empty();

        public DriveModeState setAvailable(boolean z) {
            this.available = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAvailable() {
            return this.available;
        }

        public DriveModeState setEnable(boolean z) {
            this.enable = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnable() {
            return this.enable;
        }

        public DriveModeState setCall(boolean z) {
            this.call = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCall() {
            return this.call;
        }

        public DriveModeState setWechatState(WeChatStateInDriveMode weChatStateInDriveMode) {
            this.wechat_state = Optional.ofNullable(weChatStateInDriveMode);
            return this;
        }

        public Optional<WeChatStateInDriveMode> getWechatState() {
            return this.wechat_state;
        }

        public DriveModeState setNoMask(boolean z) {
            this.no_mask = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNoMask() {
            return this.no_mask;
        }
    }

    /* loaded from: classes3.dex */
    public static class ExamInfo {
        private Optional<String> name = Optional.empty();
        private Optional<ExamEventType> event_type = Optional.empty();
        private Optional<ExamName> exam_name = Optional.empty();
        private Optional<Long> start_time = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<String> location = Optional.empty();
        private Optional<Long> reminder = Optional.empty();
        private Optional<Long> last_update_time = Optional.empty();

        public ExamInfo setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public ExamInfo setEventType(ExamEventType examEventType) {
            this.event_type = Optional.ofNullable(examEventType);
            return this;
        }

        public Optional<ExamEventType> getEventType() {
            return this.event_type;
        }

        public ExamInfo setExamName(ExamName examName) {
            this.exam_name = Optional.ofNullable(examName);
            return this;
        }

        public Optional<ExamName> getExamName() {
            return this.exam_name;
        }

        public ExamInfo setStartTime(long j) {
            this.start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getStartTime() {
            return this.start_time;
        }

        public ExamInfo setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public ExamInfo setLocation(String str) {
            this.location = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLocation() {
            return this.location;
        }

        public ExamInfo setReminder(long j) {
            this.reminder = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getReminder() {
            return this.reminder;
        }

        public ExamInfo setLastUpdateTime(long j) {
            this.last_update_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLastUpdateTime() {
            return this.last_update_time;
        }
    }

    @NamespaceName(name = "ExpectPush", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class ExpectPush implements InstructionPayload {
        @Required
        private String source;
        private Optional<Long> timeout_in_millis = Optional.empty();

        public ExpectPush() {
        }

        public ExpectPush(String str) {
            this.source = str;
        }

        @Required
        public ExpectPush setSource(String str) {
            this.source = str;
            return this;
        }

        @Required
        public String getSource() {
            return this.source;
        }

        public ExpectPush setTimeoutInMillis(long j) {
            this.timeout_in_millis = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getTimeoutInMillis() {
            return this.timeout_in_millis;
        }
    }

    @NamespaceName(name = "GenerateSpeak", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class GenerateSpeak implements EventPayload {
        private Optional<String> text = Optional.empty();

        public GenerateSpeak setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }
    }

    /* loaded from: classes3.dex */
    public static class Hint {
        private Optional<String> audio_url = Optional.empty();
        @Required
        private String description;
        @Required
        private HintTag tag;

        public Hint() {
        }

        public Hint(HintTag hintTag, String str) {
            this.tag = hintTag;
            this.description = str;
        }

        @Required
        public Hint setTag(HintTag hintTag) {
            this.tag = hintTag;
            return this;
        }

        @Required
        public HintTag getTag() {
            return this.tag;
        }

        @Required
        public Hint setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        public Hint setAudioUrl(String str) {
            this.audio_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioUrl() {
            return this.audio_url;
        }
    }

    /* loaded from: classes3.dex */
    public static class LockScreenFunctionSwitch {
        private Optional<Boolean> query_alarm = Optional.empty();

        public LockScreenFunctionSwitch setQueryAlarm(boolean z) {
            this.query_alarm = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isQueryAlarm() {
            return this.query_alarm;
        }
    }

    /* loaded from: classes3.dex */
    public static class MIOTState {
        private Optional<MIOTExecuteMode> execute_mode = Optional.empty();

        public MIOTState setExecuteMode(MIOTExecuteMode mIOTExecuteMode) {
            this.execute_mode = Optional.ofNullable(mIOTExecuteMode);
            return this;
        }

        public Optional<MIOTExecuteMode> getExecuteMode() {
            return this.execute_mode;
        }
    }

    /* loaded from: classes3.dex */
    public static class MenstrualData {
        private Optional<Long> period_begin_timestamp = Optional.empty();
        private Optional<Long> period_end_timestamp = Optional.empty();
        private Optional<Long> predict_period_begin_timestamp = Optional.empty();
        private Optional<Long> predict_period_end_timestamp = Optional.empty();
        private Optional<Long> last_update_timestamp = Optional.empty();

        public MenstrualData setPeriodBeginTimestamp(long j) {
            this.period_begin_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPeriodBeginTimestamp() {
            return this.period_begin_timestamp;
        }

        public MenstrualData setPeriodEndTimestamp(long j) {
            this.period_end_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPeriodEndTimestamp() {
            return this.period_end_timestamp;
        }

        public MenstrualData setPredictPeriodBeginTimestamp(long j) {
            this.predict_period_begin_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPredictPeriodBeginTimestamp() {
            return this.predict_period_begin_timestamp;
        }

        public MenstrualData setPredictPeriodEndTimestamp(long j) {
            this.predict_period_end_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPredictPeriodEndTimestamp() {
            return this.predict_period_end_timestamp;
        }

        public MenstrualData setLastUpdateTimestamp(long j) {
            this.last_update_timestamp = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLastUpdateTimestamp() {
            return this.last_update_timestamp;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiPlayAudioRelayDevice {
        @Required
        private String miot_did;
        @Required
        private MiPlayAudioRelayDeviceStatus status;
        @Required
        private String uid;
        private Optional<String> name = Optional.empty();
        private Optional<String> house_id = Optional.empty();
        private Optional<String> room_id = Optional.empty();
        private Optional<String> room_name = Optional.empty();
        private Optional<String> alias = Optional.empty();
        private Optional<String> room_alias = Optional.empty();
        private Optional<String> mac = Optional.empty();

        public MiPlayAudioRelayDevice() {
        }

        public MiPlayAudioRelayDevice(String str, String str2, MiPlayAudioRelayDeviceStatus miPlayAudioRelayDeviceStatus) {
            this.uid = str;
            this.miot_did = str2;
            this.status = miPlayAudioRelayDeviceStatus;
        }

        @Required
        public MiPlayAudioRelayDevice setUid(String str) {
            this.uid = str;
            return this;
        }

        @Required
        public String getUid() {
            return this.uid;
        }

        @Required
        public MiPlayAudioRelayDevice setMiotDid(String str) {
            this.miot_did = str;
            return this;
        }

        @Required
        public String getMiotDid() {
            return this.miot_did;
        }

        @Required
        public MiPlayAudioRelayDevice setStatus(MiPlayAudioRelayDeviceStatus miPlayAudioRelayDeviceStatus) {
            this.status = miPlayAudioRelayDeviceStatus;
            return this;
        }

        @Required
        public MiPlayAudioRelayDeviceStatus getStatus() {
            return this.status;
        }

        public MiPlayAudioRelayDevice setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public MiPlayAudioRelayDevice setHouseId(String str) {
            this.house_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHouseId() {
            return this.house_id;
        }

        public MiPlayAudioRelayDevice setRoomId(String str) {
            this.room_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoomId() {
            return this.room_id;
        }

        public MiPlayAudioRelayDevice setRoomName(String str) {
            this.room_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoomName() {
            return this.room_name;
        }

        public MiPlayAudioRelayDevice setAlias(String str) {
            this.alias = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlias() {
            return this.alias;
        }

        public MiPlayAudioRelayDevice setRoomAlias(String str) {
            this.room_alias = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoomAlias() {
            return this.room_alias;
        }

        public MiPlayAudioRelayDevice setMac(String str) {
            this.mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMac() {
            return this.mac;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiPlayAudioRelayParam {
        @Required
        private List<MiPlayAudioRelayDevice> devices;

        public MiPlayAudioRelayParam() {
        }

        public MiPlayAudioRelayParam(List<MiPlayAudioRelayDevice> list) {
            this.devices = list;
        }

        @Required
        public MiPlayAudioRelayParam setDevices(List<MiPlayAudioRelayDevice> list) {
            this.devices = list;
            return this;
        }

        @Required
        public List<MiPlayAudioRelayDevice> getDevices() {
            return this.devices;
        }
    }

    @NamespaceName(name = "Operate", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class Operate implements InstructionPayload {
        private Optional<List<AppExtra>> app_extras = Optional.empty();
        @Required
        private List<Template.AndroidApp> apps;
        @Required
        private List<Hint> hints;
        @Required
        private String keyword;
        @Required
        private ApplicationOp operation;
        @Required
        private boolean use_local_app;

        public Operate() {
        }

        public Operate(ApplicationOp applicationOp, List<Template.AndroidApp> list, String str, boolean z, List<Hint> list2) {
            this.operation = applicationOp;
            this.apps = list;
            this.keyword = str;
            this.use_local_app = z;
            this.hints = list2;
        }

        @Required
        public Operate setOperation(ApplicationOp applicationOp) {
            this.operation = applicationOp;
            return this;
        }

        @Required
        public ApplicationOp getOperation() {
            return this.operation;
        }

        @Required
        public Operate setApps(List<Template.AndroidApp> list) {
            this.apps = list;
            return this;
        }

        @Required
        public List<Template.AndroidApp> getApps() {
            return this.apps;
        }

        @Required
        public Operate setKeyword(String str) {
            this.keyword = str;
            return this;
        }

        @Required
        public String getKeyword() {
            return this.keyword;
        }

        @Required
        public Operate setUseLocalApp(boolean z) {
            this.use_local_app = z;
            return this;
        }

        @Required
        public boolean isUseLocalApp() {
            return this.use_local_app;
        }

        @Required
        public Operate setHints(List<Hint> list) {
            this.hints = list;
            return this;
        }

        @Required
        public List<Hint> getHints() {
            return this.hints;
        }

        public Operate setAppExtras(List<AppExtra> list) {
            this.app_extras = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AppExtra>> getAppExtras() {
            return this.app_extras;
        }
    }

    @NamespaceName(name = "OperateTvApp", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class OperateTvApp implements InstructionPayload {
        @Required
        private List<String> app_names;
        @Required
        private ObjectNode content;
        @Required
        private ApplicationOp operation;
        private Optional<List<String>> pkg_names = Optional.empty();

        public OperateTvApp() {
        }

        public OperateTvApp(ApplicationOp applicationOp, List<String> list, ObjectNode objectNode) {
            this.operation = applicationOp;
            this.app_names = list;
            this.content = objectNode;
        }

        @Required
        public OperateTvApp setOperation(ApplicationOp applicationOp) {
            this.operation = applicationOp;
            return this;
        }

        @Required
        public ApplicationOp getOperation() {
            return this.operation;
        }

        @Required
        public OperateTvApp setAppNames(List<String> list) {
            this.app_names = list;
            return this;
        }

        @Required
        public List<String> getAppNames() {
            return this.app_names;
        }

        public OperateTvApp setPkgNames(List<String> list) {
            this.pkg_names = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getPkgNames() {
            return this.pkg_names;
        }

        @Required
        public OperateTvApp setContent(ObjectNode objectNode) {
            this.content = objectNode;
            return this;
        }

        @Required
        public ObjectNode getContent() {
            return this.content;
        }
    }

    @NamespaceName(name = "OperateWXInDriveMode", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class OperateWXInDriveMode implements InstructionPayload {
        @Required
        private WeiXinDriveModeOp operate;
        private Optional<String> receiver = Optional.empty();
        private Optional<String> message = Optional.empty();

        public OperateWXInDriveMode() {
        }

        public OperateWXInDriveMode(WeiXinDriveModeOp weiXinDriveModeOp) {
            this.operate = weiXinDriveModeOp;
        }

        @Required
        public OperateWXInDriveMode setOperate(WeiXinDriveModeOp weiXinDriveModeOp) {
            this.operate = weiXinDriveModeOp;
            return this;
        }

        @Required
        public WeiXinDriveModeOp getOperate() {
            return this.operate;
        }

        public OperateWXInDriveMode setReceiver(String str) {
            this.receiver = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReceiver() {
            return this.receiver;
        }

        public OperateWXInDriveMode setMessage(String str) {
            this.message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessage() {
            return this.message;
        }
    }

    @NamespaceName(name = "PersonEvent", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class PersonEvent implements EventPayload {
        private Optional<Template.WikiEvent> wiki_data = Optional.empty();

        public PersonEvent setWikiData(Template.WikiEvent wikiEvent) {
            this.wiki_data = Optional.ofNullable(wikiEvent);
            return this;
        }

        public Optional<Template.WikiEvent> getWikiData() {
            return this.wiki_data;
        }
    }

    @NamespaceName(name = "PersonalState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class PersonalState implements ContextPayload {
        private Optional<MenstrualData> menstrual = Optional.empty();
        private Optional<List<ExamInfo>> exams = Optional.empty();

        public PersonalState setMenstrual(MenstrualData menstrualData) {
            this.menstrual = Optional.ofNullable(menstrualData);
            return this;
        }

        public Optional<MenstrualData> getMenstrual() {
            return this.menstrual;
        }

        public PersonalState setExams(List<ExamInfo> list) {
            this.exams = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ExamInfo>> getExams() {
            return this.exams;
        }
    }

    /* loaded from: classes3.dex */
    public static class PrivacySetting {
        private Optional<Boolean> smart_travel = Optional.empty();
        private Optional<Boolean> menstruation = Optional.empty();
        private Optional<Boolean> personalize = Optional.empty();

        public PrivacySetting setSmartTravel(boolean z) {
            this.smart_travel = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSmartTravel() {
            return this.smart_travel;
        }

        public PrivacySetting setMenstruation(boolean z) {
            this.menstruation = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMenstruation() {
            return this.menstruation;
        }

        public PrivacySetting setPersonalize(boolean z) {
            this.personalize = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPersonalize() {
            return this.personalize;
        }
    }

    @NamespaceName(name = "QueryBonusAssistant", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class QueryBonusAssistant implements InstructionPayload {
        @Required
        private String directive;

        public QueryBonusAssistant() {
        }

        public QueryBonusAssistant(String str) {
            this.directive = str;
        }

        @Required
        public QueryBonusAssistant setDirective(String str) {
            this.directive = str;
            return this;
        }

        @Required
        public String getDirective() {
            return this.directive;
        }
    }

    @NamespaceName(name = "QuickAppState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class QuickAppState implements ContextPayload {
        private Optional<ObjectNode> cookie = Optional.empty();

        public QuickAppState setCookie(ObjectNode objectNode) {
            this.cookie = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getCookie() {
            return this.cookie;
        }
    }

    @NamespaceName(name = "RelayContent", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class RelayContent implements InstructionPayload {
        @Required
        private RelayContentType type;
        private Optional<Common.CommonDeviceCategory> from_device = Optional.empty();
        private Optional<Common.CommonDeviceCategory> to_device = Optional.empty();
        private Optional<MiPlayAudioRelayParam> audio_relay_param = Optional.empty();

        public RelayContent() {
        }

        public RelayContent(RelayContentType relayContentType) {
            this.type = relayContentType;
        }

        @Required
        public RelayContent setType(RelayContentType relayContentType) {
            this.type = relayContentType;
            return this;
        }

        @Required
        public RelayContentType getType() {
            return this.type;
        }

        public RelayContent setFromDevice(Common.CommonDeviceCategory commonDeviceCategory) {
            this.from_device = Optional.ofNullable(commonDeviceCategory);
            return this;
        }

        public Optional<Common.CommonDeviceCategory> getFromDevice() {
            return this.from_device;
        }

        public RelayContent setToDevice(Common.CommonDeviceCategory commonDeviceCategory) {
            this.to_device = Optional.ofNullable(commonDeviceCategory);
            return this;
        }

        public Optional<Common.CommonDeviceCategory> getToDevice() {
            return this.to_device;
        }

        public RelayContent setAudioRelayParam(MiPlayAudioRelayParam miPlayAudioRelayParam) {
            this.audio_relay_param = Optional.ofNullable(miPlayAudioRelayParam);
            return this;
        }

        public Optional<MiPlayAudioRelayParam> getAudioRelayParam() {
            return this.audio_relay_param;
        }
    }

    @NamespaceName(name = "ReportRelayDevices", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class ReportRelayDevices implements EventPayload {
        @Required
        private List<MiPlayAudioRelayDevice> devices;

        public ReportRelayDevices() {
        }

        public ReportRelayDevices(List<MiPlayAudioRelayDevice> list) {
            this.devices = list;
        }

        @Required
        public ReportRelayDevices setDevices(List<MiPlayAudioRelayDevice> list) {
            this.devices = list;
            return this;
        }

        @Required
        public List<MiPlayAudioRelayDevice> getDevices() {
            return this.devices;
        }
    }

    @NamespaceName(name = "ResourceInfo", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class ResourceInfo implements ContextPayload {
        private Optional<Boolean> need_paid_resource = Optional.empty();

        public ResourceInfo setNeedPaidResource(boolean z) {
            this.need_paid_resource = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedPaidResource() {
            return this.need_paid_resource;
        }
    }

    @NamespaceName(name = "SetBonusAssistantProperty", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class SetBonusAssistantProperty implements InstructionPayload {
        @Required
        private String name;
        @Required
        private String value;

        public SetBonusAssistantProperty() {
        }

        public SetBonusAssistantProperty(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        @Required
        public SetBonusAssistantProperty setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public SetBonusAssistantProperty setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }
    }

    @NamespaceName(name = "SetSwitchStatus", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class SetSwitchStatus implements InstructionPayload {
        @Required
        private List<SwitchStatus> status;

        public SetSwitchStatus() {
        }

        public SetSwitchStatus(List<SwitchStatus> list) {
            this.status = list;
        }

        @Required
        public SetSwitchStatus setStatus(List<SwitchStatus> list) {
            this.status = list;
            return this;
        }

        @Required
        public List<SwitchStatus> getStatus() {
            return this.status;
        }
    }

    @NamespaceName(name = "Share", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class Share implements InstructionPayload {
        @Required
        private ObjectNode params;
        @Required
        private ShareStyle style;
        @Required
        private ShareType type;

        public Share() {
        }

        public Share(ShareType shareType, ObjectNode objectNode, ShareStyle shareStyle) {
            this.type = shareType;
            this.params = objectNode;
            this.style = shareStyle;
        }

        @Required
        public Share setType(ShareType shareType) {
            this.type = shareType;
            return this;
        }

        @Required
        public ShareType getType() {
            return this.type;
        }

        @Required
        public Share setParams(ObjectNode objectNode) {
            this.params = objectNode;
            return this;
        }

        @Required
        public ObjectNode getParams() {
            return this.params;
        }

        @Required
        public Share setStyle(ShareStyle shareStyle) {
            this.style = shareStyle;
            return this;
        }

        @Required
        public ShareStyle getStyle() {
            return this.style;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShareAnimationParam {
        @Required
        private String title;
        @Required
        private String url;
        private Optional<String> description = Optional.empty();
        private Optional<String> icon_url = Optional.empty();
        private Optional<String> animation_url = Optional.empty();

        public ShareAnimationParam() {
        }

        public ShareAnimationParam(String str, String str2) {
            this.url = str;
            this.title = str2;
        }

        @Required
        public ShareAnimationParam setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        @Required
        public ShareAnimationParam setTitle(String str) {
            this.title = str;
            return this;
        }

        @Required
        public String getTitle() {
            return this.title;
        }

        public ShareAnimationParam setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public ShareAnimationParam setIconUrl(String str) {
            this.icon_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconUrl() {
            return this.icon_url;
        }

        public ShareAnimationParam setAnimationUrl(String str) {
            this.animation_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAnimationUrl() {
            return this.animation_url;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShareLinkParam {
        @Required
        private String title;
        @Required
        private String url;
        private Optional<String> icon_url = Optional.empty();
        private Optional<String> description = Optional.empty();

        public ShareLinkParam() {
        }

        public ShareLinkParam(String str, String str2) {
            this.url = str;
            this.title = str2;
        }

        @Required
        public ShareLinkParam setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        @Required
        public ShareLinkParam setTitle(String str) {
            this.title = str;
            return this;
        }

        @Required
        public String getTitle() {
            return this.title;
        }

        public ShareLinkParam setIconUrl(String str) {
            this.icon_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconUrl() {
            return this.icon_url;
        }

        public ShareLinkParam setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }
    }

    /* loaded from: classes3.dex */
    public static class SharePicParam {
        @Required
        private String qrcode_url;
        @Required
        private String title;
        private Optional<String> description = Optional.empty();
        private Optional<String> icon_url = Optional.empty();
        private Optional<String> screenshot_placeholder = Optional.empty();

        public SharePicParam() {
        }

        public SharePicParam(String str, String str2) {
            this.qrcode_url = str;
            this.title = str2;
        }

        @Required
        public SharePicParam setQrcodeUrl(String str) {
            this.qrcode_url = str;
            return this;
        }

        @Required
        public String getQrcodeUrl() {
            return this.qrcode_url;
        }

        @Required
        public SharePicParam setTitle(String str) {
            this.title = str;
            return this;
        }

        @Required
        public String getTitle() {
            return this.title;
        }

        public SharePicParam setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public SharePicParam setIconUrl(String str) {
            this.icon_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconUrl() {
            return this.icon_url;
        }

        public SharePicParam setScreenshotPlaceholder(String str) {
            this.screenshot_placeholder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScreenshotPlaceholder() {
            return this.screenshot_placeholder;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShareStyle {
        @Required
        private ShareStyleType type;
        private Optional<String> title = Optional.empty();
        private Optional<String> description = Optional.empty();
        private Optional<String> pic_url = Optional.empty();

        public ShareStyle() {
        }

        public ShareStyle(ShareStyleType shareStyleType) {
            this.type = shareStyleType;
        }

        @Required
        public ShareStyle setType(ShareStyleType shareStyleType) {
            this.type = shareStyleType;
            return this;
        }

        @Required
        public ShareStyleType getType() {
            return this.type;
        }

        public ShareStyle setTitle(String str) {
            this.title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTitle() {
            return this.title;
        }

        public ShareStyle setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public ShareStyle setPicUrl(String str) {
            this.pic_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPicUrl() {
            return this.pic_url;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShareWXMiniProgramParam {
        @Required
        private String id;
        @Required
        private String path;
        @Required
        private String title;
        @Required
        private String url;
        private Optional<String> description = Optional.empty();
        private Optional<String> icon_url = Optional.empty();
        private Optional<WXMiniProgramType> tpe = Optional.empty();

        public ShareWXMiniProgramParam() {
        }

        public ShareWXMiniProgramParam(String str, String str2, String str3, String str4) {
            this.url = str;
            this.title = str2;
            this.id = str3;
            this.path = str4;
        }

        @Required
        public ShareWXMiniProgramParam setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        @Required
        public ShareWXMiniProgramParam setTitle(String str) {
            this.title = str;
            return this;
        }

        @Required
        public String getTitle() {
            return this.title;
        }

        @Required
        public ShareWXMiniProgramParam setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public ShareWXMiniProgramParam setPath(String str) {
            this.path = str;
            return this;
        }

        @Required
        public String getPath() {
            return this.path;
        }

        public ShareWXMiniProgramParam setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public ShareWXMiniProgramParam setIconUrl(String str) {
            this.icon_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconUrl() {
            return this.icon_url;
        }

        public ShareWXMiniProgramParam setTpe(WXMiniProgramType wXMiniProgramType) {
            this.tpe = Optional.ofNullable(wXMiniProgramType);
            return this;
        }

        public Optional<WXMiniProgramType> getTpe() {
            return this.tpe;
        }
    }

    @NamespaceName(name = "ShowBottomCapture", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class ShowBottomCapture implements InstructionPayload {
        private Optional<String> guide = Optional.empty();

        public ShowBottomCapture setGuide(String str) {
            this.guide = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGuide() {
            return this.guide;
        }
    }

    @NamespaceName(name = "SimulateClickState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class SimulateClickState implements ContextPayload {
        private Optional<Boolean> input_method_running = Optional.empty();
        private Optional<Integer> node_id = Optional.empty();
        private Optional<String> wechat_contact_matched = Optional.empty();
        private Optional<String> page_id = Optional.empty();

        public SimulateClickState setInputMethodRunning(boolean z) {
            this.input_method_running = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInputMethodRunning() {
            return this.input_method_running;
        }

        public SimulateClickState setNodeId(int i) {
            this.node_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNodeId() {
            return this.node_id;
        }

        public SimulateClickState setWechatContactMatched(String str) {
            this.wechat_contact_matched = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWechatContactMatched() {
            return this.wechat_contact_matched;
        }

        public SimulateClickState setPageId(String str) {
            this.page_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageId() {
            return this.page_id;
        }
    }

    @NamespaceName(name = "SimulateClickV0", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class SimulateClickV0 implements InstructionPayload {
        @Required
        private ArrayNode nodes;
        @Required
        private List<Integer> rcmd_list;
        private Optional<Boolean> is_final_round = Optional.empty();
        private Optional<ArrayNode> hints = Optional.empty();
        private Optional<String> directive = Optional.empty();
        private Optional<WeixinContactSlot> contact_slots = Optional.empty();
        private Optional<SimulateClickType> type = Optional.empty();

        public SimulateClickV0() {
        }

        public SimulateClickV0(ArrayNode arrayNode, List<Integer> list) {
            this.nodes = arrayNode;
            this.rcmd_list = list;
        }

        @Required
        public SimulateClickV0 setNodes(ArrayNode arrayNode) {
            this.nodes = arrayNode;
            return this;
        }

        @Required
        public ArrayNode getNodes() {
            return this.nodes;
        }

        @Required
        public SimulateClickV0 setRcmdList(List<Integer> list) {
            this.rcmd_list = list;
            return this;
        }

        @Required
        public List<Integer> getRcmdList() {
            return this.rcmd_list;
        }

        public SimulateClickV0 setIsFinalRound(boolean z) {
            this.is_final_round = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFinalRound() {
            return this.is_final_round;
        }

        public SimulateClickV0 setHints(ArrayNode arrayNode) {
            this.hints = Optional.ofNullable(arrayNode);
            return this;
        }

        public Optional<ArrayNode> getHints() {
            return this.hints;
        }

        public SimulateClickV0 setDirective(String str) {
            this.directive = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDirective() {
            return this.directive;
        }

        public SimulateClickV0 setContactSlots(WeixinContactSlot weixinContactSlot) {
            this.contact_slots = Optional.ofNullable(weixinContactSlot);
            return this;
        }

        public Optional<WeixinContactSlot> getContactSlots() {
            return this.contact_slots;
        }

        public SimulateClickV0 setType(SimulateClickType simulateClickType) {
            this.type = Optional.ofNullable(simulateClickType);
            return this;
        }

        public Optional<SimulateClickType> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "State", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class State implements ContextPayload {
        private Optional<Boolean> screening_on = Optional.empty();
        @Deprecated
        private Optional<Boolean> parental_control_mode = Optional.empty();
        private Optional<List<ToneInfo>> support_tones = Optional.empty();
        private Optional<String> current_tone_id = Optional.empty();
        private Optional<String> current_music_player_pkg = Optional.empty();
        private Optional<String> current_music_player_version = Optional.empty();
        private Optional<Boolean> enable_screening = Optional.empty();
        private Optional<Boolean> use_trip_app_data = Optional.empty();
        private Optional<Boolean> shutoff_timer = Optional.empty();
        private Optional<String> current_tone_vendor_id = Optional.empty();
        private Optional<Boolean> showing_bottom_capture = Optional.empty();
        private Optional<List<SwitchStatus>> switch_status = Optional.empty();
        private Optional<AppState> app_state = Optional.empty();
        private Optional<MIOTState> miot_state = Optional.empty();
        private Optional<UserState> user_state = Optional.empty();
        private Optional<CpState> cp_state = Optional.empty();
        private Optional<PrivacySetting> privacy_setting = Optional.empty();
        private Optional<LockScreenFunctionSwitch> lock_screen_switch = Optional.empty();
        private Optional<Boolean> is_avatar_open = Optional.empty();
        private Optional<WakeupSetting> wakeup = Optional.empty();
        private Optional<Boolean> smart_alarm_opened = Optional.empty();
        private Optional<ApplicationStatePayload> next_level_state = Optional.empty();

        public State setScreeningOn(boolean z) {
            this.screening_on = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isScreeningOn() {
            return this.screening_on;
        }

        @Deprecated
        public State setParentalControlMode(boolean z) {
            this.parental_control_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        @Deprecated
        public Optional<Boolean> isParentalControlMode() {
            return this.parental_control_mode;
        }

        public State setSupportTones(List<ToneInfo> list) {
            this.support_tones = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ToneInfo>> getSupportTones() {
            return this.support_tones;
        }

        public State setCurrentToneId(String str) {
            this.current_tone_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrentToneId() {
            return this.current_tone_id;
        }

        public State setCurrentMusicPlayerPkg(String str) {
            this.current_music_player_pkg = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrentMusicPlayerPkg() {
            return this.current_music_player_pkg;
        }

        public State setCurrentMusicPlayerVersion(String str) {
            this.current_music_player_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrentMusicPlayerVersion() {
            return this.current_music_player_version;
        }

        public State setEnableScreening(boolean z) {
            this.enable_screening = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnableScreening() {
            return this.enable_screening;
        }

        public State setUseTripAppData(boolean z) {
            this.use_trip_app_data = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isUseTripAppData() {
            return this.use_trip_app_data;
        }

        public State setShutoffTimer(boolean z) {
            this.shutoff_timer = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShutoffTimer() {
            return this.shutoff_timer;
        }

        public State setCurrentToneVendorId(String str) {
            this.current_tone_vendor_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrentToneVendorId() {
            return this.current_tone_vendor_id;
        }

        public State setShowingBottomCapture(boolean z) {
            this.showing_bottom_capture = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShowingBottomCapture() {
            return this.showing_bottom_capture;
        }

        public State setSwitchStatus(List<SwitchStatus> list) {
            this.switch_status = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SwitchStatus>> getSwitchStatus() {
            return this.switch_status;
        }

        public State setAppState(AppState appState) {
            this.app_state = Optional.ofNullable(appState);
            return this;
        }

        public Optional<AppState> getAppState() {
            return this.app_state;
        }

        public State setMiotState(MIOTState mIOTState) {
            this.miot_state = Optional.ofNullable(mIOTState);
            return this;
        }

        public Optional<MIOTState> getMiotState() {
            return this.miot_state;
        }

        public State setUserState(UserState userState) {
            this.user_state = Optional.ofNullable(userState);
            return this;
        }

        public Optional<UserState> getUserState() {
            return this.user_state;
        }

        public State setCpState(CpState cpState) {
            this.cp_state = Optional.ofNullable(cpState);
            return this;
        }

        public Optional<CpState> getCpState() {
            return this.cp_state;
        }

        public State setPrivacySetting(PrivacySetting privacySetting) {
            this.privacy_setting = Optional.ofNullable(privacySetting);
            return this;
        }

        public Optional<PrivacySetting> getPrivacySetting() {
            return this.privacy_setting;
        }

        public State setLockScreenSwitch(LockScreenFunctionSwitch lockScreenFunctionSwitch) {
            this.lock_screen_switch = Optional.ofNullable(lockScreenFunctionSwitch);
            return this;
        }

        public Optional<LockScreenFunctionSwitch> getLockScreenSwitch() {
            return this.lock_screen_switch;
        }

        public State setIsAvatarOpen(boolean z) {
            this.is_avatar_open = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAvatarOpen() {
            return this.is_avatar_open;
        }

        public State setWakeup(WakeupSetting wakeupSetting) {
            this.wakeup = Optional.ofNullable(wakeupSetting);
            return this;
        }

        public Optional<WakeupSetting> getWakeup() {
            return this.wakeup;
        }

        public State setSmartAlarmOpened(boolean z) {
            this.smart_alarm_opened = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSmartAlarmOpened() {
            return this.smart_alarm_opened;
        }

        public State setNextLevelState(ApplicationStatePayload applicationStatePayload) {
            this.next_level_state = Optional.ofNullable(applicationStatePayload);
            return this;
        }

        public Optional<ApplicationStatePayload> getNextLevelState() {
            return this.next_level_state;
        }
    }

    /* loaded from: classes3.dex */
    public static class SwitchStatus {
        @Required
        private boolean enabled;
        @Required
        private SwitchFeature name;

        public SwitchStatus() {
        }

        public SwitchStatus(SwitchFeature switchFeature, boolean z) {
            this.name = switchFeature;
            this.enabled = z;
        }

        @Required
        public SwitchStatus setName(SwitchFeature switchFeature) {
            this.name = switchFeature;
            return this;
        }

        @Required
        public SwitchFeature getName() {
            return this.name;
        }

        @Required
        public SwitchStatus setEnabled(boolean z) {
            this.enabled = z;
            return this;
        }

        @Required
        public boolean isEnabled() {
            return this.enabled;
        }
    }

    @NamespaceName(name = "SwitchTimeFormat", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class SwitchTimeFormat implements InstructionPayload {
        @Required
        private Common.TimeFormat format;

        public SwitchTimeFormat() {
        }

        public SwitchTimeFormat(Common.TimeFormat timeFormat) {
            this.format = timeFormat;
        }

        @Required
        public SwitchTimeFormat setFormat(Common.TimeFormat timeFormat) {
            this.format = timeFormat;
            return this;
        }

        @Required
        public Common.TimeFormat getFormat() {
            return this.format;
        }
    }

    @NamespaceName(name = "TaskState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class TaskState implements ContextPayload {
        private Optional<String> foreground = Optional.empty();
        private Optional<List<String>> all = Optional.empty();

        public TaskState setForeground(String str) {
            this.foreground = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForeground() {
            return this.foreground;
        }

        public TaskState setAll(List<String> list) {
            this.all = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getAll() {
            return this.all;
        }
    }

    /* loaded from: classes3.dex */
    public static class ToneInfo {
        @Required
        private String id;
        @Required
        private ToneName name;
        @Required
        private ToneType type;

        public ToneInfo() {
        }

        public ToneInfo(String str, ToneType toneType, ToneName toneName) {
            this.id = str;
            this.type = toneType;
            this.name = toneName;
        }

        @Required
        public ToneInfo setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public ToneInfo setType(ToneType toneType) {
            this.type = toneType;
            return this;
        }

        @Required
        public ToneType getType() {
            return this.type;
        }

        @Required
        public ToneInfo setName(ToneName toneName) {
            this.name = toneName;
            return this;
        }

        @Required
        public ToneName getName() {
            return this.name;
        }
    }

    @NamespaceName(name = "Translation", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class Translation implements EventPayload {
        @Required
        private String src_lang;
        @Required
        private List<String> support_lang;
        @Required
        private String target_lang;
        @Required
        private String text;
        private Optional<TranslationType> type = Optional.empty();
        private Optional<String> backup = Optional.empty();

        public Translation() {
        }

        public Translation(String str, String str2, String str3, List<String> list) {
            this.src_lang = str;
            this.target_lang = str2;
            this.text = str3;
            this.support_lang = list;
        }

        @Required
        public Translation setSrcLang(String str) {
            this.src_lang = str;
            return this;
        }

        @Required
        public String getSrcLang() {
            return this.src_lang;
        }

        @Required
        public Translation setTargetLang(String str) {
            this.target_lang = str;
            return this;
        }

        @Required
        public String getTargetLang() {
            return this.target_lang;
        }

        @Required
        public Translation setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public Translation setSupportLang(List<String> list) {
            this.support_lang = list;
            return this;
        }

        @Required
        public List<String> getSupportLang() {
            return this.support_lang;
        }

        public Translation setType(TranslationType translationType) {
            this.type = Optional.ofNullable(translationType);
            return this;
        }

        public Optional<TranslationType> getType() {
            return this.type;
        }

        public Translation setBackup(String str) {
            this.backup = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBackup() {
            return this.backup;
        }
    }

    /* loaded from: classes3.dex */
    public static class UserState {
        private Optional<List<UserVipState>> user_vip_states = Optional.empty();

        public UserState setUserVipStates(List<UserVipState> list) {
            this.user_vip_states = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<UserVipState>> getUserVipStates() {
            return this.user_vip_states;
        }
    }

    /* loaded from: classes3.dex */
    public static class UserVipState {
        private Optional<UserVipType> vip_type = Optional.empty();
        private Optional<Boolean> is_valid = Optional.empty();

        public UserVipState setVipType(UserVipType userVipType) {
            this.vip_type = Optional.ofNullable(userVipType);
            return this;
        }

        public Optional<UserVipType> getVipType() {
            return this.vip_type;
        }

        public UserVipState setIsValid(boolean z) {
            this.is_valid = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isValid() {
            return this.is_valid;
        }
    }

    @NamespaceName(name = "Vibrate", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class Vibrate implements InstructionPayload {
        @Required
        private long duration_in_ms;

        public Vibrate() {
        }

        public Vibrate(long j) {
            this.duration_in_ms = j;
        }

        @Required
        public Vibrate setDurationInMs(long j) {
            this.duration_in_ms = j;
            return this;
        }

        @Required
        public long getDurationInMs() {
            return this.duration_in_ms;
        }
    }

    @NamespaceName(name = "VoiceWakeupState", namespace = AIApiConstants.Application.NAME)
    /* loaded from: classes3.dex */
    public static class VoiceWakeupState implements ContextPayload {
        private Optional<VoiceWakeupType> type = Optional.empty();
        private Optional<Boolean> is_open = Optional.empty();
        private Optional<Long> last_close_time = Optional.empty();
        private Optional<Boolean> is_voiceprint_enter = Optional.empty();
        private Optional<Boolean> is_long_press_power_open = Optional.empty();

        public VoiceWakeupState setType(VoiceWakeupType voiceWakeupType) {
            this.type = Optional.ofNullable(voiceWakeupType);
            return this;
        }

        public Optional<VoiceWakeupType> getType() {
            return this.type;
        }

        public VoiceWakeupState setIsOpen(boolean z) {
            this.is_open = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isOpen() {
            return this.is_open;
        }

        public VoiceWakeupState setLastCloseTime(long j) {
            this.last_close_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLastCloseTime() {
            return this.last_close_time;
        }

        public VoiceWakeupState setIsVoiceprintEnter(boolean z) {
            this.is_voiceprint_enter = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVoiceprintEnter() {
            return this.is_voiceprint_enter;
        }

        public VoiceWakeupState setIsLongPressPowerOpen(boolean z) {
            this.is_long_press_power_open = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLongPressPowerOpen() {
            return this.is_long_press_power_open;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeupSetting {
        private Optional<Boolean> is_request_sound_off = Optional.empty();

        public WakeupSetting setIsRequestSoundOff(boolean z) {
            this.is_request_sound_off = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRequestSoundOff() {
            return this.is_request_sound_off;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeChatStateInDriveMode {
        private Optional<Boolean> prompt_for_blocked_message_sender = Optional.empty();
        private Optional<Boolean> need_confirm_education = Optional.empty();
        private Optional<Boolean> need_reply_education = Optional.empty();
        private Optional<Boolean> reinput_button_clicked = Optional.empty();
        private Optional<Boolean> is_passive_mode = Optional.empty();
        private Optional<Boolean> need_finish_education = Optional.empty();

        public WeChatStateInDriveMode setPromptForBlockedMessageSender(boolean z) {
            this.prompt_for_blocked_message_sender = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPromptForBlockedMessageSender() {
            return this.prompt_for_blocked_message_sender;
        }

        public WeChatStateInDriveMode setNeedConfirmEducation(boolean z) {
            this.need_confirm_education = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedConfirmEducation() {
            return this.need_confirm_education;
        }

        public WeChatStateInDriveMode setNeedReplyEducation(boolean z) {
            this.need_reply_education = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedReplyEducation() {
            return this.need_reply_education;
        }

        public WeChatStateInDriveMode setReinputButtonClicked(boolean z) {
            this.reinput_button_clicked = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isReinputButtonClicked() {
            return this.reinput_button_clicked;
        }

        public WeChatStateInDriveMode setIsPassiveMode(boolean z) {
            this.is_passive_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPassiveMode() {
            return this.is_passive_mode;
        }

        public WeChatStateInDriveMode setNeedFinishEducation(boolean z) {
            this.need_finish_education = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedFinishEducation() {
            return this.need_finish_education;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeixinContactSlot {
        @Required
        private String name;
        @Required
        private String raw_name;

        public WeixinContactSlot() {
        }

        public WeixinContactSlot(String str, String str2) {
            this.name = str;
            this.raw_name = str2;
        }

        @Required
        public WeixinContactSlot setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public WeixinContactSlot setRawName(String str) {
            this.raw_name = str;
            return this;
        }

        @Required
        public String getRawName() {
            return this.raw_name;
        }
    }
}
