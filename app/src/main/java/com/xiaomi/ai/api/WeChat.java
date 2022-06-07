package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class WeChat {

    @NamespaceName(name = "Cancel", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class Cancel implements InstructionPayload {
    }

    @NamespaceName(name = "ReadMessage", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class ReadMessage implements InstructionPayload {
        @Required
        private List<String> contacts;
        @Required
        private int number;

        public ReadMessage() {
        }

        public ReadMessage(List<String> list, int i) {
            this.contacts = list;
            this.number = i;
        }

        @Required
        public ReadMessage setContacts(List<String> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<String> getContacts() {
            return this.contacts;
        }

        @Required
        public ReadMessage setNumber(int i) {
            this.number = i;
            return this;
        }

        @Required
        public int getNumber() {
            return this.number;
        }
    }

    @NamespaceName(name = "SendLocation", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class SendLocation implements InstructionPayload {
        @Required
        private List<String> contacts;

        public SendLocation() {
        }

        public SendLocation(List<String> list) {
            this.contacts = list;
        }

        @Required
        public SendLocation setContacts(List<String> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<String> getContacts() {
            return this.contacts;
        }
    }

    @NamespaceName(name = "SendMessage", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class SendMessage implements InstructionPayload {
        @Required
        private List<String> contacts;
        private Optional<String> text = Optional.empty();

        public SendMessage() {
        }

        public SendMessage(List<String> list) {
            this.contacts = list;
        }

        @Required
        public SendMessage setContacts(List<String> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<String> getContacts() {
            return this.contacts;
        }

        public SendMessage setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }
    }

    @NamespaceName(name = "SendPhoto", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class SendPhoto implements InstructionPayload {
        @Required
        private List<String> contacts;

        public SendPhoto() {
        }

        public SendPhoto(List<String> list) {
            this.contacts = list;
        }

        @Required
        public SendPhoto setContacts(List<String> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<String> getContacts() {
            return this.contacts;
        }
    }

    @NamespaceName(name = "SendVoiceMessage", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class SendVoiceMessage implements InstructionPayload {
        @Required
        private List<String> contacts;
        private Optional<String> text = Optional.empty();

        public SendVoiceMessage() {
        }

        public SendVoiceMessage(List<String> list) {
            this.contacts = list;
        }

        @Required
        public SendVoiceMessage setContacts(List<String> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<String> getContacts() {
            return this.contacts;
        }

        public SendVoiceMessage setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }
    }

    /* loaded from: classes3.dex */
    public static class TtsScripts {
        @Required
        private String ask_for_contact;
        @Required
        private String ask_for_content;
        @Required
        private String found_multiple_contacts;
        @Required
        private String found_zero_contacts;
        @Required
        private String no_new_messages;
        @Required
        private String ok;

        public TtsScripts() {
        }

        public TtsScripts(String str, String str2, String str3, String str4, String str5, String str6) {
            this.ask_for_content = str;
            this.ask_for_contact = str2;
            this.found_zero_contacts = str3;
            this.found_multiple_contacts = str4;
            this.no_new_messages = str5;
            this.ok = str6;
        }

        @Required
        public TtsScripts setAskForContent(String str) {
            this.ask_for_content = str;
            return this;
        }

        @Required
        public String getAskForContent() {
            return this.ask_for_content;
        }

        @Required
        public TtsScripts setAskForContact(String str) {
            this.ask_for_contact = str;
            return this;
        }

        @Required
        public String getAskForContact() {
            return this.ask_for_contact;
        }

        @Required
        public TtsScripts setFoundZeroContacts(String str) {
            this.found_zero_contacts = str;
            return this;
        }

        @Required
        public String getFoundZeroContacts() {
            return this.found_zero_contacts;
        }

        @Required
        public TtsScripts setFoundMultipleContacts(String str) {
            this.found_multiple_contacts = str;
            return this;
        }

        @Required
        public String getFoundMultipleContacts() {
            return this.found_multiple_contacts;
        }

        @Required
        public TtsScripts setNoNewMessages(String str) {
            this.no_new_messages = str;
            return this;
        }

        @Required
        public String getNoNewMessages() {
            return this.no_new_messages;
        }

        @Required
        public TtsScripts setOk(String str) {
            this.ok = str;
            return this;
        }

        @Required
        public String getOk() {
            return this.ok;
        }
    }

    @NamespaceName(name = "UpdateLocal", namespace = AIApiConstants.WeChat.NAME)
    /* loaded from: classes3.dex */
    public static class UpdateLocal {
        private Optional<TtsScripts> tts = Optional.empty();

        public UpdateLocal setTts(TtsScripts ttsScripts) {
            this.tts = Optional.ofNullable(ttsScripts);
            return this;
        }

        public Optional<TtsScripts> getTts() {
            return this.tts;
        }
    }
}
