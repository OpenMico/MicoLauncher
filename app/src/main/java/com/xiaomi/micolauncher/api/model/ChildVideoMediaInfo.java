package com.xiaomi.micolauncher.api.model;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoMediaInfo {
    private List<DataBean> data;
    private String encoding;
    private String language;
    private StatBean stat;
    private int status;
    private int ver;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public StatBean getStat() {
        return this.stat;
    }

    public void setStat(StatBean statBean) {
        this.stat = statBean;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int i) {
        this.ver = i;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    /* loaded from: classes3.dex */
    public static class StatBean {
        private String traceid;

        public String getTraceid() {
            return this.traceid;
        }

        public void setTraceid(String str) {
            this.traceid = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class DataBean {
        private String extra;
        private int id;
        private ChildVideo.ImagesBeanX images;
        private int mold;
        private boolean online;
        private ChildVideo.StatBeanXX stat;
        private TargetBean target;
        private String title;
        private int type;
        private UiTypeBean ui_type;

        public ChildVideo.StatBeanXX getStat() {
            return this.stat;
        }

        public void setStat(ChildVideo.StatBeanXX statBeanXX) {
            this.stat = statBeanXX;
        }

        public TargetBean getTarget() {
            return this.target;
        }

        public void setTarget(TargetBean targetBean) {
            this.target = targetBean;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public String getExtra() {
            return this.extra;
        }

        public void setExtra(String str) {
            this.extra = str;
        }

        public UiTypeBean getUi_type() {
            return this.ui_type;
        }

        public void setUi_type(UiTypeBean uiTypeBean) {
            this.ui_type = uiTypeBean;
        }

        public boolean isOnline() {
            return this.online;
        }

        public void setOnline(boolean z) {
            this.online = z;
        }

        public ChildVideo.ImagesBeanX getImages() {
            return this.images;
        }

        public void setImages(ChildVideo.ImagesBeanX imagesBeanX) {
            this.images = imagesBeanX;
        }

        public int getMold() {
            return this.mold;
        }

        public void setMold(int i) {
            this.mold = i;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int i) {
            this.id = i;
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
        public static class UiTypeBean {
            private String name;
            private PosBean pos;
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
