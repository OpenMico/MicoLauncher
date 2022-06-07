package com.bumptech.glide.gifdecoder;

import androidx.annotation.ColorInt;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GifHeader {
    public static final int NETSCAPE_LOOP_COUNT_DOES_NOT_EXIST = -1;
    public static final int NETSCAPE_LOOP_COUNT_FOREVER = 0;
    a d;
    int f;
    int g;
    boolean h;
    int i;
    int j;
    int k;
    @ColorInt
    int l;
    @ColorInt
    int[] a = null;
    int b = 0;
    int c = 0;
    final List<a> e = new ArrayList();
    int m = -1;

    public int getHeight() {
        return this.g;
    }

    public int getWidth() {
        return this.f;
    }

    public int getNumFrames() {
        return this.c;
    }

    public int getStatus() {
        return this.b;
    }
}
