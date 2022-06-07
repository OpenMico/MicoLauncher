package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.common.base.Ascii;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class WebvttCssStyle {
    public static final int FONT_SIZE_UNIT_EM = 2;
    public static final int FONT_SIZE_UNIT_PERCENT = 3;
    public static final int FONT_SIZE_UNIT_PIXEL = 1;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_BOLD_ITALIC = 3;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_NORMAL = 0;
    public static final int UNSPECIFIED = -1;
    @ColorInt
    private int f;
    private int h;
    private float o;
    private String a = "";
    private String b = "";
    private Set<String> c = Collections.emptySet();
    private String d = "";
    @Nullable
    private String e = null;
    private boolean g = false;
    private boolean i = false;
    private int j = -1;
    private int k = -1;
    private int l = -1;
    private int m = -1;
    private int n = -1;
    private int p = -1;
    private boolean q = false;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface StyleFlags {
    }

    public void setTargetId(String str) {
        this.a = str;
    }

    public void setTargetTagName(String str) {
        this.b = str;
    }

    public void setTargetClasses(String[] strArr) {
        this.c = new HashSet(Arrays.asList(strArr));
    }

    public void setTargetVoice(String str) {
        this.d = str;
    }

    public int getSpecificityScore(@Nullable String str, @Nullable String str2, Set<String> set, @Nullable String str3) {
        if (this.a.isEmpty() && this.b.isEmpty() && this.c.isEmpty() && this.d.isEmpty()) {
            return TextUtils.isEmpty(str2) ? 1 : 0;
        }
        int a = a(a(a(0, this.a, str, 1073741824), this.b, str2, 2), this.d, str3, 4);
        if (a == -1 || !set.containsAll(this.c)) {
            return 0;
        }
        return a + (this.c.size() * 4);
    }

    public int getStyle() {
        if (this.l == -1 && this.m == -1) {
            return -1;
        }
        int i = 0;
        int i2 = this.l == 1 ? 1 : 0;
        if (this.m == 1) {
            i = 2;
        }
        return i2 | i;
    }

    public boolean isLinethrough() {
        return this.j == 1;
    }

    public WebvttCssStyle setLinethrough(boolean z) {
        this.j = z ? 1 : 0;
        return this;
    }

    public boolean isUnderline() {
        return this.k == 1;
    }

    public WebvttCssStyle setUnderline(boolean z) {
        this.k = z ? 1 : 0;
        return this;
    }

    public WebvttCssStyle setBold(boolean z) {
        this.l = z ? 1 : 0;
        return this;
    }

    public WebvttCssStyle setItalic(boolean z) {
        this.m = z ? 1 : 0;
        return this;
    }

    @Nullable
    public String getFontFamily() {
        return this.e;
    }

    public WebvttCssStyle setFontFamily(@Nullable String str) {
        this.e = str == null ? null : Ascii.toLowerCase(str);
        return this;
    }

    public int getFontColor() {
        if (this.g) {
            return this.f;
        }
        throw new IllegalStateException("Font color not defined");
    }

    public WebvttCssStyle setFontColor(int i) {
        this.f = i;
        this.g = true;
        return this;
    }

    public boolean hasFontColor() {
        return this.g;
    }

    public int getBackgroundColor() {
        if (this.i) {
            return this.h;
        }
        throw new IllegalStateException("Background color not defined.");
    }

    public WebvttCssStyle setBackgroundColor(int i) {
        this.h = i;
        this.i = true;
        return this;
    }

    public boolean hasBackgroundColor() {
        return this.i;
    }

    public WebvttCssStyle setFontSize(float f) {
        this.o = f;
        return this;
    }

    public WebvttCssStyle setFontSizeUnit(int i) {
        this.n = i;
        return this;
    }

    public int getFontSizeUnit() {
        return this.n;
    }

    public float getFontSize() {
        return this.o;
    }

    public WebvttCssStyle setRubyPosition(int i) {
        this.p = i;
        return this;
    }

    public int getRubyPosition() {
        return this.p;
    }

    public WebvttCssStyle setCombineUpright(boolean z) {
        this.q = z;
        return this;
    }

    public boolean getCombineUpright() {
        return this.q;
    }

    private static int a(int i, String str, @Nullable String str2, int i2) {
        if (str.isEmpty() || i == -1) {
            return i;
        }
        if (str.equals(str2)) {
            return i + i2;
        }
        return -1;
    }
}
