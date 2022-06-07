package androidx.core.graphics;

import android.graphics.Color;
import android.graphics.ColorSpace;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import com.umeng.analytics.pro.ai;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Color.kt */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\r\u0010\u0018\u001a\u00020\u0004*\u00020\u0019H\u0087\n\u001a\r\u0010\u0018\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\r\u0010\u0018\u001a\u00020\u0004*\u00020\u0005H\u0087\n\u001a\r\u0010\u001a\u001a\u00020\u0004*\u00020\u0019H\u0087\n\u001a\r\u0010\u001a\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\r\u0010\u001a\u001a\u00020\u0004*\u00020\u0005H\u0087\n\u001a\r\u0010\u001b\u001a\u00020\u0004*\u00020\u0019H\u0087\n\u001a\r\u0010\u001b\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\r\u0010\u001b\u001a\u00020\u0004*\u00020\u0005H\u0087\n\u001a\r\u0010\u001c\u001a\u00020\u0004*\u00020\u0019H\u0087\n\u001a\r\u0010\u001c\u001a\u00020\u0001*\u00020\u0001H\u0086\n\u001a\r\u0010\u001c\u001a\u00020\u0004*\u00020\u0005H\u0087\n\u001a\u0015\u0010\u001d\u001a\u00020\u0019*\u00020\u00192\u0006\u0010\t\u001a\u00020\nH\u0087\f\u001a\u0015\u0010\u001d\u001a\u00020\u0019*\u00020\u00192\u0006\u0010\t\u001a\u00020\u001eH\u0087\f\u001a\u0015\u0010\u001d\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0087\f\u001a\u0015\u0010\u001d\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\t\u001a\u00020\u001eH\u0087\f\u001a\u0015\u0010\u001d\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0087\f\u001a\u0015\u0010\u001d\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\t\u001a\u00020\u001eH\u0087\f\u001a\u0015\u0010\u001f\u001a\u00020\u0019*\u00020\u00192\u0006\u0010 \u001a\u00020\u0019H\u0087\u0002\u001a\r\u0010!\u001a\u00020\u0019*\u00020\u0001H\u0087\b\u001a\r\u0010!\u001a\u00020\u0019*\u00020\u0005H\u0087\b\u001a\r\u0010\"\u001a\u00020\u0001*\u00020\u0005H\u0087\b\u001a\r\u0010\"\u001a\u00020\u0001*\u00020#H\u0087\b\u001a\r\u0010$\u001a\u00020\u0005*\u00020\u0001H\u0087\b\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0000\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006\"\u0016\u0010\u0007\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0003\"\u0016\u0010\u0007\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006\"\u0016\u0010\t\u001a\u00020\n*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0016\u0010\r\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0003\"\u0016\u0010\r\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006\"\u0016\u0010\u000f\u001a\u00020\u0010*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011\"\u0016\u0010\u0012\u001a\u00020\u0010*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011\"\u0016\u0010\u0013\u001a\u00020\u0004*\u00020\u00018Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0016\u0010\u0013\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006\"\u0016\u0010\u0016\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0003\"\u0016\u0010\u0016\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0006¨\u0006%"}, d2 = {"alpha", "", "getAlpha", "(I)I", "", "", "(J)F", "blue", "getBlue", "colorSpace", "Landroid/graphics/ColorSpace;", "getColorSpace", "(J)Landroid/graphics/ColorSpace;", "green", "getGreen", "isSrgb", "", "(J)Z", "isWideGamut", "luminance", "getLuminance", "(I)F", "red", "getRed", "component1", "Landroid/graphics/Color;", "component2", "component3", "component4", "convertTo", "Landroid/graphics/ColorSpace$Named;", "plus", ai.aD, "toColor", "toColorInt", "", "toColorLong", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ColorKt {
    public static final int component1(@ColorInt int i) {
        return (i >> 24) & 255;
    }

    public static final int component2(@ColorInt int i) {
        return (i >> 16) & 255;
    }

    public static final int component3(@ColorInt int i) {
        return (i >> 8) & 255;
    }

    public static final int component4(@ColorInt int i) {
        return i & 255;
    }

    public static final int getAlpha(@ColorInt int i) {
        return (i >> 24) & 255;
    }

    public static final int getBlue(@ColorInt int i) {
        return i & 255;
    }

    public static final int getGreen(@ColorInt int i) {
        return (i >> 8) & 255;
    }

    public static final int getRed(@ColorInt int i) {
        return (i >> 16) & 255;
    }

    @RequiresApi(26)
    public static final float component1(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        return color.getComponent(0);
    }

    @RequiresApi(26)
    public static final float component2(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        return color.getComponent(1);
    }

    @RequiresApi(26)
    public static final float component3(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        return color.getComponent(2);
    }

    @RequiresApi(26)
    public static final float component4(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        return color.getComponent(3);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color plus(@NotNull Color color, @NotNull Color c) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        Color compositeColors = ColorUtils.compositeColors(c, color);
        Intrinsics.checkNotNullExpressionValue(compositeColors, "compositeColors(c, this)");
        return compositeColors;
    }

    @RequiresApi(26)
    public static final float getLuminance(@ColorInt int i) {
        return Color.luminance(i);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color toColor(@ColorInt int i) {
        Color valueOf = Color.valueOf(i);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    public static final long toColorLong(@ColorInt int i) {
        return Color.pack(i);
    }

    @RequiresApi(26)
    public static final float component1(long j) {
        return Color.red(j);
    }

    @RequiresApi(26)
    public static final float component2(long j) {
        return Color.green(j);
    }

    @RequiresApi(26)
    public static final float component3(long j) {
        return Color.blue(j);
    }

    @RequiresApi(26)
    public static final float component4(long j) {
        return Color.alpha(j);
    }

    @RequiresApi(26)
    public static final float getAlpha(long j) {
        return Color.alpha(j);
    }

    @RequiresApi(26)
    public static final float getRed(long j) {
        return Color.red(j);
    }

    @RequiresApi(26)
    public static final float getGreen(long j) {
        return Color.green(j);
    }

    @RequiresApi(26)
    public static final float getBlue(long j) {
        return Color.blue(j);
    }

    @RequiresApi(26)
    public static final float getLuminance(long j) {
        return Color.luminance(j);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color toColor(long j) {
        Color valueOf = Color.valueOf(j);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    @ColorInt
    @RequiresApi(26)
    public static final int toColorInt(long j) {
        return Color.toArgb(j);
    }

    @RequiresApi(26)
    public static final boolean isSrgb(long j) {
        return Color.isSrgb(j);
    }

    @RequiresApi(26)
    public static final boolean isWideGamut(long j) {
        return Color.isWideGamut(j);
    }

    @RequiresApi(26)
    @NotNull
    public static final ColorSpace getColorSpace(long j) {
        ColorSpace colorSpace = Color.colorSpace(j);
        Intrinsics.checkNotNullExpressionValue(colorSpace, "colorSpace(this)");
        return colorSpace;
    }

    @RequiresApi(26)
    public static final long convertTo(@ColorInt int i, @NotNull ColorSpace.Named colorSpace) {
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        return Color.convert(i, ColorSpace.get(colorSpace));
    }

    @RequiresApi(26)
    public static final long convertTo(@ColorInt int i, @NotNull ColorSpace colorSpace) {
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        return Color.convert(i, colorSpace);
    }

    @RequiresApi(26)
    public static final long convertTo(long j, @NotNull ColorSpace.Named colorSpace) {
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        return Color.convert(j, ColorSpace.get(colorSpace));
    }

    @RequiresApi(26)
    public static final long convertTo(long j, @NotNull ColorSpace colorSpace) {
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        return Color.convert(j, colorSpace);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color convertTo(@NotNull Color color, @NotNull ColorSpace.Named colorSpace) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        Color convert = color.convert(ColorSpace.get(colorSpace));
        Intrinsics.checkNotNullExpressionValue(convert, "convert(ColorSpace.get(colorSpace))");
        return convert;
    }

    @RequiresApi(26)
    @NotNull
    public static final Color convertTo(@NotNull Color color, @NotNull ColorSpace colorSpace) {
        Intrinsics.checkNotNullParameter(color, "<this>");
        Intrinsics.checkNotNullParameter(colorSpace, "colorSpace");
        Color convert = color.convert(colorSpace);
        Intrinsics.checkNotNullExpressionValue(convert, "convert(colorSpace)");
        return convert;
    }

    @ColorInt
    public static final int toColorInt(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return Color.parseColor(str);
    }
}
