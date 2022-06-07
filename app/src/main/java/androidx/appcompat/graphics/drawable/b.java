package androidx.appcompat.graphics.drawable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.graphics.drawable.a;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StateListDrawable.java */
@SuppressLint({"RestrictedAPI"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class b extends a {
    private a a;
    private boolean b;

    @Override // androidx.appcompat.graphics.drawable.a, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    b() {
        this(null, null);
    }

    public void addState(int[] iArr, Drawable drawable) {
        if (drawable != null) {
            this.a.a(iArr, drawable);
            onStateChange(getState());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.graphics.drawable.a, android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        int b = this.a.b(iArr);
        if (b < 0) {
            b = this.a.b(StateSet.WILD_CARD);
        }
        return a(b) || onStateChange;
    }

    public void inflate(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawable);
        setVisible(obtainAttributes.getBoolean(R.styleable.StateListDrawable_android_visible, true), true);
        a(obtainAttributes);
        a(resources);
        obtainAttributes.recycle();
        a(context, resources, xmlPullParser, attributeSet, theme);
        onStateChange(getState());
    }

    private void a(TypedArray typedArray) {
        a aVar = this.a;
        if (Build.VERSION.SDK_INT >= 21) {
            aVar.f |= typedArray.getChangingConfigurations();
        }
        aVar.k = typedArray.getBoolean(R.styleable.StateListDrawable_android_variablePadding, aVar.k);
        aVar.n = typedArray.getBoolean(R.styleable.StateListDrawable_android_constantSize, aVar.n);
        aVar.C = typedArray.getInt(R.styleable.StateListDrawable_android_enterFadeDuration, aVar.C);
        aVar.D = typedArray.getInt(R.styleable.StateListDrawable_android_exitFadeDuration, aVar.D);
        aVar.z = typedArray.getBoolean(R.styleable.StateListDrawable_android_dither, aVar.z);
    }

    private void a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        a aVar = this.a;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next2 == 3) {
                    return;
                }
                if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals("item")) {
                    TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawableItem);
                    Drawable drawable = null;
                    int resourceId = obtainAttributes.getResourceId(R.styleable.StateListDrawableItem_android_drawable, -1);
                    if (resourceId > 0) {
                        drawable = ResourceManagerInternal.get().getDrawable(context, resourceId);
                    }
                    obtainAttributes.recycle();
                    int[] a2 = a(attributeSet);
                    if (drawable == null) {
                        do {
                            next = xmlPullParser.next();
                        } while (next == 4);
                        if (next != 2) {
                            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                        } else if (Build.VERSION.SDK_INT >= 21) {
                            drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                        } else {
                            drawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                        }
                    }
                    aVar.a(a2, drawable);
                }
            } else {
                return;
            }
        }
    }

    int[] a(AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        int[] iArr = new int[attributeCount];
        int i = 0;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            int attributeNameResource = attributeSet.getAttributeNameResource(i2);
            if (!(attributeNameResource == 0 || attributeNameResource == 16842960 || attributeNameResource == 16843161)) {
                i++;
                if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                    attributeNameResource = -attributeNameResource;
                }
                iArr[i] = attributeNameResource;
            }
        }
        return StateSet.trimStateSet(iArr, i);
    }

    @Override // androidx.appcompat.graphics.drawable.a, android.graphics.drawable.Drawable
    @NonNull
    public Drawable mutate() {
        if (!this.b && super.mutate() == this) {
            this.a.a();
            this.b = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public a c() {
        return new a(this.a, this, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.graphics.drawable.a
    public void clearMutated() {
        super.clearMutated();
        this.b = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StateListDrawable.java */
    /* loaded from: classes.dex */
    public static class a extends a.c {
        int[][] L;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(a aVar, b bVar, Resources resources) {
            super(aVar, bVar, resources);
            if (aVar != null) {
                this.L = aVar.L;
            } else {
                this.L = new int[c()];
            }
        }

        @Override // androidx.appcompat.graphics.drawable.a.c
        void a() {
            int[][] iArr = this.L;
            int[][] iArr2 = new int[iArr.length];
            for (int length = iArr.length - 1; length >= 0; length--) {
                int[][] iArr3 = this.L;
                iArr2[length] = iArr3[length] != null ? (int[]) iArr3[length].clone() : null;
            }
            this.L = iArr2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int a(int[] iArr, Drawable drawable) {
            int a = a(drawable);
            this.L[a] = iArr;
            return a;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int b(int[] iArr) {
            int[][] iArr2 = this.L;
            int d = d();
            for (int i = 0; i < d; i++) {
                if (StateSet.stateSetMatches(iArr2[i], iArr)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new b(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new b(this, resources);
        }

        @Override // androidx.appcompat.graphics.drawable.a.c
        public void e(int i, int i2) {
            super.e(i, i2);
            int[][] iArr = new int[i2];
            System.arraycopy(this.L, 0, iArr, 0, i);
            this.L = iArr;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.a, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.graphics.drawable.a
    public void a(@NonNull a.c cVar) {
        super.a(cVar);
        if (cVar instanceof a) {
            this.a = (a) cVar;
        }
    }

    b(a aVar, Resources resources) {
        a(new a(aVar, this, resources));
        onStateChange(getState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@Nullable a aVar) {
        if (aVar != null) {
            a(aVar);
        }
    }
}
