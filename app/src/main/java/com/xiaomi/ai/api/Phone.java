package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Phone {

    @NamespaceName(name = "DialBack", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class DialBack implements InstructionPayload {
    }

    @NamespaceName(name = "HangUp", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class HangUp implements InstructionPayload {
    }

    @NamespaceName(name = "PickUp", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class PickUp implements InstructionPayload {
    }

    @NamespaceName(name = "Redial", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class Redial implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum CallMode {
        DEFAULT(-1),
        VOICE(0),
        VIDEO(1),
        HANDS_FREE(2);
        
        private int id;

        CallMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CallType {
        UNKNOWN(-1),
        RECENT_MISSED_CALL(0),
        RECENT_ANSWERED_CALL(1),
        RECENT_OUTGOING_CALL(2),
        RECENT_ALL_CALL(3),
        RECENT_INCOMING_CALL(4);
        
        private int id;

        CallType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CardType {
        UNKNOWN(-1),
        DEFAULT(0),
        CARD_ONE(1),
        CARD_TWO(2),
        SIM_CARD(3);
        
        private int id;

        CardType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ClickPurpose {
        UNKNOWN(-1),
        MAKE_CALL(0),
        CONTACT_DETAIL(1),
        ACTIVE_CARD(2),
        SEND_MESSAGE(3);
        
        private int id;

        ClickPurpose(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ContactCategory {
        UNKNOWN(-1),
        APP(0),
        WATCH(1),
        TV(2);
        
        private int id;

        ContactCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum NameType {
        UNKNOWN(-1),
        NORMAL(0),
        DIGIT(1),
        FAMOUS(2),
        RELATIVE(3),
        YELLOW_PAGE(4);
        
        private int id;

        NameType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PhoneOperationType {
        UNKNOWN(-1),
        MAKE_CALL(0),
        SEND_MESSAGE(2);
        
        private int id;

        PhoneOperationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ShowMessageType {
        MESSAGE_DRAFT(0),
        MESSAGE_RESULT(1);
        
        private int id;

        ShowMessageType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class Contact {
        @Required
        private String id;
        @Required
        private String name;
        @Required
        private String number;
        private Optional<String> tag = Optional.empty();
        private Optional<Template.AndroidIntent> intent = Optional.empty();
        private Optional<Boolean> auto_dial = Optional.empty();
        private Optional<NameType> name_type = Optional.empty();
        private Optional<ContactCategory> category = Optional.empty();

        public Contact() {
        }

        public Contact(String str, String str2, String str3) {
            this.name = str;
            this.number = str2;
            this.id = str3;
        }

        @Required
        public Contact setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Contact setNumber(String str) {
            this.number = str;
            return this;
        }

        @Required
        public String getNumber() {
            return this.number;
        }

        @Required
        public Contact setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        public Contact setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public Contact setIntent(Template.AndroidIntent androidIntent) {
            this.intent = Optional.ofNullable(androidIntent);
            return this;
        }

        public Optional<Template.AndroidIntent> getIntent() {
            return this.intent;
        }

        public Contact setAutoDial(boolean z) {
            this.auto_dial = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAutoDial() {
            return this.auto_dial;
        }

        public Contact setNameType(NameType nameType) {
            this.name_type = Optional.ofNullable(nameType);
            return this;
        }

        public Optional<NameType> getNameType() {
            return this.name_type;
        }

        public Contact setCategory(ContactCategory contactCategory) {
            this.category = Optional.ofNullable(contactCategory);
            return this;
        }

        public Optional<ContactCategory> getCategory() {
            return this.category;
        }
    }

    @NamespaceName(name = "LocalCallingData", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class LocalCallingData implements ContextPayload {
        @Required
        private String action;
        private Optional<List<Contact>> contacts = Optional.empty();

        public LocalCallingData() {
        }

        public LocalCallingData(String str) {
            this.action = str;
        }

        @Required
        public LocalCallingData setAction(String str) {
            this.action = str;
            return this;
        }

        @Required
        public String getAction() {
            return this.action;
        }

        public LocalCallingData setContacts(List<Contact> list) {
            this.contacts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Contact>> getContacts() {
            return this.contacts;
        }
    }

    @NamespaceName(name = "MakeCall", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class MakeCall implements InstructionPayload {
        @Required
        private CardType card_type;
        private Optional<Contact> contact = Optional.empty();
        private Optional<CallType> call_type = Optional.empty();
        private Optional<CallMode> call_mode = Optional.empty();
        private Optional<Template.Image> skill_icon = Optional.empty();

        public MakeCall() {
        }

        public MakeCall(CardType cardType) {
            this.card_type = cardType;
        }

        @Required
        public MakeCall setCardType(CardType cardType) {
            this.card_type = cardType;
            return this;
        }

        @Required
        public CardType getCardType() {
            return this.card_type;
        }

        public MakeCall setContact(Contact contact) {
            this.contact = Optional.ofNullable(contact);
            return this;
        }

        public Optional<Contact> getContact() {
            return this.contact;
        }

        public MakeCall setCallType(CallType callType) {
            this.call_type = Optional.ofNullable(callType);
            return this;
        }

        public Optional<CallType> getCallType() {
            return this.call_type;
        }

        public MakeCall setCallMode(CallMode callMode) {
            this.call_mode = Optional.ofNullable(callMode);
            return this;
        }

        public Optional<CallMode> getCallMode() {
            return this.call_mode;
        }

        public MakeCall setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class NameSlot {
        @Required
        private boolean is_qrw;
        @Required
        private String name;
        @Required
        private NameType name_type;
        @Required
        private boolean offline_asr;
        @Required
        private String prefix;
        @Required
        private String suffix;

        public NameSlot() {
        }

        public NameSlot(String str, String str2, String str3, boolean z, NameType nameType, boolean z2) {
            this.name = str;
            this.prefix = str2;
            this.suffix = str3;
            this.is_qrw = z;
            this.name_type = nameType;
            this.offline_asr = z2;
        }

        @Required
        public NameSlot setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public NameSlot setPrefix(String str) {
            this.prefix = str;
            return this;
        }

        @Required
        public String getPrefix() {
            return this.prefix;
        }

        @Required
        public NameSlot setSuffix(String str) {
            this.suffix = str;
            return this;
        }

        @Required
        public String getSuffix() {
            return this.suffix;
        }

        @Required
        public NameSlot setIsQrw(boolean z) {
            this.is_qrw = z;
            return this;
        }

        @Required
        public boolean isQrw() {
            return this.is_qrw;
        }

        @Required
        public NameSlot setNameType(NameType nameType) {
            this.name_type = nameType;
            return this;
        }

        @Required
        public NameType getNameType() {
            return this.name_type;
        }

        @Required
        public NameSlot setOfflineAsr(boolean z) {
            this.offline_asr = z;
            return this;
        }

        @Required
        public boolean isOfflineAsr() {
            return this.offline_asr;
        }
    }

    /* loaded from: classes3.dex */
    public static class NicknameInfo {
        @Required
        private String name;
        @Required
        private String nickname;

        public NicknameInfo() {
        }

        public NicknameInfo(String str, String str2) {
            this.name = str;
            this.nickname = str2;
        }

        @Required
        public NicknameInfo setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public NicknameInfo setNickname(String str) {
            this.nickname = str;
            return this;
        }

        @Required
        public String getNickname() {
            return this.nickname;
        }
    }

    /* loaded from: classes3.dex */
    public static class PhoneIntention {
        @Required
        private String action;
        @Required
        private CardType card_type;
        @Required
        private String name;
        @Required
        private List<NameSlot> name_slots;
        private Optional<Integer> chosen_index = Optional.empty();
        private Optional<Integer> blocked_round = Optional.empty();
        private Optional<Boolean> multiple_turn = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<String> norm_tag = Optional.empty();
        private Optional<NicknameInfo> nickname_info = Optional.empty();
        private Optional<List<Contact>> contacts = Optional.empty();
        private Optional<String> query = Optional.empty();
        private Optional<List<String>> disable_instructions = Optional.empty();
        private Optional<ContactCategory> category = Optional.empty();
        private Optional<String> tail_numbers = Optional.empty();
        private Optional<CallMode> call_mode = Optional.empty();
        private Optional<String> message_text = Optional.empty();
        private Optional<Boolean> had_chosen = Optional.empty();

        public PhoneIntention() {
        }

        public PhoneIntention(String str, String str2, List<NameSlot> list, CardType cardType) {
            this.action = str;
            this.name = str2;
            this.name_slots = list;
            this.card_type = cardType;
        }

        @Required
        public PhoneIntention setAction(String str) {
            this.action = str;
            return this;
        }

        @Required
        public String getAction() {
            return this.action;
        }

        @Required
        public PhoneIntention setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public PhoneIntention setNameSlots(List<NameSlot> list) {
            this.name_slots = list;
            return this;
        }

        @Required
        public List<NameSlot> getNameSlots() {
            return this.name_slots;
        }

        @Required
        public PhoneIntention setCardType(CardType cardType) {
            this.card_type = cardType;
            return this;
        }

        @Required
        public CardType getCardType() {
            return this.card_type;
        }

        public PhoneIntention setChosenIndex(int i) {
            this.chosen_index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getChosenIndex() {
            return this.chosen_index;
        }

        public PhoneIntention setBlockedRound(int i) {
            this.blocked_round = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getBlockedRound() {
            return this.blocked_round;
        }

        public PhoneIntention setMultipleTurn(boolean z) {
            this.multiple_turn = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMultipleTurn() {
            return this.multiple_turn;
        }

        public PhoneIntention setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public PhoneIntention setNormTag(String str) {
            this.norm_tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNormTag() {
            return this.norm_tag;
        }

        public PhoneIntention setNicknameInfo(NicknameInfo nicknameInfo) {
            this.nickname_info = Optional.ofNullable(nicknameInfo);
            return this;
        }

        public Optional<NicknameInfo> getNicknameInfo() {
            return this.nickname_info;
        }

        public PhoneIntention setContacts(List<Contact> list) {
            this.contacts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Contact>> getContacts() {
            return this.contacts;
        }

        public PhoneIntention setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public PhoneIntention setDisableInstructions(List<String> list) {
            this.disable_instructions = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getDisableInstructions() {
            return this.disable_instructions;
        }

        public PhoneIntention setCategory(ContactCategory contactCategory) {
            this.category = Optional.ofNullable(contactCategory);
            return this;
        }

        public Optional<ContactCategory> getCategory() {
            return this.category;
        }

        public PhoneIntention setTailNumbers(String str) {
            this.tail_numbers = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTailNumbers() {
            return this.tail_numbers;
        }

        public PhoneIntention setCallMode(CallMode callMode) {
            this.call_mode = Optional.ofNullable(callMode);
            return this;
        }

        public Optional<CallMode> getCallMode() {
            return this.call_mode;
        }

        public PhoneIntention setMessageText(String str) {
            this.message_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessageText() {
            return this.message_text;
        }

        public PhoneIntention setHadChosen(boolean z) {
            this.had_chosen = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHadChosen() {
            return this.had_chosen;
        }
    }

    @NamespaceName(name = "SelectSimCard", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class SelectSimCard implements InstructionPayload {
        @Required
        private Template.Launcher launcher;
        @Required
        private PhoneOperationType type;
        private Optional<List<Contact>> contacts = Optional.empty();
        private Optional<Template.Image> skill_icon = Optional.empty();

        public SelectSimCard() {
        }

        public SelectSimCard(PhoneOperationType phoneOperationType, Template.Launcher launcher) {
            this.type = phoneOperationType;
            this.launcher = launcher;
        }

        @Required
        public SelectSimCard setType(PhoneOperationType phoneOperationType) {
            this.type = phoneOperationType;
            return this;
        }

        @Required
        public PhoneOperationType getType() {
            return this.type;
        }

        @Required
        public SelectSimCard setLauncher(Template.Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Template.Launcher getLauncher() {
            return this.launcher;
        }

        public SelectSimCard setContacts(List<Contact> list) {
            this.contacts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Contact>> getContacts() {
            return this.contacts;
        }

        public SelectSimCard setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "SendMessage", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class SendMessage implements InstructionPayload {
        @Required
        private CardType card_type;
        private Optional<List<Contact>> contacts = Optional.empty();
        private Optional<String> message = Optional.empty();

        public SendMessage() {
        }

        public SendMessage(CardType cardType) {
            this.card_type = cardType;
        }

        @Required
        public SendMessage setCardType(CardType cardType) {
            this.card_type = cardType;
            return this;
        }

        @Required
        public CardType getCardType() {
            return this.card_type;
        }

        public SendMessage setContacts(List<Contact> list) {
            this.contacts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Contact>> getContacts() {
            return this.contacts;
        }

        public SendMessage setMessage(String str) {
            this.message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessage() {
            return this.message;
        }
    }

    @NamespaceName(name = "SetNickname", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class SetNickname implements InstructionPayload {
        private Optional<String> id = Optional.empty();
        @Required
        private String name;
        @Required
        private String nickname;

        public SetNickname() {
        }

        public SetNickname(String str, String str2) {
            this.name = str;
            this.nickname = str2;
        }

        @Required
        public SetNickname setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public SetNickname setNickname(String str) {
            this.nickname = str;
            return this;
        }

        @Required
        public String getNickname() {
            return this.nickname;
        }

        public SetNickname setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "ShowContacts", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class ShowContacts implements InstructionPayload {
        @Required
        private List<Contact> contacts;
        @Required
        private boolean one_person;
        private Optional<Template.Image> skill_icon = Optional.empty();
        private Optional<CardType> card_type = Optional.empty();
        private Optional<Boolean> match_recent_and_call = Optional.empty();
        private Optional<ClickPurpose> click_purpose = Optional.empty();
        private Optional<CallMode> call_mode = Optional.empty();

        public ShowContacts() {
        }

        public ShowContacts(List<Contact> list, boolean z) {
            this.contacts = list;
            this.one_person = z;
        }

        @Required
        public ShowContacts setContacts(List<Contact> list) {
            this.contacts = list;
            return this;
        }

        @Required
        public List<Contact> getContacts() {
            return this.contacts;
        }

        @Required
        public ShowContacts setOnePerson(boolean z) {
            this.one_person = z;
            return this;
        }

        @Required
        public boolean isOnePerson() {
            return this.one_person;
        }

        public ShowContacts setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }

        public ShowContacts setCardType(CardType cardType) {
            this.card_type = Optional.ofNullable(cardType);
            return this;
        }

        public Optional<CardType> getCardType() {
            return this.card_type;
        }

        public ShowContacts setMatchRecentAndCall(boolean z) {
            this.match_recent_and_call = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isMatchRecentAndCall() {
            return this.match_recent_and_call;
        }

        public ShowContacts setClickPurpose(ClickPurpose clickPurpose) {
            this.click_purpose = Optional.ofNullable(clickPurpose);
            return this;
        }

        public Optional<ClickPurpose> getClickPurpose() {
            return this.click_purpose;
        }

        public ShowContacts setCallMode(CallMode callMode) {
            this.call_mode = Optional.ofNullable(callMode);
            return this;
        }

        public Optional<CallMode> getCallMode() {
            return this.call_mode;
        }
    }

    @NamespaceName(name = "ShowMessage", namespace = AIApiConstants.Phone.NAME)
    /* loaded from: classes3.dex */
    public static class ShowMessage implements InstructionPayload {
        @Required
        private CardType card_type;
        @Required
        private Template.Launcher launcher;
        @Required
        private ShowMessageType type;
        private Optional<List<Contact>> contacts = Optional.empty();
        private Optional<String> message = Optional.empty();
        private Optional<Template.Image> skill_icon = Optional.empty();

        public ShowMessage() {
        }

        public ShowMessage(ShowMessageType showMessageType, CardType cardType, Template.Launcher launcher) {
            this.type = showMessageType;
            this.card_type = cardType;
            this.launcher = launcher;
        }

        @Required
        public ShowMessage setType(ShowMessageType showMessageType) {
            this.type = showMessageType;
            return this;
        }

        @Required
        public ShowMessageType getType() {
            return this.type;
        }

        @Required
        public ShowMessage setCardType(CardType cardType) {
            this.card_type = cardType;
            return this;
        }

        @Required
        public CardType getCardType() {
            return this.card_type;
        }

        @Required
        public ShowMessage setLauncher(Template.Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Template.Launcher getLauncher() {
            return this.launcher;
        }

        public ShowMessage setContacts(List<Contact> list) {
            this.contacts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Contact>> getContacts() {
            return this.contacts;
        }

        public ShowMessage setMessage(String str) {
            this.message = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMessage() {
            return this.message;
        }

        public ShowMessage setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }
}
