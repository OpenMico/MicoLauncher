package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class TrackLogV3 {

    /* loaded from: classes3.dex */
    public enum ButtonContent {
        OPEN(0),
        NOT_YET(1);
        
        private int id;

        ButtonContent(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DataType {
        UNKNOWN(-1),
        VOICE_ASSISTANT(0),
        TV(1),
        WAKE_UP(2),
        NEARBY_WAKE_UP(3),
        LITE(4),
        NO_SCREEN_BOX(5),
        PERF(6),
        SCREEN_BOX(7),
        H5(8),
        WATCH(9),
        OPENPLATFORM(10),
        MINIPROGRAM(11);
        
        private int id;

        DataType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum H5EventType {
        EXPOSE(0),
        CLICK(1),
        ENTER(2),
        DELETE(3),
        EXIT(4);
        
        private int id;

        H5EventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteEventContextAppState {
        FOREGROUND(0),
        BACKGROUND(1);
        
        private int id;

        LiteEventContextAppState(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteEventContextScreenState {
        ON(0),
        OFF(1);
        
        private int id;

        LiteEventContextScreenState(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteEventType {
        EXPOSE(0),
        CLICK(1),
        ADD(2),
        EXECUTE(3),
        DELETE(4),
        EXIT(5),
        PLAY(6),
        PAUSE(7),
        DURATION(8),
        STATE(9),
        SCAN(10),
        CONNECT(11),
        RESULT(12),
        ENTER(13),
        AWAKE(14),
        LAUNCH(15),
        DISCONNECT(16),
        ERROR(17);
        
        private int id;

        LiteEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteExtendJsonInputMethod {
        VOICEBTN(0),
        KEYBOARD(1);
        
        private int id;

        LiteExtendJsonInputMethod(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteExtendJsonMode {
        SMALL(0),
        FULLSCREEN(1);
        
        private int id;

        LiteExtendJsonMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteExtendJsonStopMicMethod {
        VAD(0),
        VOICE_BTN(1),
        TIMEOUT(2);
        
        private int id;

        LiteExtendJsonStopMicMethod(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LiteExtendJsonSuggestionType {
        ENV(0),
        CLASS_SCHEDULE(1);
        
        private int id;

        LiteExtendJsonSuggestionType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MiniProgramEventType {
        PLAY(0),
        STATE(1);
        
        private int id;

        MiniProgramEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum NoScreenBoxEventType {
        PLAY(0),
        LOAD_ERROR(1),
        LOAD_TIME(2),
        LOAD_MORE(3),
        DURATION(4),
        FINISH(5),
        SWITCH(6);
        
        private int id;

        NoScreenBoxEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum OpenplatformEventType {
        PLAY(0),
        STATE(1),
        EXPOSE(2),
        CLICK(3),
        LAUNCH(4),
        ENTER(5),
        CONNECT(6),
        DISCONNECT(7),
        EXIT(8),
        DURATION(9);
        
        private int id;

        OpenplatformEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PerfEventType {
        DURATION(0),
        STATE(1),
        LOAD_ERROR(2),
        TIMESTAMP_GROUP(3),
        EXPOSE(4),
        CLICK(5),
        RATE(6);
        
        private int id;

        PerfEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ProductType {
        YEAR_CARD(0),
        SEASONLY_CARD(1),
        MONTHLY_CARD(2),
        HALF_YEAR_CARD(3),
        SEASON_CARD(4),
        MONTH_CARD(5);
        
        private int id;

        ProductType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenBoxEventType {
        EXPOSE(0),
        CLICK(1),
        FINISH(2),
        PLAY(3),
        FAVORITE(4),
        ENTER(5),
        EXIT(6),
        CANCEL_FAVORITE(7);
        
        private int id;

        ScreenBoxEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenBoxExtendType {
        SWITCH(0),
        INTENT(1);
        
        private int id;

        ScreenBoxExtendType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenBoxPlayEventType {
        FINISH(0),
        PAUSE(1),
        PLAY(2),
        FAST_FORWARD(3),
        FAST_REWIND(4),
        NEXT(5),
        PREV(6),
        PROGRESS_BAR(7);
        
        private int id;

        ScreenBoxPlayEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenBoxPlayFinishType {
        AUTO_SWITCH(0),
        MAN_SWITCH(1);
        
        private int id;

        ScreenBoxPlayFinishType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenBoxStatus {
        UNKNOWN(-1),
        SUCCEED(0),
        FAILED(1);
        
        private int id;

        ScreenBoxStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TrackCardType {
        FULL_SCREEN(0),
        SMALL(1),
        TOAST(2);
        
        private int id;

        TrackCardType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TrackFloatingBallWakeupMethod {
        SINGLE_CLICK(0),
        DOUBLE_CLICK(1),
        LONG_PRESS(2);
        
        private int id;

        TrackFloatingBallWakeupMethod(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TrackNetworkStatus {
        WIFI(0),
        NONE(1);
        
        private int id;

        TrackNetworkStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TrackPageType {
        CHILDREN_PAGE(0);
        
        private int id;

        TrackPageType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TrackUserMode {
        KID(0),
        NORMAL(1);
        
        private int id;

        TrackUserMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvButton {
        PLAY(0),
        SELECT(1),
        FAVORITE(2),
        UNFAVORITE(3);
        
        private int id;

        TvButton(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvEventType {
        EXPOSE(0),
        CLICK(1),
        FINISH(2),
        PLAY(3),
        FAVORITE(4),
        DURATION(5),
        EXIT(6),
        STATE(7),
        RESULT(8),
        LAUNCH(9);
        
        private int id;

        TvEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvExitType {
        CLICK_RESULT(0),
        OVERTIME(1),
        EXIT(2);
        
        private int id;

        TvExitType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvGuidanceType {
        CONTEXT(0),
        GUIDANCE(1);
        
        private int id;

        TvGuidanceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvLanguageType {
        ZH_CN(0),
        ZH_TW(1),
        EN_US(2);
        
        private int id;

        TvLanguageType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvNetworkStatus {
        WIFI(0),
        NONE(1),
        ETHERNET(2);
        
        private int id;

        TvNetworkStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TvUserMode {
        NORMAL(0),
        KID(1);
        
        private int id;

        TvUserMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VaEventType {
        EXPOSE(0),
        CLICK(1),
        ADD(2),
        EXECUTE(3),
        DELETE(4),
        EXIT(5),
        PLAY(6),
        PAUSE(7),
        DURATION(8),
        STATE(9),
        AWAKE(10),
        RESULT(11),
        REFRESH(12),
        CANCEL(13),
        RECOGNIZE(14),
        RESPONSE(15),
        FINISH(16),
        BACKGROUND(17),
        SPEAK(18),
        FAVOR(19),
        OPEN_MIC(20),
        ENTER(21),
        START(22),
        LAUNCH(23),
        SLIDE(24),
        SCAN(25),
        CONNECT(26),
        DISCONNECT(27);
        
        private int id;

        VaEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum WakeUpEventType {
        TV_WAKEUP_NEARFIELD_COUNT(0),
        TV_WAKEUP_REAL_FARFIELD_COUNT(1),
        TV_WAKEUP_SUSPECT_FARFIELD_COUNT(2),
        TV_SPEECH_ASR_RESULT(3),
        TV_SPEECH_ASR_FAILURE(4),
        TV_SPEECH_NLP_RESULT(5),
        TV_SPEECH_SDK_ERR_COUNT(6),
        PH_WAKEUP_REAL_COUNT(30),
        PH_WAKEUP_SUSPECT_COUNT(31),
        PH_SPEECH_STATE_COUNT(32),
        PH_WAKEUP_FUNC(33),
        sound_wakeup_real_count(60),
        sound_wakeup_suspect_count(61),
        sound_speech_asr_no_query_count(62),
        sound_speech_no_response_count(63),
        sound_speech_invalid_domain_action_count(64),
        sound_speech_success_count(65),
        sound_speech_sdk_err_count(66);
        
        private int id;

        WakeUpEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum WatchEventType {
        EXPOSE(0),
        CLICK(1),
        EXIT(2),
        STATE(3),
        ENTER(4),
        DURATION(5),
        AWAKE(6),
        SLIDE(7);
        
        private int id;

        WatchEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioItemLog {
        @Required
        private String eid;
        @Required
        private String refer;

        public AudioItemLog() {
        }

        public AudioItemLog(String str, String str2) {
            this.eid = str;
            this.refer = str2;
        }

        @Required
        public AudioItemLog setEid(String str) {
            this.eid = str;
            return this;
        }

        @Required
        public String getEid() {
            return this.eid;
        }

        @Required
        public AudioItemLog setRefer(String str) {
            this.refer = str;
            return this;
        }

        @Required
        public String getRefer() {
            return this.refer;
        }
    }

    /* loaded from: classes3.dex */
    public static class H5EventContext {
        private Optional<String> device_type = Optional.empty();
        private Optional<String> position = Optional.empty();
        private Optional<String> cur_page = Optional.empty();
        private Optional<String> ua = Optional.empty();
        private Optional<String> cid = Optional.empty();

        public H5EventContext setDeviceType(String str) {
            this.device_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceType() {
            return this.device_type;
        }

        public H5EventContext setPosition(String str) {
            this.position = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPosition() {
            return this.position;
        }

        public H5EventContext setCurPage(String str) {
            this.cur_page = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurPage() {
            return this.cur_page;
        }

        public H5EventContext setUa(String str) {
            this.ua = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUa() {
            return this.ua;
        }

        public H5EventContext setCid(String str) {
            this.cid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCid() {
            return this.cid;
        }
    }

    /* loaded from: classes3.dex */
    public static class H5EventParams {
        @Required
        private H5EventContext event_context;
        @Required
        private String event_data_type;
        private Optional<H5ExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public H5EventParams() {
        }

        public H5EventParams(String str, H5EventContext h5EventContext, String str2, long j) {
            this.widget = str;
            this.event_context = h5EventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public H5EventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public H5EventParams setEventContext(H5EventContext h5EventContext) {
            this.event_context = h5EventContext;
            return this;
        }

        @Required
        public H5EventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public H5EventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public H5EventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public H5EventParams setExtendJson(H5ExtendJson h5ExtendJson) {
            this.extend_json = Optional.ofNullable(h5ExtendJson);
            return this;
        }

        public Optional<H5ExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class H5ExtendJson {
        private Optional<String> from = Optional.empty();
        private Optional<String> open_from = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> template = Optional.empty();
        private Optional<String> activity_id = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> result_list = Optional.empty();
        private Optional<String> share_type = Optional.empty();
        private Optional<String> status = Optional.empty();
        private Optional<String> domain_id = Optional.empty();
        private Optional<String> remind_id = Optional.empty();
        private Optional<String> app_name = Optional.empty();
        private Optional<String> click_position = Optional.empty();
        private Optional<String> sec_status = Optional.empty();
        private Optional<Boolean> is_remind = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> btn_word = Optional.empty();
        private Optional<String> first_status = Optional.empty();
        private Optional<Integer> element_id = Optional.empty();
        private Optional<String> click_type = Optional.empty();

        public H5ExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public H5ExtendJson setOpenFrom(String str) {
            this.open_from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOpenFrom() {
            return this.open_from;
        }

        public H5ExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public H5ExtendJson setTemplate(String str) {
            this.template = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTemplate() {
            return this.template;
        }

        public H5ExtendJson setActivityId(String str) {
            this.activity_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getActivityId() {
            return this.activity_id;
        }

        public H5ExtendJson setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public H5ExtendJson setResultList(String str) {
            this.result_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultList() {
            return this.result_list;
        }

        public H5ExtendJson setShareType(String str) {
            this.share_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getShareType() {
            return this.share_type;
        }

        public H5ExtendJson setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public H5ExtendJson setDomainId(String str) {
            this.domain_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomainId() {
            return this.domain_id;
        }

        public H5ExtendJson setRemindId(String str) {
            this.remind_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRemindId() {
            return this.remind_id;
        }

        public H5ExtendJson setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public H5ExtendJson setClickPosition(String str) {
            this.click_position = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickPosition() {
            return this.click_position;
        }

        public H5ExtendJson setSecStatus(String str) {
            this.sec_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSecStatus() {
            return this.sec_status;
        }

        public H5ExtendJson setIsRemind(boolean z) {
            this.is_remind = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isRemind() {
            return this.is_remind;
        }

        public H5ExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public H5ExtendJson setBtnWord(String str) {
            this.btn_word = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtnWord() {
            return this.btn_word;
        }

        public H5ExtendJson setFirstStatus(String str) {
            this.first_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFirstStatus() {
            return this.first_status;
        }

        public H5ExtendJson setElementId(int i) {
            this.element_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElementId() {
            return this.element_id;
        }

        public H5ExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class H5TrackLog {
        @Required
        private H5EventParams event_params;
        @Required
        private H5EventType event_type;

        public H5TrackLog() {
        }

        public H5TrackLog(H5EventType h5EventType, H5EventParams h5EventParams) {
            this.event_type = h5EventType;
            this.event_params = h5EventParams;
        }

        @Required
        public H5TrackLog setEventType(H5EventType h5EventType) {
            this.event_type = h5EventType;
            return this;
        }

        @Required
        public H5EventType getEventType() {
            return this.event_type;
        }

        @Required
        public H5TrackLog setEventParams(H5EventParams h5EventParams) {
            this.event_params = h5EventParams;
            return this;
        }

        @Required
        public H5EventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class LiteEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        private Optional<String> bt_device_mac_address = Optional.empty();
        private Optional<String> bt_device_name = Optional.empty();
        private Optional<String> bt_vid = Optional.empty();
        private Optional<String> bt_pid = Optional.empty();
        private Optional<String> uid = Optional.empty();
        private Optional<String> app_version = Optional.empty();
        private Optional<String> network_status = Optional.empty();
        private Optional<String> device_model = Optional.empty();
        private Optional<String> system_version = Optional.empty();
        private Optional<String> download_channel = Optional.empty();
        private Optional<String> bt_device_version = Optional.empty();
        private Optional<LiteEventContextAppState> app_state = Optional.empty();
        private Optional<LiteEventContextScreenState> screen_state = Optional.empty();

        public LiteEventContext() {
        }

        public LiteEventContext(String str, String str2) {
            this.device_id = str;
            this.app_id = str2;
        }

        @Required
        public LiteEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public LiteEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        public LiteEventContext setBtDeviceMacAddress(String str) {
            this.bt_device_mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceMacAddress() {
            return this.bt_device_mac_address;
        }

        public LiteEventContext setBtDeviceName(String str) {
            this.bt_device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceName() {
            return this.bt_device_name;
        }

        public LiteEventContext setBtVid(String str) {
            this.bt_vid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtVid() {
            return this.bt_vid;
        }

        public LiteEventContext setBtPid(String str) {
            this.bt_pid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtPid() {
            return this.bt_pid;
        }

        public LiteEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public LiteEventContext setAppVersion(String str) {
            this.app_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppVersion() {
            return this.app_version;
        }

        public LiteEventContext setNetworkStatus(String str) {
            this.network_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkStatus() {
            return this.network_status;
        }

        public LiteEventContext setDeviceModel(String str) {
            this.device_model = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModel() {
            return this.device_model;
        }

        public LiteEventContext setSystemVersion(String str) {
            this.system_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSystemVersion() {
            return this.system_version;
        }

        public LiteEventContext setDownloadChannel(String str) {
            this.download_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDownloadChannel() {
            return this.download_channel;
        }

        public LiteEventContext setBtDeviceVersion(String str) {
            this.bt_device_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceVersion() {
            return this.bt_device_version;
        }

        public LiteEventContext setAppState(LiteEventContextAppState liteEventContextAppState) {
            this.app_state = Optional.ofNullable(liteEventContextAppState);
            return this;
        }

        public Optional<LiteEventContextAppState> getAppState() {
            return this.app_state;
        }

        public LiteEventContext setScreenState(LiteEventContextScreenState liteEventContextScreenState) {
            this.screen_state = Optional.ofNullable(liteEventContextScreenState);
            return this;
        }

        public Optional<LiteEventContextScreenState> getScreenState() {
            return this.screen_state;
        }
    }

    /* loaded from: classes3.dex */
    public static class LiteEventParams {
        @Required
        private LiteEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<LiteExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public LiteEventParams() {
        }

        public LiteEventParams(String str, String str2, long j, LiteEventContext liteEventContext) {
            this.widget = str;
            this.event_data_type = str2;
            this.timestamp = j;
            this.event_context = liteEventContext;
        }

        @Required
        public LiteEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public LiteEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public LiteEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        @Required
        public LiteEventParams setEventContext(LiteEventContext liteEventContext) {
            this.event_context = liteEventContext;
            return this;
        }

        @Required
        public LiteEventContext getEventContext() {
            return this.event_context;
        }

        public LiteEventParams setExtendJson(LiteExtendJson liteExtendJson) {
            this.extend_json = Optional.ofNullable(liteExtendJson);
            return this;
        }

        public Optional<LiteExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class LiteExtendJson {
        private Optional<Integer> status = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> extend_type = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> event_id = Optional.empty();
        private Optional<String> vidpid = Optional.empty();
        private Optional<String> model_id = Optional.empty();
        private Optional<String> template = Optional.empty();
        private Optional<String> results = Optional.empty();
        private Optional<String> click_type = Optional.empty();
        private Optional<String> scan_mode = Optional.empty();
        private Optional<Integer> item_position = Optional.empty();
        private Optional<String> scan_id = Optional.empty();
        private Optional<String> mac_address = Optional.empty();
        private Optional<String> connect_mode = Optional.empty();
        private Optional<String> appauto_connect_subtype = Optional.empty();
        private Optional<String> connect_id = Optional.empty();
        private Optional<String> status_code = Optional.empty();
        private Optional<Integer> is_mandatory_upgrade = Optional.empty();
        private Optional<Integer> appauto_subtype = Optional.empty();
        private Optional<String> bt_device_version = Optional.empty();
        private Optional<Boolean> is_guided = Optional.empty();
        private Optional<String> error_msg = Optional.empty();
        private Optional<String> function = Optional.empty();
        private Optional<String> func = Optional.empty();
        private Optional<String> msg_id = Optional.empty();
        private Optional<String> wakeup_channel = Optional.empty();
        private Optional<LiteExtendJsonMode> mode = Optional.empty();
        private Optional<String> origin_lang = Optional.empty();
        private Optional<String> target_lang = Optional.empty();
        private Optional<LiteExtendJsonInputMethod> input_method = Optional.empty();
        private Optional<Long> open_mic_time_length = Optional.empty();
        private Optional<LiteExtendJsonStopMicMethod> stop_mic_method = Optional.empty();
        private Optional<LiteExtendJsonSuggestionType> suggestion_type = Optional.empty();
        private Optional<List<String>> func_list = Optional.empty();
        private Optional<TrackCardType> card_type = Optional.empty();
        private Optional<String> bt_service_api_name = Optional.empty();
        private Optional<List<String>> bt_service_params = Optional.empty();
        private Optional<Boolean> is_floating_ball_switch_open = Optional.empty();
        private Optional<TrackFloatingBallWakeupMethod> floating_ball_wakeup_method = Optional.empty();
        private Optional<Boolean> floating_ball_invisible_swtich = Optional.empty();
        private Optional<Double> floating_ball_size = Optional.empty();

        public LiteExtendJson setStatus(int i) {
            this.status = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatus() {
            return this.status;
        }

        public LiteExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public LiteExtendJson setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public LiteExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public LiteExtendJson setExtendType(String str) {
            this.extend_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExtendType() {
            return this.extend_type;
        }

        public LiteExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public LiteExtendJson setEventId(String str) {
            this.event_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEventId() {
            return this.event_id;
        }

        public LiteExtendJson setVidpid(String str) {
            this.vidpid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVidpid() {
            return this.vidpid;
        }

        public LiteExtendJson setModelId(String str) {
            this.model_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModelId() {
            return this.model_id;
        }

        public LiteExtendJson setTemplate(String str) {
            this.template = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTemplate() {
            return this.template;
        }

        public LiteExtendJson setResults(String str) {
            this.results = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResults() {
            return this.results;
        }

        public LiteExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }

        public LiteExtendJson setScanMode(String str) {
            this.scan_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScanMode() {
            return this.scan_mode;
        }

        public LiteExtendJson setItemPosition(int i) {
            this.item_position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getItemPosition() {
            return this.item_position;
        }

        public LiteExtendJson setScanId(String str) {
            this.scan_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScanId() {
            return this.scan_id;
        }

        public LiteExtendJson setMacAddress(String str) {
            this.mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMacAddress() {
            return this.mac_address;
        }

        public LiteExtendJson setConnectMode(String str) {
            this.connect_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getConnectMode() {
            return this.connect_mode;
        }

        public LiteExtendJson setAppautoConnectSubtype(String str) {
            this.appauto_connect_subtype = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppautoConnectSubtype() {
            return this.appauto_connect_subtype;
        }

        public LiteExtendJson setConnectId(String str) {
            this.connect_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getConnectId() {
            return this.connect_id;
        }

        public LiteExtendJson setStatusCode(String str) {
            this.status_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatusCode() {
            return this.status_code;
        }

        public LiteExtendJson setIsMandatoryUpgrade(int i) {
            this.is_mandatory_upgrade = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIsMandatoryUpgrade() {
            return this.is_mandatory_upgrade;
        }

        public LiteExtendJson setAppautoSubtype(int i) {
            this.appauto_subtype = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAppautoSubtype() {
            return this.appauto_subtype;
        }

        public LiteExtendJson setBtDeviceVersion(String str) {
            this.bt_device_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceVersion() {
            return this.bt_device_version;
        }

        public LiteExtendJson setIsGuided(boolean z) {
            this.is_guided = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isGuided() {
            return this.is_guided;
        }

        public LiteExtendJson setErrorMsg(String str) {
            this.error_msg = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorMsg() {
            return this.error_msg;
        }

        public LiteExtendJson setFunction(String str) {
            this.function = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFunction() {
            return this.function;
        }

        public LiteExtendJson setFunc(String str) {
            this.func = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFunc() {
            return this.func;
        }

        public LiteExtendJson setMsgId(String str) {
            this.msg_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMsgId() {
            return this.msg_id;
        }

        public LiteExtendJson setWakeupChannel(String str) {
            this.wakeup_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupChannel() {
            return this.wakeup_channel;
        }

        public LiteExtendJson setMode(LiteExtendJsonMode liteExtendJsonMode) {
            this.mode = Optional.ofNullable(liteExtendJsonMode);
            return this;
        }

        public Optional<LiteExtendJsonMode> getMode() {
            return this.mode;
        }

        public LiteExtendJson setOriginLang(String str) {
            this.origin_lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginLang() {
            return this.origin_lang;
        }

        public LiteExtendJson setTargetLang(String str) {
            this.target_lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTargetLang() {
            return this.target_lang;
        }

        public LiteExtendJson setInputMethod(LiteExtendJsonInputMethod liteExtendJsonInputMethod) {
            this.input_method = Optional.ofNullable(liteExtendJsonInputMethod);
            return this;
        }

        public Optional<LiteExtendJsonInputMethod> getInputMethod() {
            return this.input_method;
        }

        public LiteExtendJson setOpenMicTimeLength(long j) {
            this.open_mic_time_length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getOpenMicTimeLength() {
            return this.open_mic_time_length;
        }

        public LiteExtendJson setStopMicMethod(LiteExtendJsonStopMicMethod liteExtendJsonStopMicMethod) {
            this.stop_mic_method = Optional.ofNullable(liteExtendJsonStopMicMethod);
            return this;
        }

        public Optional<LiteExtendJsonStopMicMethod> getStopMicMethod() {
            return this.stop_mic_method;
        }

        public LiteExtendJson setSuggestionType(LiteExtendJsonSuggestionType liteExtendJsonSuggestionType) {
            this.suggestion_type = Optional.ofNullable(liteExtendJsonSuggestionType);
            return this;
        }

        public Optional<LiteExtendJsonSuggestionType> getSuggestionType() {
            return this.suggestion_type;
        }

        public LiteExtendJson setFuncList(List<String> list) {
            this.func_list = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getFuncList() {
            return this.func_list;
        }

        public LiteExtendJson setCardType(TrackCardType trackCardType) {
            this.card_type = Optional.ofNullable(trackCardType);
            return this;
        }

        public Optional<TrackCardType> getCardType() {
            return this.card_type;
        }

        public LiteExtendJson setBtServiceApiName(String str) {
            this.bt_service_api_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtServiceApiName() {
            return this.bt_service_api_name;
        }

        public LiteExtendJson setBtServiceParams(List<String> list) {
            this.bt_service_params = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getBtServiceParams() {
            return this.bt_service_params;
        }

        public LiteExtendJson setIsFloatingBallSwitchOpen(boolean z) {
            this.is_floating_ball_switch_open = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFloatingBallSwitchOpen() {
            return this.is_floating_ball_switch_open;
        }

        public LiteExtendJson setFloatingBallWakeupMethod(TrackFloatingBallWakeupMethod trackFloatingBallWakeupMethod) {
            this.floating_ball_wakeup_method = Optional.ofNullable(trackFloatingBallWakeupMethod);
            return this;
        }

        public Optional<TrackFloatingBallWakeupMethod> getFloatingBallWakeupMethod() {
            return this.floating_ball_wakeup_method;
        }

        public LiteExtendJson setFloatingBallInvisibleSwtich(boolean z) {
            this.floating_ball_invisible_swtich = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFloatingBallInvisibleSwtich() {
            return this.floating_ball_invisible_swtich;
        }

        public LiteExtendJson setFloatingBallSize(double d) {
            this.floating_ball_size = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getFloatingBallSize() {
            return this.floating_ball_size;
        }
    }

    /* loaded from: classes3.dex */
    public static class LiteTrackLog {
        @Required
        private LiteEventParams event_params;
        @Required
        private LiteEventType event_type;

        public LiteTrackLog() {
        }

        public LiteTrackLog(LiteEventType liteEventType, LiteEventParams liteEventParams) {
            this.event_type = liteEventType;
            this.event_params = liteEventParams;
        }

        @Required
        public LiteTrackLog setEventType(LiteEventType liteEventType) {
            this.event_type = liteEventType;
            return this;
        }

        @Required
        public LiteEventType getEventType() {
            return this.event_type;
        }

        @Required
        public LiteTrackLog setEventParams(LiteEventParams liteEventParams) {
            this.event_params = liteEventParams;
            return this;
        }

        @Required
        public LiteEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiniProgramEventContext {
        @Required
        private String device_type;
        @Required
        private String miai_version;
        @Required
        private String platform_id;
        @Required
        private String uid;
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> rom_version = Optional.empty();

        public MiniProgramEventContext() {
        }

        public MiniProgramEventContext(String str, String str2, String str3, String str4) {
            this.uid = str;
            this.platform_id = str2;
            this.device_type = str3;
            this.miai_version = str4;
        }

        @Required
        public MiniProgramEventContext setUid(String str) {
            this.uid = str;
            return this;
        }

        @Required
        public String getUid() {
            return this.uid;
        }

        public MiniProgramEventContext setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        @Required
        public MiniProgramEventContext setPlatformId(String str) {
            this.platform_id = str;
            return this;
        }

        @Required
        public String getPlatformId() {
            return this.platform_id;
        }

        @Required
        public MiniProgramEventContext setDeviceType(String str) {
            this.device_type = str;
            return this;
        }

        @Required
        public String getDeviceType() {
            return this.device_type;
        }

        @Required
        public MiniProgramEventContext setMiaiVersion(String str) {
            this.miai_version = str;
            return this;
        }

        @Required
        public String getMiaiVersion() {
            return this.miai_version;
        }

        public MiniProgramEventContext setRomVersion(String str) {
            this.rom_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRomVersion() {
            return this.rom_version;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiniProgramEventParams {
        @Required
        private MiniProgramEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<MiniProgramExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public MiniProgramEventParams() {
        }

        public MiniProgramEventParams(String str, MiniProgramEventContext miniProgramEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = miniProgramEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public MiniProgramEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public MiniProgramEventParams setEventContext(MiniProgramEventContext miniProgramEventContext) {
            this.event_context = miniProgramEventContext;
            return this;
        }

        @Required
        public MiniProgramEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public MiniProgramEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public MiniProgramEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public MiniProgramEventParams setExtendJson(MiniProgramExtendJson miniProgramExtendJson) {
            this.extend_json = Optional.ofNullable(miniProgramExtendJson);
            return this;
        }

        public Optional<MiniProgramExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiniProgramExtendJson {
        private Optional<String> from = Optional.empty();
        private Optional<String> click_type = Optional.empty();

        public MiniProgramExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public MiniProgramExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiniProgramTrackLog {
        @Required
        private MiniProgramEventParams event_params;
        @Required
        private MiniProgramEventType event_type;

        public MiniProgramTrackLog() {
        }

        public MiniProgramTrackLog(MiniProgramEventType miniProgramEventType, MiniProgramEventParams miniProgramEventParams) {
            this.event_type = miniProgramEventType;
            this.event_params = miniProgramEventParams;
        }

        @Required
        public MiniProgramTrackLog setEventType(MiniProgramEventType miniProgramEventType) {
            this.event_type = miniProgramEventType;
            return this;
        }

        @Required
        public MiniProgramEventType getEventType() {
            return this.event_type;
        }

        @Required
        public MiniProgramTrackLog setEventParams(MiniProgramEventParams miniProgramEventParams) {
            this.event_params = miniProgramEventParams;
            return this;
        }

        @Required
        public MiniProgramEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class NoScreenBoxEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        @Required
        private String hardware;
        @Required
        private String rom_version;
        @Required
        private String serial_number;
        private Optional<String> uid = Optional.empty();
        private Optional<String> district_setting = Optional.empty();
        private Optional<String> device_model = Optional.empty();
        private Optional<String> channel_version = Optional.empty();
        private Optional<String> os = Optional.empty();

        public NoScreenBoxEventContext() {
        }

        public NoScreenBoxEventContext(String str, String str2, String str3, String str4, String str5) {
            this.device_id = str;
            this.serial_number = str2;
            this.hardware = str3;
            this.app_id = str4;
            this.rom_version = str5;
        }

        @Required
        public NoScreenBoxEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public NoScreenBoxEventContext setSerialNumber(String str) {
            this.serial_number = str;
            return this;
        }

        @Required
        public String getSerialNumber() {
            return this.serial_number;
        }

        @Required
        public NoScreenBoxEventContext setHardware(String str) {
            this.hardware = str;
            return this;
        }

        @Required
        public String getHardware() {
            return this.hardware;
        }

        public NoScreenBoxEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        @Required
        public NoScreenBoxEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        @Required
        public NoScreenBoxEventContext setRomVersion(String str) {
            this.rom_version = str;
            return this;
        }

        @Required
        public String getRomVersion() {
            return this.rom_version;
        }

        public NoScreenBoxEventContext setDistrictSetting(String str) {
            this.district_setting = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDistrictSetting() {
            return this.district_setting;
        }

        public NoScreenBoxEventContext setDeviceModel(String str) {
            this.device_model = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModel() {
            return this.device_model;
        }

        public NoScreenBoxEventContext setChannelVersion(String str) {
            this.channel_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getChannelVersion() {
            return this.channel_version;
        }

        public NoScreenBoxEventContext setOs(String str) {
            this.os = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOs() {
            return this.os;
        }
    }

    /* loaded from: classes3.dex */
    public static class NoScreenBoxEventParams {
        @Required
        private String dialog_id;
        @Required
        private NoScreenBoxEventContext event_context;
        @Required
        private long timestamp;
        private Optional<NoScreenBoxExtendJson> extend_json = Optional.empty();
        private Optional<String> widget = Optional.empty();
        private Optional<String> event_data_type = Optional.empty();

        public NoScreenBoxEventParams() {
        }

        public NoScreenBoxEventParams(String str, long j, NoScreenBoxEventContext noScreenBoxEventContext) {
            this.dialog_id = str;
            this.timestamp = j;
            this.event_context = noScreenBoxEventContext;
        }

        @Required
        public NoScreenBoxEventParams setDialogId(String str) {
            this.dialog_id = str;
            return this;
        }

        @Required
        public String getDialogId() {
            return this.dialog_id;
        }

        @Required
        public NoScreenBoxEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        @Required
        public NoScreenBoxEventParams setEventContext(NoScreenBoxEventContext noScreenBoxEventContext) {
            this.event_context = noScreenBoxEventContext;
            return this;
        }

        @Required
        public NoScreenBoxEventContext getEventContext() {
            return this.event_context;
        }

        public NoScreenBoxEventParams setExtendJson(NoScreenBoxExtendJson noScreenBoxExtendJson) {
            this.extend_json = Optional.ofNullable(noScreenBoxExtendJson);
            return this;
        }

        public Optional<NoScreenBoxExtendJson> getExtendJson() {
            return this.extend_json;
        }

        public NoScreenBoxEventParams setWidget(String str) {
            this.widget = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWidget() {
            return this.widget;
        }

        public NoScreenBoxEventParams setEventDataType(String str) {
            this.event_data_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEventDataType() {
            return this.event_data_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class NoScreenBoxExtendJson {
        private Optional<String> origin_id = Optional.empty();
        private Optional<String> loadmore_token = Optional.empty();
        private Optional<String> request_type = Optional.empty();
        private Optional<String> audio_type = Optional.empty();
        private Optional<String> error_type = Optional.empty();
        private Optional<Boolean> is_interrupted = Optional.empty();
        private Optional<String> interrupted_type = Optional.empty();
        private Optional<String> audio_id = Optional.empty();
        private Optional<String> cp_id = Optional.empty();
        private Optional<String> cp_name = Optional.empty();
        private Optional<Long> start_time = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<Long> length = Optional.empty();
        private Optional<Long> position = Optional.empty();
        private Optional<Integer> offset = Optional.empty();
        private Optional<String> switch_type = Optional.empty();
        private Optional<Integer> mdplay = Optional.empty();
        private Optional<String> cp_album_id = Optional.empty();
        private Optional<Integer> episode_index = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<ObjectNode> log_extension = Optional.empty();
        private Optional<String> origin = Optional.empty();
        private Optional<String> widget = Optional.empty();
        private Optional<String> target = Optional.empty();
        private Optional<String> error_code = Optional.empty();

        public NoScreenBoxExtendJson setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public NoScreenBoxExtendJson setLoadmoreToken(String str) {
            this.loadmore_token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadmoreToken() {
            return this.loadmore_token;
        }

        public NoScreenBoxExtendJson setRequestType(String str) {
            this.request_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRequestType() {
            return this.request_type;
        }

        public NoScreenBoxExtendJson setAudioType(String str) {
            this.audio_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioType() {
            return this.audio_type;
        }

        public NoScreenBoxExtendJson setErrorType(String str) {
            this.error_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorType() {
            return this.error_type;
        }

        public NoScreenBoxExtendJson setIsInterrupted(boolean z) {
            this.is_interrupted = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInterrupted() {
            return this.is_interrupted;
        }

        public NoScreenBoxExtendJson setInterruptedType(String str) {
            this.interrupted_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInterruptedType() {
            return this.interrupted_type;
        }

        public NoScreenBoxExtendJson setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public NoScreenBoxExtendJson setCpId(String str) {
            this.cp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpId() {
            return this.cp_id;
        }

        public NoScreenBoxExtendJson setCpName(String str) {
            this.cp_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpName() {
            return this.cp_name;
        }

        public NoScreenBoxExtendJson setStartTime(long j) {
            this.start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getStartTime() {
            return this.start_time;
        }

        public NoScreenBoxExtendJson setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public NoScreenBoxExtendJson setLength(long j) {
            this.length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLength() {
            return this.length;
        }

        public NoScreenBoxExtendJson setPosition(long j) {
            this.position = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPosition() {
            return this.position;
        }

        public NoScreenBoxExtendJson setOffset(int i) {
            this.offset = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOffset() {
            return this.offset;
        }

        public NoScreenBoxExtendJson setSwitchType(String str) {
            this.switch_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSwitchType() {
            return this.switch_type;
        }

        public NoScreenBoxExtendJson setMdplay(int i) {
            this.mdplay = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMdplay() {
            return this.mdplay;
        }

        public NoScreenBoxExtendJson setCpAlbumId(String str) {
            this.cp_album_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpAlbumId() {
            return this.cp_album_id;
        }

        public NoScreenBoxExtendJson setEpisodeIndex(int i) {
            this.episode_index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisodeIndex() {
            return this.episode_index;
        }

        public NoScreenBoxExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public NoScreenBoxExtendJson setLogExtension(ObjectNode objectNode) {
            this.log_extension = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLogExtension() {
            return this.log_extension;
        }

        public NoScreenBoxExtendJson setOrigin(String str) {
            this.origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOrigin() {
            return this.origin;
        }

        public NoScreenBoxExtendJson setWidget(String str) {
            this.widget = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWidget() {
            return this.widget;
        }

        public NoScreenBoxExtendJson setTarget(String str) {
            this.target = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTarget() {
            return this.target;
        }

        public NoScreenBoxExtendJson setErrorCode(String str) {
            this.error_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorCode() {
            return this.error_code;
        }
    }

    /* loaded from: classes3.dex */
    public static class NoScreenBoxTrackLog {
        @Required
        private NoScreenBoxEventParams event_params;
        @Required
        private NoScreenBoxEventType event_type;

        public NoScreenBoxTrackLog() {
        }

        public NoScreenBoxTrackLog(NoScreenBoxEventType noScreenBoxEventType, NoScreenBoxEventParams noScreenBoxEventParams) {
            this.event_type = noScreenBoxEventType;
            this.event_params = noScreenBoxEventParams;
        }

        @Required
        public NoScreenBoxTrackLog setEventType(NoScreenBoxEventType noScreenBoxEventType) {
            this.event_type = noScreenBoxEventType;
            return this;
        }

        @Required
        public NoScreenBoxEventType getEventType() {
            return this.event_type;
        }

        @Required
        public NoScreenBoxTrackLog setEventParams(NoScreenBoxEventParams noScreenBoxEventParams) {
            this.event_params = noScreenBoxEventParams;
            return this;
        }

        @Required
        public NoScreenBoxEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class OpenplatformEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        @Required
        private String device_type;
        @Required
        private String miai_version;
        @Required
        private String platform_id;
        @Required
        private String uid;
        private Optional<String> sid = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> rom_version = Optional.empty();

        public OpenplatformEventContext() {
        }

        public OpenplatformEventContext(String str, String str2, String str3, String str4, String str5, String str6) {
            this.device_id = str;
            this.platform_id = str2;
            this.app_id = str3;
            this.uid = str4;
            this.device_type = str5;
            this.miai_version = str6;
        }

        @Required
        public OpenplatformEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public OpenplatformEventContext setPlatformId(String str) {
            this.platform_id = str;
            return this;
        }

        @Required
        public String getPlatformId() {
            return this.platform_id;
        }

        @Required
        public OpenplatformEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        @Required
        public OpenplatformEventContext setUid(String str) {
            this.uid = str;
            return this;
        }

        @Required
        public String getUid() {
            return this.uid;
        }

        @Required
        public OpenplatformEventContext setDeviceType(String str) {
            this.device_type = str;
            return this;
        }

        @Required
        public String getDeviceType() {
            return this.device_type;
        }

        @Required
        public OpenplatformEventContext setMiaiVersion(String str) {
            this.miai_version = str;
            return this;
        }

        @Required
        public String getMiaiVersion() {
            return this.miai_version;
        }

        public OpenplatformEventContext setSid(String str) {
            this.sid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSid() {
            return this.sid;
        }

        public OpenplatformEventContext setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public OpenplatformEventContext setRomVersion(String str) {
            this.rom_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRomVersion() {
            return this.rom_version;
        }
    }

    /* loaded from: classes3.dex */
    public static class OpenplatformEventParams {
        @Required
        private OpenplatformEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<OpenplatformExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public OpenplatformEventParams() {
        }

        public OpenplatformEventParams(String str, OpenplatformEventContext openplatformEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = openplatformEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public OpenplatformEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public OpenplatformEventParams setEventContext(OpenplatformEventContext openplatformEventContext) {
            this.event_context = openplatformEventContext;
            return this;
        }

        @Required
        public OpenplatformEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public OpenplatformEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public OpenplatformEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public OpenplatformEventParams setExtendJson(OpenplatformExtendJson openplatformExtendJson) {
            this.extend_json = Optional.ofNullable(openplatformExtendJson);
            return this;
        }

        public Optional<OpenplatformExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class OpenplatformExtendJson {
        private Optional<String> audio_id = Optional.empty();
        private Optional<String> audio_type = Optional.empty();
        private Optional<String> cp_name = Optional.empty();
        private Optional<String> cp_id = Optional.empty();
        private Optional<String> label = Optional.empty();
        private Optional<String> label_id = Optional.empty();
        private Optional<String> album_id = Optional.empty();
        private Optional<Integer> episode_index = Optional.empty();
        private Optional<Integer> time_cost = Optional.empty();
        private Optional<Long> duration_in_milli = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> switch_type = Optional.empty();
        private Optional<Long> start_time = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<Integer> volume = Optional.empty();
        private Optional<Boolean> is_alarm = Optional.empty();
        private Optional<Boolean> is_kid_mode = Optional.empty();
        private Optional<Boolean> is_disable_personalize = Optional.empty();
        private Optional<String> play_mode = Optional.empty();
        private Optional<Integer> status = Optional.empty();
        private Optional<Integer> error_code = Optional.empty();
        private Optional<String> key_value = Optional.empty();
        private Optional<String> click_type = Optional.empty();
        private Optional<String> extend_type = Optional.empty();
        private Optional<Integer> status_code = Optional.empty();
        private Optional<Boolean> is_true = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<String> content = Optional.empty();

        public OpenplatformExtendJson setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public OpenplatformExtendJson setAudioType(String str) {
            this.audio_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioType() {
            return this.audio_type;
        }

        public OpenplatformExtendJson setCpName(String str) {
            this.cp_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpName() {
            return this.cp_name;
        }

        public OpenplatformExtendJson setCpId(String str) {
            this.cp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpId() {
            return this.cp_id;
        }

        public OpenplatformExtendJson setLabel(String str) {
            this.label = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLabel() {
            return this.label;
        }

        public OpenplatformExtendJson setLabelId(String str) {
            this.label_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLabelId() {
            return this.label_id;
        }

        public OpenplatformExtendJson setAlbumId(String str) {
            this.album_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlbumId() {
            return this.album_id;
        }

        public OpenplatformExtendJson setEpisodeIndex(int i) {
            this.episode_index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisodeIndex() {
            return this.episode_index;
        }

        public OpenplatformExtendJson setTimeCost(int i) {
            this.time_cost = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTimeCost() {
            return this.time_cost;
        }

        public OpenplatformExtendJson setDurationInMilli(long j) {
            this.duration_in_milli = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDurationInMilli() {
            return this.duration_in_milli;
        }

        public OpenplatformExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public OpenplatformExtendJson setSwitchType(String str) {
            this.switch_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSwitchType() {
            return this.switch_type;
        }

        public OpenplatformExtendJson setStartTime(long j) {
            this.start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getStartTime() {
            return this.start_time;
        }

        public OpenplatformExtendJson setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public OpenplatformExtendJson setVolume(int i) {
            this.volume = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVolume() {
            return this.volume;
        }

        public OpenplatformExtendJson setIsAlarm(boolean z) {
            this.is_alarm = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAlarm() {
            return this.is_alarm;
        }

        public OpenplatformExtendJson setIsKidMode(boolean z) {
            this.is_kid_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isKidMode() {
            return this.is_kid_mode;
        }

        public OpenplatformExtendJson setIsDisablePersonalize(boolean z) {
            this.is_disable_personalize = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDisablePersonalize() {
            return this.is_disable_personalize;
        }

        public OpenplatformExtendJson setPlayMode(String str) {
            this.play_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPlayMode() {
            return this.play_mode;
        }

        public OpenplatformExtendJson setStatus(int i) {
            this.status = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatus() {
            return this.status;
        }

        public OpenplatformExtendJson setErrorCode(int i) {
            this.error_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getErrorCode() {
            return this.error_code;
        }

        public OpenplatformExtendJson setKeyValue(String str) {
            this.key_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyValue() {
            return this.key_value;
        }

        public OpenplatformExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }

        public OpenplatformExtendJson setExtendType(String str) {
            this.extend_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExtendType() {
            return this.extend_type;
        }

        public OpenplatformExtendJson setStatusCode(int i) {
            this.status_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatusCode() {
            return this.status_code;
        }

        public OpenplatformExtendJson setIsTrue(boolean z) {
            this.is_true = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isTrue() {
            return this.is_true;
        }

        public OpenplatformExtendJson setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public OpenplatformExtendJson setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }
    }

    /* loaded from: classes3.dex */
    public static class OpenplatformTrackLog {
        @Required
        private OpenplatformEventParams event_params;
        @Required
        private OpenplatformEventType event_type;

        public OpenplatformTrackLog() {
        }

        public OpenplatformTrackLog(OpenplatformEventType openplatformEventType, OpenplatformEventParams openplatformEventParams) {
            this.event_type = openplatformEventType;
            this.event_params = openplatformEventParams;
        }

        @Required
        public OpenplatformTrackLog setEventType(OpenplatformEventType openplatformEventType) {
            this.event_type = openplatformEventType;
            return this;
        }

        @Required
        public OpenplatformEventType getEventType() {
            return this.event_type;
        }

        @Required
        public OpenplatformTrackLog setEventParams(OpenplatformEventParams openplatformEventParams) {
            this.event_params = openplatformEventParams;
            return this;
        }

        @Required
        public OpenplatformEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class PerfEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        private Optional<String> bt_device_mac_address = Optional.empty();
        private Optional<String> bt_device_name = Optional.empty();
        private Optional<String> uid = Optional.empty();
        private Optional<String> network_status = Optional.empty();
        private Optional<String> rom_version = Optional.empty();
        private Optional<String> device_model = Optional.empty();
        private Optional<String> miai_version = Optional.empty();
        private Optional<String> build_versioncode = Optional.empty();
        private Optional<Boolean> channel_rom_version = Optional.empty();
        private Optional<String> device_model_codename = Optional.empty();
        private Optional<String> android_version = Optional.empty();
        private Optional<String> android_sdk_version = Optional.empty();
        private Optional<String> upgrade_channel = Optional.empty();
        private Optional<String> device_type = Optional.empty();
        private Optional<String> position = Optional.empty();
        private Optional<String> cur_page = Optional.empty();
        private Optional<String> ua = Optional.empty();
        private Optional<String> cid = Optional.empty();

        public PerfEventContext() {
        }

        public PerfEventContext(String str, String str2) {
            this.device_id = str;
            this.app_id = str2;
        }

        @Required
        public PerfEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public PerfEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        public PerfEventContext setBtDeviceMacAddress(String str) {
            this.bt_device_mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceMacAddress() {
            return this.bt_device_mac_address;
        }

        public PerfEventContext setBtDeviceName(String str) {
            this.bt_device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceName() {
            return this.bt_device_name;
        }

        public PerfEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public PerfEventContext setNetworkStatus(String str) {
            this.network_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkStatus() {
            return this.network_status;
        }

        public PerfEventContext setRomVersion(String str) {
            this.rom_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRomVersion() {
            return this.rom_version;
        }

        public PerfEventContext setDeviceModel(String str) {
            this.device_model = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModel() {
            return this.device_model;
        }

        public PerfEventContext setMiaiVersion(String str) {
            this.miai_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMiaiVersion() {
            return this.miai_version;
        }

        public PerfEventContext setBuildVersioncode(String str) {
            this.build_versioncode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBuildVersioncode() {
            return this.build_versioncode;
        }

        public PerfEventContext setChannelRomVersion(boolean z) {
            this.channel_rom_version = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isChannelRomVersion() {
            return this.channel_rom_version;
        }

        public PerfEventContext setDeviceModelCodename(String str) {
            this.device_model_codename = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModelCodename() {
            return this.device_model_codename;
        }

        public PerfEventContext setAndroidVersion(String str) {
            this.android_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidVersion() {
            return this.android_version;
        }

        public PerfEventContext setAndroidSdkVersion(String str) {
            this.android_sdk_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidSdkVersion() {
            return this.android_sdk_version;
        }

        public PerfEventContext setUpgradeChannel(String str) {
            this.upgrade_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUpgradeChannel() {
            return this.upgrade_channel;
        }

        public PerfEventContext setDeviceType(String str) {
            this.device_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceType() {
            return this.device_type;
        }

        public PerfEventContext setPosition(String str) {
            this.position = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPosition() {
            return this.position;
        }

        public PerfEventContext setCurPage(String str) {
            this.cur_page = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurPage() {
            return this.cur_page;
        }

        public PerfEventContext setUa(String str) {
            this.ua = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUa() {
            return this.ua;
        }

        public PerfEventContext setCid(String str) {
            this.cid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCid() {
            return this.cid;
        }
    }

    /* loaded from: classes3.dex */
    public static class PerfEventParams {
        @Required
        private PerfEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<PerfExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public PerfEventParams() {
        }

        public PerfEventParams(String str, PerfEventContext perfEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = perfEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public PerfEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public PerfEventParams setEventContext(PerfEventContext perfEventContext) {
            this.event_context = perfEventContext;
            return this;
        }

        @Required
        public PerfEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public PerfEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public PerfEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public PerfEventParams setExtendJson(PerfExtendJson perfExtendJson) {
            this.extend_json = Optional.ofNullable(perfExtendJson);
            return this;
        }

        public Optional<PerfExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class PerfExtendJson {
        private Optional<String> rpk_packagename = Optional.empty();
        private Optional<Integer> rpk_version_code = Optional.empty();
        private Optional<String> url = Optional.empty();
        private Optional<Long> length = Optional.empty();
        private Optional<String> status_code = Optional.empty();
        private Optional<String> message = Optional.empty();
        private Optional<String> page_url = Optional.empty();
        private Optional<String> item_list = Optional.empty();
        private Optional<String> get_type = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<Boolean> support = Optional.empty();
        private Optional<String> version = Optional.empty();
        private Optional<Integer> sw_handle_times = Optional.empty();
        private Optional<Integer> sw_cache_will_be_used_times = Optional.empty();
        private Optional<String> sw_cache_will_be_load_urls = Optional.empty();
        private Optional<Integer> static_load_success_times = Optional.empty();
        private Optional<Integer> static_handle_times = Optional.empty();
        private Optional<String> static_urls = Optional.empty();
        private Optional<String> uuid = Optional.empty();
        private Optional<String> cid = Optional.empty();
        private Optional<String> user_defined_id = Optional.empty();
        private Optional<Double> rate = Optional.empty();
        private Optional<String> stage = Optional.empty();
        private Optional<Integer> status = Optional.empty();

        public PerfExtendJson setRpkPackagename(String str) {
            this.rpk_packagename = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRpkPackagename() {
            return this.rpk_packagename;
        }

        public PerfExtendJson setRpkVersionCode(int i) {
            this.rpk_version_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRpkVersionCode() {
            return this.rpk_version_code;
        }

        public PerfExtendJson setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public PerfExtendJson setLength(long j) {
            this.length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLength() {
            return this.length;
        }

        public PerfExtendJson setStatusCode(String str) {
            this.status_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatusCode() {
            return this.status_code;
        }

        public PerfExtendJson setMessage(String str) {
            this.message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessage() {
            return this.message;
        }

        public PerfExtendJson setPageUrl(String str) {
            this.page_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageUrl() {
            return this.page_url;
        }

        public PerfExtendJson setItemList(String str) {
            this.item_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getItemList() {
            return this.item_list;
        }

        public PerfExtendJson setGetType(String str) {
            this.get_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGetType() {
            return this.get_type;
        }

        public PerfExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public PerfExtendJson setSupport(boolean z) {
            this.support = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSupport() {
            return this.support;
        }

        public PerfExtendJson setVersion(String str) {
            this.version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVersion() {
            return this.version;
        }

        public PerfExtendJson setSwHandleTimes(int i) {
            this.sw_handle_times = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSwHandleTimes() {
            return this.sw_handle_times;
        }

        public PerfExtendJson setSwCacheWillBeUsedTimes(int i) {
            this.sw_cache_will_be_used_times = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSwCacheWillBeUsedTimes() {
            return this.sw_cache_will_be_used_times;
        }

        public PerfExtendJson setSwCacheWillBeLoadUrls(String str) {
            this.sw_cache_will_be_load_urls = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSwCacheWillBeLoadUrls() {
            return this.sw_cache_will_be_load_urls;
        }

        public PerfExtendJson setStaticLoadSuccessTimes(int i) {
            this.static_load_success_times = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStaticLoadSuccessTimes() {
            return this.static_load_success_times;
        }

        public PerfExtendJson setStaticHandleTimes(int i) {
            this.static_handle_times = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStaticHandleTimes() {
            return this.static_handle_times;
        }

        public PerfExtendJson setStaticUrls(String str) {
            this.static_urls = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStaticUrls() {
            return this.static_urls;
        }

        public PerfExtendJson setUuid(String str) {
            this.uuid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUuid() {
            return this.uuid;
        }

        public PerfExtendJson setCid(String str) {
            this.cid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCid() {
            return this.cid;
        }

        public PerfExtendJson setUserDefinedId(String str) {
            this.user_defined_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUserDefinedId() {
            return this.user_defined_id;
        }

        public PerfExtendJson setRate(double d) {
            this.rate = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getRate() {
            return this.rate;
        }

        public PerfExtendJson setStage(String str) {
            this.stage = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStage() {
            return this.stage;
        }

        public PerfExtendJson setStatus(int i) {
            this.status = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatus() {
            return this.status;
        }
    }

    /* loaded from: classes3.dex */
    public static class PerfTrackLog {
        @Required
        private PerfEventParams event_params;
        @Required
        private PerfEventType event_type;

        public PerfTrackLog() {
        }

        public PerfTrackLog(PerfEventType perfEventType, PerfEventParams perfEventParams) {
            this.event_type = perfEventType;
            this.event_params = perfEventParams;
        }

        @Required
        public PerfTrackLog setEventType(PerfEventType perfEventType) {
            this.event_type = perfEventType;
            return this;
        }

        @Required
        public PerfEventType getEventType() {
            return this.event_type;
        }

        @Required
        public PerfTrackLog setEventParams(PerfEventParams perfEventParams) {
            this.event_params = perfEventParams;
            return this;
        }

        @Required
        public PerfEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class ScreenBoxEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        @Required
        private String hardware;
        @Required
        private String os_version;
        @Required
        private String rom_version;
        @Required
        private String serial_number;
        private Optional<String> district_setting = Optional.empty();
        private Optional<String> channel_version = Optional.empty();
        private Optional<String> uid = Optional.empty();
        private Optional<TrackNetworkStatus> network_status = Optional.empty();
        private Optional<TrackUserMode> user_mode = Optional.empty();
        private Optional<TrackPageType> page_type = Optional.empty();

        public ScreenBoxEventContext() {
        }

        public ScreenBoxEventContext(String str, String str2, String str3, String str4, String str5, String str6) {
            this.device_id = str;
            this.serial_number = str2;
            this.hardware = str3;
            this.app_id = str4;
            this.rom_version = str5;
            this.os_version = str6;
        }

        @Required
        public ScreenBoxEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public ScreenBoxEventContext setSerialNumber(String str) {
            this.serial_number = str;
            return this;
        }

        @Required
        public String getSerialNumber() {
            return this.serial_number;
        }

        @Required
        public ScreenBoxEventContext setHardware(String str) {
            this.hardware = str;
            return this;
        }

        @Required
        public String getHardware() {
            return this.hardware;
        }

        @Required
        public ScreenBoxEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        @Required
        public ScreenBoxEventContext setRomVersion(String str) {
            this.rom_version = str;
            return this;
        }

        @Required
        public String getRomVersion() {
            return this.rom_version;
        }

        @Required
        public ScreenBoxEventContext setOsVersion(String str) {
            this.os_version = str;
            return this;
        }

        @Required
        public String getOsVersion() {
            return this.os_version;
        }

        public ScreenBoxEventContext setDistrictSetting(String str) {
            this.district_setting = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDistrictSetting() {
            return this.district_setting;
        }

        public ScreenBoxEventContext setChannelVersion(String str) {
            this.channel_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getChannelVersion() {
            return this.channel_version;
        }

        public ScreenBoxEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public ScreenBoxEventContext setNetworkStatus(TrackNetworkStatus trackNetworkStatus) {
            this.network_status = Optional.ofNullable(trackNetworkStatus);
            return this;
        }

        public Optional<TrackNetworkStatus> getNetworkStatus() {
            return this.network_status;
        }

        public ScreenBoxEventContext setUserMode(TrackUserMode trackUserMode) {
            this.user_mode = Optional.ofNullable(trackUserMode);
            return this;
        }

        public Optional<TrackUserMode> getUserMode() {
            return this.user_mode;
        }

        public ScreenBoxEventContext setPageType(TrackPageType trackPageType) {
            this.page_type = Optional.ofNullable(trackPageType);
            return this;
        }

        public Optional<TrackPageType> getPageType() {
            return this.page_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class ScreenBoxEventParams {
        @Required
        private ScreenBoxEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<ScreenBoxExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public ScreenBoxEventParams() {
        }

        public ScreenBoxEventParams(String str, ScreenBoxEventContext screenBoxEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = screenBoxEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public ScreenBoxEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public ScreenBoxEventParams setEventContext(ScreenBoxEventContext screenBoxEventContext) {
            this.event_context = screenBoxEventContext;
            return this;
        }

        @Required
        public ScreenBoxEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public ScreenBoxEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public ScreenBoxEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public ScreenBoxEventParams setExtendJson(ScreenBoxExtendJson screenBoxExtendJson) {
            this.extend_json = Optional.ofNullable(screenBoxExtendJson);
            return this;
        }

        public Optional<ScreenBoxExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class ScreenBoxExtendJson {
        private Optional<String> meta_id = Optional.empty();
        private Optional<String> resource_id = Optional.empty();
        private Optional<String> cp = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> media_type = Optional.empty();
        private Optional<String> click_type = Optional.empty();
        @Deprecated
        private Optional<String> play_type = Optional.empty();
        private Optional<Long> duration = Optional.empty();
        @Deprecated
        private Optional<Long> position = Optional.empty();
        private Optional<String> cp_id = Optional.empty();
        private Optional<String> cp_name = Optional.empty();
        private Optional<Integer> offset = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> exp_id = Optional.empty();
        private Optional<String> imp_area = Optional.empty();
        private Optional<String> content_mode = Optional.empty();
        private Optional<String> control_type = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<String> dialog_origin = Optional.empty();
        private Optional<String> shortvideo_type = Optional.empty();
        private Optional<String> element_id = Optional.empty();
        private Optional<String> trace_id = Optional.empty();
        private Optional<String> tab = Optional.empty();
        private Optional<Integer> tab_offset = Optional.empty();
        private Optional<String> audio_id = Optional.empty();
        private Optional<String> audio_source = Optional.empty();
        private Optional<Long> play_start_time = Optional.empty();
        private Optional<Long> play_position = Optional.empty();
        private Optional<ScreenBoxPlayFinishType> play_finish_type = Optional.empty();
        private Optional<String> play_event_type = Optional.empty();
        private Optional<Boolean> is_interrupted = Optional.empty();
        private Optional<String> music_copyright_party = Optional.empty();
        private Optional<String> music_copyright_party_id = Optional.empty();
        private Optional<String> album_id = Optional.empty();
        private Optional<Integer> episode_index = Optional.empty();
        private Optional<String> query_string = Optional.empty();
        private Optional<ScreenBoxStatus> status = Optional.empty();
        private Optional<ScreenBoxExtendType> extend_type = Optional.empty();
        private Optional<String> debug_info = Optional.empty();
        private Optional<String> query_source = Optional.empty();
        private Optional<String> app_name = Optional.empty();
        private Optional<String> query_text = Optional.empty();
        private Optional<String> send_query = Optional.empty();
        private Optional<String> query_id = Optional.empty();
        private Optional<String> domain = Optional.empty();
        private Optional<Boolean> is_cut_in = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<Long> play_end_time = Optional.empty();
        private Optional<ObjectNode> log_extension = Optional.empty();
        private Optional<String> cp_album_id = Optional.empty();
        private Optional<String> switch_type = Optional.empty();
        private Optional<String> audio_type = Optional.empty();
        private Optional<String> button_content = Optional.empty();
        private Optional<String> product = Optional.empty();
        private Optional<Double> price = Optional.empty();
        private Optional<String> recall_type = Optional.empty();
        private Optional<String> sub_tab = Optional.empty();
        private Optional<String> module_name = Optional.empty();
        private Optional<String> page_type = Optional.empty();
        private Optional<String> float_type = Optional.empty();
        private Optional<Boolean> is_true = Optional.empty();
        private Optional<String> exit_type = Optional.empty();
        private Optional<String> error_code = Optional.empty();
        private Optional<String> pathway_id = Optional.empty();

        public ScreenBoxExtendJson setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }

        public ScreenBoxExtendJson setResourceId(String str) {
            this.resource_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceId() {
            return this.resource_id;
        }

        public ScreenBoxExtendJson setCp(String str) {
            this.cp = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCp() {
            return this.cp;
        }

        public ScreenBoxExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public ScreenBoxExtendJson setMediaType(String str) {
            this.media_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMediaType() {
            return this.media_type;
        }

        public ScreenBoxExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }

        @Deprecated
        public ScreenBoxExtendJson setPlayType(String str) {
            this.play_type = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getPlayType() {
            return this.play_type;
        }

        public ScreenBoxExtendJson setDuration(long j) {
            this.duration = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDuration() {
            return this.duration;
        }

        @Deprecated
        public ScreenBoxExtendJson setPosition(long j) {
            this.position = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        @Deprecated
        public Optional<Long> getPosition() {
            return this.position;
        }

        public ScreenBoxExtendJson setCpId(String str) {
            this.cp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpId() {
            return this.cp_id;
        }

        public ScreenBoxExtendJson setCpName(String str) {
            this.cp_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpName() {
            return this.cp_name;
        }

        public ScreenBoxExtendJson setOffset(int i) {
            this.offset = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOffset() {
            return this.offset;
        }

        public ScreenBoxExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public ScreenBoxExtendJson setExpId(String str) {
            this.exp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpId() {
            return this.exp_id;
        }

        public ScreenBoxExtendJson setImpArea(String str) {
            this.imp_area = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getImpArea() {
            return this.imp_area;
        }

        public ScreenBoxExtendJson setContentMode(String str) {
            this.content_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContentMode() {
            return this.content_mode;
        }

        public ScreenBoxExtendJson setControlType(String str) {
            this.control_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getControlType() {
            return this.control_type;
        }

        public ScreenBoxExtendJson setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public ScreenBoxExtendJson setDialogOrigin(String str) {
            this.dialog_origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogOrigin() {
            return this.dialog_origin;
        }

        public ScreenBoxExtendJson setShortvideoType(String str) {
            this.shortvideo_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getShortvideoType() {
            return this.shortvideo_type;
        }

        public ScreenBoxExtendJson setElementId(String str) {
            this.element_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getElementId() {
            return this.element_id;
        }

        public ScreenBoxExtendJson setTraceId(String str) {
            this.trace_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTraceId() {
            return this.trace_id;
        }

        public ScreenBoxExtendJson setTab(String str) {
            this.tab = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTab() {
            return this.tab;
        }

        public ScreenBoxExtendJson setTabOffset(int i) {
            this.tab_offset = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTabOffset() {
            return this.tab_offset;
        }

        public ScreenBoxExtendJson setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public ScreenBoxExtendJson setAudioSource(String str) {
            this.audio_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioSource() {
            return this.audio_source;
        }

        public ScreenBoxExtendJson setPlayStartTime(long j) {
            this.play_start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPlayStartTime() {
            return this.play_start_time;
        }

        public ScreenBoxExtendJson setPlayPosition(long j) {
            this.play_position = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPlayPosition() {
            return this.play_position;
        }

        public ScreenBoxExtendJson setPlayFinishType(ScreenBoxPlayFinishType screenBoxPlayFinishType) {
            this.play_finish_type = Optional.ofNullable(screenBoxPlayFinishType);
            return this;
        }

        public Optional<ScreenBoxPlayFinishType> getPlayFinishType() {
            return this.play_finish_type;
        }

        public ScreenBoxExtendJson setPlayEventType(String str) {
            this.play_event_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPlayEventType() {
            return this.play_event_type;
        }

        public ScreenBoxExtendJson setIsInterrupted(boolean z) {
            this.is_interrupted = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInterrupted() {
            return this.is_interrupted;
        }

        public ScreenBoxExtendJson setMusicCopyrightParty(String str) {
            this.music_copyright_party = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMusicCopyrightParty() {
            return this.music_copyright_party;
        }

        public ScreenBoxExtendJson setMusicCopyrightPartyId(String str) {
            this.music_copyright_party_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMusicCopyrightPartyId() {
            return this.music_copyright_party_id;
        }

        public ScreenBoxExtendJson setAlbumId(String str) {
            this.album_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlbumId() {
            return this.album_id;
        }

        public ScreenBoxExtendJson setEpisodeIndex(int i) {
            this.episode_index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisodeIndex() {
            return this.episode_index;
        }

        public ScreenBoxExtendJson setQueryString(String str) {
            this.query_string = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryString() {
            return this.query_string;
        }

        public ScreenBoxExtendJson setStatus(ScreenBoxStatus screenBoxStatus) {
            this.status = Optional.ofNullable(screenBoxStatus);
            return this;
        }

        public Optional<ScreenBoxStatus> getStatus() {
            return this.status;
        }

        public ScreenBoxExtendJson setExtendType(ScreenBoxExtendType screenBoxExtendType) {
            this.extend_type = Optional.ofNullable(screenBoxExtendType);
            return this;
        }

        public Optional<ScreenBoxExtendType> getExtendType() {
            return this.extend_type;
        }

        public ScreenBoxExtendJson setDebugInfo(String str) {
            this.debug_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDebugInfo() {
            return this.debug_info;
        }

        public ScreenBoxExtendJson setQuerySource(String str) {
            this.query_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuerySource() {
            return this.query_source;
        }

        public ScreenBoxExtendJson setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public ScreenBoxExtendJson setQueryText(String str) {
            this.query_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryText() {
            return this.query_text;
        }

        public ScreenBoxExtendJson setSendQuery(String str) {
            this.send_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSendQuery() {
            return this.send_query;
        }

        public ScreenBoxExtendJson setQueryId(String str) {
            this.query_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryId() {
            return this.query_id;
        }

        public ScreenBoxExtendJson setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public ScreenBoxExtendJson setIsCutIn(boolean z) {
            this.is_cut_in = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCutIn() {
            return this.is_cut_in;
        }

        public ScreenBoxExtendJson setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public ScreenBoxExtendJson setPlayEndTime(long j) {
            this.play_end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getPlayEndTime() {
            return this.play_end_time;
        }

        public ScreenBoxExtendJson setLogExtension(ObjectNode objectNode) {
            this.log_extension = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLogExtension() {
            return this.log_extension;
        }

        public ScreenBoxExtendJson setCpAlbumId(String str) {
            this.cp_album_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpAlbumId() {
            return this.cp_album_id;
        }

        public ScreenBoxExtendJson setSwitchType(String str) {
            this.switch_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSwitchType() {
            return this.switch_type;
        }

        public ScreenBoxExtendJson setAudioType(String str) {
            this.audio_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioType() {
            return this.audio_type;
        }

        public ScreenBoxExtendJson setButtonContent(String str) {
            this.button_content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getButtonContent() {
            return this.button_content;
        }

        public ScreenBoxExtendJson setProduct(String str) {
            this.product = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getProduct() {
            return this.product;
        }

        public ScreenBoxExtendJson setPrice(double d) {
            this.price = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getPrice() {
            return this.price;
        }

        public ScreenBoxExtendJson setRecallType(String str) {
            this.recall_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRecallType() {
            return this.recall_type;
        }

        public ScreenBoxExtendJson setSubTab(String str) {
            this.sub_tab = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSubTab() {
            return this.sub_tab;
        }

        public ScreenBoxExtendJson setModuleName(String str) {
            this.module_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModuleName() {
            return this.module_name;
        }

        public ScreenBoxExtendJson setPageType(String str) {
            this.page_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageType() {
            return this.page_type;
        }

        public ScreenBoxExtendJson setFloatType(String str) {
            this.float_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFloatType() {
            return this.float_type;
        }

        public ScreenBoxExtendJson setIsTrue(boolean z) {
            this.is_true = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isTrue() {
            return this.is_true;
        }

        public ScreenBoxExtendJson setExitType(String str) {
            this.exit_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExitType() {
            return this.exit_type;
        }

        public ScreenBoxExtendJson setErrorCode(String str) {
            this.error_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorCode() {
            return this.error_code;
        }

        public ScreenBoxExtendJson setPathwayId(String str) {
            this.pathway_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPathwayId() {
            return this.pathway_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class ScreenBoxTrackLog {
        @Required
        private ScreenBoxEventParams event_params;
        @Required
        private ScreenBoxEventType event_type;

        public ScreenBoxTrackLog() {
        }

        public ScreenBoxTrackLog(ScreenBoxEventType screenBoxEventType, ScreenBoxEventParams screenBoxEventParams) {
            this.event_type = screenBoxEventType;
            this.event_params = screenBoxEventParams;
        }

        @Required
        public ScreenBoxTrackLog setEventType(ScreenBoxEventType screenBoxEventType) {
            this.event_type = screenBoxEventType;
            return this;
        }

        @Required
        public ScreenBoxEventType getEventType() {
            return this.event_type;
        }

        @Required
        public ScreenBoxTrackLog setEventParams(ScreenBoxEventParams screenBoxEventParams) {
            this.event_params = screenBoxEventParams;
            return this;
        }

        @Required
        public ScreenBoxEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class TrackLog {
        @Required
        private ObjectNode data;
        @Required
        private DataType data_type;

        public TrackLog() {
        }

        public TrackLog(DataType dataType, ObjectNode objectNode) {
            this.data_type = dataType;
            this.data = objectNode;
        }

        @Required
        public TrackLog setDataType(DataType dataType) {
            this.data_type = dataType;
            return this;
        }

        @Required
        public DataType getDataType() {
            return this.data_type;
        }

        @Required
        public TrackLog setData(ObjectNode objectNode) {
            this.data = objectNode;
            return this;
        }

        @Required
        public ObjectNode getData() {
            return this.data;
        }
    }

    /* loaded from: classes3.dex */
    public static class TvEventContext {
        private Optional<String> device_id = Optional.empty();
        private Optional<String> platform_id = Optional.empty();
        private Optional<String> uid = Optional.empty();
        private Optional<String> app_id = Optional.empty();
        private Optional<String> miai_version = Optional.empty();
        private Optional<String> wakeup_version = Optional.empty();
        private Optional<String> wakeup_channel = Optional.empty();
        private Optional<String> patchwall_version = Optional.empty();
        private Optional<Integer> operator = Optional.empty();
        private Optional<String> source = Optional.empty();
        private Optional<String> mode = Optional.empty();
        private Optional<TvUserMode> user_mode = Optional.empty();
        private Optional<Boolean> is_continuous_dialog = Optional.empty();
        private Optional<TvNetworkStatus> network_status = Optional.empty();
        private Optional<String> env = Optional.empty();
        private Optional<Boolean> is_soundbox_device = Optional.empty();

        public TvEventContext setDeviceId(String str) {
            this.device_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceId() {
            return this.device_id;
        }

        public TvEventContext setPlatformId(String str) {
            this.platform_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPlatformId() {
            return this.platform_id;
        }

        public TvEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public TvEventContext setAppId(String str) {
            this.app_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppId() {
            return this.app_id;
        }

        public TvEventContext setMiaiVersion(String str) {
            this.miai_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMiaiVersion() {
            return this.miai_version;
        }

        public TvEventContext setWakeupVersion(String str) {
            this.wakeup_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupVersion() {
            return this.wakeup_version;
        }

        public TvEventContext setWakeupChannel(String str) {
            this.wakeup_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupChannel() {
            return this.wakeup_channel;
        }

        public TvEventContext setPatchwallVersion(String str) {
            this.patchwall_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPatchwallVersion() {
            return this.patchwall_version;
        }

        public TvEventContext setOperator(int i) {
            this.operator = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getOperator() {
            return this.operator;
        }

        public TvEventContext setSource(String str) {
            this.source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSource() {
            return this.source;
        }

        public TvEventContext setMode(String str) {
            this.mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMode() {
            return this.mode;
        }

        public TvEventContext setUserMode(TvUserMode tvUserMode) {
            this.user_mode = Optional.ofNullable(tvUserMode);
            return this;
        }

        public Optional<TvUserMode> getUserMode() {
            return this.user_mode;
        }

        public TvEventContext setIsContinuousDialog(boolean z) {
            this.is_continuous_dialog = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isContinuousDialog() {
            return this.is_continuous_dialog;
        }

        public TvEventContext setNetworkStatus(TvNetworkStatus tvNetworkStatus) {
            this.network_status = Optional.ofNullable(tvNetworkStatus);
            return this;
        }

        public Optional<TvNetworkStatus> getNetworkStatus() {
            return this.network_status;
        }

        public TvEventContext setEnv(String str) {
            this.env = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEnv() {
            return this.env;
        }

        public TvEventContext setIsSoundboxDevice(boolean z) {
            this.is_soundbox_device = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSoundboxDevice() {
            return this.is_soundbox_device;
        }
    }

    /* loaded from: classes3.dex */
    public static class TvEventParams {
        @Required
        private TvEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<TvExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public TvEventParams() {
        }

        public TvEventParams(String str, TvEventContext tvEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = tvEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public TvEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public TvEventParams setEventContext(TvEventContext tvEventContext) {
            this.event_context = tvEventContext;
            return this;
        }

        @Required
        public TvEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public TvEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public TvEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public TvEventParams setExtendJson(TvExtendJson tvExtendJson) {
            this.extend_json = Optional.ofNullable(tvExtendJson);
            return this;
        }

        public Optional<TvExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class TvExtendJson {
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> extend_type = Optional.empty();
        private Optional<Long> start_time = Optional.empty();
        private Optional<Boolean> is_interrupted = Optional.empty();
        private Optional<String> click_type = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> media_id = Optional.empty();
        private Optional<String> media_name = Optional.empty();
        private Optional<String> from_type = Optional.empty();
        private Optional<String> instruction_id = Optional.empty();
        private Optional<String> cp = Optional.empty();
        private Optional<String> audio_id = Optional.empty();
        private Optional<Boolean> is_accurate = Optional.empty();
        private Optional<String> eid = Optional.empty();
        private Optional<String> audio_type = Optional.empty();
        private Optional<String> resource_cp = Optional.empty();
        private Optional<String> resource_id = Optional.empty();
        private Optional<String> meta_id = Optional.empty();
        private Optional<String> grade = Optional.empty();
        private Optional<String> intention = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<Long> duration = Optional.empty();
        private Optional<String> switch_type = Optional.empty();
        private Optional<String> mode = Optional.empty();
        private Optional<String> action_type = Optional.empty();
        private Optional<String> request_type = Optional.empty();
        private Optional<Integer> current_episode = Optional.empty();
        private Optional<Long> length = Optional.empty();
        private Optional<Double> played_ratio = Optional.empty();
        private Optional<String> event_id = Optional.empty();
        private Optional<String> tab_name = Optional.empty();
        private Optional<TvExitType> exit_type = Optional.empty();
        private Optional<TvButton> button = Optional.empty();
        private Optional<String> wakeup_channel = Optional.empty();
        private Optional<TvLanguageType> language_type = Optional.empty();
        private Optional<Boolean> mute_music_at_slient = Optional.empty();
        private Optional<Integer> screen_resolution = Optional.empty();
        private Optional<Integer> screen_luminance = Optional.empty();
        private Optional<String> app_name = Optional.empty();
        private Optional<String> app_page = Optional.empty();
        private Optional<String> query_source = Optional.empty();
        private Optional<List<String>> exposure_queries = Optional.empty();
        private Optional<TvGuidanceType> guidance_type = Optional.empty();
        private Optional<Integer> time_cost = Optional.empty();
        private Optional<Integer> max_wait_time = Optional.empty();
        private Optional<String> card_type = Optional.empty();
        private Optional<String> debug_info = Optional.empty();
        private Optional<Integer> query_version = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<String> query_text = Optional.empty();
        private Optional<String> send_query = Optional.empty();
        private Optional<String> query_id = Optional.empty();
        private Optional<Boolean> is_mobile_app_advertise = Optional.empty();
        private Optional<String> domain = Optional.empty();
        private Optional<ObjectNode> log_extension = Optional.empty();
        private Optional<String> status = Optional.empty();
        private Optional<String> error_code = Optional.empty();
        private Optional<String> package_name = Optional.empty();
        private Optional<String> button_type = Optional.empty();
        private Optional<String> result_type = Optional.empty();
        private Optional<String> page_type = Optional.empty();
        private Optional<String> end_percent = Optional.empty();
        private Optional<String> start_percent = Optional.empty();
        private Optional<String> origin = Optional.empty();
        private Optional<String> target = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> from_page = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<Boolean> is_historic = Optional.empty();
        private Optional<String> pay_type = Optional.empty();
        private Optional<String> present_type = Optional.empty();
        private Optional<Long> ad_time = Optional.empty();
        private Optional<String> request_id = Optional.empty();
        private Optional<String> module_name = Optional.empty();
        private Optional<String> uuid = Optional.empty();
        private Optional<Boolean> is_choose = Optional.empty();
        private Optional<String> user_content = Optional.empty();
        private Optional<String> happen_time = Optional.empty();
        private Optional<String> tel = Optional.empty();
        private Optional<String> wakeup_origin = Optional.empty();
        private Optional<Boolean> is_boot = Optional.empty();
        private Optional<String> click_content = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<Integer> num = Optional.empty();
        private Optional<Long> interrupt_second = Optional.empty();
        private Optional<String> interrupt_type = Optional.empty();
        private Optional<String> mismatch_type = Optional.empty();
        private Optional<String> feedback_id = Optional.empty();

        public TvExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public TvExtendJson setExtendType(String str) {
            this.extend_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExtendType() {
            return this.extend_type;
        }

        public TvExtendJson setStartTime(long j) {
            this.start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getStartTime() {
            return this.start_time;
        }

        public TvExtendJson setIsInterrupted(boolean z) {
            this.is_interrupted = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInterrupted() {
            return this.is_interrupted;
        }

        public TvExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }

        public TvExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public TvExtendJson setMediaId(String str) {
            this.media_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMediaId() {
            return this.media_id;
        }

        public TvExtendJson setMediaName(String str) {
            this.media_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMediaName() {
            return this.media_name;
        }

        public TvExtendJson setFromType(String str) {
            this.from_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFromType() {
            return this.from_type;
        }

        public TvExtendJson setInstructionId(String str) {
            this.instruction_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInstructionId() {
            return this.instruction_id;
        }

        public TvExtendJson setCp(String str) {
            this.cp = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCp() {
            return this.cp;
        }

        public TvExtendJson setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public TvExtendJson setIsAccurate(boolean z) {
            this.is_accurate = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAccurate() {
            return this.is_accurate;
        }

        public TvExtendJson setEid(String str) {
            this.eid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEid() {
            return this.eid;
        }

        public TvExtendJson setAudioType(String str) {
            this.audio_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioType() {
            return this.audio_type;
        }

        public TvExtendJson setResourceCp(String str) {
            this.resource_cp = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceCp() {
            return this.resource_cp;
        }

        public TvExtendJson setResourceId(String str) {
            this.resource_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceId() {
            return this.resource_id;
        }

        public TvExtendJson setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }

        public TvExtendJson setGrade(String str) {
            this.grade = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGrade() {
            return this.grade;
        }

        public TvExtendJson setIntention(String str) {
            this.intention = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIntention() {
            return this.intention;
        }

        public TvExtendJson setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public TvExtendJson setDuration(long j) {
            this.duration = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDuration() {
            return this.duration;
        }

        public TvExtendJson setSwitchType(String str) {
            this.switch_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSwitchType() {
            return this.switch_type;
        }

        public TvExtendJson setMode(String str) {
            this.mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMode() {
            return this.mode;
        }

        public TvExtendJson setActionType(String str) {
            this.action_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getActionType() {
            return this.action_type;
        }

        public TvExtendJson setRequestType(String str) {
            this.request_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRequestType() {
            return this.request_type;
        }

        public TvExtendJson setCurrentEpisode(int i) {
            this.current_episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCurrentEpisode() {
            return this.current_episode;
        }

        public TvExtendJson setLength(long j) {
            this.length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLength() {
            return this.length;
        }

        public TvExtendJson setPlayedRatio(double d) {
            this.played_ratio = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getPlayedRatio() {
            return this.played_ratio;
        }

        public TvExtendJson setEventId(String str) {
            this.event_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEventId() {
            return this.event_id;
        }

        public TvExtendJson setTabName(String str) {
            this.tab_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTabName() {
            return this.tab_name;
        }

        public TvExtendJson setExitType(TvExitType tvExitType) {
            this.exit_type = Optional.ofNullable(tvExitType);
            return this;
        }

        public Optional<TvExitType> getExitType() {
            return this.exit_type;
        }

        public TvExtendJson setButton(TvButton tvButton) {
            this.button = Optional.ofNullable(tvButton);
            return this;
        }

        public Optional<TvButton> getButton() {
            return this.button;
        }

        public TvExtendJson setWakeupChannel(String str) {
            this.wakeup_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupChannel() {
            return this.wakeup_channel;
        }

        public TvExtendJson setLanguageType(TvLanguageType tvLanguageType) {
            this.language_type = Optional.ofNullable(tvLanguageType);
            return this;
        }

        public Optional<TvLanguageType> getLanguageType() {
            return this.language_type;
        }

        public TvExtendJson setMuteMusicAtSlient(boolean z) {
            this.mute_music_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteMusicAtSlient() {
            return this.mute_music_at_slient;
        }

        public TvExtendJson setScreenResolution(int i) {
            this.screen_resolution = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenResolution() {
            return this.screen_resolution;
        }

        public TvExtendJson setScreenLuminance(int i) {
            this.screen_luminance = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenLuminance() {
            return this.screen_luminance;
        }

        public TvExtendJson setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public TvExtendJson setAppPage(String str) {
            this.app_page = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppPage() {
            return this.app_page;
        }

        public TvExtendJson setQuerySource(String str) {
            this.query_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuerySource() {
            return this.query_source;
        }

        public TvExtendJson setExposureQueries(List<String> list) {
            this.exposure_queries = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getExposureQueries() {
            return this.exposure_queries;
        }

        public TvExtendJson setGuidanceType(TvGuidanceType tvGuidanceType) {
            this.guidance_type = Optional.ofNullable(tvGuidanceType);
            return this;
        }

        public Optional<TvGuidanceType> getGuidanceType() {
            return this.guidance_type;
        }

        public TvExtendJson setTimeCost(int i) {
            this.time_cost = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTimeCost() {
            return this.time_cost;
        }

        public TvExtendJson setMaxWaitTime(int i) {
            this.max_wait_time = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMaxWaitTime() {
            return this.max_wait_time;
        }

        public TvExtendJson setCardType(String str) {
            this.card_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardType() {
            return this.card_type;
        }

        public TvExtendJson setDebugInfo(String str) {
            this.debug_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDebugInfo() {
            return this.debug_info;
        }

        public TvExtendJson setQueryVersion(int i) {
            this.query_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getQueryVersion() {
            return this.query_version;
        }

        public TvExtendJson setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public TvExtendJson setQueryText(String str) {
            this.query_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryText() {
            return this.query_text;
        }

        public TvExtendJson setSendQuery(String str) {
            this.send_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSendQuery() {
            return this.send_query;
        }

        public TvExtendJson setQueryId(String str) {
            this.query_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryId() {
            return this.query_id;
        }

        public TvExtendJson setIsMobileAppAdvertise(boolean z) {
            this.is_mobile_app_advertise = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMobileAppAdvertise() {
            return this.is_mobile_app_advertise;
        }

        public TvExtendJson setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public TvExtendJson setLogExtension(ObjectNode objectNode) {
            this.log_extension = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLogExtension() {
            return this.log_extension;
        }

        public TvExtendJson setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public TvExtendJson setErrorCode(String str) {
            this.error_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorCode() {
            return this.error_code;
        }

        public TvExtendJson setPackageName(String str) {
            this.package_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPackageName() {
            return this.package_name;
        }

        public TvExtendJson setButtonType(String str) {
            this.button_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getButtonType() {
            return this.button_type;
        }

        public TvExtendJson setResultType(String str) {
            this.result_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultType() {
            return this.result_type;
        }

        public TvExtendJson setPageType(String str) {
            this.page_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageType() {
            return this.page_type;
        }

        public TvExtendJson setEndPercent(String str) {
            this.end_percent = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndPercent() {
            return this.end_percent;
        }

        public TvExtendJson setStartPercent(String str) {
            this.start_percent = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStartPercent() {
            return this.start_percent;
        }

        public TvExtendJson setOrigin(String str) {
            this.origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOrigin() {
            return this.origin;
        }

        public TvExtendJson setTarget(String str) {
            this.target = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTarget() {
            return this.target;
        }

        public TvExtendJson setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public TvExtendJson setFromPage(String str) {
            this.from_page = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFromPage() {
            return this.from_page;
        }

        public TvExtendJson setTitle(String str) {
            this.title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTitle() {
            return this.title;
        }

        public TvExtendJson setIsHistoric(boolean z) {
            this.is_historic = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHistoric() {
            return this.is_historic;
        }

        public TvExtendJson setPayType(String str) {
            this.pay_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPayType() {
            return this.pay_type;
        }

        public TvExtendJson setPresentType(String str) {
            this.present_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPresentType() {
            return this.present_type;
        }

        public TvExtendJson setAdTime(long j) {
            this.ad_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getAdTime() {
            return this.ad_time;
        }

        public TvExtendJson setRequestId(String str) {
            this.request_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRequestId() {
            return this.request_id;
        }

        public TvExtendJson setModuleName(String str) {
            this.module_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModuleName() {
            return this.module_name;
        }

        public TvExtendJson setUuid(String str) {
            this.uuid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUuid() {
            return this.uuid;
        }

        public TvExtendJson setIsChoose(boolean z) {
            this.is_choose = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isChoose() {
            return this.is_choose;
        }

        public TvExtendJson setUserContent(String str) {
            this.user_content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUserContent() {
            return this.user_content;
        }

        public TvExtendJson setHappenTime(String str) {
            this.happen_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHappenTime() {
            return this.happen_time;
        }

        public TvExtendJson setTel(String str) {
            this.tel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTel() {
            return this.tel;
        }

        public TvExtendJson setWakeupOrigin(String str) {
            this.wakeup_origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupOrigin() {
            return this.wakeup_origin;
        }

        public TvExtendJson setIsBoot(boolean z) {
            this.is_boot = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isBoot() {
            return this.is_boot;
        }

        public TvExtendJson setClickContent(String str) {
            this.click_content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickContent() {
            return this.click_content;
        }

        public TvExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public TvExtendJson setNum(int i) {
            this.num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNum() {
            return this.num;
        }

        public TvExtendJson setInterruptSecond(long j) {
            this.interrupt_second = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getInterruptSecond() {
            return this.interrupt_second;
        }

        public TvExtendJson setInterruptType(String str) {
            this.interrupt_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInterruptType() {
            return this.interrupt_type;
        }

        public TvExtendJson setMismatchType(String str) {
            this.mismatch_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMismatchType() {
            return this.mismatch_type;
        }

        public TvExtendJson setFeedbackId(String str) {
            this.feedback_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFeedbackId() {
            return this.feedback_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class TvTrackLog {
        @Required
        private TvEventParams event_params;
        @Required
        private TvEventType event_type;

        public TvTrackLog() {
        }

        public TvTrackLog(TvEventType tvEventType, TvEventParams tvEventParams) {
            this.event_type = tvEventType;
            this.event_params = tvEventParams;
        }

        @Required
        public TvTrackLog setEventType(TvEventType tvEventType) {
            this.event_type = tvEventType;
            return this;
        }

        @Required
        public TvEventType getEventType() {
            return this.event_type;
        }

        @Required
        public TvTrackLog setEventParams(TvEventParams tvEventParams) {
            this.event_params = tvEventParams;
            return this;
        }

        @Required
        public TvEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class VaEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        private Optional<String> bt_device_mac_address = Optional.empty();
        private Optional<String> bt_device_name = Optional.empty();
        private Optional<String> uid = Optional.empty();
        private Optional<String> network_status = Optional.empty();
        private Optional<String> rom_version = Optional.empty();
        private Optional<String> device_model = Optional.empty();
        private Optional<String> miai_version = Optional.empty();
        private Optional<String> build_versioncode = Optional.empty();
        private Optional<Boolean> channel_rom_version = Optional.empty();
        private Optional<String> device_model_codename = Optional.empty();
        private Optional<String> android_version = Optional.empty();
        private Optional<String> android_sdk_version = Optional.empty();
        private Optional<String> upgrade_channel = Optional.empty();
        private Optional<String> oaid = Optional.empty();
        private Optional<Boolean> is_lock_screen = Optional.empty();
        private Optional<Integer> silent_mode = Optional.empty();
        private Optional<Boolean> mute_music_at_slient = Optional.empty();
        private Optional<Boolean> mute_voiceassist_at_slient = Optional.empty();
        private Optional<Integer> electric_quantity = Optional.empty();
        private Optional<Integer> screen_resolution = Optional.empty();
        private Optional<Integer> screen_luminance = Optional.empty();
        private Optional<String> language_type = Optional.empty();
        private Optional<String> network_company = Optional.empty();
        private Optional<String> wakeup_id = Optional.empty();

        public VaEventContext() {
        }

        public VaEventContext(String str, String str2) {
            this.device_id = str;
            this.app_id = str2;
        }

        @Required
        public VaEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public VaEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        public VaEventContext setBtDeviceMacAddress(String str) {
            this.bt_device_mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceMacAddress() {
            return this.bt_device_mac_address;
        }

        public VaEventContext setBtDeviceName(String str) {
            this.bt_device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceName() {
            return this.bt_device_name;
        }

        public VaEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public VaEventContext setNetworkStatus(String str) {
            this.network_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkStatus() {
            return this.network_status;
        }

        public VaEventContext setRomVersion(String str) {
            this.rom_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRomVersion() {
            return this.rom_version;
        }

        public VaEventContext setDeviceModel(String str) {
            this.device_model = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModel() {
            return this.device_model;
        }

        public VaEventContext setMiaiVersion(String str) {
            this.miai_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMiaiVersion() {
            return this.miai_version;
        }

        public VaEventContext setBuildVersioncode(String str) {
            this.build_versioncode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBuildVersioncode() {
            return this.build_versioncode;
        }

        public VaEventContext setChannelRomVersion(boolean z) {
            this.channel_rom_version = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isChannelRomVersion() {
            return this.channel_rom_version;
        }

        public VaEventContext setDeviceModelCodename(String str) {
            this.device_model_codename = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModelCodename() {
            return this.device_model_codename;
        }

        public VaEventContext setAndroidVersion(String str) {
            this.android_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidVersion() {
            return this.android_version;
        }

        public VaEventContext setAndroidSdkVersion(String str) {
            this.android_sdk_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidSdkVersion() {
            return this.android_sdk_version;
        }

        public VaEventContext setUpgradeChannel(String str) {
            this.upgrade_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUpgradeChannel() {
            return this.upgrade_channel;
        }

        public VaEventContext setOaid(String str) {
            this.oaid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOaid() {
            return this.oaid;
        }

        public VaEventContext setIsLockScreen(boolean z) {
            this.is_lock_screen = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLockScreen() {
            return this.is_lock_screen;
        }

        public VaEventContext setSilentMode(int i) {
            this.silent_mode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSilentMode() {
            return this.silent_mode;
        }

        public VaEventContext setMuteMusicAtSlient(boolean z) {
            this.mute_music_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteMusicAtSlient() {
            return this.mute_music_at_slient;
        }

        public VaEventContext setMuteVoiceassistAtSlient(boolean z) {
            this.mute_voiceassist_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteVoiceassistAtSlient() {
            return this.mute_voiceassist_at_slient;
        }

        public VaEventContext setElectricQuantity(int i) {
            this.electric_quantity = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElectricQuantity() {
            return this.electric_quantity;
        }

        public VaEventContext setScreenResolution(int i) {
            this.screen_resolution = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenResolution() {
            return this.screen_resolution;
        }

        public VaEventContext setScreenLuminance(int i) {
            this.screen_luminance = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenLuminance() {
            return this.screen_luminance;
        }

        public VaEventContext setLanguageType(String str) {
            this.language_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLanguageType() {
            return this.language_type;
        }

        public VaEventContext setNetworkCompany(String str) {
            this.network_company = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkCompany() {
            return this.network_company;
        }

        public VaEventContext setWakeupId(String str) {
            this.wakeup_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupId() {
            return this.wakeup_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class VaEventParams {
        @Required
        private VaEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<ObjectNode> extend_json = Optional.empty();
        private Optional<VaExtendJson> extend_json_v3 = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public VaEventParams() {
        }

        public VaEventParams(String str, String str2, long j, VaEventContext vaEventContext) {
            this.widget = str;
            this.event_data_type = str2;
            this.timestamp = j;
            this.event_context = vaEventContext;
        }

        @Required
        public VaEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public VaEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public VaEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        @Required
        public VaEventParams setEventContext(VaEventContext vaEventContext) {
            this.event_context = vaEventContext;
            return this;
        }

        @Required
        public VaEventContext getEventContext() {
            return this.event_context;
        }

        public VaEventParams setExtendJson(ObjectNode objectNode) {
            this.extend_json = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getExtendJson() {
            return this.extend_json;
        }

        public VaEventParams setExtendJsonV3(VaExtendJson vaExtendJson) {
            this.extend_json_v3 = Optional.ofNullable(vaExtendJson);
            return this;
        }

        public Optional<VaExtendJson> getExtendJsonV3() {
            return this.extend_json_v3;
        }
    }

    /* loaded from: classes3.dex */
    public static class VaExtendJson {
        private Optional<String> from = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<Integer> status = Optional.empty();
        private Optional<Integer> page_num = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> extend_type = Optional.empty();
        private Optional<String> card_id = Optional.empty();
        private Optional<String> source_type = Optional.empty();
        private Optional<Boolean> is_lock_screen = Optional.empty();
        private Optional<Boolean> is_long_click = Optional.empty();
        private Optional<String> landing_page = Optional.empty();
        private Optional<String> template_name = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> instruction_id = Optional.empty();
        private Optional<String> cp = Optional.empty();
        private Optional<String> resource_id = Optional.empty();
        private Optional<String> meta_id = Optional.empty();
        private Optional<String> eid = Optional.empty();
        private Optional<String> query = Optional.empty();
        private Optional<String> home_expose_list = Optional.empty();
        private Optional<Integer> item_position = Optional.empty();
        private Optional<Long> length = Optional.empty();
        private Optional<Integer> response_code = Optional.empty();
        private Optional<String> param_query_edit_origin = Optional.empty();
        private Optional<String> param_query_edit_done = Optional.empty();
        private Optional<String> request_id = Optional.empty();
        private Optional<String> query_origin = Optional.empty();
        private Optional<String> package_name = Optional.empty();
        private Optional<String> result_type = Optional.empty();
        private Optional<Boolean> is_used = Optional.empty();
        private Optional<Integer> index = Optional.empty();
        private Optional<Boolean> is_last = Optional.empty();
        private Optional<String> session_id = Optional.empty();
        private Optional<Boolean> is_empty = Optional.empty();
        private Optional<Long> time = Optional.empty();
        private Optional<String> flag = Optional.empty();
        private Optional<Integer> is_requesting = Optional.empty();
        private Optional<Boolean> is_aec_mode = Optional.empty();
        private Optional<Boolean> is_full_duplex = Optional.empty();
        private Optional<Boolean> is_answer = Optional.empty();
        private Optional<Boolean> is_interrupted = Optional.empty();
        private Optional<String> query_text = Optional.empty();
        private Optional<String> query_id = Optional.empty();
        private Optional<String> debug_info = Optional.empty();
        private Optional<String> app_name = Optional.empty();
        private Optional<String> slide_type = Optional.empty();
        private Optional<String> network_company = Optional.empty();
        private Optional<String> result_list = Optional.empty();
        private Optional<Integer> cp_position = Optional.empty();
        private Optional<String> instruction_name = Optional.empty();
        private Optional<Long> create_time = Optional.empty();
        private Optional<String> message = Optional.empty();
        private Optional<Boolean> is_dialing = Optional.empty();
        private Optional<Boolean> is_2g = Optional.empty();
        private Optional<String> key_phrase = Optional.empty();
        private Optional<String> voicetrigger_version_name = Optional.empty();
        private Optional<String> voicetrigger_version_code = Optional.empty();
        private Optional<Integer> voiceprint_switch_status = Optional.empty();
        private Optional<String> voicetrigger_switch_status = Optional.empty();
        private Optional<String> sensitivity = Optional.empty();
        private Optional<String> browser_versioncode = Optional.empty();
        private Optional<Boolean> is_device_supported = Optional.empty();
        private Optional<Integer> electric_quantity = Optional.empty();
        private Optional<Integer> screen_resolution = Optional.empty();
        private Optional<Integer> screen_luminance = Optional.empty();
        private Optional<String> language_type = Optional.empty();
        private Optional<String> activity_id = Optional.empty();
        private Optional<String> sec_status = Optional.empty();
        private Optional<Integer> is_popup_option = Optional.empty();
        private Optional<Integer> silent_mode = Optional.empty();
        private Optional<Boolean> mute_music_at_slient = Optional.empty();
        private Optional<Boolean> mute_voiceassist_at_slient = Optional.empty();
        private Optional<Boolean> is_headline = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<String> card_type = Optional.empty();
        private Optional<String> optional_info = Optional.empty();
        private Optional<Boolean> is_2m = Optional.empty();
        private Optional<Boolean> is_highlight = Optional.empty();
        private Optional<String> android_component_name = Optional.empty();
        private Optional<String> url = Optional.empty();
        private Optional<Boolean> is_vertical = Optional.empty();
        private Optional<Integer> num = Optional.empty();
        private Optional<Long> create_done_time = Optional.empty();
        private Optional<Long> process_start_time = Optional.empty();
        private Optional<Long> process_end_time = Optional.empty();
        private Optional<String> result = Optional.empty();
        private Optional<String> operation_state = Optional.empty();
        private Optional<String> final_execute_node_name = Optional.empty();
        private Optional<Boolean> auto_mic = Optional.empty();
        private Optional<String> group_id = Optional.empty();
        private Optional<String> rpk_packagename = Optional.empty();
        private Optional<String> rpk_version_code = Optional.empty();
        private Optional<String> rpk_version_name = Optional.empty();
        private Optional<String> share_content = Optional.empty();
        private Optional<Integer> is_selected_all = Optional.empty();
        private Optional<String> local_info = Optional.empty();
        private Optional<String> online_info = Optional.empty();
        private Optional<String> artist = Optional.empty();
        private Optional<String> song = Optional.empty();
        private Optional<String> album = Optional.empty();
        private Optional<Boolean> is_fav = Optional.empty();
        private Optional<Integer> status_code = Optional.empty();
        private Optional<Integer> responsecode = Optional.empty();
        private Optional<String> expection = Optional.empty();
        private Optional<Boolean> is_calling = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<Integer> list_position = Optional.empty();
        private Optional<String> new_list = Optional.empty();
        private Optional<String> old_list = Optional.empty();
        private Optional<String> page_url = Optional.empty();
        private Optional<Integer> page_id = Optional.empty();
        private Optional<Integer> element_id = Optional.empty();
        private Optional<String> model_name = Optional.empty();
        private Optional<String> key_value = Optional.empty();
        private Optional<Boolean> is_click_to_score = Optional.empty();
        private Optional<String> button = Optional.empty();
        private Optional<String> file_name = Optional.empty();
        private Optional<Integer> file_version = Optional.empty();
        private Optional<String> relation = Optional.empty();
        private Optional<String> entity_id = Optional.empty();
        private Optional<String> cp_id = Optional.empty();
        private Optional<Integer> play_music_error_code = Optional.empty();
        private Optional<String> scan_mode = Optional.empty();
        private Optional<String> scan_id = Optional.empty();
        private Optional<String> vidpid = Optional.empty();
        private Optional<String> mac_address = Optional.empty();
        private Optional<String> bt_device_version = Optional.empty();
        private Optional<String> connect_mode = Optional.empty();
        private Optional<String> appauto_connect_subtype = Optional.empty();
        private Optional<String> connect_id = Optional.empty();
        private Optional<Integer> is_mandatory_upgrade = Optional.empty();
        private Optional<String> click_type = Optional.empty();
        private Optional<String> content_type = Optional.empty();
        private Optional<String> user_defined_dialog_id = Optional.empty();
        private Optional<String> query_from = Optional.empty();
        private Optional<Double> offline_asr_score = Optional.empty();
        private Optional<String> domain = Optional.empty();
        private Optional<String> intention = Optional.empty();
        private Optional<String> page_name = Optional.empty();
        private Optional<String> event_id = Optional.empty();
        private Optional<String> trace_id = Optional.empty();
        private Optional<Boolean> is_appear_ad = Optional.empty();
        private Optional<String> send_query = Optional.empty();
        private Optional<Boolean> is_appear_gross_blowhole = Optional.empty();
        private Optional<String> page_type = Optional.empty();
        private Optional<String> msg_id = Optional.empty();
        private Optional<Double> percentage = Optional.empty();
        private Optional<String> old_version = Optional.empty();
        private Optional<String> new_version = Optional.empty();
        private Optional<String> query_source = Optional.empty();
        private Optional<Boolean> is_mobile_app_advertise = Optional.empty();
        private Optional<String> origin = Optional.empty();
        private Optional<String> target = Optional.empty();
        private Optional<Boolean> is_l2_wakeup_model_pass = Optional.empty();
        private Optional<Boolean> is_l2_voiceprint_model_pass = Optional.empty();
        private Optional<String> scheme = Optional.empty();
        private Optional<String> screen_status = Optional.empty();
        private Optional<String> l2_wakeup_model_version = Optional.empty();
        private Optional<String> l2_wakeup_engine_version = Optional.empty();
        private Optional<String> audio_md5 = Optional.empty();
        private Optional<Boolean> is_exist = Optional.empty();
        private Optional<String> l2_voiceprint_model_version = Optional.empty();
        private Optional<String> l2_voiceprint_engine_version = Optional.empty();
        private Optional<Double> l2_wakeup_score = Optional.empty();
        private Optional<Double> l2_voiceprint_score = Optional.empty();
        private Optional<Double> l1_wakeup_score = Optional.empty();
        private Optional<Double> l1_voiceprint_score = Optional.empty();
        private Optional<Long> start_time = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<Long> l2_wakeup_start = Optional.empty();
        private Optional<Long> l2_wakeup_end = Optional.empty();
        private Optional<Long> l2_voiceprint_start = Optional.empty();
        private Optional<Long> l2_voiceprint_end = Optional.empty();
        private Optional<Long> l2_wakeup_result_start = Optional.empty();
        private Optional<Long> l2_wakeup_result_end = Optional.empty();
        private Optional<Long> l1_wakeup_time = Optional.empty();
        private Optional<Long> l2_wakeup_time = Optional.empty();
        private Optional<Long> l2_wakeup_audio_length = Optional.empty();
        private Optional<String> vendor_version = Optional.empty();
        private Optional<Double> score = Optional.empty();
        private Optional<Double> miai_volume = Optional.empty();
        private Optional<Double> media_volume = Optional.empty();
        private Optional<Double> phone_volume = Optional.empty();
        private Optional<Boolean> is_mute_phone_volume = Optional.empty();
        private Optional<Boolean> is_mute = Optional.empty();
        private Optional<Boolean> is_not_disturb = Optional.empty();
        private Optional<Boolean> is_have_media_play = Optional.empty();
        private Optional<Boolean> is_connect = Optional.empty();

        public VaExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public VaExtendJson setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public VaExtendJson setStatus(int i) {
            this.status = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatus() {
            return this.status;
        }

        public VaExtendJson setPageNum(int i) {
            this.page_num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPageNum() {
            return this.page_num;
        }

        public VaExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public VaExtendJson setExtendType(String str) {
            this.extend_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExtendType() {
            return this.extend_type;
        }

        public VaExtendJson setCardId(String str) {
            this.card_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardId() {
            return this.card_id;
        }

        public VaExtendJson setSourceType(String str) {
            this.source_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSourceType() {
            return this.source_type;
        }

        public VaExtendJson setIsLockScreen(boolean z) {
            this.is_lock_screen = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLockScreen() {
            return this.is_lock_screen;
        }

        public VaExtendJson setIsLongClick(boolean z) {
            this.is_long_click = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLongClick() {
            return this.is_long_click;
        }

        public VaExtendJson setLandingPage(String str) {
            this.landing_page = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLandingPage() {
            return this.landing_page;
        }

        public VaExtendJson setTemplateName(String str) {
            this.template_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTemplateName() {
            return this.template_name;
        }

        public VaExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public VaExtendJson setInstructionId(String str) {
            this.instruction_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInstructionId() {
            return this.instruction_id;
        }

        public VaExtendJson setCp(String str) {
            this.cp = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCp() {
            return this.cp;
        }

        public VaExtendJson setResourceId(String str) {
            this.resource_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceId() {
            return this.resource_id;
        }

        public VaExtendJson setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }

        public VaExtendJson setEid(String str) {
            this.eid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEid() {
            return this.eid;
        }

        public VaExtendJson setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public VaExtendJson setHomeExposeList(String str) {
            this.home_expose_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHomeExposeList() {
            return this.home_expose_list;
        }

        public VaExtendJson setItemPosition(int i) {
            this.item_position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getItemPosition() {
            return this.item_position;
        }

        public VaExtendJson setLength(long j) {
            this.length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getLength() {
            return this.length;
        }

        public VaExtendJson setResponseCode(int i) {
            this.response_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getResponseCode() {
            return this.response_code;
        }

        public VaExtendJson setParamQueryEditOrigin(String str) {
            this.param_query_edit_origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getParamQueryEditOrigin() {
            return this.param_query_edit_origin;
        }

        public VaExtendJson setParamQueryEditDone(String str) {
            this.param_query_edit_done = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getParamQueryEditDone() {
            return this.param_query_edit_done;
        }

        public VaExtendJson setRequestId(String str) {
            this.request_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRequestId() {
            return this.request_id;
        }

        public VaExtendJson setQueryOrigin(String str) {
            this.query_origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryOrigin() {
            return this.query_origin;
        }

        public VaExtendJson setPackageName(String str) {
            this.package_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPackageName() {
            return this.package_name;
        }

        public VaExtendJson setResultType(String str) {
            this.result_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultType() {
            return this.result_type;
        }

        public VaExtendJson setIsUsed(boolean z) {
            this.is_used = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isUsed() {
            return this.is_used;
        }

        public VaExtendJson setIndex(int i) {
            this.index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIndex() {
            return this.index;
        }

        public VaExtendJson setIsLast(boolean z) {
            this.is_last = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLast() {
            return this.is_last;
        }

        public VaExtendJson setSessionId(String str) {
            this.session_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSessionId() {
            return this.session_id;
        }

        public VaExtendJson setIsEmpty(boolean z) {
            this.is_empty = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEmpty() {
            return this.is_empty;
        }

        public VaExtendJson setTime(long j) {
            this.time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getTime() {
            return this.time;
        }

        public VaExtendJson setFlag(String str) {
            this.flag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFlag() {
            return this.flag;
        }

        public VaExtendJson setIsRequesting(int i) {
            this.is_requesting = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIsRequesting() {
            return this.is_requesting;
        }

        public VaExtendJson setIsAecMode(boolean z) {
            this.is_aec_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAecMode() {
            return this.is_aec_mode;
        }

        public VaExtendJson setIsFullDuplex(boolean z) {
            this.is_full_duplex = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFullDuplex() {
            return this.is_full_duplex;
        }

        public VaExtendJson setIsAnswer(boolean z) {
            this.is_answer = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAnswer() {
            return this.is_answer;
        }

        public VaExtendJson setIsInterrupted(boolean z) {
            this.is_interrupted = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInterrupted() {
            return this.is_interrupted;
        }

        public VaExtendJson setQueryText(String str) {
            this.query_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryText() {
            return this.query_text;
        }

        public VaExtendJson setQueryId(String str) {
            this.query_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryId() {
            return this.query_id;
        }

        public VaExtendJson setDebugInfo(String str) {
            this.debug_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDebugInfo() {
            return this.debug_info;
        }

        public VaExtendJson setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public VaExtendJson setSlideType(String str) {
            this.slide_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSlideType() {
            return this.slide_type;
        }

        public VaExtendJson setNetworkCompany(String str) {
            this.network_company = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkCompany() {
            return this.network_company;
        }

        public VaExtendJson setResultList(String str) {
            this.result_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultList() {
            return this.result_list;
        }

        public VaExtendJson setCpPosition(int i) {
            this.cp_position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCpPosition() {
            return this.cp_position;
        }

        public VaExtendJson setInstructionName(String str) {
            this.instruction_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInstructionName() {
            return this.instruction_name;
        }

        public VaExtendJson setCreateTime(long j) {
            this.create_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getCreateTime() {
            return this.create_time;
        }

        public VaExtendJson setMessage(String str) {
            this.message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessage() {
            return this.message;
        }

        public VaExtendJson setIsDialing(boolean z) {
            this.is_dialing = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDialing() {
            return this.is_dialing;
        }

        public VaExtendJson setIs2g(boolean z) {
            this.is_2g = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> is2g() {
            return this.is_2g;
        }

        public VaExtendJson setKeyPhrase(String str) {
            this.key_phrase = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyPhrase() {
            return this.key_phrase;
        }

        public VaExtendJson setVoicetriggerVersionName(String str) {
            this.voicetrigger_version_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVoicetriggerVersionName() {
            return this.voicetrigger_version_name;
        }

        public VaExtendJson setVoicetriggerVersionCode(String str) {
            this.voicetrigger_version_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVoicetriggerVersionCode() {
            return this.voicetrigger_version_code;
        }

        public VaExtendJson setVoiceprintSwitchStatus(int i) {
            this.voiceprint_switch_status = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVoiceprintSwitchStatus() {
            return this.voiceprint_switch_status;
        }

        public VaExtendJson setVoicetriggerSwitchStatus(String str) {
            this.voicetrigger_switch_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVoicetriggerSwitchStatus() {
            return this.voicetrigger_switch_status;
        }

        public VaExtendJson setSensitivity(String str) {
            this.sensitivity = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSensitivity() {
            return this.sensitivity;
        }

        public VaExtendJson setBrowserVersioncode(String str) {
            this.browser_versioncode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBrowserVersioncode() {
            return this.browser_versioncode;
        }

        public VaExtendJson setIsDeviceSupported(boolean z) {
            this.is_device_supported = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDeviceSupported() {
            return this.is_device_supported;
        }

        public VaExtendJson setElectricQuantity(int i) {
            this.electric_quantity = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElectricQuantity() {
            return this.electric_quantity;
        }

        public VaExtendJson setScreenResolution(int i) {
            this.screen_resolution = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenResolution() {
            return this.screen_resolution;
        }

        public VaExtendJson setScreenLuminance(int i) {
            this.screen_luminance = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenLuminance() {
            return this.screen_luminance;
        }

        public VaExtendJson setLanguageType(String str) {
            this.language_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLanguageType() {
            return this.language_type;
        }

        public VaExtendJson setActivityId(String str) {
            this.activity_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getActivityId() {
            return this.activity_id;
        }

        public VaExtendJson setSecStatus(String str) {
            this.sec_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSecStatus() {
            return this.sec_status;
        }

        public VaExtendJson setIsPopupOption(int i) {
            this.is_popup_option = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIsPopupOption() {
            return this.is_popup_option;
        }

        public VaExtendJson setSilentMode(int i) {
            this.silent_mode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSilentMode() {
            return this.silent_mode;
        }

        public VaExtendJson setMuteMusicAtSlient(boolean z) {
            this.mute_music_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteMusicAtSlient() {
            return this.mute_music_at_slient;
        }

        public VaExtendJson setMuteVoiceassistAtSlient(boolean z) {
            this.mute_voiceassist_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteVoiceassistAtSlient() {
            return this.mute_voiceassist_at_slient;
        }

        public VaExtendJson setIsHeadline(boolean z) {
            this.is_headline = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHeadline() {
            return this.is_headline;
        }

        public VaExtendJson setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public VaExtendJson setCardType(String str) {
            this.card_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardType() {
            return this.card_type;
        }

        public VaExtendJson setOptionalInfo(String str) {
            this.optional_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOptionalInfo() {
            return this.optional_info;
        }

        public VaExtendJson setIs2m(boolean z) {
            this.is_2m = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> is2m() {
            return this.is_2m;
        }

        public VaExtendJson setIsHighlight(boolean z) {
            this.is_highlight = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHighlight() {
            return this.is_highlight;
        }

        public VaExtendJson setAndroidComponentName(String str) {
            this.android_component_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidComponentName() {
            return this.android_component_name;
        }

        public VaExtendJson setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public VaExtendJson setIsVertical(boolean z) {
            this.is_vertical = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVertical() {
            return this.is_vertical;
        }

        public VaExtendJson setNum(int i) {
            this.num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNum() {
            return this.num;
        }

        public VaExtendJson setCreateDoneTime(long j) {
            this.create_done_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getCreateDoneTime() {
            return this.create_done_time;
        }

        public VaExtendJson setProcessStartTime(long j) {
            this.process_start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getProcessStartTime() {
            return this.process_start_time;
        }

        public VaExtendJson setProcessEndTime(long j) {
            this.process_end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getProcessEndTime() {
            return this.process_end_time;
        }

        public VaExtendJson setResult(String str) {
            this.result = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResult() {
            return this.result;
        }

        public VaExtendJson setOperationState(String str) {
            this.operation_state = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOperationState() {
            return this.operation_state;
        }

        public VaExtendJson setFinalExecuteNodeName(String str) {
            this.final_execute_node_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFinalExecuteNodeName() {
            return this.final_execute_node_name;
        }

        public VaExtendJson setAutoMic(boolean z) {
            this.auto_mic = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAutoMic() {
            return this.auto_mic;
        }

        public VaExtendJson setGroupId(String str) {
            this.group_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGroupId() {
            return this.group_id;
        }

        public VaExtendJson setRpkPackagename(String str) {
            this.rpk_packagename = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRpkPackagename() {
            return this.rpk_packagename;
        }

        public VaExtendJson setRpkVersionCode(String str) {
            this.rpk_version_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRpkVersionCode() {
            return this.rpk_version_code;
        }

        public VaExtendJson setRpkVersionName(String str) {
            this.rpk_version_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRpkVersionName() {
            return this.rpk_version_name;
        }

        public VaExtendJson setShareContent(String str) {
            this.share_content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getShareContent() {
            return this.share_content;
        }

        public VaExtendJson setIsSelectedAll(int i) {
            this.is_selected_all = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIsSelectedAll() {
            return this.is_selected_all;
        }

        public VaExtendJson setLocalInfo(String str) {
            this.local_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLocalInfo() {
            return this.local_info;
        }

        public VaExtendJson setOnlineInfo(String str) {
            this.online_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOnlineInfo() {
            return this.online_info;
        }

        public VaExtendJson setArtist(String str) {
            this.artist = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArtist() {
            return this.artist;
        }

        public VaExtendJson setSong(String str) {
            this.song = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSong() {
            return this.song;
        }

        public VaExtendJson setAlbum(String str) {
            this.album = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAlbum() {
            return this.album;
        }

        public VaExtendJson setIsFav(boolean z) {
            this.is_fav = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFav() {
            return this.is_fav;
        }

        public VaExtendJson setStatusCode(int i) {
            this.status_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStatusCode() {
            return this.status_code;
        }

        public VaExtendJson setResponsecode(int i) {
            this.responsecode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getResponsecode() {
            return this.responsecode;
        }

        public VaExtendJson setExpection(String str) {
            this.expection = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpection() {
            return this.expection;
        }

        public VaExtendJson setIsCalling(boolean z) {
            this.is_calling = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCalling() {
            return this.is_calling;
        }

        public VaExtendJson setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public VaExtendJson setListPosition(int i) {
            this.list_position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getListPosition() {
            return this.list_position;
        }

        public VaExtendJson setNewList(String str) {
            this.new_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNewList() {
            return this.new_list;
        }

        public VaExtendJson setOldList(String str) {
            this.old_list = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOldList() {
            return this.old_list;
        }

        public VaExtendJson setPageUrl(String str) {
            this.page_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageUrl() {
            return this.page_url;
        }

        public VaExtendJson setPageId(int i) {
            this.page_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPageId() {
            return this.page_id;
        }

        public VaExtendJson setElementId(int i) {
            this.element_id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElementId() {
            return this.element_id;
        }

        public VaExtendJson setModelName(String str) {
            this.model_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModelName() {
            return this.model_name;
        }

        public VaExtendJson setKeyValue(String str) {
            this.key_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyValue() {
            return this.key_value;
        }

        public VaExtendJson setIsClickToScore(boolean z) {
            this.is_click_to_score = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isClickToScore() {
            return this.is_click_to_score;
        }

        public VaExtendJson setButton(String str) {
            this.button = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getButton() {
            return this.button;
        }

        public VaExtendJson setFileName(String str) {
            this.file_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFileName() {
            return this.file_name;
        }

        public VaExtendJson setFileVersion(int i) {
            this.file_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getFileVersion() {
            return this.file_version;
        }

        public VaExtendJson setRelation(String str) {
            this.relation = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRelation() {
            return this.relation;
        }

        public VaExtendJson setEntityId(String str) {
            this.entity_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEntityId() {
            return this.entity_id;
        }

        public VaExtendJson setCpId(String str) {
            this.cp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCpId() {
            return this.cp_id;
        }

        public VaExtendJson setPlayMusicErrorCode(int i) {
            this.play_music_error_code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPlayMusicErrorCode() {
            return this.play_music_error_code;
        }

        public VaExtendJson setScanMode(String str) {
            this.scan_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScanMode() {
            return this.scan_mode;
        }

        public VaExtendJson setScanId(String str) {
            this.scan_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScanId() {
            return this.scan_id;
        }

        public VaExtendJson setVidpid(String str) {
            this.vidpid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVidpid() {
            return this.vidpid;
        }

        public VaExtendJson setMacAddress(String str) {
            this.mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMacAddress() {
            return this.mac_address;
        }

        public VaExtendJson setBtDeviceVersion(String str) {
            this.bt_device_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceVersion() {
            return this.bt_device_version;
        }

        public VaExtendJson setConnectMode(String str) {
            this.connect_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getConnectMode() {
            return this.connect_mode;
        }

        public VaExtendJson setAppautoConnectSubtype(String str) {
            this.appauto_connect_subtype = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppautoConnectSubtype() {
            return this.appauto_connect_subtype;
        }

        public VaExtendJson setConnectId(String str) {
            this.connect_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getConnectId() {
            return this.connect_id;
        }

        public VaExtendJson setIsMandatoryUpgrade(int i) {
            this.is_mandatory_upgrade = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIsMandatoryUpgrade() {
            return this.is_mandatory_upgrade;
        }

        public VaExtendJson setClickType(String str) {
            this.click_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickType() {
            return this.click_type;
        }

        public VaExtendJson setContentType(String str) {
            this.content_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContentType() {
            return this.content_type;
        }

        public VaExtendJson setUserDefinedDialogId(String str) {
            this.user_defined_dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUserDefinedDialogId() {
            return this.user_defined_dialog_id;
        }

        public VaExtendJson setQueryFrom(String str) {
            this.query_from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryFrom() {
            return this.query_from;
        }

        public VaExtendJson setOfflineAsrScore(double d) {
            this.offline_asr_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getOfflineAsrScore() {
            return this.offline_asr_score;
        }

        public VaExtendJson setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public VaExtendJson setIntention(String str) {
            this.intention = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIntention() {
            return this.intention;
        }

        public VaExtendJson setPageName(String str) {
            this.page_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageName() {
            return this.page_name;
        }

        public VaExtendJson setEventId(String str) {
            this.event_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEventId() {
            return this.event_id;
        }

        public VaExtendJson setTraceId(String str) {
            this.trace_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTraceId() {
            return this.trace_id;
        }

        public VaExtendJson setIsAppearAd(boolean z) {
            this.is_appear_ad = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAppearAd() {
            return this.is_appear_ad;
        }

        public VaExtendJson setSendQuery(String str) {
            this.send_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSendQuery() {
            return this.send_query;
        }

        public VaExtendJson setIsAppearGrossBlowhole(boolean z) {
            this.is_appear_gross_blowhole = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAppearGrossBlowhole() {
            return this.is_appear_gross_blowhole;
        }

        public VaExtendJson setPageType(String str) {
            this.page_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPageType() {
            return this.page_type;
        }

        public VaExtendJson setMsgId(String str) {
            this.msg_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMsgId() {
            return this.msg_id;
        }

        public VaExtendJson setPercentage(double d) {
            this.percentage = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getPercentage() {
            return this.percentage;
        }

        public VaExtendJson setOldVersion(String str) {
            this.old_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOldVersion() {
            return this.old_version;
        }

        public VaExtendJson setNewVersion(String str) {
            this.new_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNewVersion() {
            return this.new_version;
        }

        public VaExtendJson setQuerySource(String str) {
            this.query_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuerySource() {
            return this.query_source;
        }

        public VaExtendJson setIsMobileAppAdvertise(boolean z) {
            this.is_mobile_app_advertise = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMobileAppAdvertise() {
            return this.is_mobile_app_advertise;
        }

        public VaExtendJson setOrigin(String str) {
            this.origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOrigin() {
            return this.origin;
        }

        public VaExtendJson setTarget(String str) {
            this.target = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTarget() {
            return this.target;
        }

        public VaExtendJson setIsL2WakeupModelPass(boolean z) {
            this.is_l2_wakeup_model_pass = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isL2WakeupModelPass() {
            return this.is_l2_wakeup_model_pass;
        }

        public VaExtendJson setIsL2VoiceprintModelPass(boolean z) {
            this.is_l2_voiceprint_model_pass = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isL2VoiceprintModelPass() {
            return this.is_l2_voiceprint_model_pass;
        }

        public VaExtendJson setScheme(String str) {
            this.scheme = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScheme() {
            return this.scheme;
        }

        public VaExtendJson setScreenStatus(String str) {
            this.screen_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScreenStatus() {
            return this.screen_status;
        }

        public VaExtendJson setL2WakeupModelVersion(String str) {
            this.l2_wakeup_model_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getL2WakeupModelVersion() {
            return this.l2_wakeup_model_version;
        }

        public VaExtendJson setL2WakeupEngineVersion(String str) {
            this.l2_wakeup_engine_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getL2WakeupEngineVersion() {
            return this.l2_wakeup_engine_version;
        }

        public VaExtendJson setAudioMd5(String str) {
            this.audio_md5 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioMd5() {
            return this.audio_md5;
        }

        public VaExtendJson setIsExist(boolean z) {
            this.is_exist = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isExist() {
            return this.is_exist;
        }

        public VaExtendJson setL2VoiceprintModelVersion(String str) {
            this.l2_voiceprint_model_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getL2VoiceprintModelVersion() {
            return this.l2_voiceprint_model_version;
        }

        public VaExtendJson setL2VoiceprintEngineVersion(String str) {
            this.l2_voiceprint_engine_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getL2VoiceprintEngineVersion() {
            return this.l2_voiceprint_engine_version;
        }

        public VaExtendJson setL2WakeupScore(double d) {
            this.l2_wakeup_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getL2WakeupScore() {
            return this.l2_wakeup_score;
        }

        public VaExtendJson setL2VoiceprintScore(double d) {
            this.l2_voiceprint_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getL2VoiceprintScore() {
            return this.l2_voiceprint_score;
        }

        public VaExtendJson setL1WakeupScore(double d) {
            this.l1_wakeup_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getL1WakeupScore() {
            return this.l1_wakeup_score;
        }

        public VaExtendJson setL1VoiceprintScore(double d) {
            this.l1_voiceprint_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getL1VoiceprintScore() {
            return this.l1_voiceprint_score;
        }

        public VaExtendJson setStartTime(long j) {
            this.start_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getStartTime() {
            return this.start_time;
        }

        public VaExtendJson setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public VaExtendJson setL2WakeupStart(long j) {
            this.l2_wakeup_start = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupStart() {
            return this.l2_wakeup_start;
        }

        public VaExtendJson setL2WakeupEnd(long j) {
            this.l2_wakeup_end = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupEnd() {
            return this.l2_wakeup_end;
        }

        public VaExtendJson setL2VoiceprintStart(long j) {
            this.l2_voiceprint_start = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2VoiceprintStart() {
            return this.l2_voiceprint_start;
        }

        public VaExtendJson setL2VoiceprintEnd(long j) {
            this.l2_voiceprint_end = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2VoiceprintEnd() {
            return this.l2_voiceprint_end;
        }

        public VaExtendJson setL2WakeupResultStart(long j) {
            this.l2_wakeup_result_start = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupResultStart() {
            return this.l2_wakeup_result_start;
        }

        public VaExtendJson setL2WakeupResultEnd(long j) {
            this.l2_wakeup_result_end = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupResultEnd() {
            return this.l2_wakeup_result_end;
        }

        public VaExtendJson setL1WakeupTime(long j) {
            this.l1_wakeup_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL1WakeupTime() {
            return this.l1_wakeup_time;
        }

        public VaExtendJson setL2WakeupTime(long j) {
            this.l2_wakeup_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupTime() {
            return this.l2_wakeup_time;
        }

        public VaExtendJson setL2WakeupAudioLength(long j) {
            this.l2_wakeup_audio_length = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getL2WakeupAudioLength() {
            return this.l2_wakeup_audio_length;
        }

        public VaExtendJson setVendorVersion(String str) {
            this.vendor_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVendorVersion() {
            return this.vendor_version;
        }

        public VaExtendJson setScore(double d) {
            this.score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getScore() {
            return this.score;
        }

        public VaExtendJson setMiaiVolume(double d) {
            this.miai_volume = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getMiaiVolume() {
            return this.miai_volume;
        }

        public VaExtendJson setMediaVolume(double d) {
            this.media_volume = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getMediaVolume() {
            return this.media_volume;
        }

        public VaExtendJson setPhoneVolume(double d) {
            this.phone_volume = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getPhoneVolume() {
            return this.phone_volume;
        }

        public VaExtendJson setIsMutePhoneVolume(boolean z) {
            this.is_mute_phone_volume = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMutePhoneVolume() {
            return this.is_mute_phone_volume;
        }

        public VaExtendJson setIsMute(boolean z) {
            this.is_mute = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMute() {
            return this.is_mute;
        }

        public VaExtendJson setIsNotDisturb(boolean z) {
            this.is_not_disturb = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNotDisturb() {
            return this.is_not_disturb;
        }

        public VaExtendJson setIsHaveMediaPlay(boolean z) {
            this.is_have_media_play = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHaveMediaPlay() {
            return this.is_have_media_play;
        }

        public VaExtendJson setIsConnect(boolean z) {
            this.is_connect = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isConnect() {
            return this.is_connect;
        }
    }

    /* loaded from: classes3.dex */
    public static class VaTrackLog {
        @Required
        private VaEventParams event_params;
        @Required
        private VaEventType event_type;

        public VaTrackLog() {
        }

        public VaTrackLog(VaEventType vaEventType, VaEventParams vaEventParams) {
            this.event_type = vaEventType;
            this.event_params = vaEventParams;
        }

        @Required
        public VaTrackLog setEventType(VaEventType vaEventType) {
            this.event_type = vaEventType;
            return this;
        }

        @Required
        public VaEventType getEventType() {
            return this.event_type;
        }

        @Required
        public VaTrackLog setEventParams(VaEventParams vaEventParams) {
            this.event_params = vaEventParams;
            return this;
        }

        @Required
        public VaEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoItemLog {
        private Optional<String> eid = Optional.empty();
        private Optional<String> meta_id = Optional.empty();
        private Optional<String> resource_id = Optional.empty();
        private Optional<String> refer = Optional.empty();
        private Optional<String> audio_id = Optional.empty();
        private Optional<String> third_cp_name = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<Boolean> is_history = Optional.empty();

        public VideoItemLog setEid(String str) {
            this.eid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEid() {
            return this.eid;
        }

        public VideoItemLog setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }

        public VideoItemLog setResourceId(String str) {
            this.resource_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResourceId() {
            return this.resource_id;
        }

        public VideoItemLog setRefer(String str) {
            this.refer = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRefer() {
            return this.refer;
        }

        public VideoItemLog setAudioId(String str) {
            this.audio_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioId() {
            return this.audio_id;
        }

        public VideoItemLog setThirdCpName(String str) {
            this.third_cp_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getThirdCpName() {
            return this.third_cp_name;
        }

        public VideoItemLog setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public VideoItemLog setIsHistory(boolean z) {
            this.is_history = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHistory() {
            return this.is_history;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeUpEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        private Optional<String> uid = Optional.empty();
        private Optional<String> bt_device_mac_address = Optional.empty();
        private Optional<String> bt_device_name = Optional.empty();
        private Optional<String> network_status = Optional.empty();
        private Optional<String> rom_version = Optional.empty();
        private Optional<String> device_model = Optional.empty();
        private Optional<String> miai_version = Optional.empty();
        private Optional<String> build_versioncode = Optional.empty();
        private Optional<Boolean> channel_rom_version = Optional.empty();
        private Optional<String> device_model_codename = Optional.empty();
        private Optional<String> android_version = Optional.empty();
        private Optional<String> android_sdk_version = Optional.empty();
        private Optional<String> upgrade_channel = Optional.empty();
        private Optional<String> wakeup_version = Optional.empty();
        private Optional<String> wakeup_channel = Optional.empty();
        private Optional<String> channel_version = Optional.empty();
        private Optional<String> os = Optional.empty();

        public WakeUpEventContext() {
        }

        public WakeUpEventContext(String str, String str2) {
            this.device_id = str;
            this.app_id = str2;
        }

        @Required
        public WakeUpEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public WakeUpEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        public WakeUpEventContext setUid(String str) {
            this.uid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUid() {
            return this.uid;
        }

        public WakeUpEventContext setBtDeviceMacAddress(String str) {
            this.bt_device_mac_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceMacAddress() {
            return this.bt_device_mac_address;
        }

        public WakeUpEventContext setBtDeviceName(String str) {
            this.bt_device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBtDeviceName() {
            return this.bt_device_name;
        }

        public WakeUpEventContext setNetworkStatus(String str) {
            this.network_status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkStatus() {
            return this.network_status;
        }

        public WakeUpEventContext setRomVersion(String str) {
            this.rom_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRomVersion() {
            return this.rom_version;
        }

        public WakeUpEventContext setDeviceModel(String str) {
            this.device_model = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModel() {
            return this.device_model;
        }

        public WakeUpEventContext setMiaiVersion(String str) {
            this.miai_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMiaiVersion() {
            return this.miai_version;
        }

        public WakeUpEventContext setBuildVersioncode(String str) {
            this.build_versioncode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBuildVersioncode() {
            return this.build_versioncode;
        }

        public WakeUpEventContext setChannelRomVersion(boolean z) {
            this.channel_rom_version = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isChannelRomVersion() {
            return this.channel_rom_version;
        }

        public WakeUpEventContext setDeviceModelCodename(String str) {
            this.device_model_codename = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceModelCodename() {
            return this.device_model_codename;
        }

        public WakeUpEventContext setAndroidVersion(String str) {
            this.android_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidVersion() {
            return this.android_version;
        }

        public WakeUpEventContext setAndroidSdkVersion(String str) {
            this.android_sdk_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidSdkVersion() {
            return this.android_sdk_version;
        }

        public WakeUpEventContext setUpgradeChannel(String str) {
            this.upgrade_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUpgradeChannel() {
            return this.upgrade_channel;
        }

        public WakeUpEventContext setWakeupVersion(String str) {
            this.wakeup_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupVersion() {
            return this.wakeup_version;
        }

        public WakeUpEventContext setWakeupChannel(String str) {
            this.wakeup_channel = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWakeupChannel() {
            return this.wakeup_channel;
        }

        public WakeUpEventContext setChannelVersion(String str) {
            this.channel_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getChannelVersion() {
            return this.channel_version;
        }

        public WakeUpEventContext setOs(String str) {
            this.os = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOs() {
            return this.os;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeUpEventParams {
        @Required
        private String dialog_id;
        @Required
        private WakeUpEventContext event_context;
        @Required
        private String event_data_type;
        @Deprecated
        private Optional<ObjectNode> extend_json = Optional.empty();
        private Optional<WakeUpExtendJson> extend_json_v3 = Optional.empty();
        @Required
        private long timestamp;

        public WakeUpEventParams() {
        }

        public WakeUpEventParams(String str, String str2, long j, WakeUpEventContext wakeUpEventContext) {
            this.dialog_id = str;
            this.event_data_type = str2;
            this.timestamp = j;
            this.event_context = wakeUpEventContext;
        }

        @Required
        public WakeUpEventParams setDialogId(String str) {
            this.dialog_id = str;
            return this;
        }

        @Required
        public String getDialogId() {
            return this.dialog_id;
        }

        @Required
        public WakeUpEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public WakeUpEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        @Required
        public WakeUpEventParams setEventContext(WakeUpEventContext wakeUpEventContext) {
            this.event_context = wakeUpEventContext;
            return this;
        }

        @Required
        public WakeUpEventContext getEventContext() {
            return this.event_context;
        }

        @Deprecated
        public WakeUpEventParams setExtendJson(ObjectNode objectNode) {
            this.extend_json = Optional.ofNullable(objectNode);
            return this;
        }

        @Deprecated
        public Optional<ObjectNode> getExtendJson() {
            return this.extend_json;
        }

        public WakeUpEventParams setExtendJsonV3(WakeUpExtendJson wakeUpExtendJson) {
            this.extend_json_v3 = Optional.ofNullable(wakeUpExtendJson);
            return this;
        }

        public Optional<WakeUpExtendJson> getExtendJsonV3() {
            return this.extend_json_v3;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeUpExtendJson {
        private Optional<String> app_name = Optional.empty();
        private Optional<String> func = Optional.empty();
        private Optional<String> query = Optional.empty();

        public WakeUpExtendJson setAppName(String str) {
            this.app_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppName() {
            return this.app_name;
        }

        public WakeUpExtendJson setFunc(String str) {
            this.func = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFunc() {
            return this.func;
        }

        public WakeUpExtendJson setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeUpTrackLog {
        @Required
        private WakeUpEventParams event_params;
        @Required
        private WakeUpEventType event_type;

        public WakeUpTrackLog() {
        }

        public WakeUpTrackLog(WakeUpEventType wakeUpEventType, WakeUpEventParams wakeUpEventParams) {
            this.event_type = wakeUpEventType;
            this.event_params = wakeUpEventParams;
        }

        @Required
        public WakeUpTrackLog setEventType(WakeUpEventType wakeUpEventType) {
            this.event_type = wakeUpEventType;
            return this;
        }

        @Required
        public WakeUpEventType getEventType() {
            return this.event_type;
        }

        @Required
        public WakeUpTrackLog setEventParams(WakeUpEventParams wakeUpEventParams) {
            this.event_params = wakeUpEventParams;
            return this;
        }

        @Required
        public WakeUpEventParams getEventParams() {
            return this.event_params;
        }
    }

    /* loaded from: classes3.dex */
    public static class WatchEventContext {
        @Required
        private String app_id;
        @Required
        private String device_id;
        @Required
        private String miai_version;
        @Required
        private String network_status;
        @Required
        private String platform_id;
        @Required
        private String uid;
        private Optional<String> android_version = Optional.empty();
        private Optional<String> position = Optional.empty();
        private Optional<String> android_sdk_version = Optional.empty();

        public WatchEventContext() {
        }

        public WatchEventContext(String str, String str2, String str3, String str4, String str5, String str6) {
            this.device_id = str;
            this.platform_id = str2;
            this.app_id = str3;
            this.uid = str4;
            this.network_status = str5;
            this.miai_version = str6;
        }

        @Required
        public WatchEventContext setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public WatchEventContext setPlatformId(String str) {
            this.platform_id = str;
            return this;
        }

        @Required
        public String getPlatformId() {
            return this.platform_id;
        }

        @Required
        public WatchEventContext setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        @Required
        public WatchEventContext setUid(String str) {
            this.uid = str;
            return this;
        }

        @Required
        public String getUid() {
            return this.uid;
        }

        @Required
        public WatchEventContext setNetworkStatus(String str) {
            this.network_status = str;
            return this;
        }

        @Required
        public String getNetworkStatus() {
            return this.network_status;
        }

        @Required
        public WatchEventContext setMiaiVersion(String str) {
            this.miai_version = str;
            return this;
        }

        @Required
        public String getMiaiVersion() {
            return this.miai_version;
        }

        public WatchEventContext setAndroidVersion(String str) {
            this.android_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidVersion() {
            return this.android_version;
        }

        public WatchEventContext setPosition(String str) {
            this.position = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPosition() {
            return this.position;
        }

        public WatchEventContext setAndroidSdkVersion(String str) {
            this.android_sdk_version = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAndroidSdkVersion() {
            return this.android_sdk_version;
        }
    }

    /* loaded from: classes3.dex */
    public static class WatchEventParams {
        @Required
        private WatchEventContext event_context;
        @Required
        private String event_data_type;
        private Optional<WatchExtendJson> extend_json = Optional.empty();
        @Required
        private long timestamp;
        @Required
        private String widget;

        public WatchEventParams() {
        }

        public WatchEventParams(String str, WatchEventContext watchEventContext, String str2, long j) {
            this.widget = str;
            this.event_context = watchEventContext;
            this.event_data_type = str2;
            this.timestamp = j;
        }

        @Required
        public WatchEventParams setWidget(String str) {
            this.widget = str;
            return this;
        }

        @Required
        public String getWidget() {
            return this.widget;
        }

        @Required
        public WatchEventParams setEventContext(WatchEventContext watchEventContext) {
            this.event_context = watchEventContext;
            return this;
        }

        @Required
        public WatchEventContext getEventContext() {
            return this.event_context;
        }

        @Required
        public WatchEventParams setEventDataType(String str) {
            this.event_data_type = str;
            return this;
        }

        @Required
        public String getEventDataType() {
            return this.event_data_type;
        }

        @Required
        public WatchEventParams setTimestamp(long j) {
            this.timestamp = j;
            return this;
        }

        @Required
        public long getTimestamp() {
            return this.timestamp;
        }

        public WatchEventParams setExtendJson(WatchExtendJson watchExtendJson) {
            this.extend_json = Optional.ofNullable(watchExtendJson);
            return this;
        }

        public Optional<WatchExtendJson> getExtendJson() {
            return this.extend_json;
        }
    }

    /* loaded from: classes3.dex */
    public static class WatchExtendJson {
        private Optional<String> exp_id = Optional.empty();
        private Optional<String> dialog_id = Optional.empty();
        private Optional<String> query_source = Optional.empty();
        private Optional<String> exposure_queries = Optional.empty();
        private Optional<String> click_queries = Optional.empty();
        private Optional<Long> end_time = Optional.empty();
        private Optional<String> query_origin = Optional.empty();
        private Optional<String> guidance_type = Optional.empty();
        private Optional<Integer> time_cost = Optional.empty();
        private Optional<Integer> max_wait_time = Optional.empty();
        private Optional<String> card_type = Optional.empty();
        private Optional<String> debug_info = Optional.empty();
        private Optional<Integer> query_version = Optional.empty();
        private Optional<String> extend_type = Optional.empty();
        private Optional<String> language_type = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> network_company = Optional.empty();
        private Optional<Integer> silent_mode = Optional.empty();
        private Optional<Boolean> mute_music_at_slient = Optional.empty();
        private Optional<Integer> electric_quantity = Optional.empty();
        private Optional<Integer> screen_resolution = Optional.empty();
        private Optional<Integer> screen_luminance = Optional.empty();
        private Optional<String> key_value = Optional.empty();
        private Optional<Integer> position = Optional.empty();
        private Optional<String> status = Optional.empty();
        private Optional<String> error_code = Optional.empty();
        private Optional<String> element_id = Optional.empty();
        private Optional<Double> percentage = Optional.empty();
        private Optional<String> origin_id = Optional.empty();
        private Optional<Boolean> is_true = Optional.empty();
        private Optional<Integer> length = Optional.empty();
        private Optional<String> model_name = Optional.empty();

        public WatchExtendJson setExpId(String str) {
            this.exp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpId() {
            return this.exp_id;
        }

        public WatchExtendJson setDialogId(String str) {
            this.dialog_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDialogId() {
            return this.dialog_id;
        }

        public WatchExtendJson setQuerySource(String str) {
            this.query_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuerySource() {
            return this.query_source;
        }

        public WatchExtendJson setExposureQueries(String str) {
            this.exposure_queries = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExposureQueries() {
            return this.exposure_queries;
        }

        public WatchExtendJson setClickQueries(String str) {
            this.click_queries = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClickQueries() {
            return this.click_queries;
        }

        public WatchExtendJson setEndTime(long j) {
            this.end_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getEndTime() {
            return this.end_time;
        }

        public WatchExtendJson setQueryOrigin(String str) {
            this.query_origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryOrigin() {
            return this.query_origin;
        }

        public WatchExtendJson setGuidanceType(String str) {
            this.guidance_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGuidanceType() {
            return this.guidance_type;
        }

        public WatchExtendJson setTimeCost(int i) {
            this.time_cost = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTimeCost() {
            return this.time_cost;
        }

        public WatchExtendJson setMaxWaitTime(int i) {
            this.max_wait_time = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMaxWaitTime() {
            return this.max_wait_time;
        }

        public WatchExtendJson setCardType(String str) {
            this.card_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardType() {
            return this.card_type;
        }

        public WatchExtendJson setDebugInfo(String str) {
            this.debug_info = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDebugInfo() {
            return this.debug_info;
        }

        public WatchExtendJson setQueryVersion(int i) {
            this.query_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getQueryVersion() {
            return this.query_version;
        }

        public WatchExtendJson setExtendType(String str) {
            this.extend_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExtendType() {
            return this.extend_type;
        }

        public WatchExtendJson setLanguageType(String str) {
            this.language_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLanguageType() {
            return this.language_type;
        }

        public WatchExtendJson setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public WatchExtendJson setNetworkCompany(String str) {
            this.network_company = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNetworkCompany() {
            return this.network_company;
        }

        public WatchExtendJson setSilentMode(int i) {
            this.silent_mode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSilentMode() {
            return this.silent_mode;
        }

        public WatchExtendJson setMuteMusicAtSlient(boolean z) {
            this.mute_music_at_slient = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMuteMusicAtSlient() {
            return this.mute_music_at_slient;
        }

        public WatchExtendJson setElectricQuantity(int i) {
            this.electric_quantity = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElectricQuantity() {
            return this.electric_quantity;
        }

        public WatchExtendJson setScreenResolution(int i) {
            this.screen_resolution = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenResolution() {
            return this.screen_resolution;
        }

        public WatchExtendJson setScreenLuminance(int i) {
            this.screen_luminance = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getScreenLuminance() {
            return this.screen_luminance;
        }

        public WatchExtendJson setKeyValue(String str) {
            this.key_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyValue() {
            return this.key_value;
        }

        public WatchExtendJson setPosition(int i) {
            this.position = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPosition() {
            return this.position;
        }

        public WatchExtendJson setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public WatchExtendJson setErrorCode(String str) {
            this.error_code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorCode() {
            return this.error_code;
        }

        public WatchExtendJson setElementId(String str) {
            this.element_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getElementId() {
            return this.element_id;
        }

        public WatchExtendJson setPercentage(double d) {
            this.percentage = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getPercentage() {
            return this.percentage;
        }

        public WatchExtendJson setOriginId(String str) {
            this.origin_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOriginId() {
            return this.origin_id;
        }

        public WatchExtendJson setIsTrue(boolean z) {
            this.is_true = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isTrue() {
            return this.is_true;
        }

        public WatchExtendJson setLength(int i) {
            this.length = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getLength() {
            return this.length;
        }

        public WatchExtendJson setModelName(String str) {
            this.model_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModelName() {
            return this.model_name;
        }
    }

    /* loaded from: classes3.dex */
    public static class WatchTrackLog {
        @Required
        private WatchEventParams event_params;
        @Required
        private WatchEventType event_type;

        public WatchTrackLog() {
        }

        public WatchTrackLog(WatchEventType watchEventType, WatchEventParams watchEventParams) {
            this.event_type = watchEventType;
            this.event_params = watchEventParams;
        }

        @Required
        public WatchTrackLog setEventType(WatchEventType watchEventType) {
            this.event_type = watchEventType;
            return this;
        }

        @Required
        public WatchEventType getEventType() {
            return this.event_type;
        }

        @Required
        public WatchTrackLog setEventParams(WatchEventParams watchEventParams) {
            this.event_params = watchEventParams;
            return this;
        }

        @Required
        public WatchEventParams getEventParams() {
            return this.event_params;
        }
    }
}
