package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mipush.sdk.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RtspAuthenticationInfo.java */
/* loaded from: classes2.dex */
public final class c {
    public final int a;
    public final String b;
    public final String c;
    public final String d;

    public c(int i, String str, String str2, String str3) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public String a(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i) throws ParserException {
        switch (this.a) {
            case 1:
                return a(rtspAuthUserInfo);
            case 2:
                return b(rtspAuthUserInfo, uri, i);
            default:
                throw ParserException.createForManifestWithUnsupportedFeature(null, new UnsupportedOperationException());
        }
    }

    private String a(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo) {
        String str = rtspAuthUserInfo.username;
        String str2 = rtspAuthUserInfo.password;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(str2);
        return Base64.encodeToString(RtspMessageUtil.a(sb.toString()), 0);
    }

    private String b(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i) throws ParserException {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            String a = RtspMessageUtil.a(i);
            String str = rtspAuthUserInfo.username;
            String str2 = this.b;
            String str3 = rtspAuthUserInfo.password;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length() + String.valueOf(str3).length());
            sb.append(str);
            sb.append(Constants.COLON_SEPARATOR);
            sb.append(str2);
            sb.append(Constants.COLON_SEPARATOR);
            sb.append(str3);
            String hexString = Util.toHexString(instance.digest(RtspMessageUtil.a(sb.toString())));
            String valueOf = String.valueOf(uri);
            StringBuilder sb2 = new StringBuilder(String.valueOf(a).length() + 1 + String.valueOf(valueOf).length());
            sb2.append(a);
            sb2.append(Constants.COLON_SEPARATOR);
            sb2.append(valueOf);
            String hexString2 = Util.toHexString(instance.digest(RtspMessageUtil.a(sb2.toString())));
            String str4 = this.c;
            StringBuilder sb3 = new StringBuilder(String.valueOf(hexString).length() + 2 + String.valueOf(str4).length() + String.valueOf(hexString2).length());
            sb3.append(hexString);
            sb3.append(Constants.COLON_SEPARATOR);
            sb3.append(str4);
            sb3.append(Constants.COLON_SEPARATOR);
            sb3.append(hexString2);
            String hexString3 = Util.toHexString(instance.digest(RtspMessageUtil.a(sb3.toString())));
            return this.d.isEmpty() ? Util.formatInvariant("Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\"", rtspAuthUserInfo.username, this.b, this.c, uri, hexString3) : Util.formatInvariant("Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\", opaque=\"%s\"", rtspAuthUserInfo.username, this.b, this.c, uri, hexString3, this.d);
        } catch (NoSuchAlgorithmException e) {
            throw ParserException.createForManifestWithUnsupportedFeature(null, e);
        }
    }
}
