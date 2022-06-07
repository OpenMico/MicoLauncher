package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class InitMultipartUploadResult {
    private String a;
    private String b;
    private String c;

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

    public String getUploadId() {
        return this.c;
    }

    public void setUploadId(String str) {
        this.c = str;
    }
}
