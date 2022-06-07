package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Speaker {

    /* loaded from: classes3.dex */
    public enum UnitDef {
        UNKNOWN(-1),
        PERCENT(0),
        ABSOLUTE(1);
        
        private int id;

        UnitDef(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VolumeType {
        UNKNOWN(-1),
        MEDIA(0),
        RING(1),
        NOTIFICATION(2),
        XIAOAI_VOICE_ASSISTANT(3),
        ALARM(4),
        TV_KARAOKE_MICROPHONE(5);
        
        private int id;

        VolumeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AdjustVolume", namespace = AIApiConstants.Speaker.NAME)
    /* loaded from: classes3.dex */
    public static class AdjustVolume implements InstructionPayload {
        @Required
        private VolumeType type;
        @Required
        private UnitDef unit;
        @Required
        private int volume_delta;

        public AdjustVolume() {
        }

        public AdjustVolume(int i, VolumeType volumeType, UnitDef unitDef) {
            this.volume_delta = i;
            this.type = volumeType;
            this.unit = unitDef;
        }

        @Required
        public AdjustVolume setVolumeDelta(int i) {
            this.volume_delta = i;
            return this;
        }

        @Required
        public int getVolumeDelta() {
            return this.volume_delta;
        }

        @Required
        public AdjustVolume setType(VolumeType volumeType) {
            this.type = volumeType;
            return this;
        }

        @Required
        public VolumeType getType() {
            return this.type;
        }

        @Required
        public AdjustVolume setUnit(UnitDef unitDef) {
            this.unit = unitDef;
            return this;
        }

        @Required
        public UnitDef getUnit() {
            return this.unit;
        }
    }

    @NamespaceName(name = "SetAIVolume", namespace = AIApiConstants.Speaker.NAME)
    /* loaded from: classes3.dex */
    public static class SetAIVolume implements InstructionPayload {
        @Required
        private String status;

        public SetAIVolume() {
        }

        public SetAIVolume(String str) {
            this.status = str;
        }

        @Required
        public SetAIVolume setStatus(String str) {
            this.status = str;
            return this;
        }

        @Required
        public String getStatus() {
            return this.status;
        }
    }

    @NamespaceName(name = "SetMute", namespace = AIApiConstants.Speaker.NAME)
    /* loaded from: classes3.dex */
    public static class SetMute implements InstructionPayload {
        @Required
        private boolean mute;

        public SetMute() {
        }

        public SetMute(boolean z) {
            this.mute = z;
        }

        @Required
        public SetMute setMute(boolean z) {
            this.mute = z;
            return this;
        }

        @Required
        public boolean isMute() {
            return this.mute;
        }
    }

    @NamespaceName(name = "SetVolume", namespace = AIApiConstants.Speaker.NAME)
    /* loaded from: classes3.dex */
    public static class SetVolume implements InstructionPayload {
        private Optional<Boolean> once = Optional.empty();
        @Required
        private VolumeType type;
        @Required
        private UnitDef unit;
        @Required
        private int volume;

        public SetVolume() {
        }

        public SetVolume(int i, VolumeType volumeType, UnitDef unitDef) {
            this.volume = i;
            this.type = volumeType;
            this.unit = unitDef;
        }

        @Required
        public SetVolume setVolume(int i) {
            this.volume = i;
            return this;
        }

        @Required
        public int getVolume() {
            return this.volume;
        }

        @Required
        public SetVolume setType(VolumeType volumeType) {
            this.type = volumeType;
            return this;
        }

        @Required
        public VolumeType getType() {
            return this.type;
        }

        @Required
        public SetVolume setUnit(UnitDef unitDef) {
            this.unit = unitDef;
            return this;
        }

        @Required
        public UnitDef getUnit() {
            return this.unit;
        }

        public SetVolume setOnce(boolean z) {
            this.once = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isOnce() {
            return this.once;
        }
    }
}
