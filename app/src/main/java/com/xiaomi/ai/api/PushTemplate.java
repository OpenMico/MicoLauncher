package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class PushTemplate {

    @NamespaceName(name = "GeneralPush", namespace = AIApiConstants.PushTemplate.NAME)
    /* loaded from: classes3.dex */
    public static class GeneralPush implements InstructionPayload {
        private Optional<Template.Title> title = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<Template.Image> icon = Optional.empty();

        public GeneralPush setTitle(Template.Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Template.Title> getTitle() {
            return this.title;
        }

        public GeneralPush setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public GeneralPush setIcon(Template.Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getIcon() {
            return this.icon;
        }
    }
}
