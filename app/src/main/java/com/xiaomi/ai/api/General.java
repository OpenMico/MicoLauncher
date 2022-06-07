package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Experiments;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.Context;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class General {

    @NamespaceName(name = "FetchDeviceState", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class FetchDeviceState implements InstructionPayload {
    }

    @NamespaceName(name = "RenewSession", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class RenewSession implements ContextPayload {
    }

    /* loaded from: classes3.dex */
    public enum ModeActionType {
        OPEN(0),
        CLOSE(1),
        SWITCH(2);
        
        private int id;

        ModeActionType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ModeType {
        UNKNOWN(-1),
        SOUNDBOX(0),
        TV(1);
        
        private int id;

        ModeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum QueryItem {
        UNKNOWN(-1),
        MEDIA_VOLUME(0),
        PLAYING_ITEM_ARTIST(1),
        PLAYING_ITEM_TITLE(2),
        PLAYER_LOOP_MODE(3),
        BLUETOOTH_CONNECTED_STATUS(4),
        DATA_TRAFFIC(5),
        REMAINING_BATTERY(6),
        BLUETOOTH_DEVICE_REMAINING_BATTERY(7),
        EARPHONE_REMAINING_BATTERY(8),
        SOUNDBOX_REMAINING_BATTERY(9),
        ALARM_REMAINING_BATTERY(10);
        
        private int id;

        QueryItem(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ResourceType {
        MUSIC(0),
        SCENE(1),
        VOICE(2),
        SONG_LIST(3);
        
        private int id;

        ResourceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "CacheResource", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class CacheResource implements InstructionPayload {
        @Required
        private ArrayNode instructions;

        public CacheResource() {
        }

        public CacheResource(ArrayNode arrayNode) {
            this.instructions = arrayNode;
        }

        @Required
        public CacheResource setInstructions(ArrayNode arrayNode) {
            this.instructions = arrayNode;
            return this;
        }

        @Required
        public ArrayNode getInstructions() {
            return this.instructions;
        }
    }

    /* loaded from: classes3.dex */
    public static class ContextDeleteItem {
        @Required
        private String name;
        @Required
        private String namespace;

        public ContextDeleteItem() {
        }

        public ContextDeleteItem(String str, String str2) {
            this.namespace = str;
            this.name = str2;
        }

        @Required
        public ContextDeleteItem setNamespace(String str) {
            this.namespace = str;
            return this;
        }

        @Required
        public String getNamespace() {
            return this.namespace;
        }

        @Required
        public ContextDeleteItem setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }
    }

    @NamespaceName(name = "ContextUpdate", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class ContextUpdate implements EventPayload {
        private Optional<String> id = Optional.empty();
        private Optional<List<ContextDeleteItem>> delete_items = Optional.empty();
        private Optional<Boolean> enable_incremental_update = Optional.empty();

        public ContextUpdate setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public ContextUpdate setDeleteItems(List<ContextDeleteItem> list) {
            this.delete_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ContextDeleteItem>> getDeleteItems() {
            return this.delete_items;
        }

        public ContextUpdate setEnableIncrementalUpdate(boolean z) {
            this.enable_incremental_update = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isEnableIncrementalUpdate() {
            return this.enable_incremental_update;
        }
    }

    @NamespaceName(name = "DeviceStateReport", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class DeviceStateReport implements EventPayload {
        private Optional<AudioPlayer.PlaybackState> audio_player_state = Optional.empty();
        private Optional<VideoPlayer.VideoPlaybackState> video_player_state = Optional.empty();
        private Optional<Boolean> is_idle = Optional.empty();

        public DeviceStateReport setAudioPlayerState(AudioPlayer.PlaybackState playbackState) {
            this.audio_player_state = Optional.ofNullable(playbackState);
            return this;
        }

        public Optional<AudioPlayer.PlaybackState> getAudioPlayerState() {
            return this.audio_player_state;
        }

        public DeviceStateReport setVideoPlayerState(VideoPlayer.VideoPlaybackState videoPlaybackState) {
            this.video_player_state = Optional.ofNullable(videoPlaybackState);
            return this;
        }

        public Optional<VideoPlayer.VideoPlaybackState> getVideoPlayerState() {
            return this.video_player_state;
        }

        public DeviceStateReport setIsIdle(boolean z) {
            this.is_idle = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isIdle() {
            return this.is_idle;
        }
    }

    @NamespaceName(name = "Experiment", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class Experiment implements InstructionPayload {
        @Required
        private String eid;
        private Optional<Experiments.ExperimentType> type = Optional.empty();
        private Optional<ObjectNode> param = Optional.empty();

        public Experiment() {
        }

        public Experiment(String str) {
            this.eid = str;
        }

        @Required
        public Experiment setEid(String str) {
            this.eid = str;
            return this;
        }

        @Required
        public String getEid() {
            return this.eid;
        }

        public Experiment setType(Experiments.ExperimentType experimentType) {
            this.type = Optional.ofNullable(experimentType);
            return this;
        }

        public Optional<Experiments.ExperimentType> getType() {
            return this.type;
        }

        public Experiment setParam(ObjectNode objectNode) {
            this.param = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParam() {
            return this.param;
        }
    }

    @NamespaceName(name = "FetchResource", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class FetchResource implements EventPayload {
        @Required
        private String command;
        @Required
        private boolean needs_cache;
        @Required
        private ResourceType type;

        public FetchResource() {
        }

        public FetchResource(ResourceType resourceType, boolean z, String str) {
            this.type = resourceType;
            this.needs_cache = z;
            this.command = str;
        }

        @Required
        public FetchResource setType(ResourceType resourceType) {
            this.type = resourceType;
            return this;
        }

        @Required
        public ResourceType getType() {
            return this.type;
        }

        @Required
        public FetchResource setNeedsCache(boolean z) {
            this.needs_cache = z;
            return this;
        }

        @Required
        public boolean isNeedsCache() {
            return this.needs_cache;
        }

        @Required
        public FetchResource setCommand(String str) {
            this.command = str;
            return this;
        }

        @Required
        public String getCommand() {
            return this.command;
        }
    }

    @NamespaceName(name = "ModeOp", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class ModeOp implements InstructionPayload {
        @Required
        private ModeActionType action;
        @Required
        private ModeType mode;

        public ModeOp() {
        }

        public ModeOp(ModeActionType modeActionType, ModeType modeType) {
            this.action = modeActionType;
            this.mode = modeType;
        }

        @Required
        public ModeOp setAction(ModeActionType modeActionType) {
            this.action = modeActionType;
            return this;
        }

        @Required
        public ModeActionType getAction() {
            return this.action;
        }

        @Required
        public ModeOp setMode(ModeType modeType) {
            this.mode = modeType;
            return this;
        }

        @Required
        public ModeType getMode() {
            return this.mode;
        }
    }

    @NamespaceName(name = "PickDevice", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class PickDevice implements InstructionPayload {
        @Required
        private String pid;
        @Required
        private String vid;

        public PickDevice() {
        }

        public PickDevice(String str, String str2) {
            this.vid = str;
            this.pid = str2;
        }

        @Required
        public PickDevice setVid(String str) {
            this.vid = str;
            return this;
        }

        @Required
        public String getVid() {
            return this.vid;
        }

        @Required
        public PickDevice setPid(String str) {
            this.pid = str;
            return this;
        }

        @Required
        public String getPid() {
            return this.pid;
        }
    }

    @NamespaceName(name = "Push", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class Push implements InstructionPayload {
        @Required
        private ArrayNode instructions;
        private Optional<PushMsgMeta> msg_meta = Optional.empty();

        public Push() {
        }

        public Push(ArrayNode arrayNode) {
            this.instructions = arrayNode;
        }

        @Required
        public Push setInstructions(ArrayNode arrayNode) {
            this.instructions = arrayNode;
            return this;
        }

        @Required
        public ArrayNode getInstructions() {
            return this.instructions;
        }

        public Push setMsgMeta(PushMsgMeta pushMsgMeta) {
            this.msg_meta = Optional.ofNullable(pushMsgMeta);
            return this;
        }

        public Optional<PushMsgMeta> getMsgMeta() {
            return this.msg_meta;
        }
    }

    /* loaded from: classes3.dex */
    public static class PushMsgMeta {
        @Required
        private long expiration_in_seconds;
        @Required
        private String id;
        @Required
        private long send_timestamp;
        @Required
        private String sub_type;

        public PushMsgMeta() {
        }

        public PushMsgMeta(String str, String str2, long j, long j2) {
            this.id = str;
            this.sub_type = str2;
            this.send_timestamp = j;
            this.expiration_in_seconds = j2;
        }

        @Required
        public PushMsgMeta setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public PushMsgMeta setSubType(String str) {
            this.sub_type = str;
            return this;
        }

        @Required
        public String getSubType() {
            return this.sub_type;
        }

        @Required
        public PushMsgMeta setSendTimestamp(long j) {
            this.send_timestamp = j;
            return this;
        }

        @Required
        public long getSendTimestamp() {
            return this.send_timestamp;
        }

        @Required
        public PushMsgMeta setExpirationInSeconds(long j) {
            this.expiration_in_seconds = j;
            return this;
        }

        @Required
        public long getExpirationInSeconds() {
            return this.expiration_in_seconds;
        }
    }

    @NamespaceName(name = "QueryClientStatus", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class QueryClientStatus implements InstructionPayload {
        @Required
        private QueryItem item;

        public QueryClientStatus() {
        }

        public QueryClientStatus(QueryItem queryItem) {
            this.item = queryItem;
        }

        @Required
        public QueryClientStatus setItem(QueryItem queryItem) {
            this.item = queryItem;
            return this;
        }

        @Required
        public QueryItem getItem() {
            return this.item;
        }
    }

    @NamespaceName(name = "RequestState", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class RequestState implements ContextPayload {
        private Optional<String> origin = Optional.empty();
        private Optional<String> tts_vendor = Optional.empty();
        @Deprecated
        private Optional<Settings.ClientInfo> client_info = Optional.empty();
        private Optional<Boolean> is_init_wakeup = Optional.empty();
        private Optional<String> speaker = Optional.empty();
        @Deprecated
        private Optional<Boolean> executing_device_picking = Optional.empty();

        public RequestState setOrigin(String str) {
            this.origin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOrigin() {
            return this.origin;
        }

        public RequestState setTtsVendor(String str) {
            this.tts_vendor = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTtsVendor() {
            return this.tts_vendor;
        }

        @Deprecated
        public RequestState setClientInfo(Settings.ClientInfo clientInfo) {
            this.client_info = Optional.ofNullable(clientInfo);
            return this;
        }

        @Deprecated
        public Optional<Settings.ClientInfo> getClientInfo() {
            return this.client_info;
        }

        public RequestState setIsInitWakeup(boolean z) {
            this.is_init_wakeup = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInitWakeup() {
            return this.is_init_wakeup;
        }

        public RequestState setSpeaker(String str) {
            this.speaker = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSpeaker() {
            return this.speaker;
        }

        @Deprecated
        public RequestState setExecutingDevicePicking(boolean z) {
            this.executing_device_picking = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        @Deprecated
        public Optional<Boolean> isExecutingDevicePicking() {
            return this.executing_device_picking;
        }
    }

    @NamespaceName(name = "SetClientTracker", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class SetClientTracker implements InstructionPayload {
        @Required
        private String token;

        public SetClientTracker() {
        }

        public SetClientTracker(String str) {
            this.token = str;
        }

        @Required
        public SetClientTracker setToken(String str) {
            this.token = str;
            return this;
        }

        @Required
        public String getToken() {
            return this.token;
        }
    }

    @NamespaceName(name = "Success", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class Success implements InstructionPayload {
        @Required
        private String id;

        public Success() {
        }

        public Success(String str) {
            this.id = str;
        }

        @Required
        public Success setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "SwitchTone", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class SwitchTone implements InstructionPayload {
        @Required
        private String tone_id;
        private Optional<String> vendor_id = Optional.empty();
        private Optional<String> speaker = Optional.empty();
        private Optional<Common.UserInfo> user = Optional.empty();

        public SwitchTone() {
        }

        public SwitchTone(String str) {
            this.tone_id = str;
        }

        @Required
        public SwitchTone setToneId(String str) {
            this.tone_id = str;
            return this;
        }

        @Required
        public String getToneId() {
            return this.tone_id;
        }

        public SwitchTone setVendorId(String str) {
            this.vendor_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVendorId() {
            return this.vendor_id;
        }

        public SwitchTone setSpeaker(String str) {
            this.speaker = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSpeaker() {
            return this.speaker;
        }

        public SwitchTone setUser(Common.UserInfo userInfo) {
            this.user = Optional.ofNullable(userInfo);
            return this;
        }

        public Optional<Common.UserInfo> getUser() {
            return this.user;
        }
    }

    @NamespaceName(name = "ThirdPartyWakeupState", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class ThirdPartyWakeupState implements ContextPayload {
        @Required
        private String activity_id;
        @Required
        private String encrypted_user_info;
        @Required
        private String wakeup_id;

        public ThirdPartyWakeupState() {
        }

        public ThirdPartyWakeupState(String str, String str2, String str3) {
            this.encrypted_user_info = str;
            this.activity_id = str2;
            this.wakeup_id = str3;
        }

        @Required
        public ThirdPartyWakeupState setEncryptedUserInfo(String str) {
            this.encrypted_user_info = str;
            return this;
        }

        @Required
        public String getEncryptedUserInfo() {
            return this.encrypted_user_info;
        }

        @Required
        public ThirdPartyWakeupState setActivityId(String str) {
            this.activity_id = str;
            return this;
        }

        @Required
        public String getActivityId() {
            return this.activity_id;
        }

        @Required
        public ThirdPartyWakeupState setWakeupId(String str) {
            this.wakeup_id = str;
            return this;
        }

        @Required
        public String getWakeupId() {
            return this.wakeup_id;
        }
    }

    @NamespaceName(name = "UpdateGlobalContexts", namespace = AIApiConstants.General.NAME)
    /* loaded from: classes3.dex */
    public static class UpdateGlobalContexts implements EventPayload {
        private Optional<List<Context<ContextPayload>>> create_or_update_items = Optional.empty();
        private Optional<List<ContextDeleteItem>> delete_items = Optional.empty();

        public UpdateGlobalContexts setCreateOrUpdateItems(List<Context<ContextPayload>> list) {
            this.create_or_update_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Context<ContextPayload>>> getCreateOrUpdateItems() {
            return this.create_or_update_items;
        }

        public UpdateGlobalContexts setDeleteItems(List<ContextDeleteItem> list) {
            this.delete_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ContextDeleteItem>> getDeleteItems() {
            return this.delete_items;
        }
    }
}
