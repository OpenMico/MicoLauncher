package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class WakeGuide {
    public static final int WAKE_GUIDE_STATUS_OK = 200;
    private AnswerBean answer;
    private StatusBean status;

    public StatusBean getStatus() {
        return this.status;
    }

    public void setStatus(StatusBean statusBean) {
        this.status = statusBean;
    }

    public AnswerBean getAnswer() {
        return this.answer;
    }

    public void setAnswer(AnswerBean answerBean) {
        this.answer = answerBean;
    }

    /* loaded from: classes3.dex */
    public static class StatusBean {
        private int code;
        private String error_type;

        public int getCode() {
            return this.code;
        }

        public void setCode(int i) {
            this.code = i;
        }

        public String getError_type() {
            return this.error_type;
        }

        public void setError_type(String str) {
            this.error_type = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class AnswerBean {
        private String card_type;
        private CloudControlBean cloudControl;
        private DebugInfoBean debug_info;
        private String exp_id;
        private String module_title;
        @SerializedName("offline_suggestions")
        private OfflineSuggestion offlineSuggestion;
        private List<?> request_url;
        private List<SuggestionsBean> suggestions;
        private List<UpdatedResourcesBean> updated_resources;

        public AnswerBean(AnswerBean answerBean) {
            setModule_title(answerBean.getModule_title());
            setSuggestions(answerBean.getSuggestions());
            setCard_type(answerBean.getCard_type());
            setCloudControl(answerBean.getCloudControl());
            setDebug_info(answerBean.getDebug_info());
            setExp_id(answerBean.getExp_id());
            setOfflineSuggestion(answerBean.getOfflineSuggestion());
            setRequest_url(answerBean.getRequest_url());
            setUpdated_resources(answerBean.getUpdated_resources());
        }

        public CloudControlBean getCloudControl() {
            return this.cloudControl;
        }

        public void setCloudControl(CloudControlBean cloudControlBean) {
            this.cloudControl = cloudControlBean;
        }

        public String getExp_id() {
            return this.exp_id;
        }

        public void setExp_id(String str) {
            this.exp_id = str;
        }

        public String getCard_type() {
            return this.card_type;
        }

        public void setCard_type(String str) {
            this.card_type = str;
        }

        public DebugInfoBean getDebug_info() {
            return this.debug_info;
        }

        public void setDebug_info(DebugInfoBean debugInfoBean) {
            this.debug_info = debugInfoBean;
        }

        public String getModule_title() {
            return this.module_title;
        }

        public void setModule_title(String str) {
            this.module_title = str;
        }

        public List<SuggestionsBean> getSuggestions() {
            return this.suggestions;
        }

        public void setSuggestions(List<SuggestionsBean> list) {
            this.suggestions = list;
        }

        public List<UpdatedResourcesBean> getUpdated_resources() {
            return this.updated_resources;
        }

        public void setUpdated_resources(List<UpdatedResourcesBean> list) {
            this.updated_resources = list;
        }

        public List<?> getRequest_url() {
            return this.request_url;
        }

        public void setRequest_url(List<?> list) {
            this.request_url = list;
        }

        public OfflineSuggestion getOfflineSuggestion() {
            return this.offlineSuggestion;
        }

        public void setOfflineSuggestion(OfflineSuggestion offlineSuggestion) {
            this.offlineSuggestion = offlineSuggestion;
        }

        /* loaded from: classes3.dex */
        public static class CloudControlBean {
            private int card_fade_time_ms;
            private int card_position_refresh_time_ms;
            private int show_layout;
            private int with_double_quotation;

            public int getShow_layout() {
                return this.show_layout;
            }

            public void setShow_layout(int i) {
                this.show_layout = i;
            }

            public int getWith_double_quotation() {
                return this.with_double_quotation;
            }

            public void setWith_double_quotation(int i) {
                this.with_double_quotation = i;
            }

            public int getCard_fade_time_ms() {
                return this.card_fade_time_ms;
            }

            public void setCard_fade_time_ms(int i) {
                this.card_fade_time_ms = i;
            }

            public int getCard_position_refresh_time_ms() {
                return this.card_position_refresh_time_ms;
            }

            public void setCard_position_refresh_time_ms(int i) {
                this.card_position_refresh_time_ms = i;
            }
        }

        /* loaded from: classes3.dex */
        public static class DebugInfoBean {
            @SerializedName("ranker.elapsed.PRIMARY")
            private String _$RankerElapsedPRIMARY232;
            @SerializedName("ranker.version.PRIMARY")
            private String _$RankerVersionPRIMARY305;
            @SerializedName("base-ranker")
            private String baseranker;
            @SerializedName("expose-history")
            private String exposehistory;
            @SerializedName("history-entries")
            private String historyentries;
            @SerializedName("history-events")
            private String historyevents;
            private String merged;
            @SerializedName("primary-ranker")
            private String primaryranker;
            private String recalled;
            private String reodered;

            public String getPrimaryranker() {
                return this.primaryranker;
            }

            public void setPrimaryranker(String str) {
                this.primaryranker = str;
            }

            public String get_$RankerVersionPRIMARY305() {
                return this._$RankerVersionPRIMARY305;
            }

            public void set_$RankerVersionPRIMARY305(String str) {
                this._$RankerVersionPRIMARY305 = str;
            }

            public String getHistoryevents() {
                return this.historyevents;
            }

            public void setHistoryevents(String str) {
                this.historyevents = str;
            }

            public String getMerged() {
                return this.merged;
            }

            public void setMerged(String str) {
                this.merged = str;
            }

            public String getHistoryentries() {
                return this.historyentries;
            }

            public void setHistoryentries(String str) {
                this.historyentries = str;
            }

            public String getRecalled() {
                return this.recalled;
            }

            public void setRecalled(String str) {
                this.recalled = str;
            }

            public String get_$RankerElapsedPRIMARY232() {
                return this._$RankerElapsedPRIMARY232;
            }

            public void set_$RankerElapsedPRIMARY232(String str) {
                this._$RankerElapsedPRIMARY232 = str;
            }

            public String getReodered() {
                return this.reodered;
            }

            public void setReodered(String str) {
                this.reodered = str;
            }

            public String getExposehistory() {
                return this.exposehistory;
            }

            public void setExposehistory(String str) {
                this.exposehistory = str;
            }

            public String getBaseranker() {
                return this.baseranker;
            }

            public void setBaseranker(String str) {
                this.baseranker = str;
            }

            public String toString() {
                return Gsons.getGson().toJson(this);
            }
        }

        /* loaded from: classes3.dex */
        public static class OfflineSuggestion {
            private List<SuggestionsBean> function;
            private List<SuggestionsBean> guide_queries;

            public List<SuggestionsBean> getOfflineSuggestions() {
                return WakeGuide.joinLists(this.function, this.guide_queries);
            }

            public List<SuggestionsBean> getFunction() {
                return this.function;
            }

            public void setFunction(List<SuggestionsBean> list) {
                this.function = list;
            }

            public List<SuggestionsBean> getGuide_queries() {
                return this.guide_queries;
            }

            public void setGuide_queries(List<SuggestionsBean> list) {
                this.guide_queries = list;
            }
        }

        /* loaded from: classes3.dex */
        public static class SuggestionsBean {
            private CardColorBean card_color;
            private DebugInfoBeanX debug_info;
            private String domain;
            private String exe_pkg_name;
            private String headline_background;
            private String icon_url;
            private String id;
            private boolean is_cut_in;
            private int min_version;
            private String query;
            private String send_query;
            private String source;
            private String subtitle;
            private String suggest_query_type;
            private double suggest_score;
            private String ui_template;

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getQuery() {
                return this.query;
            }

            public void setQuery(String str) {
                this.query = str;
            }

            public String getSend_query() {
                return this.send_query;
            }

            public void setSend_query(String str) {
                this.send_query = str;
            }

            public String getExe_pkg_name() {
                return this.exe_pkg_name;
            }

            public void setExe_pkg_name(String str) {
                this.exe_pkg_name = str;
            }

            public int getMin_version() {
                return this.min_version;
            }

            public void setMin_version(int i) {
                this.min_version = i;
            }

            public String getDomain() {
                return this.domain;
            }

            public void setDomain(String str) {
                this.domain = str;
            }

            public double getSuggest_score() {
                return this.suggest_score;
            }

            public void setSuggest_score(double d) {
                this.suggest_score = d;
            }

            public String getSuggest_query_type() {
                return this.suggest_query_type;
            }

            public void setSuggest_query_type(String str) {
                this.suggest_query_type = str;
            }

            public String getIcon_url() {
                return this.icon_url;
            }

            public void setIcon_url(String str) {
                this.icon_url = str;
            }

            public String getSource() {
                return this.source;
            }

            public void setSource(String str) {
                this.source = str;
            }

            public String getUi_template() {
                return this.ui_template;
            }

            public void setUi_template(String str) {
                this.ui_template = str;
            }

            public CardColorBean getCard_color() {
                return this.card_color;
            }

            public void setCard_color(CardColorBean cardColorBean) {
                this.card_color = cardColorBean;
            }

            public DebugInfoBeanX getDebug_info() {
                return this.debug_info;
            }

            public void setDebug_info(DebugInfoBeanX debugInfoBeanX) {
                this.debug_info = debugInfoBeanX;
            }

            public String getHeadline_background() {
                return this.headline_background;
            }

            public void setHeadline_background(String str) {
                this.headline_background = str;
            }

            public boolean isIs_cut_in() {
                return this.is_cut_in;
            }

            public void setIs_cut_in(boolean z) {
                this.is_cut_in = z;
            }

            public String getSubtitle() {
                return this.subtitle;
            }

            public void setSubtitle(String str) {
                this.subtitle = str;
            }

            /* loaded from: classes3.dex */
            public static class CardColorBean {
                private String background_color;
                private String bubble_color;
                private String gradient_color;
                private String pressed_color;
                private String pressed_gradient_color;
                private String query_color;
                private String subtitle_color;

                public String getBackground_color() {
                    return this.background_color;
                }

                public void setBackground_color(String str) {
                    this.background_color = str;
                }

                public String getBubble_color() {
                    return this.bubble_color;
                }

                public void setBubble_color(String str) {
                    this.bubble_color = str;
                }

                public String getPressed_color() {
                    return this.pressed_color;
                }

                public void setPressed_color(String str) {
                    this.pressed_color = str;
                }

                public String getQuery_color() {
                    return this.query_color;
                }

                public void setQuery_color(String str) {
                    this.query_color = str;
                }

                public String getSubtitle_color() {
                    return this.subtitle_color;
                }

                public void setSubtitle_color(String str) {
                    this.subtitle_color = str;
                }

                public String getGradient_color() {
                    return this.gradient_color;
                }

                public void setGradient_color(String str) {
                    this.gradient_color = str;
                }

                public String getPressed_gradient_color() {
                    return this.pressed_gradient_color;
                }

                public void setPressed_gradient_color(String str) {
                    this.pressed_gradient_color = str;
                }
            }

            /* loaded from: classes3.dex */
            public static class DebugInfoBeanX {
                private String primary;
                private String queue;

                public String getQueue() {
                    return this.queue;
                }

                public void setQueue(String str) {
                    this.queue = str;
                }

                public String getPrimary() {
                    return this.primary;
                }

                public void setPrimary(String str) {
                    this.primary = str;
                }

                public String toString() {
                    return Gsons.getGson().toJson(this);
                }
            }
        }

        /* loaded from: classes3.dex */
        public static class UpdatedResourcesBean {
            private String content_url;
            private String name;
            private int version;
            private String wakeup_device_category;

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public int getVersion() {
                return this.version;
            }

            public void setVersion(int i) {
                this.version = i;
            }

            public String getContent_url() {
                return this.content_url;
            }

            public void setContent_url(String str) {
                this.content_url = str;
            }

            public String getWakeup_device_category() {
                return this.wakeup_device_category;
            }

            public void setWakeup_device_category(String str) {
                this.wakeup_device_category = str;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Device {
        private String app_id;
        private String device_id;

        public String getDevice_id() {
            return this.device_id;
        }

        public void setDevice_id(String str) {
            this.device_id = str;
        }

        public String getApp_id() {
            return this.app_id;
        }

        public void setApp_id(String str) {
            this.app_id = str;
        }

        public String toString() {
            return "{\"device_id\":\"" + this.device_id + "\", \"app_id\":\"" + this.app_id + "\"}";
        }
    }

    /* loaded from: classes3.dex */
    public static class DeviceStatus {
        private boolean is_kid_mode_on;

        public boolean isIs_kid_mode_on() {
            return this.is_kid_mode_on;
        }

        public void setIs_kid_mode_on(boolean z) {
            this.is_kid_mode_on = z;
        }
    }

    /* loaded from: classes3.dex */
    public static class UserInfo {
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class WakeRequestBody {
        private String app_name;
        private String app_page;
        private int app_version;
        private Device device;
        private DeviceStatus device_status;
        private String request_origin;
        private String today_impressions;
        String trace_id;
        private UserInfo user_info;

        public String getTrace_id() {
            return this.trace_id;
        }

        public void setTrace_id(String str) {
            this.trace_id = str;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public void setApp_name(String str) {
            this.app_name = str;
        }

        public int getApp_version() {
            return this.app_version;
        }

        public void setApp_version(int i) {
            this.app_version = i;
        }

        public String getRequest_origin() {
            return this.request_origin;
        }

        public void setRequest_origin(String str) {
            this.request_origin = str;
        }

        public String getApp_page() {
            return this.app_page;
        }

        public void setApp_page(String str) {
            this.app_page = str;
        }

        public UserInfo getUser_info() {
            return this.user_info;
        }

        public void setUser_info(UserInfo userInfo) {
            this.user_info = userInfo;
        }

        public void setToday_impressions(String str) {
            this.today_impressions = str;
        }

        public String getToday_impressions() {
            return this.today_impressions;
        }

        public DeviceStatus getDevice_status() {
            return this.device_status;
        }

        public void setDevice_status(DeviceStatus deviceStatus) {
            this.device_status = deviceStatus;
        }
    }

    public static List<AnswerBean.SuggestionsBean> joinLists(List<AnswerBean.SuggestionsBean> list, List<AnswerBean.SuggestionsBean> list2) {
        if (ContainerUtil.isEmpty(list)) {
            return list2;
        }
        if (ContainerUtil.isEmpty(list2)) {
            return list;
        }
        if (ContainerUtil.isEmpty(list) && ContainerUtil.isEmpty(list2)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(list.size() + list2.size());
        int i = 0;
        if (list.size() > list2.size()) {
            while (i < list2.size()) {
                arrayList.add(list.get(i));
                arrayList.add(list2.get(i));
                i++;
            }
            arrayList.addAll(list.subList(list2.size(), list.size()));
        } else if (list.size() < list2.size()) {
            while (i < list.size()) {
                arrayList.add(list.get(i));
                arrayList.add(list2.get(i));
                i++;
            }
            arrayList.addAll(list2.subList(list.size(), list2.size()));
        } else {
            while (i < list.size()) {
                arrayList.add(list.get(i));
                arrayList.add(list2.get(i));
                i++;
            }
        }
        return arrayList;
    }
}
