package com.xiaomi.infra.galaxy.fds.android.auth;

import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.infra.galaxy.fds.android.util.Args;
import kotlin.text.Typography;
import org.apache.http.client.methods.HttpRequestBase;

/* loaded from: classes3.dex */
public class SSOCredential implements GalaxyFDSCredential {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;

    @Deprecated
    public SSOCredential(String str) {
        this.a = "SSO";
        this.b = AuthorizeActivityBase.KEY_SERVICETOKEN;
        this.c = "APP_ID";
        Args.notNull(str, "Service token");
        Args.notEmpty(str, "Service token");
        this.d = str;
        this.e = null;
    }

    public SSOCredential(String str, String str2) {
        this.a = "SSO";
        this.b = AuthorizeActivityBase.KEY_SERVICETOKEN;
        this.c = "APP_ID";
        Args.notNull(str, "Service token");
        Args.notEmpty(str, "Service token");
        Args.notNull(str2, "App id");
        Args.notEmpty(str2, "App id");
        this.d = str;
        this.e = str2;
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public void addHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.addHeader("Authorization", "SSO");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public String addParam(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (str.indexOf(63) == -1) {
            sb.append('?');
        } else {
            sb.append(Typography.amp);
        }
        sb.append(AuthorizeActivityBase.KEY_SERVICETOKEN);
        sb.append('=');
        sb.append(this.d);
        if (this.e != null) {
            sb.append(Typography.amp);
            sb.append("APP_ID");
            sb.append('=');
            sb.append(this.e);
        }
        return sb.toString();
    }
}
