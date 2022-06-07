package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Sys {

    @NamespaceName(name = "AudioStore", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class AudioStore implements EventPayload {
    }

    @NamespaceName(name = "AudioStoreStreamFinished", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class AudioStoreStreamFinished implements EventPayload {
    }

    @NamespaceName(name = "CheckScreenUnlocked", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class CheckScreenUnlocked implements InstructionPayload {
    }

    @NamespaceName(name = "ClientPing", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class ClientPing implements EventPayload {
    }

    @NamespaceName(name = "FileStoreFailedNotification", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class FileStoreFailedNotification implements InstructionPayload {
    }

    @NamespaceName(name = "Heartbeat", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Heartbeat implements InstructionPayload {
    }

    @NamespaceName(name = "LockScreen", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class LockScreen implements InstructionPayload {
    }

    @NamespaceName(name = "Pong", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Pong implements InstructionPayload {
    }

    @NamespaceName(name = "ReportPhoneLag", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class ReportPhoneLag implements InstructionPayload {
    }

    @NamespaceName(name = "UnlockScreen", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class UnlockScreen implements InstructionPayload {
    }

    @NamespaceName(name = "UpgradeRom", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class UpgradeRom implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum ApplicationDeviceRunningMode {
        Unknown(-1),
        SoundBox(0),
        TV(1),
        BlueTooth(2),
        Automotive(3),
        Phone(4);
        
        private int id;

        ApplicationDeviceRunningMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ApplicationLevelMode {
        Unknown(-1),
        Standard(0),
        Kids(1),
        Simple(2),
        Office(3),
        Mi(4);
        
        private int id;

        ApplicationLevelMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum BluetoothConnectState {
        UNKNOWN(-1),
        DEVICE_CONNECT_DISCONNECT(0),
        DEVICE_CONNECT_NORMAL(1),
        DEVICE_CONNECT_LOW_BATTERY(2),
        DEVICE_CONNECT_UPDATE(3),
        DEVICE_CONNECT_WAIT(4);
        
        private int id;

        BluetoothConnectState(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum BluetoothDeviceElectricType {
        UNKNOWN(-1),
        R(0),
        L(1);
        
        private int id;

        BluetoothDeviceElectricType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum BluetoothDeviceType {
        UNKNOWN(-1),
        OTHER(0),
        EARPHONE(1),
        SOUND_BOX(2);
        
        private int id;

        BluetoothDeviceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DeviceAppliedScenario {
        HOTEL(0),
        GOVERNMENT(1),
        SCHOOL(2),
        HOSPITAL(3),
        FACTORY(4),
        RESTAURANT(5),
        ENTERTAINMENT(6),
        ENTERPRISE(7);
        
        private int id;

        DeviceAppliedScenario(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum Diagnose {
        UNKNOWN(-1),
        CLIENT(0),
        SERVER(1);
        
        private int id;

        Diagnose(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum Environment {
        PRODUCTION(0),
        PREVIEW(1),
        PREVIEW4TEST(2);
        
        private int id;

        Environment(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PowerOp {
        UNKNOWN(-1),
        BOOT(0),
        REBOOT(1),
        SLEEP(2),
        HIBERNATE(3),
        SHUTDOWN(4),
        CANCEL_SHUTDOWN(5);
        
        private int id;

        PowerOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PropertyType {
        Int(0),
        Long(1),
        String(2),
        Float(3);
        
        private int id;

        PropertyType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SceneCategory {
        INPUT_METHOD(0);
        
        private int id;

        SceneCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Abort", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Abort implements InstructionPayload {
        @Required
        private String reason;

        public Abort() {
        }

        public Abort(String str) {
            this.reason = str;
        }

        @Required
        public Abort setReason(String str) {
            this.reason = str;
            return this;
        }

        @Required
        public String getReason() {
            return this.reason;
        }
    }

    @NamespaceName(name = "Ack", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Ack implements EventPayload {
        @Required
        private String id;
        @Required
        private String type;

        public Ack() {
        }

        public Ack(String str, String str2) {
            this.id = str;
            this.type = str2;
        }

        @Required
        public Ack setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Ack setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }
    }

    /* loaded from: classes3.dex */
    public static class ApplicationMode {
        @Required
        private ApplicationLevelMode application;
        @Required
        private ApplicationDeviceRunningMode device_running;

        public ApplicationMode() {
        }

        public ApplicationMode(ApplicationDeviceRunningMode applicationDeviceRunningMode, ApplicationLevelMode applicationLevelMode) {
            this.device_running = applicationDeviceRunningMode;
            this.application = applicationLevelMode;
        }

        @Required
        public ApplicationMode setDeviceRunning(ApplicationDeviceRunningMode applicationDeviceRunningMode) {
            this.device_running = applicationDeviceRunningMode;
            return this;
        }

        @Required
        public ApplicationDeviceRunningMode getDeviceRunning() {
            return this.device_running;
        }

        @Required
        public ApplicationMode setApplication(ApplicationLevelMode applicationLevelMode) {
            this.application = applicationLevelMode;
            return this;
        }

        @Required
        public ApplicationLevelMode getApplication() {
            return this.application;
        }
    }

    @NamespaceName(name = "AutoLock", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class AutoLock implements InstructionPayload {
        private Optional<Integer> delay = Optional.empty();

        public AutoLock setDelay(int i) {
            this.delay = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDelay() {
            return this.delay;
        }
    }

    /* loaded from: classes3.dex */
    public static class BluetoothDeviceElectricInfo {
        private Optional<BluetoothDeviceElectricType> electric_type = Optional.empty();
        private Optional<Integer> electric_value = Optional.empty();
        private Optional<String> electri_unit = Optional.empty();

        public BluetoothDeviceElectricInfo setElectricType(BluetoothDeviceElectricType bluetoothDeviceElectricType) {
            this.electric_type = Optional.ofNullable(bluetoothDeviceElectricType);
            return this;
        }

        public Optional<BluetoothDeviceElectricType> getElectricType() {
            return this.electric_type;
        }

        public BluetoothDeviceElectricInfo setElectricValue(int i) {
            this.electric_value = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getElectricValue() {
            return this.electric_value;
        }

        public BluetoothDeviceElectricInfo setElectriUnit(String str) {
            this.electri_unit = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getElectriUnit() {
            return this.electri_unit;
        }
    }

    @NamespaceName(name = "BluetoothDeviceInfo", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class BluetoothDeviceInfo {
        private Optional<String> classic_address = Optional.empty();
        private Optional<String> ble_address = Optional.empty();
        private Optional<BluetoothConnectState> connect_state = Optional.empty();
        private Optional<String> device_name = Optional.empty();
        private Optional<String> icon_url = Optional.empty();
        private Optional<List<BluetoothDeviceElectricInfo>> electric_infos = Optional.empty();
        private Optional<BluetoothDeviceType> device_type = Optional.empty();

        public BluetoothDeviceInfo setClassicAddress(String str) {
            this.classic_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClassicAddress() {
            return this.classic_address;
        }

        public BluetoothDeviceInfo setBleAddress(String str) {
            this.ble_address = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBleAddress() {
            return this.ble_address;
        }

        public BluetoothDeviceInfo setConnectState(BluetoothConnectState bluetoothConnectState) {
            this.connect_state = Optional.ofNullable(bluetoothConnectState);
            return this;
        }

        public Optional<BluetoothConnectState> getConnectState() {
            return this.connect_state;
        }

        public BluetoothDeviceInfo setDeviceName(String str) {
            this.device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceName() {
            return this.device_name;
        }

        public BluetoothDeviceInfo setIconUrl(String str) {
            this.icon_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconUrl() {
            return this.icon_url;
        }

        public BluetoothDeviceInfo setElectricInfos(List<BluetoothDeviceElectricInfo> list) {
            this.electric_infos = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<BluetoothDeviceElectricInfo>> getElectricInfos() {
            return this.electric_infos;
        }

        public BluetoothDeviceInfo setDeviceType(BluetoothDeviceType bluetoothDeviceType) {
            this.device_type = Optional.ofNullable(bluetoothDeviceType);
            return this;
        }

        public Optional<BluetoothDeviceType> getDeviceType() {
            return this.device_type;
        }
    }

    @NamespaceName(name = "ChangeVoiceAssistantLogo", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class ChangeVoiceAssistantLogo implements InstructionPayload {
        @Required
        private String name;
        @Required
        private String sha1;
        @Required
        private String url;

        public ChangeVoiceAssistantLogo() {
        }

        public ChangeVoiceAssistantLogo(String str, String str2, String str3) {
            this.name = str;
            this.sha1 = str2;
            this.url = str3;
        }

        @Required
        public ChangeVoiceAssistantLogo setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public ChangeVoiceAssistantLogo setSha1(String str) {
            this.sha1 = str;
            return this;
        }

        @Required
        public String getSha1() {
            return this.sha1;
        }

        @Required
        public ChangeVoiceAssistantLogo setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceAuthInfo {
        @Required
        private List<Common.MobileMIUI13DeviceAuthCode> voice_assistant_auth_codes;

        public DeviceAuthInfo() {
        }

        public DeviceAuthInfo(List<Common.MobileMIUI13DeviceAuthCode> list) {
            this.voice_assistant_auth_codes = list;
        }

        @Required
        public DeviceAuthInfo setVoiceAssistantAuthCodes(List<Common.MobileMIUI13DeviceAuthCode> list) {
            this.voice_assistant_auth_codes = list;
            return this;
        }

        @Required
        public List<Common.MobileMIUI13DeviceAuthCode> getVoiceAssistantAuthCodes() {
            return this.voice_assistant_auth_codes;
        }
    }

    /* loaded from: classes3.dex */
    public static class DevicePowerInfo {
        private Optional<Boolean> has_battery = Optional.empty();
        private Optional<Boolean> is_connect_power = Optional.empty();

        public DevicePowerInfo setHasBattery(boolean z) {
            this.has_battery = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHasBattery() {
            return this.has_battery;
        }

        public DevicePowerInfo setIsConnectPower(boolean z) {
            this.is_connect_power = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isConnectPower() {
            return this.is_connect_power;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceScreen {
        private Optional<Boolean> locked = Optional.empty();
        private Optional<Integer> brightness = Optional.empty();
        private Optional<LockedScreenOps> locked_ops = Optional.empty();
        private Optional<Boolean> screen_lock = Optional.empty();
        private Optional<Boolean> display_enhance = Optional.empty();

        public DeviceScreen setLocked(boolean z) {
            this.locked = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isLocked() {
            return this.locked;
        }

        public DeviceScreen setBrightness(int i) {
            this.brightness = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getBrightness() {
            return this.brightness;
        }

        public DeviceScreen setLockedOps(LockedScreenOps lockedScreenOps) {
            this.locked_ops = Optional.ofNullable(lockedScreenOps);
            return this;
        }

        public Optional<LockedScreenOps> getLockedOps() {
            return this.locked_ops;
        }

        public DeviceScreen setScreenLock(boolean z) {
            this.screen_lock = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isScreenLock() {
            return this.screen_lock;
        }

        public DeviceScreen setDisplayEnhance(boolean z) {
            this.display_enhance = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDisplayEnhance() {
            return this.display_enhance;
        }
    }

    @NamespaceName(name = "DeviceState", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class DeviceState implements ContextPayload {
        private Optional<String> mode = Optional.empty();
        @Deprecated
        private Optional<List<String>> imeis = Optional.empty();
        private Optional<DeviceScreen> screen = Optional.empty();
        private Optional<DeviceVolume> volume = Optional.empty();
        private Optional<DeviceAppliedScenario> scenario = Optional.empty();
        private Optional<List<BluetoothDeviceInfo>> bluetooth_device_infos = Optional.empty();
        private Optional<Boolean> find_phone_enabled = Optional.empty();
        private Optional<Boolean> shutdown_password = Optional.empty();
        private Optional<NetworkAttribute> network = Optional.empty();
        private Optional<DeviceAuthInfo> auth_info = Optional.empty();
        private Optional<DevicePowerInfo> power_info = Optional.empty();

        public DeviceState setMode(String str) {
            this.mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMode() {
            return this.mode;
        }

        @Deprecated
        public DeviceState setImeis(List<String> list) {
            this.imeis = Optional.ofNullable(list);
            return this;
        }

        @Deprecated
        public Optional<List<String>> getImeis() {
            return this.imeis;
        }

        public DeviceState setScreen(DeviceScreen deviceScreen) {
            this.screen = Optional.ofNullable(deviceScreen);
            return this;
        }

        public Optional<DeviceScreen> getScreen() {
            return this.screen;
        }

        public DeviceState setVolume(DeviceVolume deviceVolume) {
            this.volume = Optional.ofNullable(deviceVolume);
            return this;
        }

        public Optional<DeviceVolume> getVolume() {
            return this.volume;
        }

        public DeviceState setScenario(DeviceAppliedScenario deviceAppliedScenario) {
            this.scenario = Optional.ofNullable(deviceAppliedScenario);
            return this;
        }

        public Optional<DeviceAppliedScenario> getScenario() {
            return this.scenario;
        }

        public DeviceState setBluetoothDeviceInfos(List<BluetoothDeviceInfo> list) {
            this.bluetooth_device_infos = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<BluetoothDeviceInfo>> getBluetoothDeviceInfos() {
            return this.bluetooth_device_infos;
        }

        public DeviceState setFindPhoneEnabled(boolean z) {
            this.find_phone_enabled = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isFindPhoneEnabled() {
            return this.find_phone_enabled;
        }

        public DeviceState setShutdownPassword(boolean z) {
            this.shutdown_password = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShutdownPassword() {
            return this.shutdown_password;
        }

        public DeviceState setNetwork(NetworkAttribute networkAttribute) {
            this.network = Optional.ofNullable(networkAttribute);
            return this;
        }

        public Optional<NetworkAttribute> getNetwork() {
            return this.network;
        }

        public DeviceState setAuthInfo(DeviceAuthInfo deviceAuthInfo) {
            this.auth_info = Optional.ofNullable(deviceAuthInfo);
            return this;
        }

        public Optional<DeviceAuthInfo> getAuthInfo() {
            return this.auth_info;
        }

        public DeviceState setPowerInfo(DevicePowerInfo devicePowerInfo) {
            this.power_info = Optional.ofNullable(devicePowerInfo);
            return this;
        }

        public Optional<DevicePowerInfo> getPowerInfo() {
            return this.power_info;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceVolume {
        private Optional<Integer> media = Optional.empty();
        private Optional<Integer> voice_assistant = Optional.empty();
        private Optional<Boolean> is_silent_mode = Optional.empty();
        private Optional<Boolean> is_not_disturb_mode = Optional.empty();
        private Optional<Boolean> voice_media_stop_switch_in_silent = Optional.empty();

        public DeviceVolume setMedia(int i) {
            this.media = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMedia() {
            return this.media;
        }

        public DeviceVolume setVoiceAssistant(int i) {
            this.voice_assistant = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVoiceAssistant() {
            return this.voice_assistant;
        }

        public DeviceVolume setIsSilentMode(boolean z) {
            this.is_silent_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSilentMode() {
            return this.is_silent_mode;
        }

        public DeviceVolume setIsNotDisturbMode(boolean z) {
            this.is_not_disturb_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNotDisturbMode() {
            return this.is_not_disturb_mode;
        }

        public DeviceVolume setVoiceMediaStopSwitchInSilent(boolean z) {
            this.voice_media_stop_switch_in_silent = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVoiceMediaStopSwitchInSilent() {
            return this.voice_media_stop_switch_in_silent;
        }
    }

    @NamespaceName(name = "DisplayState", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class DisplayState implements ContextPayload {
        private Optional<Boolean> is_dark_mode = Optional.empty();

        public DisplayState setIsDarkMode(boolean z) {
            this.is_dark_mode = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDarkMode() {
            return this.is_dark_mode;
        }
    }

    @NamespaceName(name = "EnvSwitch", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class EnvSwitch implements InstructionPayload {
        @Required
        private Environment env;

        public EnvSwitch() {
        }

        public EnvSwitch(Environment environment) {
            this.env = environment;
        }

        @Required
        public EnvSwitch setEnv(Environment environment) {
            this.env = environment;
            return this;
        }

        @Required
        public Environment getEnv() {
            return this.env;
        }
    }

    @NamespaceName(name = "EventRoute", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class EventRoute implements ContextPayload {
        @Required
        private String id;

        public EventRoute() {
        }

        public EventRoute(String str) {
            this.id = str;
        }

        @Required
        public EventRoute setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Exception", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Exception implements InstructionPayload {
        @Required
        private int code;
        @Required
        private int http_status_code;
        private Optional<String> id = Optional.empty();
        @Required
        private String message;

        public Exception() {
        }

        public Exception(int i, String str, int i2) {
            this.code = i;
            this.message = str;
            this.http_status_code = i2;
        }

        @Required
        public Exception setCode(int i) {
            this.code = i;
            return this;
        }

        @Required
        public int getCode() {
            return this.code;
        }

        @Required
        public Exception setMessage(String str) {
            this.message = str;
            return this;
        }

        @Required
        public String getMessage() {
            return this.message;
        }

        @Required
        public Exception setHttpStatusCode(int i) {
            this.http_status_code = i;
            return this;
        }

        @Required
        public int getHttpStatusCode() {
            return this.http_status_code;
        }

        public Exception setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class LockedScreenOps {
        private Optional<Boolean> call = Optional.empty();
        private Optional<Boolean> device_control = Optional.empty();

        public LockedScreenOps setCall(boolean z) {
            this.call = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCall() {
            return this.call;
        }

        public LockedScreenOps setDeviceControl(boolean z) {
            this.device_control = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDeviceControl() {
            return this.device_control;
        }
    }

    /* loaded from: classes3.dex */
    public static class NetworkAttribute {
        private Optional<String> bssid = Optional.empty();
        private Optional<String> voip_id = Optional.empty();

        public NetworkAttribute setBssid(String str) {
            this.bssid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBssid() {
            return this.bssid;
        }

        public NetworkAttribute setVoipId(String str) {
            this.voip_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVoipId() {
            return this.voip_id;
        }
    }

    @NamespaceName(name = "PhoneLag", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class PhoneLag implements EventPayload {
        @Required
        private ObjectNode data;
        @Required
        private Diagnose diagnose;

        public PhoneLag() {
        }

        public PhoneLag(Diagnose diagnose, ObjectNode objectNode) {
            this.diagnose = diagnose;
            this.data = objectNode;
        }

        @Required
        public PhoneLag setDiagnose(Diagnose diagnose) {
            this.diagnose = diagnose;
            return this;
        }

        @Required
        public Diagnose getDiagnose() {
            return this.diagnose;
        }

        @Required
        public PhoneLag setData(ObjectNode objectNode) {
            this.data = objectNode;
            return this;
        }

        @Required
        public ObjectNode getData() {
            return this.data;
        }
    }

    @NamespaceName(name = "Ping", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Ping implements InstructionPayload {
        @Required
        private String id;
        @Required
        private String type;

        public Ping() {
        }

        public Ping(String str, String str2) {
            this.id = str;
            this.type = str2;
        }

        @Required
        public Ping setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Ping setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "Power", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Power implements InstructionPayload {
        @Required
        private PowerOp operation;
        private Optional<Integer> delay = Optional.empty();
        private Optional<Boolean> confirmation = Optional.empty();
        private Optional<String> bluetooth_mac = Optional.empty();

        public Power() {
        }

        public Power(PowerOp powerOp) {
            this.operation = powerOp;
        }

        @Required
        public Power setOperation(PowerOp powerOp) {
            this.operation = powerOp;
            return this;
        }

        @Required
        public PowerOp getOperation() {
            return this.operation;
        }

        public Power setDelay(int i) {
            this.delay = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDelay() {
            return this.delay;
        }

        public Power setConfirmation(boolean z) {
            this.confirmation = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isConfirmation() {
            return this.confirmation;
        }

        public Power setBluetoothMac(String str) {
            this.bluetooth_mac = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBluetoothMac() {
            return this.bluetooth_mac;
        }
    }

    @NamespaceName(name = "Scene", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Scene implements ContextPayload {
        @Required
        private SceneCategory category;

        public Scene() {
        }

        public Scene(SceneCategory sceneCategory) {
            this.category = sceneCategory;
        }

        @Required
        public Scene setCategory(SceneCategory sceneCategory) {
            this.category = sceneCategory;
            return this;
        }

        @Required
        public SceneCategory getCategory() {
            return this.category;
        }
    }

    @NamespaceName(name = "SetMiuiDatabase", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class SetMiuiDatabase implements InstructionPayload {
        @Required
        private String database_name;
        @Required
        private String property_name;
        @Required
        private PropertyType property_type;
        @Required
        private String property_value;

        public SetMiuiDatabase() {
        }

        public SetMiuiDatabase(String str, String str2, PropertyType propertyType, String str3) {
            this.database_name = str;
            this.property_name = str2;
            this.property_type = propertyType;
            this.property_value = str3;
        }

        @Required
        public SetMiuiDatabase setDatabaseName(String str) {
            this.database_name = str;
            return this;
        }

        @Required
        public String getDatabaseName() {
            return this.database_name;
        }

        @Required
        public SetMiuiDatabase setPropertyName(String str) {
            this.property_name = str;
            return this;
        }

        @Required
        public String getPropertyName() {
            return this.property_name;
        }

        @Required
        public SetMiuiDatabase setPropertyType(PropertyType propertyType) {
            this.property_type = propertyType;
            return this;
        }

        @Required
        public PropertyType getPropertyType() {
            return this.property_type;
        }

        @Required
        public SetMiuiDatabase setPropertyValue(String str) {
            this.property_value = str;
            return this;
        }

        @Required
        public String getPropertyValue() {
            return this.property_value;
        }
    }

    @NamespaceName(name = "SetProperty", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class SetProperty implements InstructionPayload {
        @Required
        private String name;
        @Required
        private String value;

        public SetProperty() {
        }

        public SetProperty(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        @Required
        public SetProperty setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public SetProperty setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }
    }

    @NamespaceName(name = "Sleep", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Sleep implements InstructionPayload {
        @Required
        private int duration;

        public Sleep() {
        }

        public Sleep(int i) {
            this.duration = i;
        }

        @Required
        public Sleep setDuration(int i) {
            this.duration = i;
            return this;
        }

        @Required
        public int getDuration() {
            return this.duration;
        }
    }

    @NamespaceName(name = "SwitchMiuiDatabase", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class SwitchMiuiDatabase implements InstructionPayload {
        @Required
        private String database_name;
        private Optional<String> default_value = Optional.empty();
        @Required
        private String property_name;
        @Required
        private PropertyType property_type;

        public SwitchMiuiDatabase() {
        }

        public SwitchMiuiDatabase(String str, String str2, PropertyType propertyType) {
            this.database_name = str;
            this.property_name = str2;
            this.property_type = propertyType;
        }

        @Required
        public SwitchMiuiDatabase setDatabaseName(String str) {
            this.database_name = str;
            return this;
        }

        @Required
        public String getDatabaseName() {
            return this.database_name;
        }

        @Required
        public SwitchMiuiDatabase setPropertyName(String str) {
            this.property_name = str;
            return this;
        }

        @Required
        public String getPropertyName() {
            return this.property_name;
        }

        @Required
        public SwitchMiuiDatabase setPropertyType(PropertyType propertyType) {
            this.property_type = propertyType;
            return this;
        }

        @Required
        public PropertyType getPropertyType() {
            return this.property_type;
        }

        public SwitchMiuiDatabase setDefaultValue(String str) {
            this.default_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDefaultValue() {
            return this.default_value;
        }
    }

    @NamespaceName(name = "Theme", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class Theme implements ContextPayload {
        private Optional<String> id = Optional.empty();

        public Theme setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "TruncationNotification", namespace = AIApiConstants.System.NAME)
    /* loaded from: classes3.dex */
    public static class TruncationNotification implements InstructionPayload {
        @Required
        private int received_bytes;
        @Required
        private boolean received_stream_finished;
        private Optional<String> truncation_reason = Optional.empty();

        public TruncationNotification() {
        }

        public TruncationNotification(int i, boolean z) {
            this.received_bytes = i;
            this.received_stream_finished = z;
        }

        @Required
        public TruncationNotification setReceivedBytes(int i) {
            this.received_bytes = i;
            return this;
        }

        @Required
        public int getReceivedBytes() {
            return this.received_bytes;
        }

        @Required
        public TruncationNotification setReceivedStreamFinished(boolean z) {
            this.received_stream_finished = z;
            return this;
        }

        @Required
        public boolean isReceivedStreamFinished() {
            return this.received_stream_finished;
        }

        public TruncationNotification setTruncationReason(String str) {
            this.truncation_reason = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTruncationReason() {
            return this.truncation_reason;
        }
    }
}
