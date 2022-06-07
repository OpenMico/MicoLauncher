package com.xiaomi.onetrack;

import android.text.TextUtils;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.Map;

/* loaded from: classes4.dex */
public class ServiceQualityEvent {
    private String a;
    private String b;
    private Integer c;
    private String d;
    private String e;
    private Integer f;
    private Integer g;
    private String h;
    private String i;
    private Integer j;
    private Long k;
    private Long l;
    private Long m;
    private Long n;
    private Long o;
    private Long p;
    private Long q;
    private Long r;
    private String s;
    private String t;
    private Map<String, Object> u;

    /* loaded from: classes4.dex */
    public enum ResultType {
        SUCCESS("ok"),
        FAILED(MiotMeshDeviceItem.CONNECT_STATE_FAILED),
        TIMEOUT(RtspHeaders.Values.TIMEOUT);
        
        private String a;

        ResultType(String str) {
            this.a = str;
        }

        public String getResultType() {
            return this.a;
        }
    }

    public String getScheme() {
        return this.a;
    }

    public String getHost() {
        return this.b;
    }

    public Integer getPort() {
        return this.c;
    }

    public String getIps() {
        return this.e;
    }

    public String getPath() {
        return this.d;
    }

    public Integer getResponseCode() {
        return this.f;
    }

    public Integer getStatusCode() {
        return this.g;
    }

    public String getExceptionTag() {
        return this.h;
    }

    public String getResultType() {
        return this.i;
    }

    public Integer getRetryCount() {
        return this.j;
    }

    public Long getDnsLookupTime() {
        return this.k;
    }

    public Long getTcpConnectTime() {
        return this.l;
    }

    public Long getHandshakeTime() {
        return this.m;
    }

    public Long getReceiveFirstByteTime() {
        return this.o;
    }

    public Long getReceiveAllByteTime() {
        return this.p;
    }

    public Long getRequestTimestamp() {
        return this.r;
    }

    public String getRequestNetType() {
        return this.s;
    }

    public Long getRequestDataSendTime() {
        return this.n;
    }

    public Long getDuration() {
        return this.q;
    }

    public String getNetSdkVersion() {
        return this.t;
    }

    public Map<String, Object> getExtraParams() {
        return this.u;
    }

    private ServiceQualityEvent(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        String str = null;
        this.i = builder.i != null ? builder.i.getResultType() : null;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
        this.o = builder.o;
        this.p = builder.p;
        this.r = builder.r;
        this.s = builder.s != null ? builder.s.toString() : str;
        this.n = builder.n;
        this.q = builder.q;
        this.t = builder.t;
        this.u = builder.u;
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private String a;
        private String b;
        private Integer c;
        private String d;
        private String e;
        private Integer f;
        private Integer g;
        private String h;
        private ResultType i;
        private Integer j;
        private Long k;
        private Long l;
        private Long m;
        private Long n;
        private Long o;
        private Long p;
        private Long q;
        private Long r;
        private OneTrack.NetType s;
        private String t;
        private Map<String, Object> u;

        public Builder setScheme(String str) {
            this.a = str;
            return this;
        }

        public Builder setHost(String str) {
            this.b = str;
            return this;
        }

        public Builder setPort(Integer num) {
            this.c = num;
            return this;
        }

        public Builder setIps(String... strArr) {
            if (strArr != null) {
                this.e = TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, strArr);
            }
            return this;
        }

        public Builder setPath(String str) {
            this.d = str;
            return this;
        }

        public Builder setResponseCode(Integer num) {
            this.f = num;
            return this;
        }

        public Builder setStatusCode(Integer num) {
            this.g = num;
            return this;
        }

        public Builder setExceptionTag(String str) {
            this.h = str;
            return this;
        }

        public Builder setResultType(ResultType resultType) {
            this.i = resultType;
            return this;
        }

        public Builder setRetryCount(Integer num) {
            this.j = num;
            return this;
        }

        public Builder setDnsLookupTime(Long l) {
            this.k = l;
            return this;
        }

        public Builder setTcpConnectTime(Long l) {
            this.l = l;
            return this;
        }

        public Builder setHandshakeTime(Long l) {
            this.m = l;
            return this;
        }

        public Builder setReceiveFirstByteTime(Long l) {
            this.o = l;
            return this;
        }

        public Builder setReceiveAllByteTime(Long l) {
            this.p = l;
            return this;
        }

        public Builder setRequestTimestamp(Long l) {
            this.r = l;
            return this;
        }

        public Builder setRequestNetType(OneTrack.NetType netType) {
            this.s = netType;
            return this;
        }

        public Builder setRequestDataSendTime(Long l) {
            this.n = l;
            return this;
        }

        public Builder setDuration(Long l) {
            this.q = l;
            return this;
        }

        public Builder setNetSdkVersion(String str) {
            this.t = str;
            return this;
        }

        public Builder setExtraParams(Map<String, Object> map) {
            this.u = map;
            return this;
        }

        public ServiceQualityEvent build() {
            return new ServiceQualityEvent(this);
        }
    }
}
