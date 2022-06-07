package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.MultipartUploadBean;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class ListMultipartUploadsResult {
    private String a;
    private String b;
    private int c;
    private String d;
    private boolean e;
    private String f;
    private List<MultipartUploadBean> g;
    private List<String> h;
    private String i;

    public String getBucketName() {
        return this.a;
    }

    public void setBucketName(String str) {
        this.a = str;
    }

    public String getPrefix() {
        return this.b;
    }

    public void setPrefix(String str) {
        this.b = str;
    }

    public int getMaxKeys() {
        return this.c;
    }

    public void setMaxKeys(int i) {
        this.c = i;
    }

    public String getMarker() {
        return this.d;
    }

    public void setMarker(String str) {
        this.d = str;
    }

    public boolean isTruncated() {
        return this.e;
    }

    public void setTruncated(boolean z) {
        this.e = z;
    }

    public String getNextMarker() {
        return this.f;
    }

    public void setNextMarker(String str) {
        this.f = str;
    }

    public List<MultipartUploadBean> getUploads() {
        return this.g;
    }

    public void setUploads(List<MultipartUploadBean> list) {
        this.g = list;
    }

    public List<String> getCommonPrefixes() {
        return this.h;
    }

    public void setCommonPrefixes(List<String> list) {
        this.h = list;
    }

    public String getDelimiter() {
        return this.i;
    }

    public void setDelimiter(String str) {
        this.i = str;
    }
}
