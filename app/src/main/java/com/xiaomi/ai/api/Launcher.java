package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Launcher {

    /* loaded from: classes3.dex */
    public enum KuGouSDKIntentType {
        SONG(0),
        ALBUM(1),
        STATION(2);
        
        private int id;

        KuGouSDKIntentType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum KuGouSDKRecommendType {
        EVERYDAY(0),
        GUESS(1),
        NEW_SONG(2);
        
        private int id;

        KuGouSDKRecommendType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum KuGouSDKSlotNameType {
        SONG(0),
        SINGER(1),
        ALBUM(2),
        AGE(3),
        PEOPLE(4),
        TOPLIST(5),
        EMOTION(6),
        INSTRUMENT(7),
        LANGUAGE(8),
        SCENE(9),
        STYLE(10),
        THEME(11),
        TVFILM(12),
        VERSION(13),
        CODE(14),
        RADIO(15),
        LIST(16),
        GENDER(17),
        AREA(18),
        SOURCE(19),
        TAG(20);
        
        private int id;

        KuGouSDKSlotNameType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum KuGouSDKSlotType {
        ENTITY(0),
        NUMBER(1);
        
        private int id;

        KuGouSDKSlotType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LaunchTargetType {
        UNKNOWN(-1),
        MAPPING(0),
        URL(1),
        ANDROID_INTENT(2),
        IOS_SCHEME(3),
        SDK(4),
        ANDROID_KEY_CODE(5);
        
        private int id;

        LaunchTargetType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum LoadingType {
        UNKNOWN(-1),
        NONE(0),
        SYSTEM(1),
        CARD(2);
        
        private int id;

        LoadingType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum NetEaseParamType {
        UNKNOWN(-1),
        SONG(0),
        ARTIST(1);
        
        private int id;

        NetEaseParamType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ThirdPartyMapSDKCPType {
        GAODE(0),
        BAIDU(1),
        TENCENT(2);
        
        private int id;

        ThirdPartyMapSDKCPType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ThirdPartySDKType {
        KUGOU(0),
        MAP(1),
        NETEASE(2);
        
        private int id;

        ThirdPartySDKType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum UIType {
        UNKNOWN(-1),
        FULL(0),
        PART(1),
        ANIMATION(2);
        
        private int id;

        UIType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class KuGouSDK {
        @Required
        private boolean is_recommend;
        private Optional<KuGouSDKRecommendType> recommend_type = Optional.empty();
        private Optional<String> domain = Optional.empty();
        private Optional<KuGouSDKIntentType> intent = Optional.empty();
        private Optional<List<KuGouSDKSlot>> slots = Optional.empty();

        public KuGouSDK() {
        }

        public KuGouSDK(boolean z) {
            this.is_recommend = z;
        }

        @Required
        public KuGouSDK setIsRecommend(boolean z) {
            this.is_recommend = z;
            return this;
        }

        @Required
        public boolean isRecommend() {
            return this.is_recommend;
        }

        public KuGouSDK setRecommendType(KuGouSDKRecommendType kuGouSDKRecommendType) {
            this.recommend_type = Optional.ofNullable(kuGouSDKRecommendType);
            return this;
        }

        public Optional<KuGouSDKRecommendType> getRecommendType() {
            return this.recommend_type;
        }

        public KuGouSDK setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public KuGouSDK setIntent(KuGouSDKIntentType kuGouSDKIntentType) {
            this.intent = Optional.ofNullable(kuGouSDKIntentType);
            return this;
        }

        public Optional<KuGouSDKIntentType> getIntent() {
            return this.intent;
        }

        public KuGouSDK setSlots(List<KuGouSDKSlot> list) {
            this.slots = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<KuGouSDKSlot>> getSlots() {
            return this.slots;
        }
    }

    /* loaded from: classes3.dex */
    public static class KuGouSDKSlot {
        @Required
        private KuGouSDKSlotNameType name;
        @Required
        private KuGouSDKSlotType type;
        @Required
        private List<KuGouSDKSlotValue> values;

        public KuGouSDKSlot() {
        }

        public KuGouSDKSlot(KuGouSDKSlotNameType kuGouSDKSlotNameType, KuGouSDKSlotType kuGouSDKSlotType, List<KuGouSDKSlotValue> list) {
            this.name = kuGouSDKSlotNameType;
            this.type = kuGouSDKSlotType;
            this.values = list;
        }

        @Required
        public KuGouSDKSlot setName(KuGouSDKSlotNameType kuGouSDKSlotNameType) {
            this.name = kuGouSDKSlotNameType;
            return this;
        }

        @Required
        public KuGouSDKSlotNameType getName() {
            return this.name;
        }

        @Required
        public KuGouSDKSlot setType(KuGouSDKSlotType kuGouSDKSlotType) {
            this.type = kuGouSDKSlotType;
            return this;
        }

        @Required
        public KuGouSDKSlotType getType() {
            return this.type;
        }

        @Required
        public KuGouSDKSlot setValues(List<KuGouSDKSlotValue> list) {
            this.values = list;
            return this;
        }

        @Required
        public List<KuGouSDKSlotValue> getValues() {
            return this.values;
        }
    }

    /* loaded from: classes3.dex */
    public static class KuGouSDKSlotValue {
        @Required
        private String original_text;
        @Required
        private String text;

        public KuGouSDKSlotValue() {
        }

        public KuGouSDKSlotValue(String str, String str2) {
            this.text = str;
            this.original_text = str2;
        }

        @Required
        public KuGouSDKSlotValue setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public KuGouSDKSlotValue setOriginalText(String str) {
            this.original_text = str;
            return this;
        }

        @Required
        public String getOriginalText() {
            return this.original_text;
        }
    }

    @NamespaceName(name = "LaunchApp", namespace = AIApiConstants.Launcher.NAME)
    /* loaded from: classes3.dex */
    public static class LaunchApp implements InstructionPayload {
        @Required
        private String identifier;
        @Required
        private String name;
        private Optional<Template.AndroidIntent> intent = Optional.empty();
        @Deprecated
        private Optional<Template.OnError> on_error = Optional.empty();
        private Optional<String> url = Optional.empty();
        private Optional<LaunchTargetType> type = Optional.empty();
        private Optional<Boolean> cacheable = Optional.empty();
        private Optional<ThirdPartySDK> sdk = Optional.empty();
        private Optional<Template.AndroidKeyCode> key_code = Optional.empty();

        public LaunchApp() {
        }

        public LaunchApp(String str, String str2) {
            this.name = str;
            this.identifier = str2;
        }

        @Required
        public LaunchApp setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public LaunchApp setIdentifier(String str) {
            this.identifier = str;
            return this;
        }

        @Required
        public String getIdentifier() {
            return this.identifier;
        }

        public LaunchApp setIntent(Template.AndroidIntent androidIntent) {
            this.intent = Optional.ofNullable(androidIntent);
            return this;
        }

        public Optional<Template.AndroidIntent> getIntent() {
            return this.intent;
        }

        @Deprecated
        public LaunchApp setOnError(Template.OnError onError) {
            this.on_error = Optional.ofNullable(onError);
            return this;
        }

        @Deprecated
        public Optional<Template.OnError> getOnError() {
            return this.on_error;
        }

        public LaunchApp setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public LaunchApp setType(LaunchTargetType launchTargetType) {
            this.type = Optional.ofNullable(launchTargetType);
            return this;
        }

        public Optional<LaunchTargetType> getType() {
            return this.type;
        }

        public LaunchApp setCacheable(boolean z) {
            this.cacheable = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isCacheable() {
            return this.cacheable;
        }

        public LaunchApp setSdk(ThirdPartySDK thirdPartySDK) {
            this.sdk = Optional.ofNullable(thirdPartySDK);
            return this;
        }

        public Optional<ThirdPartySDK> getSdk() {
            return this.sdk;
        }

        public LaunchApp setKeyCode(Template.AndroidKeyCode androidKeyCode) {
            this.key_code = Optional.ofNullable(androidKeyCode);
            return this;
        }

        public Optional<Template.AndroidKeyCode> getKeyCode() {
            return this.key_code;
        }
    }

    @NamespaceName(name = "LaunchGeneralQuickApp", namespace = AIApiConstants.Launcher.NAME)
    /* loaded from: classes3.dex */
    public static class LaunchGeneralQuickApp implements InstructionPayload {
        @Required
        private String path;
        @Required
        private String pkg_name;
        private Optional<String> parameters = Optional.empty();
        private Optional<ObjectNode> extra = Optional.empty();

        public LaunchGeneralQuickApp() {
        }

        public LaunchGeneralQuickApp(String str, String str2) {
            this.pkg_name = str;
            this.path = str2;
        }

        @Required
        public LaunchGeneralQuickApp setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public LaunchGeneralQuickApp setPath(String str) {
            this.path = str;
            return this;
        }

        @Required
        public String getPath() {
            return this.path;
        }

        public LaunchGeneralQuickApp setParameters(String str) {
            this.parameters = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getParameters() {
            return this.parameters;
        }

        public LaunchGeneralQuickApp setExtra(ObjectNode objectNode) {
            this.extra = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getExtra() {
            return this.extra;
        }
    }

    @NamespaceName(name = "LaunchQuickApp", namespace = AIApiConstants.Launcher.NAME)
    /* loaded from: classes3.dex */
    public static class LaunchQuickApp implements InstructionPayload {
        @Required
        private ObjectNode params;
        @Required
        private String pkg_name;
        @Required
        private int size;
        @Required
        private Template.AppVersion version;
        private Optional<LoadingType> loading_type = Optional.empty();
        private Optional<UIType> ui_type = Optional.empty();
        private Optional<Template.CustomBackground> background = Optional.empty();
        private Optional<Template.Task> task = Optional.empty();
        private Optional<Template.FullScreen> full_screen = Optional.empty();

        public LaunchQuickApp() {
        }

        public LaunchQuickApp(String str, int i, Template.AppVersion appVersion, ObjectNode objectNode) {
            this.pkg_name = str;
            this.size = i;
            this.version = appVersion;
            this.params = objectNode;
        }

        @Required
        public LaunchQuickApp setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public LaunchQuickApp setSize(int i) {
            this.size = i;
            return this;
        }

        @Required
        public int getSize() {
            return this.size;
        }

        @Required
        public LaunchQuickApp setVersion(Template.AppVersion appVersion) {
            this.version = appVersion;
            return this;
        }

        @Required
        public Template.AppVersion getVersion() {
            return this.version;
        }

        @Required
        public LaunchQuickApp setParams(ObjectNode objectNode) {
            this.params = objectNode;
            return this;
        }

        @Required
        public ObjectNode getParams() {
            return this.params;
        }

        public LaunchQuickApp setLoadingType(LoadingType loadingType) {
            this.loading_type = Optional.ofNullable(loadingType);
            return this;
        }

        public Optional<LoadingType> getLoadingType() {
            return this.loading_type;
        }

        public LaunchQuickApp setUiType(UIType uIType) {
            this.ui_type = Optional.ofNullable(uIType);
            return this;
        }

        public Optional<UIType> getUiType() {
            return this.ui_type;
        }

        public LaunchQuickApp setBackground(Template.CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<Template.CustomBackground> getBackground() {
            return this.background;
        }

        public LaunchQuickApp setTask(Template.Task task) {
            this.task = Optional.ofNullable(task);
            return this;
        }

        public Optional<Template.Task> getTask() {
            return this.task;
        }

        public LaunchQuickApp setFullScreen(Template.FullScreen fullScreen) {
            this.full_screen = Optional.ofNullable(fullScreen);
            return this;
        }

        public Optional<Template.FullScreen> getFullScreen() {
            return this.full_screen;
        }
    }

    @NamespaceName(name = "LaunchShortcut", namespace = AIApiConstants.Launcher.NAME)
    /* loaded from: classes3.dex */
    public static class LaunchShortcut implements InstructionPayload {
        @Required
        private String identifier;
        @Required
        private String name;
        private Optional<LaunchTargetType> type = Optional.empty();
        private Optional<Template.AndroidIntent> intent = Optional.empty();

        public LaunchShortcut() {
        }

        public LaunchShortcut(String str, String str2) {
            this.name = str;
            this.identifier = str2;
        }

        @Required
        public LaunchShortcut setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public LaunchShortcut setIdentifier(String str) {
            this.identifier = str;
            return this;
        }

        @Required
        public String getIdentifier() {
            return this.identifier;
        }

        public LaunchShortcut setType(LaunchTargetType launchTargetType) {
            this.type = Optional.ofNullable(launchTargetType);
            return this;
        }

        public Optional<LaunchTargetType> getType() {
            return this.type;
        }

        public LaunchShortcut setIntent(Template.AndroidIntent androidIntent) {
            this.intent = Optional.ofNullable(androidIntent);
            return this;
        }

        public Optional<Template.AndroidIntent> getIntent() {
            return this.intent;
        }
    }

    /* loaded from: classes3.dex */
    public static class NetEaseSDK {
        @Required
        private String action_target;
        private Optional<NetEaseSDKParam> param = Optional.empty();

        public NetEaseSDK() {
        }

        public NetEaseSDK(String str) {
            this.action_target = str;
        }

        @Required
        public NetEaseSDK setActionTarget(String str) {
            this.action_target = str;
            return this;
        }

        @Required
        public String getActionTarget() {
            return this.action_target;
        }

        public NetEaseSDK setParam(NetEaseSDKParam netEaseSDKParam) {
            this.param = Optional.ofNullable(netEaseSDKParam);
            return this;
        }

        public Optional<NetEaseSDKParam> getParam() {
            return this.param;
        }
    }

    /* loaded from: classes3.dex */
    public static class NetEaseSDKParam {
        private Optional<String> name = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<NetEaseParamType> type = Optional.empty();
        private Optional<String> keyword = Optional.empty();

        public NetEaseSDKParam setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public NetEaseSDKParam setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public NetEaseSDKParam setType(NetEaseParamType netEaseParamType) {
            this.type = Optional.ofNullable(netEaseParamType);
            return this;
        }

        public Optional<NetEaseParamType> getType() {
            return this.type;
        }

        public NetEaseSDKParam setKeyword(String str) {
            this.keyword = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyword() {
            return this.keyword;
        }
    }

    /* loaded from: classes3.dex */
    public static class ThirdPartyMapSDKParam {
        @Required
        private ThirdPartyMapSDKCPType cp_name;
        @Required
        private String query;
        private Optional<String> token = Optional.empty();

        public ThirdPartyMapSDKParam() {
        }

        public ThirdPartyMapSDKParam(String str, ThirdPartyMapSDKCPType thirdPartyMapSDKCPType) {
            this.query = str;
            this.cp_name = thirdPartyMapSDKCPType;
        }

        @Required
        public ThirdPartyMapSDKParam setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        @Required
        public ThirdPartyMapSDKParam setCpName(ThirdPartyMapSDKCPType thirdPartyMapSDKCPType) {
            this.cp_name = thirdPartyMapSDKCPType;
            return this;
        }

        @Required
        public ThirdPartyMapSDKCPType getCpName() {
            return this.cp_name;
        }

        public ThirdPartyMapSDKParam setToken(String str) {
            this.token = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getToken() {
            return this.token;
        }
    }

    /* loaded from: classes3.dex */
    public static class ThirdPartySDK {
        private Optional<ObjectNode> params = Optional.empty();
        @Required
        private ThirdPartySDKType type;

        public ThirdPartySDK() {
        }

        public ThirdPartySDK(ThirdPartySDKType thirdPartySDKType) {
            this.type = thirdPartySDKType;
        }

        @Required
        public ThirdPartySDK setType(ThirdPartySDKType thirdPartySDKType) {
            this.type = thirdPartySDKType;
            return this;
        }

        @Required
        public ThirdPartySDKType getType() {
            return this.type;
        }

        public ThirdPartySDK setParams(ObjectNode objectNode) {
            this.params = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getParams() {
            return this.params;
        }
    }
}
