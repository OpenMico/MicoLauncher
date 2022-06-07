package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MIBrain {

    /* loaded from: classes3.dex */
    public static class CpInfo {
        public int cpcode;
        public String cpmessage;
    }

    /* loaded from: classes3.dex */
    public static class CpResourceItem {
        public String id;
        public String url;
    }

    /* loaded from: classes3.dex */
    public static class CpStatus {
        public int code;
    }

    /* loaded from: classes3.dex */
    public static class FetchTodoListByStartTimeResponse {
        public List<TodoInfoItem> result;
    }

    /* loaded from: classes3.dex */
    public static class LoadMoreAnswer {
        public Directive directive;
    }

    /* loaded from: classes3.dex */
    public static class LoadMoreResponse {
        public LoadMoreAnswer answer;
        public String request_id;
        public String version;
    }

    /* loaded from: classes3.dex */
    public static class MVResource {
        public List<MVResourceItem> data;
        public String requestId;
        public CpStatus status;
    }

    /* loaded from: classes3.dex */
    public static class MemoIdItem {
        public String memoId;
    }

    /* loaded from: classes3.dex */
    public static class SaveAudioResult {
        public MemoIdItem result;
    }

    /* loaded from: classes3.dex */
    public static class TodoInfoItem {
        public long audioDuration;
        public String audioFile;
        public String content;
        public long createTime;
        public String memoId;
    }

    /* loaded from: classes3.dex */
    public static class CpResource {
        public CpInfo cpinfo;
        public List<CpResourceItem> data;
        private long loadTimeStamp;
        public CpStatus status;

        public void setLoadTimeStamp() {
            this.loadTimeStamp = System.currentTimeMillis();
        }

        public boolean isNotExpired() {
            return System.currentTimeMillis() - this.loadTimeStamp < TimeUnit.MINUTES.toMillis(30L);
        }
    }

    /* loaded from: classes3.dex */
    public static class MVResourceItem {
        @SerializedName("mv_BlueRay_size")
        public long blueRaySize;
        @SerializedName("mv_BlueRay_url")
        public String blueRayUrl;
        @SerializedName("mv_HQ_size")
        public long hqSize;
        @SerializedName("mv_HQ_url")
        public String hqUrl;
        public String id;
        @SerializedName("mv_LQ_size")
        public long lqSize;
        @SerializedName("mv_LQ_url")
        public String lqUrl;
        @SerializedName("mv_SQ_size")
        public long sqSize;
        @SerializedName("mv_SQ_url")
        public String sqUrl;
        public String url;

        public String getPlayUrl() {
            if (!TextUtils.isEmpty(this.sqUrl)) {
                return this.sqUrl;
            }
            if (!TextUtils.isEmpty(this.lqUrl)) {
                return this.lqUrl;
            }
            if (!TextUtils.isEmpty(this.hqUrl)) {
                return this.hqUrl;
            }
            if (!TextUtils.isEmpty(this.blueRayUrl)) {
                return this.blueRayUrl;
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static class CPBindStatus implements Serializable {
        public static final String Binded = "binded";
        public static final String Exired = "expired";
        public static final String ExpireSoon = "expireSoon";
        public static final String Normal = "normal";
        public static final String NotBind = "notBind";
        public String detailedState;
        public long expireAt;
        public String expireStatus;
        public int expiresIn;
        public String providerName;

        public String providerId() {
            return "com.tencent.qq".equals(this.providerName) ? "259021302864545792" : "com.tencent.weixin".equals(this.providerName) ? "259021749641805824" : "com.kkbox".equals(this.providerName) ? "347442676548767744" : "";
        }

        public int willExpireDay() {
            return this.expiresIn / CacheConstants.DAY;
        }

        public boolean isWillExpire() {
            return willExpireDay() < 7;
        }

        public boolean isBinded() {
            return Binded.equals(this.expireStatus) || "normal".equals(this.expireStatus);
        }

        public boolean isNotBinded() {
            return NotBind.equals(this.expireStatus);
        }

        public boolean isExpired() {
            return Exired.equals(this.expireStatus);
        }

        public boolean isInvalid() {
            return isNotBinded() || isExpired();
        }

        public boolean isValid() {
            return !isInvalid();
        }

        public boolean isExpireSoon() {
            return ExpireSoon.equals(this.expireStatus);
        }

        public boolean isQQMusicAuthValid() {
            return ("normal".equalsIgnoreCase(this.expireStatus) || ExpireSoon.equalsIgnoreCase(this.expireStatus)) && isQQMusicCP();
        }

        public boolean isQQMusicCP() {
            return "com.tencent.qq".equals(this.providerName) || "com.tencent.weixin".equals(this.providerName);
        }

        public boolean isKKBoxMusicCP() {
            return "com.kkbox".equals(this.providerName);
        }

        public String toString() {
            return "CPBindStatus{detailedState='" + this.detailedState + "', expireStatus='" + this.expireStatus + "', providerName='" + this.providerName + "', expiresIn=" + this.expiresIn + ", expireAt=" + this.expireAt + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class MiTvResource {
        public static final int ILLEGAL_PASS_TOKEN = 27;
        private List<MiTvResourceItem> data;
        private String requestId;
        private StatusBean status;

        public String getRequestId() {
            return this.requestId;
        }

        public void setRequestId(String str) {
            this.requestId = str;
        }

        public StatusBean getStatus() {
            return this.status;
        }

        public void setStatus(StatusBean statusBean) {
            this.status = statusBean;
        }

        public List<MiTvResourceItem> getData() {
            return this.data;
        }

        public void setData(List<MiTvResourceItem> list) {
            this.data = list;
        }

        /* loaded from: classes3.dex */
        public static class StatusBean {
            private int code;

            public int getCode() {
                return this.code;
            }

            public void setCode(int i) {
                this.code = i;
            }
        }

        /* loaded from: classes3.dex */
        public static class MiTvResourceItem {
            private String id;
            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String str) {
                this.url = str;
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }
        }
    }
}
