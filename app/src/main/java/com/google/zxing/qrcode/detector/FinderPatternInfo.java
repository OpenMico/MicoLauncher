package com.google.zxing.qrcode.detector;

/* loaded from: classes2.dex */
public final class FinderPatternInfo {
    private final FinderPattern a;
    private final FinderPattern b;
    private final FinderPattern c;

    public FinderPatternInfo(FinderPattern[] finderPatternArr) {
        this.a = finderPatternArr[0];
        this.b = finderPatternArr[1];
        this.c = finderPatternArr[2];
    }

    public FinderPattern getBottomLeft() {
        return this.a;
    }

    public FinderPattern getTopLeft() {
        return this.b;
    }

    public FinderPattern getTopRight() {
        return this.c;
    }
}
