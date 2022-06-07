package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.databinding.BindingAdapter;
import com.elvishew.xlog.Logger;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.xiaomi.mico.base.exts.SizeExtsKt;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.DeviceTypeDic;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VerticalSeekView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 e2\u00020\u0001:\u0001eB%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fJ\u0012\u0010W\u001a\u00020X2\b\u0010Y\u001a\u0004\u0018\u00010ZH\u0014J\u0010\u0010[\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0010\u0010\\\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0010\u0010]\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0010\u0010^\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0012\u0010_\u001a\u00020\u00102\b\u0010`\u001a\u0004\u0018\u00010aH\u0017J\b\u0010b\u001a\u00020XH\u0002J\u000e\u0010c\u001a\u00020X2\u0006\u0010d\u001a\u00020DR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR&\u0010\u001f\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010&\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010!\"\u0004\b(\u0010#R&\u0010)\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010!\"\u0004\b+\u0010#R\u000e\u0010,\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010-\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0013\"\u0004\b.\u0010\u0015R\u000e\u0010/\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u00103\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b5\u0010\u001e\u001a\u0004\b4\u0010!R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R$\u00108\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010!\"\u0004\b:\u0010#R\u000e\u0010;\u001a\u00020<X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010=\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u0013\"\u0004\b?\u0010\u0015R&\u0010@\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010!\"\u0004\bB\u0010#R\u001a\u0010C\u001a\u00020DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u000e\u0010I\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010K\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010!\"\u0004\bM\u0010#R$\u0010N\u001a\u00020D2\u0006\u0010\u000f\u001a\u00020D@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010F\"\u0004\bP\u0010HR\u001c\u0010Q\u001a\u0004\u0018\u00010RX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010V¨\u0006f"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/VerticalSeekView;", "Landroid/view/View;", c.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "bitmap", "Landroid/graphics/Bitmap;", com.xiaomi.onetrack.api.b.p, "", "canDrag", "getCanDrag", "()Z", "setCanDrag", "(Z)V", "currentX", "", "currentY", "destRect", "Landroid/graphics/Rect;", "getDestRect", "()Landroid/graphics/Rect;", "destRect$delegate", "Lkotlin/Lazy;", "deviceTypeIcon", "getDeviceTypeIcon", "()I", "setDeviceTypeIcon", "(I)V", "downTimestamp", "", "endColor", "getEndColor", "setEndColor", "foregroundColor", "getForegroundColor", "setForegroundColor", "isFistMoveAction", "isTouching", "setTouching", "isVolumeChange", "lastX", "lastY", "layerId", "minTouchSlop", "getMinTouchSlop", "minTouchSlop$delegate", "paint", "Landroid/graphics/Paint;", "radius", "getRadius", "setRadius", "rectF", "Landroid/graphics/RectF;", "showIcon", "getShowIcon", "setShowIcon", "startColor", "getStartColor", "setStartColor", "startVolume", "", "getStartVolume", "()D", "setStartVolume", "(D)V", "startX", "startY", "underlyingColor", "getUnderlyingColor", "setUnderlyingColor", SchemaActivity.KEY_VOLUME, "getVolume", "setVolume", "volumeChangeListener", "Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;", "getVolumeChangeListener", "()Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;", "setVolumeChangeListener", "(Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;)V", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onDrawForegroundLayer", "onDrawHandler", "onDrawIcon", "onDrawUnderlyingLayer", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "refresh", "updateVolume", ai.aC, "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class VerticalSeekView extends View {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "VerticalSeekView:";
    private final Lazy A;
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private boolean g;
    private boolean h;
    private final Paint i;
    private final RectF j;
    private int k;
    private long l;
    @ColorInt
    private int m;
    @ColorInt
    private int n;
    @ColorInt
    private int o;
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    @DrawableRes
    private int t;
    private Bitmap u;
    private final Lazy v;
    @ColorInt
    private int w;
    private double x;
    private double y;
    @Nullable
    private OnVolumeChangeListener z;

    @BindingAdapter({"data"})
    @JvmStatic
    public static final void bindData(@NotNull VerticalSeekView verticalSeekView, @NotNull DeviceInfoWrapper deviceInfoWrapper) {
        Companion.bindData(verticalSeekView, deviceInfoWrapper);
    }

    private final Rect getDestRect() {
        return (Rect) this.v.getValue();
    }

    private final int getMinTouchSlop() {
        return ((Number) this.A.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VerticalSeekView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b extends Lambda implements Function0<Integer> {
        final /* synthetic */ Context $context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(Context context) {
            super(0);
            this.$context = context;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* synthetic */ Integer invoke() {
            return Integer.valueOf(a());
        }

        public final int a() {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(this.$context);
            Intrinsics.checkNotNullExpressionValue(viewConfiguration, "ViewConfiguration.get(context)");
            return viewConfiguration.getScaledTouchSlop();
        }
    }

    public /* synthetic */ VerticalSeekView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.g = true;
        this.i = new Paint(1);
        this.j = new RectF();
        this.m = context.getColor(R.color.sh_miot_seek_view_foreground_color);
        int i3 = this.m;
        this.n = i3;
        this.o = i3;
        this.r = true;
        this.t = R.drawable.sh_ic_iot_lamp;
        this.v = LazyKt.lazy(new a());
        this.w = context.getColor(R.color.sh_miot_seek_view_background_color);
        this.y = this.x;
        this.A = LazyKt.lazy(new b(context));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VerticalSeekView);
        boolean z = false;
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.VerticalSeekView_radius, 0);
        setRadius(dimensionPixelOffset < 0 ? 0 : dimensionPixelOffset);
        setDeviceTypeIcon(obtainStyledAttributes.getResourceId(R.styleable.VerticalSeekView_deviceTypeIcon, R.drawable.sh_ic_iot_lamp));
        Drawable drawable = ContextCompat.getDrawable(context, this.t);
        this.u = drawable != null ? DrawableKt.toBitmap$default(drawable, 0, 0, null, 7, null) : null;
        setUnderlyingColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView_underlyingLayerColor, context.getColor(R.color.sh_miot_seek_view_background_color)));
        setForegroundColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView_foregroundLayerColor, context.getColor(R.color.sh_miot_seek_view_foreground_color)));
        float f = 0.0f;
        Float valueOf = Float.valueOf(obtainStyledAttributes.getFloat(R.styleable.VerticalSeekView_volume, 0.0f));
        float floatValue = valueOf.floatValue();
        valueOf = !(((floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1)) >= 0 || (floatValue > 100.0f ? 1 : (floatValue == 100.0f ? 0 : -1)) <= 0) ? true : z) ? null : valueOf;
        setVolume(valueOf != null ? valueOf.floatValue() : f);
        setStartColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView_startColor, this.m));
        setEndColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView_endColor, this.m));
        setCanDrag(obtainStyledAttributes.getBoolean(R.styleable.VerticalSeekView_canDrag, true));
        this.i.setAntiAlias(true);
        this.i.setDither(true);
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ VerticalSeekView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* compiled from: VerticalSeekView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/VerticalSeekView$Companion;", "", "()V", "TAG", "", "bindData", "", OneTrack.Event.VIEW, "Lcom/xiaomi/smarthome/ui/widgets/VerticalSeekView;", "data", "Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @BindingAdapter({"data"})
        @JvmStatic
        public final void bindData(@NotNull VerticalSeekView view, @NotNull DeviceInfoWrapper data) {
            int i;
            int i2;
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(data, "data");
            DeviceInfo deviceInfo = data.getDeviceInfo();
            ModelInfo modelInfo = data.getModelInfo();
            Intrinsics.checkNotNullExpressionValue(modelInfo, "data.modelInfo");
            String deviceType = modelInfo.getDeviceType();
            if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_LIGHT_LIGHT.getDeviceType()) || Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_LIGHT_MIRROR.getDeviceType()) || Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_LIGHT_NIGHT_LIGHT.getDeviceType())) {
                view.setShowIcon(true);
                if (deviceInfo.currentStatus) {
                    i = R.drawable.sh_ic_light_icon_on;
                } else {
                    i = R.drawable.sh_ic_light_icon_off;
                }
                view.setDeviceTypeIcon(i);
            } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_HEATER.getDeviceType())) {
                view.setShowIcon(true);
                if (deviceInfo.currentStatus) {
                    i2 = R.drawable.sh_ic_heater_temperature_on;
                } else {
                    i2 = R.drawable.sh_ic_heater_temperature_off;
                }
                view.setDeviceTypeIcon(i2);
                view.setForegroundColor(ContextCompat.getColor(view.getContext(), deviceInfo.currentStatus ? R.color.sh_miot_heater_foreground_color : R.color.sh_miot_heater_foreground_color_off));
            }
        }
    }

    public final int getForegroundColor() {
        return this.m;
    }

    public final void setForegroundColor(int i) {
        if (i != this.m) {
            this.m = i;
            a();
        }
    }

    public final int getStartColor() {
        return this.n;
    }

    public final void setStartColor(int i) {
        if (i != this.n) {
            this.n = i;
            a();
        }
    }

    public final int getEndColor() {
        return this.o;
    }

    public final void setEndColor(int i) {
        if (i != this.o) {
            this.o = i;
            a();
        }
    }

    public final int getRadius() {
        return this.p;
    }

    public final void setRadius(int i) {
        if (i >= 0 && 100 >= i && this.p != i) {
            this.p = i;
            a();
        }
    }

    public final boolean getShowIcon() {
        return this.q;
    }

    public final void setShowIcon(boolean z) {
        if (z != this.q) {
            this.q = z;
            a();
        }
    }

    public final boolean getCanDrag() {
        return this.r;
    }

    public final void setCanDrag(boolean z) {
        if (z != this.r) {
            this.r = z;
        }
    }

    public final boolean isTouching() {
        return this.s;
    }

    public final void setTouching(boolean z) {
        if (z != this.s) {
            this.s = z;
        }
    }

    public final int getDeviceTypeIcon() {
        return this.t;
    }

    public final void setDeviceTypeIcon(int i) {
        if (i != this.t) {
            this.t = i;
            Drawable drawable = ContextCompat.getDrawable(getContext(), i);
            this.u = drawable != null ? DrawableKt.toBitmap$default(drawable, 0, 0, null, 7, null) : null;
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VerticalSeekView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroid/graphics/Rect;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a extends Lambda implements Function0<Rect> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super(0);
            VerticalSeekView.this = r1;
        }

        @NotNull
        /* renamed from: a */
        public final Rect invoke() {
            int width = VerticalSeekView.this.getWidth();
            Bitmap bitmap = VerticalSeekView.this.u;
            Integer num = null;
            Integer valueOf = bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null;
            Intrinsics.checkNotNull(valueOf);
            int intValue = (width - valueOf.intValue()) / 2;
            int height = VerticalSeekView.this.getHeight() - SizeExtsKt.toPx(24);
            Bitmap bitmap2 = VerticalSeekView.this.u;
            Integer valueOf2 = bitmap2 != null ? Integer.valueOf(bitmap2.getHeight()) : null;
            Intrinsics.checkNotNull(valueOf2);
            int intValue2 = height - valueOf2.intValue();
            int width2 = VerticalSeekView.this.getWidth();
            Bitmap bitmap3 = VerticalSeekView.this.u;
            if (bitmap3 != null) {
                num = Integer.valueOf(bitmap3.getWidth());
            }
            Intrinsics.checkNotNull(num);
            return new Rect(intValue, intValue2, (width2 + num.intValue()) / 2, VerticalSeekView.this.getHeight() - SizeExtsKt.toPx(24));
        }
    }

    public final int getUnderlyingColor() {
        return this.w;
    }

    public final void setUnderlyingColor(int i) {
        if (i != this.w) {
            this.w = i;
            a();
        }
    }

    public final double getVolume() {
        return this.x;
    }

    public final void setVolume(double d) {
        if (d >= 0.0d && d <= 1.0d && this.x != d) {
            this.x = d;
            OnVolumeChangeListener onVolumeChangeListener = this.z;
            if (onVolumeChangeListener != null) {
                onVolumeChangeListener.onVolumeChanged(d);
            }
            a();
        }
    }

    public final double getStartVolume() {
        return this.y;
    }

    public final void setStartVolume(double d) {
        this.y = d;
    }

    @Nullable
    public final OnVolumeChangeListener getVolumeChangeListener() {
        return this.z;
    }

    public final void setVolumeChangeListener(@Nullable OnVolumeChangeListener onVolumeChangeListener) {
        this.z = onVolumeChangeListener;
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent motionEvent) {
        if (!this.r) {
            return super.onTouchEvent(motionEvent);
        }
        Integer valueOf = motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null;
        if (valueOf != null && valueOf.intValue() == 0) {
            this.c = motionEvent.getX();
            this.d = motionEvent.getY();
            this.l = System.currentTimeMillis();
            Logger logger = L.smarthome;
            logger.d("VerticalSeekView: onTouchEvent ACTION_DOWN downX=" + this.c + ",downY=" + this.d + ",minTouchSlop=" + getMinTouchSlop());
            OnVolumeChangeListener onVolumeChangeListener = this.z;
            if (onVolumeChangeListener != null) {
                onVolumeChangeListener.onVolumeChangedBegin(this.x);
            }
            this.y = this.x;
            return true;
        }
        if (valueOf != null && valueOf.intValue() == 2) {
            this.a = motionEvent.getX();
            this.b = motionEvent.getY();
            float f = this.c - this.a;
            float f2 = this.d - this.b;
            if (Math.abs(f2) > Math.abs(f)) {
                getParent().requestDisallowInterceptTouchEvent(true);
                if (Math.abs(f2) > getMinTouchSlop()) {
                    if (!this.g) {
                        setTouching(true);
                        double height = (f2 / getHeight()) + this.y;
                        double d = height >= ((double) 1) ? 1.0d : height;
                        if (height <= 0) {
                            d = 0.0d;
                        }
                        setVolume(d);
                        this.h = true;
                        Logger logger2 = L.smarthome;
                        logger2.d("VerticalSeekView: onTouchEvent ACTION_MOVE setVolume=" + this.x);
                        return true;
                    }
                    this.g = false;
                }
            }
            this.e = this.a;
            this.f = this.b;
        } else if ((valueOf != null && valueOf.intValue() == 1) || (valueOf != null && valueOf.intValue() == 3)) {
            setTouching(false);
            this.a = 0.0f;
            this.b = 0.0f;
            this.f = 0.0f;
            this.e = 0.0f;
            this.g = true;
            getParent().requestDisallowInterceptTouchEvent(false);
            Logger logger3 = L.smarthome;
            StringBuilder sb = new StringBuilder();
            sb.append("VerticalSeekView: ");
            sb.append(motionEvent.getAction() == 1 ? "ACTION_UP" : "ACTION_CANCEL");
            sb.append(" requestDisallowInterceptTouchEvent:false");
            logger3.d(sb.toString());
            boolean z = System.currentTimeMillis() - this.l >= ((long) ViewConfiguration.getLongPressTimeout());
            if (motionEvent.getAction() == 1 && !this.h && !z) {
                performClick();
            }
            if (this.h) {
                this.h = false;
            }
            OnVolumeChangeListener onVolumeChangeListener2 = this.z;
            if (onVolumeChangeListener2 != null) {
                onVolumeChangeListener2.onVolumeChangedEnd(this.h, this.x);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            this.k = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
            c(canvas);
            d(canvas);
            b(canvas);
            a(canvas);
            canvas.restoreToCount(this.k);
        }
    }

    public final void updateVolume(double d) {
        if (!this.s) {
            setVolume(d);
        }
    }

    private final void a(Canvas canvas) {
        Bitmap bitmap;
        if (this.q && (bitmap = this.u) != null) {
            Logger logger = L.smarthome;
            logger.d("VerticalSeekView:,width=" + bitmap.getWidth() + ",height=" + bitmap.getHeight());
            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), getDestRect(), (Paint) null);
        }
    }

    private final void b(Canvas canvas) {
        if (this.r) {
            this.j.left = (getWidth() - SizeExtsKt.toPx(40)) / 2;
            this.j.top = (float) ((canvas.getHeight() - (canvas.getHeight() * this.x)) + SizeExtsKt.toPx(8));
            this.j.right = (getWidth() / 2) + 17;
            RectF rectF = this.j;
            rectF.bottom = rectF.top - 4;
            this.i.setColor(ContextCompat.getColor(getContext(), R.color.vertical_seek_bar_handler_color));
            canvas.drawRoundRect(this.j, 1.5f, 1.5f, this.i);
        }
    }

    private final void c(Canvas canvas) {
        this.i.setStyle(Paint.Style.FILL);
        this.i.setColor(this.w);
        RectF rectF = this.j;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = canvas.getWidth();
        this.j.bottom = canvas.getHeight();
        RectF rectF2 = this.j;
        int i = this.p;
        canvas.drawRoundRect(rectF2, i, i, this.i);
    }

    private final void d(Canvas canvas) {
        this.i.setStyle(Paint.Style.FILL);
        this.i.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        float height = (float) (canvas.getHeight() - (canvas.getHeight() * this.x));
        float height2 = canvas.getHeight();
        int i = this.n;
        int i2 = this.m;
        if (i == i2 && this.o == i2) {
            this.i.setColor(i2);
        } else {
            this.i.setShader(new LinearGradient(getWidth() / 2, height, getWidth() / 2, height2, this.n, this.o, Shader.TileMode.CLAMP));
        }
        RectF rectF = this.j;
        rectF.left = 0.0f;
        rectF.top = height;
        rectF.right = canvas.getWidth();
        RectF rectF2 = this.j;
        rectF2.bottom = height2;
        canvas.drawRect(rectF2, this.i);
        this.i.setXfermode(null);
        this.i.setShader(null);
    }

    private final void a() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            postInvalidate();
        } else {
            invalidate();
        }
    }
}
