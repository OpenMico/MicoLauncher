package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class PutObjectResult {
    private String a;
    private String b;
    private String c;
    private String d;
    private long e;

    public String getBucketName() {
        return this.a;
    }

    public void setBucketName(String str) {
        this.a = str;
    }

    public String getObjectName() {
        return this.b;
    }

    public void setObjectName(String str) {
        this.b = str;
    }

    public String getAccessKeyId() {
        return this.c;
    }

    public void setAccessKeyId(String str) {
        this.c = str;
    }

    public String getSignature() {
        return this.d;
    }

    public void setSignature(String str) {
        this.d = str;
    }

    public long getExpires() {
        return this.e;
    }

    public void setExpires(long j) {
        this.e = j;
    }

    public String getRelativePreSignedUri() throws URISyntaxException {
        return new URI(null, null, null, -1, "/" + this.a + "/" + this.b, "GalaxyAccessKeyId=" + this.c + MusicGroupListActivity.SPECIAL_SYMBOL + "Expires=" + this.e + MusicGroupListActivity.SPECIAL_SYMBOL + "Signature=" + this.d, null).toString();
    }

    private static String a(String str) {
        return (str == null || str.isEmpty() || str.charAt(str.length() + (-1)) != '/') ? str : str.substring(0, str.length() - 1);
    }

    public String getAbsolutePreSignedUri() throws URISyntaxException {
        return getAbsolutePreSignedUri(Common.DEFAULT_FDS_SERVICE_BASE_URI);
    }

    public String getAbsolutePreSignedUri(String str) throws URISyntaxException {
        return a(str) + getRelativePreSignedUri();
    }

    public String getCdnPreSignedUri() throws URISyntaxException {
        return getCdnPreSignedUri(Common.DEFAULT_CDN_SERVICE_URI);
    }

    public String getCdnPreSignedUri(String str) throws URISyntaxException {
        return a(str) + getRelativePreSignedUri();
    }
}
