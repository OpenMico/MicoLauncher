package com.xiaomi.micolauncher.common.skill;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class InstructionConfig {

    /* loaded from: classes3.dex */
    public static class Dialog {
        public static final String NAMESPACE = "Dialog";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            TurnOnContinuousDialog,
            TurnOffContinuousDialog,
            EnterTemporaryContinuousDialog,
            ExitContinuousDialog
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class Settings {
        public static final String NAMESPACE = "Settings";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            PowerState
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SpeechSettings {
        public static final String NAMESPACE = "SpeechSettings";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            OperatedQuery
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SpeechRecognizer {
        public static final String NAMESPACE = "SpeechRecognizer";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            ExpectSpeech,
            RegisterVoiceprint,
            RecognizeVoiceprint
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SpeechSynthesizer {
        public static final String NAMESPACE = "SpeechSynthesizer";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            Speak
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioPlayer {
        public static final String NAMESPACE = "AudioPlayer";

        /* loaded from: classes3.dex */
        public enum Behavior {
            unknown,
            REPLACE_ALL
        }

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            Play
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }

        public static Behavior getBehavior(String str) {
            return (Behavior) Enums.getIfPresent(Behavior.class, str).or((Optional) Behavior.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class Scene {
        public static final String NAMESPACE = "Scene";

        /* loaded from: classes3.dex */
        public enum Name {
            unknown,
            Enter
        }

        /* loaded from: classes3.dex */
        public static class Payload {
            @SerializedName("mic_duration")
            public int micDuration;
            @SerializedName("type")
            public String type;
        }

        public static Name getName(String str) {
            return (Name) Enums.getIfPresent(Name.class, str).or((Optional) Name.unknown);
        }
    }
}
