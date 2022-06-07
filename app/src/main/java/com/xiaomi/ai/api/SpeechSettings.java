package com.xiaomi.ai.api;

import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class SpeechSettings {

    @NamespaceName(name = "OperatedQuery", namespace = "SpeechSettings")
    /* loaded from: classes3.dex */
    public static class OperatedQuery implements EventPayload {
        @Required
        private String query;

        public OperatedQuery() {
        }

        public OperatedQuery(String str) {
            this.query = str;
        }

        @Required
        public OperatedQuery setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }
    }

    @NamespaceName(name = "SetLanguage", namespace = "SpeechSettings")
    /* loaded from: classes3.dex */
    public static class SetLanguage implements InstructionPayload {
        @Required
        private String asr;
        private Optional<String> tts = Optional.empty();

        public SetLanguage() {
        }

        public SetLanguage(String str) {
            this.asr = str;
        }

        @Required
        public SetLanguage setAsr(String str) {
            this.asr = str;
            return this;
        }

        @Required
        public String getAsr() {
            return this.asr;
        }

        public SetLanguage setTts(String str) {
            this.tts = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTts() {
            return this.tts;
        }
    }

    @NamespaceName(name = "UnsupportedLanguage", namespace = "SpeechSettings")
    /* loaded from: classes3.dex */
    public static class UnsupportedLanguage implements InstructionPayload {
        @Required
        private String lang;

        public UnsupportedLanguage() {
        }

        public UnsupportedLanguage(String str) {
            this.lang = str;
        }

        @Required
        public UnsupportedLanguage setLang(String str) {
            this.lang = str;
            return this;
        }

        @Required
        public String getLang() {
            return this.lang;
        }
    }
}
