package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public abstract class Key {
    public static int UNSET = -1;
    int a;
    int b;
    String c = null;
    HashMap<String, ConstraintAttribute> d;
    protected int mType;

    public abstract void addValues(HashMap<String, SplineSet> hashMap);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void getAttributeNames(HashSet<String> hashSet);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void load(Context context, AttributeSet attributeSet);

    public void setInterpolation(HashMap<String, Integer> hashMap) {
    }

    public abstract void setValue(String str, Object obj);

    public Key() {
        int i = UNSET;
        this.a = i;
        this.b = i;
    }

    public boolean a(String str) {
        String str2 = this.c;
        if (str2 == null || str == null) {
            return false;
        }
        return str.matches(str2);
    }

    float a(Object obj) {
        return obj instanceof Float ? ((Float) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    int b(Object obj) {
        return obj instanceof Integer ? ((Integer) obj).intValue() : Integer.parseInt(obj.toString());
    }

    boolean c(Object obj) {
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : Boolean.parseBoolean(obj.toString());
    }
}
