package com.xiaomi.smarthome.core.server.internal.account;

import android.content.Context;
import java.util.Locale;

/* loaded from: classes4.dex */
public class NetApiConfig {
    public boolean IS_DEBUG_BUILD_TYPE;
    public Locale mLocale;
    public String mServer;
    public String mServerEnv;
    public Context sAppContext;

    private NetApiConfig(Builder builder) {
        this.sAppContext = builder.a;
        this.mServer = builder.c;
        this.mServerEnv = builder.d;
        this.mLocale = builder.b;
        this.IS_DEBUG_BUILD_TYPE = builder.e;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        Context a;
        Locale b;
        String c;
        String d = "release";
        boolean e;

        public Builder init(Context context) {
            this.a = context;
            return this;
        }

        public Builder setServer(String str) {
            this.c = str;
            return this;
        }

        public Builder setServerEnv(String str) {
            this.d = str;
            return this;
        }

        public Builder setIsDebug(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setLocal(Locale locale) {
            this.b = locale;
            return this;
        }

        public NetApiConfig build() {
            return new NetApiConfig(this);
        }
    }
}
