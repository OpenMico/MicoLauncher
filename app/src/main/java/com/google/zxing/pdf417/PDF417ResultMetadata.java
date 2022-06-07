package com.google.zxing.pdf417;

/* loaded from: classes2.dex */
public final class PDF417ResultMetadata {
    private int a;
    private String b;
    private int[] c;
    private boolean d;

    public int getSegmentIndex() {
        return this.a;
    }

    public void setSegmentIndex(int i) {
        this.a = i;
    }

    public String getFileId() {
        return this.b;
    }

    public void setFileId(String str) {
        this.b = str;
    }

    public int[] getOptionalData() {
        return this.c;
    }

    public void setOptionalData(int[] iArr) {
        this.c = iArr;
    }

    public boolean isLastSegment() {
        return this.d;
    }

    public void setLastSegment(boolean z) {
        this.d = z;
    }
}
