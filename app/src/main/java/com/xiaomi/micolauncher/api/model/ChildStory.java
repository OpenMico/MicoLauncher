package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildStory {
    public static final String BLOCK_AGE = "display_block_age";
    public static final String BLOCK_GRID_RICH = "block_grid_rich";
    public static final String BLOCK_HIGHLIGHT = "display_block_highlight";
    public static final String BLOCK_HOT = "display_block_hot";
    public static final String BLOCK_KAISHU = "display_block_kaishu";
    public static final String BLOCK_RECOMMEND = "display_block_recommend";
    private static final int DEFAULT_ITEM_SIZE = 6;
    private List<BlocksBean> blocks;
    private FlowUiTypeBean flowUiType;

    public FlowUiTypeBean getFlowUiType() {
        return this.flowUiType;
    }

    public void setFlowUiType(FlowUiTypeBean flowUiTypeBean) {
        this.flowUiType = flowUiTypeBean;
    }

    public List<BlocksBean> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<BlocksBean> list) {
        this.blocks = list;
    }

    /* loaded from: classes3.dex */
    public static class FlowUiTypeBean {
        private Object name;

        public Object getName() {
            return this.name;
        }

        public void setName(Object obj) {
            this.name = obj;
        }
    }

    /* loaded from: classes3.dex */
    public static class BlocksBean implements IBlockBean {
        private BlockStatBean blockStat;
        private String blockType;
        private BlockUiTypeBean blockUiType;
        private int categoryType;
        private List<?> cpList;
        private String description;
        private boolean displayMore;
        private int id;
        private List<ItemsBean> items;
        private int status;
        private String title;

        public BlocksBean(boolean z) {
            this.items = new ArrayList();
            for (int i = 0; i < 6; i++) {
                ItemsBean itemsBean = new ItemsBean();
                itemsBean.setTitle("");
                itemsBean.setImageUrl("");
                itemsBean.setId("");
                this.items.add(itemsBean);
            }
        }

        public BlocksBean() {
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

        public String getId() {
            return String.valueOf(this.id);
        }

        public void setId(int i) {
            this.id = i;
        }

        public boolean isDisplayMore() {
            return this.displayMore;
        }

        public void setDisplayMore(boolean z) {
            this.displayMore = z;
        }

        public int getCategoryType() {
            return this.categoryType;
        }

        public void setCategoryType(int i) {
            this.categoryType = i;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public List<ItemsBean> getItems() {
            return this.items;
        }

        public void setItems(List<ItemsBean> list) {
            this.items = list;
        }

        public List<?> getCpList() {
            return this.cpList;
        }

        public void setCpList(List<?> list) {
            this.cpList = list;
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getBlockType() {
            return TextUtils.isEmpty(this.blockType) ? IBlockBean.BLOCK_TYPE_STORY : this.blockType;
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getBlockTitle() {
            return getTitle();
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public String getUiType() {
            return this.blockUiType.name;
        }

        @Override // com.xiaomi.micolauncher.api.model.IBlockBean
        public List<? extends IListItem> getListItems() {
            return getItems();
        }

        public void setBlockType(String str) {
            this.blockType = str;
        }

        /* loaded from: classes3.dex */
        public static class BlockStatBean {
            private String traceid;

            public String getTraceid() {
                return this.traceid;
            }

            public void setTraceid(String str) {
                this.traceid = str;
            }
        }

        /* loaded from: classes3.dex */
        public static class BlockUiTypeBean {
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

        /* loaded from: classes3.dex */
        public static class ItemsBean implements IListItem {
            private AdBean ad;
            private Object extra;
            private Object groupName;
            private Object groupTypeId;
            private String id;
            public String imageUrl;
            private ImagesBean images;
            private boolean isFeature;
            private Object itemStat;
            private ItemUiTypeBean itemUiType;
            private String ns;
            private int playCount;
            private int saleType;
            private String shortDescription;
            private Object songs;
            private String target;
            private String title;
            private int updateTime;

            @Override // com.xiaomi.micolauncher.api.model.IListItem
            public String getItemId() {
                return "";
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

            public String getShortDescription() {
                return this.shortDescription;
            }

            public void setShortDescription(String str) {
                this.shortDescription = str;
            }

            public Object getItemStat() {
                return this.itemStat;
            }

            public void setItemStat(Object obj) {
                this.itemStat = obj;
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

            public Object getGroupTypeId() {
                return this.groupTypeId;
            }

            public void setGroupTypeId(Object obj) {
                this.groupTypeId = obj;
            }

            public Object getGroupName() {
                return this.groupName;
            }

            public void setGroupName(Object obj) {
                this.groupName = obj;
            }

            public Object getSongs() {
                return this.songs;
            }

            public void setSongs(Object obj) {
                this.songs = obj;
            }

            public int getPlayCount() {
                return this.playCount;
            }

            public void setPlayCount(int i) {
                this.playCount = i;
            }

            public int getUpdateTime() {
                return this.updateTime;
            }

            public void setUpdateTime(int i) {
                this.updateTime = i;
            }

            public int getSaleType() {
                return this.saleType;
            }

            public void setSaleType(int i) {
                this.saleType = i;
            }

            public boolean isIsFeature() {
                return this.isFeature;
            }

            public void setIsFeature(boolean z) {
                this.isFeature = z;
            }

            public Object getExtra() {
                return this.extra;
            }

            public void setExtra(Object obj) {
                this.extra = obj;
            }

            public String getImageUrl() {
                return this.imageUrl;
            }

            public void setImageUrl(String str) {
                this.imageUrl = str;
                this.images = new ImagesBean();
                this.images.poster = new ImagesBean.PosterBean();
                this.images.poster.url = str;
                this.images.background = new ImagesBean.BackgroundBean();
                this.images.background.url = str;
            }

            @Override // com.xiaomi.micolauncher.api.model.IListItem
            public String getItemImageUrl() {
                if (this.images.getBackground() != null) {
                    return this.images.getBackground().url;
                }
                return this.images.poster.url;
            }

            @Override // com.xiaomi.micolauncher.api.model.IListItem
            public String getItemTitle() {
                return getTitle();
            }

            @Override // com.xiaomi.micolauncher.api.model.IListItem
            public String getItemTarget() {
                return this.target;
            }

            /* loaded from: classes3.dex */
            public static class ImagesBean {
                private BackgroundBean background;
                private PosterBean poster;

                public PosterBean getPoster() {
                    return this.poster;
                }

                public void setPoster(PosterBean posterBean) {
                    this.poster = posterBean;
                }

                public BackgroundBean getBackground() {
                    return this.background;
                }

                public void setBackground(BackgroundBean backgroundBean) {
                    this.background = backgroundBean;
                }

                /* loaded from: classes3.dex */
                public static class PosterBean {
                    private Object md5;
                    private String url;

                    public String getUrl() {
                        return this.url;
                    }

                    public void setUrl(String str) {
                        this.url = str;
                    }

                    public Object getMd5() {
                        return this.md5;
                    }

                    public void setMd5(Object obj) {
                        this.md5 = obj;
                    }
                }

                /* loaded from: classes3.dex */
                public static class BackgroundBean {
                    private Object md5;
                    private String url;

                    public String getUrl() {
                        return this.url;
                    }

                    public void setUrl(String str) {
                        this.url = str;
                    }

                    public Object getMd5() {
                        return this.md5;
                    }

                    public void setMd5(Object obj) {
                        this.md5 = obj;
                    }
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

            /* loaded from: classes3.dex */
            public static class ItemUiTypeBean {
                private String name;
                private PosBean pos;

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
                public static class PosBean {
                    private int h;
                    private int w;
                    private int x;
                    private int y;

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
