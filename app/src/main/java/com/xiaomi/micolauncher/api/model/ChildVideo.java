package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideoMediaInfo;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.course.KidCourseDataManager;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideo {
    public static final String BLOCK_AGE = "block_age";
    public static final String BLOCK_BANNER = "block_carousel_banner";
    public static final String BLOCK_GRID = "block_grid_button";
    public static final String BLOCK_TYPE = "block_type";
    private static final int DEFAULT_ITEM_SIZE = 5;
    private List<BlocksBean> blocks;
    private String fp;
    private String id;
    private ImagesBean images;
    private MetaBean meta;
    private StatBean stat;
    private int status;
    private String title;
    private UiTypeBean ui_type;
    private int ver;

    /* loaded from: classes3.dex */
    public static class VipStatus {
        public static final int RESULT_GET_SEVEN_DAY_VIP_SUCCEED = 0;
        public static final int RESULT_HAS_NOT_PURCHASED = 0;
        public static final int RESULT_HAS_PURCHASED = 1;
        public int status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getFp() {
        return this.fp;
    }

    public void setFp(String str) {
        this.fp = str;
    }

    public StatBean getStat() {
        return this.stat;
    }

    public void setStat(StatBean statBean) {
        this.stat = statBean;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public UiTypeBean getUi_type() {
        return this.ui_type;
    }

    public void setUi_type(UiTypeBean uiTypeBean) {
        this.ui_type = uiTypeBean;
    }

    public MetaBean getMeta() {
        return this.meta;
    }

    public void setMeta(MetaBean metaBean) {
        this.meta = metaBean;
    }

    public ImagesBean getImages() {
        return this.images;
    }

    public void setImages(ImagesBean imagesBean) {
        this.images = imagesBean;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int i) {
        this.ver = i;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public List<BlocksBean> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<BlocksBean> list) {
        this.blocks = list;
    }

    /* loaded from: classes3.dex */
    public static class StatBean {
        private String path;
        private String tp;

        public String getPath() {
            return this.path;
        }

        public void setPath(String str) {
            this.path = str;
        }

        public String getTp() {
            return this.tp;
        }

        public void setTp(String str) {
            this.tp = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class UiTypeBean {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class MetaBean {
        private String more;

        public String getMore() {
            return this.more;
        }

        public void setMore(String str) {
            this.more = str;
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
    public static class BlocksBean implements IBlockBean {
        private String area;
        private String id;
        private List<ItemsBean> items;
        private MetaBean meta;
        private StatBeanX stat;
        private String title;
        private UiTypeBeanX ui_type;
        private int ver;

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getBlockType() {
            return "video";
        }

        public BlocksBean(String str) {
            this.ui_type = new UiTypeBeanX();
            this.ui_type.setName(str);
            setUi_type(this.ui_type);
            this.items = new ArrayList();
            for (int i = 0; i < 5; i++) {
                ItemsBean itemsBean = new ItemsBean("", "", "");
                itemsBean.setTarget(new ItemsBean.TargetBean());
                this.items.add(itemsBean);
            }
        }

        public BlocksBean() {
        }

        public MetaBean getMeta() {
            if (this.meta == null) {
                this.meta = new MetaBean();
            }
            return this.meta;
        }

        public void setMeta(MetaBean metaBean) {
            this.meta = metaBean;
        }

        public StatBeanX getStat() {
            return this.stat;
        }

        public void setStat(StatBeanX statBeanX) {
            this.stat = statBeanX;
        }

        public int getVer() {
            return this.ver;
        }

        public void setVer(int i) {
            this.ver = i;
        }

        public UiTypeBeanX getUi_type() {
            return this.ui_type;
        }

        public void setUi_type(UiTypeBeanX uiTypeBeanX) {
            this.ui_type = uiTypeBeanX;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public List<ItemsBean> getItems() {
            return this.items;
        }

        public void setItems(List<ItemsBean> list) {
            this.items = list;
        }

        public String getArea() {
            return this.area;
        }

        public void setArea(String str) {
            this.area = str;
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getBlockTitle() {
            return getTitle();
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getUiType() {
            return this.ui_type.name;
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public List<? extends IListItem> getListItems() {
            return getItems();
        }

        /* loaded from: classes3.dex */
        public static class StatBeanX {
            private String traceid;

            public String getTraceid() {
                return this.traceid;
            }

            public void setTraceid(String str) {
                this.traceid = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class UiTypeBeanX {
            private int columns;
            private String name;
            private double ratio;
            private boolean unitary;

            public boolean isUnitary() {
                return this.unitary;
            }

            public void setUnitary(boolean z) {
                this.unitary = z;
            }

            public double getRatio() {
                return this.ratio;
            }

            public void setRatio(double d) {
                this.ratio = d;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public int getColumns() {
                return this.columns;
            }

            public void setColumns(int i) {
                this.columns = i;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class ItemsBean implements IListItem, BaseGroupListActivity.CategoryInterface, Serializable {
        public static final String TYPE_BUILTIN = "builtin";
        public static final String TYPE_FILTER = "filter";
        public static final String TYPE_GRID = "type_grid";
        public static final String TYPE_LIST = "type_list";
        public static final String UI_TYPE_DISPLAY = "display_item";
        public static final String UI_TYPE_VIP = "user_vip_item";
        private AdBean ad;
        private String androidIntent;
        private String content_desc;
        private String extra;
        @SerializedName("extra_info")
        private ExtraInfo extraInfo;
        private String id;
        private ImagesBeanX images;
        private String ns;
        private String pCode;
        private List<ProductBean> product;
        private List<String> productions;
        private StatBeanXX stat;
        private String sub_title;
        private TargetBean target;
        private String title;
        private String type;
        private UiTypeBeanXX ui_type;

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryType() {
            return "";
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTarget() {
            return "";
        }

        public ItemsBean(String str, String str2, String str3) {
            this(str, str2, str3, "");
        }

        public ItemsBean(String str, String str2, String str3, String str4) {
            this.title = str2;
            StatBeanXX statBeanXX = new StatBeanXX();
            statBeanXX.id = str;
            this.stat = statBeanXX;
            ImagesBeanX imagesBeanX = new ImagesBeanX();
            ImagesBeanX.PosterBean posterBean = new ImagesBeanX.PosterBean();
            posterBean.setUrl(str3);
            imagesBeanX.setPoster(posterBean);
            this.images = imagesBeanX;
            this.pCode = str4;
        }

        public ItemsBean() {
        }

        public ItemsBean(String str) {
            ExtraInfo extraInfo = new ExtraInfo();
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            extraInfo.setProducts(arrayList);
            this.extraInfo = extraInfo;
        }

        public String getTitle() {
            if (TextUtils.isEmpty(this.title)) {
                return this.content_desc;
            }
            return this.title;
        }

        public String getSubtitle() {
            return TextUtils.isEmpty(this.sub_title) ? "" : this.sub_title;
        }

        public String getType() {
            return TextUtils.isEmpty(this.type) ? TYPE_LIST : this.type;
        }

        public String getpCode() {
            return this.pCode;
        }

        public void setpCode(String str) {
            this.pCode = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getExtra() {
            return this.extra;
        }

        public void setExtra(String str) {
            this.extra = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public StatBeanXX getStat() {
            return this.stat;
        }

        public void setStat(StatBeanXX statBeanXX) {
            this.stat = statBeanXX;
        }

        public TargetBean getTarget() {
            return this.target;
        }

        public void setTarget(TargetBean targetBean) {
            this.target = targetBean;
        }

        public String getContent_desc() {
            return this.content_desc;
        }

        public void setContent_desc(String str) {
            this.content_desc = str;
        }

        public UiTypeBeanXX getUi_type() {
            return this.ui_type;
        }

        public void setUi_type(UiTypeBeanXX uiTypeBeanXX) {
            this.ui_type = uiTypeBeanXX;
        }

        public ImagesBeanX getImages() {
            return this.images;
        }

        public void setImages(ImagesBeanX imagesBeanX) {
            this.images = imagesBeanX;
        }

        public String getNs() {
            return this.ns;
        }

        public void setNs(String str) {
            this.ns = str;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public AdBean getAd() {
            return this.ad;
        }

        public void setAd(AdBean adBean) {
            this.ad = adBean;
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public long getCategoryId() {
            return Long.parseLong(this.stat.id);
        }

        @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity.CategoryInterface
        public String getCategoryName() {
            return this.title;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemImageUrl() {
            ImagesBeanX imagesBeanX = this.images;
            return (imagesBeanX == null || imagesBeanX.getPoster() == null) ? "" : this.images.getPoster().url;
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemTitle() {
            return getTitle();
        }

        @Override // com.xiaomi.micolauncher.api.model.IListItem
        public String getItemId() {
            return getStat().id;
        }

        public String getMediaId() {
            return getStat().getId();
        }

        public List<ProductBean> getProduct() {
            return this.product;
        }

        public void setProduct(List<ProductBean> list) {
            this.product = list;
        }

        public boolean isVipDisplayItem() {
            if (getUi_type() == null) {
                return false;
            }
            return UI_TYPE_VIP.equals(getUi_type().name);
        }

        public boolean isDisplayItem() {
            if (getUi_type() == null) {
                return false;
            }
            return UI_TYPE_DISPLAY.equals(getUi_type().name);
        }

        public boolean isFilterItem() {
            return TYPE_FILTER.equals(this.stat.getTp());
        }

        public boolean isBuiltinItem() {
            return TYPE_BUILTIN.equals(this.stat.getTp());
        }

        public String getAndroidIntent() {
            TargetBean targetBean = this.target;
            return (targetBean == null || targetBean.getParams() == null || this.target.getParams().getAndroid_intent() == null) ? "" : this.target.getParams().getAndroid_intent();
        }

        public String getRequestUrl() {
            return getTarget() == null ? "" : getTarget().getUrl();
        }

        public void setAndroidIntent(String str) {
            this.androidIntent = str;
        }

        public ItemsBean convertFromSource(ChildVideoMediaInfo.DataBean dataBean) {
            ItemsBean itemsBean = new ItemsBean();
            if (dataBean == null) {
                return itemsBean;
            }
            itemsBean.setImages(dataBean.getImages());
            itemsBean.setTitle(dataBean.getTitle());
            itemsBean.setStat(dataBean.getStat());
            itemsBean.setId(String.valueOf(dataBean.getId()));
            return itemsBean;
        }

        public ItemsBean convertFromSource(VideoItem videoItem) {
            if (videoItem == null) {
                return new ItemsBean();
            }
            return new ItemsBean(videoItem.getMediaId(), videoItem.getTitle(), videoItem.getHorizontalImage(), videoItem.getpCode());
        }

        public ExtraInfo getExtraInfo() {
            return this.extraInfo;
        }

        public void setExtraInfo(ExtraInfo extraInfo) {
            this.extraInfo = extraInfo;
        }

        public MiTvType getMiTvType() {
            if (getExtraInfo() == null || getExtraInfo().getProducts() == null) {
                this.productions = new ArrayList();
            } else {
                this.productions = getExtraInfo().getProducts();
            }
            addProducts();
            if (ContainerUtil.isEmpty(this.productions)) {
                this.productions.add(KidCourseDataManager.getManager().getCurrentTab().getPcode());
            }
            if (!ContainerUtil.hasData(this.productions)) {
                return MiTvType.CHILD_VIDEO;
            }
            if (this.productions.contains(ChildVideoDataManager.PCODE_ERTONG)) {
                return MiTvType.CHILD_VIDEO;
            }
            if (this.productions.contains(ChildVideoDataManager.PCODE_JUNIOR)) {
                return MiTvType.COURSE_JUNIOR;
            }
            if (this.productions.contains(ChildVideoDataManager.PCODE_HIGH)) {
                return MiTvType.COURSE_HIGH;
            }
            if (this.productions.contains(ChildVideoDataManager.PCODE_PRIMARY)) {
                return MiTvType.COURSE_PRIMARY;
            }
            return MiTvType.CHILD_VIDEO;
        }

        private void addProducts() {
            List<ProductBean> product = getProduct();
            if (ContainerUtil.hasData(product)) {
                for (ProductBean productBean : product) {
                    this.productions.add(productBean.getName());
                }
            }
            if (!TextUtils.isEmpty(getpCode())) {
                this.productions.add(getpCode());
            }
        }

        public boolean isChildVideo() {
            return MiTvType.CHILD_VIDEO.equals(getMiTvType());
        }

        public boolean isChildCourse() {
            return MiTvType.COURSE_PRIMARY.equals(getMiTvType()) || MiTvType.COURSE_HIGH.equals(getMiTvType()) || MiTvType.COURSE_JUNIOR.equals(getMiTvType());
        }

        public boolean isPrimaryVideo() {
            return MiTvType.COURSE_PRIMARY.equals(getMiTvType());
        }

        public boolean isJuniorVideo() {
            return MiTvType.COURSE_JUNIOR.equals(getMiTvType());
        }

        public boolean isHighVideo() {
            return MiTvType.COURSE_HIGH.equals(getMiTvType());
        }

        public boolean isFree() {
            if (getExtraInfo() == null) {
                return true;
            }
            return !getExtraInfo().is_charged;
        }

        /* loaded from: classes3.dex */
        public static class TargetBean {
            private String entity;
            private ParamsBean params;
            private String schema;
            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public String getEntity() {
                return this.entity;
            }

            public void setEntity(String str) {
                this.entity = str;
            }

            public ParamsBean getParams() {
                return this.params;
            }

            public void setParams(ParamsBean paramsBean) {
                this.params = paramsBean;
            }

            public String getSchema() {
                return this.schema;
            }

            public void setSchema(String str) {
                this.schema = str;
            }

            /* loaded from: classes3.dex */
            public static class ParamsBean {
                private String android_intent;

                public String getAndroid_intent() {
                    return this.android_intent;
                }

                public void setAndroid_intent(String str) {
                    this.android_intent = str;
                }
            }
        }

        /* loaded from: classes3.dex */
        public static class ExtraBean {
            private String category;
            private double douban_rating;
            private String focus;
            private List<String> genres;
            private boolean is_free;
            private boolean is_single;
            private String last_ci;
            private int playtime;
            private int premiere_date;
            private int setcount;
            private int setnow;
            private double xiaomi_rating;

            public boolean isIs_single() {
                return this.is_single;
            }

            public void setIs_single(boolean z) {
                this.is_single = z;
            }

            public String getFocus() {
                return this.focus;
            }

            public void setFocus(String str) {
                this.focus = str;
            }

            public boolean isIs_free() {
                return this.is_free;
            }

            public void setIs_free(boolean z) {
                this.is_free = z;
            }

            public int getPlaytime() {
                return this.playtime;
            }

            public void setPlaytime(int i) {
                this.playtime = i;
            }

            public int getPremiere_date() {
                return this.premiere_date;
            }

            public void setPremiere_date(int i) {
                this.premiere_date = i;
            }

            public int getSetnow() {
                return this.setnow;
            }

            public void setSetnow(int i) {
                this.setnow = i;
            }

            public String getCategory() {
                return this.category;
            }

            public void setCategory(String str) {
                this.category = str;
            }

            public int getSetcount() {
                return this.setcount;
            }

            public void setSetcount(int i) {
                this.setcount = i;
            }

            public String getLast_ci() {
                return this.last_ci;
            }

            public void setLast_ci(String str) {
                this.last_ci = str;
            }

            public double getDouban_rating() {
                return this.douban_rating;
            }

            public void setDouban_rating(double d) {
                this.douban_rating = d;
            }

            public double getXiaomi_rating() {
                return this.xiaomi_rating;
            }

            public void setXiaomi_rating(double d) {
                this.xiaomi_rating = d;
            }

            public List<String> getGenres() {
                return this.genres;
            }

            public void setGenres(List<String> list) {
                this.genres = list;
            }
        }

        /* loaded from: classes3.dex */
        public static class ProductBean {
            private BgImageBean bg_image;
            private int bssid;
            private String channel_id;
            private String channel_title;
            private int cpid;
            private String display_name;
            private int id;
            private int login;
            private String name;
            private List<PosterBean> poster;
            private int renewable;
            private int status;
            private int type;

            public int getStatus() {
                return this.status;
            }

            public void setStatus(int i) {
                this.status = i;
            }

            public String getChannel_title() {
                return this.channel_title;
            }

            public void setChannel_title(String str) {
                this.channel_title = str;
            }

            public int getBssid() {
                return this.bssid;
            }

            public void setBssid(int i) {
                this.bssid = i;
            }

            public BgImageBean getBg_image() {
                return this.bg_image;
            }

            public void setBg_image(BgImageBean bgImageBean) {
                this.bg_image = bgImageBean;
            }

            public int getCpid() {
                return this.cpid;
            }

            public void setCpid(int i) {
                this.cpid = i;
            }

            public int getId() {
                return this.id;
            }

            public void setId(int i) {
                this.id = i;
            }

            public int getRenewable() {
                return this.renewable;
            }

            public void setRenewable(int i) {
                this.renewable = i;
            }

            public String getDisplay_name() {
                return this.display_name;
            }

            public void setDisplay_name(String str) {
                this.display_name = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getChannel_id() {
                return this.channel_id;
            }

            public void setChannel_id(String str) {
                this.channel_id = str;
            }

            public int getLogin() {
                return this.login;
            }

            public void setLogin(int i) {
                this.login = i;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int i) {
                this.type = i;
            }

            public List<PosterBean> getPoster() {
                return this.poster;
            }

            public void setPoster(List<PosterBean> list) {
                this.poster = list;
            }

            /* loaded from: classes3.dex */
            public static class BgImageBean {
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

            /* loaded from: classes3.dex */
            public static class PosterBean {
                private String md5;
                private int type;
                private String url;

                public String getUrl() {
                    return this.url;
                }

                public void setUrl(String str) {
                    this.url = str;
                }

                public int getType() {
                    return this.type;
                }

                public void setType(int i) {
                    this.type = i;
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
        public static class UiTypeBeanXX {
            private String name;
            private String style;

            public String getStyle() {
                return this.style;
            }

            public void setStyle(String str) {
                this.style = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class AdBean {
            private boolean enable;

            public boolean isEnable() {
                return this.enable;
            }

            public void setEnable(boolean z) {
                this.enable = z;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class DueTime {
        private int code;
        private long data;
        private String message;

        public int getCode() {
            return this.code;
        }

        public void setCode(int i) {
            this.code = i;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String str) {
            this.message = str;
        }

        public long getDueTime() {
            return this.data;
        }

        public void setData(long j) {
            this.data = j;
        }
    }

    /* loaded from: classes3.dex */
    public static class ImagesBeanX {
        private PosterBean poster;

        public PosterBean getPoster() {
            return this.poster;
        }

        public void setPoster(PosterBean posterBean) {
            this.poster = posterBean;
        }

        /* loaded from: classes3.dex */
        public static class PosterBean {
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
    public static class StatBeanXX {
        private String id;
        private int position;
        private int style;
        private String tp;

        public int getPosition() {
            return this.position;
        }

        public void setPosition(int i) {
            this.position = i;
        }

        public int getStyle() {
            return this.style;
        }

        public void setStyle(int i) {
            this.style = i;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public String getTp() {
            return this.tp;
        }

        public void setTp(String str) {
            this.tp = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class ExtraInfo {
        private String category;
        private int episode_count;
        private int episode_now;
        private boolean is_charged;
        private boolean is_single;
        private int play_length;
        private List<String> products;
        private int season;

        public int getSeason() {
            return this.season;
        }

        public void setSeason(int i) {
            this.season = i;
        }

        public int getEpisode_count() {
            return this.episode_count;
        }

        public void setEpisode_count(int i) {
            this.episode_count = i;
        }

        public int getEpisode_now() {
            return this.episode_now;
        }

        public void setEpisode_now(int i) {
            this.episode_now = i;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String str) {
            this.category = str;
        }

        public boolean isIs_charged() {
            return this.is_charged;
        }

        public void setIs_charged(boolean z) {
            this.is_charged = z;
        }

        public boolean isIs_single() {
            return this.is_single;
        }

        public void setIs_single(boolean z) {
            this.is_single = z;
        }

        public int getPlay_length() {
            return this.play_length;
        }

        public void setPlay_length(int i) {
            this.play_length = i;
        }

        public List<String> getProducts() {
            return this.products;
        }

        public void setProducts(List<String> list) {
            this.products = list;
        }
    }

    /* loaded from: classes3.dex */
    public static class MiTvPromotion {
        private List<Promotion> data;
        private int status;

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public List<Promotion> getData() {
            return this.data;
        }

        public void setData(List<Promotion> list) {
            this.data = list;
        }

        /* loaded from: classes3.dex */
        public static class Promotion {
            public static final int PROMOTION_COURSE_HIGH = 1009;
            public static final int PROMOTION_COURSE_JUNIOR = 1009;
            public static final int PROMOTION_COURSE_PRIMARY = 1010;
            public static final int PROMOTION_MITV_VIDEO = 1001;
            private int promotion_id;
            private int promotion_type;
            private int status;

            public int getStatus() {
                return this.status;
            }

            public void setStatus(int i) {
                this.status = i;
            }

            public int getPromotion_id() {
                return this.promotion_id;
            }

            public void setPromotion_id(int i) {
                this.promotion_id = i;
            }

            public int getPromotion_type() {
                return this.promotion_type;
            }

            public void setPromotion_type(int i) {
                this.promotion_type = i;
            }
        }
    }

    /* loaded from: classes3.dex */
    public enum MiTvType {
        CHILD_VIDEO(ChildVideoDataManager.PCODE_ERTONG),
        COURSE_PRIMARY(ChildVideoDataManager.PCODE_PRIMARY),
        COURSE_JUNIOR(ChildVideoDataManager.PCODE_JUNIOR),
        COURSE_HIGH(ChildVideoDataManager.PCODE_HIGH);
        
        private String pCode;

        MiTvType(String str) {
            this.pCode = str;
        }

        public String getpCode() {
            return this.pCode;
        }

        public void setpCode(String str) {
            this.pCode = str;
        }
    }

    public static MiTvType parseCodeToType(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1630308248) {
            if (str.equals(ChildVideoDataManager.PCODE_ERTONG)) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode == -1558845957) {
            if (str.equals(ChildVideoDataManager.PCODE_JUNIOR)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 968776174) {
            if (hashCode == 2079548212 && str.equals(ChildVideoDataManager.PCODE_HIGH)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(ChildVideoDataManager.PCODE_PRIMARY)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return MiTvType.COURSE_PRIMARY;
            case 1:
                return MiTvType.COURSE_JUNIOR;
            case 2:
                return MiTvType.COURSE_HIGH;
            default:
                return MiTvType.CHILD_VIDEO;
        }
    }
}
