package com.xiaomi.ai.api;

import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class SpeechSynthesizer {

    @NamespaceName(name = "FinishSpeakStream", namespace = "SpeechSynthesizer")
    /* loaded from: classes3.dex */
    public static class FinishSpeakStream implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum TTSEmotionCategory {
        DEFAULT(0),
        HAPPY(1),
        WARM(2),
        ANGRY(3),
        SAD(4),
        SHY(5),
        SURPRISE(6);
        
        private int id;

        TTSEmotionCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TTSEmotionLevel {
        LOW(0),
        MEDIUM(1),
        HIGH(2);
        
        private int id;

        TTSEmotionLevel(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "Speak", namespace = "SpeechSynthesizer")
    /* loaded from: classes3.dex */
    public static class Speak implements InstructionPayload {
        @Required
        private String text;
        private Optional<String> url = Optional.empty();
        private Optional<Integer> sample_rate = Optional.empty();
        private Optional<TTSEmotion> emotion = Optional.empty();
        private Optional<Common.TTSCodec> codec = Optional.empty();

        public Speak() {
        }

        public Speak(String str) {
            this.text = str;
        }

        @Required
        public Speak setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public Speak setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public Speak setSampleRate(int i) {
            this.sample_rate = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSampleRate() {
            return this.sample_rate;
        }

        public Speak setEmotion(TTSEmotion tTSEmotion) {
            this.emotion = Optional.ofNullable(tTSEmotion);
            return this;
        }

        public Optional<TTSEmotion> getEmotion() {
            return this.emotion;
        }

        public Speak setCodec(Common.TTSCodec tTSCodec) {
            this.codec = Optional.ofNullable(tTSCodec);
            return this;
        }

        public Optional<Common.TTSCodec> getCodec() {
            return this.codec;
        }
    }

    @NamespaceName(name = "Synthesize", namespace = "SpeechSynthesizer")
    /* loaded from: classes3.dex */
    public static class Synthesize implements EventPayload {
        @Required
        private String text;
        private Optional<Settings.TtsConfig> tts = Optional.empty();
        private Optional<Boolean> need_avatar = Optional.empty();

        public Synthesize() {
        }

        public Synthesize(String str) {
            this.text = str;
        }

        @Required
        public Synthesize setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public Synthesize setTts(Settings.TtsConfig ttsConfig) {
            this.tts = Optional.ofNullable(ttsConfig);
            return this;
        }

        public Optional<Settings.TtsConfig> getTts() {
            return this.tts;
        }

        public Synthesize setNeedAvatar(boolean z) {
            this.need_avatar = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedAvatar() {
            return this.need_avatar;
        }
    }

    /* loaded from: classes3.dex */
    public static class TTSEmotion {
        @Required
        private TTSEmotionCategory category;
        @Required
        private TTSEmotionLevel level;

        public TTSEmotion() {
        }

        public TTSEmotion(TTSEmotionCategory tTSEmotionCategory, TTSEmotionLevel tTSEmotionLevel) {
            this.category = tTSEmotionCategory;
            this.level = tTSEmotionLevel;
        }

        @Required
        public TTSEmotion setCategory(TTSEmotionCategory tTSEmotionCategory) {
            this.category = tTSEmotionCategory;
            return this;
        }

        @Required
        public TTSEmotionCategory getCategory() {
            return this.category;
        }

        @Required
        public TTSEmotion setLevel(TTSEmotionLevel tTSEmotionLevel) {
            this.level = tTSEmotionLevel;
            return this;
        }

        @Required
        public TTSEmotionLevel getLevel() {
            return this.level;
        }
    }
}
