package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class BuiltinSkills {

    @NamespaceName(name = "PreferredSkills", namespace = AIApiConstants.BuiltinSkills.NAME)
    /* loaded from: classes3.dex */
    public static class PreferredSkills implements ContextPayload {
        @Required
        private List<String> name;
        private Optional<String> tag = Optional.empty();

        public PreferredSkills() {
        }

        public PreferredSkills(List<String> list) {
            this.name = list;
        }

        @Required
        public PreferredSkills setName(List<String> list) {
            this.name = list;
            return this;
        }

        @Required
        public List<String> getName() {
            return this.name;
        }

        public PreferredSkills setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }
    }

    @NamespaceName(name = "Translation.Settings", namespace = AIApiConstants.BuiltinSkills.NAME)
    /* loaded from: classes3.dex */
    public static class TranslationSettings implements ContextPayload {
        @Required
        private boolean boosting;
        @Required
        private String from_lang;
        @Required
        private boolean keep_punctuation;
        @Required
        private String to_lang;

        public TranslationSettings() {
        }

        public TranslationSettings(String str, String str2, boolean z, boolean z2) {
            this.from_lang = str;
            this.to_lang = str2;
            this.boosting = z;
            this.keep_punctuation = z2;
        }

        @Required
        public TranslationSettings setFromLang(String str) {
            this.from_lang = str;
            return this;
        }

        @Required
        public String getFromLang() {
            return this.from_lang;
        }

        @Required
        public TranslationSettings setToLang(String str) {
            this.to_lang = str;
            return this;
        }

        @Required
        public String getToLang() {
            return this.to_lang;
        }

        @Required
        public TranslationSettings setBoosting(boolean z) {
            this.boosting = z;
            return this;
        }

        @Required
        public boolean isBoosting() {
            return this.boosting;
        }

        @Required
        public TranslationSettings setKeepPunctuation(boolean z) {
            this.keep_punctuation = z;
            return this;
        }

        @Required
        public boolean isKeepPunctuation() {
            return this.keep_punctuation;
        }
    }
}
