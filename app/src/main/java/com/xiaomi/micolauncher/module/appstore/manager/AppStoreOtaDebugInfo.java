package com.xiaomi.micolauncher.module.appstore.manager;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.onetrack.OneTrack;
import java.util.List;

/* loaded from: classes3.dex */
public class AppStoreOtaDebugInfo {
    @SerializedName("id")
    private int a;
    @SerializedName(SchemaActivity.KEY_METHOD)
    private String b;
    @SerializedName("params")
    private MemoryInfo c;

    public void setId(int i) {
        this.a = i;
    }

    public int getId() {
        return this.a;
    }

    public void setMethod(String str) {
        this.b = str;
    }

    public String getMethod() {
        return this.b;
    }

    public void setParams(MemoryInfo memoryInfo) {
        this.c = memoryInfo;
    }

    public MemoryInfo getParams() {
        return this.c;
    }

    /* loaded from: classes3.dex */
    public static class MemoryInfo {
        @SerializedName("freeSpace")
        private long a;
        @SerializedName("pkgInfoListSize")
        private long b;
        @SerializedName("pkgInfoList")
        private List<pkgInfo> c;

        public List<pkgInfo> getPkgInfoList() {
            return this.c;
        }

        public void setPkgInfoList(List<pkgInfo> list) {
            this.c = list;
        }

        public long getPkgInfoListSize() {
            return this.b;
        }

        public void setPkgInfoListSize(long j) {
            this.b = j;
        }

        public long getFreeSpace() {
            return this.a;
        }

        public void setFreeSpace(long j) {
            this.a = j;
        }

        public String toString() {
            return "MemoryInfo{freeSpace=" + this.a + ", pkgInfoListSize=" + this.b + ", pkgInfoList=" + this.c + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class pkgInfo {
        @SerializedName(OneTrack.Param.PKG)
        private String a;
        @SerializedName("dataStats")
        private long b;
        @SerializedName("cacheStats")
        private long c;

        public void setPkg(String str) {
            this.a = str;
        }

        public String getPkg() {
            return this.a;
        }

        public long getCacheStats() {
            return this.c;
        }

        public void setCacheStats(long j) {
            this.c = j;
        }

        public long getDataStats() {
            return this.b;
        }

        public void setDataStats(long j) {
            this.b = j;
        }

        public String toString() {
            return "pkgInfo{pkg='" + this.a + "', dataStats=" + this.b + ", cacheStats=" + this.c + '}';
        }
    }
}
