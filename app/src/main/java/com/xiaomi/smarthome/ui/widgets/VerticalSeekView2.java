package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.ColorInt;
import com.umeng.analytics.pro.c;
import com.xiaomi.mico.base.exts.SizeExtsKt;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VerticalSeekView2.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 :2\u00020\u0001:\u0001:B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fJ\u0012\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u000102H\u0014J\u0010\u00103\u001a\u0002002\u0006\u00101\u001a\u000202H\u0002J\u0010\u00104\u001a\u0002002\u0006\u00101\u001a\u000202H\u0002J\u0012\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0017J\b\u00109\u001a\u000200H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010 \u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00078\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0013\"\u0004\b\"\u0010\u0015R$\u0010$\u001a\u00020#2\u0006\u0010\u0010\u001a\u00020#@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010)\u001a\u0004\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.¨\u0006;"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/VerticalSeekView2;", "Landroid/view/View;", c.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "currentX", "", "currentY", b.p, "endColor", "getEndColor", "()I", "setEndColor", "(I)V", "lastX", "lastY", "layerId", "paint", "Landroid/graphics/Paint;", "radius", "getRadius", "setRadius", "rectF", "Landroid/graphics/RectF;", "startColor", "getStartColor", "setStartColor", "", SchemaActivity.KEY_VOLUME, "getVolume", "()D", "setVolume", "(D)V", "volumeChangeListener", "Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;", "getVolumeChangeListener", "()Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;", "setVolumeChangeListener", "(Lcom/xiaomi/smarthome/ui/widgets/OnVolumeChangeListener;)V", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onDrawHandler", "onDrawUnderlyingLayer", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "refresh", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class VerticalSeekView2 extends View {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "VerticalSeekView2:";
    private float a;
    private float b;
    private float c;
    private float d;
    private final Paint e;
    private final RectF f;
    private int g;
    private int h;
    private double i;
    @Nullable
    private OnVolumeChangeListener j;
    @ColorInt
    private int k;
    @ColorInt
    private int l;

    public /* synthetic */ VerticalSeekView2(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView2(@NotNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.e = new Paint(1);
        this.f = new RectF();
        this.k = -1;
        this.l = -16776961;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VerticalSeekView2);
        boolean z = false;
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.VerticalSeekView2_radius, 0);
        setRadius(dimensionPixelOffset < 0 ? 0 : dimensionPixelOffset);
        setStartColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView2_startColor, -1));
        setEndColor(obtainStyledAttributes.getColor(R.styleable.VerticalSeekView2_endColor, -16776961));
        float f = 0.0f;
        Float valueOf = Float.valueOf(obtainStyledAttributes.getFloat(R.styleable.VerticalSeekView2_volume, 0.0f));
        float floatValue = valueOf.floatValue();
        valueOf = !(((floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1)) >= 0 || (floatValue > 100.0f ? 1 : (floatValue == 100.0f ? 0 : -1)) <= 0) ? true : z) ? null : valueOf;
        setVolume(valueOf != null ? valueOf.floatValue() : f);
        this.e.setAntiAlias(true);
        this.e.setDither(true);
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView2(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ VerticalSeekView2(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView2(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VerticalSeekView2(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* compiled from: VerticalSeekView2.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/VerticalSeekView2$Companion;", "", "()V", "TAG", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final int getRadius() {
        return this.h;
    }

    public final void setRadius(int i) {
        if (i >= 0 && 100 >= i && this.h != i) {
            this.h = i;
            a();
        }
    }

    public final double getVolume() {
        return this.i;
    }

    public final void setVolume(double d) {
        if (d >= 0.0d && d <= 1.0d && this.i != d) {
            this.i = d;
            a();
            OnVolumeChangeListener onVolumeChangeListener = this.j;
            if (onVolumeChangeListener != null) {
                onVolumeChangeListener.onVolumeChanged(d);
            }
        }
    }

    @Nullable
    public final OnVolumeChangeListener getVolumeChangeListener() {
        return this.j;
    }

    public final void setVolumeChangeListener(@Nullable OnVolumeChangeListener onVolumeChangeListener) {
        this.j = onVolumeChangeListener;
    }

    public final int getStartColor() {
        return this.k;
    }

    public final void setStartColor(int i) {
        if (i != this.k) {
            this.k = i;
            a();
        }
    }

    public final int getEndColor() {
        return this.l;
    }

    public final void setEndColor(int i) {
        if (i != this.l) {
            this.l = i;
            a();
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent motionEvent) {
        Integer valueOf = motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null;
        if ((valueOf != null && valueOf.intValue() == 0) || (valueOf != null && valueOf.intValue() == 2)) {
            if (motionEvent.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            this.a = motionEvent.getY();
            this.b = motionEvent.getX();
            if (Math.abs(this.c - this.a) < Math.abs(this.d - this.b)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            double height = (getHeight() - this.a) / getHeight();
            double d = height >= ((double) 1) ? 1.0d : height;
            if (height <= 0) {
                d = 0.0d;
            }
            setVolume(d);
            this.c = this.a;
            this.d = this.b;
            L.smarthome.d("VerticalSeekView2: onTouchEvent currentX=" + this.a + ",currentY=" + this.b);
            return true;
        }
        if ((valueOf != null && valueOf.intValue() == 1) || (valueOf != null && valueOf.intValue() == 3)) {
            this.a = 0.0f;
            this.b = 0.0f;
            this.d = 0.0f;
            this.c = 0.0f;
            getParent().requestDisallowInterceptTouchEvent(false);
            L.smarthome.d("VerticalSeekView2:requestDisallowInterceptTouchEvent:false");
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            this.g = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
            b(canvas);
            a(canvas);
            canvas.restoreToCount(this.g);
        }
    }

    private final void a(Canvas canvas) {
        RectF rectF = this.f;
        rectF.left = 0.0f;
        float height = (float) (canvas.getHeight() - (canvas.getHeight() * this.i));
        if (height >= canvas.getHeight() + SizeExtsKt.toPx(48)) {
            height = canvas.getHeight() + SizeExtsKt.toPx(48);
        } else if (height <= SizeExtsKt.toPx(48)) {
            height = SizeExtsKt.toPx(48);
        }
        rectF.top = height;
        this.f.right = getWidth();
        RectF rectF2 = this.f;
        rectF2.bottom = rectF2.top - SizeExtsKt.toPx(48);
        this.e.setColor(-1);
        canvas.drawRoundRect(this.f, 26.0f, 26.0f, this.e);
    }

    private final void b(Canvas canvas) {
        this.e.setStyle(Paint.Style.FILL);
        this.e.setShader(new LinearGradient(getWidth() / 2, 0.0f, getWidth() / 2, getHeight(), this.k, this.l, Shader.TileMode.REPEAT));
        RectF rectF = this.f;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = canvas.getWidth();
        this.f.bottom = canvas.getHeight();
        RectF rectF2 = this.f;
        int i = this.h;
        canvas.drawRoundRect(rectF2, i, i, this.e);
        this.e.setShader(null);
    }

    private final void a() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            postInvalidate();
        } else {
            invalidate();
        }
    }
}
