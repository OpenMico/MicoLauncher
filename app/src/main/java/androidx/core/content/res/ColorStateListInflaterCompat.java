package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import androidx.core.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> a = new ThreadLocal<>();

    private ColorStateListInflaterCompat() {
    }

    @Nullable
    public static ColorStateList inflate(@NonNull Resources resources, @XmlRes int i, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, resources.getXml(i), theme);
        } catch (Exception e) {
            Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
            return null;
        }
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName();
        if (name.equals("selector")) {
            return a(resources, xmlPullParser, attributeSet, theme);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    private static ColorStateList a(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i;
        int i2 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr = new int[20];
        int[] iArr2 = new int[iArr.length];
        int i3 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i2 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            } else if (next != 2 || depth > depth2 || !xmlPullParser.getName().equals("item")) {
                i2 = 1;
            } else {
                TypedArray a2 = a(resources, theme, attributeSet, R.styleable.ColorStateListItem);
                int resourceId = a2.getResourceId(R.styleable.ColorStateListItem_android_color, -1);
                if (resourceId == -1 || a(resources, resourceId)) {
                    i = a2.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                } else {
                    try {
                        i = createFromXml(resources, resources.getXml(resourceId), theme).getDefaultColor();
                    } catch (Exception unused) {
                        i = a2.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                    }
                }
                float f = 1.0f;
                if (a2.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                    f = a2.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
                } else if (a2.hasValue(R.styleable.ColorStateListItem_alpha)) {
                    f = a2.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
                }
                a2.recycle();
                int attributeCount = attributeSet.getAttributeCount();
                int[] iArr3 = new int[attributeCount];
                int i4 = 0;
                for (int i5 = 0; i5 < attributeCount; i5++) {
                    int attributeNameResource = attributeSet.getAttributeNameResource(i5);
                    if (!(attributeNameResource == 16843173 || attributeNameResource == 16843551 || attributeNameResource == R.attr.alpha)) {
                        i4++;
                        if (!attributeSet.getAttributeBooleanValue(i5, false)) {
                            attributeNameResource = -attributeNameResource;
                        }
                        iArr3[i4] = attributeNameResource;
                    }
                }
                int[] trimStateSet = StateSet.trimStateSet(iArr3, i4);
                iArr2 = b.a(iArr2, i3, a(i, f));
                iArr = (int[][]) b.a(iArr, i3, trimStateSet);
                i3++;
                i2 = 1;
            }
        }
        int[] iArr4 = new int[i3];
        int[][] iArr5 = new int[i3];
        System.arraycopy(iArr2, 0, iArr4, 0, i3);
        System.arraycopy(iArr, 0, iArr5, 0, i3);
        return new ColorStateList(iArr5, iArr4);
    }

    private static boolean a(@NonNull Resources resources, @ColorRes int i) {
        TypedValue a2 = a();
        resources.getValue(i, a2, true);
        return a2.type >= 28 && a2.type <= 31;
    }

    @NonNull
    private static TypedValue a() {
        TypedValue typedValue = a.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        a.set(typedValue2);
        return typedValue2;
    }

    private static TypedArray a(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    @ColorInt
    private static int a(@ColorInt int i, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        return (i & 16777215) | (Math.round(Color.alpha(i) * f) << 24);
    }
}
