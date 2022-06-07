package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class ProductPrice {
    private String abVersion;
    private boolean anonymousSwitch;
    private List<PriceBean> data;
    private ImagesBean images;
    private SpeakBean speak;
    private int status;

    public SpeakBean getSpeak() {
        return this.speak;
    }

    public void setSpeak(SpeakBean speakBean) {
        this.speak = speakBean;
    }

    public String getAbVersion() {
        return this.abVersion;
    }

    public void setAbVersion(String str) {
        this.abVersion = str;
    }

    public ImagesBean getImages() {
        return this.images;
    }

    public void setImages(ImagesBean imagesBean) {
        this.images = imagesBean;
    }

    public boolean isAnonymousSwitch() {
        return this.anonymousSwitch;
    }

    public void setAnonymousSwitch(boolean z) {
        this.anonymousSwitch = z;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public List<PriceBean> getData() {
        return this.data;
    }

    public void setData(List<PriceBean> list) {
        this.data = list;
    }

    /* loaded from: classes3.dex */
    public static class SpeakBean {
        private String content;
        private boolean just_by_player;
        private boolean switcher;
        private String video_url;

        public boolean isSwitcher() {
            return this.switcher;
        }

        public void setSwitcher(boolean z) {
            this.switcher = z;
        }

        public boolean isJust_by_player() {
            return this.just_by_player;
        }

        public void setJust_by_player(boolean z) {
            this.just_by_player = z;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public String getVideo_url() {
            return this.video_url;
        }

        public void setVideo_url(String str) {
            this.video_url = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class ImagesBean {
        private BackgroundBean background;

        public BackgroundBean getBackground() {
            return this.background;
        }

        public void setBackground(BackgroundBean backgroundBean) {
            this.background = backgroundBean;
        }

        /* loaded from: classes3.dex */
        public static class BackgroundBean {
            private String md5;
            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public String getMd5() {
                return this.md5;
            }

            public void setMd5(String str) {
                this.md5 = str;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class PriceBean {
        private CpData cpData;
        private String cp_data;
        private boolean isRenew;
        private String orig_price;
        private List<PayTypeListBean> pay_type_list;
        private int priority;
        private String product_code;
        private String product_id;
        private String product_name;
        private String promotion_desc;
        private String real_price;
        private String type_id;
        private String unit_desc;

        public boolean isRenew() {
            return this.isRenew;
        }

        public void setRenew(boolean z) {
            this.isRenew = z;
        }

        public CpData getCpData() {
            return this.cpData;
        }

        public void setCpData(CpData cpData) {
            this.cpData = cpData;
        }

        public String getType_id() {
            return this.type_id;
        }

        public void setType_id(String str) {
            this.type_id = str;
        }

        public List<PayTypeListBean> getPay_type_list() {
            return this.pay_type_list;
        }

        public void setPay_type_list(List<PayTypeListBean> list) {
            this.pay_type_list = list;
        }

        /* loaded from: classes3.dex */
        public static class CpData {
            private String desc;
            private String lab_bgc;
            private String lab_tc;
            private String lab_text;
            private String sub_title;
            private int type;
            private int ui_origin_price;

            public int getUi_origin_price() {
                return this.ui_origin_price;
            }

            public void setUi_origin_price(int i) {
                this.ui_origin_price = i;
            }

            public String getLab_text() {
                return this.lab_text;
            }

            public void setLab_text(String str) {
                this.lab_text = str;
            }

            public String getLab_bgc() {
                return this.lab_bgc;
            }

            public void setLab_bgc(String str) {
                this.lab_bgc = str;
            }

            public String getLab_tc() {
                return this.lab_tc;
            }

            public void setLab_tc(String str) {
                this.lab_tc = str;
            }

            public String getSub_title() {
                return this.sub_title;
            }

            public void setSub_title(String str) {
                this.sub_title = str;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int i) {
                this.type = i;
            }

            public String getDesc() {
                return this.desc;
            }

            public void setDesc(String str) {
                this.desc = str;
            }
        }

        public int getPriority() {
            return this.priority;
        }

        public void setPriority(int i) {
            this.priority = i;
        }

        public String getProduct_id() {
            return this.product_id;
        }

        public void setProduct_id(String str) {
            this.product_id = str;
        }

        public String getProduct_code() {
            return this.product_code;
        }

        public void setProduct_code(String str) {
            this.product_code = str;
        }

        public String getProduct_name() {
            return this.product_name;
        }

        public void setProduct_name(String str) {
            this.product_name = str;
        }

        public String getOrig_price() {
            return this.orig_price;
        }

        public void setOrig_price(String str) {
            this.orig_price = str;
        }

        public String getReal_price() {
            return this.real_price;
        }

        public void setReal_price(String str) {
            this.real_price = str;
        }

        public String getCp_data() {
            return this.cp_data;
        }

        public void setCp_data(String str) {
            this.cp_data = str;
        }

        public String getUnit_desc() {
            return this.unit_desc;
        }

        public void setUnit_desc(String str) {
            this.unit_desc = str;
        }

        public String getPromotion_desc() {
            return this.promotion_desc;
        }

        public void setPromotion_desc(String str) {
            this.promotion_desc = str;
        }

        /* loaded from: classes3.dex */
        public static class PayTypeListBean {
            private int id;
            private String name;
            @SerializedName("priority")
            private int priorityX;

            public int getId() {
                return this.id;
            }

            public void setId(int i) {
                this.id = i;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public int getPriorityX() {
                return this.priorityX;
            }

            public void setPriorityX(int i) {
                this.priorityX = i;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class PriceTag {
        private DataBean data;
        private String msg;
        private int status;

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String str) {
            this.msg = str;
        }

        public DataBean getData() {
            return this.data;
        }

        public void setData(DataBean dataBean) {
            this.data = dataBean;
        }

        /* loaded from: classes3.dex */
        public static class DataBean {
            private String callback;
            private String shortKey;

            public String getShortKey() {
                return this.shortKey;
            }

            public void setShortKey(String str) {
                this.shortKey = str;
            }

            public String getCallback() {
                return this.callback;
            }

            public void setCallback(String str) {
                this.callback = str;
            }
        }
    }
}
