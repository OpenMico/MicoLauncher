package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Alerts;
import com.xiaomi.ai.api.ApplicationSettings;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.ai.api.Maps;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Template {

    @NamespaceName(name = "Help", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Help implements InstructionPayload {
    }

    @NamespaceName(name = "MemoDraftPanel", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class MemoDraftPanel implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum AlarmOperation {
        UNKNOWN(-1),
        CREATE(0),
        QUERY(1),
        OPEN(2),
        CLOSE(3),
        PAUSE(4),
        PROCEED(5),
        DELETE(6),
        STOP(7);
        
        private int id;

        AlarmOperation(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AttachmentType {
        FEEDBACK(0),
        ADS(1);
        
        private int id;

        AttachmentType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum BackgroundColorType {
        GRADIENT(0),
        PURE(1);
        
        private int id;

        BackgroundColorType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CalculatorType {
        CALCULATE(0),
        EXCHANGE_RATE(1),
        UNIT_CONVERSION(2),
        DISCOUNT_CALCULATION(3),
        CAPITAL(4),
        RELATIVE(5),
        GUIDE(6),
        COMPUTE_TAX(7);
        
        private int id;

        CalculatorType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DirectionDef {
        UNKNOWN(-1),
        VERTICAL(0),
        HORIZONTAL(1);
        
        private int id;

        DirectionDef(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DisplayControlType {
        UNKNOWN(-1),
        DISAPPEAR_BY_TTS_FINISHED(0),
        DISAPPEAR_BY_FIRST_AUDIO_FINISHED(1);
        
        private int id;

        DisplayControlType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum DisplayMode {
        SMALL(0),
        FULL(1);
        
        private int id;

        DisplayMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum GeneralDisplayType {
        UNKNOWN(-1),
        BILLBOARD(0),
        MULTI_IMAGE_1(1),
        MULTI_IMAGE_2(2),
        ARROW_TO_RIGHT(3);
        
        private int id;

        GeneralDisplayType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum GeneralUIType {
        COMPOSITION(0);
        
        private int id;

        GeneralUIType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum GradientOrientationType {
        TOPRIGHT_BOTTOMLEFT(0),
        TOPLEFT_BOTTOMRIGHT(1),
        TOP_BOTTOM(2),
        LEFT_RIGHT(3);
        
        private int id;

        GradientOrientationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum H5PageLoadType {
        URL(0),
        DATA(1);
        
        private int id;

        H5PageLoadType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum H5PageSource {
        INNER(0),
        BAIDU_BAIKE(1);
        
        private int id;

        H5PageSource(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum H5ParamType {
        UNKNOWN(-1),
        TRANSLATION(0),
        DICTIONARIES(1),
        TRANSLATION_V2(2),
        URL_ONLY(3),
        QABOT(4),
        WIKI_DETAIL(5),
        LISTS_DETAIL(6),
        VIDEO(7),
        RECIPE_DETAIL(8),
        RECIPE_LIST(9),
        COURSE_SCHEDULE(10),
        STATION(11),
        COMPOSITION(12),
        OPENPLATFORM(13),
        TRANSLATION_V3(14),
        DICTIONARY_TARGET(15),
        ASSIST_MEMO(16),
        Anniversary(17),
        FEIHUALING(18),
        SPORTS(19);
        
        private int id;

        H5ParamType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum KnowledgeType {
        WORLDEST(0),
        WONDER_WHY(1);
        
        private int id;

        KnowledgeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ListsItemDisplayType {
        UNKNOWN(-1),
        RIGHT_IMAGE(0),
        LEFT_IMAGE(1),
        TEXT_ONLY(2);
        
        private int id;

        ListsItemDisplayType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ListsType {
        QABOT_COMPOSITION(0),
        POEM_VERSE(1);
        
        private int id;

        ListsType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MessageSendDeviceCategory {
        PHONE(0),
        SOUNDBOX(1),
        TV(2),
        PC(3),
        SOUNDBOX_APP(4);
        
        private int id;

        MessageSendDeviceCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MessageSendDeviceType {
        FAMILY(0),
        PERSONAL(1);
        
        private int id;

        MessageSendDeviceType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MessageSendType {
        SINGLE(0),
        GROUP(1);
        
        private int id;

        MessageSendType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayInfoItemDisplayType {
        UNKNOWN(-1),
        HORIZONTAL(0),
        VERTICAL(1),
        VERTICAL_LESS(2),
        DETAIL_TITLE_CENTER(3);
        
        private int id;

        PlayInfoItemDisplayType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayInfoType {
        UNKNOWN(-1),
        JOKE(0),
        POEM(1),
        MUSIC(2),
        STATION(3),
        VOICE_NEWS(4),
        SOUND(5),
        TRANSLATION(6);
        
        private int id;

        PlayInfoType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum QabotImageType {
        SMALL(0),
        FULL(1);
        
        private int id;

        QabotImageType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum QabotParagraphType {
        PARAGRAPH(0),
        PARAGRAPH_TURN(1);
        
        private int id;

        QabotParagraphType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum QabotType {
        WIKI(0),
        PARAGRAPH(1);
        
        private int id;

        QabotType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum QueryType {
        SONG_SEARCH(0),
        SONG_RECOMMENDATION(1),
        TAG_SEARCH(2),
        ARTIST_SEARCH(3),
        OTHER(4);
        
        private int id;

        QueryType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ResolveWordsType {
        DETAILS(0),
        MORE_HOMOPHONES(1),
        TARGET(2);
        
        private int id;

        ResolveWordsType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SearchHitLevel {
        ACCURATE(0),
        FALLBACK_OPT_INTENTION(1),
        FALLBACK_FUZZY(2),
        FALLBACK_LESS_SLOT(3),
        FALLBACK_RANDOM(4),
        HOT_RECOMMEND(5),
        ERROR_RANDOM(6);
        
        private int id;

        SearchHitLevel(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ShowOneCardType {
        START(0),
        APPEND(1);
        
        private int id;

        ShowOneCardType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SmartHotelCategory {
        SUMMARY(0),
        WIFI(1),
        RESTAURANT(2),
        BREAKFAST(3),
        LOBBYBAR(4),
        GYM(5),
        COSMETIC(6),
        SHOPPING(7),
        ATM(8),
        BUSINESS(9),
        MEETING(10),
        PARKING(11),
        STADIUM(12),
        CHILDCARE(13),
        GARDEN(14),
        AROUND(15),
        POOL(16),
        WASH_HOUSE(17),
        CARDS_ROOM(18),
        FRONT_PHONE(19),
        CHECKOUT_TIME(20),
        COMPLAINT_PHONE(21),
        UMBRELLA_BORROW(22),
        TAKEOUT_COLLECT(23),
        DELIVERY_COLLECT(24),
        LEFT_LUGGAGE(25),
        LOCATION(26);
        
        private int id;

        SmartHotelCategory(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SportMatchStatus {
        UNKNOWN(-1),
        NOT_STARTED(0),
        ON_GOING(1),
        FINISHED(2),
        IN_LIVE(3),
        LIVE_EXCEPTION(4);
        
        private int id;

        SportMatchStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SwitchPanelType {
        UNKNOWN(-1),
        DEFAULT(0),
        COUNTDOWN(1);
        
        private int id;

        SwitchPanelType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TableType {
        UNKNOWN(-1),
        NBA_STANDINGS(0),
        WORLD_CUP_STANDINGS(1),
        WORLD_CUP_SQUAD_LISTS(2),
        WORLD_CUP_BEST_GOALSCORERS(3);
        
        private int id;

        TableType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TaskLoadType {
        REFRESH(0),
        APPEND(1),
        QUIT(2);
        
        private int id;

        TaskLoadType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TimeOfDayType {
        UNKNOWN(-1),
        MORNING(0),
        AFTERNOON(1);
        
        private int id;

        TimeOfDayType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TimeType {
        DATE(0),
        TIME(1);
        
        private int id;

        TimeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ToastPosition {
        TOP(0);
        
        private int id;

        ToastPosition(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TranslationType {
        DICTIONARY(0),
        MACHINE(1);
        
        private int id;

        TranslationType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoActorRole {
        DEFAULT(0),
        DIRECTOR(1),
        MAIN_ACTOR(2),
        ACTOR(3),
        PRODUCER(4);
        
        private int id;

        VideoActorRole(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoLengthType {
        LONG(0),
        SHORT(1);
        
        private int id;

        VideoLengthType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VideoShowStatus {
        OFFLINE(0),
        PRE_ONLINE(1),
        ONLINE(2),
        PRE_SALE(3),
        FINISHED(4),
        NOT_FINISHED(5);
        
        private int id;

        VideoShowStatus(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum WikiEventType {
        POLYSEMY(0),
        ENTITY(1);
        
        private int id;

        WikiEventType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AIMemory", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class AIMemory implements InstructionPayload {
        @Required
        private String content;
        private Optional<List<AIMemoryLauncher>> launchers = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        @Required
        private Title title;

        public AIMemory() {
        }

        public AIMemory(Title title, String str) {
            this.title = title;
            this.content = str;
        }

        @Required
        public AIMemory setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public AIMemory setContent(String str) {
            this.content = str;
            return this;
        }

        @Required
        public String getContent() {
            return this.content;
        }

        public AIMemory setLaunchers(List<AIMemoryLauncher> list) {
            this.launchers = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AIMemoryLauncher>> getLaunchers() {
            return this.launchers;
        }

        public AIMemory setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class AIMemoryLauncher {
        private Optional<Image> icon = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        @Required
        private String name;

        public AIMemoryLauncher() {
        }

        public AIMemoryLauncher(String str) {
            this.name = str;
        }

        @Required
        public AIMemoryLauncher setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public AIMemoryLauncher setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public AIMemoryLauncher setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "AIShortcut", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class AIShortcut implements InstructionPayload {
        private Optional<Image> bg_image = Optional.empty();
        private Optional<AIShortcutColor> color = Optional.empty();
        @Required
        private Image icon;
        @Required
        private Nlp.InvokeNlpRequest invoke_nlp_request;
        @Required
        private String pkg_name;
        @Required
        private String text;

        public AIShortcut() {
        }

        public AIShortcut(String str, String str2, Image image, Nlp.InvokeNlpRequest invokeNlpRequest) {
            this.pkg_name = str;
            this.text = str2;
            this.icon = image;
            this.invoke_nlp_request = invokeNlpRequest;
        }

        @Required
        public AIShortcut setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public AIShortcut setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public AIShortcut setIcon(Image image) {
            this.icon = image;
            return this;
        }

        @Required
        public Image getIcon() {
            return this.icon;
        }

        @Required
        public AIShortcut setInvokeNlpRequest(Nlp.InvokeNlpRequest invokeNlpRequest) {
            this.invoke_nlp_request = invokeNlpRequest;
            return this;
        }

        @Required
        public Nlp.InvokeNlpRequest getInvokeNlpRequest() {
            return this.invoke_nlp_request;
        }

        public AIShortcut setBgImage(Image image) {
            this.bg_image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getBgImage() {
            return this.bg_image;
        }

        public AIShortcut setColor(AIShortcutColor aIShortcutColor) {
            this.color = Optional.ofNullable(aIShortcutColor);
            return this;
        }

        public Optional<AIShortcutColor> getColor() {
            return this.color;
        }
    }

    /* loaded from: classes3.dex */
    public static class AIShortcutColor {
        private Optional<String> for_text = Optional.empty();
        private Optional<String> for_bg = Optional.empty();
        private Optional<String> for_border = Optional.empty();
        private Optional<String> for_icon_border = Optional.empty();

        public AIShortcutColor setForText(String str) {
            this.for_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForText() {
            return this.for_text;
        }

        public AIShortcutColor setForBg(String str) {
            this.for_bg = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForBg() {
            return this.for_bg;
        }

        public AIShortcutColor setForBorder(String str) {
            this.for_border = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForBorder() {
            return this.for_border;
        }

        public AIShortcutColor setForIconBorder(String str) {
            this.for_icon_border = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getForIconBorder() {
            return this.for_icon_border;
        }
    }

    /* loaded from: classes3.dex */
    public static class AbstractItem {
        @Deprecated
        private Optional<String> entity = Optional.empty();
        @Required
        private String k;
        @Required
        private String v;

        public AbstractItem() {
        }

        public AbstractItem(String str, String str2) {
            this.k = str;
            this.v = str2;
        }

        @Required
        public AbstractItem setK(String str) {
            this.k = str;
            return this;
        }

        @Required
        public String getK() {
            return this.k;
        }

        @Required
        public AbstractItem setV(String str) {
            this.v = str;
            return this;
        }

        @Required
        public String getV() {
            return this.v;
        }

        @Deprecated
        public AbstractItem setEntity(String str) {
            this.entity = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getEntity() {
            return this.entity;
        }
    }

    @NamespaceName(name = "AdjustProgress", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class AdjustProgress implements InstructionPayload {
        @Required
        private Image skill_icon;
        @Required
        private Title title;
        private Optional<Image> left_icon = Optional.empty();
        private Optional<Image> right_icon = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<LocalTarget> target = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public AdjustProgress() {
        }

        public AdjustProgress(Title title, Image image) {
            this.title = title;
            this.skill_icon = image;
        }

        @Required
        public AdjustProgress setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public AdjustProgress setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public AdjustProgress setLeftIcon(Image image) {
            this.left_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getLeftIcon() {
            return this.left_icon;
        }

        public AdjustProgress setRightIcon(Image image) {
            this.right_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getRightIcon() {
            return this.right_icon;
        }

        public AdjustProgress setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public AdjustProgress setTarget(LocalTarget localTarget) {
            this.target = Optional.ofNullable(localTarget);
            return this;
        }

        public Optional<LocalTarget> getTarget() {
            return this.target;
        }

        public AdjustProgress setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public AdjustProgress setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class AdsAttachment {
        @Required
        private Image image;
        @Required
        private Launcher img_launcher;
        @Required
        private String tag;

        public AdsAttachment() {
        }

        public AdsAttachment(Image image, Launcher launcher, String str) {
            this.image = image;
            this.img_launcher = launcher;
            this.tag = str;
        }

        @Required
        public AdsAttachment setImage(Image image) {
            this.image = image;
            return this;
        }

        @Required
        public Image getImage() {
            return this.image;
        }

        @Required
        public AdsAttachment setImgLauncher(Launcher launcher) {
            this.img_launcher = launcher;
            return this;
        }

        @Required
        public Launcher getImgLauncher() {
            return this.img_launcher;
        }

        @Required
        public AdsAttachment setTag(String str) {
            this.tag = str;
            return this;
        }

        @Required
        public String getTag() {
            return this.tag;
        }
    }

    @NamespaceName(name = "Alarm", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Alarm implements InstructionPayload {
        @Required
        private List<AlarmItem> items;
        @Required
        private AlarmOperation operation;
        @Deprecated
        private Optional<Image> icon = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();

        public Alarm() {
        }

        public Alarm(AlarmOperation alarmOperation, List<AlarmItem> list) {
            this.operation = alarmOperation;
            this.items = list;
        }

        @Required
        public Alarm setOperation(AlarmOperation alarmOperation) {
            this.operation = alarmOperation;
            return this;
        }

        @Required
        public AlarmOperation getOperation() {
            return this.operation;
        }

        @Required
        public Alarm setItems(List<AlarmItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<AlarmItem> getItems() {
            return this.items;
        }

        @Deprecated
        public Alarm setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        @Deprecated
        public Optional<Image> getIcon() {
            return this.icon;
        }

        public Alarm setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Alarm setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlarmDisplay {
        @Required
        private String date;
        @Required
        private String time;
        private Optional<String> weekday = Optional.empty();
        private Optional<String> circle = Optional.empty();
        private Optional<TimeOfDayType> time_of_day = Optional.empty();

        public AlarmDisplay() {
        }

        public AlarmDisplay(String str, String str2) {
            this.time = str;
            this.date = str2;
        }

        @Required
        public AlarmDisplay setTime(String str) {
            this.time = str;
            return this;
        }

        @Required
        public String getTime() {
            return this.time;
        }

        @Required
        public AlarmDisplay setDate(String str) {
            this.date = str;
            return this;
        }

        @Required
        public String getDate() {
            return this.date;
        }

        public AlarmDisplay setWeekday(String str) {
            this.weekday = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWeekday() {
            return this.weekday;
        }

        public AlarmDisplay setCircle(String str) {
            this.circle = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircle() {
            return this.circle;
        }

        public AlarmDisplay setTimeOfDay(TimeOfDayType timeOfDayType) {
            this.time_of_day = Optional.ofNullable(timeOfDayType);
            return this;
        }

        public Optional<TimeOfDayType> getTimeOfDay() {
            return this.time_of_day;
        }
    }

    /* loaded from: classes3.dex */
    public static class AlarmItem {
        @Required
        private String datetime;
        @Required
        private String id;
        @Required
        private Alerts.AlertType type;
        private Optional<Alerts.AlertCircleType> circle = Optional.empty();
        private Optional<String> circle_extra = Optional.empty();
        private Optional<String> event = Optional.empty();
        private Optional<String> reminder = Optional.empty();
        private Optional<Integer> repeat_ringing = Optional.empty();
        private Optional<String> offset = Optional.empty();
        private Optional<Integer> advance_reminder = Optional.empty();
        private Optional<Alerts.Ringtone> ringtone = Optional.empty();
        private Optional<String> time_reminder = Optional.empty();
        private Optional<String> end_datetime = Optional.empty();
        private Optional<AlarmDisplay> display = Optional.empty();
        private Optional<String> circle_rule = Optional.empty();
        private Optional<Alerts.AlertStatus> status = Optional.empty();
        private Optional<LocalTarget> target = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> icon = Optional.empty();
        private Optional<Alerts.ExamItemType> exam_item_type = Optional.empty();

        public AlarmItem() {
        }

        public AlarmItem(String str, Alerts.AlertType alertType, String str2) {
            this.id = str;
            this.type = alertType;
            this.datetime = str2;
        }

        @Required
        public AlarmItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public AlarmItem setType(Alerts.AlertType alertType) {
            this.type = alertType;
            return this;
        }

        @Required
        public Alerts.AlertType getType() {
            return this.type;
        }

        @Required
        public AlarmItem setDatetime(String str) {
            this.datetime = str;
            return this;
        }

        @Required
        public String getDatetime() {
            return this.datetime;
        }

        public AlarmItem setCircle(Alerts.AlertCircleType alertCircleType) {
            this.circle = Optional.ofNullable(alertCircleType);
            return this;
        }

        public Optional<Alerts.AlertCircleType> getCircle() {
            return this.circle;
        }

        public AlarmItem setCircleExtra(String str) {
            this.circle_extra = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleExtra() {
            return this.circle_extra;
        }

        public AlarmItem setEvent(String str) {
            this.event = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEvent() {
            return this.event;
        }

        public AlarmItem setReminder(String str) {
            this.reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReminder() {
            return this.reminder;
        }

        public AlarmItem setRepeatRinging(int i) {
            this.repeat_ringing = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getRepeatRinging() {
            return this.repeat_ringing;
        }

        public AlarmItem setOffset(String str) {
            this.offset = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getOffset() {
            return this.offset;
        }

        public AlarmItem setAdvanceReminder(int i) {
            this.advance_reminder = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAdvanceReminder() {
            return this.advance_reminder;
        }

        public AlarmItem setRingtone(Alerts.Ringtone ringtone) {
            this.ringtone = Optional.ofNullable(ringtone);
            return this;
        }

        public Optional<Alerts.Ringtone> getRingtone() {
            return this.ringtone;
        }

        public AlarmItem setTimeReminder(String str) {
            this.time_reminder = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTimeReminder() {
            return this.time_reminder;
        }

        public AlarmItem setEndDatetime(String str) {
            this.end_datetime = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndDatetime() {
            return this.end_datetime;
        }

        public AlarmItem setDisplay(AlarmDisplay alarmDisplay) {
            this.display = Optional.ofNullable(alarmDisplay);
            return this;
        }

        public Optional<AlarmDisplay> getDisplay() {
            return this.display;
        }

        public AlarmItem setCircleRule(String str) {
            this.circle_rule = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCircleRule() {
            return this.circle_rule;
        }

        public AlarmItem setStatus(Alerts.AlertStatus alertStatus) {
            this.status = Optional.ofNullable(alertStatus);
            return this;
        }

        public Optional<Alerts.AlertStatus> getStatus() {
            return this.status;
        }

        public AlarmItem setTarget(LocalTarget localTarget) {
            this.target = Optional.ofNullable(localTarget);
            return this;
        }

        public Optional<LocalTarget> getTarget() {
            return this.target;
        }

        public AlarmItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public AlarmItem setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public AlarmItem setExamItemType(Alerts.ExamItemType examItemType) {
            this.exam_item_type = Optional.ofNullable(examItemType);
            return this;
        }

        public Optional<Alerts.ExamItemType> getExamItemType() {
            return this.exam_item_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class Align {
        private Optional<String> h_align = Optional.empty();
        private Optional<String> v_align = Optional.empty();

        public Align setHAlign(String str) {
            this.h_align = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHAlign() {
            return this.h_align;
        }

        public Align setVAlign(String str) {
            this.v_align = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVAlign() {
            return this.v_align;
        }
    }

    @NamespaceName(name = "AncientPoem", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class AncientPoem implements InstructionPayload {
        @Required
        private List<AncientPoemData> poems;

        public AncientPoem() {
        }

        public AncientPoem(List<AncientPoemData> list) {
            this.poems = list;
        }

        @Required
        public AncientPoem setPoems(List<AncientPoemData> list) {
            this.poems = list;
            return this;
        }

        @Required
        public List<AncientPoemData> getPoems() {
            return this.poems;
        }
    }

    /* loaded from: classes3.dex */
    public static class AncientPoemData {
        @Required
        private String audio_id;
        @Required
        private List<String> authors;
        @Required
        private String dynasty;
        private Optional<String> meaning = Optional.empty();
        @Required
        private String name;
        @Required
        private String verse;

        public AncientPoemData() {
        }

        public AncientPoemData(String str, List<String> list, String str2, String str3, String str4) {
            this.name = str;
            this.authors = list;
            this.verse = str2;
            this.audio_id = str3;
            this.dynasty = str4;
        }

        @Required
        public AncientPoemData setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public AncientPoemData setAuthors(List<String> list) {
            this.authors = list;
            return this;
        }

        @Required
        public List<String> getAuthors() {
            return this.authors;
        }

        @Required
        public AncientPoemData setVerse(String str) {
            this.verse = str;
            return this;
        }

        @Required
        public String getVerse() {
            return this.verse;
        }

        @Required
        public AncientPoemData setAudioId(String str) {
            this.audio_id = str;
            return this;
        }

        @Required
        public String getAudioId() {
            return this.audio_id;
        }

        @Required
        public AncientPoemData setDynasty(String str) {
            this.dynasty = str;
            return this;
        }

        @Required
        public String getDynasty() {
            return this.dynasty;
        }

        public AncientPoemData setMeaning(String str) {
            this.meaning = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMeaning() {
            return this.meaning;
        }
    }

    /* loaded from: classes3.dex */
    public static class AndroidApp {
        @Required
        private Image icon;
        @Required
        private String name;
        @Required
        private String pkg_name;
        private Optional<String> slogan = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<Float> rating_score = Optional.empty();
        private Optional<Integer> apk_size = Optional.empty();
        private Optional<AppVersion> version = Optional.empty();
        private Optional<Long> downloads = Optional.empty();
        private Optional<String> developer = Optional.empty();
        private Optional<Launcher> privacy = Optional.empty();
        private Optional<Launcher> auth = Optional.empty();
        private Optional<Long> app_size = Optional.empty();

        public AndroidApp() {
        }

        public AndroidApp(String str, String str2, Image image) {
            this.name = str;
            this.pkg_name = str2;
            this.icon = image;
        }

        @Required
        public AndroidApp setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public AndroidApp setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        public AndroidApp setSlogan(String str) {
            this.slogan = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSlogan() {
            return this.slogan;
        }

        public AndroidApp setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        @Required
        public AndroidApp setIcon(Image image) {
            this.icon = image;
            return this;
        }

        @Required
        public Image getIcon() {
            return this.icon;
        }

        public AndroidApp setRatingScore(float f) {
            this.rating_score = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getRatingScore() {
            return this.rating_score;
        }

        public AndroidApp setApkSize(int i) {
            this.apk_size = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getApkSize() {
            return this.apk_size;
        }

        public AndroidApp setVersion(AppVersion appVersion) {
            this.version = Optional.ofNullable(appVersion);
            return this;
        }

        public Optional<AppVersion> getVersion() {
            return this.version;
        }

        public AndroidApp setDownloads(long j) {
            this.downloads = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDownloads() {
            return this.downloads;
        }

        public AndroidApp setDeveloper(String str) {
            this.developer = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeveloper() {
            return this.developer;
        }

        public AndroidApp setPrivacy(Launcher launcher) {
            this.privacy = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getPrivacy() {
            return this.privacy;
        }

        public AndroidApp setAuth(Launcher launcher) {
            this.auth = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getAuth() {
            return this.auth;
        }

        public AndroidApp setAppSize(long j) {
            this.app_size = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getAppSize() {
            return this.app_size;
        }
    }

    /* loaded from: classes3.dex */
    public static class AndroidIntent {
        @Required
        private String pkg_name;
        @Required
        private String type;
        @Required
        private String uri;
        private Optional<String> permission = Optional.empty();
        private Optional<OnError> on_error = Optional.empty();
        private Optional<Integer> min_version = Optional.empty();
        private Optional<ObjectNode> params = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<Task> task = Optional.empty();

        public AndroidIntent() {
        }

        public AndroidIntent(String str, String str2, String str3) {
            this.type = str;
            this.pkg_name = str2;
            this.uri = str3;
        }

        @Required
        public AndroidIntent setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        @Required
        public AndroidIntent setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public AndroidIntent setUri(String str) {
            this.uri = str;
            return this;
        }

        @Required
        public String getUri() {
            return this.uri;
        }

        public AndroidIntent setPermission(String str) {
            this.permission = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPermission() {
            return this.permission;
        }

        public AndroidIntent setOnError(OnError onError) {
            this.on_error = Optional.ofNullable(onError);
            return this;
        }

        public Optional<OnError> getOnError() {
            return this.on_error;
        }

        public AndroidIntent setMinVersion(int i) {
            this.min_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMinVersion() {
            return this.min_version;
        }

        public AndroidIntent setParams(ObjectNode objectNode) {
            this.params = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParams() {
            return this.params;
        }

        public AndroidIntent setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public AndroidIntent setTask(Task task) {
            this.task = Optional.ofNullable(task);
            return this;
        }

        public Optional<Task> getTask() {
            return this.task;
        }
    }

    /* loaded from: classes3.dex */
    public static class AndroidKeyCode {
        @Required
        private int key_code;
        private Optional<String> description = Optional.empty();
        private Optional<Task> task = Optional.empty();

        public AndroidKeyCode() {
        }

        public AndroidKeyCode(int i) {
            this.key_code = i;
        }

        @Required
        public AndroidKeyCode setKeyCode(int i) {
            this.key_code = i;
            return this;
        }

        @Required
        public int getKeyCode() {
            return this.key_code;
        }

        public AndroidKeyCode setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public AndroidKeyCode setTask(Task task) {
            this.task = Optional.ofNullable(task);
            return this;
        }

        public Optional<Task> getTask() {
            return this.task;
        }
    }

    /* loaded from: classes3.dex */
    public static class AnimationConfig {
        @Required
        private String url;

        public AnimationConfig() {
        }

        public AnimationConfig(String str) {
            this.url = str;
        }

        @Required
        public AnimationConfig setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppAds {
        @Required
        private List<String> click_monitor_urls;
        @Required
        private String ext_apk_channel;
        @Required
        private List<String> finish_download_monitor_urls;
        @Required
        private List<String> finish_install_monitor_urls;
        @Required
        private List<String> launch_monitor_urls;
        @Required
        private List<String> start_download_monitor_urls;
        @Required
        private List<String> start_install_monitor_urls;
        @Required
        private List<String> view_monitor_urls;
        @Required
        private boolean with_ads;
        private Optional<String> ex = Optional.empty();
        private Optional<String> app_ref = Optional.empty();
        private Optional<String> landing_page_url = Optional.empty();
        private Optional<String> float_card_url = Optional.empty();
        private Optional<String> ai_ex = Optional.empty();
        private Optional<String> deep_link = Optional.empty();

        public AppAds() {
        }

        public AppAds(boolean z, String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, List<String> list7) {
            this.with_ads = z;
            this.ext_apk_channel = str;
            this.view_monitor_urls = list;
            this.click_monitor_urls = list2;
            this.launch_monitor_urls = list3;
            this.start_download_monitor_urls = list4;
            this.finish_download_monitor_urls = list5;
            this.start_install_monitor_urls = list6;
            this.finish_install_monitor_urls = list7;
        }

        @Required
        public AppAds setWithAds(boolean z) {
            this.with_ads = z;
            return this;
        }

        @Required
        public boolean isWithAds() {
            return this.with_ads;
        }

        @Required
        public AppAds setExtApkChannel(String str) {
            this.ext_apk_channel = str;
            return this;
        }

        @Required
        public String getExtApkChannel() {
            return this.ext_apk_channel;
        }

        @Required
        public AppAds setViewMonitorUrls(List<String> list) {
            this.view_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getViewMonitorUrls() {
            return this.view_monitor_urls;
        }

        @Required
        public AppAds setClickMonitorUrls(List<String> list) {
            this.click_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getClickMonitorUrls() {
            return this.click_monitor_urls;
        }

        @Required
        public AppAds setLaunchMonitorUrls(List<String> list) {
            this.launch_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getLaunchMonitorUrls() {
            return this.launch_monitor_urls;
        }

        @Required
        public AppAds setStartDownloadMonitorUrls(List<String> list) {
            this.start_download_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getStartDownloadMonitorUrls() {
            return this.start_download_monitor_urls;
        }

        @Required
        public AppAds setFinishDownloadMonitorUrls(List<String> list) {
            this.finish_download_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getFinishDownloadMonitorUrls() {
            return this.finish_download_monitor_urls;
        }

        @Required
        public AppAds setStartInstallMonitorUrls(List<String> list) {
            this.start_install_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getStartInstallMonitorUrls() {
            return this.start_install_monitor_urls;
        }

        @Required
        public AppAds setFinishInstallMonitorUrls(List<String> list) {
            this.finish_install_monitor_urls = list;
            return this;
        }

        @Required
        public List<String> getFinishInstallMonitorUrls() {
            return this.finish_install_monitor_urls;
        }

        public AppAds setEx(String str) {
            this.ex = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEx() {
            return this.ex;
        }

        public AppAds setAppRef(String str) {
            this.app_ref = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppRef() {
            return this.app_ref;
        }

        public AppAds setLandingPageUrl(String str) {
            this.landing_page_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLandingPageUrl() {
            return this.landing_page_url;
        }

        public AppAds setFloatCardUrl(String str) {
            this.float_card_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFloatCardUrl() {
            return this.float_card_url;
        }

        public AppAds setAiEx(String str) {
            this.ai_ex = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAiEx() {
            return this.ai_ex;
        }

        public AppAds setDeepLink(String str) {
            this.deep_link = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeepLink() {
            return this.deep_link;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppControl {
        @Required
        private String name;
        @Required
        private String type;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<String> app_sign = Optional.empty();
        private Optional<String> nonce = Optional.empty();

        public AppControl() {
        }

        public AppControl(String str, String str2) {
            this.type = str;
            this.name = str2;
        }

        @Required
        public AppControl setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        @Required
        public AppControl setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public AppControl setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public AppControl setAppSign(String str) {
            this.app_sign = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppSign() {
            return this.app_sign;
        }

        public AppControl setNonce(String str) {
            this.nonce = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNonce() {
            return this.nonce;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppEntity {
        private Optional<AppAds> ads = Optional.empty();
        @Required

        /* renamed from: app */
        private AndroidApp f198app;
        @Required
        private AppControl control;

        public AppEntity() {
        }

        public AppEntity(AndroidApp androidApp, AppControl appControl) {
            this.f198app = androidApp;
            this.control = appControl;
        }

        @Required
        public AppEntity setApp(AndroidApp androidApp) {
            this.f198app = androidApp;
            return this;
        }

        @Required
        public AndroidApp getApp() {
            return this.f198app;
        }

        @Required
        public AppEntity setControl(AppControl appControl) {
            this.control = appControl;
            return this;
        }

        @Required
        public AppControl getControl() {
            return this.control;
        }

        public AppEntity setAds(AppAds appAds) {
            this.ads = Optional.ofNullable(appAds);
            return this;
        }

        public Optional<AppAds> getAds() {
            return this.ads;
        }
    }

    /* loaded from: classes3.dex */
    public static class AppVersion {
        @Required
        private int latest;
        @Required
        private int min;
        private Optional<String> version_name = Optional.empty();

        public AppVersion() {
        }

        public AppVersion(int i, int i2) {
            this.min = i;
            this.latest = i2;
        }

        @Required
        public AppVersion setMin(int i) {
            this.min = i;
            return this;
        }

        @Required
        public int getMin() {
            return this.min;
        }

        @Required
        public AppVersion setLatest(int i) {
            this.latest = i;
            return this;
        }

        @Required
        public int getLatest() {
            return this.latest;
        }

        public AppVersion setVersionName(String str) {
            this.version_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getVersionName() {
            return this.version_name;
        }
    }

    @NamespaceName(name = AIApiConstants.Application.NAME, namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Application implements InstructionPayload {
        @Required
        private List<AppEntity> apps;
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<List<AppEntity>> recommend_apps = Optional.empty();
        @Required
        private Image skill_icon;

        public Application() {
        }

        public Application(Image image, List<AppEntity> list) {
            this.skill_icon = image;
            this.apps = list;
        }

        @Required
        public Application setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        @Required
        public Application setApps(List<AppEntity> list) {
            this.apps = list;
            return this;
        }

        @Required
        public List<AppEntity> getApps() {
            return this.apps;
        }

        public Application setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Application setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Application setRecommendApps(List<AppEntity> list) {
            this.recommend_apps = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AppEntity>> getRecommendApps() {
            return this.recommend_apps;
        }
    }

    /* loaded from: classes3.dex */
    public static class ArithRecord {
        private Optional<String> expression = Optional.empty();
        private Optional<String> result_unit = Optional.empty();
        private Optional<String> approximate_value = Optional.empty();
        private Optional<String> value = Optional.empty();

        public ArithRecord setExpression(String str) {
            this.expression = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpression() {
            return this.expression;
        }

        public ArithRecord setResultUnit(String str) {
            this.result_unit = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultUnit() {
            return this.result_unit;
        }

        public ArithRecord setApproximateValue(String str) {
            this.approximate_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getApproximateValue() {
            return this.approximate_value;
        }

        public ArithRecord setValue(String str) {
            this.value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getValue() {
            return this.value;
        }
    }

    @NamespaceName(name = "Attachment", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Attachment implements InstructionPayload {
        @Required
        private String id;
        @Required
        private AttachmentType type;
        private Optional<FeedbackAttachment> feedback = Optional.empty();
        private Optional<AdsAttachment> ads = Optional.empty();

        public Attachment() {
        }

        public Attachment(String str, AttachmentType attachmentType) {
            this.id = str;
            this.type = attachmentType;
        }

        @Required
        public Attachment setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Attachment setType(AttachmentType attachmentType) {
            this.type = attachmentType;
            return this;
        }

        @Required
        public AttachmentType getType() {
            return this.type;
        }

        public Attachment setFeedback(FeedbackAttachment feedbackAttachment) {
            this.feedback = Optional.ofNullable(feedbackAttachment);
            return this;
        }

        public Optional<FeedbackAttachment> getFeedback() {
            return this.feedback;
        }

        public Attachment setAds(AdsAttachment adsAttachment) {
            this.ads = Optional.ofNullable(adsAttachment);
            return this;
        }

        public Optional<AdsAttachment> getAds() {
            return this.ads;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioInfo {
        @Required
        private String album_id;
        @Required
        private String album_name;
        @Required
        private int artist_id;
        @Required
        private String artist_name;
        @Required
        private String audio_id;
        @Required
        private String audio_name;
        @Required
        private List<String> controls;
        @Required
        private Image cover;
        private Optional<String> lyric_url = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Boolean> is_vip = Optional.empty();
        private Optional<Boolean> is_original = Optional.empty();
        private Optional<EntityInfo> entity_info = Optional.empty();
        private Optional<Boolean> payment = Optional.empty();

        public AudioInfo() {
        }

        public AudioInfo(String str, String str2, String str3, int i, String str4, String str5, Image image, List<String> list) {
            this.audio_id = str;
            this.audio_name = str2;
            this.artist_name = str3;
            this.artist_id = i;
            this.album_name = str4;
            this.album_id = str5;
            this.cover = image;
            this.controls = list;
        }

        @Required
        public AudioInfo setAudioId(String str) {
            this.audio_id = str;
            return this;
        }

        @Required
        public String getAudioId() {
            return this.audio_id;
        }

        @Required
        public AudioInfo setAudioName(String str) {
            this.audio_name = str;
            return this;
        }

        @Required
        public String getAudioName() {
            return this.audio_name;
        }

        @Required
        public AudioInfo setArtistName(String str) {
            this.artist_name = str;
            return this;
        }

        @Required
        public String getArtistName() {
            return this.artist_name;
        }

        @Required
        public AudioInfo setArtistId(int i) {
            this.artist_id = i;
            return this;
        }

        @Required
        public int getArtistId() {
            return this.artist_id;
        }

        @Required
        public AudioInfo setAlbumName(String str) {
            this.album_name = str;
            return this;
        }

        @Required
        public String getAlbumName() {
            return this.album_name;
        }

        @Required
        public AudioInfo setAlbumId(String str) {
            this.album_id = str;
            return this;
        }

        @Required
        public String getAlbumId() {
            return this.album_id;
        }

        @Required
        public AudioInfo setCover(Image image) {
            this.cover = image;
            return this;
        }

        @Required
        public Image getCover() {
            return this.cover;
        }

        public AudioInfo setLyricUrl(String str) {
            this.lyric_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLyricUrl() {
            return this.lyric_url;
        }

        public AudioInfo setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        @Required
        public AudioInfo setControls(List<String> list) {
            this.controls = list;
            return this;
        }

        @Required
        public List<String> getControls() {
            return this.controls;
        }

        public AudioInfo setIsVip(boolean z) {
            this.is_vip = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVip() {
            return this.is_vip;
        }

        public AudioInfo setIsOriginal(boolean z) {
            this.is_original = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isOriginal() {
            return this.is_original;
        }

        public AudioInfo setEntityInfo(EntityInfo entityInfo) {
            this.entity_info = Optional.ofNullable(entityInfo);
            return this;
        }

        public Optional<EntityInfo> getEntityInfo() {
            return this.entity_info;
        }

        public AudioInfo setPayment(boolean z) {
            this.payment = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPayment() {
            return this.payment;
        }
    }

    /* loaded from: classes3.dex */
    public static class AudioProvider {
        private Optional<Launcher> launcher = Optional.empty();
        @Required
        private Image logo;
        @Required
        private String name;

        public AudioProvider() {
        }

        public AudioProvider(String str, Image image) {
            this.name = str;
            this.logo = image;
        }

        @Required
        public AudioProvider setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public AudioProvider setLogo(Image image) {
            this.logo = image;
            return this;
        }

        @Required
        public Image getLogo() {
            return this.logo;
        }

        public AudioProvider setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class BackgroundColor {
        @Required
        private List<Integer> colors;
        private Optional<GradientOrientationType> gradient_orientation = Optional.empty();
        @Required
        private BackgroundColorType type;

        public BackgroundColor() {
        }

        public BackgroundColor(BackgroundColorType backgroundColorType, List<Integer> list) {
            this.type = backgroundColorType;
            this.colors = list;
        }

        @Required
        public BackgroundColor setType(BackgroundColorType backgroundColorType) {
            this.type = backgroundColorType;
            return this;
        }

        @Required
        public BackgroundColorType getType() {
            return this.type;
        }

        @Required
        public BackgroundColor setColors(List<Integer> list) {
            this.colors = list;
            return this;
        }

        @Required
        public List<Integer> getColors() {
            return this.colors;
        }

        public BackgroundColor setGradientOrientation(GradientOrientationType gradientOrientationType) {
            this.gradient_orientation = Optional.ofNullable(gradientOrientationType);
            return this;
        }

        public Optional<GradientOrientationType> getGradientOrientation() {
            return this.gradient_orientation;
        }
    }

    @NamespaceName(name = "BuslineInfo", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class BuslineInfo implements InstructionPayload {
        @Required
        private String bus_name;
        @Required
        private String current_station;
        @Required
        private List<Maps.Busline> lines;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public BuslineInfo() {
        }

        public BuslineInfo(List<Maps.Busline> list, String str, String str2) {
            this.lines = list;
            this.bus_name = str;
            this.current_station = str2;
        }

        @Required
        public BuslineInfo setLines(List<Maps.Busline> list) {
            this.lines = list;
            return this;
        }

        @Required
        public List<Maps.Busline> getLines() {
            return this.lines;
        }

        @Required
        public BuslineInfo setBusName(String str) {
            this.bus_name = str;
            return this;
        }

        @Required
        public String getBusName() {
            return this.bus_name;
        }

        @Required
        public BuslineInfo setCurrentStation(String str) {
            this.current_station = str;
            return this;
        }

        @Required
        public String getCurrentStation() {
            return this.current_station;
        }

        public BuslineInfo setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public BuslineInfo setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class Button {
        @Required
        private Launcher launcher;
        @Required
        private String text;

        public Button() {
        }

        public Button(String str, Launcher launcher) {
            this.text = str;
            this.launcher = launcher;
        }

        @Required
        public Button setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public Button setLauncher(Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Launcher getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "Calculator", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Calculator implements InstructionPayload {
        @Required
        private Image skill_icon;
        private Optional<String> expression = Optional.empty();
        private Optional<String> result_unit = Optional.empty();
        private Optional<String> approximate_value = Optional.empty();
        private Optional<String> value = Optional.empty();
        private Optional<List<ArithRecord>> recent_records = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<CalculatorType> calculator_type = Optional.empty();
        private Optional<String> data_source = Optional.empty();

        public Calculator() {
        }

        public Calculator(Image image) {
            this.skill_icon = image;
        }

        @Required
        public Calculator setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public Calculator setExpression(String str) {
            this.expression = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpression() {
            return this.expression;
        }

        public Calculator setResultUnit(String str) {
            this.result_unit = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResultUnit() {
            return this.result_unit;
        }

        public Calculator setApproximateValue(String str) {
            this.approximate_value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getApproximateValue() {
            return this.approximate_value;
        }

        public Calculator setValue(String str) {
            this.value = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getValue() {
            return this.value;
        }

        public Calculator setRecentRecords(List<ArithRecord> list) {
            this.recent_records = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ArithRecord>> getRecentRecords() {
            return this.recent_records;
        }

        public Calculator setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Calculator setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Calculator setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Calculator setCalculatorType(CalculatorType calculatorType) {
            this.calculator_type = Optional.ofNullable(calculatorType);
            return this;
        }

        public Optional<CalculatorType> getCalculatorType() {
            return this.calculator_type;
        }

        public Calculator setDataSource(String str) {
            this.data_source = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDataSource() {
            return this.data_source;
        }
    }

    /* loaded from: classes3.dex */
    public static class Cineaste {
        @Required
        private Image avatar;
        @Required
        private String id;
        private Optional<String> meta_id = Optional.empty();
        @Required
        private String name;

        public Cineaste() {
        }

        public Cineaste(String str, String str2, Image image) {
            this.name = str;
            this.id = str2;
            this.avatar = image;
        }

        @Required
        public Cineaste setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Cineaste setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Cineaste setAvatar(Image image) {
            this.avatar = image;
            return this;
        }

        @Required
        public Image getAvatar() {
            return this.avatar;
        }

        public Cineaste setMetaId(String str) {
            this.meta_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMetaId() {
            return this.meta_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class CineasteWithTag {
        @Required
        private Cineaste cineaste;
        @Required
        private List<Video.VideoSearchTags> tags;

        public CineasteWithTag() {
        }

        public CineasteWithTag(Cineaste cineaste, List<Video.VideoSearchTags> list) {
            this.cineaste = cineaste;
            this.tags = list;
        }

        @Required
        public CineasteWithTag setCineaste(Cineaste cineaste) {
            this.cineaste = cineaste;
            return this;
        }

        @Required
        public Cineaste getCineaste() {
            return this.cineaste;
        }

        @Required
        public CineasteWithTag setTags(List<Video.VideoSearchTags> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<Video.VideoSearchTags> getTags() {
            return this.tags;
        }
    }

    /* loaded from: classes3.dex */
    public static class ClickAlbum {
        @Required
        private List<Image> images;
        private Optional<Launcher> launcher = Optional.empty();

        public ClickAlbum() {
        }

        public ClickAlbum(List<Image> list) {
            this.images = list;
        }

        @Required
        public ClickAlbum setImages(List<Image> list) {
            this.images = list;
            return this;
        }

        @Required
        public List<Image> getImages() {
            return this.images;
        }

        public ClickAlbum setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "ConfirmCancelBox", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ConfirmCancelBox implements InstructionPayload {
        @Required
        private String text;
        private Optional<Launcher> confirm = Optional.empty();
        private Optional<Launcher> cancel = Optional.empty();

        public ConfirmCancelBox() {
        }

        public ConfirmCancelBox(String str) {
            this.text = str;
        }

        @Required
        public ConfirmCancelBox setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public ConfirmCancelBox setConfirm(Launcher launcher) {
            this.confirm = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getConfirm() {
            return this.confirm;
        }

        public ConfirmCancelBox setCancel(Launcher launcher) {
            this.cancel = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getCancel() {
            return this.cancel;
        }
    }

    /* loaded from: classes3.dex */
    public static class Course {
        private Optional<String> name = Optional.empty();
        private Optional<String> classroom = Optional.empty();
        private Optional<String> teacher = Optional.empty();
        private Optional<String> time_range = Optional.empty();

        public Course setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public Course setClassroom(String str) {
            this.classroom = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClassroom() {
            return this.classroom;
        }

        public Course setTeacher(String str) {
            this.teacher = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTeacher() {
            return this.teacher;
        }

        public Course setTimeRange(String str) {
            this.time_range = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTimeRange() {
            return this.time_range;
        }
    }

    @NamespaceName(name = "CourseSchedule", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class CourseSchedule implements InstructionPayload {
        private Optional<DayCourseSchedule> day_courses = Optional.empty();
        private Optional<List<DayCourseSchedule>> week_courses = Optional.empty();

        public CourseSchedule setDayCourses(DayCourseSchedule dayCourseSchedule) {
            this.day_courses = Optional.ofNullable(dayCourseSchedule);
            return this;
        }

        public Optional<DayCourseSchedule> getDayCourses() {
            return this.day_courses;
        }

        public CourseSchedule setWeekCourses(List<DayCourseSchedule> list) {
            this.week_courses = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<DayCourseSchedule>> getWeekCourses() {
            return this.week_courses;
        }
    }

    /* loaded from: classes3.dex */
    public static class CustomBackground {
        private Optional<BackgroundColor> background_color = Optional.empty();
        @Required
        private boolean dark_mode;

        public CustomBackground() {
        }

        public CustomBackground(boolean z) {
            this.dark_mode = z;
        }

        public CustomBackground setBackgroundColor(BackgroundColor backgroundColor) {
            this.background_color = Optional.ofNullable(backgroundColor);
            return this;
        }

        public Optional<BackgroundColor> getBackgroundColor() {
            return this.background_color;
        }

        @Required
        public CustomBackground setDarkMode(boolean z) {
            this.dark_mode = z;
            return this;
        }

        @Required
        public boolean isDarkMode() {
            return this.dark_mode;
        }
    }

    /* loaded from: classes3.dex */
    public static class DayCourseSchedule {
        @Required
        private List<Course> courses;
        @Required
        private Common.Weekday week_day;

        public DayCourseSchedule() {
        }

        public DayCourseSchedule(Common.Weekday weekday, List<Course> list) {
            this.week_day = weekday;
            this.courses = list;
        }

        @Required
        public DayCourseSchedule setWeekDay(Common.Weekday weekday) {
            this.week_day = weekday;
            return this;
        }

        @Required
        public Common.Weekday getWeekDay() {
            return this.week_day;
        }

        @Required
        public DayCourseSchedule setCourses(List<Course> list) {
            this.courses = list;
            return this;
        }

        @Required
        public List<Course> getCourses() {
            return this.courses;
        }
    }

    @NamespaceName(name = "Details", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Details implements EventPayload {
        @Required
        private String id;

        public Details() {
        }

        public Details(String str) {
            this.id = str;
        }

        @Required
        public Details setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceInfoV1 {
        @Required
        private String category;
        @Required
        private String device_id;
        private Optional<String> room = Optional.empty();

        public DeviceInfoV1() {
        }

        public DeviceInfoV1(String str, String str2) {
            this.device_id = str;
            this.category = str2;
        }

        @Required
        public DeviceInfoV1 setDeviceId(String str) {
            this.device_id = str;
            return this;
        }

        @Required
        public String getDeviceId() {
            return this.device_id;
        }

        @Required
        public DeviceInfoV1 setCategory(String str) {
            this.category = str;
            return this;
        }

        @Required
        public String getCategory() {
            return this.category;
        }

        public DeviceInfoV1 setRoom(String str) {
            this.room = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoom() {
            return this.room;
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceItem {
        @Required
        private Title title;
        private Optional<DeviceInfoV1> info = Optional.empty();
        private Optional<List<Image>> images = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public DeviceItem() {
        }

        public DeviceItem(Title title) {
            this.title = title;
        }

        @Required
        public DeviceItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        public DeviceItem setInfo(DeviceInfoV1 deviceInfoV1) {
            this.info = Optional.ofNullable(deviceInfoV1);
            return this;
        }

        public Optional<DeviceInfoV1> getInfo() {
            return this.info;
        }

        public DeviceItem setImages(List<Image> list) {
            this.images = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Image>> getImages() {
            return this.images;
        }

        public DeviceItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "DeviceList", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class DeviceList implements InstructionPayload {
        @Required
        private List<DeviceItem> items;
        @Required
        private boolean show_index;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public DeviceList() {
        }

        public DeviceList(List<DeviceItem> list, boolean z) {
            this.items = list;
            this.show_index = z;
        }

        @Required
        public DeviceList setItems(List<DeviceItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<DeviceItem> getItems() {
            return this.items;
        }

        public DeviceList setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        @Required
        public DeviceList setShowIndex(boolean z) {
            this.show_index = z;
            return this;
        }

        @Required
        public boolean isShowIndex() {
            return this.show_index;
        }

        public DeviceList setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public DeviceList setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class DictionaryTranslation {
        private Optional<SynonymCardV2> synonym_card = Optional.empty();
        private Optional<FullScreenTemplate.PartSimple> part_simple = Optional.empty();
        private Optional<FullScreenTemplate.WordDetail> word_detail = Optional.empty();
        private Optional<MachineTranslation> word_simple = Optional.empty();

        public DictionaryTranslation setSynonymCard(SynonymCardV2 synonymCardV2) {
            this.synonym_card = Optional.ofNullable(synonymCardV2);
            return this;
        }

        public Optional<SynonymCardV2> getSynonymCard() {
            return this.synonym_card;
        }

        public DictionaryTranslation setPartSimple(FullScreenTemplate.PartSimple partSimple) {
            this.part_simple = Optional.ofNullable(partSimple);
            return this;
        }

        public Optional<FullScreenTemplate.PartSimple> getPartSimple() {
            return this.part_simple;
        }

        public DictionaryTranslation setWordDetail(FullScreenTemplate.WordDetail wordDetail) {
            this.word_detail = Optional.ofNullable(wordDetail);
            return this;
        }

        public Optional<FullScreenTemplate.WordDetail> getWordDetail() {
            return this.word_detail;
        }

        public DictionaryTranslation setWordSimple(MachineTranslation machineTranslation) {
            this.word_simple = Optional.ofNullable(machineTranslation);
            return this;
        }

        public Optional<MachineTranslation> getWordSimple() {
            return this.word_simple;
        }
    }

    /* loaded from: classes3.dex */
    public static class DisplayCommon {
        private Optional<FullScreen> full_screen = Optional.empty();
        private Optional<DisplayMode> display_mode = Optional.empty();
        @Deprecated
        private Optional<Feedback> feedback = Optional.empty();

        public DisplayCommon setFullScreen(FullScreen fullScreen) {
            this.full_screen = Optional.ofNullable(fullScreen);
            return this;
        }

        public Optional<FullScreen> getFullScreen() {
            return this.full_screen;
        }

        public DisplayCommon setDisplayMode(DisplayMode displayMode) {
            this.display_mode = Optional.ofNullable(displayMode);
            return this;
        }

        public Optional<DisplayMode> getDisplayMode() {
            return this.display_mode;
        }

        @Deprecated
        public DisplayCommon setFeedback(Feedback feedback) {
            this.feedback = Optional.ofNullable(feedback);
            return this;
        }

        @Deprecated
        public Optional<Feedback> getFeedback() {
            return this.feedback;
        }
    }

    /* loaded from: classes3.dex */
    public static class DisplayControl {
        private Optional<Integer> duration = Optional.empty();
        private Optional<DisplayControlType> type = Optional.empty();

        public DisplayControl setDuration(int i) {
            this.duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDuration() {
            return this.duration;
        }

        public DisplayControl setType(DisplayControlType displayControlType) {
            this.type = Optional.ofNullable(displayControlType);
            return this;
        }

        public Optional<DisplayControlType> getType() {
            return this.type;
        }
    }

    @NamespaceName(name = "DynamicDialogFlow", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class DynamicDialogFlow implements InstructionPayload {
        @Required
        private Image skill_icon;
        private Optional<Title> title = Optional.empty();

        public DynamicDialogFlow() {
        }

        public DynamicDialogFlow(Image image) {
            this.skill_icon = image;
        }

        public DynamicDialogFlow setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        @Required
        public DynamicDialogFlow setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class EmbeddedList {
        @Required
        private DirectionDef direction;
        @Required
        private List<ListsItem> items;
        private Optional<Title> title = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public EmbeddedList() {
        }

        public EmbeddedList(List<ListsItem> list, DirectionDef directionDef) {
            this.items = list;
            this.direction = directionDef;
        }

        @Required
        public EmbeddedList setItems(List<ListsItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<ListsItem> getItems() {
            return this.items;
        }

        @Required
        public EmbeddedList setDirection(DirectionDef directionDef) {
            this.direction = directionDef;
            return this;
        }

        @Required
        public DirectionDef getDirection() {
            return this.direction;
        }

        public EmbeddedList setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public EmbeddedList setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class EmojiConfig {
        @Required
        private String url;
        private Optional<Long> duration_in_ms = Optional.empty();
        private Optional<Float> speed = Optional.empty();
        private Optional<Integer> interpolator = Optional.empty();

        public EmojiConfig() {
        }

        public EmojiConfig(String str) {
            this.url = str;
        }

        @Required
        public EmojiConfig setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        public EmojiConfig setDurationInMs(long j) {
            this.duration_in_ms = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getDurationInMs() {
            return this.duration_in_ms;
        }

        public EmojiConfig setSpeed(float f) {
            this.speed = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getSpeed() {
            return this.speed;
        }

        public EmojiConfig setInterpolator(int i) {
            this.interpolator = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getInterpolator() {
            return this.interpolator;
        }
    }

    /* loaded from: classes3.dex */
    public static class EntityInfo {
        @Required
        private String id;

        public EntityInfo() {
        }

        public EntityInfo(String str) {
            this.id = str;
        }

        @Required
        public EntityInfo setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class ErrorDetail {
        private Optional<String> audio_url = Optional.empty();
        @Required
        private String description;

        public ErrorDetail() {
        }

        public ErrorDetail(String str) {
            this.description = str;
        }

        @Required
        public ErrorDetail setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        public ErrorDetail setAudioUrl(String str) {
            this.audio_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAudioUrl() {
            return this.audio_url;
        }
    }

    /* loaded from: classes3.dex */
    public static class EventRequestInfo {
        @Required
        private String name;
        @Required
        private String namespace;

        public EventRequestInfo() {
        }

        public EventRequestInfo(String str, String str2) {
            this.namespace = str;
            this.name = str2;
        }

        @Required
        public EventRequestInfo setNamespace(String str) {
            this.namespace = str;
            return this;
        }

        @Required
        public String getNamespace() {
            return this.namespace;
        }

        @Required
        public EventRequestInfo setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }
    }

    /* loaded from: classes3.dex */
    public static class FacilityLocation {
        private Optional<Image> icon = Optional.empty();
        @Required
        private String location;

        public FacilityLocation() {
        }

        public FacilityLocation(String str) {
            this.location = str;
        }

        @Required
        public FacilityLocation setLocation(String str) {
            this.location = str;
            return this;
        }

        @Required
        public String getLocation() {
            return this.location;
        }

        public FacilityLocation setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class FacilityTime {
        private Optional<Image> icon = Optional.empty();
        @Required
        private String time;

        public FacilityTime() {
        }

        public FacilityTime(String str) {
            this.time = str;
        }

        @Required
        public FacilityTime setTime(String str) {
            this.time = str;
            return this;
        }

        @Required
        public String getTime() {
            return this.time;
        }

        public FacilityTime setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class Feedback {
        @Required
        private String like_text;
        @Required
        private String prompt_text;
        @Required
        private String unlike_text;
        private Optional<String> like_response_text = Optional.empty();
        private Optional<String> unlike_response_text = Optional.empty();

        public Feedback() {
        }

        public Feedback(String str, String str2, String str3) {
            this.prompt_text = str;
            this.like_text = str2;
            this.unlike_text = str3;
        }

        @Required
        public Feedback setPromptText(String str) {
            this.prompt_text = str;
            return this;
        }

        @Required
        public String getPromptText() {
            return this.prompt_text;
        }

        @Required
        public Feedback setLikeText(String str) {
            this.like_text = str;
            return this;
        }

        @Required
        public String getLikeText() {
            return this.like_text;
        }

        @Required
        public Feedback setUnlikeText(String str) {
            this.unlike_text = str;
            return this;
        }

        @Required
        public String getUnlikeText() {
            return this.unlike_text;
        }

        public Feedback setLikeResponseText(String str) {
            this.like_response_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLikeResponseText() {
            return this.like_response_text;
        }

        public Feedback setUnlikeResponseText(String str) {
            this.unlike_response_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUnlikeResponseText() {
            return this.unlike_response_text;
        }
    }

    /* loaded from: classes3.dex */
    public static class FeedbackAttachment {
        @Required
        private String dislike_response;
        @Required
        private String like;
        @Required
        private String prompt;
        private Optional<String> dislike = Optional.empty();
        private Optional<String> like_response = Optional.empty();
        private Optional<List<String>> dislike_reasons = Optional.empty();

        public FeedbackAttachment() {
        }

        public FeedbackAttachment(String str, String str2, String str3) {
            this.prompt = str;
            this.like = str2;
            this.dislike_response = str3;
        }

        @Required
        public FeedbackAttachment setPrompt(String str) {
            this.prompt = str;
            return this;
        }

        @Required
        public String getPrompt() {
            return this.prompt;
        }

        @Required
        public FeedbackAttachment setLike(String str) {
            this.like = str;
            return this;
        }

        @Required
        public String getLike() {
            return this.like;
        }

        @Required
        public FeedbackAttachment setDislikeResponse(String str) {
            this.dislike_response = str;
            return this;
        }

        @Required
        public String getDislikeResponse() {
            return this.dislike_response;
        }

        public FeedbackAttachment setDislike(String str) {
            this.dislike = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDislike() {
            return this.dislike;
        }

        public FeedbackAttachment setLikeResponse(String str) {
            this.like_response = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLikeResponse() {
            return this.like_response;
        }

        public FeedbackAttachment setDislikeReasons(List<String> list) {
            this.dislike_reasons = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getDislikeReasons() {
            return this.dislike_reasons;
        }
    }

    @NamespaceName(name = "ForeignDictionary", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ForeignDictionary implements InstructionPayload {
        @Required
        private String description;
        private Optional<List<ForeignDictionaryWordTranslations>> word_translations = Optional.empty();

        public ForeignDictionary() {
        }

        public ForeignDictionary(String str) {
            this.description = str;
        }

        @Required
        public ForeignDictionary setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        public ForeignDictionary setWordTranslations(List<ForeignDictionaryWordTranslations> list) {
            this.word_translations = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ForeignDictionaryWordTranslations>> getWordTranslations() {
            return this.word_translations;
        }
    }

    /* loaded from: classes3.dex */
    public static class ForeignDictionaryExampleSentence {
        @Required
        private String example_sentence_title;
        @Required
        private List<ForeignDictionarySentence> sentences;

        public ForeignDictionaryExampleSentence() {
        }

        public ForeignDictionaryExampleSentence(String str, List<ForeignDictionarySentence> list) {
            this.example_sentence_title = str;
            this.sentences = list;
        }

        @Required
        public ForeignDictionaryExampleSentence setExampleSentenceTitle(String str) {
            this.example_sentence_title = str;
            return this;
        }

        @Required
        public String getExampleSentenceTitle() {
            return this.example_sentence_title;
        }

        @Required
        public ForeignDictionaryExampleSentence setSentences(List<ForeignDictionarySentence> list) {
            this.sentences = list;
            return this;
        }

        @Required
        public List<ForeignDictionarySentence> getSentences() {
            return this.sentences;
        }
    }

    /* loaded from: classes3.dex */
    public static class ForeignDictionarySentence {
        @Required
        private String chinese_sentence;
        @Required
        private String foreign_sentence;

        public ForeignDictionarySentence() {
        }

        public ForeignDictionarySentence(String str, String str2) {
            this.foreign_sentence = str;
            this.chinese_sentence = str2;
        }

        @Required
        public ForeignDictionarySentence setForeignSentence(String str) {
            this.foreign_sentence = str;
            return this;
        }

        @Required
        public String getForeignSentence() {
            return this.foreign_sentence;
        }

        @Required
        public ForeignDictionarySentence setChineseSentence(String str) {
            this.chinese_sentence = str;
            return this;
        }

        @Required
        public String getChineseSentence() {
            return this.chinese_sentence;
        }
    }

    /* loaded from: classes3.dex */
    public static class ForeignDictionaryWord {
        @Required
        private String word_name;
        private Optional<String> word_audio_url = Optional.empty();
        private Optional<List<String>> speech_interpretations = Optional.empty();

        public ForeignDictionaryWord() {
        }

        public ForeignDictionaryWord(String str) {
            this.word_name = str;
        }

        @Required
        public ForeignDictionaryWord setWordName(String str) {
            this.word_name = str;
            return this;
        }

        @Required
        public String getWordName() {
            return this.word_name;
        }

        public ForeignDictionaryWord setWordAudioUrl(String str) {
            this.word_audio_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWordAudioUrl() {
            return this.word_audio_url;
        }

        public ForeignDictionaryWord setSpeechInterpretations(List<String> list) {
            this.speech_interpretations = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getSpeechInterpretations() {
            return this.speech_interpretations;
        }
    }

    /* loaded from: classes3.dex */
    public static class ForeignDictionaryWordTranslations {
        private Optional<ForeignDictionaryExampleSentence> example_sentence = Optional.empty();
        @Required
        private ForeignDictionaryWord word;

        public ForeignDictionaryWordTranslations() {
        }

        public ForeignDictionaryWordTranslations(ForeignDictionaryWord foreignDictionaryWord) {
            this.word = foreignDictionaryWord;
        }

        @Required
        public ForeignDictionaryWordTranslations setWord(ForeignDictionaryWord foreignDictionaryWord) {
            this.word = foreignDictionaryWord;
            return this;
        }

        @Required
        public ForeignDictionaryWord getWord() {
            return this.word;
        }

        public ForeignDictionaryWordTranslations setExampleSentence(ForeignDictionaryExampleSentence foreignDictionaryExampleSentence) {
            this.example_sentence = Optional.ofNullable(foreignDictionaryExampleSentence);
            return this;
        }

        public Optional<ForeignDictionaryExampleSentence> getExampleSentence() {
            return this.example_sentence;
        }
    }

    /* loaded from: classes3.dex */
    public static class FullScreen {
        private Optional<Launcher> action = Optional.empty();
        private Optional<String> task = Optional.empty();
        private Optional<List<ListsItem>> more_items = Optional.empty();

        public FullScreen setAction(Launcher launcher) {
            this.action = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getAction() {
            return this.action;
        }

        public FullScreen setTask(String str) {
            this.task = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTask() {
            return this.task;
        }

        public FullScreen setMoreItems(List<ListsItem> list) {
            this.more_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ListsItem>> getMoreItems() {
            return this.more_items;
        }
    }

    @NamespaceName(name = AIApiConstants.General.NAME, namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class General implements InstructionPayload {
        @Required
        private List<Image> images;
        @Required
        private Image skill_icon;
        @Required
        private String text;
        @Required
        private Title title;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<List<AbstractItem>> abstracts = Optional.empty();
        private Optional<GeneralDisplayType> type = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<GeneralUIType> ui_type = Optional.empty();

        public General() {
        }

        public General(Title title, String str, Image image, List<Image> list) {
            this.title = title;
            this.text = str;
            this.skill_icon = image;
            this.images = list;
        }

        @Required
        public General setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public General setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public General setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public General setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        @Required
        public General setImages(List<Image> list) {
            this.images = list;
            return this;
        }

        @Required
        public List<Image> getImages() {
            return this.images;
        }

        public General setAbstracts(List<AbstractItem> list) {
            this.abstracts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AbstractItem>> getAbstracts() {
            return this.abstracts;
        }

        public General setType(GeneralDisplayType generalDisplayType) {
            this.type = Optional.ofNullable(generalDisplayType);
            return this;
        }

        public Optional<GeneralDisplayType> getType() {
            return this.type;
        }

        public General setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public General setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public General setUiType(GeneralUIType generalUIType) {
            this.ui_type = Optional.ofNullable(generalUIType);
            return this;
        }

        public Optional<GeneralUIType> getUiType() {
            return this.ui_type;
        }
    }

    @NamespaceName(name = "General2", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class General2 implements InstructionPayload {
        @Required
        private RichText main_title;
        @Required
        private Image skill_icon;
        @Required
        private List<RichText> sub_titles;
        @Required
        private List<RichText> texts;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<String> specification = Optional.empty();

        public General2() {
        }

        public General2(RichText richText, List<RichText> list, List<RichText> list2, Image image) {
            this.main_title = richText;
            this.sub_titles = list;
            this.texts = list2;
            this.skill_icon = image;
        }

        @Required
        public General2 setMainTitle(RichText richText) {
            this.main_title = richText;
            return this;
        }

        @Required
        public RichText getMainTitle() {
            return this.main_title;
        }

        @Required
        public General2 setSubTitles(List<RichText> list) {
            this.sub_titles = list;
            return this;
        }

        @Required
        public List<RichText> getSubTitles() {
            return this.sub_titles;
        }

        @Required
        public General2 setTexts(List<RichText> list) {
            this.texts = list;
            return this;
        }

        @Required
        public List<RichText> getTexts() {
            return this.texts;
        }

        @Required
        public General2 setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public General2 setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public General2 setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public General2 setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public General2 setSpecification(String str) {
            this.specification = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSpecification() {
            return this.specification;
        }
    }

    @NamespaceName(name = "H5Page", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class H5Page implements InstructionPayload {
        @Required
        private ErrorDetail error_prompt;
        @Required
        private H5PageLoadType load_type;
        @Required
        private ObjectNode param;
        @Required
        private H5ParamType param_type;
        @Required
        private Image skill_icon;
        @Required
        private int timeout_in_milli;
        private Optional<String> load_url = Optional.empty();
        private Optional<String> load_html = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Common.MultiExecutionSequences> secondaries = Optional.empty();
        private Optional<H5PageExtraData> extra_data = Optional.empty();
        private Optional<Boolean> all_ui_h5 = Optional.empty();
        private Optional<H5PageSource> source = Optional.empty();
        private Optional<Boolean> reback = Optional.empty();
        private Optional<Boolean> auto_play_audio = Optional.empty();

        public H5Page() {
        }

        public H5Page(H5PageLoadType h5PageLoadType, int i, ErrorDetail errorDetail, Image image, ObjectNode objectNode, H5ParamType h5ParamType) {
            this.load_type = h5PageLoadType;
            this.timeout_in_milli = i;
            this.error_prompt = errorDetail;
            this.skill_icon = image;
            this.param = objectNode;
            this.param_type = h5ParamType;
        }

        @Required
        public H5Page setLoadType(H5PageLoadType h5PageLoadType) {
            this.load_type = h5PageLoadType;
            return this;
        }

        @Required
        public H5PageLoadType getLoadType() {
            return this.load_type;
        }

        @Required
        public H5Page setTimeoutInMilli(int i) {
            this.timeout_in_milli = i;
            return this;
        }

        @Required
        public int getTimeoutInMilli() {
            return this.timeout_in_milli;
        }

        @Required
        public H5Page setErrorPrompt(ErrorDetail errorDetail) {
            this.error_prompt = errorDetail;
            return this;
        }

        @Required
        public ErrorDetail getErrorPrompt() {
            return this.error_prompt;
        }

        @Required
        public H5Page setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        @Required
        public H5Page setParam(ObjectNode objectNode) {
            this.param = objectNode;
            return this;
        }

        @Required
        public ObjectNode getParam() {
            return this.param;
        }

        @Required
        public H5Page setParamType(H5ParamType h5ParamType) {
            this.param_type = h5ParamType;
            return this;
        }

        @Required
        public H5ParamType getParamType() {
            return this.param_type;
        }

        public H5Page setLoadUrl(String str) {
            this.load_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadUrl() {
            return this.load_url;
        }

        public H5Page setLoadHtml(String str) {
            this.load_html = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoadHtml() {
            return this.load_html;
        }

        public H5Page setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public H5Page setSecondaries(Common.MultiExecutionSequences multiExecutionSequences) {
            this.secondaries = Optional.ofNullable(multiExecutionSequences);
            return this;
        }

        public Optional<Common.MultiExecutionSequences> getSecondaries() {
            return this.secondaries;
        }

        public H5Page setExtraData(H5PageExtraData h5PageExtraData) {
            this.extra_data = Optional.ofNullable(h5PageExtraData);
            return this;
        }

        public Optional<H5PageExtraData> getExtraData() {
            return this.extra_data;
        }

        public H5Page setAllUiH5(boolean z) {
            this.all_ui_h5 = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAllUiH5() {
            return this.all_ui_h5;
        }

        public H5Page setSource(H5PageSource h5PageSource) {
            this.source = Optional.ofNullable(h5PageSource);
            return this;
        }

        public Optional<H5PageSource> getSource() {
            return this.source;
        }

        public H5Page setReback(boolean z) {
            this.reback = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isReback() {
            return this.reback;
        }

        public H5Page setAutoPlayAudio(boolean z) {
            this.auto_play_audio = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAutoPlayAudio() {
            return this.auto_play_audio;
        }
    }

    /* loaded from: classes3.dex */
    public static class H5PageExtraData {
        private Optional<String> query = Optional.empty();
        private Optional<String> text = Optional.empty();
        private Optional<String> to_speak = Optional.empty();

        public H5PageExtraData setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public H5PageExtraData setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }

        public H5PageExtraData setToSpeak(String str) {
            this.to_speak = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToSpeak() {
            return this.to_speak;
        }
    }

    @NamespaceName(name = "H5RefreshCard", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class H5RefreshCard implements InstructionPayload {
        private Optional<H5PageExtraData> extra_data = Optional.empty();
        @Required
        private ObjectNode param;
        @Required
        private H5ParamType param_type;

        public H5RefreshCard() {
        }

        public H5RefreshCard(H5ParamType h5ParamType, ObjectNode objectNode) {
            this.param_type = h5ParamType;
            this.param = objectNode;
        }

        @Required
        public H5RefreshCard setParamType(H5ParamType h5ParamType) {
            this.param_type = h5ParamType;
            return this;
        }

        @Required
        public H5ParamType getParamType() {
            return this.param_type;
        }

        @Required
        public H5RefreshCard setParam(ObjectNode objectNode) {
            this.param = objectNode;
            return this;
        }

        @Required
        public ObjectNode getParam() {
            return this.param;
        }

        public H5RefreshCard setExtraData(H5PageExtraData h5PageExtraData) {
            this.extra_data = Optional.ofNullable(h5PageExtraData);
            return this;
        }

        public Optional<H5PageExtraData> getExtraData() {
            return this.extra_data;
        }
    }

    /* loaded from: classes3.dex */
    public static class HomeInfo {
        @Required
        private String home_id;
        @Required
        private String home_name;
        private Optional<Boolean> resident = Optional.empty();
        private Optional<String> home_description = Optional.empty();
        private Optional<DeviceList> device_list = Optional.empty();
        private Optional<Boolean> is_device_list_show = Optional.empty();

        public HomeInfo() {
        }

        public HomeInfo(String str, String str2) {
            this.home_id = str;
            this.home_name = str2;
        }

        @Required
        public HomeInfo setHomeId(String str) {
            this.home_id = str;
            return this;
        }

        @Required
        public String getHomeId() {
            return this.home_id;
        }

        @Required
        public HomeInfo setHomeName(String str) {
            this.home_name = str;
            return this;
        }

        @Required
        public String getHomeName() {
            return this.home_name;
        }

        public HomeInfo setResident(boolean z) {
            this.resident = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isResident() {
            return this.resident;
        }

        public HomeInfo setHomeDescription(String str) {
            this.home_description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHomeDescription() {
            return this.home_description;
        }

        public HomeInfo setDeviceList(DeviceList deviceList) {
            this.device_list = Optional.ofNullable(deviceList);
            return this;
        }

        public Optional<DeviceList> getDeviceList() {
            return this.device_list;
        }

        public HomeInfo setIsDeviceListShow(boolean z) {
            this.is_device_list_show = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDeviceListShow() {
            return this.is_device_list_show;
        }
    }

    /* loaded from: classes3.dex */
    public static class HomeItem {
        @Required
        private Title title;
        private Optional<HomeInfo> info = Optional.empty();
        private Optional<List<Image>> images = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public HomeItem() {
        }

        public HomeItem(Title title) {
            this.title = title;
        }

        @Required
        public HomeItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        public HomeItem setInfo(HomeInfo homeInfo) {
            this.info = Optional.ofNullable(homeInfo);
            return this;
        }

        public Optional<HomeInfo> getInfo() {
            return this.info;
        }

        public HomeItem setImages(List<Image> list) {
            this.images = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Image>> getImages() {
            return this.images;
        }

        public HomeItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "HomeList", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class HomeList implements InstructionPayload {
        @Required
        private List<HomeItem> items;
        @Required
        private boolean show_index;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public HomeList() {
        }

        public HomeList(List<HomeItem> list, boolean z) {
            this.items = list;
            this.show_index = z;
        }

        @Required
        public HomeList setItems(List<HomeItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<HomeItem> getItems() {
            return this.items;
        }

        @Required
        public HomeList setShowIndex(boolean z) {
            this.show_index = z;
            return this;
        }

        @Required
        public boolean isShowIndex() {
            return this.show_index;
        }

        public HomeList setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public HomeList setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public HomeList setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public HomeList setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class Image {
        @Required
        private String description;
        private Optional<ImageLauncher> launcher = Optional.empty();
        @Required
        private List<ImageSource> sources;

        public Image() {
        }

        public Image(String str, List<ImageSource> list) {
            this.description = str;
            this.sources = list;
        }

        @Required
        public Image setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        @Required
        public Image setSources(List<ImageSource> list) {
            this.sources = list;
            return this;
        }

        @Required
        public List<ImageSource> getSources() {
            return this.sources;
        }

        public Image setLauncher(ImageLauncher imageLauncher) {
            this.launcher = Optional.ofNullable(imageLauncher);
            return this;
        }

        public Optional<ImageLauncher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class ImageAndroidIntent {
        @Required
        private String pkg_name;
        @Required
        private String type;
        @Required
        private String uri;
        private Optional<String> permission = Optional.empty();
        private Optional<OnError> on_error = Optional.empty();
        private Optional<Integer> min_version = Optional.empty();
        private Optional<ObjectNode> params = Optional.empty();

        public ImageAndroidIntent() {
        }

        public ImageAndroidIntent(String str, String str2, String str3) {
            this.type = str;
            this.pkg_name = str2;
            this.uri = str3;
        }

        @Required
        public ImageAndroidIntent setType(String str) {
            this.type = str;
            return this;
        }

        @Required
        public String getType() {
            return this.type;
        }

        @Required
        public ImageAndroidIntent setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public ImageAndroidIntent setUri(String str) {
            this.uri = str;
            return this;
        }

        @Required
        public String getUri() {
            return this.uri;
        }

        public ImageAndroidIntent setPermission(String str) {
            this.permission = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPermission() {
            return this.permission;
        }

        public ImageAndroidIntent setOnError(OnError onError) {
            this.on_error = Optional.ofNullable(onError);
            return this;
        }

        public Optional<OnError> getOnError() {
            return this.on_error;
        }

        public ImageAndroidIntent setMinVersion(int i) {
            this.min_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMinVersion() {
            return this.min_version;
        }

        public ImageAndroidIntent setParams(ObjectNode objectNode) {
            this.params = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParams() {
            return this.params;
        }
    }

    @NamespaceName(name = "ImageCard", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ImageCard implements InstructionPayload {
        @Required
        private Image image;
        private Optional<Title> title = Optional.empty();
        private Optional<Integer> duration = Optional.empty();

        public ImageCard() {
        }

        public ImageCard(Image image) {
            this.image = image;
        }

        @Required
        public ImageCard setImage(Image image) {
            this.image = image;
            return this;
        }

        @Required
        public Image getImage() {
            return this.image;
        }

        public ImageCard setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public ImageCard setDuration(int i) {
            this.duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDuration() {
            return this.duration;
        }
    }

    /* loaded from: classes3.dex */
    public static class ImageLauncher {
        private Optional<String> url = Optional.empty();
        private Optional<ImageAndroidIntent> intent = Optional.empty();

        public ImageLauncher setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public ImageLauncher setIntent(ImageAndroidIntent imageAndroidIntent) {
            this.intent = Optional.ofNullable(imageAndroidIntent);
            return this;
        }

        public Optional<ImageAndroidIntent> getIntent() {
            return this.intent;
        }
    }

    /* loaded from: classes3.dex */
    public static class ImageSource {
        @Required
        private String url;
        private Optional<Integer> id = Optional.empty();
        private Optional<String> bg_url = Optional.empty();
        private Optional<String> size = Optional.empty();
        private Optional<Integer> width = Optional.empty();
        private Optional<Integer> height = Optional.empty();
        private Optional<Boolean> authentication = Optional.empty();
        private Optional<String> token = Optional.empty();
        private Optional<String> md5 = Optional.empty();

        public ImageSource() {
        }

        public ImageSource(String str) {
            this.url = str;
        }

        @Required
        public ImageSource setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        public ImageSource setId(int i) {
            this.id = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getId() {
            return this.id;
        }

        public ImageSource setBgUrl(String str) {
            this.bg_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBgUrl() {
            return this.bg_url;
        }

        public ImageSource setSize(String str) {
            this.size = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSize() {
            return this.size;
        }

        public ImageSource setWidth(int i) {
            this.width = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getWidth() {
            return this.width;
        }

        public ImageSource setHeight(int i) {
            this.height = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getHeight() {
            return this.height;
        }

        public ImageSource setAuthentication(boolean z) {
            this.authentication = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAuthentication() {
            return this.authentication;
        }

        public ImageSource setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }

        public ImageSource setMd5(String str) {
            this.md5 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMd5() {
            return this.md5;
        }
    }

    /* loaded from: classes3.dex */
    public static class JumpItem {
        private Optional<Title> title = Optional.empty();
        private Optional<Image> icon = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public JumpItem setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public JumpItem setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public JumpItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "Knowledge", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Knowledge implements EventPayload {
        private Optional<Integer> count = Optional.empty();
        @Required
        private KnowledgeType type;

        public Knowledge() {
        }

        public Knowledge(KnowledgeType knowledgeType) {
            this.type = knowledgeType;
        }

        @Required
        public Knowledge setType(KnowledgeType knowledgeType) {
            this.type = knowledgeType;
            return this;
        }

        @Required
        public KnowledgeType getType() {
            return this.type;
        }

        public Knowledge setCount(int i) {
            this.count = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCount() {
            return this.count;
        }
    }

    @NamespaceName(name = "KnowledgeInfo", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class KnowledgeInfo implements InstructionPayload {
        @Required
        private List<KnowledgeItem> items;
        private Optional<Image> skill_icon = Optional.empty();

        public KnowledgeInfo() {
        }

        public KnowledgeInfo(List<KnowledgeItem> list) {
            this.items = list;
        }

        @Required
        public KnowledgeInfo setItems(List<KnowledgeItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<KnowledgeItem> getItems() {
            return this.items;
        }

        public KnowledgeInfo setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class KnowledgeItem {
        @Required
        private String query;
        @Required
        private KnowledgeType type;
        private Optional<String> name = Optional.empty();
        private Optional<String> property = Optional.empty();
        private Optional<String> summary = Optional.empty();
        private Optional<Image> image = Optional.empty();

        public KnowledgeItem() {
        }

        public KnowledgeItem(KnowledgeType knowledgeType, String str) {
            this.type = knowledgeType;
            this.query = str;
        }

        @Required
        public KnowledgeItem setType(KnowledgeType knowledgeType) {
            this.type = knowledgeType;
            return this;
        }

        @Required
        public KnowledgeType getType() {
            return this.type;
        }

        @Required
        public KnowledgeItem setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        public KnowledgeItem setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public KnowledgeItem setProperty(String str) {
            this.property = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getProperty() {
            return this.property;
        }

        public KnowledgeItem setSummary(String str) {
            this.summary = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSummary() {
            return this.summary;
        }

        public KnowledgeItem setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }
    }

    /* loaded from: classes3.dex */
    public static class Launcher {
        private Optional<String> url = Optional.empty();
        private Optional<AndroidIntent> intent = Optional.empty();
        private Optional<QuickAppLaunchInfo> quick_app = Optional.empty();
        private Optional<Nlp.InvokeNlpRequest> next_request = Optional.empty();
        private Optional<Common.GeneralQuickAppInfo> general_quick_app = Optional.empty();
        private Optional<ArrayNode> instructions = Optional.empty();
        private Optional<EventRequestInfo> event = Optional.empty();

        public Launcher setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public Launcher setIntent(AndroidIntent androidIntent) {
            this.intent = Optional.ofNullable(androidIntent);
            return this;
        }

        public Optional<AndroidIntent> getIntent() {
            return this.intent;
        }

        public Launcher setQuickApp(QuickAppLaunchInfo quickAppLaunchInfo) {
            this.quick_app = Optional.ofNullable(quickAppLaunchInfo);
            return this;
        }

        public Optional<QuickAppLaunchInfo> getQuickApp() {
            return this.quick_app;
        }

        public Launcher setNextRequest(Nlp.InvokeNlpRequest invokeNlpRequest) {
            this.next_request = Optional.ofNullable(invokeNlpRequest);
            return this;
        }

        public Optional<Nlp.InvokeNlpRequest> getNextRequest() {
            return this.next_request;
        }

        public Launcher setGeneralQuickApp(Common.GeneralQuickAppInfo generalQuickAppInfo) {
            this.general_quick_app = Optional.ofNullable(generalQuickAppInfo);
            return this;
        }

        public Optional<Common.GeneralQuickAppInfo> getGeneralQuickApp() {
            return this.general_quick_app;
        }

        public Launcher setInstructions(ArrayNode arrayNode) {
            this.instructions = Optional.ofNullable(arrayNode);
            return this;
        }

        public Optional<ArrayNode> getInstructions() {
            return this.instructions;
        }

        public Launcher setEvent(EventRequestInfo eventRequestInfo) {
            this.event = Optional.ofNullable(eventRequestInfo);
            return this;
        }

        public Optional<EventRequestInfo> getEvent() {
            return this.event;
        }
    }

    @NamespaceName(name = "Lists", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Lists implements InstructionPayload {
        @Required
        private List<ListsItem> items;
        @Required
        private Image skill_icon;
        private Optional<Title> title = Optional.empty();
        private Optional<ListsMoreItem> more = Optional.empty();
        private Optional<ListsItemDisplayType> item_display_type = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<ListsType> type = Optional.empty();

        public Lists() {
        }

        public Lists(Image image, List<ListsItem> list) {
            this.skill_icon = image;
            this.items = list;
        }

        public Lists setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        @Required
        public Lists setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        @Required
        public Lists setItems(List<ListsItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<ListsItem> getItems() {
            return this.items;
        }

        public Lists setMore(ListsMoreItem listsMoreItem) {
            this.more = Optional.ofNullable(listsMoreItem);
            return this;
        }

        public Optional<ListsMoreItem> getMore() {
            return this.more;
        }

        public Lists setItemDisplayType(ListsItemDisplayType listsItemDisplayType) {
            this.item_display_type = Optional.ofNullable(listsItemDisplayType);
            return this;
        }

        public Optional<ListsItemDisplayType> getItemDisplayType() {
            return this.item_display_type;
        }

        public Lists setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Lists setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Lists setType(ListsType listsType) {
            this.type = Optional.ofNullable(listsType);
            return this;
        }

        public Optional<ListsType> getType() {
            return this.type;
        }
    }

    /* loaded from: classes3.dex */
    public static class ListsDetail {
        @Required
        private Image skill_icon;
        @Required
        private Title title;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<List<Lists>> abstracts = Optional.empty();

        public ListsDetail() {
        }

        public ListsDetail(Title title, Image image) {
            this.title = title;
            this.skill_icon = image;
        }

        @Required
        public ListsDetail setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public ListsDetail setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public ListsDetail setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public ListsDetail setAbstracts(List<Lists> list) {
            this.abstracts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Lists>> getAbstracts() {
            return this.abstracts;
        }
    }

    /* loaded from: classes3.dex */
    public static class ListsItem {
        @Required
        private List<Image> images;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<List<ListsSubItem>> sub_items = Optional.empty();
        @Required
        private String text;
        @Required
        private Title title;

        public ListsItem() {
        }

        public ListsItem(Title title, String str, List<Image> list) {
            this.title = title;
            this.text = str;
            this.images = list;
        }

        @Required
        public ListsItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public ListsItem setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public ListsItem setImages(List<Image> list) {
            this.images = list;
            return this;
        }

        @Required
        public List<Image> getImages() {
            return this.images;
        }

        public ListsItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public ListsItem setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public ListsItem setSubItems(List<ListsSubItem> list) {
            this.sub_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ListsSubItem>> getSubItems() {
            return this.sub_items;
        }
    }

    /* loaded from: classes3.dex */
    public static class ListsMoreItem {
        @Required
        private Launcher launcher;
        @Required
        private Title title;

        public ListsMoreItem() {
        }

        public ListsMoreItem(Title title, Launcher launcher) {
            this.title = title;
            this.launcher = launcher;
        }

        @Required
        public ListsMoreItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public ListsMoreItem setLauncher(Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Launcher getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class ListsSubItem {
        private Optional<List<Image>> images = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        @Required
        private String text;
        @Required
        private Title title;

        public ListsSubItem() {
        }

        public ListsSubItem(Title title, String str) {
            this.title = title;
            this.text = str;
        }

        @Required
        public ListsSubItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public ListsSubItem setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public ListsSubItem setImages(List<Image> list) {
            this.images = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Image>> getImages() {
            return this.images;
        }

        public ListsSubItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public ListsSubItem setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class LocalTarget {
        private Optional<String> identifier = Optional.empty();
        @Required
        private String name;

        public LocalTarget() {
        }

        public LocalTarget(String str) {
            this.name = str;
        }

        @Required
        public LocalTarget setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public LocalTarget setIdentifier(String str) {
            this.identifier = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIdentifier() {
            return this.identifier;
        }
    }

    /* loaded from: classes3.dex */
    public static class MachineTranslation {
        @Required
        private String trans_text;
        @Required
        private String trans_text_audio_url;
        private Optional<String> word_meaning = Optional.empty();
        private Optional<String> ph_symbol = Optional.empty();

        public MachineTranslation() {
        }

        public MachineTranslation(String str, String str2) {
            this.trans_text = str;
            this.trans_text_audio_url = str2;
        }

        @Required
        public MachineTranslation setTransText(String str) {
            this.trans_text = str;
            return this;
        }

        @Required
        public String getTransText() {
            return this.trans_text;
        }

        @Required
        public MachineTranslation setTransTextAudioUrl(String str) {
            this.trans_text_audio_url = str;
            return this;
        }

        @Required
        public String getTransTextAudioUrl() {
            return this.trans_text_audio_url;
        }

        public MachineTranslation setWordMeaning(String str) {
            this.word_meaning = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWordMeaning() {
            return this.word_meaning;
        }

        public MachineTranslation setPhSymbol(String str) {
            this.ph_symbol = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhSymbol() {
            return this.ph_symbol;
        }
    }

    /* loaded from: classes3.dex */
    public static class Match {
        private Optional<Button> button_link = Optional.empty();
        private Optional<MoreLink> more_link = Optional.empty();
        private Optional<PredictRate> predict_rate = Optional.empty();
        @Required
        private MatchSchedule schedule;
        @Required
        private Title title;

        public Match() {
        }

        public Match(Title title, MatchSchedule matchSchedule) {
            this.title = title;
            this.schedule = matchSchedule;
        }

        @Required
        public Match setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public Match setSchedule(MatchSchedule matchSchedule) {
            this.schedule = matchSchedule;
            return this;
        }

        @Required
        public MatchSchedule getSchedule() {
            return this.schedule;
        }

        public Match setButtonLink(Button button) {
            this.button_link = Optional.ofNullable(button);
            return this;
        }

        public Optional<Button> getButtonLink() {
            return this.button_link;
        }

        public Match setMoreLink(MoreLink moreLink) {
            this.more_link = Optional.ofNullable(moreLink);
            return this;
        }

        public Optional<MoreLink> getMoreLink() {
            return this.more_link;
        }

        public Match setPredictRate(PredictRate predictRate) {
            this.predict_rate = Optional.ofNullable(predictRate);
            return this;
        }

        public Optional<PredictRate> getPredictRate() {
            return this.predict_rate;
        }
    }

    /* loaded from: classes3.dex */
    public static class MatchSchedule {
        @Required
        private Team team1;
        @Required
        private Team team2;
        private Optional<String> status = Optional.empty();
        private Optional<String> score = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public MatchSchedule() {
        }

        public MatchSchedule(Team team, Team team2) {
            this.team1 = team;
            this.team2 = team2;
        }

        @Required
        public MatchSchedule setTeam1(Team team) {
            this.team1 = team;
            return this;
        }

        @Required
        public Team getTeam1() {
            return this.team1;
        }

        @Required
        public MatchSchedule setTeam2(Team team) {
            this.team2 = team;
            return this;
        }

        @Required
        public Team getTeam2() {
            return this.team2;
        }

        public MatchSchedule setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public MatchSchedule setScore(String str) {
            this.score = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getScore() {
            return this.score;
        }

        public MatchSchedule setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "MatchUp", namespace = AIApiConstants.Template.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class MatchUp implements InstructionPayload {
        @Required
        private List<Match> data;
        @Required
        private Image skill_icon;
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public MatchUp() {
        }

        public MatchUp(List<Match> list, Image image) {
            this.data = list;
            this.skill_icon = image;
        }

        @Required
        public MatchUp setData(List<Match> list) {
            this.data = list;
            return this;
        }

        @Required
        public List<Match> getData() {
            return this.data;
        }

        @Required
        public MatchUp setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public MatchUp setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public MatchUp setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    @NamespaceName(name = AIApiConstants.Memo.NAME, namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Memo implements InstructionPayload {
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        @Required
        private List<MemoItem> items;
        @Required
        private Image skill_icon;
        @Required
        private Title title;

        public Memo() {
        }

        public Memo(Title title, Image image, List<MemoItem> list) {
            this.title = title;
            this.skill_icon = image;
            this.items = list;
        }

        @Required
        public Memo setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public Memo setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        @Required
        public Memo setItems(List<MemoItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<MemoItem> getItems() {
            return this.items;
        }

        public Memo setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Memo setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class MemoItem {
        @Required
        private String item_id;
        @Required
        private String text;
        private Optional<AudioPlayer.Stream> audio = Optional.empty();
        private Optional<Image> image = Optional.empty();
        private Optional<Long> create_time = Optional.empty();

        public MemoItem() {
        }

        public MemoItem(String str, String str2) {
            this.item_id = str;
            this.text = str2;
        }

        @Required
        public MemoItem setItemId(String str) {
            this.item_id = str;
            return this;
        }

        @Required
        public String getItemId() {
            return this.item_id;
        }

        @Required
        public MemoItem setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public MemoItem setAudio(AudioPlayer.Stream stream) {
            this.audio = Optional.ofNullable(stream);
            return this;
        }

        public Optional<AudioPlayer.Stream> getAudio() {
            return this.audio;
        }

        public MemoItem setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }

        public MemoItem setCreateTime(long j) {
            this.create_time = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getCreateTime() {
            return this.create_time;
        }
    }

    @NamespaceName(name = "Menstruation", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Menstruation implements InstructionPayload {
        @Required
        private List<ApplicationSettings.MenstrualPeriod> items;
        @Required
        private ApplicationSettings.MenstruationOperationType operation_type;
        private Optional<Launcher> intent = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();

        public Menstruation() {
        }

        public Menstruation(ApplicationSettings.MenstruationOperationType menstruationOperationType, List<ApplicationSettings.MenstrualPeriod> list) {
            this.operation_type = menstruationOperationType;
            this.items = list;
        }

        @Required
        public Menstruation setOperationType(ApplicationSettings.MenstruationOperationType menstruationOperationType) {
            this.operation_type = menstruationOperationType;
            return this;
        }

        @Required
        public ApplicationSettings.MenstruationOperationType getOperationType() {
            return this.operation_type;
        }

        @Required
        public Menstruation setItems(List<ApplicationSettings.MenstrualPeriod> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<ApplicationSettings.MenstrualPeriod> getItems() {
            return this.items;
        }

        public Menstruation setIntent(Launcher launcher) {
            this.intent = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getIntent() {
            return this.intent;
        }

        public Menstruation setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "Message", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Message implements InstructionPayload {
        @Required
        private MessageSendDeviceCategory send_device_category;
        @Required
        private MessageSendDeviceType send_device_type;
        @Required
        private MessageSendType send_type;
        private Optional<String> asr_text = Optional.empty();
        private Optional<String> room_name = Optional.empty();
        private Optional<String> device_name = Optional.empty();
        private Optional<String> home_name = Optional.empty();
        private Optional<String> home_id = Optional.empty();
        private Optional<String> room_id = Optional.empty();
        private Optional<String> sender_device_id = Optional.empty();
        private Optional<Image> family_avatar = Optional.empty();
        private Optional<Image> personal_avatar = Optional.empty();
        private Optional<String> id = Optional.empty();

        public Message() {
        }

        public Message(MessageSendType messageSendType, MessageSendDeviceType messageSendDeviceType, MessageSendDeviceCategory messageSendDeviceCategory) {
            this.send_type = messageSendType;
            this.send_device_type = messageSendDeviceType;
            this.send_device_category = messageSendDeviceCategory;
        }

        @Required
        public Message setSendType(MessageSendType messageSendType) {
            this.send_type = messageSendType;
            return this;
        }

        @Required
        public MessageSendType getSendType() {
            return this.send_type;
        }

        @Required
        public Message setSendDeviceType(MessageSendDeviceType messageSendDeviceType) {
            this.send_device_type = messageSendDeviceType;
            return this;
        }

        @Required
        public MessageSendDeviceType getSendDeviceType() {
            return this.send_device_type;
        }

        @Required
        public Message setSendDeviceCategory(MessageSendDeviceCategory messageSendDeviceCategory) {
            this.send_device_category = messageSendDeviceCategory;
            return this;
        }

        @Required
        public MessageSendDeviceCategory getSendDeviceCategory() {
            return this.send_device_category;
        }

        public Message setAsrText(String str) {
            this.asr_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAsrText() {
            return this.asr_text;
        }

        public Message setRoomName(String str) {
            this.room_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoomName() {
            return this.room_name;
        }

        public Message setDeviceName(String str) {
            this.device_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceName() {
            return this.device_name;
        }

        public Message setHomeName(String str) {
            this.home_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHomeName() {
            return this.home_name;
        }

        public Message setHomeId(String str) {
            this.home_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHomeId() {
            return this.home_id;
        }

        public Message setRoomId(String str) {
            this.room_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getRoomId() {
            return this.room_id;
        }

        public Message setSenderDeviceId(String str) {
            this.sender_device_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSenderDeviceId() {
            return this.sender_device_id;
        }

        public Message setFamilyAvatar(Image image) {
            this.family_avatar = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getFamilyAvatar() {
            return this.family_avatar;
        }

        public Message setPersonalAvatar(Image image) {
            this.personal_avatar = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getPersonalAvatar() {
            return this.personal_avatar;
        }

        public Message setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class MoreLink {
        @Required
        private Launcher launcher;
        @Required
        private Title title;

        public MoreLink() {
        }

        public MoreLink(Title title, Launcher launcher) {
            this.title = title;
            this.launcher = launcher;
        }

        @Required
        public MoreLink setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public MoreLink setLauncher(Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Launcher getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "Music", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Music implements InstructionPayload {
        @Required
        private List<MusicEntity> group;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<String> loop_mode = Optional.empty();
        private Optional<Title> title = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<QueryType> query_type = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Common.MultiExecutionSequences> secondaries = Optional.empty();

        public Music() {
        }

        public Music(List<MusicEntity> list) {
            this.group = list;
        }

        @Required
        public Music setGroup(List<MusicEntity> list) {
            this.group = list;
            return this;
        }

        @Required
        public List<MusicEntity> getGroup() {
            return this.group;
        }

        public Music setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Music setLoopMode(String str) {
            this.loop_mode = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLoopMode() {
            return this.loop_mode;
        }

        public Music setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public Music setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Music setQueryType(QueryType queryType) {
            this.query_type = Optional.ofNullable(queryType);
            return this;
        }

        public Optional<QueryType> getQueryType() {
            return this.query_type;
        }

        public Music setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Music setSecondaries(Common.MultiExecutionSequences multiExecutionSequences) {
            this.secondaries = Optional.ofNullable(multiExecutionSequences);
            return this;
        }

        public Optional<Common.MultiExecutionSequences> getSecondaries() {
            return this.secondaries;
        }
    }

    /* loaded from: classes3.dex */
    public static class MusicEntity {
        @Required

        /* renamed from: app */
        private AppEntity f199app;
        @Required
        private List<AudioInfo> audio_items;
        @Required
        private int auto_play_len;
        @Required
        private AudioProvider provider;
        @Required
        private Image skill_icon;

        public MusicEntity() {
        }

        public MusicEntity(AudioProvider audioProvider, int i, List<AudioInfo> list, AppEntity appEntity, Image image) {
            this.provider = audioProvider;
            this.auto_play_len = i;
            this.audio_items = list;
            this.f199app = appEntity;
            this.skill_icon = image;
        }

        @Required
        public MusicEntity setProvider(AudioProvider audioProvider) {
            this.provider = audioProvider;
            return this;
        }

        @Required
        public AudioProvider getProvider() {
            return this.provider;
        }

        @Required
        public MusicEntity setAutoPlayLen(int i) {
            this.auto_play_len = i;
            return this;
        }

        @Required
        public int getAutoPlayLen() {
            return this.auto_play_len;
        }

        @Required
        public MusicEntity setAudioItems(List<AudioInfo> list) {
            this.audio_items = list;
            return this;
        }

        @Required
        public List<AudioInfo> getAudioItems() {
            return this.audio_items;
        }

        @Required
        public MusicEntity setApp(AppEntity appEntity) {
            this.f199app = appEntity;
            return this;
        }

        @Required
        public AppEntity getApp() {
            return this.f199app;
        }

        @Required
        public MusicEntity setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class OnError {
        @Required
        private ErrorDetail not_installed;
        @Required
        private ErrorDetail unknown;
        @Required
        private ErrorDetail version_too_low;

        public OnError() {
        }

        public OnError(ErrorDetail errorDetail, ErrorDetail errorDetail2, ErrorDetail errorDetail3) {
            this.version_too_low = errorDetail;
            this.not_installed = errorDetail2;
            this.unknown = errorDetail3;
        }

        @Required
        public OnError setVersionTooLow(ErrorDetail errorDetail) {
            this.version_too_low = errorDetail;
            return this;
        }

        @Required
        public ErrorDetail getVersionTooLow() {
            return this.version_too_low;
        }

        @Required
        public OnError setNotInstalled(ErrorDetail errorDetail) {
            this.not_installed = errorDetail;
            return this;
        }

        @Required
        public ErrorDetail getNotInstalled() {
            return this.not_installed;
        }

        @Required
        public OnError setUnknown(ErrorDetail errorDetail) {
            this.unknown = errorDetail;
            return this;
        }

        @Required
        public ErrorDetail getUnknown() {
            return this.unknown;
        }
    }

    @NamespaceName(name = "OralExamination", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class OralExamination implements InstructionPayload {
        @Required
        private ObjectNode result;

        public OralExamination() {
        }

        public OralExamination(ObjectNode objectNode) {
            this.result = objectNode;
        }

        @Required
        public OralExamination setResult(ObjectNode objectNode) {
            this.result = objectNode;
            return this;
        }

        @Required
        public ObjectNode getResult() {
            return this.result;
        }
    }

    @NamespaceName(name = "PersonDetail", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PersonDetail implements InstructionPayload {
        @Required
        private Cineaste cineaste;

        public PersonDetail() {
        }

        public PersonDetail(Cineaste cineaste) {
            this.cineaste = cineaste;
        }

        @Required
        public PersonDetail setCineaste(Cineaste cineaste) {
            this.cineaste = cineaste;
            return this;
        }

        @Required
        public Cineaste getCineaste() {
            return this.cineaste;
        }
    }

    @NamespaceName(name = "PersonDisambiguation", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PersonDisambiguation implements InstructionPayload {
        @Required
        private List<CineasteWithTag> candidates;
        @Required
        private int hits;

        public PersonDisambiguation() {
        }

        public PersonDisambiguation(int i, List<CineasteWithTag> list) {
            this.hits = i;
            this.candidates = list;
        }

        @Required
        public PersonDisambiguation setHits(int i) {
            this.hits = i;
            return this;
        }

        @Required
        public int getHits() {
            return this.hits;
        }

        @Required
        public PersonDisambiguation setCandidates(List<CineasteWithTag> list) {
            this.candidates = list;
            return this;
        }

        @Required
        public List<CineasteWithTag> getCandidates() {
            return this.candidates;
        }
    }

    @NamespaceName(name = "PersonSearchList", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PersonSearchList implements InstructionPayload {
        @Required
        private List<Cineaste> cineastes;
        @Required
        private SearchHitLevel hit_level;
        @Required
        private long hits;

        public PersonSearchList() {
        }

        public PersonSearchList(SearchHitLevel searchHitLevel, long j, List<Cineaste> list) {
            this.hit_level = searchHitLevel;
            this.hits = j;
            this.cineastes = list;
        }

        @Required
        public PersonSearchList setHitLevel(SearchHitLevel searchHitLevel) {
            this.hit_level = searchHitLevel;
            return this;
        }

        @Required
        public SearchHitLevel getHitLevel() {
            return this.hit_level;
        }

        @Required
        public PersonSearchList setHits(long j) {
            this.hits = j;
            return this;
        }

        @Required
        public long getHits() {
            return this.hits;
        }

        @Required
        public PersonSearchList setCineastes(List<Cineaste> list) {
            this.cineastes = list;
            return this;
        }

        @Required
        public List<Cineaste> getCineastes() {
            return this.cineastes;
        }
    }

    @NamespaceName(name = "PlayInfo", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PlayInfo implements InstructionPayload {
        @Required
        private List<PlayInfoItem> items;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<DisplayControl> display_control = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<PlayInfoItemDisplayType> display_type = Optional.empty();
        private Optional<AudioPlayer.PlayBehavior> play_behavior = Optional.empty();
        private Optional<PlayInfoType> type = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<String> cp = Optional.empty();

        public PlayInfo() {
        }

        public PlayInfo(List<PlayInfoItem> list) {
            this.items = list;
        }

        @Required
        public PlayInfo setItems(List<PlayInfoItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<PlayInfoItem> getItems() {
            return this.items;
        }

        public PlayInfo setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public PlayInfo setDisplayControl(DisplayControl displayControl) {
            this.display_control = Optional.ofNullable(displayControl);
            return this;
        }

        public Optional<DisplayControl> getDisplayControl() {
            return this.display_control;
        }

        public PlayInfo setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public PlayInfo setDisplayType(PlayInfoItemDisplayType playInfoItemDisplayType) {
            this.display_type = Optional.ofNullable(playInfoItemDisplayType);
            return this;
        }

        public Optional<PlayInfoItemDisplayType> getDisplayType() {
            return this.display_type;
        }

        public PlayInfo setPlayBehavior(AudioPlayer.PlayBehavior playBehavior) {
            this.play_behavior = Optional.ofNullable(playBehavior);
            return this;
        }

        public Optional<AudioPlayer.PlayBehavior> getPlayBehavior() {
            return this.play_behavior;
        }

        public PlayInfo setType(PlayInfoType playInfoType) {
            this.type = Optional.ofNullable(playInfoType);
            return this;
        }

        public Optional<PlayInfoType> getType() {
            return this.type;
        }

        public PlayInfo setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public PlayInfo setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public PlayInfo setCp(String str) {
            this.cp = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCp() {
            return this.cp;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlayInfoItem {
        @Required
        private String audio_id;
        @Required
        private List<String> controls;
        @Required
        private Title title;
        private Optional<Integer> duration_in_ms = Optional.empty();
        private Optional<Image> cover = Optional.empty();
        private Optional<PlayInfoItemProvider> provider = Optional.empty();
        private Optional<String> text = Optional.empty();
        private Optional<Common.AudioType> audio_type = Optional.empty();
        private Optional<String> lyric_uri = Optional.empty();
        @Deprecated
        private Optional<String> mv_id = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<AudioPlayer.Stream> mv_stream = Optional.empty();
        private Optional<Boolean> is_vip = Optional.empty();
        private Optional<Boolean> payment = Optional.empty();

        public PlayInfoItem() {
        }

        public PlayInfoItem(String str, List<String> list, Title title) {
            this.audio_id = str;
            this.controls = list;
            this.title = title;
        }

        @Required
        public PlayInfoItem setAudioId(String str) {
            this.audio_id = str;
            return this;
        }

        @Required
        public String getAudioId() {
            return this.audio_id;
        }

        @Required
        public PlayInfoItem setControls(List<String> list) {
            this.controls = list;
            return this;
        }

        @Required
        public List<String> getControls() {
            return this.controls;
        }

        @Required
        public PlayInfoItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        public PlayInfoItem setDurationInMs(int i) {
            this.duration_in_ms = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDurationInMs() {
            return this.duration_in_ms;
        }

        public PlayInfoItem setCover(Image image) {
            this.cover = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getCover() {
            return this.cover;
        }

        public PlayInfoItem setProvider(PlayInfoItemProvider playInfoItemProvider) {
            this.provider = Optional.ofNullable(playInfoItemProvider);
            return this;
        }

        public Optional<PlayInfoItemProvider> getProvider() {
            return this.provider;
        }

        public PlayInfoItem setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }

        public PlayInfoItem setAudioType(Common.AudioType audioType) {
            this.audio_type = Optional.ofNullable(audioType);
            return this;
        }

        public Optional<Common.AudioType> getAudioType() {
            return this.audio_type;
        }

        public PlayInfoItem setLyricUri(String str) {
            this.lyric_uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLyricUri() {
            return this.lyric_uri;
        }

        @Deprecated
        public PlayInfoItem setMvId(String str) {
            this.mv_id = Optional.ofNullable(str);
            return this;
        }

        @Deprecated
        public Optional<String> getMvId() {
            return this.mv_id;
        }

        public PlayInfoItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public PlayInfoItem setMvStream(AudioPlayer.Stream stream) {
            this.mv_stream = Optional.ofNullable(stream);
            return this;
        }

        public Optional<AudioPlayer.Stream> getMvStream() {
            return this.mv_stream;
        }

        public PlayInfoItem setIsVip(boolean z) {
            this.is_vip = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isVip() {
            return this.is_vip;
        }

        public PlayInfoItem setPayment(boolean z) {
            this.payment = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isPayment() {
            return this.payment;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlayInfoItemProvider {
        private Optional<Image> logo = Optional.empty();
        @Required
        private String name;

        public PlayInfoItemProvider() {
        }

        public PlayInfoItemProvider(String str) {
            this.name = str;
        }

        @Required
        public PlayInfoItemProvider setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public PlayInfoItemProvider setLogo(Image image) {
            this.logo = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getLogo() {
            return this.logo;
        }
    }

    @NamespaceName(name = "PlayTTS", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PlayTTS implements InstructionPayload {
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        @Required
        private List<PlayTTSItem> items;
        @Required
        private Image skill_icon;

        public PlayTTS() {
        }

        public PlayTTS(List<PlayTTSItem> list, Image image) {
            this.items = list;
            this.skill_icon = image;
        }

        @Required
        public PlayTTS setItems(List<PlayTTSItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<PlayTTSItem> getItems() {
            return this.items;
        }

        @Required
        public PlayTTS setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public PlayTTS setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public PlayTTS setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class PlayTTSItem {
        @Required
        private String album_id;
        private Optional<Title> title = Optional.empty();
        private Optional<String> summary = Optional.empty();
        private Optional<Image> cover = Optional.empty();
        private Optional<List<TTSTextItem>> texts = Optional.empty();

        public PlayTTSItem() {
        }

        public PlayTTSItem(String str) {
            this.album_id = str;
        }

        @Required
        public PlayTTSItem setAlbumId(String str) {
            this.album_id = str;
            return this;
        }

        @Required
        public String getAlbumId() {
            return this.album_id;
        }

        public PlayTTSItem setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public PlayTTSItem setSummary(String str) {
            this.summary = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSummary() {
            return this.summary;
        }

        public PlayTTSItem setCover(Image image) {
            this.cover = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getCover() {
            return this.cover;
        }

        public PlayTTSItem setTexts(List<TTSTextItem> list) {
            this.texts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<TTSTextItem>> getTexts() {
            return this.texts;
        }
    }

    /* loaded from: classes3.dex */
    public static class Player {
        @Required
        private String name;
        private Optional<String> introduction = Optional.empty();
        private Optional<Image> photo = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public Player() {
        }

        public Player(String str) {
            this.name = str;
        }

        @Required
        public Player setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public Player setIntroduction(String str) {
            this.introduction = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIntroduction() {
            return this.introduction;
        }

        public Player setPhoto(Image image) {
            this.photo = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getPhoto() {
            return this.photo;
        }

        public Player setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "PlayerRecord", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class PlayerRecord implements InstructionPayload {
        @Required
        private List<Common.PropItem> data;
        @Required
        private Player player;
        @Required
        private Image skill_icon;
        private Optional<MatchSchedule> recent_match = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public PlayerRecord() {
        }

        public PlayerRecord(Player player, List<Common.PropItem> list, Image image) {
            this.player = player;
            this.data = list;
            this.skill_icon = image;
        }

        @Required
        public PlayerRecord setPlayer(Player player) {
            this.player = player;
            return this;
        }

        @Required
        public Player getPlayer() {
            return this.player;
        }

        @Required
        public PlayerRecord setData(List<Common.PropItem> list) {
            this.data = list;
            return this;
        }

        @Required
        public List<Common.PropItem> getData() {
            return this.data;
        }

        @Required
        public PlayerRecord setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public PlayerRecord setRecentMatch(MatchSchedule matchSchedule) {
            this.recent_match = Optional.ofNullable(matchSchedule);
            return this;
        }

        public Optional<MatchSchedule> getRecentMatch() {
            return this.recent_match;
        }

        public PlayerRecord setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public PlayerRecord setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class PredictRate {
        @Required
        private double away_win;
        private Optional<Double> draw = Optional.empty();
        @Required
        private double home_win;

        public PredictRate() {
        }

        public PredictRate(double d, double d2) {
            this.home_win = d;
            this.away_win = d2;
        }

        @Required
        public PredictRate setHomeWin(double d) {
            this.home_win = d;
            return this;
        }

        @Required
        public double getHomeWin() {
            return this.home_win;
        }

        @Required
        public PredictRate setAwayWin(double d) {
            this.away_win = d;
            return this;
        }

        @Required
        public double getAwayWin() {
            return this.away_win;
        }

        public PredictRate setDraw(double d) {
            this.draw = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getDraw() {
            return this.draw;
        }
    }

    /* loaded from: classes3.dex */
    public static class Pronunciation {
        @Required
        private List<String> text;
        private Optional<String> url = Optional.empty();

        public Pronunciation() {
        }

        public Pronunciation(List<String> list) {
            this.text = list;
        }

        @Required
        public Pronunciation setText(List<String> list) {
            this.text = list;
            return this;
        }

        @Required
        public List<String> getText() {
            return this.text;
        }

        public Pronunciation setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }
    }

    @NamespaceName(name = "Qabot", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Qabot implements InstructionPayload {
        @Required
        private Title title;
        @Required
        private QabotType type;
        private Optional<VideoSnapshot> videoSnapshot = Optional.empty();
        private Optional<ClickAlbum> images = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<QabotParagraphs> paragraphs = Optional.empty();
        private Optional<List<AbstractItem>> abstracts = Optional.empty();
        private Optional<String> text = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<QabotImageType> image_type = Optional.empty();

        public Qabot() {
        }

        public Qabot(QabotType qabotType, Title title) {
            this.type = qabotType;
            this.title = title;
        }

        @Required
        public Qabot setType(QabotType qabotType) {
            this.type = qabotType;
            return this;
        }

        @Required
        public QabotType getType() {
            return this.type;
        }

        @Required
        public Qabot setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        public Qabot setVideoSnapshot(VideoSnapshot videoSnapshot) {
            this.videoSnapshot = Optional.ofNullable(videoSnapshot);
            return this;
        }

        public Optional<VideoSnapshot> getVideoSnapshot() {
            return this.videoSnapshot;
        }

        public Qabot setImages(ClickAlbum clickAlbum) {
            this.images = Optional.ofNullable(clickAlbum);
            return this;
        }

        public Optional<ClickAlbum> getImages() {
            return this.images;
        }

        public Qabot setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Qabot setParagraphs(QabotParagraphs qabotParagraphs) {
            this.paragraphs = Optional.ofNullable(qabotParagraphs);
            return this;
        }

        public Optional<QabotParagraphs> getParagraphs() {
            return this.paragraphs;
        }

        public Qabot setAbstracts(List<AbstractItem> list) {
            this.abstracts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AbstractItem>> getAbstracts() {
            return this.abstracts;
        }

        public Qabot setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }

        public Qabot setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public Qabot setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Qabot setImageType(QabotImageType qabotImageType) {
            this.image_type = Optional.ofNullable(qabotImageType);
            return this;
        }

        public Optional<QabotImageType> getImageType() {
            return this.image_type;
        }
    }

    @NamespaceName(name = "QabotLists", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class QabotLists implements InstructionPayload {
        @Required
        private List<ListsItem> items;
        @Required
        private Title title;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Integer> max_row_num = Optional.empty();

        public QabotLists() {
        }

        public QabotLists(Title title, List<ListsItem> list) {
            this.title = title;
            this.items = list;
        }

        @Required
        public QabotLists setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public QabotLists setItems(List<ListsItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<ListsItem> getItems() {
            return this.items;
        }

        public QabotLists setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public QabotLists setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public QabotLists setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public QabotLists setMaxRowNum(int i) {
            this.max_row_num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMaxRowNum() {
            return this.max_row_num;
        }
    }

    /* loaded from: classes3.dex */
    public static class QabotParagraph {
        @Required
        private List<String> content;
        private Optional<Image> image = Optional.empty();
        @Required
        private Title title;

        public QabotParagraph() {
        }

        public QabotParagraph(Title title, List<String> list) {
            this.title = title;
            this.content = list;
        }

        @Required
        public QabotParagraph setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public QabotParagraph setContent(List<String> list) {
            this.content = list;
            return this;
        }

        @Required
        public List<String> getContent() {
            return this.content;
        }

        public QabotParagraph setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }
    }

    /* loaded from: classes3.dex */
    public static class QabotParagraphs {
        @Required
        private List<QabotParagraph> paragraphs;
        @Required
        private QabotParagraphType type;
        private Optional<String> head = Optional.empty();
        private Optional<String> tail = Optional.empty();

        public QabotParagraphs() {
        }

        public QabotParagraphs(QabotParagraphType qabotParagraphType, List<QabotParagraph> list) {
            this.type = qabotParagraphType;
            this.paragraphs = list;
        }

        @Required
        public QabotParagraphs setType(QabotParagraphType qabotParagraphType) {
            this.type = qabotParagraphType;
            return this;
        }

        @Required
        public QabotParagraphType getType() {
            return this.type;
        }

        @Required
        public QabotParagraphs setParagraphs(List<QabotParagraph> list) {
            this.paragraphs = list;
            return this;
        }

        @Required
        public List<QabotParagraph> getParagraphs() {
            return this.paragraphs;
        }

        public QabotParagraphs setHead(String str) {
            this.head = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHead() {
            return this.head;
        }

        public QabotParagraphs setTail(String str) {
            this.tail = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTail() {
            return this.tail;
        }
    }

    @NamespaceName(name = "Query", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Query implements InstructionPayload {
        @Required
        private String text;

        public Query() {
        }

        public Query(String str) {
            this.text = str;
        }

        @Required
        public Query setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    /* loaded from: classes3.dex */
    public static class QuickAppLaunchInfo {
        @Required
        private String pkg_name;
        @Required
        private int size;
        @Required
        private AppVersion version;
        private Optional<ObjectNode> params = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<Task> task = Optional.empty();

        public QuickAppLaunchInfo() {
        }

        public QuickAppLaunchInfo(String str, int i, AppVersion appVersion) {
            this.pkg_name = str;
            this.size = i;
            this.version = appVersion;
        }

        @Required
        public QuickAppLaunchInfo setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public QuickAppLaunchInfo setSize(int i) {
            this.size = i;
            return this;
        }

        @Required
        public int getSize() {
            return this.size;
        }

        @Required
        public QuickAppLaunchInfo setVersion(AppVersion appVersion) {
            this.version = appVersion;
            return this;
        }

        @Required
        public AppVersion getVersion() {
            return this.version;
        }

        public QuickAppLaunchInfo setParams(ObjectNode objectNode) {
            this.params = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParams() {
            return this.params;
        }

        public QuickAppLaunchInfo setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public QuickAppLaunchInfo setTask(Task task) {
            this.task = Optional.ofNullable(task);
            return this;
        }

        public Optional<Task> getTask() {
            return this.task;
        }
    }

    @NamespaceName(name = "ResolveWords", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ResolveWords implements EventPayload {
        @Required
        private String words;
        private Optional<String> id = Optional.empty();
        private Optional<ResolveWordsType> type = Optional.empty();
        private Optional<String> property = Optional.empty();

        public ResolveWords() {
        }

        public ResolveWords(String str) {
            this.words = str;
        }

        @Required
        public ResolveWords setWords(String str) {
            this.words = str;
            return this;
        }

        @Required
        public String getWords() {
            return this.words;
        }

        public ResolveWords setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public ResolveWords setType(ResolveWordsType resolveWordsType) {
            this.type = Optional.ofNullable(resolveWordsType);
            return this;
        }

        public Optional<ResolveWordsType> getType() {
            return this.type;
        }

        public ResolveWords setProperty(String str) {
            this.property = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getProperty() {
            return this.property;
        }
    }

    @NamespaceName(name = "RestrictDriving", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class RestrictDriving implements InstructionPayload {
        @Required
        private String description;
        @Required
        private List<Integer> numbers;
        private Optional<String> date = Optional.empty();
        private Optional<Common.Weekday> week_day = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();

        public RestrictDriving() {
        }

        public RestrictDriving(String str, List<Integer> list) {
            this.description = str;
            this.numbers = list;
        }

        @Required
        public RestrictDriving setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        @Required
        public RestrictDriving setNumbers(List<Integer> list) {
            this.numbers = list;
            return this;
        }

        @Required
        public List<Integer> getNumbers() {
            return this.numbers;
        }

        public RestrictDriving setDate(String str) {
            this.date = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDate() {
            return this.date;
        }

        public RestrictDriving setWeekDay(Common.Weekday weekday) {
            this.week_day = Optional.ofNullable(weekday);
            return this;
        }

        public Optional<Common.Weekday> getWeekDay() {
            return this.week_day;
        }

        public RestrictDriving setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public RestrictDriving setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "RichPicture", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class RichPicture implements InstructionPayload {
        private Optional<Image> image = Optional.empty();
        private Optional<AudioPlayer.Stream> bg_stream = Optional.empty();

        public RichPicture setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }

        public RichPicture setBgStream(AudioPlayer.Stream stream) {
            this.bg_stream = Optional.ofNullable(stream);
            return this;
        }

        public Optional<AudioPlayer.Stream> getBgStream() {
            return this.bg_stream;
        }
    }

    /* loaded from: classes3.dex */
    public static class RichText {
        @Required
        private String text;
        private Optional<String> color = Optional.empty();
        private Optional<String> bg_color = Optional.empty();
        private Optional<String> html = Optional.empty();

        public RichText() {
        }

        public RichText(String str) {
            this.text = str;
        }

        @Required
        public RichText setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public RichText setColor(String str) {
            this.color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getColor() {
            return this.color;
        }

        public RichText setBgColor(String str) {
            this.bg_color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBgColor() {
            return this.bg_color;
        }

        public RichText setHtml(String str) {
            this.html = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHtml() {
            return this.html;
        }
    }

    /* loaded from: classes3.dex */
    public static class SceneGroup {
        @Required
        private ArrayNode instructions;
        @Required
        private String name;

        public SceneGroup() {
        }

        public SceneGroup(ArrayNode arrayNode, String str) {
            this.instructions = arrayNode;
            this.name = str;
        }

        @Required
        public SceneGroup setInstructions(ArrayNode arrayNode) {
            this.instructions = arrayNode;
            return this;
        }

        @Required
        public ArrayNode getInstructions() {
            return this.instructions;
        }

        @Required
        public SceneGroup setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }
    }

    @NamespaceName(name = "Scenes", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Scenes implements InstructionPayload {
        @Required
        private List<SceneGroup> groups;
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();

        public Scenes() {
        }

        public Scenes(List<SceneGroup> list) {
            this.groups = list;
        }

        @Required
        public Scenes setGroups(List<SceneGroup> list) {
            this.groups = list;
            return this;
        }

        @Required
        public List<SceneGroup> getGroups() {
            return this.groups;
        }

        public Scenes setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Scenes setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "SetDisplayProperty", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SetDisplayProperty implements InstructionPayload {
        private Optional<Integer> duration_in_ms = Optional.empty();
        private Optional<Integer> wakeup_ball_duration = Optional.empty();

        public SetDisplayProperty setDurationInMs(int i) {
            this.duration_in_ms = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDurationInMs() {
            return this.duration_in_ms;
        }

        public SetDisplayProperty setWakeupBallDuration(int i) {
            this.wakeup_ball_duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getWakeupBallDuration() {
            return this.wakeup_ball_duration;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShopInfo {
        @Required
        private String name;
        private Optional<String> location = Optional.empty();
        private Optional<String> distance = Optional.empty();
        private Optional<Float> score = Optional.empty();
        private Optional<String> average_price = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<String> coupon = Optional.empty();
        private Optional<Boolean> open = Optional.empty();
        private Optional<Image> logo = Optional.empty();

        public ShopInfo() {
        }

        public ShopInfo(String str) {
            this.name = str;
        }

        @Required
        public ShopInfo setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public ShopInfo setLocation(String str) {
            this.location = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLocation() {
            return this.location;
        }

        public ShopInfo setDistance(String str) {
            this.distance = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDistance() {
            return this.distance;
        }

        public ShopInfo setScore(float f) {
            this.score = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getScore() {
            return this.score;
        }

        public ShopInfo setAveragePrice(String str) {
            this.average_price = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAveragePrice() {
            return this.average_price;
        }

        public ShopInfo setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public ShopInfo setCoupon(String str) {
            this.coupon = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCoupon() {
            return this.coupon;
        }

        public ShopInfo setOpen(boolean z) {
            this.open = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isOpen() {
            return this.open;
        }

        public ShopInfo setLogo(Image image) {
            this.logo = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getLogo() {
            return this.logo;
        }
    }

    @NamespaceName(name = "ShopRecommendation", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ShopRecommendation implements InstructionPayload {
        @Required
        private List<ShopRecommendationItem> items;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public ShopRecommendation() {
        }

        public ShopRecommendation(List<ShopRecommendationItem> list) {
            this.items = list;
        }

        @Required
        public ShopRecommendation setItems(List<ShopRecommendationItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<ShopRecommendationItem> getItems() {
            return this.items;
        }

        public ShopRecommendation setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public ShopRecommendation setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public ShopRecommendation setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShopRecommendationItem {
        @Required
        private ShopInfo shop_info;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<Button> button = Optional.empty();

        public ShopRecommendationItem() {
        }

        public ShopRecommendationItem(ShopInfo shopInfo) {
            this.shop_info = shopInfo;
        }

        @Required
        public ShopRecommendationItem setShopInfo(ShopInfo shopInfo) {
            this.shop_info = shopInfo;
            return this;
        }

        @Required
        public ShopInfo getShopInfo() {
            return this.shop_info;
        }

        public ShopRecommendationItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public ShopRecommendationItem setButton(Button button) {
            this.button = Optional.ofNullable(button);
            return this;
        }

        public Optional<Button> getButton() {
            return this.button;
        }
    }

    @NamespaceName(name = "ShortcutNewUserGuide", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ShortcutNewUserGuide implements InstructionPayload {
        @Required
        private String exp_id;
        @Required
        private String message;
        @Required
        private String skill_id;

        public ShortcutNewUserGuide() {
        }

        public ShortcutNewUserGuide(String str, String str2, String str3) {
            this.skill_id = str;
            this.message = str2;
            this.exp_id = str3;
        }

        @Required
        public ShortcutNewUserGuide setSkillId(String str) {
            this.skill_id = str;
            return this;
        }

        @Required
        public String getSkillId() {
            return this.skill_id;
        }

        @Required
        public ShortcutNewUserGuide setMessage(String str) {
            this.message = str;
            return this;
        }

        @Required
        public String getMessage() {
            return this.message;
        }

        @Required
        public ShortcutNewUserGuide setExpId(String str) {
            this.exp_id = str;
            return this;
        }

        @Required
        public String getExpId() {
            return this.exp_id;
        }
    }

    @NamespaceName(name = "ShowOneCard", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ShowOneCard implements InstructionPayload {
        @Required
        private ShowOneCardType display_type;

        public ShowOneCard() {
        }

        public ShowOneCard(ShowOneCardType showOneCardType) {
            this.display_type = showOneCardType;
        }

        @Required
        public ShowOneCard setDisplayType(ShowOneCardType showOneCardType) {
            this.display_type = showOneCardType;
            return this;
        }

        @Required
        public ShowOneCardType getDisplayType() {
            return this.display_type;
        }
    }

    @NamespaceName(name = "SingleButton", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SingleButton implements InstructionPayload {
        @Required
        private Button button;
        private Optional<Image> skill_icon = Optional.empty();

        public SingleButton() {
        }

        public SingleButton(Button button) {
            this.button = button;
        }

        @Required
        public SingleButton setButton(Button button) {
            this.button = button;
            return this;
        }

        @Required
        public Button getButton() {
            return this.button;
        }

        public SingleButton setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class SmartHotel {
        private Optional<String> name = Optional.empty();
        private Optional<Image> image = Optional.empty();
        @Deprecated
        private Optional<SmartHotelSummary> summary = Optional.empty();
        private Optional<List<SmartHotelFacility>> facilities = Optional.empty();
        @Deprecated
        private Optional<SmartHotelCategory> category = Optional.empty();
        private Optional<List<SmartHotelBaseInfo>> base_infos = Optional.empty();

        public SmartHotel setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public SmartHotel setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }

        @Deprecated
        public SmartHotel setSummary(SmartHotelSummary smartHotelSummary) {
            this.summary = Optional.ofNullable(smartHotelSummary);
            return this;
        }

        @Deprecated
        public Optional<SmartHotelSummary> getSummary() {
            return this.summary;
        }

        public SmartHotel setFacilities(List<SmartHotelFacility> list) {
            this.facilities = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SmartHotelFacility>> getFacilities() {
            return this.facilities;
        }

        @Deprecated
        public SmartHotel setCategory(SmartHotelCategory smartHotelCategory) {
            this.category = Optional.ofNullable(smartHotelCategory);
            return this;
        }

        @Deprecated
        public Optional<SmartHotelCategory> getCategory() {
            return this.category;
        }

        public SmartHotel setBaseInfos(List<SmartHotelBaseInfo> list) {
            this.base_infos = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SmartHotelBaseInfo>> getBaseInfos() {
            return this.base_infos;
        }
    }

    /* loaded from: classes3.dex */
    public static class SmartHotelBaseInfo {
        private Optional<Image> icon = Optional.empty();
        @Required
        private String text;

        public SmartHotelBaseInfo() {
        }

        public SmartHotelBaseInfo(String str) {
            this.text = str;
        }

        @Required
        public SmartHotelBaseInfo setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public SmartHotelBaseInfo setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class SmartHotelFacility {
        @Required
        private String name;
        private Optional<Image> image = Optional.empty();
        private Optional<FacilityLocation> location = Optional.empty();
        private Optional<FacilityTime> time = Optional.empty();
        private Optional<String> desc = Optional.empty();

        public SmartHotelFacility() {
        }

        public SmartHotelFacility(String str) {
            this.name = str;
        }

        @Required
        public SmartHotelFacility setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public SmartHotelFacility setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }

        public SmartHotelFacility setLocation(FacilityLocation facilityLocation) {
            this.location = Optional.ofNullable(facilityLocation);
            return this;
        }

        public Optional<FacilityLocation> getLocation() {
            return this.location;
        }

        public SmartHotelFacility setTime(FacilityTime facilityTime) {
            this.time = Optional.ofNullable(facilityTime);
            return this;
        }

        public Optional<FacilityTime> getTime() {
            return this.time;
        }

        public SmartHotelFacility setDesc(String str) {
            this.desc = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDesc() {
            return this.desc;
        }
    }

    /* loaded from: classes3.dex */
    public static class SmartHotelSummary {
        @Required
        private String content;
        private Optional<Image> icon = Optional.empty();
        private Optional<SmartHotelWifi> wifi = Optional.empty();
        private Optional<List<SmartHotelBaseInfo>> base_infos = Optional.empty();

        public SmartHotelSummary() {
        }

        public SmartHotelSummary(String str) {
            this.content = str;
        }

        @Required
        public SmartHotelSummary setContent(String str) {
            this.content = str;
            return this;
        }

        @Required
        public String getContent() {
            return this.content;
        }

        public SmartHotelSummary setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public SmartHotelSummary setWifi(SmartHotelWifi smartHotelWifi) {
            this.wifi = Optional.ofNullable(smartHotelWifi);
            return this;
        }

        public Optional<SmartHotelWifi> getWifi() {
            return this.wifi;
        }

        public SmartHotelSummary setBaseInfos(List<SmartHotelBaseInfo> list) {
            this.base_infos = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SmartHotelBaseInfo>> getBaseInfos() {
            return this.base_infos;
        }
    }

    /* loaded from: classes3.dex */
    public static class SmartHotelWifi {
        private Optional<Image> icon = Optional.empty();
        @Required
        private String wifi;

        public SmartHotelWifi() {
        }

        public SmartHotelWifi(String str) {
            this.wifi = str;
        }

        @Required
        public SmartHotelWifi setWifi(String str) {
            this.wifi = str;
            return this;
        }

        @Required
        public String getWifi() {
            return this.wifi;
        }

        public SmartHotelWifi setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }
    }

    @NamespaceName(name = "SmartHotels", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SmartHotels implements InstructionPayload {
        @Required
        private List<SmartHotel> hotels;

        public SmartHotels() {
        }

        public SmartHotels(List<SmartHotel> list) {
            this.hotels = list;
        }

        @Required
        public SmartHotels setHotels(List<SmartHotel> list) {
            this.hotels = list;
            return this;
        }

        @Required
        public List<SmartHotel> getHotels() {
            return this.hotels;
        }
    }

    @NamespaceName(name = "SportMatch", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SportMatch implements InstructionPayload {
        @Required
        private Image cp;
        @Required
        private String name;
        private Optional<List<SportMatchSchedule>> schedule = Optional.empty();
        private Optional<List<SportMatchMedalRank>> medal_ranks = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public SportMatch() {
        }

        public SportMatch(String str, Image image) {
            this.name = str;
            this.cp = image;
        }

        @Required
        public SportMatch setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public SportMatch setCp(Image image) {
            this.cp = image;
            return this;
        }

        @Required
        public Image getCp() {
            return this.cp;
        }

        public SportMatch setSchedule(List<SportMatchSchedule> list) {
            this.schedule = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SportMatchSchedule>> getSchedule() {
            return this.schedule;
        }

        public SportMatch setMedalRanks(List<SportMatchMedalRank> list) {
            this.medal_ranks = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SportMatchMedalRank>> getMedalRanks() {
            return this.medal_ranks;
        }

        public SportMatch setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class SportMatchMedalRank {
        @Required
        private int bronze;
        @Required
        private String country;
        @Required
        private int gold;
        @Required
        private int rank;
        @Required
        private int silver;
        @Required
        private int total;

        public SportMatchMedalRank() {
        }

        public SportMatchMedalRank(String str, int i, int i2, int i3, int i4, int i5) {
            this.country = str;
            this.rank = i;
            this.total = i2;
            this.gold = i3;
            this.silver = i4;
            this.bronze = i5;
        }

        @Required
        public SportMatchMedalRank setCountry(String str) {
            this.country = str;
            return this;
        }

        @Required
        public String getCountry() {
            return this.country;
        }

        @Required
        public SportMatchMedalRank setRank(int i) {
            this.rank = i;
            return this;
        }

        @Required
        public int getRank() {
            return this.rank;
        }

        @Required
        public SportMatchMedalRank setTotal(int i) {
            this.total = i;
            return this;
        }

        @Required
        public int getTotal() {
            return this.total;
        }

        @Required
        public SportMatchMedalRank setGold(int i) {
            this.gold = i;
            return this;
        }

        @Required
        public int getGold() {
            return this.gold;
        }

        @Required
        public SportMatchMedalRank setSilver(int i) {
            this.silver = i;
            return this;
        }

        @Required
        public int getSilver() {
            return this.silver;
        }

        @Required
        public SportMatchMedalRank setBronze(int i) {
            this.bronze = i;
            return this;
        }

        @Required
        public int getBronze() {
            return this.bronze;
        }
    }

    /* loaded from: classes3.dex */
    public static class SportMatchMoment {
        @Required
        private String event;
        @Required
        private String start_time;
        @Required
        private SportMatchStatus status;
        @Required
        private String sub_event;
        private Optional<String> sub_event_stage = Optional.empty();
        private Optional<String> participants = Optional.empty();
        private Optional<String> group = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public SportMatchMoment() {
        }

        public SportMatchMoment(String str, SportMatchStatus sportMatchStatus, String str2, String str3) {
            this.start_time = str;
            this.status = sportMatchStatus;
            this.event = str2;
            this.sub_event = str3;
        }

        @Required
        public SportMatchMoment setStartTime(String str) {
            this.start_time = str;
            return this;
        }

        @Required
        public String getStartTime() {
            return this.start_time;
        }

        @Required
        public SportMatchMoment setStatus(SportMatchStatus sportMatchStatus) {
            this.status = sportMatchStatus;
            return this;
        }

        @Required
        public SportMatchStatus getStatus() {
            return this.status;
        }

        @Required
        public SportMatchMoment setEvent(String str) {
            this.event = str;
            return this;
        }

        @Required
        public String getEvent() {
            return this.event;
        }

        @Required
        public SportMatchMoment setSubEvent(String str) {
            this.sub_event = str;
            return this;
        }

        @Required
        public String getSubEvent() {
            return this.sub_event;
        }

        public SportMatchMoment setSubEventStage(String str) {
            this.sub_event_stage = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSubEventStage() {
            return this.sub_event_stage;
        }

        public SportMatchMoment setParticipants(String str) {
            this.participants = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getParticipants() {
            return this.participants;
        }

        public SportMatchMoment setGroup(String str) {
            this.group = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getGroup() {
            return this.group;
        }

        public SportMatchMoment setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class SportMatchSchedule {
        @Required
        private String date;
        @Required
        private List<SportMatchMoment> moments;

        public SportMatchSchedule() {
        }

        public SportMatchSchedule(String str, List<SportMatchMoment> list) {
            this.date = str;
            this.moments = list;
        }

        @Required
        public SportMatchSchedule setDate(String str) {
            this.date = str;
            return this;
        }

        @Required
        public String getDate() {
            return this.date;
        }

        @Required
        public SportMatchSchedule setMoments(List<SportMatchMoment> list) {
            this.moments = list;
            return this;
        }

        @Required
        public List<SportMatchMoment> getMoments() {
            return this.moments;
        }
    }

    /* loaded from: classes3.dex */
    public static class StationItem {
        @Required
        private String ai_album_id;
        @Required
        private String album_name;
        @Required
        private String chinese_name;
        @Required
        private int episode_num;
        @Required
        private String id;
        @Required
        private String origin;
        @Required
        private boolean payment;
        private Optional<Image> album_cover = Optional.empty();
        private Optional<String> category = Optional.empty();

        public StationItem() {
        }

        public StationItem(String str, String str2, String str3, String str4, String str5, int i, boolean z) {
            this.id = str;
            this.ai_album_id = str2;
            this.album_name = str3;
            this.origin = str4;
            this.chinese_name = str5;
            this.episode_num = i;
            this.payment = z;
        }

        @Required
        public StationItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public StationItem setAiAlbumId(String str) {
            this.ai_album_id = str;
            return this;
        }

        @Required
        public String getAiAlbumId() {
            return this.ai_album_id;
        }

        @Required
        public StationItem setAlbumName(String str) {
            this.album_name = str;
            return this;
        }

        @Required
        public String getAlbumName() {
            return this.album_name;
        }

        @Required
        public StationItem setOrigin(String str) {
            this.origin = str;
            return this;
        }

        @Required
        public String getOrigin() {
            return this.origin;
        }

        @Required
        public StationItem setChineseName(String str) {
            this.chinese_name = str;
            return this;
        }

        @Required
        public String getChineseName() {
            return this.chinese_name;
        }

        @Required
        public StationItem setEpisodeNum(int i) {
            this.episode_num = i;
            return this;
        }

        @Required
        public int getEpisodeNum() {
            return this.episode_num;
        }

        @Required
        public StationItem setPayment(boolean z) {
            this.payment = z;
            return this;
        }

        @Required
        public boolean isPayment() {
            return this.payment;
        }

        public StationItem setAlbumCover(Image image) {
            this.album_cover = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getAlbumCover() {
            return this.album_cover;
        }

        public StationItem setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }
    }

    @NamespaceName(name = "Stations", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Stations implements InstructionPayload {
        @Required
        private List<StationItem> items;
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<String> title = Optional.empty();

        public Stations() {
        }

        public Stations(List<StationItem> list) {
            this.items = list;
        }

        @Required
        public Stations setItems(List<StationItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<StationItem> getItems() {
            return this.items;
        }

        public Stations setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public Stations setTitle(String str) {
            this.title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTitle() {
            return this.title;
        }
    }

    @NamespaceName(name = "Stock", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Stock implements InstructionPayload {
        @Required
        private List<StockDetail> stocks;
        private Optional<String> tip = Optional.empty();
        private Optional<String> h5_url = Optional.empty();
        private Optional<Launcher> intent = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();

        public Stock() {
        }

        public Stock(List<StockDetail> list) {
            this.stocks = list;
        }

        @Required
        public Stock setStocks(List<StockDetail> list) {
            this.stocks = list;
            return this;
        }

        @Required
        public List<StockDetail> getStocks() {
            return this.stocks;
        }

        public Stock setTip(String str) {
            this.tip = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTip() {
            return this.tip;
        }

        public Stock setH5Url(String str) {
            this.h5_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getH5Url() {
            return this.h5_url;
        }

        public Stock setIntent(Launcher launcher) {
            this.intent = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getIntent() {
            return this.intent;
        }

        public Stock setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class StockDetail {
        private Optional<String> code = Optional.empty();
        private Optional<String> market = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<Double> highest_price = Optional.empty();
        private Optional<Double> lowest_price = Optional.empty();
        private Optional<Double> close_price = Optional.empty();
        private Optional<Double> cur_price = Optional.empty();
        private Optional<Double> change_amount = Optional.empty();
        private Optional<Double> change_ratio = Optional.empty();

        public StockDetail setCode(String str) {
            this.code = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCode() {
            return this.code;
        }

        public StockDetail setMarket(String str) {
            this.market = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMarket() {
            return this.market;
        }

        public StockDetail setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public StockDetail setHighestPrice(double d) {
            this.highest_price = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getHighestPrice() {
            return this.highest_price;
        }

        public StockDetail setLowestPrice(double d) {
            this.lowest_price = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getLowestPrice() {
            return this.lowest_price;
        }

        public StockDetail setClosePrice(double d) {
            this.close_price = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getClosePrice() {
            return this.close_price;
        }

        public StockDetail setCurPrice(double d) {
            this.cur_price = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getCurPrice() {
            return this.cur_price;
        }

        public StockDetail setChangeAmount(double d) {
            this.change_amount = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getChangeAmount() {
            return this.change_amount;
        }

        public StockDetail setChangeRatio(double d) {
            this.change_ratio = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getChangeRatio() {
            return this.change_ratio;
        }
    }

    /* loaded from: classes3.dex */
    public static class SwitchItem {
        @Required
        private Title title;
        private Optional<String> status = Optional.empty();
        private Optional<LocalTarget> target = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<String> number_title = Optional.empty();

        public SwitchItem() {
        }

        public SwitchItem(Title title) {
            this.title = title;
        }

        @Required
        public SwitchItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        public SwitchItem setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public SwitchItem setTarget(LocalTarget localTarget) {
            this.target = Optional.ofNullable(localTarget);
            return this;
        }

        public Optional<LocalTarget> getTarget() {
            return this.target;
        }

        public SwitchItem setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public SwitchItem setNumberTitle(String str) {
            this.number_title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNumberTitle() {
            return this.number_title;
        }
    }

    @NamespaceName(name = "SwitchPanel", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SwitchPanel implements InstructionPayload {
        @Required
        private Image skill_icon;
        @Required
        private Title title;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<String> status = Optional.empty();
        private Optional<SwitchPanelType> type = Optional.empty();
        private Optional<LocalTarget> target = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<String> number_title = Optional.empty();

        public SwitchPanel() {
        }

        public SwitchPanel(Title title, Image image) {
            this.title = title;
            this.skill_icon = image;
        }

        @Required
        public SwitchPanel setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public SwitchPanel setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public SwitchPanel setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public SwitchPanel setStatus(String str) {
            this.status = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStatus() {
            return this.status;
        }

        public SwitchPanel setType(SwitchPanelType switchPanelType) {
            this.type = Optional.ofNullable(switchPanelType);
            return this;
        }

        public Optional<SwitchPanelType> getType() {
            return this.type;
        }

        public SwitchPanel setTarget(LocalTarget localTarget) {
            this.target = Optional.ofNullable(localTarget);
            return this;
        }

        public Optional<LocalTarget> getTarget() {
            return this.target;
        }

        public SwitchPanel setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public SwitchPanel setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public SwitchPanel setNumberTitle(String str) {
            this.number_title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNumberTitle() {
            return this.number_title;
        }
    }

    @NamespaceName(name = "SwitchPanelList", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class SwitchPanelList implements InstructionPayload {
        @Required
        private Image skill_icon;
        @Required
        @Deprecated
        private List<SwitchItem> titles;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<SwitchPanelType> type = Optional.empty();
        private Optional<List<SwitchItem>> items = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public SwitchPanelList() {
        }

        public SwitchPanelList(List<SwitchItem> list, Image image) {
            this.titles = list;
            this.skill_icon = image;
        }

        @Required
        @Deprecated
        public SwitchPanelList setTitles(List<SwitchItem> list) {
            this.titles = list;
            return this;
        }

        @Required
        @Deprecated
        public List<SwitchItem> getTitles() {
            return this.titles;
        }

        @Required
        public SwitchPanelList setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public SwitchPanelList setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public SwitchPanelList setType(SwitchPanelType switchPanelType) {
            this.type = Optional.ofNullable(switchPanelType);
            return this;
        }

        public Optional<SwitchPanelType> getType() {
            return this.type;
        }

        public SwitchPanelList setItems(List<SwitchItem> list) {
            this.items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SwitchItem>> getItems() {
            return this.items;
        }

        public SwitchPanelList setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public SwitchPanelList setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class SynonymCardV2 {
        private Optional<List<MachineTranslation>> translations = Optional.empty();
        private Optional<List<String>> synonym_words = Optional.empty();

        public SynonymCardV2 setTranslations(List<MachineTranslation> list) {
            this.translations = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<MachineTranslation>> getTranslations() {
            return this.translations;
        }

        public SynonymCardV2 setSynonymWords(List<String> list) {
            this.synonym_words = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getSynonymWords() {
            return this.synonym_words;
        }
    }

    /* loaded from: classes3.dex */
    public static class TTSTextItem {
        @Required
        private String text;
        @Required
        private Title title;

        public TTSTextItem() {
        }

        public TTSTextItem(Title title, String str) {
            this.title = title;
            this.text = str;
        }

        @Required
        public TTSTextItem setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public TTSTextItem setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    @NamespaceName(name = "Table", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Table implements InstructionPayload {
        @Required
        private Image skill_icon;
        @Required
        private List<TableData> values;
        private Optional<TableTitle> title = Optional.empty();
        private Optional<TableType> type = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public Table() {
        }

        public Table(List<TableData> list, Image image) {
            this.values = list;
            this.skill_icon = image;
        }

        public Table setTitle(TableTitle tableTitle) {
            this.title = Optional.ofNullable(tableTitle);
            return this;
        }

        public Optional<TableTitle> getTitle() {
            return this.title;
        }

        @Required
        public Table setValues(List<TableData> list) {
            this.values = list;
            return this;
        }

        @Required
        public List<TableData> getValues() {
            return this.values;
        }

        @Required
        public Table setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public Table setType(TableType tableType) {
            this.type = Optional.ofNullable(tableType);
            return this;
        }

        public Optional<TableType> getType() {
            return this.type;
        }

        public Table setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Table setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class TableAttribute {
        private Optional<Align> align = Optional.empty();

        public TableAttribute setAlign(Align align) {
            this.align = Optional.ofNullable(align);
            return this;
        }

        public Optional<Align> getAlign() {
            return this.align;
        }
    }

    /* loaded from: classes3.dex */
    public static class TableCell {
        private Optional<Image> image = Optional.empty();
        @Required
        private String text;

        public TableCell() {
        }

        public TableCell(String str) {
            this.text = str;
        }

        @Required
        public TableCell setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public TableCell setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }
    }

    /* loaded from: classes3.dex */
    public static class TableData {
        @Required
        private List<List<TableCell>> data;
        @Required
        private List<TableHeader> headers;

        public TableData() {
        }

        public TableData(List<TableHeader> list, List<List<TableCell>> list2) {
            this.headers = list;
            this.data = list2;
        }

        @Required
        public TableData setHeaders(List<TableHeader> list) {
            this.headers = list;
            return this;
        }

        @Required
        public List<TableHeader> getHeaders() {
            return this.headers;
        }

        @Required
        public TableData setData(List<List<TableCell>> list) {
            this.data = list;
            return this;
        }

        @Required
        public List<List<TableCell>> getData() {
            return this.data;
        }
    }

    /* loaded from: classes3.dex */
    public static class TableHeader {
        private Optional<TableAttribute> attributes = Optional.empty();
        @Required
        private String text;

        public TableHeader() {
        }

        public TableHeader(String str) {
            this.text = str;
        }

        @Required
        public TableHeader setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public TableHeader setAttributes(TableAttribute tableAttribute) {
            this.attributes = Optional.ofNullable(tableAttribute);
            return this;
        }

        public Optional<TableAttribute> getAttributes() {
            return this.attributes;
        }
    }

    /* loaded from: classes3.dex */
    public static class TableTitle {
        private Optional<Image> image = Optional.empty();
        @Required
        private Title text;

        public TableTitle() {
        }

        public TableTitle(Title title) {
            this.text = title;
        }

        @Required
        public TableTitle setText(Title title) {
            this.text = title;
            return this;
        }

        @Required
        public Title getText() {
            return this.text;
        }

        public TableTitle setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }
    }

    /* loaded from: classes3.dex */
    public static class Task {
        private Optional<Boolean> can_ui_back = Optional.empty();
        @Required
        private Image icon;
        @Required
        private String id;
        @Required
        private TaskLoadType load_type;
        @Required
        private String name;

        public Task() {
        }

        public Task(String str, Image image, String str2, TaskLoadType taskLoadType) {
            this.name = str;
            this.icon = image;
            this.id = str2;
            this.load_type = taskLoadType;
        }

        @Required
        public Task setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Task setIcon(Image image) {
            this.icon = image;
            return this;
        }

        @Required
        public Image getIcon() {
            return this.icon;
        }

        @Required
        public Task setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Task setLoadType(TaskLoadType taskLoadType) {
            this.load_type = taskLoadType;
            return this;
        }

        @Required
        public TaskLoadType getLoadType() {
            return this.load_type;
        }

        public Task setCanUiBack(boolean z) {
            this.can_ui_back = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCanUiBack() {
            return this.can_ui_back;
        }
    }

    /* loaded from: classes3.dex */
    public static class Team {
        @Required
        private String name;
        private Optional<Image> logo = Optional.empty();
        private Optional<Image> aux_image = Optional.empty();
        private Optional<Boolean> home = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public Team() {
        }

        public Team(String str) {
            this.name = str;
        }

        @Required
        public Team setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public Team setLogo(Image image) {
            this.logo = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getLogo() {
            return this.logo;
        }

        public Team setAuxImage(Image image) {
            this.aux_image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getAuxImage() {
            return this.aux_image;
        }

        public Team setHome(boolean z) {
            this.home = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isHome() {
            return this.home;
        }

        public Team setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "Time", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Time implements InstructionPayload {
        @Required
        private TimeType type;
        private Optional<String> date = Optional.empty();
        private Optional<String> festival = Optional.empty();
        private Optional<String> week = Optional.empty();
        private Optional<String> lunar = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<String> time = Optional.empty();
        private Optional<String> period = Optional.empty();
        private Optional<Image> icon = Optional.empty();
        private Optional<String> location = Optional.empty();
        private Optional<Image> skill_icon = Optional.empty();
        private Optional<String> three_level_intent_name = Optional.empty();
        private Optional<Nlp.TimeIntention> time_intention = Optional.empty();

        public Time() {
        }

        public Time(TimeType timeType) {
            this.type = timeType;
        }

        @Required
        public Time setType(TimeType timeType) {
            this.type = timeType;
            return this;
        }

        @Required
        public TimeType getType() {
            return this.type;
        }

        public Time setDate(String str) {
            this.date = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDate() {
            return this.date;
        }

        public Time setFestival(String str) {
            this.festival = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFestival() {
            return this.festival;
        }

        public Time setWeek(String str) {
            this.week = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWeek() {
            return this.week;
        }

        public Time setLunar(String str) {
            this.lunar = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLunar() {
            return this.lunar;
        }

        public Time setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Time setTime(String str) {
            this.time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTime() {
            return this.time;
        }

        public Time setPeriod(String str) {
            this.period = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPeriod() {
            return this.period;
        }

        public Time setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public Time setLocation(String str) {
            this.location = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLocation() {
            return this.location;
        }

        public Time setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }

        public Time setThreeLevelIntentName(String str) {
            this.three_level_intent_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getThreeLevelIntentName() {
            return this.three_level_intent_name;
        }

        public Time setTimeIntention(Nlp.TimeIntention timeIntention) {
            this.time_intention = Optional.ofNullable(timeIntention);
            return this;
        }

        public Optional<Nlp.TimeIntention> getTimeIntention() {
            return this.time_intention;
        }
    }

    /* loaded from: classes3.dex */
    public static class Title {
        private Optional<Launcher> launcher = Optional.empty();
        @Required
        private String main_title;
        @Required
        private String sub_title;

        public Title() {
        }

        public Title(String str, String str2) {
            this.main_title = str;
            this.sub_title = str2;
        }

        @Required
        public Title setMainTitle(String str) {
            this.main_title = str;
            return this;
        }

        @Required
        public String getMainTitle() {
            return this.main_title;
        }

        @Required
        public Title setSubTitle(String str) {
            this.sub_title = str;
            return this;
        }

        @Required
        public String getSubTitle() {
            return this.sub_title;
        }

        public Title setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "ToDo", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class ToDo implements InstructionPayload {
        @Required
        private ToDoContent content;
        private Optional<Launcher> launcher = Optional.empty();
        @Required
        private Image skill_icon;

        public ToDo() {
        }

        public ToDo(ToDoContent toDoContent, Image image) {
            this.content = toDoContent;
            this.skill_icon = image;
        }

        @Required
        public ToDo setContent(ToDoContent toDoContent) {
            this.content = toDoContent;
            return this;
        }

        @Required
        public ToDoContent getContent() {
            return this.content;
        }

        @Required
        public ToDo setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public ToDo setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class ToDoContent {
        @Required
        private String time;
        @Required
        private String todo;

        public ToDoContent() {
        }

        public ToDoContent(String str, String str2) {
            this.todo = str;
            this.time = str2;
        }

        @Required
        public ToDoContent setTodo(String str) {
            this.todo = str;
            return this;
        }

        @Required
        public String getTodo() {
            return this.todo;
        }

        @Required
        public ToDoContent setTime(String str) {
            this.time = str;
            return this;
        }

        @Required
        public String getTime() {
            return this.time;
        }
    }

    @NamespaceName(name = "Toast", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Toast implements InstructionPayload {
        @Required
        private String text;
        private Optional<String> query = Optional.empty();
        private Optional<Boolean> training = Optional.empty();
        private Optional<Image> image = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<ToastPosition> position = Optional.empty();
        private Optional<EmojiConfig> emoji = Optional.empty();
        private Optional<AnimationConfig> animation = Optional.empty();
        private Optional<Boolean> show_audio = Optional.empty();

        public Toast() {
        }

        public Toast(String str) {
            this.text = str;
        }

        @Required
        public Toast setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public Toast setQuery(String str) {
            this.query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQuery() {
            return this.query;
        }

        public Toast setTraining(boolean z) {
            this.training = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isTraining() {
            return this.training;
        }

        public Toast setImage(Image image) {
            this.image = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getImage() {
            return this.image;
        }

        public Toast setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Toast setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public Toast setPosition(ToastPosition toastPosition) {
            this.position = Optional.ofNullable(toastPosition);
            return this;
        }

        public Optional<ToastPosition> getPosition() {
            return this.position;
        }

        public Toast setEmoji(EmojiConfig emojiConfig) {
            this.emoji = Optional.ofNullable(emojiConfig);
            return this;
        }

        public Optional<EmojiConfig> getEmoji() {
            return this.emoji;
        }

        public Toast setAnimation(AnimationConfig animationConfig) {
            this.animation = Optional.ofNullable(animationConfig);
            return this;
        }

        public Optional<AnimationConfig> getAnimation() {
            return this.animation;
        }

        public Toast setShowAudio(boolean z) {
            this.show_audio = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShowAudio() {
            return this.show_audio;
        }
    }

    @NamespaceName(name = "TouchBar", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class TouchBar implements InstructionPayload {
        @Required
        private Button button;
        private Optional<Image> skill_icon = Optional.empty();
        @Required
        private Title title;

        public TouchBar() {
        }

        public TouchBar(Title title, Button button) {
            this.title = title;
            this.button = button;
        }

        @Required
        public TouchBar setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public TouchBar setButton(Button button) {
            this.button = button;
            return this;
        }

        @Required
        public Button getButton() {
            return this.button;
        }

        public TouchBar setSkillIcon(Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "Translation", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Translation implements InstructionPayload {
        @Required
        private String dest_text;
        @Required
        private String src_text;
        private Optional<String> src_language = Optional.empty();
        private Optional<String> dest_language = Optional.empty();
        private Optional<TranslationType> type = Optional.empty();
        private Optional<DictionaryTranslation> dictionary = Optional.empty();
        private Optional<MachineTranslation> machine = Optional.empty();

        public Translation() {
        }

        public Translation(String str, String str2) {
            this.src_text = str;
            this.dest_text = str2;
        }

        @Required
        public Translation setSrcText(String str) {
            this.src_text = str;
            return this;
        }

        @Required
        public String getSrcText() {
            return this.src_text;
        }

        @Required
        public Translation setDestText(String str) {
            this.dest_text = str;
            return this;
        }

        @Required
        public String getDestText() {
            return this.dest_text;
        }

        public Translation setSrcLanguage(String str) {
            this.src_language = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSrcLanguage() {
            return this.src_language;
        }

        public Translation setDestLanguage(String str) {
            this.dest_language = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDestLanguage() {
            return this.dest_language;
        }

        public Translation setType(TranslationType translationType) {
            this.type = Optional.ofNullable(translationType);
            return this;
        }

        public Optional<TranslationType> getType() {
            return this.type;
        }

        public Translation setDictionary(DictionaryTranslation dictionaryTranslation) {
            this.dictionary = Optional.ofNullable(dictionaryTranslation);
            return this;
        }

        public Optional<DictionaryTranslation> getDictionary() {
            return this.dictionary;
        }

        public Translation setMachine(MachineTranslation machineTranslation) {
            this.machine = Optional.ofNullable(machineTranslation);
            return this;
        }

        public Optional<MachineTranslation> getMachine() {
            return this.machine;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoActor {
        @Required
        private Image avatar;
        @Required
        private String name;
        @Required
        private VideoActorRole role;

        public VideoActor() {
        }

        public VideoActor(String str, Image image, VideoActorRole videoActorRole) {
            this.name = str;
            this.avatar = image;
            this.role = videoActorRole;
        }

        @Required
        public VideoActor setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public VideoActor setAvatar(Image image) {
            this.avatar = image;
            return this;
        }

        @Required
        public Image getAvatar() {
            return this.avatar;
        }

        @Required
        public VideoActor setRole(VideoActorRole videoActorRole) {
            this.role = videoActorRole;
            return this;
        }

        @Required
        public VideoActorRole getRole() {
            return this.role;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoItem {
        @Required
        private List<Video.ThirdPartyContentProvider> cp;
        @Required
        private String id;
        @Required
        private VideoLengthType length_type;
        @Required
        private String name;
        private Optional<Image> horizontal_poster = Optional.empty();
        private Optional<Image> vertical_poster = Optional.empty();
        private Optional<VideoShowStatus> status = Optional.empty();
        private Optional<String> description = Optional.empty();
        private Optional<String> publish_date = Optional.empty();
        private Optional<Integer> publish_year = Optional.empty();
        private Optional<Float> rating = Optional.empty();
        private Optional<String> area = Optional.empty();
        private Optional<Integer> duration_in_min = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<Integer> latest_episode = Optional.empty();
        private Optional<Integer> episode_num = Optional.empty();
        private Optional<List<VideoActor>> actors = Optional.empty();
        private Optional<ObjectNode> log = Optional.empty();
        private Optional<Common.VideoMediaType> video_media_type = Optional.empty();
        private Optional<List<String>> tags = Optional.empty();

        public VideoItem() {
        }

        public VideoItem(String str, String str2, VideoLengthType videoLengthType, List<Video.ThirdPartyContentProvider> list) {
            this.name = str;
            this.id = str2;
            this.length_type = videoLengthType;
            this.cp = list;
        }

        @Required
        public VideoItem setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public VideoItem setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public VideoItem setLengthType(VideoLengthType videoLengthType) {
            this.length_type = videoLengthType;
            return this;
        }

        @Required
        public VideoLengthType getLengthType() {
            return this.length_type;
        }

        @Required
        public VideoItem setCp(List<Video.ThirdPartyContentProvider> list) {
            this.cp = list;
            return this;
        }

        @Required
        public List<Video.ThirdPartyContentProvider> getCp() {
            return this.cp;
        }

        public VideoItem setHorizontalPoster(Image image) {
            this.horizontal_poster = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getHorizontalPoster() {
            return this.horizontal_poster;
        }

        public VideoItem setVerticalPoster(Image image) {
            this.vertical_poster = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getVerticalPoster() {
            return this.vertical_poster;
        }

        public VideoItem setStatus(VideoShowStatus videoShowStatus) {
            this.status = Optional.ofNullable(videoShowStatus);
            return this;
        }

        public Optional<VideoShowStatus> getStatus() {
            return this.status;
        }

        public VideoItem setDescription(String str) {
            this.description = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescription() {
            return this.description;
        }

        public VideoItem setPublishDate(String str) {
            this.publish_date = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPublishDate() {
            return this.publish_date;
        }

        public VideoItem setPublishYear(int i) {
            this.publish_year = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPublishYear() {
            return this.publish_year;
        }

        public VideoItem setRating(float f) {
            this.rating = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getRating() {
            return this.rating;
        }

        public VideoItem setArea(String str) {
            this.area = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getArea() {
            return this.area;
        }

        public VideoItem setDurationInMin(int i) {
            this.duration_in_min = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDurationInMin() {
            return this.duration_in_min;
        }

        public VideoItem setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public VideoItem setLatestEpisode(int i) {
            this.latest_episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getLatestEpisode() {
            return this.latest_episode;
        }

        public VideoItem setEpisodeNum(int i) {
            this.episode_num = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisodeNum() {
            return this.episode_num;
        }

        public VideoItem setActors(List<VideoActor> list) {
            this.actors = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<VideoActor>> getActors() {
            return this.actors;
        }

        public VideoItem setLog(ObjectNode objectNode) {
            this.log = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getLog() {
            return this.log;
        }

        public VideoItem setVideoMediaType(Common.VideoMediaType videoMediaType) {
            this.video_media_type = Optional.ofNullable(videoMediaType);
            return this;
        }

        public Optional<Common.VideoMediaType> getVideoMediaType() {
            return this.video_media_type;
        }

        public VideoItem setTags(List<String> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getTags() {
            return this.tags;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSnapshot {
        private Optional<Image> cover = Optional.empty();
        private Optional<Title> title = Optional.empty();
        private Optional<Image> pause_icon = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<VideoSource> source = Optional.empty();
        private Optional<Integer> duration_in_ms = Optional.empty();

        public VideoSnapshot setCover(Image image) {
            this.cover = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getCover() {
            return this.cover;
        }

        public VideoSnapshot setTitle(Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getTitle() {
            return this.title;
        }

        public VideoSnapshot setPauseIcon(Image image) {
            this.pause_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getPauseIcon() {
            return this.pause_icon;
        }

        public VideoSnapshot setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public VideoSnapshot setSource(VideoSource videoSource) {
            this.source = Optional.ofNullable(videoSource);
            return this;
        }

        public Optional<VideoSource> getSource() {
            return this.source;
        }

        public VideoSnapshot setDurationInMs(int i) {
            this.duration_in_ms = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDurationInMs() {
            return this.duration_in_ms;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoSource {
        private Optional<String> id = Optional.empty();
        private Optional<Integer> width_in_pixel = Optional.empty();
        private Optional<Integer> height_in_pixel = Optional.empty();
        private Optional<Boolean> authentication = Optional.empty();
        private Optional<String> token = Optional.empty();
        private Optional<String> md5 = Optional.empty();

        public VideoSource setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public VideoSource setWidthInPixel(int i) {
            this.width_in_pixel = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getWidthInPixel() {
            return this.width_in_pixel;
        }

        public VideoSource setHeightInPixel(int i) {
            this.height_in_pixel = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getHeightInPixel() {
            return this.height_in_pixel;
        }

        public VideoSource setAuthentication(boolean z) {
            this.authentication = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAuthentication() {
            return this.authentication;
        }

        public VideoSource setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }

        public VideoSource setMd5(String str) {
            this.md5 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMd5() {
            return this.md5;
        }
    }

    @NamespaceName(name = "Videos", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Videos implements InstructionPayload {
        @Required
        private SearchHitLevel hit_level;
        @Required
        private int hits;
        @Required
        private List<VideoItem> items;
        private Optional<Integer> episode = Optional.empty();
        private Optional<Boolean> is_auto_play = Optional.empty();

        public Videos() {
        }

        public Videos(List<VideoItem> list, SearchHitLevel searchHitLevel, int i) {
            this.items = list;
            this.hit_level = searchHitLevel;
            this.hits = i;
        }

        @Required
        public Videos setItems(List<VideoItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<VideoItem> getItems() {
            return this.items;
        }

        @Required
        public Videos setHitLevel(SearchHitLevel searchHitLevel) {
            this.hit_level = searchHitLevel;
            return this;
        }

        @Required
        public SearchHitLevel getHitLevel() {
            return this.hit_level;
        }

        @Required
        public Videos setHits(int i) {
            this.hits = i;
            return this;
        }

        @Required
        public int getHits() {
            return this.hits;
        }

        public Videos setEpisode(int i) {
            this.episode = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEpisode() {
            return this.episode;
        }

        public Videos setIsAutoPlay(boolean z) {
            this.is_auto_play = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isAutoPlay() {
            return this.is_auto_play;
        }
    }

    @NamespaceName(name = "Weather", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class Weather implements InstructionPayload {
        @Required
        private Image skill_icon;
        @Required
        private List<WeatherItem> weather;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();

        public Weather() {
        }

        public Weather(List<WeatherItem> list, Image image) {
            this.weather = list;
            this.skill_icon = image;
        }

        @Required
        public Weather setWeather(List<WeatherItem> list) {
            this.weather = list;
            return this;
        }

        @Required
        public List<WeatherItem> getWeather() {
            return this.weather;
        }

        @Required
        public Weather setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public Weather setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public Weather setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public Weather setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherCode {
        private Optional<String> current = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> to = Optional.empty();

        public WeatherCode setCurrent(String str) {
            this.current = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrent() {
            return this.current;
        }

        public WeatherCode setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public WeatherCode setTo(String str) {
            this.to = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTo() {
            return this.to;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherItem {
        @Required
        private String date;
        @Required
        private String location;
        private Optional<String> aqi = Optional.empty();
        private Optional<String> current_temperature = Optional.empty();
        private Optional<String> high_temperature = Optional.empty();
        private Optional<String> low_temperature = Optional.empty();
        private Optional<Image> icon = Optional.empty();
        private Optional<WeatherCode> weather_code = Optional.empty();
        private Optional<WeatherWind> wind = Optional.empty();

        public WeatherItem() {
        }

        public WeatherItem(String str, String str2) {
            this.date = str;
            this.location = str2;
        }

        @Required
        public WeatherItem setDate(String str) {
            this.date = str;
            return this;
        }

        @Required
        public String getDate() {
            return this.date;
        }

        public WeatherItem setAqi(String str) {
            this.aqi = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAqi() {
            return this.aqi;
        }

        @Required
        public WeatherItem setLocation(String str) {
            this.location = str;
            return this;
        }

        @Required
        public String getLocation() {
            return this.location;
        }

        public WeatherItem setCurrentTemperature(String str) {
            this.current_temperature = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrentTemperature() {
            return this.current_temperature;
        }

        public WeatherItem setHighTemperature(String str) {
            this.high_temperature = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHighTemperature() {
            return this.high_temperature;
        }

        public WeatherItem setLowTemperature(String str) {
            this.low_temperature = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLowTemperature() {
            return this.low_temperature;
        }

        public WeatherItem setIcon(Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Image> getIcon() {
            return this.icon;
        }

        public WeatherItem setWeatherCode(WeatherCode weatherCode) {
            this.weather_code = Optional.ofNullable(weatherCode);
            return this;
        }

        public Optional<WeatherCode> getWeatherCode() {
            return this.weather_code;
        }

        public WeatherItem setWind(WeatherWind weatherWind) {
            this.wind = Optional.ofNullable(weatherWind);
            return this;
        }

        public Optional<WeatherWind> getWind() {
            return this.wind;
        }
    }

    @NamespaceName(name = "WeatherV2", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class WeatherV2 implements InstructionPayload {
        @Required
        private Title location;
        @Required
        private Image skill_icon;
        @Required
        private List<FullScreenTemplate.WeatherFragmentData> weather;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<Boolean> daylight = Optional.empty();

        public WeatherV2() {
        }

        public WeatherV2(Title title, List<FullScreenTemplate.WeatherFragmentData> list, Image image) {
            this.location = title;
            this.weather = list;
            this.skill_icon = image;
        }

        @Required
        public WeatherV2 setLocation(Title title) {
            this.location = title;
            return this;
        }

        @Required
        public Title getLocation() {
            return this.location;
        }

        @Required
        public WeatherV2 setWeather(List<FullScreenTemplate.WeatherFragmentData> list) {
            this.weather = list;
            return this;
        }

        @Required
        public List<FullScreenTemplate.WeatherFragmentData> getWeather() {
            return this.weather;
        }

        @Required
        public WeatherV2 setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public WeatherV2 setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public WeatherV2 setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public WeatherV2 setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public WeatherV2 setDaylight(boolean z) {
            this.daylight = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDaylight() {
            return this.daylight;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherWind {
        private Optional<WeatherWindDirection> direction = Optional.empty();
        private Optional<WeatherWindSpeed> speed = Optional.empty();

        public WeatherWind setDirection(WeatherWindDirection weatherWindDirection) {
            this.direction = Optional.ofNullable(weatherWindDirection);
            return this;
        }

        public Optional<WeatherWindDirection> getDirection() {
            return this.direction;
        }

        public WeatherWind setSpeed(WeatherWindSpeed weatherWindSpeed) {
            this.speed = Optional.ofNullable(weatherWindSpeed);
            return this;
        }

        public Optional<WeatherWindSpeed> getSpeed() {
            return this.speed;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherWindDirection {
        @Required
        private String text;
        private Optional<String> current = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> to = Optional.empty();

        public WeatherWindDirection() {
        }

        public WeatherWindDirection(String str) {
            this.text = str;
        }

        @Required
        public WeatherWindDirection setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public WeatherWindDirection setCurrent(String str) {
            this.current = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrent() {
            return this.current;
        }

        public WeatherWindDirection setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public WeatherWindDirection setTo(String str) {
            this.to = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTo() {
            return this.to;
        }
    }

    /* loaded from: classes3.dex */
    public static class WeatherWindSpeed {
        @Required
        private String text;
        private Optional<String> current = Optional.empty();
        private Optional<String> from = Optional.empty();
        private Optional<String> to = Optional.empty();

        public WeatherWindSpeed() {
        }

        public WeatherWindSpeed(String str) {
            this.text = str;
        }

        @Required
        public WeatherWindSpeed setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public WeatherWindSpeed setCurrent(String str) {
            this.current = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCurrent() {
            return this.current;
        }

        public WeatherWindSpeed setFrom(String str) {
            this.from = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFrom() {
            return this.from;
        }

        public WeatherWindSpeed setTo(String str) {
            this.to = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTo() {
            return this.to;
        }
    }

    /* loaded from: classes3.dex */
    public static class WikiDetail {
        @Required
        private Image skill_icon;
        @Required
        private String text;
        @Required
        private Title title;
        private Optional<Launcher> launcher = Optional.empty();
        private Optional<ClickAlbum> images = Optional.empty();
        private Optional<List<AbstractItem>> abstracts = Optional.empty();
        private Optional<GeneralDisplayType> type = Optional.empty();
        private Optional<CustomBackground> background = Optional.empty();
        private Optional<DisplayCommon> display = Optional.empty();
        private Optional<VideoSnapshot> videoSnapshot = Optional.empty();
        private Optional<EmbeddedList> relationShip = Optional.empty();
        private Optional<EmbeddedList> news = Optional.empty();
        private Optional<Title> polysemy = Optional.empty();
        private Optional<WikiMoreDetail> more_detail = Optional.empty();
        private Optional<String> entity_id = Optional.empty();

        public WikiDetail() {
        }

        public WikiDetail(Title title, String str, Image image) {
            this.title = title;
            this.text = str;
            this.skill_icon = image;
        }

        @Required
        public WikiDetail setTitle(Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Title getTitle() {
            return this.title;
        }

        @Required
        public WikiDetail setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public WikiDetail setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public WikiDetail setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }

        public WikiDetail setImages(ClickAlbum clickAlbum) {
            this.images = Optional.ofNullable(clickAlbum);
            return this;
        }

        public Optional<ClickAlbum> getImages() {
            return this.images;
        }

        public WikiDetail setAbstracts(List<AbstractItem> list) {
            this.abstracts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AbstractItem>> getAbstracts() {
            return this.abstracts;
        }

        public WikiDetail setType(GeneralDisplayType generalDisplayType) {
            this.type = Optional.ofNullable(generalDisplayType);
            return this;
        }

        public Optional<GeneralDisplayType> getType() {
            return this.type;
        }

        public WikiDetail setBackground(CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<CustomBackground> getBackground() {
            return this.background;
        }

        public WikiDetail setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }

        public WikiDetail setVideoSnapshot(VideoSnapshot videoSnapshot) {
            this.videoSnapshot = Optional.ofNullable(videoSnapshot);
            return this;
        }

        public Optional<VideoSnapshot> getVideoSnapshot() {
            return this.videoSnapshot;
        }

        public WikiDetail setRelationShip(EmbeddedList embeddedList) {
            this.relationShip = Optional.ofNullable(embeddedList);
            return this;
        }

        public Optional<EmbeddedList> getRelationShip() {
            return this.relationShip;
        }

        public WikiDetail setNews(EmbeddedList embeddedList) {
            this.news = Optional.ofNullable(embeddedList);
            return this;
        }

        public Optional<EmbeddedList> getNews() {
            return this.news;
        }

        public WikiDetail setPolysemy(Title title) {
            this.polysemy = Optional.ofNullable(title);
            return this;
        }

        public Optional<Title> getPolysemy() {
            return this.polysemy;
        }

        public WikiDetail setMoreDetail(WikiMoreDetail wikiMoreDetail) {
            this.more_detail = Optional.ofNullable(wikiMoreDetail);
            return this;
        }

        public Optional<WikiMoreDetail> getMoreDetail() {
            return this.more_detail;
        }

        public WikiDetail setEntityId(String str) {
            this.entity_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEntityId() {
            return this.entity_id;
        }
    }

    @NamespaceName(name = "WikiEvent", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class WikiEvent implements EventPayload {
        @Required
        private WikiEventType category;
        private Optional<String> text = Optional.empty();
        private Optional<String> sid = Optional.empty();
        private Optional<String> mentions = Optional.empty();

        public WikiEvent() {
        }

        public WikiEvent(WikiEventType wikiEventType) {
            this.category = wikiEventType;
        }

        @Required
        public WikiEvent setCategory(WikiEventType wikiEventType) {
            this.category = wikiEventType;
            return this;
        }

        @Required
        public WikiEventType getCategory() {
            return this.category;
        }

        public WikiEvent setText(String str) {
            this.text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getText() {
            return this.text;
        }

        public WikiEvent setSid(String str) {
            this.sid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSid() {
            return this.sid;
        }

        public WikiEvent setMentions(String str) {
            this.mentions = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMentions() {
            return this.mentions;
        }
    }

    /* loaded from: classes3.dex */
    public static class WikiMoreDetail {
        @Required
        private WikiMoreItem base;
        private Optional<List<WikiMoreItem>> items = Optional.empty();
        private Optional<Launcher> launcher = Optional.empty();

        public WikiMoreDetail() {
        }

        public WikiMoreDetail(WikiMoreItem wikiMoreItem) {
            this.base = wikiMoreItem;
        }

        @Required
        public WikiMoreDetail setBase(WikiMoreItem wikiMoreItem) {
            this.base = wikiMoreItem;
            return this;
        }

        @Required
        public WikiMoreItem getBase() {
            return this.base;
        }

        public WikiMoreDetail setItems(List<WikiMoreItem> list) {
            this.items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<WikiMoreItem>> getItems() {
            return this.items;
        }

        public WikiMoreDetail setLauncher(Launcher launcher) {
            this.launcher = Optional.ofNullable(launcher);
            return this;
        }

        public Optional<Launcher> getLauncher() {
            return this.launcher;
        }
    }

    /* loaded from: classes3.dex */
    public static class WikiMoreItem {
        @Required
        private String desc;
        private Optional<String> sid = Optional.empty();
        private Optional<String> mentions = Optional.empty();

        public WikiMoreItem() {
        }

        public WikiMoreItem(String str) {
            this.desc = str;
        }

        @Required
        public WikiMoreItem setDesc(String str) {
            this.desc = str;
            return this;
        }

        @Required
        public String getDesc() {
            return this.desc;
        }

        public WikiMoreItem setSid(String str) {
            this.sid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSid() {
            return this.sid;
        }

        public WikiMoreItem setMentions(String str) {
            this.mentions = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMentions() {
            return this.mentions;
        }
    }

    /* loaded from: classes3.dex */
    public static class Words {
        @Required
        private List<Pronunciation> pronunciation;
        @Required
        private List<String> text;
        private Optional<List<Image>> image = Optional.empty();
        private Optional<List<AbstractItem>> property = Optional.empty();

        public Words() {
        }

        public Words(List<String> list, List<Pronunciation> list2) {
            this.text = list;
            this.pronunciation = list2;
        }

        @Required
        public Words setText(List<String> list) {
            this.text = list;
            return this;
        }

        @Required
        public List<String> getText() {
            return this.text;
        }

        @Required
        public Words setPronunciation(List<Pronunciation> list) {
            this.pronunciation = list;
            return this;
        }

        @Required
        public List<Pronunciation> getPronunciation() {
            return this.pronunciation;
        }

        public Words setImage(List<Image> list) {
            this.image = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Image>> getImage() {
            return this.image;
        }

        public Words setProperty(List<AbstractItem> list) {
            this.property = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<AbstractItem>> getProperty() {
            return this.property;
        }
    }

    @NamespaceName(name = "WordsInfo", namespace = AIApiConstants.Template.NAME)
    /* loaded from: classes3.dex */
    public static class WordsInfo implements InstructionPayload {
        private Optional<DisplayCommon> display = Optional.empty();
        @Required
        private FullScreen full_screen;
        @Required
        private Image skill_icon;
        @Required
        private Words words;

        public WordsInfo() {
        }

        public WordsInfo(Words words, FullScreen fullScreen, Image image) {
            this.words = words;
            this.full_screen = fullScreen;
            this.skill_icon = image;
        }

        @Required
        public WordsInfo setWords(Words words) {
            this.words = words;
            return this;
        }

        @Required
        public Words getWords() {
            return this.words;
        }

        @Required
        public WordsInfo setFullScreen(FullScreen fullScreen) {
            this.full_screen = fullScreen;
            return this;
        }

        @Required
        public FullScreen getFullScreen() {
            return this.full_screen;
        }

        @Required
        public WordsInfo setSkillIcon(Image image) {
            this.skill_icon = image;
            return this;
        }

        @Required
        public Image getSkillIcon() {
            return this.skill_icon;
        }

        public WordsInfo setDisplay(DisplayCommon displayCommon) {
            this.display = Optional.ofNullable(displayCommon);
            return this;
        }

        public Optional<DisplayCommon> getDisplay() {
            return this.display;
        }
    }
}
