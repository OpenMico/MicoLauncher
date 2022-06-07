package com.xiaomi.infra.galaxy.fds.auth.sso;

/* loaded from: classes3.dex */
public class ServiceToken {
    private boolean a;
    private long b;
    private long c;
    private String d;
    private long e;
    private String f;

    /* loaded from: classes3.dex */
    public static class Builder {
        private boolean a;
        private long b;
        private String c;
        private long d;
        private String e;
        private long f;

        public ServiceToken build() {
            ServiceToken serviceToken = new ServiceToken();
            serviceToken.a = this.a;
            serviceToken.b = this.b;
            serviceToken.d = this.c;
            serviceToken.e = this.d;
            serviceToken.f = this.e;
            serviceToken.c = this.f;
            return serviceToken;
        }

        public Builder tsl(boolean z) {
            this.a = z;
            return this;
        }

        public Builder uid(long j) {
            this.b = j;
            return this;
        }

        public Builder secret(String str) {
            this.c = str;
            return this;
        }

        public Builder timestamp(long j) {
            this.d = j;
            return this;
        }

        public Builder version(String str) {
            this.e = str;
            return this;
        }

        public Builder developerId(long j) {
            this.f = j;
            return this;
        }
    }

    public boolean isTsl() {
        return this.a;
    }

    public long getUid() {
        return this.b;
    }

    public String getSecret() {
        return this.d;
    }

    public long getTimestamp() {
        return this.e;
    }

    public String getVersion() {
        return this.f;
    }

    public long getDeveloperId() {
        return this.c;
    }

    public String toString() {
        return "[ServiceToken: tsl=" + this.a + ", uid=" + this.b + ", timestamp=" + this.e + ", version=" + this.f + "]";
    }
}
