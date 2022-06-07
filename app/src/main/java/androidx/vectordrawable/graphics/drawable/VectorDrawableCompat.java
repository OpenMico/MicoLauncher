package androidx.vectordrawable.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class VectorDrawableCompat extends b {
    static final PorterDuff.Mode a = PorterDuff.Mode.SRC_IN;
    private g b;
    private PorterDuffColorFilter c;
    private ColorFilter e;
    private boolean f;
    private boolean g;
    private Drawable.ConstantState h;
    private final float[] i;
    private final Matrix j;
    private final Rect k;

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(int i, PorterDuff.Mode mode) {
        super.setColorFilter(i, mode);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    VectorDrawableCompat() {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.b = new g();
    }

    VectorDrawableCompat(@NonNull g gVar) {
        this.g = true;
        this.i = new float[9];
        this.j = new Matrix();
        this.k = new Rect();
        this.b = gVar;
        this.c = a(this.c, gVar.c, gVar.d);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (this.d != null) {
            this.d.mutate();
            return this;
        }
        if (!this.f && super.mutate() == this) {
            this.b = new g(this.b);
            this.f = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object a(String str) {
        return this.b.b.k.get(str);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.d != null && Build.VERSION.SDK_INT >= 24) {
            return new h(this.d.getConstantState());
        }
        this.b.a = getChangingConfigurations();
        return this.b;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.d != null) {
            this.d.draw(canvas);
            return;
        }
        copyBounds(this.k);
        if (this.k.width() > 0 && this.k.height() > 0) {
            ColorFilter colorFilter = this.e;
            if (colorFilter == null) {
                colorFilter = this.c;
            }
            canvas.getMatrix(this.j);
            this.j.getValues(this.i);
            float abs = Math.abs(this.i[0]);
            float abs2 = Math.abs(this.i[4]);
            float abs3 = Math.abs(this.i[1]);
            float abs4 = Math.abs(this.i[3]);
            if (!(abs3 == 0.0f && abs4 == 0.0f)) {
                abs = 1.0f;
                abs2 = 1.0f;
            }
            int min = Math.min(2048, (int) (this.k.width() * abs));
            int min2 = Math.min(2048, (int) (this.k.height() * abs2));
            if (min > 0 && min2 > 0) {
                int save = canvas.save();
                canvas.translate(this.k.left, this.k.top);
                if (a()) {
                    canvas.translate(this.k.width(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.k.offsetTo(0, 0);
                this.b.b(min, min2);
                if (!this.g) {
                    this.b.a(min, min2);
                } else if (!this.b.b()) {
                    this.b.a(min, min2);
                    this.b.c();
                }
                this.b.a(canvas, colorFilter, this.k);
                canvas.restoreToCount(save);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        if (this.d != null) {
            return DrawableCompat.getAlpha(this.d);
        }
        return this.b.b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.d != null) {
            this.d.setAlpha(i);
        } else if (this.b.b.getRootAlpha() != i) {
            this.b.b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.d != null) {
            this.d.setColorFilter(colorFilter);
            return;
        }
        this.e = colorFilter;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        if (this.d != null) {
            return DrawableCompat.getColorFilter(this.d);
        }
        return this.e;
    }

    PorterDuffColorFilter a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTint(int i) {
        if (this.d != null) {
            DrawableCompat.setTint(this.d, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(ColorStateList colorStateList) {
        if (this.d != null) {
            DrawableCompat.setTintList(this.d, colorStateList);
            return;
        }
        g gVar = this.b;
        if (gVar.c != colorStateList) {
            gVar.c = colorStateList;
            this.c = a(this.c, colorStateList, gVar.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.d != null) {
            DrawableCompat.setTintMode(this.d, mode);
            return;
        }
        g gVar = this.b;
        if (gVar.d != mode) {
            gVar.d = mode;
            this.c = a(this.c, gVar.c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        g gVar;
        if (this.d != null) {
            return this.d.isStateful();
        }
        return super.isStateful() || ((gVar = this.b) != null && (gVar.d() || (this.b.c != null && this.b.c.isStateful())));
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        if (this.d != null) {
            return this.d.setState(iArr);
        }
        boolean z = false;
        g gVar = this.b;
        if (!(gVar.c == null || gVar.d == null)) {
            this.c = a(this.c, gVar.c, gVar.d);
            invalidateSelf();
            z = true;
        }
        if (!gVar.d() || !gVar.a(iArr)) {
            return z;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.d != null) {
            return this.d.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.d != null) {
            return this.d.getIntrinsicWidth();
        }
        return (int) this.b.b.d;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.d != null) {
            return this.d.getIntrinsicHeight();
        }
        return (int) this.b.b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        if (this.d == null) {
            return false;
        }
        DrawableCompat.canApplyTheme(this.d);
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        if (this.d != null) {
            return DrawableCompat.isAutoMirrored(this.d);
        }
        return this.b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.d != null) {
            DrawableCompat.setAutoMirrored(this.d, z);
        } else {
            this.b.e = z;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public float getPixelSize() {
        g gVar = this.b;
        if (gVar == null || gVar.b == null || this.b.b.d == 0.0f || this.b.b.e == 0.0f || this.b.b.g == 0.0f || this.b.b.f == 0.0f) {
            return 1.0f;
        }
        float f2 = this.b.b.d;
        float f3 = this.b.b.e;
        return Math.min(this.b.b.f / f2, this.b.b.g / f3);
    }

    @Nullable
    public static VectorDrawableCompat create(@NonNull Resources resources, @DrawableRes int i, @Nullable Resources.Theme theme) {
        int next;
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = ResourcesCompat.getDrawable(resources, i, theme);
            vectorDrawableCompat.h = new h(vectorDrawableCompat.d.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
            while (true) {
                next = xml.next();
                if (next == 2 || next == 1) {
                    break;
                }
            }
            if (next == 2) {
                return createFromXmlInner(resources, (XmlPullParser) xml, asAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException e2) {
            Log.e("VectorDrawableCompat", "parser error", e2);
            return null;
        } catch (XmlPullParserException e3) {
            Log.e("VectorDrawableCompat", "parser error", e3);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    static int a(int i, float f2) {
        return (i & 16777215) | (((int) (Color.alpha(i) * f2)) << 24);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        if (this.d != null) {
            this.d.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (this.d != null) {
            DrawableCompat.inflate(this.d, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        g gVar = this.b;
        gVar.b = new f();
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, a.a);
        a(obtainAttributes, xmlPullParser, theme);
        obtainAttributes.recycle();
        gVar.a = getChangingConfigurations();
        gVar.k = true;
        a(resources, xmlPullParser, attributeSet, theme);
        this.c = a(this.c, gVar.c, gVar.d);
    }

    private static PorterDuff.Mode a(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    private void a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException {
        g gVar = this.b;
        f fVar = gVar.b;
        gVar.d = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList namedColorStateList = TypedArrayUtils.getNamedColorStateList(typedArray, xmlPullParser, theme, "tint", 1);
        if (namedColorStateList != null) {
            gVar.c = namedColorStateList;
        }
        gVar.e = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, gVar.e);
        fVar.f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, fVar.f);
        fVar.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, fVar.g);
        if (fVar.f <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (fVar.g > 0.0f) {
            fVar.d = typedArray.getDimension(3, fVar.d);
            fVar.e = typedArray.getDimension(2, fVar.e);
            if (fVar.d <= 0.0f) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (fVar.e > 0.0f) {
                fVar.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, fVar.getAlpha()));
                String string = typedArray.getString(0);
                if (string != null) {
                    fVar.i = string;
                    fVar.k.put(string, fVar);
                }
            } else {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
            }
        } else {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
    }

    private void a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        g gVar = this.b;
        f fVar = gVar.b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(fVar.c);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                c cVar = (c) arrayDeque.peek();
                if ("path".equals(name)) {
                    b bVar = new b();
                    bVar.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.b.add(bVar);
                    if (bVar.getPathName() != null) {
                        fVar.k.put(bVar.getPathName(), bVar);
                    }
                    z = false;
                    gVar.a = bVar.o | gVar.a;
                } else if ("clip-path".equals(name)) {
                    a aVar = new a();
                    aVar.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.b.add(aVar);
                    if (aVar.getPathName() != null) {
                        fVar.k.put(aVar.getPathName(), aVar);
                    }
                    gVar.a = aVar.o | gVar.a;
                } else if ("group".equals(name)) {
                    c cVar2 = new c();
                    cVar2.a(resources, attributeSet, theme, xmlPullParser);
                    cVar.b.add(cVar2);
                    arrayDeque.push(cVar2);
                    if (cVar2.getGroupName() != null) {
                        fVar.k.put(cVar2.getGroupName(), cVar2);
                    }
                    gVar.a = cVar2.e | gVar.a;
                }
            } else if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            throw new XmlPullParserException("no path defined");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.g = z;
    }

    private boolean a() {
        return Build.VERSION.SDK_INT >= 17 && isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
    }

    @Override // androidx.vectordrawable.graphics.drawable.b, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (this.d != null) {
            this.d.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        if (this.d != null) {
            return this.d.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.b.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        if (this.d != null) {
            this.d.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j) {
        if (this.d != null) {
            this.d.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (this.d != null) {
            return this.d.setVisible(z, z2);
        }
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        if (this.d != null) {
            this.d.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static class h extends Drawable.ConstantState {
        private final Drawable.ConstantState a;

        public h(Drawable.ConstantState constantState) {
            this.a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.a.newDrawable();
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.a.newDrawable(resources);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.a.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class g extends Drawable.ConstantState {
        int a;
        f b;
        ColorStateList c;
        PorterDuff.Mode d;
        boolean e;
        Bitmap f;
        ColorStateList g;
        PorterDuff.Mode h;
        int i;
        boolean j;
        boolean k;
        Paint l;

        public g(g gVar) {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            if (gVar != null) {
                this.a = gVar.a;
                this.b = new f(gVar.b);
                if (gVar.b.b != null) {
                    this.b.b = new Paint(gVar.b.b);
                }
                if (gVar.b.a != null) {
                    this.b.a = new Paint(gVar.b.a);
                }
                this.c = gVar.c;
                this.d = gVar.d;
                this.e = gVar.e;
            }
        }

        public void a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f, (Rect) null, rect, a(colorFilter));
        }

        public boolean a() {
            return this.b.getRootAlpha() < 255;
        }

        public Paint a(ColorFilter colorFilter) {
            if (!a() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                this.l = new Paint();
                this.l.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public void a(int i, int i2) {
            this.f.eraseColor(0);
            this.b.a(new Canvas(this.f), i, i2, (ColorFilter) null);
        }

        public void b(int i, int i2) {
            if (this.f == null || !c(i, i2)) {
                this.f = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public boolean c(int i, int i2) {
            return i == this.f.getWidth() && i2 == this.f.getHeight();
        }

        public boolean b() {
            return !this.k && this.g == this.c && this.h == this.d && this.j == this.e && this.i == this.b.getRootAlpha();
        }

        public void c() {
            this.g = this.c;
            this.h = this.d;
            this.i = this.b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        public g() {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            this.b = new f();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.a;
        }

        public boolean d() {
            return this.b.a();
        }

        public boolean a(int[] iArr) {
            boolean a = this.b.a(iArr);
            this.k |= a;
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class f {
        private static final Matrix n = new Matrix();
        Paint a;
        Paint b;
        final c c;
        float d;
        float e;
        float f;
        float g;
        int h;
        String i;
        Boolean j;
        final ArrayMap<String, Object> k;
        private final Path l;
        private final Path m;
        private final Matrix o;
        private PathMeasure p;
        private int q;

        private static float a(float f, float f2, float f3, float f4) {
            return (f * f4) - (f2 * f3);
        }

        public f() {
            this.o = new Matrix();
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 255;
            this.i = null;
            this.j = null;
            this.k = new ArrayMap<>();
            this.c = new c();
            this.l = new Path();
            this.m = new Path();
        }

        public void setRootAlpha(int i) {
            this.h = i;
        }

        public int getRootAlpha() {
            return this.h;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public f(f fVar) {
            this.o = new Matrix();
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 255;
            this.i = null;
            this.j = null;
            this.k = new ArrayMap<>();
            this.c = new c(fVar.c, this.k);
            this.l = new Path(fVar.l);
            this.m = new Path(fVar.m);
            this.d = fVar.d;
            this.e = fVar.e;
            this.f = fVar.f;
            this.g = fVar.g;
            this.q = fVar.q;
            this.h = fVar.h;
            this.i = fVar.i;
            String str = fVar.i;
            if (str != null) {
                this.k.put(str, this);
            }
            this.j = fVar.j;
        }

        private void a(c cVar, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            cVar.a.set(matrix);
            cVar.a.preConcat(cVar.d);
            canvas.save();
            for (int i3 = 0; i3 < cVar.b.size(); i3++) {
                d dVar = cVar.b.get(i3);
                if (dVar instanceof c) {
                    a((c) dVar, cVar.a, canvas, i, i2, colorFilter);
                } else if (dVar instanceof e) {
                    a(cVar, (e) dVar, canvas, i, i2, colorFilter);
                }
            }
            canvas.restore();
        }

        public void a(Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            a(this.c, n, canvas, i, i2, colorFilter);
        }

        private void a(c cVar, e eVar, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            float f = i / this.f;
            float f2 = i2 / this.g;
            float min = Math.min(f, f2);
            Matrix matrix = cVar.a;
            this.o.set(matrix);
            this.o.postScale(f, f2);
            float a = a(matrix);
            if (a != 0.0f) {
                eVar.a(this.l);
                Path path = this.l;
                this.m.reset();
                if (eVar.a()) {
                    this.m.setFillType(eVar.n == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                    this.m.addPath(path, this.o);
                    canvas.clipPath(this.m);
                    return;
                }
                b bVar = (b) eVar;
                if (!(bVar.f == 0.0f && bVar.g == 1.0f)) {
                    float f3 = (bVar.f + bVar.h) % 1.0f;
                    float f4 = (bVar.g + bVar.h) % 1.0f;
                    if (this.p == null) {
                        this.p = new PathMeasure();
                    }
                    this.p.setPath(this.l, false);
                    float length = this.p.getLength();
                    float f5 = f3 * length;
                    float f6 = f4 * length;
                    path.reset();
                    if (f5 > f6) {
                        this.p.getSegment(f5, length, path, true);
                        this.p.getSegment(0.0f, f6, path, true);
                    } else {
                        this.p.getSegment(f5, f6, path, true);
                    }
                    path.rLineTo(0.0f, 0.0f);
                }
                this.m.addPath(path, this.o);
                if (bVar.c.willDraw()) {
                    ComplexColorCompat complexColorCompat = bVar.c;
                    if (this.b == null) {
                        this.b = new Paint(1);
                        this.b.setStyle(Paint.Style.FILL);
                    }
                    Paint paint = this.b;
                    if (complexColorCompat.isGradient()) {
                        Shader shader = complexColorCompat.getShader();
                        shader.setLocalMatrix(this.o);
                        paint.setShader(shader);
                        paint.setAlpha(Math.round(bVar.e * 255.0f));
                    } else {
                        paint.setShader(null);
                        paint.setAlpha(255);
                        paint.setColor(VectorDrawableCompat.a(complexColorCompat.getColor(), bVar.e));
                    }
                    paint.setColorFilter(colorFilter);
                    this.m.setFillType(bVar.n == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                    canvas.drawPath(this.m, paint);
                }
                if (bVar.a.willDraw()) {
                    ComplexColorCompat complexColorCompat2 = bVar.a;
                    if (this.a == null) {
                        this.a = new Paint(1);
                        this.a.setStyle(Paint.Style.STROKE);
                    }
                    Paint paint2 = this.a;
                    if (bVar.j != null) {
                        paint2.setStrokeJoin(bVar.j);
                    }
                    if (bVar.i != null) {
                        paint2.setStrokeCap(bVar.i);
                    }
                    paint2.setStrokeMiter(bVar.k);
                    if (complexColorCompat2.isGradient()) {
                        Shader shader2 = complexColorCompat2.getShader();
                        shader2.setLocalMatrix(this.o);
                        paint2.setShader(shader2);
                        paint2.setAlpha(Math.round(bVar.d * 255.0f));
                    } else {
                        paint2.setShader(null);
                        paint2.setAlpha(255);
                        paint2.setColor(VectorDrawableCompat.a(complexColorCompat2.getColor(), bVar.d));
                    }
                    paint2.setColorFilter(colorFilter);
                    paint2.setStrokeWidth(bVar.b * min * a);
                    canvas.drawPath(this.m, paint2);
                }
            }
        }

        private float a(Matrix matrix) {
            float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
            matrix.mapVectors(fArr);
            float a = a(fArr[0], fArr[1], fArr[2], fArr[3]);
            float max = Math.max((float) Math.hypot(fArr[0], fArr[1]), (float) Math.hypot(fArr[2], fArr[3]));
            if (max > 0.0f) {
                return Math.abs(a) / max;
            }
            return 0.0f;
        }

        public boolean a() {
            if (this.j == null) {
                this.j = Boolean.valueOf(this.c.b());
            }
            return this.j.booleanValue();
        }

        public boolean a(int[] iArr) {
            return this.c.a(iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static abstract class d {
        public boolean a(int[] iArr) {
            return false;
        }

        public boolean b() {
            return false;
        }

        private d() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class c extends d {
        final Matrix a;
        final ArrayList<d> b;
        float c;
        final Matrix d;
        int e;
        private float f;
        private float g;
        private float h;
        private float i;
        private float j;
        private float k;
        private int[] l;
        private String m;

        public c(c cVar, ArrayMap<String, Object> arrayMap) {
            super();
            e eVar;
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 1.0f;
            this.i = 1.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.d = new Matrix();
            this.m = null;
            this.c = cVar.c;
            this.f = cVar.f;
            this.g = cVar.g;
            this.h = cVar.h;
            this.i = cVar.i;
            this.j = cVar.j;
            this.k = cVar.k;
            this.l = cVar.l;
            this.m = cVar.m;
            this.e = cVar.e;
            String str = this.m;
            if (str != null) {
                arrayMap.put(str, this);
            }
            this.d.set(cVar.d);
            ArrayList<d> arrayList = cVar.b;
            for (int i = 0; i < arrayList.size(); i++) {
                d dVar = arrayList.get(i);
                if (dVar instanceof c) {
                    this.b.add(new c((c) dVar, arrayMap));
                } else {
                    if (dVar instanceof b) {
                        eVar = new b((b) dVar);
                    } else if (dVar instanceof a) {
                        eVar = new a((a) dVar);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.b.add(eVar);
                    if (eVar.m != null) {
                        arrayMap.put(eVar.m, eVar);
                    }
                }
            }
        }

        public c() {
            super();
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 1.0f;
            this.i = 1.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.d = new Matrix();
            this.m = null;
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.d;
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, a.b);
            a(obtainAttributes, xmlPullParser);
            obtainAttributes.recycle();
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.l = null;
            this.c = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "rotation", 5, this.c);
            this.f = typedArray.getFloat(1, this.f);
            this.g = typedArray.getFloat(2, this.g);
            this.h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleX", 3, this.h);
            this.i = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleY", 4, this.i);
            this.j = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateX", 6, this.j);
            this.k = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateY", 7, this.k);
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            a();
        }

        private void a() {
            this.d.reset();
            this.d.postTranslate(-this.f, -this.g);
            this.d.postScale(this.h, this.i);
            this.d.postRotate(this.c, 0.0f, 0.0f);
            this.d.postTranslate(this.j + this.f, this.k + this.g);
        }

        public float getRotation() {
            return this.c;
        }

        public void setRotation(float f) {
            if (f != this.c) {
                this.c = f;
                a();
            }
        }

        public float getPivotX() {
            return this.f;
        }

        public void setPivotX(float f) {
            if (f != this.f) {
                this.f = f;
                a();
            }
        }

        public float getPivotY() {
            return this.g;
        }

        public void setPivotY(float f) {
            if (f != this.g) {
                this.g = f;
                a();
            }
        }

        public float getScaleX() {
            return this.h;
        }

        public void setScaleX(float f) {
            if (f != this.h) {
                this.h = f;
                a();
            }
        }

        public float getScaleY() {
            return this.i;
        }

        public void setScaleY(float f) {
            if (f != this.i) {
                this.i = f;
                a();
            }
        }

        public float getTranslateX() {
            return this.j;
        }

        public void setTranslateX(float f) {
            if (f != this.j) {
                this.j = f;
                a();
            }
        }

        public float getTranslateY() {
            return this.k;
        }

        public void setTranslateY(float f) {
            if (f != this.k) {
                this.k = f;
                a();
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.d
        public boolean b() {
            for (int i = 0; i < this.b.size(); i++) {
                if (this.b.get(i).b()) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.d
        public boolean a(int[] iArr) {
            boolean z = false;
            for (int i = 0; i < this.b.size(); i++) {
                z |= this.b.get(i).a(iArr);
            }
            return z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static abstract class e extends d {
        protected PathParser.PathDataNode[] l;
        String m;
        int n;
        int o;

        public boolean a() {
            return false;
        }

        public e() {
            super();
            this.l = null;
            this.n = 0;
        }

        public e(e eVar) {
            super();
            this.l = null;
            this.n = 0;
            this.m = eVar.m;
            this.o = eVar.o;
            this.l = PathParser.deepCopyNodes(eVar.l);
        }

        public void a(Path path) {
            path.reset();
            PathParser.PathDataNode[] pathDataNodeArr = this.l;
            if (pathDataNodeArr != null) {
                PathParser.PathDataNode.nodesToPath(pathDataNodeArr, path);
            }
        }

        public String getPathName() {
            return this.m;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.l;
        }

        public void setPathData(PathParser.PathDataNode[] pathDataNodeArr) {
            if (!PathParser.canMorph(this.l, pathDataNodeArr)) {
                this.l = PathParser.deepCopyNodes(pathDataNodeArr);
            } else {
                PathParser.updateNodes(this.l, pathDataNodeArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a extends e {
        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.e
        public boolean a() {
            return true;
        }

        a() {
        }

        a(a aVar) {
            super(aVar);
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, a.d);
                a(obtainAttributes, xmlPullParser);
                obtainAttributes.recycle();
            }
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.l = PathParser.createNodesFromPathData(string2);
            }
            this.n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 2, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b extends e {
        ComplexColorCompat a;
        float b;
        ComplexColorCompat c;
        float d;
        float e;
        float f;
        float g;
        float h;
        Paint.Cap i;
        Paint.Join j;
        float k;
        private int[] p;

        b() {
            this.b = 0.0f;
            this.d = 1.0f;
            this.e = 1.0f;
            this.f = 0.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = Paint.Cap.BUTT;
            this.j = Paint.Join.MITER;
            this.k = 4.0f;
        }

        b(b bVar) {
            super(bVar);
            this.b = 0.0f;
            this.d = 1.0f;
            this.e = 1.0f;
            this.f = 0.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = Paint.Cap.BUTT;
            this.j = Paint.Join.MITER;
            this.k = 4.0f;
            this.p = bVar.p;
            this.a = bVar.a;
            this.b = bVar.b;
            this.d = bVar.d;
            this.c = bVar.c;
            this.n = bVar.n;
            this.e = bVar.e;
            this.f = bVar.f;
            this.g = bVar.g;
            this.h = bVar.h;
            this.i = bVar.i;
            this.j = bVar.j;
            this.k = bVar.k;
        }

        private Paint.Cap a(int i, Paint.Cap cap) {
            switch (i) {
                case 0:
                    return Paint.Cap.BUTT;
                case 1:
                    return Paint.Cap.ROUND;
                case 2:
                    return Paint.Cap.SQUARE;
                default:
                    return cap;
            }
        }

        private Paint.Join a(int i, Paint.Join join) {
            switch (i) {
                case 0:
                    return Paint.Join.MITER;
                case 1:
                    return Paint.Join.ROUND;
                case 2:
                    return Paint.Join.BEVEL;
                default:
                    return join;
            }
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, a.c);
            a(obtainAttributes, xmlPullParser, theme);
            obtainAttributes.recycle();
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.p = null;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.m = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.l = PathParser.createNodesFromPathData(string2);
                }
                this.c = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
                this.e = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.e);
                this.i = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.i);
                this.j = a(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.j);
                this.k = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.k);
                this.a = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
                this.d = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.d);
                this.b = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.b);
                this.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.g);
                this.h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.h);
                this.f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.f);
                this.n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.n);
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.d
        public boolean b() {
            return this.c.isStateful() || this.a.isStateful();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.d
        public boolean a(int[] iArr) {
            return this.a.onStateChanged(iArr) | this.c.onStateChanged(iArr);
        }

        @ColorInt
        int getStrokeColor() {
            return this.a.getColor();
        }

        void setStrokeColor(int i) {
            this.a.setColor(i);
        }

        float getStrokeWidth() {
            return this.b;
        }

        void setStrokeWidth(float f) {
            this.b = f;
        }

        float getStrokeAlpha() {
            return this.d;
        }

        void setStrokeAlpha(float f) {
            this.d = f;
        }

        @ColorInt
        int getFillColor() {
            return this.c.getColor();
        }

        void setFillColor(int i) {
            this.c.setColor(i);
        }

        float getFillAlpha() {
            return this.e;
        }

        void setFillAlpha(float f) {
            this.e = f;
        }

        float getTrimPathStart() {
            return this.f;
        }

        void setTrimPathStart(float f) {
            this.f = f;
        }

        float getTrimPathEnd() {
            return this.g;
        }

        void setTrimPathEnd(float f) {
            this.g = f;
        }

        float getTrimPathOffset() {
            return this.h;
        }

        void setTrimPathOffset(float f) {
            this.h = f;
        }
    }
}
