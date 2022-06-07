package com.google.android.exoplayer2.decoder;

/* loaded from: classes.dex */
public abstract class Buffer {
    private int a;

    public void clear() {
        this.a = 0;
    }

    public final boolean isDecodeOnly() {
        return getFlag(Integer.MIN_VALUE);
    }

    public final boolean isEndOfStream() {
        return getFlag(4);
    }

    public final boolean isKeyFrame() {
        return getFlag(1);
    }

    public final boolean hasSupplementalData() {
        return getFlag(268435456);
    }

    public final void setFlags(int i) {
        this.a = i;
    }

    public final void addFlag(int i) {
        this.a = i | this.a;
    }

    public final void clearFlag(int i) {
        this.a = (~i) & this.a;
    }

    protected final boolean getFlag(int i) {
        return (this.a & i) == i;
    }
}
