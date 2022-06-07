package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class PassportScope {

    /* loaded from: classes3.dex */
    public static class ScopeResult {
        public String code;
        CodeInfo[] scope;
        String status;

        public String toString() {
            return "ScopeResult{status='" + this.status + "', scope='" + this.code + "', code=" + Arrays.toString(this.scope) + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class CodeInfo {
        String description;
        String id;
        String name;

        public String toString() {
            return "CodeInfo{id='" + this.id + "', name='" + this.name + "', description='" + this.description + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public static class IssuedTokenResult {
        @SerializedName(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2)
        public String accessToken;
        @SerializedName(XiaomiOAuthConstants.EXTRA_EXPIRES_IN_2)
        public long expiresInSec;
        @SerializedName(XiaomiOAuthConstants.EXTRA_MAC_ALGORITHM_2)
        String macAlgorithm;
        @SerializedName(XiaomiOAuthConstants.EXTRA_MAC_KEY_2)
        String macKey;
        String openId;
        @SerializedName("refresh_token")
        public String refreshToken;

        public String toString() {
            return "IssuedTokenResult{accessToken='" + this.accessToken + "', expiresInSec=" + this.expiresInSec + ", refreshToken='" + this.refreshToken + "', macKey='" + this.macKey + "', macAlgorithm='" + this.macAlgorithm + "', openId='" + this.openId + "'}";
        }
    }
}
