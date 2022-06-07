package com.xiaomi.micolauncher.module.miot.defined;

import android.util.Log;
import com.xiaomi.miot.typedef.urn.ActionType;
import com.xiaomi.miot.typedef.urn.PropertyType;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class SpeakerDefined {
    public static final int CAMERA_ACTION_DOORBELL_ID = 1;
    public static final int CAMERA_SERVICE_IID = 1;
    public static final int CLOCK_SERVICE_IID = 6;
    public static final int DOORBELL_DID_PROPERTY_IID = 1;
    public static final int DOORBELL_STATE_PROPERTY_IID = 2;
    public static final int DOORBELL_TIME_STAMP_PROPERTY_IID = 3;
    public static final int DOORBELL_TRACE_PROPERTY_IID = 4;
    public static final int DOWN_PROPERTY_IID = 5;
    public static final int GATEWAY_ACTION_DISENABLE_ID = 2;
    public static final int GATEWAY_ACTION_ENABLE_ID = 1;
    public static final int GATEWAY_ACTION_STATUS_ID = 3;
    public static final int GATEWAY_PROPERTY_IID = 2;
    public static final int GATEWAY_SERVICE_IID = 0;
    public static final int INTELLIGENT_SPEAKER_SERVICE_ID = 7;
    public static final int MESH_SWITCH_PROPERTY_IID = 1;
    public static final int MICROPHONE_MUTE_PROPERTY_IID = 1;
    public static final int MICROPHONE_SERVICE_IID = 4;
    public static final int PLAYER_ALARMSTOP_ACTION_ID = 1;
    public static final int PLAYER_DIRECTIVES_ACTION_ID = 4;
    public static final int PLAYER_FM_ACTION_ID = 2;
    public static final int PLAYER_MUTE_ACTION_ID = 7;
    public static final int PLAYER_NEXT_ACTION_ID = 6;
    public static final int PLAYER_PAUSE_ACTION_ID = 3;
    public static final int PLAYER_PLAY_ACTION_ID = 2;
    public static final int PLAYER_PREVIOUS_ACTION_ID = 5;
    public static final int PLAYER_SEEK_ACTION_ID = 1;
    public static final int PLAYER_SERVICE_IID = 3;
    public static final int PLAYER_STATE_PROPERTY_IID = 1;
    public static final int PLAYER_STOP_ACTION_ID = 4;
    public static final int PLAYER_TTS_ACTION_ID = 3;
    public static final int PLAYER_TV_SWITCHON_ACTION_ID = 1;
    public static final int PLAYER_UNMUTE_ACTION_ID = 8;
    public static final int PLAYER_WAKEUP_ACTION_ID = 1;
    public static final int SILENT_EXECUTION_PROPERTY_IID = 2;
    public static final int SPEAKER_MUTE_PROPERTY_IID = 2;
    public static final int SPEAKER_RATE_PROPERTY_IID = 3;
    public static final int SPEAKER_SERVICE_IID = 2;
    public static final int SPEAKER_VOLUME_PROPERTY_IID = 1;
    public static final int TEXT_PROPERTY_IID = 1;
    public static final int TTS_REPLY_PROPERTY_IID = 2;
    public static final int TV_SWITCH_SERVICE_ID = 8;
    public static final int UP_PROPERTY_IID = 4;

    private SpeakerDefined() {
    }

    /* loaded from: classes3.dex */
    public enum Service {
        Undefined(0),
        Microphone(1),
        Speaker(2),
        Player(3),
        Camera(4),
        GateWay(5),
        Clock(6),
        IntelligentSpeaker(7),
        TvSwitch(8);
        
        private int value;

        Service(int i) {
            this.value = i;
        }

        public static Service valueOf(int i) {
            Service[] values = values();
            for (Service service : values) {
                if (service.value() == i) {
                    return service;
                }
            }
            Log.d("SpeakerDefined", "invalid value: " + i);
            return Undefined;
        }

        public static Service valueOf(ServiceType serviceType) {
            if (!serviceType.getDomain().equals("speaker")) {
                return Undefined;
            }
            Service[] values = values();
            for (Service service : values) {
                if (service.toString().equals(serviceType.getSubType())) {
                    return service;
                }
            }
            return Undefined;
        }

        public int value() {
            return this.value;
        }

        public ServiceType toServiceType() {
            return new ServiceType("speaker", toString(), toShortUUID());
        }

        public String toUUID() {
            return String.format("%08X%s", Integer.valueOf(this.value), "00000A01");
        }

        public String toShortUUID() {
            return String.format("%04X", Integer.valueOf(this.value));
        }
    }

    /* loaded from: classes3.dex */
    public enum Property {
        Undefined(0),
        MicrophoneMute(1),
        SpeakerVolume(2),
        SpeakerRate(3),
        SpeakerMute(4),
        Up(5),
        Down(6),
        Text(7),
        DoorbellDid(8),
        DoorbellState(9),
        DoorbellTimeStamp(10),
        TtsReply(11),
        DoorbellTraceId(12),
        ble_mesh_switch(13),
        gateway_status(14),
        SilentExecution(15),
        PlayerState(16);
        
        private int value;

        Property(int i) {
            this.value = i;
        }

        public static Property valueOf(int i) {
            Property[] values = values();
            for (Property property : values) {
                if (property.value() == i) {
                    return property;
                }
            }
            Log.e("SpeakerDefined", "invalid value: " + i);
            return Undefined;
        }

        public static Property valueOf(PropertyType propertyType) {
            if (!propertyType.getDomain().equals("speaker")) {
                return Undefined;
            }
            Property[] values = values();
            for (Property property : values) {
                if (property.toString().equals(propertyType.getSubType())) {
                    return property;
                }
            }
            return Undefined;
        }

        public int value() {
            return this.value;
        }

        public PropertyType toPropertyType() {
            return new PropertyType("speaker", toString(), toShortUUID());
        }

        public String toUUID() {
            return String.format("%08X%s", Integer.valueOf(this.value), "00000A01");
        }

        public String toShortUUID() {
            return String.format("%04X", Integer.valueOf(this.value));
        }
    }

    /* loaded from: classes3.dex */
    public enum Action {
        Undefined(0),
        Play(1),
        Pause(2),
        Prev(3),
        Next(4),
        Mute(5),
        UnMute(6),
        Fm(7),
        AlarmStop(8),
        Tts(9),
        Doorbell(10),
        TvSwitchOn(11),
        WakeUp(12),
        Directives(13),
        bt_gateway_enable(14),
        bt_gateway_disable(15),
        bt_gateway_status(16);
        
        private int value;

        Action(int i) {
            this.value = i;
        }

        public static Action valueOf(int i) {
            Action[] values = values();
            for (Action action : values) {
                if (action.value() == i) {
                    return action;
                }
            }
            Log.e("SpeakerDefined", "invalid value: " + i);
            return Undefined;
        }

        public static Action valueOf(ActionType actionType) {
            if (!actionType.getDomain().equals("speaker")) {
                return Undefined;
            }
            Action[] values = values();
            for (Action action : values) {
                if (action.toString().equals(actionType.getSubType())) {
                    return action;
                }
            }
            return Undefined;
        }

        public int value() {
            return this.value;
        }

        public ActionType toActionType() {
            return new ActionType("speaker", toString(), toShortUUID());
        }

        public String toUUID() {
            return String.format("%08X%s", Integer.valueOf(this.value), "00000A01");
        }

        public String toShortUUID() {
            return String.format("%04X", Integer.valueOf(this.value));
        }
    }
}
