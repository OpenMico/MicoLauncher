package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public class ImageInfo {
    public static final int BMP = 4;
    public static final int DNG = 7;
    public static final int GIF = 2;
    public static final int HEIC = 8;
    public static final int HEIF = 9;
    public static final int JPEG = 1;
    public static final int JPG = 0;
    public static final int ORIENTATION_0 = 0;
    public static final int ORIENTATION_180 = 2;
    public static final int ORIENTATION_270 = 3;
    public static final int ORIENTATION_90 = 1;
    public static final int PNG = 3;
    public static final int UNSUPPORT_PHOTO = 11;
    public static final int UNSUPPORT_VIDEO = 12;
    public static final int VIDEO_IMAGE = 10;
    public static final int WBMP = 6;
    public static final int WEBP = 5;
    private byte[] a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private String h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;

    public byte[] getData() {
        return this.a;
    }

    public void setData(byte[] bArr) {
        this.a = bArr;
    }

    public int getSize() {
        return this.b;
    }

    public void setSize(int i) {
        this.b = i;
    }

    public int getWidth() {
        return this.c;
    }

    public void setWidth(int i) {
        this.c = i;
    }

    public int getHeight() {
        return this.d;
    }

    public void setHeight(int i) {
        this.d = i;
    }

    public int getImageType() {
        return this.e;
    }

    public void setImageType(int i) {
        this.e = i;
    }

    public int getOrientation() {
        return this.f;
    }

    public void setOrientation(int i) {
        this.f = i;
    }

    public boolean isCompressed() {
        return this.g;
    }

    public void setCompressed(boolean z) {
        this.g = z;
    }

    public String getPhotoUri() {
        return this.h;
    }

    public void setPhotoUri(String str) {
        this.h = str;
    }

    public int getImageMaxBitrate() {
        return this.i;
    }

    public void setImageMaxBitrate(int i) {
        this.i = i;
    }

    public int getImageMaxWidth() {
        return this.j;
    }

    public void setImageMaxWidth(int i) {
        this.j = i;
    }

    public int getImageMaxHeight() {
        return this.k;
    }

    public void setImageMaxHeight(int i) {
        this.k = i;
    }

    public int getImageBestWidth() {
        return this.l;
    }

    public void setImageBestWidth(int i) {
        this.l = i;
    }

    public int getImageBestHeight() {
        return this.m;
    }

    public void setImageBestHeight(int i) {
        this.m = i;
    }
}
