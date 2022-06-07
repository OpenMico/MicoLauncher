package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Notification {

    @NamespaceName(name = AIApiConstants.General.NAME, namespace = AIApiConstants.Notification.NAME)
    /* loaded from: classes3.dex */
    public static class General implements InstructionPayload {
        @Required
        private GeneralUIAction action;
        @Required
        private String channel_name;
        @Required
        private long duration;
        @Required
        private String guide;
        @Required
        private Template.Image logo;
        @Required
        private String message;
        private Optional<String> secondary_message = Optional.empty();
        private Optional<Template.Image> image = Optional.empty();

        public General() {
        }

        public General(String str, String str2, String str3, long j, Template.Image image, GeneralUIAction generalUIAction) {
            this.channel_name = str;
            this.message = str2;
            this.guide = str3;
            this.duration = j;
            this.logo = image;
            this.action = generalUIAction;
        }

        @Required
        public General setChannelName(String str) {
            this.channel_name = str;
            return this;
        }

        @Required
        public String getChannelName() {
            return this.channel_name;
        }

        @Required
        public General setMessage(String str) {
            this.message = str;
            return this;
        }

        @Required
        public String getMessage() {
            return this.message;
        }

        @Required
        public General setGuide(String str) {
            this.guide = str;
            return this;
        }

        @Required
        public String getGuide() {
            return this.guide;
        }

        @Required
        public General setDuration(long j) {
            this.duration = j;
            return this;
        }

        @Required
        public long getDuration() {
            return this.duration;
        }

        @Required
        public General setLogo(Template.Image image) {
            this.logo = image;
            return this;
        }

        @Required
        public Template.Image getLogo() {
            return this.logo;
        }

        @Required
        public General setAction(GeneralUIAction generalUIAction) {
            this.action = generalUIAction;
            return this;
        }

        @Required
        public GeneralUIAction getAction() {
            return this.action;
        }

        public General setSecondaryMessage(String str) {
            this.secondary_message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSecondaryMessage() {
            return this.secondary_message;
        }

        public General setImage(Template.Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getImage() {
            return this.image;
        }
    }

    /* loaded from: classes3.dex */
    public static class GeneralUIAction {
        private Optional<Template.Launcher> confirm_action = Optional.empty();
        @Required
        private boolean enable_back;
        @Required
        private boolean enable_confirm;

        public GeneralUIAction() {
        }

        public GeneralUIAction(boolean z, boolean z2) {
            this.enable_back = z;
            this.enable_confirm = z2;
        }

        @Required
        public GeneralUIAction setEnableBack(boolean z) {
            this.enable_back = z;
            return this;
        }

        @Required
        public boolean isEnableBack() {
            return this.enable_back;
        }

        @Required
        public GeneralUIAction setEnableConfirm(boolean z) {
            this.enable_confirm = z;
            return this;
        }

        @Required
        public boolean isEnableConfirm() {
            return this.enable_confirm;
        }

        public GeneralUIAction setConfirmAction(Template.Launcher launcher) {
            this.confirm_action = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Template.Launcher> getConfirmAction() {
            return this.confirm_action;
        }
    }
}
