package com.xiaomi.micolauncher.api.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class AudioDiscoveryPage {

    /* loaded from: classes3.dex */
    public static class TabListBean implements Parcelable {
        public static final Parcelable.Creator<TabListBean> CREATOR = new Parcelable.Creator<TabListBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.TabListBean.1
            @Override // android.os.Parcelable.Creator
            public TabListBean createFromParcel(Parcel parcel) {
                return new TabListBean(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public TabListBean[] newArray(int i) {
                return new TabListBean[i];
            }
        };
        private List<TabListItemBean> tabList;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected TabListBean(Parcel parcel) {
            this.tabList = parcel.createTypedArrayList(TabListItemBean.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.tabList);
        }

        public List<TabListItemBean> getTabList() {
            return this.tabList;
        }

        public void setTabList(List<TabListItemBean> list) {
            this.tabList = list;
        }

        /* loaded from: classes3.dex */
        public static class TabListItemBean implements Parcelable {
            public static final Parcelable.Creator<TabListItemBean> CREATOR = new Parcelable.Creator<TabListItemBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.TabListBean.TabListItemBean.1
                @Override // android.os.Parcelable.Creator
                public TabListItemBean createFromParcel(Parcel parcel) {
                    return new TabListItemBean(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public TabListItemBean[] newArray(int i) {
                    return new TabListItemBean[i];
                }
            };
            private String desc;
            private int editable;
            public boolean isSelected;
            private String name;
            private int type;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public TabListItemBean() {
            }

            protected TabListItemBean(Parcel parcel) {
                this.name = parcel.readString();
                this.desc = parcel.readString();
                this.type = parcel.readInt();
                this.editable = parcel.readInt();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.name);
                parcel.writeString(this.desc);
                parcel.writeInt(this.type);
                parcel.writeInt(this.editable);
            }

            public String getName() {
                String str = this.name;
                return str == null ? "" : str;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getDesc() {
                String str = this.desc;
                return str == null ? "" : str;
            }

            public void setDesc(String str) {
                this.desc = str;
            }

            public int getType() {
                return this.type;
            }

            public void setType(int i) {
                this.type = i;
            }

            public int getEditable() {
                return this.editable;
            }

            public void setEditable(int i) {
                this.editable = i;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Flow implements Parcelable {
        public static final Parcelable.Creator<Flow> CREATOR = new Parcelable.Creator<Flow>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.1
            @Override // android.os.Parcelable.Creator
            public Flow createFromParcel(Parcel parcel) {
                return new Flow(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public Flow[] newArray(int i) {
                return new Flow[i];
            }
        };
        private List<BlocksBean> blocks;
        private FlowStatBean flowStat;
        private FlowUiTypeBean flowUiType;
        private String fp;
        private String id;
        private MetaBean meta;
        private int status;
        private String title;
        private int ver;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected Flow(Parcel parcel) {
            this.status = parcel.readInt();
            this.fp = parcel.readString();
            this.flowStat = (FlowStatBean) parcel.readParcelable(FlowStatBean.class.getClassLoader());
            this.title = parcel.readString();
            this.meta = (MetaBean) parcel.readParcelable(MetaBean.class.getClassLoader());
            this.flowUiType = (FlowUiTypeBean) parcel.readParcelable(FlowUiTypeBean.class.getClassLoader());
            this.id = parcel.readString();
            this.ver = parcel.readInt();
            this.blocks = parcel.createTypedArrayList(BlocksBean.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.status);
            parcel.writeString(this.fp);
            parcel.writeParcelable(this.flowStat, i);
            parcel.writeString(this.title);
            parcel.writeParcelable(this.meta, i);
            parcel.writeParcelable(this.flowUiType, i);
            parcel.writeString(this.id);
            parcel.writeInt(this.ver);
            parcel.writeTypedList(this.blocks);
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

        public FlowStatBean getFlowStat() {
            return this.flowStat;
        }

        public void setFlowStat(FlowStatBean flowStatBean) {
            this.flowStat = flowStatBean;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public MetaBean getMeta() {
            return this.meta;
        }

        public void setMeta(MetaBean metaBean) {
            this.meta = metaBean;
        }

        public FlowUiTypeBean getFlowUiType() {
            return this.flowUiType;
        }

        public void setFlowUiType(FlowUiTypeBean flowUiTypeBean) {
            this.flowUiType = flowUiTypeBean;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String str) {
            this.id = str;
        }

        public int getVer() {
            return this.ver;
        }

        public void setVer(int i) {
            this.ver = i;
        }

        public List<BlocksBean> getBlocks() {
            return this.blocks;
        }

        public void setBlocks(List<BlocksBean> list) {
            this.blocks = list;
        }

        /* loaded from: classes3.dex */
        public static class FlowStatBean implements Parcelable {
            public static final Parcelable.Creator<FlowStatBean> CREATOR = new Parcelable.Creator<FlowStatBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.FlowStatBean.1
                @Override // android.os.Parcelable.Creator
                public FlowStatBean createFromParcel(Parcel parcel) {
                    return new FlowStatBean(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public FlowStatBean[] newArray(int i) {
                    return new FlowStatBean[i];
                }
            };
            private String path;
            private String tp;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            protected FlowStatBean(Parcel parcel) {
                this.path = parcel.readString();
                this.tp = parcel.readString();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.path);
                parcel.writeString(this.tp);
            }

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
        public static class MetaBean implements Parcelable {
            public static final Parcelable.Creator<MetaBean> CREATOR = new Parcelable.Creator<MetaBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.MetaBean.1
                @Override // android.os.Parcelable.Creator
                public MetaBean createFromParcel(Parcel parcel) {
                    return new MetaBean(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public MetaBean[] newArray(int i) {
                    return new MetaBean[i];
                }
            };
            private String more;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            protected MetaBean(Parcel parcel) {
                this.more = parcel.readString();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.more);
            }

            public String getMore() {
                return this.more;
            }

            public void setMore(String str) {
                this.more = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class FlowUiTypeBean implements Parcelable {
            public static final Parcelable.Creator<FlowUiTypeBean> CREATOR = new Parcelable.Creator<FlowUiTypeBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.FlowUiTypeBean.1
                @Override // android.os.Parcelable.Creator
                public FlowUiTypeBean createFromParcel(Parcel parcel) {
                    return new FlowUiTypeBean(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public FlowUiTypeBean[] newArray(int i) {
                    return new FlowUiTypeBean[i];
                }
            };
            private String name;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            protected FlowUiTypeBean(Parcel parcel) {
                this.name = parcel.readString();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.name);
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class BlocksBean implements Parcelable {
            public static final Parcelable.Creator<BlocksBean> CREATOR = new Parcelable.Creator<BlocksBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.1
                @Override // android.os.Parcelable.Creator
                public BlocksBean createFromParcel(Parcel parcel) {
                    return new BlocksBean(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public BlocksBean[] newArray(int i) {
                    return new BlocksBean[i];
                }
            };
            private BlockStatBean blockStat;
            private BlockUiTypeBean blockUiType;
            private String description;
            private boolean displayMore;
            private long id;
            private List<ItemsBean> items;
            private String title;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            protected BlocksBean(Parcel parcel) {
                this.blockStat = (BlockStatBean) parcel.readParcelable(BlockStatBean.class.getClassLoader());
                this.blockUiType = (BlockUiTypeBean) parcel.readParcelable(BlockUiTypeBean.class.getClassLoader());
                this.title = parcel.readString();
                this.description = parcel.readString();
                this.id = parcel.readLong();
                this.displayMore = parcel.readByte() != 0;
                this.items = parcel.createTypedArrayList(ItemsBean.CREATOR);
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeParcelable(this.blockStat, i);
                parcel.writeParcelable(this.blockUiType, i);
                parcel.writeString(this.title);
                parcel.writeString(this.description);
                parcel.writeLong(this.id);
                parcel.writeByte(this.displayMore ? (byte) 1 : (byte) 0);
                parcel.writeTypedList(this.items);
            }

            public BlockStatBean getBlockStat() {
                return this.blockStat;
            }

            public void setBlockStat(BlockStatBean blockStatBean) {
                this.blockStat = blockStatBean;
            }

            public BlockUiTypeBean getBlockUiType() {
                return this.blockUiType;
            }

            public void setBlockUiType(BlockUiTypeBean blockUiTypeBean) {
                this.blockUiType = blockUiTypeBean;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public String getDescription() {
                return this.description;
            }

            public void setDescription(String str) {
                this.description = str;
            }

            public long getId() {
                return this.id;
            }

            public void setId(long j) {
                this.id = j;
            }

            public boolean isDisplayMore() {
                return this.displayMore;
            }

            public void setDisplayMore(boolean z) {
                this.displayMore = z;
            }

            public List<ItemsBean> getItems() {
                return this.items;
            }

            public void setItems(List<ItemsBean> list) {
                this.items = list;
            }

            /* loaded from: classes3.dex */
            public static class BlockStatBean implements Parcelable {
                public static final Parcelable.Creator<BlockStatBean> CREATOR = new Parcelable.Creator<BlockStatBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.BlockStatBean.1
                    @Override // android.os.Parcelable.Creator
                    public BlockStatBean createFromParcel(Parcel parcel) {
                        return new BlockStatBean(parcel);
                    }

                    @Override // android.os.Parcelable.Creator
                    public BlockStatBean[] newArray(int i) {
                        return new BlockStatBean[i];
                    }
                };
                private String traceid;

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                protected BlockStatBean(Parcel parcel) {
                    this.traceid = parcel.readString();
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeString(this.traceid);
                }

                public String getTraceid() {
                    return this.traceid;
                }

                public void setTraceid(String str) {
                    this.traceid = str;
                }
            }

            /* loaded from: classes3.dex */
            public static class BlockUiTypeBean implements Parcelable {
                public static final Parcelable.Creator<BlockUiTypeBean> CREATOR = new Parcelable.Creator<BlockUiTypeBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.BlockUiTypeBean.1
                    @Override // android.os.Parcelable.Creator
                    public BlockUiTypeBean createFromParcel(Parcel parcel) {
                        return new BlockUiTypeBean(parcel);
                    }

                    @Override // android.os.Parcelable.Creator
                    public BlockUiTypeBean[] newArray(int i) {
                        return new BlockUiTypeBean[i];
                    }
                };
                private int columns;
                private String name;
                private int ratio;
                private boolean unitary;

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                protected BlockUiTypeBean(Parcel parcel) {
                    this.unitary = parcel.readByte() != 0;
                    this.ratio = parcel.readInt();
                    this.name = parcel.readString();
                    this.columns = parcel.readInt();
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeByte(this.unitary ? (byte) 1 : (byte) 0);
                    parcel.writeInt(this.ratio);
                    parcel.writeString(this.name);
                    parcel.writeInt(this.columns);
                }

                public boolean isUnitary() {
                    return this.unitary;
                }

                public void setUnitary(boolean z) {
                    this.unitary = z;
                }

                public int getRatio() {
                    return this.ratio;
                }

                public void setRatio(int i) {
                    this.ratio = i;
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

            /* loaded from: classes3.dex */
            public static class ItemsBean implements Parcelable {
                public static final Parcelable.Creator<ItemsBean> CREATOR = new Parcelable.Creator<ItemsBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.1
                    @Override // android.os.Parcelable.Creator
                    public ItemsBean createFromParcel(Parcel parcel) {
                        return new ItemsBean(parcel);
                    }

                    @Override // android.os.Parcelable.Creator
                    public ItemsBean[] newArray(int i) {
                        return new ItemsBean[i];
                    }
                };
                private static final int DATA_ITEMS_BEAN = 0;
                private static final int DATA_ORDER_ORDERINFO = 2;
                private static final int DATA_STATION_ITEM = 1;
                private AdBean ad;
                private String blockTitle;
                private int dataType = 0;
                private String id;
                private ImagesBean images;
                private ItemStatBean itemStat;
                private ItemUiTypeBean itemUiType;
                private String ns;
                private String origin;
                private int playCount;
                private int saleType;
                private String stationId;
                private String target;
                private String title;
                private int typeId;

                @Target({ElementType.FIELD, ElementType.PARAMETER})
                @Retention(RetentionPolicy.SOURCE)
                /* loaded from: classes3.dex */
                private @interface DATA_TYPE {
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (!(obj instanceof ItemsBean)) {
                        return false;
                    }
                    ItemsBean itemsBean = (ItemsBean) obj;
                    return this.typeId == itemsBean.typeId && Objects.equals(this.target, itemsBean.target) && Objects.equals(this.title, itemsBean.title) && Objects.equals(this.images, itemsBean.images) && Objects.equals(this.id, itemsBean.id) && Objects.equals(this.blockTitle, itemsBean.blockTitle) && Objects.equals(this.origin, itemsBean.origin) && Objects.equals(this.stationId, itemsBean.stationId);
                }

                public int hashCode() {
                    return Objects.hash(this.target, this.title, this.images, this.id, this.blockTitle, Integer.valueOf(this.typeId), this.origin, this.stationId);
                }

                public ItemsBean() {
                }

                public ItemsBean(String str) {
                    this.blockTitle = str;
                }

                protected ItemsBean(Parcel parcel) {
                    this.target = parcel.readString();
                    this.title = parcel.readString();
                    this.itemStat = (ItemStatBean) parcel.readParcelable(ItemStatBean.class.getClassLoader());
                    this.images = (ImagesBean) parcel.readParcelable(ImagesBean.class.getClassLoader());
                    this.ns = parcel.readString();
                    this.ad = (AdBean) parcel.readParcelable(AdBean.class.getClassLoader());
                    this.id = parcel.readString();
                    this.itemUiType = (ItemUiTypeBean) parcel.readParcelable(ItemUiTypeBean.class.getClassLoader());
                    this.playCount = parcel.readInt();
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeString(this.target);
                    parcel.writeString(this.title);
                    parcel.writeParcelable(this.itemStat, i);
                    parcel.writeParcelable(this.images, i);
                    parcel.writeString(this.ns);
                    parcel.writeParcelable(this.ad, i);
                    parcel.writeString(this.id);
                    parcel.writeParcelable(this.itemUiType, i);
                    parcel.writeInt(this.playCount);
                }

                public String getTarget() {
                    return this.target;
                }

                public void setTarget(String str) {
                    this.target = str;
                }

                public String getTitle() {
                    return this.title;
                }

                public void setTitle(String str) {
                    this.title = str;
                }

                public ItemStatBean getItemStat() {
                    return this.itemStat;
                }

                public void setItemStat(ItemStatBean itemStatBean) {
                    this.itemStat = itemStatBean;
                }

                public ImagesBean getImages() {
                    return this.images;
                }

                public void setImages(ImagesBean imagesBean) {
                    this.images = imagesBean;
                }

                public String getNs() {
                    return this.ns;
                }

                public void setNs(String str) {
                    this.ns = str;
                }

                public AdBean getAd() {
                    return this.ad;
                }

                public void setAd(AdBean adBean) {
                    this.ad = adBean;
                }

                public String getId() {
                    return this.id;
                }

                public void setId(String str) {
                    this.id = str;
                }

                public ItemUiTypeBean getItemUiType() {
                    return this.itemUiType;
                }

                public void setItemUiType(ItemUiTypeBean itemUiTypeBean) {
                    this.itemUiType = itemUiTypeBean;
                }

                public int getPlayCount() {
                    return this.playCount;
                }

                public void setPlayCount(int i) {
                    this.playCount = i;
                }

                public String getBlockTitle() {
                    return this.blockTitle;
                }

                public boolean isBlockTitle() {
                    return !TextUtils.isEmpty(this.blockTitle);
                }

                public int getTypeId() {
                    return this.typeId;
                }

                public void setTypeId(int i) {
                    this.typeId = i;
                }

                public String getOrigin() {
                    String str = this.origin;
                    return str == null ? "" : str;
                }

                public void setOrigin(String str) {
                    this.origin = str;
                }

                public String getStationId() {
                    String str = this.stationId;
                    return str == null ? "" : str;
                }

                public void setStationId(String str) {
                    this.stationId = str;
                }

                public int getSaleType() {
                    if (this.dataType == 1 && !TextUtils.isEmpty(this.target)) {
                        try {
                            String queryParameter = Uri.parse(this.target).getQueryParameter("saleType");
                            if (TextUtils.isEmpty(queryParameter)) {
                                return 0;
                            }
                            return Integer.parseInt(queryParameter);
                        } catch (UnsupportedOperationException e) {
                            L.audiobook.d("ItemsBean.target uri解析异常:%s", e);
                        } catch (Exception e2) {
                            L.audiobook.d("ItemsBean.target uri解析异常a:%s", e2);
                        }
                    }
                    return this.saleType;
                }

                public void setSaleType(int i) {
                    this.saleType = i;
                }

                public boolean isFree() {
                    return getSaleType() <= 0;
                }

                public int getDataType() {
                    return this.dataType;
                }

                public void setDataType(int i) {
                    this.dataType = i;
                }

                public static ItemsBean build(Station.Item item) {
                    ItemsBean itemsBean = new ItemsBean();
                    itemsBean.setOrigin(item.getOrigin());
                    itemsBean.setTarget(item.getTarget());
                    itemsBean.setTypeId(item.getTypeId());
                    itemsBean.setStationId(item.getId());
                    itemsBean.setSaleType(item.getSaleType());
                    itemsBean.setDataType(1);
                    ImagesBean imagesBean = new ImagesBean();
                    ImagesBean.PosterBean posterBean = new ImagesBean.PosterBean();
                    posterBean.setUrl(item.getCover());
                    imagesBean.setPoster(posterBean);
                    itemsBean.setImages(imagesBean);
                    itemsBean.setTitle(item.getTitle());
                    return itemsBean;
                }

                public static ItemsBean build(Order.OrderInfo orderInfo) {
                    ItemsBean itemsBean = new ItemsBean();
                    itemsBean.setOrigin(orderInfo.origin);
                    itemsBean.setTarget(orderInfo.action);
                    itemsBean.setTypeId(orderInfo.getTypeId(orderInfo.type));
                    itemsBean.setStationId(orderInfo.productId);
                    itemsBean.setSaleType(1);
                    itemsBean.setDataType(2);
                    ImagesBean imagesBean = new ImagesBean();
                    ImagesBean.PosterBean posterBean = new ImagesBean.PosterBean();
                    posterBean.setUrl(orderInfo.pictureUrl);
                    imagesBean.setPoster(posterBean);
                    itemsBean.setImages(imagesBean);
                    itemsBean.setTitle(orderInfo.productName);
                    return itemsBean;
                }

                /* loaded from: classes3.dex */
                public static class ItemStatBean implements Parcelable {
                    public static final Parcelable.Creator<ItemStatBean> CREATOR = new Parcelable.Creator<ItemStatBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.ItemStatBean.1
                        @Override // android.os.Parcelable.Creator
                        public ItemStatBean createFromParcel(Parcel parcel) {
                            return new ItemStatBean(parcel);
                        }

                        @Override // android.os.Parcelable.Creator
                        public ItemStatBean[] newArray(int i) {
                            return new ItemStatBean[i];
                        }
                    };
                    private long id;
                    private int position;
                    private int style;
                    private String tp;

                    @Override // android.os.Parcelable
                    public int describeContents() {
                        return 0;
                    }

                    protected ItemStatBean(Parcel parcel) {
                        this.position = parcel.readInt();
                        this.style = parcel.readInt();
                        this.id = parcel.readLong();
                        this.tp = parcel.readString();
                    }

                    @Override // android.os.Parcelable
                    public void writeToParcel(Parcel parcel, int i) {
                        parcel.writeInt(this.position);
                        parcel.writeInt(this.style);
                        parcel.writeLong(this.id);
                        parcel.writeString(this.tp);
                    }

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

                    public long getId() {
                        return this.id;
                    }

                    public void setId(long j) {
                        this.id = j;
                    }

                    public String getTp() {
                        return this.tp;
                    }

                    public void setTp(String str) {
                        this.tp = str;
                    }
                }

                /* loaded from: classes3.dex */
                public static class ImagesBean implements Parcelable {
                    public static final Parcelable.Creator<ImagesBean> CREATOR = new Parcelable.Creator<ImagesBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.ImagesBean.1
                        @Override // android.os.Parcelable.Creator
                        public ImagesBean createFromParcel(Parcel parcel) {
                            return new ImagesBean(parcel);
                        }

                        @Override // android.os.Parcelable.Creator
                        public ImagesBean[] newArray(int i) {
                            return new ImagesBean[i];
                        }
                    };
                    private PosterBean poster;

                    @Override // android.os.Parcelable
                    public int describeContents() {
                        return 0;
                    }

                    public ImagesBean() {
                    }

                    protected ImagesBean(Parcel parcel) {
                        this.poster = (PosterBean) parcel.readParcelable(PosterBean.class.getClassLoader());
                    }

                    @Override // android.os.Parcelable
                    public void writeToParcel(Parcel parcel, int i) {
                        parcel.writeParcelable(this.poster, i);
                    }

                    public boolean equals(Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (!(obj instanceof ImagesBean)) {
                            return false;
                        }
                        return Objects.equals(this.poster, ((ImagesBean) obj).poster);
                    }

                    public int hashCode() {
                        return Objects.hash(this.poster);
                    }

                    public PosterBean getPoster() {
                        return this.poster;
                    }

                    public void setPoster(PosterBean posterBean) {
                        this.poster = posterBean;
                    }

                    /* loaded from: classes3.dex */
                    public static class PosterBean implements Parcelable {
                        public static final Parcelable.Creator<PosterBean> CREATOR = new Parcelable.Creator<PosterBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.ImagesBean.PosterBean.1
                            @Override // android.os.Parcelable.Creator
                            public PosterBean createFromParcel(Parcel parcel) {
                                return new PosterBean(parcel);
                            }

                            @Override // android.os.Parcelable.Creator
                            public PosterBean[] newArray(int i) {
                                return new PosterBean[i];
                            }
                        };
                        private String md5;
                        private String url;

                        @Override // android.os.Parcelable
                        public int describeContents() {
                            return 0;
                        }

                        public PosterBean() {
                        }

                        protected PosterBean(Parcel parcel) {
                            this.url = parcel.readString();
                            this.md5 = parcel.readString();
                        }

                        public boolean equals(Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (!(obj instanceof PosterBean)) {
                                return false;
                            }
                            return Objects.equals(this.url, ((PosterBean) obj).url);
                        }

                        public int hashCode() {
                            return Objects.hash(this.url);
                        }

                        @Override // android.os.Parcelable
                        public void writeToParcel(Parcel parcel, int i) {
                            parcel.writeString(this.url);
                            parcel.writeString(this.md5);
                        }

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
                public static class AdBean implements Parcelable {
                    public static final Parcelable.Creator<AdBean> CREATOR = new Parcelable.Creator<AdBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.AdBean.1
                        @Override // android.os.Parcelable.Creator
                        public AdBean createFromParcel(Parcel parcel) {
                            return new AdBean(parcel);
                        }

                        @Override // android.os.Parcelable.Creator
                        public AdBean[] newArray(int i) {
                            return new AdBean[i];
                        }
                    };
                    private boolean enable;

                    @Override // android.os.Parcelable
                    public int describeContents() {
                        return 0;
                    }

                    protected AdBean(Parcel parcel) {
                        this.enable = parcel.readByte() != 0;
                    }

                    @Override // android.os.Parcelable
                    public void writeToParcel(Parcel parcel, int i) {
                        parcel.writeByte(this.enable ? (byte) 1 : (byte) 0);
                    }

                    public boolean isEnable() {
                        return this.enable;
                    }

                    public void setEnable(boolean z) {
                        this.enable = z;
                    }
                }

                /* loaded from: classes3.dex */
                public static class ItemUiTypeBean implements Parcelable {
                    public static final Parcelable.Creator<ItemUiTypeBean> CREATOR = new Parcelable.Creator<ItemUiTypeBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.ItemUiTypeBean.1
                        @Override // android.os.Parcelable.Creator
                        public ItemUiTypeBean createFromParcel(Parcel parcel) {
                            return new ItemUiTypeBean(parcel);
                        }

                        @Override // android.os.Parcelable.Creator
                        public ItemUiTypeBean[] newArray(int i) {
                            return new ItemUiTypeBean[i];
                        }
                    };
                    private String name;
                    private PosBean pos;

                    @Override // android.os.Parcelable
                    public int describeContents() {
                        return 0;
                    }

                    protected ItemUiTypeBean(Parcel parcel) {
                        this.name = parcel.readString();
                        this.pos = (PosBean) parcel.readParcelable(PosBean.class.getClassLoader());
                    }

                    @Override // android.os.Parcelable
                    public void writeToParcel(Parcel parcel, int i) {
                        parcel.writeString(this.name);
                        parcel.writeParcelable(this.pos, i);
                    }

                    public String getName() {
                        return this.name;
                    }

                    public void setName(String str) {
                        this.name = str;
                    }

                    public PosBean getPos() {
                        return this.pos;
                    }

                    public void setPos(PosBean posBean) {
                        this.pos = posBean;
                    }

                    /* loaded from: classes3.dex */
                    public static class PosBean implements Parcelable {
                        public static final Parcelable.Creator<PosBean> CREATOR = new Parcelable.Creator<PosBean>() { // from class: com.xiaomi.micolauncher.api.model.AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.ItemUiTypeBean.PosBean.1
                            @Override // android.os.Parcelable.Creator
                            public PosBean createFromParcel(Parcel parcel) {
                                return new PosBean(parcel);
                            }

                            @Override // android.os.Parcelable.Creator
                            public PosBean[] newArray(int i) {
                                return new PosBean[i];
                            }
                        };
                        private int h;
                        private int w;
                        private int x;
                        private int y;

                        @Override // android.os.Parcelable
                        public int describeContents() {
                            return 0;
                        }

                        protected PosBean(Parcel parcel) {
                            this.y = parcel.readInt();
                            this.x = parcel.readInt();
                            this.w = parcel.readInt();
                            this.h = parcel.readInt();
                        }

                        @Override // android.os.Parcelable
                        public void writeToParcel(Parcel parcel, int i) {
                            parcel.writeInt(this.y);
                            parcel.writeInt(this.x);
                            parcel.writeInt(this.w);
                            parcel.writeInt(this.h);
                        }

                        public int getY() {
                            return this.y;
                        }

                        public void setY(int i) {
                            this.y = i;
                        }

                        public int getX() {
                            return this.x;
                        }

                        public void setX(int i) {
                            this.x = i;
                        }

                        public int getW() {
                            return this.w;
                        }

                        public void setW(int i) {
                            this.w = i;
                        }

                        public int getH() {
                            return this.h;
                        }

                        public void setH(int i) {
                            this.h = i;
                        }
                    }
                }
            }
        }
    }
}
