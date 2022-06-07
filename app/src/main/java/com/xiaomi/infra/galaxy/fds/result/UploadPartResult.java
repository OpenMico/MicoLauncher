package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class UploadPartResult {
    private int a;
    private String b;
    private long c;

    public UploadPartResult() {
    }

    public UploadPartResult(int i, long j, String str) {
        this.a = i;
        this.b = str;
        this.c = j;
    }

    public int getPartNumber() {
        return this.a;
    }

    public void setPartNumber(int i) {
        this.a = i;
    }

    public String getEtag() {
        return this.b;
    }

    public void setEtag(String str) {
        this.b = str;
    }

    public long getPartSize() {
        return this.c;
    }

    public void setPartSize(long j) {
        this.c = j;
    }
}
