package com.xiaomi.micolauncher.module.homepage.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.appstore.bean.AppInfo;
import java.util.List;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class VideoTabInfo {
    @SerializedName("tabList")
    private List<VideoTabData> a;
    @SerializedName("secList")
    private List<CategoryInfo> b;
    @SerializedName("appList")
    private List<AppInfo> c;
    @SerializedName("recommendCard")
    private RecommendAppCard d;

    public List<VideoTabData> getTabDataList() {
        return this.a;
    }

    public void setTabDataList(List<VideoTabData> list) {
        this.a = list;
    }

    public List<AppInfo> getAppList() {
        return this.c;
    }

    public void setAppList(List<AppInfo> list) {
        this.c = list;
    }

    public List<CategoryInfo> getCategoryList() {
        return this.b;
    }

    public void setCategoryList(List<CategoryInfo> list) {
        this.b = list;
    }

    public RecommendAppCard getRecommendCard() {
        return this.d;
    }

    public void setRecommendCard(RecommendAppCard recommendAppCard) {
        this.d = recommendAppCard;
    }

    /* loaded from: classes3.dex */
    public static final class RecommendAppCard {
        @SerializedName("title")
        private String a;
        @SerializedName("description")
        private String b;
        @SerializedName("itemList")
        private List<RecommendAppInfo> c;

        public RecommendAppCard setTitle(String str) {
            this.a = str;
            return this;
        }

        public String getTitle() {
            return this.a;
        }

        public String getDescription() {
            return this.b;
        }

        public List<RecommendAppInfo> getItemList() {
            return this.c;
        }

        public void setItemList(List<RecommendAppInfo> list) {
            this.c = list;
        }
    }

    /* loaded from: classes3.dex */
    public static final class RecommendAppInfo {
        @SerializedName("itemName")
        private String a;
        @SerializedName("description")
        private String b;
        @SerializedName("appKey")
        private long c;
        @SerializedName("appInfo")
        private AppInfo d;

        @Deprecated
        public RecommendAppInfo(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public AppInfo getAppInfo() {
            return this.d;
        }

        public void setAppInfo(AppInfo appInfo) {
            this.d = appInfo;
        }

        public String getItemName() {
            return this.a;
        }

        public String getDescription() {
            return this.b;
        }

        public long getAppKey() {
            return this.c;
        }
    }

    /* loaded from: classes3.dex */
    public static final class CategoryInfo implements Parcelable {
        public static final Parcelable.Creator<CategoryInfo> CREATOR = new Parcelable.Creator<CategoryInfo>() { // from class: com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo.CategoryInfo.1
            /* renamed from: a */
            public CategoryInfo createFromParcel(Parcel parcel) {
                return new CategoryInfo(parcel);
            }

            /* renamed from: a */
            public CategoryInfo[] newArray(int i) {
                return new CategoryInfo[i];
            }
        };
        @SerializedName("iconUrl")
        private String a;
        @SerializedName("name")
        private String b;
        @SerializedName(IChannel.EXTRA_ERROR_DESC)
        private String c;
        @SerializedName("tabKey")
        private String d;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Deprecated
        public CategoryInfo(String str, String str2) {
            this.b = str;
            this.d = str2;
        }

        public String getIconUrl() {
            return this.a;
        }

        public String getName() {
            return this.b;
        }

        public String getDesc() {
            return this.c;
        }

        public void setIconUrl(String str) {
            this.a = str;
        }

        public void setName(String str) {
            this.b = str;
        }

        public void setDesc(String str) {
            this.c = str;
        }

        public String getTabKey() {
            return this.d;
        }

        public void setTabKey(String str) {
            this.d = str;
        }

        protected CategoryInfo(Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readString();
            this.c = parcel.readString();
            this.d = parcel.readString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeString(this.b);
            parcel.writeString(this.c);
            parcel.writeString(this.d);
        }
    }

    /* loaded from: classes3.dex */
    public static final class RecommendCategory {
        private List<CategoryInfo> a;

        public RecommendCategory() {
        }

        public RecommendCategory(List<CategoryInfo> list) {
            this.a = list;
        }

        public List<CategoryInfo> getCategoryList() {
            return this.a;
        }

        public void setCategoryList(List<CategoryInfo> list) {
            this.a = list;
        }
    }
}
