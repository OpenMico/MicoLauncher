package com.xiaomi.ai.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Suggestion {

    /* loaded from: classes3.dex */
    public enum ResultsPageCardSizeType {
        ALL(0),
        HALF(1);
        
        private int id;

        ResultsPageCardSizeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SuggestPage {
        UNKNOWN(-1),
        SEARCH_PAGE(0),
        DETAIL_PAGE(1),
        PLAYER_PAGE(2),
        LIST_PAGE(3);
        
        private int id;

        SuggestPage(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SuggestQueryType {
        UNKNOWN(-1),
        PRIMARY_INTENTION(0),
        MULTI_INTENTION(1),
        SUGGEST_QUERY(2),
        REWRITE_QUERY(3),
        THIRD_PARTY(4),
        COMPLETE_QUERY(5),
        COMMERCIAL(6);
        
        private int id;

        SuggestQueryType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SuggestRequestType {
        UNKNOWN(-1),
        CONTEXT(0),
        GUIDANCE(1),
        CONTRIBUTOR(2);
        
        private int id;

        SuggestRequestType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SuggestStage {
        UNKNOWN(-1),
        AFTER_PARSER(0),
        AFTER_PROVIDER(1);
        
        private int id;

        SuggestStage(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SuggestUITemplate {
        UNKNOWN(-1),
        NORMAL(0),
        BIG_CARD(1);
        
        private int id;

        SuggestUITemplate(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public static class CloudControlV3 {
        @Required
        private int show_layout;
        @Required
        private boolean with_double_quotation;
        private Optional<SuggestUITemplate> ui_template = Optional.empty();
        private Optional<Template.Image> card_icon = Optional.empty();
        private Optional<String> button_color = Optional.empty();

        public CloudControlV3() {
        }

        public CloudControlV3(int i, boolean z) {
            this.show_layout = i;
            this.with_double_quotation = z;
        }

        @Required
        public CloudControlV3 setShowLayout(int i) {
            this.show_layout = i;
            return this;
        }

        @Required
        public int getShowLayout() {
            return this.show_layout;
        }

        @Required
        public CloudControlV3 setWithDoubleQuotation(boolean z) {
            this.with_double_quotation = z;
            return this;
        }

        @Required
        public boolean isWithDoubleQuotation() {
            return this.with_double_quotation;
        }

        public CloudControlV3 setUiTemplate(SuggestUITemplate suggestUITemplate) {
            this.ui_template = Optional.ofNullable(suggestUITemplate);
            return this;
        }

        public Optional<SuggestUITemplate> getUiTemplate() {
            return this.ui_template;
        }

        public CloudControlV3 setCardIcon(Template.Image image) {
            this.card_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getCardIcon() {
            return this.card_icon;
        }

        public CloudControlV3 setButtonColor(String str) {
            this.button_color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getButtonColor() {
            return this.button_color;
        }
    }

    /* loaded from: classes3.dex */
    public static class ExampleDesc {
        @Required
        private boolean clicked;
        @Required
        private String id;
        @Required
        private String query;

        public ExampleDesc() {
        }

        public ExampleDesc(String str, String str2, boolean z) {
            this.id = str;
            this.query = str2;
            this.clicked = z;
        }

        @Required
        public ExampleDesc setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public ExampleDesc setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        @Required
        public ExampleDesc setClicked(boolean z) {
            this.clicked = z;
            return this;
        }

        @Required
        public boolean isClicked() {
            return this.clicked;
        }
    }

    /* loaded from: classes3.dex */
    public static class ExposeQuery {
        @Required
        private String query;
        private Optional<String> send_query = Optional.empty();
        private Optional<String> domain = Optional.empty();

        public ExposeQuery() {
        }

        public ExposeQuery(String str) {
            this.query = str;
        }

        @Required
        public ExposeQuery setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        public ExposeQuery setSendQuery(String str) {
            this.send_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSendQuery() {
            return this.send_query;
        }

        public ExposeQuery setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }
    }

    @NamespaceName(name = "FetchContextSuggestions", namespace = AIApiConstants.Suggestion.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class FetchContextSuggestions implements InstructionPayload {
        @Required
        private List<SuggestionItem> suggestions;

        public FetchContextSuggestions() {
        }

        public FetchContextSuggestions(List<SuggestionItem> list) {
            this.suggestions = list;
        }

        @Required
        public FetchContextSuggestions setSuggestions(List<SuggestionItem> list) {
            this.suggestions = list;
            return this;
        }

        @Required
        public List<SuggestionItem> getSuggestions() {
            return this.suggestions;
        }
    }

    /* loaded from: classes3.dex */
    public static class FunctionDesc {
        @Required
        private String desc;
        @Required
        private String id;
        @Required
        private String logo_url;

        public FunctionDesc() {
        }

        public FunctionDesc(String str, String str2, String str3) {
            this.id = str;
            this.desc = str2;
            this.logo_url = str3;
        }

        @Required
        public FunctionDesc setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public FunctionDesc setDesc(String str) {
            this.desc = str;
            return this;
        }

        @Required
        public String getDesc() {
            return this.desc;
        }

        @Required
        public FunctionDesc setLogoUrl(String str) {
            this.logo_url = str;
            return this;
        }

        @Required
        public String getLogoUrl() {
            return this.logo_url;
        }
    }

    /* loaded from: classes3.dex */
    public static class QueryInfo {
        @Required
        private String button_text;
        @Required
        private boolean clicked;
        @Required
        private boolean exposed;
        @Required
        private String icon_url;
        @Required
        private String main_title;
        @Required
        private String subtitle;

        public QueryInfo() {
        }

        public QueryInfo(String str, String str2, String str3, String str4, boolean z, boolean z2) {
            this.icon_url = str;
            this.main_title = str2;
            this.subtitle = str3;
            this.button_text = str4;
            this.exposed = z;
            this.clicked = z2;
        }

        @Required
        public QueryInfo setIconUrl(String str) {
            this.icon_url = str;
            return this;
        }

        @Required
        public String getIconUrl() {
            return this.icon_url;
        }

        @Required
        public QueryInfo setMainTitle(String str) {
            this.main_title = str;
            return this;
        }

        @Required
        public String getMainTitle() {
            return this.main_title;
        }

        @Required
        public QueryInfo setSubtitle(String str) {
            this.subtitle = str;
            return this;
        }

        @Required
        public String getSubtitle() {
            return this.subtitle;
        }

        @Required
        public QueryInfo setButtonText(String str) {
            this.button_text = str;
            return this;
        }

        @Required
        public String getButtonText() {
            return this.button_text;
        }

        @Required
        public QueryInfo setExposed(boolean z) {
            this.exposed = z;
            return this;
        }

        @Required
        public boolean isExposed() {
            return this.exposed;
        }

        @Required
        public QueryInfo setClicked(boolean z) {
            this.clicked = z;
            return this;
        }

        @Required
        public boolean isClicked() {
            return this.clicked;
        }
    }

    /* loaded from: classes3.dex */
    public static class QuerySuggestion {
        @Required
        private String query;
        @Required
        private float score;
        @Required
        private SuggestQueryType suggest_query_type;
        @Required
        private List<String> tags;
        private Optional<String> send_query = Optional.empty();
        private Optional<String> domain = Optional.empty();
        private Optional<String> pkg_name = Optional.empty();
        private Optional<Integer> min_version = Optional.empty();
        private Optional<Template.Image> icon = Optional.empty();
        private Optional<ShortCutSuggestion> shortcut = Optional.empty();
        private Optional<String> norm_query = Optional.empty();
        private Optional<String> query_id = Optional.empty();
        private Optional<String> classfication = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<String> subcategory = Optional.empty();
        private Optional<String> module = Optional.empty();
        private Optional<String> function = Optional.empty();
        private Optional<Template.CustomBackground> background = Optional.empty();
        private Optional<ObjectNode> context = Optional.empty();
        private Optional<List<String>> view_monitor_urls = Optional.empty();
        private Optional<List<String>> click_monitor_urls = Optional.empty();
        private Optional<String> ads_track_extension = Optional.empty();
        private Optional<QuerySuggestionRedirection> redirection = Optional.empty();
        private Optional<String> logo_text = Optional.empty();
        private Optional<String> tag_id = Optional.empty();

        public QuerySuggestion() {
        }

        public QuerySuggestion(String str, float f, SuggestQueryType suggestQueryType, List<String> list) {
            this.query = str;
            this.score = f;
            this.suggest_query_type = suggestQueryType;
            this.tags = list;
        }

        @Required
        public QuerySuggestion setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        public QuerySuggestion setSendQuery(String str) {
            this.send_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSendQuery() {
            return this.send_query;
        }

        @Required
        public QuerySuggestion setScore(float f) {
            this.score = f;
            return this;
        }

        @Required
        public float getScore() {
            return this.score;
        }

        @Required
        public QuerySuggestion setSuggestQueryType(SuggestQueryType suggestQueryType) {
            this.suggest_query_type = suggestQueryType;
            return this;
        }

        @Required
        public SuggestQueryType getSuggestQueryType() {
            return this.suggest_query_type;
        }

        public QuerySuggestion setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public QuerySuggestion setPkgName(String str) {
            this.pkg_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPkgName() {
            return this.pkg_name;
        }

        public QuerySuggestion setMinVersion(int i) {
            this.min_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMinVersion() {
            return this.min_version;
        }

        public QuerySuggestion setIcon(Template.Image image) {
            this.icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getIcon() {
            return this.icon;
        }

        public QuerySuggestion setShortcut(ShortCutSuggestion shortCutSuggestion) {
            this.shortcut = Optional.ofNullable(shortCutSuggestion);
            return this;
        }

        public Optional<ShortCutSuggestion> getShortcut() {
            return this.shortcut;
        }

        public QuerySuggestion setNormQuery(String str) {
            this.norm_query = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getNormQuery() {
            return this.norm_query;
        }

        public QuerySuggestion setQueryId(String str) {
            this.query_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getQueryId() {
            return this.query_id;
        }

        public QuerySuggestion setClassfication(String str) {
            this.classfication = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getClassfication() {
            return this.classfication;
        }

        public QuerySuggestion setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public QuerySuggestion setSubcategory(String str) {
            this.subcategory = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSubcategory() {
            return this.subcategory;
        }

        public QuerySuggestion setModule(String str) {
            this.module = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getModule() {
            return this.module;
        }

        public QuerySuggestion setFunction(String str) {
            this.function = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFunction() {
            return this.function;
        }

        @Required
        public QuerySuggestion setTags(List<String> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<String> getTags() {
            return this.tags;
        }

        public QuerySuggestion setBackground(Template.CustomBackground customBackground) {
            this.background = Optional.ofNullable(customBackground);
            return this;
        }

        public Optional<Template.CustomBackground> getBackground() {
            return this.background;
        }

        public QuerySuggestion setContext(ObjectNode objectNode) {
            this.context = Optional.ofNullable(objectNode);
            return this;
        }

        public Optional<ObjectNode> getContext() {
            return this.context;
        }

        public QuerySuggestion setViewMonitorUrls(List<String> list) {
            this.view_monitor_urls = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getViewMonitorUrls() {
            return this.view_monitor_urls;
        }

        public QuerySuggestion setClickMonitorUrls(List<String> list) {
            this.click_monitor_urls = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getClickMonitorUrls() {
            return this.click_monitor_urls;
        }

        public QuerySuggestion setAdsTrackExtension(String str) {
            this.ads_track_extension = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAdsTrackExtension() {
            return this.ads_track_extension;
        }

        public QuerySuggestion setRedirection(QuerySuggestionRedirection querySuggestionRedirection) {
            this.redirection = Optional.ofNullable(querySuggestionRedirection);
            return this;
        }

        public Optional<QuerySuggestionRedirection> getRedirection() {
            return this.redirection;
        }

        public QuerySuggestion setLogoText(String str) {
            this.logo_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLogoText() {
            return this.logo_text;
        }

        public QuerySuggestion setTagId(String str) {
            this.tag_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTagId() {
            return this.tag_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class QuerySuggestionRedirection {
        private Optional<String> url = Optional.empty();

        /* renamed from: app  reason: collision with root package name */
        private Optional<QuerySuggestionRedirectionAppDownloadOrLink> f197app = Optional.empty();
        private Optional<String> ads_target_type = Optional.empty();
        private Optional<String> ads_interaction_type = Optional.empty();

        public QuerySuggestionRedirection setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }

        public QuerySuggestionRedirection setApp(QuerySuggestionRedirectionAppDownloadOrLink querySuggestionRedirectionAppDownloadOrLink) {
            this.f197app = Optional.ofNullable(querySuggestionRedirectionAppDownloadOrLink);
            return this;
        }

        public Optional<QuerySuggestionRedirectionAppDownloadOrLink> getApp() {
            return this.f197app;
        }

        public QuerySuggestionRedirection setAdsTargetType(String str) {
            this.ads_target_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAdsTargetType() {
            return this.ads_target_type;
        }

        public QuerySuggestionRedirection setAdsInteractionType(String str) {
            this.ads_interaction_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAdsInteractionType() {
            return this.ads_interaction_type;
        }
    }

    /* loaded from: classes3.dex */
    public static class QuerySuggestionRedirectionAppDownloadOrLink {
        @Required
        private String package_name;
        private Optional<String> deep_link_uri = Optional.empty();
        private Optional<String> app_download_uri = Optional.empty();
        private Optional<String> app_store_detail_page_uri = Optional.empty();

        public QuerySuggestionRedirectionAppDownloadOrLink() {
        }

        public QuerySuggestionRedirectionAppDownloadOrLink(String str) {
            this.package_name = str;
        }

        @Required
        public QuerySuggestionRedirectionAppDownloadOrLink setPackageName(String str) {
            this.package_name = str;
            return this;
        }

        @Required
        public String getPackageName() {
            return this.package_name;
        }

        public QuerySuggestionRedirectionAppDownloadOrLink setDeepLinkUri(String str) {
            this.deep_link_uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeepLinkUri() {
            return this.deep_link_uri;
        }

        public QuerySuggestionRedirectionAppDownloadOrLink setAppDownloadUri(String str) {
            this.app_download_uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppDownloadUri() {
            return this.app_download_uri;
        }

        public QuerySuggestionRedirectionAppDownloadOrLink setAppStoreDetailPageUri(String str) {
            this.app_store_detail_page_uri = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getAppStoreDetailPageUri() {
            return this.app_store_detail_page_uri;
        }
    }

    @NamespaceName(name = "ResultsPageEducationSuggestion", namespace = AIApiConstants.Suggestion.NAME)
    /* loaded from: classes3.dex */
    public static class ResultsPageEducationSuggestion implements InstructionPayload {
        @Required
        private String exp_id;
        @Required
        private List<QueryInfo> queryInfos;
        private Optional<String> card_title = Optional.empty();
        private Optional<String> card_button_text = Optional.empty();
        private Optional<String> card_button_url = Optional.empty();
        private Optional<ResultsPageCardSizeType> card_size_type = Optional.empty();

        public ResultsPageEducationSuggestion() {
        }

        public ResultsPageEducationSuggestion(String str, List<QueryInfo> list) {
            this.exp_id = str;
            this.queryInfos = list;
        }

        @Required
        public ResultsPageEducationSuggestion setExpId(String str) {
            this.exp_id = str;
            return this;
        }

        @Required
        public String getExpId() {
            return this.exp_id;
        }

        @Required
        public ResultsPageEducationSuggestion setQueryInfos(List<QueryInfo> list) {
            this.queryInfos = list;
            return this;
        }

        @Required
        public List<QueryInfo> getQueryInfos() {
            return this.queryInfos;
        }

        public ResultsPageEducationSuggestion setCardTitle(String str) {
            this.card_title = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardTitle() {
            return this.card_title;
        }

        public ResultsPageEducationSuggestion setCardButtonText(String str) {
            this.card_button_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardButtonText() {
            return this.card_button_text;
        }

        public ResultsPageEducationSuggestion setCardButtonUrl(String str) {
            this.card_button_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCardButtonUrl() {
            return this.card_button_url;
        }

        public ResultsPageEducationSuggestion setCardSizeType(ResultsPageCardSizeType resultsPageCardSizeType) {
            this.card_size_type = Optional.ofNullable(resultsPageCardSizeType);
            return this;
        }

        public Optional<ResultsPageCardSizeType> getCardSizeType() {
            return this.card_size_type;
        }
    }

    @NamespaceName(name = "RichSkillSuggestion", namespace = AIApiConstants.Suggestion.NAME)
    /* loaded from: classes3.dex */
    public static class RichSkillSuggestion implements InstructionPayload {
        @Required
        private List<ExampleDesc> examples;
        @Required
        private FunctionDesc function;
        @Required
        private String session_id;
        @Required
        private boolean show_examples_directly;
        @Required
        private SkillDesc skill;

        public RichSkillSuggestion() {
        }

        public RichSkillSuggestion(String str, boolean z, SkillDesc skillDesc, FunctionDesc functionDesc, List<ExampleDesc> list) {
            this.session_id = str;
            this.show_examples_directly = z;
            this.skill = skillDesc;
            this.function = functionDesc;
            this.examples = list;
        }

        @Required
        public RichSkillSuggestion setSessionId(String str) {
            this.session_id = str;
            return this;
        }

        @Required
        public String getSessionId() {
            return this.session_id;
        }

        @Required
        public RichSkillSuggestion setShowExamplesDirectly(boolean z) {
            this.show_examples_directly = z;
            return this;
        }

        @Required
        public boolean isShowExamplesDirectly() {
            return this.show_examples_directly;
        }

        @Required
        public RichSkillSuggestion setSkill(SkillDesc skillDesc) {
            this.skill = skillDesc;
            return this;
        }

        @Required
        public SkillDesc getSkill() {
            return this.skill;
        }

        @Required
        public RichSkillSuggestion setFunction(FunctionDesc functionDesc) {
            this.function = functionDesc;
            return this;
        }

        @Required
        public FunctionDesc getFunction() {
            return this.function;
        }

        @Required
        public RichSkillSuggestion setExamples(List<ExampleDesc> list) {
            this.examples = list;
            return this;
        }

        @Required
        public List<ExampleDesc> getExamples() {
            return this.examples;
        }
    }

    @NamespaceName(name = "RichSkillSuggestionInfo", namespace = AIApiConstants.Suggestion.NAME)
    /* loaded from: classes3.dex */
    public static class RichSkillSuggestionInfo implements ContextPayload {
        @Required
        private String session_id;

        public RichSkillSuggestionInfo() {
        }

        public RichSkillSuggestionInfo(String str) {
            this.session_id = str;
        }

        @Required
        public RichSkillSuggestionInfo setSessionId(String str) {
            this.session_id = str;
            return this;
        }

        @Required
        public String getSessionId() {
            return this.session_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class ShortCutSuggestion {
        @Required
        private String brief;
        @Required
        private String description;
        @Required
        private int minAiVersion;
        @Required
        private String name;
        @Required
        private String skill_id;
        @Required
        private String text_color;
        @Required
        private int version;
        private Optional<String> icon = Optional.empty();
        private Optional<String> icon_border_color = Optional.empty();
        private Optional<String> bg_image = Optional.empty();
        private Optional<String> bg_color = Optional.empty();
        private Optional<String> border_color = Optional.empty();

        public ShortCutSuggestion() {
        }

        public ShortCutSuggestion(String str, String str2, String str3, String str4, int i, int i2, String str5) {
            this.skill_id = str;
            this.name = str2;
            this.brief = str3;
            this.description = str4;
            this.version = i;
            this.minAiVersion = i2;
            this.text_color = str5;
        }

        @Required
        public ShortCutSuggestion setSkillId(String str) {
            this.skill_id = str;
            return this;
        }

        @Required
        public String getSkillId() {
            return this.skill_id;
        }

        @Required
        public ShortCutSuggestion setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public ShortCutSuggestion setBrief(String str) {
            this.brief = str;
            return this;
        }

        @Required
        public String getBrief() {
            return this.brief;
        }

        @Required
        public ShortCutSuggestion setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        @Required
        public ShortCutSuggestion setVersion(int i) {
            this.version = i;
            return this;
        }

        @Required
        public int getVersion() {
            return this.version;
        }

        @Required
        public ShortCutSuggestion setMinAiVersion(int i) {
            this.minAiVersion = i;
            return this;
        }

        @Required
        public int getMinAiVersion() {
            return this.minAiVersion;
        }

        public ShortCutSuggestion setIcon(String str) {
            this.icon = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIcon() {
            return this.icon;
        }

        public ShortCutSuggestion setIconBorderColor(String str) {
            this.icon_border_color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getIconBorderColor() {
            return this.icon_border_color;
        }

        public ShortCutSuggestion setBgImage(String str) {
            this.bg_image = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBgImage() {
            return this.bg_image;
        }

        public ShortCutSuggestion setBgColor(String str) {
            this.bg_color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBgColor() {
            return this.bg_color;
        }

        @Required
        public ShortCutSuggestion setTextColor(String str) {
            this.text_color = str;
            return this;
        }

        @Required
        public String getTextColor() {
            return this.text_color;
        }

        public ShortCutSuggestion setBorderColor(String str) {
            this.border_color = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBorderColor() {
            return this.border_color;
        }
    }

    @NamespaceName(name = "ShowContextSuggestions", namespace = AIApiConstants.Suggestion.NAME)
    /* loaded from: classes3.dex */
    public static class ShowContextSuggestions implements InstructionPayload {
        private Optional<CloudControlV3> cloud_control = Optional.empty();
        private Optional<String> exp_id = Optional.empty();
        @Required
        private List<QuerySuggestion> suggestions;

        public ShowContextSuggestions() {
        }

        public ShowContextSuggestions(List<QuerySuggestion> list) {
            this.suggestions = list;
        }

        public ShowContextSuggestions setCloudControl(CloudControlV3 cloudControlV3) {
            this.cloud_control = Optional.ofNullable(cloudControlV3);
            return this;
        }

        public Optional<CloudControlV3> getCloudControl() {
            return this.cloud_control;
        }

        @Required
        public ShowContextSuggestions setSuggestions(List<QuerySuggestion> list) {
            this.suggestions = list;
            return this;
        }

        @Required
        public List<QuerySuggestion> getSuggestions() {
            return this.suggestions;
        }

        public ShowContextSuggestions setExpId(String str) {
            this.exp_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getExpId() {
            return this.exp_id;
        }
    }

    /* loaded from: classes3.dex */
    public static class SkillDesc {
        @Required
        private String desc;
        @Required
        private String id;

        public SkillDesc() {
        }

        public SkillDesc(String str, String str2) {
            this.id = str;
            this.desc = str2;
        }

        @Required
        public SkillDesc setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public SkillDesc setDesc(String str) {
            this.desc = str;
            return this;
        }

        @Required
        public String getDesc() {
            return this.desc;
        }
    }

    /* loaded from: classes3.dex */
    public static class SuggestionItem {
        @Required
        private String domain;
        @Required
        private String query;
        private Optional<String> type = Optional.empty();
        private Optional<String> pkg_name = Optional.empty();
        private Optional<Integer> min_version = Optional.empty();
        private Optional<Template.Image> skill_icon = Optional.empty();

        public SuggestionItem() {
        }

        public SuggestionItem(String str, String str2) {
            this.query = str;
            this.domain = str2;
        }

        @Required
        public SuggestionItem setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        @Required
        public SuggestionItem setDomain(String str) {
            this.domain = str;
            return this;
        }

        @Required
        public String getDomain() {
            return this.domain;
        }

        public SuggestionItem setType(String str) {
            this.type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getType() {
            return this.type;
        }

        public SuggestionItem setPkgName(String str) {
            this.pkg_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPkgName() {
            return this.pkg_name;
        }

        public SuggestionItem setMinVersion(int i) {
            this.min_version = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getMinVersion() {
            return this.min_version;
        }

        public SuggestionItem setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class SuggestionParam {
        private Optional<SuggestionParamInfo> primary = Optional.empty();
        private Optional<SuggestionParamInfo> rewrite = Optional.empty();
        private Optional<List<QuerySuggestion>> suggestions = Optional.empty();

        public SuggestionParam setPrimary(SuggestionParamInfo suggestionParamInfo) {
            this.primary = Optional.ofNullable(suggestionParamInfo);
            return this;
        }

        public Optional<SuggestionParamInfo> getPrimary() {
            return this.primary;
        }

        public SuggestionParam setRewrite(SuggestionParamInfo suggestionParamInfo) {
            this.rewrite = Optional.ofNullable(suggestionParamInfo);
            return this;
        }

        public Optional<SuggestionParamInfo> getRewrite() {
            return this.rewrite;
        }

        public SuggestionParam setSuggestions(List<QuerySuggestion> list) {
            this.suggestions = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<QuerySuggestion>> getSuggestions() {
            return this.suggestions;
        }
    }

    /* loaded from: classes3.dex */
    public static class SuggestionParamInfo {
        @Required
        private List<Common.PropItem> entities;
        @Required
        private List<String> suggestion_type;
        private Optional<String> domain = Optional.empty();
        private Optional<String> func = Optional.empty();
        private Optional<SuggestPage> page = Optional.empty();

        public SuggestionParamInfo() {
        }

        public SuggestionParamInfo(List<Common.PropItem> list, List<String> list2) {
            this.entities = list;
            this.suggestion_type = list2;
        }

        public SuggestionParamInfo setDomain(String str) {
            this.domain = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDomain() {
            return this.domain;
        }

        public SuggestionParamInfo setFunc(String str) {
            this.func = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFunc() {
            return this.func;
        }

        @Required
        public SuggestionParamInfo setEntities(List<Common.PropItem> list) {
            this.entities = list;
            return this;
        }

        @Required
        public List<Common.PropItem> getEntities() {
            return this.entities;
        }

        @Required
        public SuggestionParamInfo setSuggestionType(List<String> list) {
            this.suggestion_type = list;
            return this;
        }

        @Required
        public List<String> getSuggestionType() {
            return this.suggestion_type;
        }

        public SuggestionParamInfo setPage(SuggestPage suggestPage) {
            this.page = Optional.ofNullable(suggestPage);
            return this;
        }

        public Optional<SuggestPage> getPage() {
            return this.page;
        }
    }

    @NamespaceName(name = "UploadExposeQueries", namespace = AIApiConstants.Suggestion.NAME)
    /* loaded from: classes3.dex */
    public static class UploadExposeQueries implements ContextPayload {
        @Required
        private List<ExposeQuery> expose_queries;
        private Optional<String> correlation_request = Optional.empty();
        private Optional<String> invoked_by = Optional.empty();

        public UploadExposeQueries() {
        }

        public UploadExposeQueries(List<ExposeQuery> list) {
            this.expose_queries = list;
        }

        public UploadExposeQueries setCorrelationRequest(String str) {
            this.correlation_request = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCorrelationRequest() {
            return this.correlation_request;
        }

        public UploadExposeQueries setInvokedBy(String str) {
            this.invoked_by = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getInvokedBy() {
            return this.invoked_by;
        }

        @Required
        public UploadExposeQueries setExposeQueries(List<ExposeQuery> list) {
            this.expose_queries = list;
            return this;
        }

        @Required
        public List<ExposeQuery> getExposeQueries() {
            return this.expose_queries;
        }
    }
}
