package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import com.umeng.analytics.pro.ai;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.jetbrains.annotations.NotNull;

/* compiled from: Rect.kt */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0000\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f\u001a\r\u0010\u0004\u001a\u00020\u0005*\u00020\u0001H\u0086\n\u001a\r\u0010\u0004\u001a\u00020\u0006*\u00020\u0003H\u0086\n\u001a\r\u0010\u0007\u001a\u00020\u0005*\u00020\u0001H\u0086\n\u001a\r\u0010\u0007\u001a\u00020\u0006*\u00020\u0003H\u0086\n\u001a\r\u0010\b\u001a\u00020\u0005*\u00020\u0001H\u0086\n\u001a\r\u0010\b\u001a\u00020\u0006*\u00020\u0003H\u0086\n\u001a\r\u0010\t\u001a\u00020\u0005*\u00020\u0001H\u0086\n\u001a\r\u0010\t\u001a\u00020\u0006*\u00020\u0003H\u0086\n\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0086\n\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\f\u001a\u00020\u000eH\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\rH\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0011*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0005H\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000eH\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\n\u001a\u0015\u0010\u000f\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0006H\u0086\n\u001a\u0015\u0010\u0012\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\f\u001a\u0015\u0010\u0012\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\f\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\rH\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0005H\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000eH\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0006H\u0086\n\u001a\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0005H\u0086\n\u001a\u0015\u0010\u0014\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0006H\u0086\n\u001a\u0015\u0010\u0014\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0005H\u0086\n\u001a\r\u0010\u0016\u001a\u00020\u0001*\u00020\u0003H\u0086\b\u001a\r\u0010\u0017\u001a\u00020\u0003*\u00020\u0001H\u0086\b\u001a\r\u0010\u0018\u001a\u00020\u0011*\u00020\u0001H\u0086\b\u001a\r\u0010\u0018\u001a\u00020\u0011*\u00020\u0003H\u0086\b\u001a\u0015\u0010\u0019\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0086\f\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\f¨\u0006\u001d"}, d2 = {"and", "Landroid/graphics/Rect;", "r", "Landroid/graphics/RectF;", "component1", "", "", "component2", "component3", "component4", "contains", "", ai.av, "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "minus", "xy", "Landroid/graphics/Region;", "or", "plus", "times", "factor", "toRect", "toRectF", "toRegion", "transform", MessageElement.XPATH_PREFIX, "Landroid/graphics/Matrix;", "xor", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class RectKt {
    public static final int component1(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return rect.left;
    }

    public static final int component2(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return rect.top;
    }

    public static final int component3(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return rect.right;
    }

    public static final int component4(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return rect.bottom;
    }

    public static final float component1(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.left;
    }

    public static final float component2(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.top;
    }

    public static final float component3(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.right;
    }

    public static final float component4(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        return rectF.bottom;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect rect, @NotNull Rect r) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Rect rect2 = new Rect(rect);
        rect2.union(r);
        return rect2;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF rectF, @NotNull RectF r) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        RectF rectF2 = new RectF(rectF);
        rectF2.union(r);
        return rectF2;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect rect, int i) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Rect rect2 = new Rect(rect);
        rect2.offset(i, i);
        return rect2;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF rectF, float f) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(f, f);
        return rectF2;
    }

    @NotNull
    public static final Rect plus(@NotNull Rect rect, @NotNull Point xy) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(xy, "xy");
        Rect rect2 = new Rect(rect);
        rect2.offset(xy.x, xy.y);
        return rect2;
    }

    @NotNull
    public static final RectF plus(@NotNull RectF rectF, @NotNull PointF xy) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(xy, "xy");
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(xy.x, xy.y);
        return rectF2;
    }

    @NotNull
    public static final Region minus(@NotNull Rect rect, @NotNull Rect r) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Region region = new Region(rect);
        region.op(r, Region.Op.DIFFERENCE);
        return region;
    }

    @NotNull
    public static final Region minus(@NotNull RectF rectF, @NotNull RectF r) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Rect rect = new Rect();
        rectF.roundOut(rect);
        Region region = new Region(rect);
        Rect rect2 = new Rect();
        r.roundOut(rect2);
        region.op(rect2, Region.Op.DIFFERENCE);
        return region;
    }

    @NotNull
    public static final Rect minus(@NotNull Rect rect, int i) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Rect rect2 = new Rect(rect);
        int i2 = -i;
        rect2.offset(i2, i2);
        return rect2;
    }

    @NotNull
    public static final RectF minus(@NotNull RectF rectF, float f) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        RectF rectF2 = new RectF(rectF);
        float f2 = -f;
        rectF2.offset(f2, f2);
        return rectF2;
    }

    @NotNull
    public static final Rect minus(@NotNull Rect rect, @NotNull Point xy) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(xy, "xy");
        Rect rect2 = new Rect(rect);
        rect2.offset(-xy.x, -xy.y);
        return rect2;
    }

    @NotNull
    public static final RectF minus(@NotNull RectF rectF, @NotNull PointF xy) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(xy, "xy");
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(-xy.x, -xy.y);
        return rectF2;
    }

    @NotNull
    public static final Rect times(@NotNull Rect rect, int i) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Rect rect2 = new Rect(rect);
        rect2.top *= i;
        rect2.left *= i;
        rect2.right *= i;
        rect2.bottom *= i;
        return rect2;
    }

    @NotNull
    public static final RectF times(@NotNull RectF rectF, float f) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        RectF rectF2 = new RectF(rectF);
        rectF2.top *= f;
        rectF2.left *= f;
        rectF2.right *= f;
        rectF2.bottom *= f;
        return rectF2;
    }

    @SuppressLint({"CheckResult"})
    @NotNull
    public static final Rect and(@NotNull Rect rect, @NotNull Rect r) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Rect rect2 = new Rect(rect);
        rect2.intersect(r);
        return rect2;
    }

    @SuppressLint({"CheckResult"})
    @NotNull
    public static final RectF and(@NotNull RectF rectF, @NotNull RectF r) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        RectF rectF2 = new RectF(rectF);
        rectF2.intersect(r);
        return rectF2;
    }

    @NotNull
    public static final Region xor(@NotNull Rect rect, @NotNull Rect r) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Region region = new Region(rect);
        region.op(r, Region.Op.XOR);
        return region;
    }

    @NotNull
    public static final Region xor(@NotNull RectF rectF, @NotNull RectF r) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Rect rect = new Rect();
        rectF.roundOut(rect);
        Region region = new Region(rect);
        Rect rect2 = new Rect();
        r.roundOut(rect2);
        region.op(rect2, Region.Op.XOR);
        return region;
    }

    public static final boolean contains(@NotNull Rect rect, @NotNull Point p) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        return rect.contains(p.x, p.y);
    }

    public static final boolean contains(@NotNull RectF rectF, @NotNull PointF p) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(p, "p");
        return rectF.contains(p.x, p.y);
    }

    @NotNull
    public static final RectF toRectF(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return new RectF(rect);
    }

    @NotNull
    public static final Rect toRect(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return rect;
    }

    @NotNull
    public static final Region toRegion(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        return new Region(rect);
    }

    @NotNull
    public static final Region toRegion(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return new Region(rect);
    }

    @NotNull
    public static final RectF transform(@NotNull RectF rectF, @NotNull Matrix m) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(m, "m");
        m.mapRect(rectF);
        return rectF;
    }

    @NotNull
    public static final RectF times(@NotNull RectF rectF, int i) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        float f = i;
        RectF rectF2 = new RectF(rectF);
        rectF2.top *= f;
        rectF2.left *= f;
        rectF2.right *= f;
        rectF2.bottom *= f;
        return rectF2;
    }

    @NotNull
    public static final Rect or(@NotNull Rect rect, @NotNull Rect r) {
        Intrinsics.checkNotNullParameter(rect, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        Rect rect2 = new Rect(rect);
        rect2.union(r);
        return rect2;
    }

    @NotNull
    public static final RectF or(@NotNull RectF rectF, @NotNull RectF r) {
        Intrinsics.checkNotNullParameter(rectF, "<this>");
        Intrinsics.checkNotNullParameter(r, "r");
        RectF rectF2 = new RectF(rectF);
        rectF2.union(r);
        return rectF2;
    }
}
