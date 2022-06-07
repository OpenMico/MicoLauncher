package com.google.zxing.common;

import java.util.List;

/* loaded from: classes2.dex */
public final class DecoderResult {
    private final byte[] a;
    private int b;
    private final String c;
    private final List<byte[]> d;
    private final String e;
    private Integer f;
    private Integer g;
    private Object h;
    private final int i;
    private final int j;

    public DecoderResult(byte[] bArr, String str, List<byte[]> list, String str2) {
        this(bArr, str, list, str2, -1, -1);
    }

    public DecoderResult(byte[] bArr, String str, List<byte[]> list, String str2, int i, int i2) {
        this.a = bArr;
        this.b = bArr == null ? 0 : bArr.length * 8;
        this.c = str;
        this.d = list;
        this.e = str2;
        this.i = i2;
        this.j = i;
    }

    public byte[] getRawBytes() {
        return this.a;
    }

    public int getNumBits() {
        return this.b;
    }

    public void setNumBits(int i) {
        this.b = i;
    }

    public String getText() {
        return this.c;
    }

    public List<byte[]> getByteSegments() {
        return this.d;
    }

    public String getECLevel() {
        return this.e;
    }

    public Integer getErrorsCorrected() {
        return this.f;
    }

    public void setErrorsCorrected(Integer num) {
        this.f = num;
    }

    public Integer getErasures() {
        return this.g;
    }

    public void setErasures(Integer num) {
        this.g = num;
    }

    public Object getOther() {
        return this.h;
    }

    public void setOther(Object obj) {
        this.h = obj;
    }

    public boolean hasStructuredAppend() {
        return this.i >= 0 && this.j >= 0;
    }

    public int getStructuredAppendParity() {
        return this.i;
    }

    public int getStructuredAppendSequenceNumber() {
        return this.j;
    }
}
