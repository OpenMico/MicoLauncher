package com.xiaomi.ai.android.netbeans;

/* loaded from: classes2.dex */
public class AivsCloudConfigBean {
    private ClearBean clear;
    private int requestInterval;
    private int version;
    private String linkMode = "";
    private boolean uploadLogSwitch = false;

    /* loaded from: classes2.dex */
    public static class ClearBean {
        private boolean AESkey;
        private boolean httpdns;
        private boolean logcache;
        private boolean nmapcache;
        private boolean publickey;

        public boolean isAESkey() {
            return this.AESkey;
        }

        public boolean isHttpdns() {
            return this.httpdns;
        }

        public boolean isLogcache() {
            return this.logcache;
        }

        public boolean isNmapcache() {
            return this.nmapcache;
        }

        public boolean isPublickey() {
            return this.publickey;
        }

        public void setAESkey(boolean z) {
            this.AESkey = z;
        }

        public void setHttpdns(boolean z) {
            this.httpdns = z;
        }

        public void setLogcache(boolean z) {
            this.logcache = z;
        }

        public void setNmapcache(boolean z) {
            this.nmapcache = z;
        }

        public void setPublickey(boolean z) {
            this.publickey = z;
        }

        public String toString() {
            return "ClearBean{httpdns=" + this.httpdns + ", publickey=" + this.publickey + ", AESkey=" + this.AESkey + ", nmapcache=" + this.nmapcache + ", logcache=" + this.logcache + '}';
        }
    }

    public ClearBean getClear() {
        return this.clear;
    }

    public String getLinkMode() {
        return this.linkMode;
    }

    public int getRequestInterval() {
        return this.requestInterval;
    }

    public boolean getUploadLogSwitch() {
        return this.uploadLogSwitch;
    }

    public int getVersion() {
        return this.version;
    }

    public void setClear(ClearBean clearBean) {
        this.clear = clearBean;
    }

    public void setLinkMode(String str) {
        this.linkMode = str;
    }

    public void setRequestInterval(int i) {
        this.requestInterval = i;
    }

    public void setUploadLogSwitch(boolean z) {
        this.uploadLogSwitch = z;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public String toString() {
        return "AivsCloudConfigBean{version=" + this.version + ", clear=" + this.clear + ", linkMode=" + this.linkMode + ", requestInterval=" + this.requestInterval + ", uploadLogSwitch=" + this.uploadLogSwitch + '}';
    }
}
